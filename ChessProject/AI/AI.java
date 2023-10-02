package ChessProject.AI;

import ChessProject.Board;
import ChessProject.Move;

public class AI {

    private MoveNode tree;

    public AI(Board board) {
        tree = new MoveNode(board); //base
    }

    public Move calculateMove(Board board) {
        return board.generateAllLegalMoves(board.getTurn()).get(0);
        //return null;
    }

    public void createTree(int depth, Board board) {
        if (depth == -1) {
            return;
        }
        else {
            
        }
        //createTree(depth - 1)
        tree.addLegalMoveNodes();
        for (MoveNode m: tree.getLegalMoves()) {
            m.addLegalMoveNodes();
        }
        //createTree(depth - 1)
    }

    public static void main(String[] args) {
        Board board = new Board();
        AI ai = new AI(board);
        ai.createTree(1, board);
    }
}