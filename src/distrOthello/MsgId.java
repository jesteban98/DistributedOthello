package distrOthello;

/*Identification message that the client will send to the server when trying to connect.
 * We will send */
public class MsgId extends Protocol{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*Client to server*/
	public MsgId() {
		pedido=new String("Request");
	}
	/*Server to client*/
	public MsgId(int color,String answer) {
		arg1 = new Integer(color);
		resposta=answer;
	}
	
	/*Getters needed to obtain the information from the MsgId object*/
	public int getPlayerColor() {return ((Integer)arg1).intValue();}
	public String getAnswer() {return resposta;}
}