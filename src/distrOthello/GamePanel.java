package distrOthello;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;


public class GamePanel extends JPanel{

	private static final long serialVersionUID = 1L;

	public OthelloClient mediator;
    public Cell[][] othelloCells;
    public ImageIcon[] pieces = new ImageIcon[2];
    public Point move;
    
    public static final int BLACK = 0, WHITE = 1;	//For the images of the pieces

    /*Constructor of the game board.*/
    public GamePanel(OthelloClient mediator) {
    	this.mediator=mediator;
    	this.othelloCells = new Cell[8][8];
    	// create the images for the Othello pieces
        createImages();

        this.setLayout(new GridLayout(8,8));
        this.setBorder(new CompoundBorder(new EmptyBorder(1,1,1,1),new LineBorder(Color.BLACK)));
        // create the othello board squares
        Insets buttonMargin = new Insets(1,1,1,1);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                othelloCells[i][j] = new Cell(i,j,new JButton());
                othelloCells[i][j].setSize(80,80);
                othelloCells[i][j].setMargin(buttonMargin);
                othelloCells[i][j].setBackground(new Color(0,70,30));
                othelloCells[i][j].addMouseListener(new ListenerMA());
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.add(othelloCells[i][j]);
            }
        }
        
        this.setBackground(new Color(0,0,0));
        this.setSize(800, 800);
        this.setVisible(true);
        
        setupNewGame();
    }
    
    /*Method to get the images of the pieces.*/
    public void createImages() {
        try {
            pieces[BLACK] = new ImageIcon("images/negras.png");
            pieces[WHITE] = new ImageIcon("images/blancas.png");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        
    }


    public void setMove(int x, int y){move=new Point(x,y);}
    
    public Point getMove() {
    	return this.move;
    }
    
    /**
     * Sets up the pieces to begin a new game
     */
    public void setupNewGame() {
    	for(int i=0;i<8;i++) {
    		for(int j = 0;j<8;j++){
    			othelloCells[i][j].setBackground(new Color(0,70,30));
    		}
    	}
        othelloCells[3][3].setIcon(
                pieces[WHITE]);
        othelloCells[4][4].setIcon(
                pieces[WHITE]);
        othelloCells[4][3].setIcon(
                pieces[BLACK]);
        othelloCells[3][4].setIcon(
                pieces[BLACK]);
    }
        
    /*If a human player clicked a cell and the cell has a legal move, we put one piece on the cell
     * */
    public void addPiece(Point po,int plnumber) {
    	othelloCells[po.x][po.y].setIcon(
    			pieces[plnumber]);
    }
    
    /*We put the new pieces on the board*/
    public void updateGamePanelPieces(int[][] board){
    	for(int i=0;i<8;i++) {
    		for(int j = 0;j<8;j++){
    			othelloCells[i][j].setBackground(new Color(0,70,30));
    			if(board[i][j]==1) {addPiece(new Point(i,j),BLACK);}//1 for black cell, 2 for white cell
    			if(board[i][j]==2) {addPiece(new Point(i,j),WHITE);}

    		}
    	}
    }
    /*Used to highlight the cells with legal moves*/
    public void displayLegalMoves(boolean[][]moves) {
    	for(int i=0;i<8;i++) {
    		for(int j = 0;j<8;j++){
    			//The color of the legal squares changes
    			if(moves[i][j]== true) {othelloCells[i][j].setBackground(new Color(0,90,50));}
    			else {othelloCells[i][j].setBackground(new Color(0,70,30));}
    		}
    	}
    }
    
    /*When we click in a square of the board we send the request to make a move.
     * */
    class ListenerMA extends MouseAdapter {
    	public void mouseClicked(MouseEvent e) {
    		Cell btn = (Cell) e.getSource();
    		setMove(btn.getRow(),btn.getCol());
    		MsgMove move  = new MsgMove(mediator.me,getMove());
    		try {
				move.envia(mediator.output);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
    		}
    	}
    
    /*Basically a JButton with the column and row numbers as attributes*/
    class Cell extends JButton {
    	/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		int row;
    	int col;
    	JButton button;
    	
    	public Cell(int i, int j, JButton button) {
    		this.row=i;
    		this.col=j;
    		this.button=button;
    	}
    	
    	int getRow(){return this.row;}
    	int getCol() {return this.col;}
    }


}