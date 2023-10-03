package ChessProject.Pieces;

import ChessProject.Board;
import ChessProject.Move;
import ChessProject.Square;

/**
 * The general Piece object
 * Pieces have:
 * a name
 * notation (one or 0 letter abbreviation) (I actually didn't do anything with notation in the class lol, so right now its useless)
 * color (white or black)
 * if they have moved (for castling)
 * 
 * They also have a symbol which is what gets printed out the the terminal.
 */
public class Piece{
    private String name;
    private char notation;
    private GColor color;
    private boolean hasMoved;
    private String symbol;
    private int value;
    public Piece(String name, GColor color) {
        this.name = name;
        this.color = color;
    }

    /**
     * Checks to see if two pieces are equal
     * @param piece
     * @return boolean
     */
    public boolean equals(Piece piece) {
        return name.equals(piece.getName()) && color == piece.getColor(); //&& hasMoved == piece.getHasMoved();
    }

    /**
     * Enter a piece object to create a new Piece object with the same information
     * @param p
     */
    public Piece(Piece p) {
        //if (p == null) {
        //    return;
        //}
        name = p.name;
        notation = p.getNotation();
        color = p.getColor();
        hasMoved = p.getHasMoved();
        symbol = p.getSymbol();
        value = p.getValue();
    }

    /**
     * Enter a name to specify the piece (for creating the board)
     * @param name
     */
    public Piece(String name){
        this.name = name;
    }
    

    //getters
    public String getName(){return name;}
    public char getNotation() {return notation;}
    public GColor getColor(){return color;}
    public String getSymbol(){return symbol;} 
    public boolean getHasMoved(){return hasMoved;}
    public void hasMoved(){hasMoved = true;}
    public int getValue(){return value;} // it should NEVER return this tho


    public boolean controllingSquare(Board board, Move move) {
        return true;
    }

    /**
     * This will be testing general things about moving a piece
     * @param b
     * @param move
     * @return
     */
    public boolean canMove(Board board, Move move) {
        GColor turn = board.getTurn();
        Square startSquare = move.getStartSquare();
        Square endSquare = move.getEndSquare();
        Piece piece = move.getPiece();

        // if the the start and end square are the same or if the turn and piece are not the same color 
        //and if the piece on the endSquare is the player's own piece
        if (startSquare.equals(endSquare) || piece.getColor() != turn || endSquare.getPiece().getColor() == turn) {
            if (startSquare.equals(endSquare)) {
                board.debugger("CANT MOVE: YOU ARE NOT MOVING ANYWHER");
            }
            if (piece.getColor() != turn) {
                board.debugger("CANT MOVE: YOU ARE MOVING A PIECE THATS NOT YOURS");
                board.debugger("YOUR PIECE IS: " + piece.getColor() + " THE CURRENT TURN IS: " + turn);
            }
            if (endSquare.getPiece().getColor() == turn) {
                board.debugger("CANT MOVE: YOUR PIECE IS ON THE END SQUARE");
            }
            return false;
        }
        return true;
    }
    
    /**
     * Prints out the name of the piece and the color
     */
    public String toString() {
        return getName() + " " + getColor();
    }

    public String getInfo() {
        return toString() + " | Has moved: " + hasMoved;
    }

}
