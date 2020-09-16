package distrOthello;
import java.io.*;

public class Protocol implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Object arg1 = null;// int, player color
	Object arg2 = null;//int[][], board
	Object arg3 = null;//boolean[][], moves
	Object arg4 = null;//int, black score
	Object arg5 = null;//int, white score
	Object arg6 = null;//int, turn
	String pedido=null;//Client->Server request
	String resposta=null;//Server->Client answer
    
    public void envia(ObjectOutputStream out) throws Exception
    {
    	out.reset();
        out.writeObject(this);
        out.flush();
    }

    public static Protocol recebe(ObjectInputStream in) throws Exception
    {
        return (Protocol)in.readObject();
    }
}