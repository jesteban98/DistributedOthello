package distrOthello;

import java.awt.Point;

/*Sent by the client to the server, it sends the position of the board where we will 
 * place a piece*/
public class MsgMove extends Protocol{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*From client to server:*/
	public MsgMove(int playerColor, Point move) {
		arg1 = playerColor;
		arg2 = move;
	}
	
	public int getPlayerColor() {return ((Integer)arg1).intValue();}
	public Point getMove() {return (Point)arg2;}

}
