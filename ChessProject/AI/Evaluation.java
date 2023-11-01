package ChessProject.AI;

import java.util.ArrayList;
import java.util.HashMap;

import ChessProject.*;
import ChessProject.Pieces.*;

public class Evaluation {

    public static double pointsWeight = 3; 
    public static double centerControlWeight = 1; 
    public static double evaluate(Board board) {
        board.switchTurn();
        int numPossibleNextMovesSameColor = board.generateAllLegalMoves().size();
        board.switchTurn();
        //System.out.println(board.generateAllLegalMoves(board.getTurn()));
        //board.printBoard();
        //return board.generateAllLegalMoves().size();
        //System.out.println("EVAL: " + (pointsWeight * evaluatePoints(board) + centerControlWeight * evaluateCenterControl(board) + board.generateAllLegalMoves(board.getTurn()).size()));
        int factor = 1;
        if (board.getTurn() == GColor.BLACK) factor = -1;
        return (pointsWeight * evaluatePoints(board) + centerControlWeight * evaluateCenterControl(board) + numPossibleNextMovesSameColor) * factor;
    }

    /**
     * Will return positive if white has more points
     * Negative if black has more points
     * @param board
     * @return
     */
    public static int evaluatePoints(Board board) {
        Square[][] boardArray = board.getBoard();
        int points = 0;
        for (int i = 0; i < 8; i ++) {
            for (int j = 0; j < 8; j++) {
                if (boardArray[i][j].getPiece().getName().equals("Empty")) {
                    continue;
                }
                if (boardArray[i][j].getPiece().getColor() == board.getTurn()) {
                    points-= boardArray[i][j].getPiece().getValue();
                }
                else {
                    points+= boardArray[i][j].getPiece().getValue();
                } 
            }
        }
        return points;
    }

    /**
     * Finds who is attacking the center more
     * @param board
     * @return
     */
    public static int evaluateCenterControl(Board board) {
        Square[][] boardArray = board.getBoard();
        GColor saveTurn = board.getTurn();
        ArrayList<Square> whitePieces = board.findAllPiecesOfColor(GColor.WHITE);
        ArrayList<Square> blackPieces = board.findAllPiecesOfColor(GColor.BLACK);
        int numPiecesControlling = 0;

        board.makeTurn(GColor.WHITE);
        for (Square square: whitePieces) {
            for (int i = 3; i <= 4; i++) {
                for (int j = 3; j <= 4; j++) {
                    Move move = new Move(square, boardArray[i][j]);
                    if (square.getPiece().controllingSquare(board, move)) {
                        numPiecesControlling--;
                    }
                }
            }
        }

        board.makeTurn(GColor.BLACK);
        for (Square square: blackPieces) {
            for (int i = 3; i <= 4; i++) {
                for (int j = 3; j <= 4; j++) {
                    Move move = new Move(square, boardArray[i][j]);
                    if (square.getPiece().controllingSquare(board, move)) {
                        numPiecesControlling++;
                    }
                }
            }
        }

        board.makeTurn(saveTurn);
        return numPiecesControlling;
    }

    public static HashMap<GColor,ArrayList<Square>> getPastPawns(Board board) {
        HashMap<GColor,ArrayList<Square>> pastPawns = new HashMap<GColor,ArrayList<Square>>();
        pastPawns.put(GColor.WHITE, new ArrayList<Square>());
        pastPawns.put(GColor.BLACK, new ArrayList<Square>());
        //Square[][] boardArray = board.getBoard();

        ArrayList<Square> allWhitePieces = board.findAllPiecesOfColor(GColor.WHITE);
        ArrayList<Square> allBlackPieces = board.findAllPiecesOfColor(GColor.BLACK);

        for (int i = 0; i < allWhitePieces.size(); i++) {
            if (!allWhitePieces.get(i).getPiece().getName().equals("Pawn")) {
                i--;
            }
        }
        for (int i = 0; i < allBlackPieces.size(); i++) {
            if (!allBlackPieces.get(i).getPiece().getName().equals("Pawn")) {
                i--;
            }
        }

        /*for (Square square: allWhitePieces) {
            for (Square square2: allBlackPieces) {

            }
        }

        for (Square square: allBlackPieces) {

        }*/
        return pastPawns;
    }
    
    public static int piecesAttackingKing(Board board) {

        

        return 0;
    }
}
