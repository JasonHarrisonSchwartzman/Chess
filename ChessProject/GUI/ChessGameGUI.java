package ChessProject.GUI;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ChessProject.Pieces.GColor;
import ChessProject.*;
import ChessProject.AI.*;


public class ChessGameGUI extends Canvas{

static boolean first = false;
static boolean playerWent = false;

static int turn = 1;

static Board board = new Board();
static DefaultTableModel movesHistory;
static ArrayList<String> HMoves = new ArrayList<String>();
static ArrayList<Integer> predictedMove = new ArrayList<Integer>();

public static void main(String[] args) throws IOException{
    Choice();
    GameWindow();
}
public static void Remach(){
    String[] options = new String[] {"Yes", "No"};
    int response = JOptionPane.showOptionDialog(null, "Remach?", "Choice",
    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
    null, options, options[0]);
    switch(response){
        case(0):{
            try {
            turn = 1;
            board = new Board();
            first = false;
            main(null);
        } catch (IOException e) {
            e.printStackTrace();
            }
            break;
        }
        case(1):{
            System.exit(1);
            break;
        }
    }
}

public static void Choice() throws IOException{
    String[] options = new String[] {"White", "Black"};
    int response = JOptionPane.showOptionDialog(null, "Choose color", "Choice",
    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
    null, options, options[0]);
    switch(response){
        case(0):{
            first = false;
            break;
        }
        case(1):{
            first = true;
            break;
        }
    }
}

public static void GameWindow() throws IOException{
    Square[][] boardArray = board.getBoard();
    String[] loc = new String[2];

    //this is cuts out the image for the pieces 
    BufferedImage all=ImageIO.read(new File("ChessProject//GUI//chess.png"));
    BufferedImage ifile = ImageIO.read(new File("ChessProject//GUI//file.png"));
    BufferedImage irank = ImageIO.read(new File("ChessProject//GUI//rank.png"));
    BufferedImage yellow = ImageIO.read(new File("ChessProject//GUI//yellow.png"));
    Image imgs[]=new Image[12];
    Image file[]=new Image[12];
    Image rank[]=new Image[12];
    int Piece_Index=0;
    int File_Index=0;
    int Rank_Index=0;
    for(int y=0;y<400;y+=200){
    for(int x=0;x<1200;x+=200){
        imgs[Piece_Index]=all.getSubimage(x, y, 200, 200).getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH); 
        Piece_Index++;
        file[File_Index]=ifile.getSubimage(x,y,200,200).getScaledInstance(64,64,BufferedImage.SCALE_SMOOTH);
        File_Index++;
        rank[Rank_Index]=irank.getSubimage(x, y, 200, 200).getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
        Rank_Index++;
        }    
    }
    //

    //this makes the window
    JFrame frame = new JFrame();
    frame.setIconImage(ImageIO.read(new File("ChessProject//GUI//knight.png")));
    frame.setTitle("Chess");
    JPanel Chess_Board = new JPanel(){
    //
    //this makes the board (x is just the x-coordinates and y is just y-coordinates)
    @Override
    public void paint(Graphics g){
                
    boolean white = true;
    Color Brown_Tile = new Color(153,102,0);
    for(int y=0; y<8; y++){
    for(int x=0;x<8;x++){
        if(white){
            g.setColor(Color.white);
            }
            else{
            g.setColor(Brown_Tile);
            }
        g.fillRect(x*64, y*64 , 64, 64);
            white=!white;
        }
    white =! white;
}
    //
    //this makes the prediction square
    for(int i=0; i<predictedMove.size();i+=2){
        g.drawImage(yellow, predictedMove.get(i+1)*64, SCX(predictedMove.get(i))*64,this);
    }
    //this place the pieces
    int Piece_ID = -1;
    for(int i=0; i<8; i++){
    for(int j=0; j<8; j++){
        if((boardArray[j][i]).getPiece().getName().equals("Empty")){
            continue;
        }
        if((boardArray[j][i]).getPiece().getName().equals("King")){
            Piece_ID = 0;
        }
        if((boardArray[j][i]).getPiece().getName().equals("Queen")){
            Piece_ID = 1;
        }
        if((boardArray[j][i]).getPiece().getName().equals("Rook")){
            Piece_ID = 4;
        }
        if((boardArray[j][i]).getPiece().getName().equals("Bishop")){
            Piece_ID = 2;
        }
        if((boardArray[j][i]).getPiece().getName().equals("Knight")){
            Piece_ID = 3;
        }
        if((boardArray[j][i]).getPiece().getName().equals("Pawn")){
            Piece_ID = 5;
        }
        if((boardArray[j][i]).getPiece().getColor() != GColor.WHITE){
            Piece_ID += 6;
        }
    g.drawImage(imgs[Piece_ID], i*64, SCX(j)*64, this);
    }
}
    //makes the files and ranks on the board
    for(int j = 0; j<8;j++){
    g.drawImage(file[j], 8*64, SCX(j)*64, this);
    g.drawImage(rank[j], j*64, 8*64, this);
        }   
        }
    };
    //this adds the board to the window
    Chess_Board.setPreferredSize(new Dimension(576,576));
    frame.setLayout(new BorderLayout());
    frame.add(Chess_Board,BorderLayout.CENTER);
    
