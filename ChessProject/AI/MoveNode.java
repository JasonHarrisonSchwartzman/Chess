package ChessProject.AI;

import ChessProject.*;
import java.util.ArrayList;

public class MoveNode {
    private MoveNode prev; //previous move
    private Move move; //This is the move that lead to the current state of the board
    private Board board; //The board after the move was made
    private MoveNode[] legalMoves; //The next tree of move nodes
    private int totalLegalMoves; //Self explanatory
    private double eval;

    /**
     * Create a new node that contains a move and
     * @param move
     * @param board
     */
    public MoveNode(MoveNode prev, Board board, Move move) {
        this.prev = prev;
        this.move = move;
        this.board = new Board();//makes the move
        for (Move m: board.getMoves()) {
            this.board.move(m.getStartSquare().getCoordinates().getCoordinate(),m.getEndSquare().getCoordinates().getCoordinate());
        }
        this.board.move(move.getStartSquare().getCoordinates().getCoordinate(),move.getEndSquare().getCoordinates().getCoordinate());
        eval = Evaluation.evaluate(this.board);
        
        //totalLegalMoves = this.board.generateAllLegalMoves(this.board.getTurn()).size();
        //addLegalMoveNodes();
    }

    public MoveNode(Board board) {
        /*this.board = board;
        this.totalLegalMoves = board.generateAllLegalMoves(board.getTurn()).size();
        System.out.println("TOTAL LEGAL MOVES: " + totalLegalMoves);
        //addLegalMoveNodes();
        eval = Evaluation.evaluate(board);*/
    }

    /**
     * Initializes the legalMove MoveNode arrayList
     */
    public void addLegalMoveNodes() {
        legalMoves = new MoveNode[totalLegalMoves];
        ArrayList<Move> moves = board.generateAllLegalMoves(board.getTurn());
        System.out.println("Num Legal moves: " + moves.size() + " Legal moves length: " + legalMoves.length);
        for (int i = 0; i < totalLegalMoves; i++) {
            System.out.println(i);
            legalMoves[i] = new MoveNode(this, board, moves.get(i));
        }
    }

    public void evaluateMoveNode() {
        eval = Evaluation.evaluate(board);
    }

    //getters
    public MoveNode getMoveNode() {
        return prev;
    }

    public Move getMove() {
        return move; 
    }

    public Board getBoard() {
        return board;
    }

    public MoveNode[] getLegalMoves() {
        return legalMoves;
    }

    public int getTotalLegalMoves() {
        return totalLegalMoves;
    }

    public double getEval() {
        return eval;
    }

    public void setEval(double eval) {
        this.eval = eval;
    }
}
