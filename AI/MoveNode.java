package ChessProject.AI;

import ChessProject.*;
import java.util.ArrayList;

public class MoveNode {
    
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
    public MoveNode(Move move, Board board) {
        this.move = move;
        this.board = new Board(board, move);
        totalLegalMoves = board.generateAllLegalMoves(board.getTurn()).size();
        eval = Evaluation.evaluate(board);
    }

    public MoveNode(Board board) {
        this.board = board;
        totalLegalMoves = board.generateAllLegalMoves(board.getTurn()).size();
        eval = Evaluation.evaluate(board);
    }

    /**
     * Initializes the legalMove MoveNode arrayList
     */
    public void addLegalMoveNodes() {
        legalMoves = new MoveNode[totalLegalMoves];
        ArrayList<Move> moves = board.generateAllLegalMoves(board.getTurn());
        for (int i = 0; i < totalLegalMoves; i++) {
            legalMoves[i] = new MoveNode(moves.get(i), board);
        }
        legalMoves[1].getBoard().printBoard();
    }

    //getters
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
}
