package distrOthello;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*This panel displays the color of the pieces of each player,
 * the score of each player and if the game ends it also shows who is the winner. */
public class ScorePanel extends JPanel {

	public int whiteScore=2;
	public int blackScore=2;
	public int turn=0;
	public String winner;
	public JLabel[] labels = new JLabel[5];


	private static final long serialVersionUID = 1L;
	public ImageIcon[] pieces = new ImageIcon[2];
	
	/*getters*/
	public int getWhiteScore() {return this.whiteScore;}
	public int getBlackScore() {return this.blackScore;}
	public int getTurn() {return this.turn;}
	
	/*setters*/
	public void setBlackScore(int bs) {this.blackScore=bs;}
	public void setWhiteScore(int ws) {this.whiteScore=ws;}
	public void setTurn(int turn) {this.turn=turn;}

	
	public ScorePanel() {
	    pieces[0] = new ImageIcon("images/negras.png");
	    pieces[1] = new ImageIcon("images/blancas.png");
	    
	    labels[0] = new JLabel();
	    labels[0].setIcon(pieces[0]);
	    labels[0].setHorizontalAlignment(JLabel.CENTER);
	    
	    labels[1] = new JLabel("    Black Player : "+ blackScore +"    ");
	    labels[1].setHorizontalAlignment(JLabel.CENTER);
	    
	    labels[2] = new JLabel();
	    labels[2].setIcon(pieces[1]);
	    labels[2].setHorizontalAlignment(JLabel.CENTER);
	    
	    labels[3] = new JLabel("    White Player : " + whiteScore+"    ");
	    labels[3].setHorizontalAlignment(JLabel.CENTER);
	    
	    labels[4] = new JLabel();	
	    labels[4].setHorizontalAlignment(JLabel.CENTER);
		labels[4].setText("");


	    
		this.setLayout(new GridLayout(5,1));
		this.setBackground(new Color(170,170,170));
		this.setSize(200,600);
		for(int i=0;i<5;i++) {this.add(labels[i]);}

	}
	
	public void updateScore(int bs, int ws, int turn) {
		setBlackScore(bs);
		setWhiteScore(ws);
		setTurn(turn);
		labels[1].setText("    Black Player : "+ blackScore +"    ");
		labels[3].setText("    White Player : " + whiteScore +"    ");
		labels[4].setText(new String("Turn "+turn));
	}

}