    //this make the moves history (must be created first to avoid null error when AI goes first)
    Color color = new Color(102,255,102);
    String [] Lables = {"#","White", "Black"};

    //makes the data table
    movesHistory = new DefaultTableModel(Lables,0);

    //puts it on the window
    JTable print = new JTable(movesHistory);
    print.getColumnModel().getColumn(0).setPreferredWidth(3); 
    print.setBackground(color);
    print.setEnabled(false);
    JScrollPane scroll = new JScrollPane(print);
    scroll.setPreferredSize(new Dimension(200,150));

    //this add the moves history to the window
    frame.add(scroll,BorderLayout.EAST);
    frame.setDefaultCloseOperation(3);
    frame.setLocation(600, 200);
    frame.pack();
    frame.setVisible(true);

    if(first){
        System.out.println("i Jason");
        AI ai = new AI(board);
        Move computerMove = ai.calculateMove();
        HMoves.add(Conversions.moveToAlgebraic(computerMove));
        addToTable();
        System.out.println(computerMove);
        board.move(computerMove.getStartSquare().getCoordinates().getCoordinate(),computerMove.getEndSquare().getCoordinates().getCoordinate());
    } 

    //mouse movement on the board.
    //mouse movement.

    //clicks
    frame.addMouseListener(new MouseListener(){
    @Override
    //this method goes third
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    //this method goes first
    public void mousePressed(MouseEvent e) {
    //this grabs the squares data.
    if(loc[0]==null || boardArray[SCX((e.getY()-32)/64)][e.getX()/64].getPiece().getColor() == board.getTurn()){
        loc[0]=getPieceLoc(e.getX(),e.getY()-32,boardArray);
        predictedMove=new ArrayList<Integer>();
        possibleMoves(loc[0],boardArray);
        frame.repaint();
        }    
    }
    @Override
    //this method goes second
    public void mouseReleased(MouseEvent e) {
    //this grabs the second squares data and sends it to board if it can move.
    if(loc[0] != getPieceLoc(e.getX(), e.getY()-32, boardArray)){    
        loc[1]=getPieceLoc(e.getX(),e.getY()-32,boardArray);
        Move move = new Move(board.getSquare(loc[0]),board.getSquare(loc[1]));
        if (board.canMove(move) ) {
            HMoves.add(Conversions.moveToAlgebraic(move));
            addToTable();
            predictedMove=new ArrayList<Integer>();
            board.move(move);
            frame.repaint();
            loc[0]=null;
            board.printBoard();
                        
            if(!Check(frame)){
            AI ai = new AI(board);
            Move computerMove = ai.calculateMove();
            HMoves.add(Conversions.moveToAlgebraic(computerMove));
            addToTable();
            System.out.println(computerMove);
            board.move(computerMove.getStartSquare().getCoordinates().getCoordinate(),computerMove.getEndSquare().getCoordinates().getCoordinate());
        }
        else Remach();
        }
    //this updates the window
    frame.repaint();
    loc[0]=null;
    if(Check(frame)) Remach();
    }
    }
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override 
    public void mouseExited(MouseEvent e) {} 
    });
}

public static boolean Check(JFrame frame) {
    if (board.isInCheck(board.getTurn())) {
        if (board.isInCheckMate(board.getTurn())) {
            frame.repaint();
            JOptionPane.showMessageDialog(null, board.getNextTurn()+" wins!", "Winner", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
            return true;
        }
    }
    else {
        if (board.isInStaleMate(board.getTurn())) {
            frame.repaint();
            JOptionPane.showMessageDialog(null, "Stalemate", " ", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
            return true;
        }
    }
return false;
}



public static void possibleMoves(String loc1,Square[][] b){
    //this get the location of the possible moves
    for(int i=0; i<8;i++){
    for(int j=0;j<8;j++){
        Move move = new Move(board.getSquare(loc1),b[i][j]);
        if(board.canMove(move)){
            predictedMove.add(i);
            predictedMove.add(j);
            }
        }
    }
}

public static String getPieceLoc(int x,int y, Square[][] b){
    //this converts data from the GUI to board
    return b[SCX(y/64)][x/64].getCoordinates().getCoordinate();
}

public static void addToTable(){
    //this updates the table
    if(HMoves == null){
        return;
    }
    if(board.getTurn()==GColor.WHITE){
        Object[] history = {turn,HMoves.get(HMoves.size()-1),null};
        movesHistory.addRow(history);
    }
    else if(board.getTurn() == GColor.BLACK){
        movesHistory.removeRow(movesHistory.getRowCount()-1);
        Object[] history = {turn,HMoves.get(HMoves.size()-2),HMoves.get(HMoves.size()-1)};
        movesHistory.addRow(history);
        turn++;
    }
}
    
public static int SCX(int n){
//Switch case for the chess board
int x = -1;
switch(n){
    case 0:
        x = 7;
        break;
    case 1: 
        x = 6;
        break;
    case 2: 
        x = 5;
        break;
    case 3: 
        x = 4;
        break;
    case 4:
        x = 3;
        break;
    case 5: 
        x = 2;
        break;
    case  6: 
        x = 1;
        break;
    case 7:
        x = 0;
        break;
    }
    return x;
}
}