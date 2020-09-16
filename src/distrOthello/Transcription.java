package distrOthello;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Transcription {
	
	private ArrayList <String> transcription;
	private BufferedWriter output;
	private int BlackScore;
	private int WhiteScore;

	public Transcription() {
		transcription = new ArrayList <String>();
		BlackScore =0;
		WhiteScore=0;
	}
	
	/*Adds a move to the list of done moves*/
	public void addMove(int col, int row, int turn) {
		String move = ""+ turn + " : " + transcriptCol(col)+(row+1)+"\n";
		transcription.add(move);
	}
	
	/*getters*/
	public int getBlackScore() {return this.BlackScore;}
	public int getWhiteScore() {return this.WhiteScore;}
	
	/*setters*/
	public void setBlackScore(int score) {this.BlackScore=score;}
	public void setWhiteScore(int score) {this.WhiteScore=score;}

	
	/*Stores the moves made in the game in a .txt file*/
	public void saveGame() {
		try {
				output = new BufferedWriter(new FileWriter("./games/Game"+System.currentTimeMillis() + ".txt"));
				for(String move: transcription) {
					output.write(move);
				}
				output.write("End of game. Score :\n"
						+ "Black = " + getBlackScore() +"\n" 
						+ "White = " + getWhiteScore());
				output.close();
	    		/*Example: 0.-d2
	    		 *		   0.-c2
	    		 *			...
	    		 *			End of game. Score :
	    		 *			Player 1= 50
	    		 *			Player 2= 14
	    		 *
	    		 *(turn/2)+ " : " + transcriptCol(col)+(row+1)+"\n"
	    		 */
		}catch(IOException e) {
			System.err.println("Error copying the game to a text file");
			e.printStackTrace();
		}	
	}
    
    /*Auxiliar method for the tanscription of the moves of the game to a file.
     * At the transcription, the columns of the board don't have number notation, they
     * are named with letters from a to h*/
    public char transcriptCol(int col) {
    	switch(col) {
    	//default means that col =0
    	default:
    		return 'a';
    	case 1:
    		return 'b';
    	case 2:
    		return 'c';
    	case 3:
    		return 'd';
    	case 4:
    		return 'e';
    	case 5:
    		return 'f';
    	case 6:
    		return 'g';
    	case 7:
    		return 'h';
    	
    	}
    }
	
	
}