package ChessProject.AI;

import ChessProject.Board;
import ChessProject.Move;
import ChessProject.Pieces.GColor;

public class AI {

    private MoveNode tree;
    private GColor color;
    public AI(Board board) {
        tree = new MoveNode(board); //base
        color = board.getTurn();
    }

    public Move calculateMove(Board board) {
        return board.generateAllLegalMoves(board.getTurn()).get(0);
    }
    /*
     *                  e4
     *        e5  1.0               e6   1.1
     *  Nf3  1.2    d3 0.8         Nf3  0.9       d3  1.2               
     * 
     * 
     * 
     */

    public void findBestMove() {

    }

    public void createTree(int depth, Board board) {
        if (depth == 0) {
            return;
        }
        //createTree(depth - 1)
        tree.addLegalMoveNodes();
        //createTree(depth - 1)
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

    public static void main(String[] args) {
        Board board = new Board();
        AI ai = new AI(board);
        ai.createTree(1, board);
    }
}