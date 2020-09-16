package distrOthello;

/*Sent from Server to client, contains all the information the client needs in order to refresh
 *  the GUI with the information of the new turn*/
public class MsgTurn extends Protocol{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*Only from server to client*/
	public MsgTurn(int playercolor,int[][] board, boolean[][] moves,int bs, int ws,int turn) {
		arg1 = playercolor;
		arg2 = board;
		arg3 = moves;
		arg4 = bs;
		arg5 = ws;
		arg6 = turn;
	}
	
	/*Getters, the client will use them to extract the information from the MsgTurn object*/
	public int getPlayerColor() {return (Integer) arg1;}
	public int[][] getBoard(){return (int[][]) arg2;}
	public boolean[][] getLegalMoves(){return (boolean[][]) arg3;}
	public int getBlackScore() {return (Integer)arg4;}
	public int getWhiteScore(){return (Integer)arg5;}
	public int getTurn(){return (Integer)arg6;}

}
