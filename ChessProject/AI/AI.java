package ChessProject.AI;

import ChessProject.Board;
import ChessProject.Move;
import ChessProject.Pieces.GColor;
import java.util.ArrayList;

public class AI {

    private MoveNode[] moveNodes;
    private int numMoveNodes;
    private Board board; //curent board
    private GColor color;
    public AI(Board board) {
        this.board = new Board();//makes the move
        for (Move m: board.getMoves()) {
            //System.out.println(m);
            this.board.move(m.getStartSquare().getCoordinates().getCoordinate(),m.getEndSquare().getCoordinates().getCoordinate());
        }
        numMoveNodes = this.board.generateAllLegalMoves(board.getTurn()).size();
        moveNodes = new MoveNode[numMoveNodes];
        color = this.board.getTurn();
    }

    public Move calculateMove() {
        //System.out.println("Calculating move");
        createTree(2,board,null);
        return findBestMove();
        //return board.generateAllLegalMoves(board.getTurn()).get(0);
    }
    /*
     *                  e4
     *        e5  1.0               e6   1.1
     *  Nf3  1.2    d3 0.8         Nf3  0.9       d3  1.2               
     * 
     * 
     * 
     */

    public Move findBestMove() {
        Move bestMove = null;
        if (color == GColor.BLACK) {//find whites best move
            double maxVal = Double.NEGATIVE_INFINITY;
            for (MoveNode node: moveNodes) {
                if (node.getEval() > maxVal) {
                    maxVal = node.getEval();
                    bestMove = node.getMove();
                }
            }
            return bestMove;
        }
        else {
            double minVal = Double.POSITIVE_INFINITY;

            for (MoveNode node: moveNodes) {
                if (node.getEval() < minVal) {
                    minVal = node.getEval();
                    bestMove = node.getMove();
                }
            }
            return bestMove;
        }
    }

    public void createTree(int depth, Board board, MoveNode node) {
        if (depth == 0) {
            return;
        }
        if (node == null) {//only works for depth of 1
            ArrayList<Move> moves = board.generateAllLegalMoves();
            for (int i = 0; i < numMoveNodes; i++) {
                moveNodes[i] = new MoveNode(null, board, moves.get(i));
                if (depth > 1) moveNodes[i].addLegalMoveNodes();
                createTree(depth - 1, board, moveNodes[i]);
            }
        }
        else {
            for (int i = 0; i < node.getLegalMoves().length; i++) {
                MoveNode currentMove = node.getLegalMoves()[i];
                if (depth > 1) currentMove.addLegalMoveNodes();
                createTree(depth - 1, board,currentMove);
            }
        }
    }

    /**
     * Finds the best move for the opponent and sets the eval of this current node to the opponent best move's eval
     * @param moveNode
     */
    public void respectOpponentsBestMove(MoveNode moveNode) {
        if (color == GColor.BLACK) {//find whites best move
            double maxVal = Double.NEGATIVE_INFINITY;
            
            for (MoveNode node: moveNode.getLegalMoves()) {
                if (node.getEval() > maxVal) {
                    maxVal = node.getEval();
                }
            }
            moveNode.setEval(maxVal);
        }
        else {
            double minVal = Double.POSITIVE_INFINITY;

            for (MoveNode node: moveNode.getLegalMoves()) {
                if (node.getEval() < minVal) {
                    minVal = node.getEval();
                }
            }
            moveNode.setEval(minVal);
        }
    }

    /*public static void main(String[] args) {
        Board board = new Board();
        AI ai = new AI(board);
        ai.createTree(1, board, null);
    }*/
}