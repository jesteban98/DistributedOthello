package distrOthello;

/*If created by the client: 
 * */
public class MsgOver extends Protocol{

	/**
	 * 
	 */
	/*When the client wants to finish the connection will send a MsgOver object.
	 * The server will notify to the other player that his opponent disconnected*/
	private static final long serialVersionUID = 1L;

	/*Client to server:*/
	public MsgOver(int playerColor) {
		arg1 = new Integer(playerColor);
		
	}
	/*Server to client: We send the color of the winner.
	 * The answer string will be empty unless we sent an illegal move*/
	public MsgOver(int winner, String answer) {
		arg1 = new Integer(winner);
		resposta=answer;
		
	}	
	public int getPlayerColor() {return ((Integer)arg1).intValue();}
	public String getAnswer() {return resposta;}
}
