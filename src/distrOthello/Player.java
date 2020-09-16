package distrOthello;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Player extends Thread 
{
    OthelloServer control;
    Socket connection;
    ObjectInputStream input;   
    ObjectOutputStream output;
    
    int playerColor;//1=Black,2=white
    boolean done=false; //When the game is over it will be set to true
    
    public Player(Socket s, OthelloServer o, int playerColor) {
    	this.playerColor=playerColor;
    	control=o;
    	connection = s;
    	try {
			input = new ObjectInputStream(connection.getInputStream());
	    	output = new ObjectOutputStream(connection.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Couldn't create the streams of communication");
			System.exit(1);
		}
    	
    }
	
    public void run() {
    	try 
        {
            while( !done ) 
            {
                processaMsg(Protocol.recebe(input));
            }
			connection.close();
        }
        catch( Exception e ) 
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    /*Depending on  the type of instance we will use different logic*/
    public void processaMsg(Protocol msg)  throws Exception{
        if(msg instanceof MsgId)
            processaMsgId((MsgId)msg);
			else if(msg instanceof MsgTurn)
				processaMsgTurn((MsgTurn)msg);
				else if(msg instanceof MsgMove)
					processaMsgMove((MsgMove)msg);         
						else if(msg instanceof MsgOver)
							processaMsgOver((MsgOver)msg);         
    }
    
    public void processaMsgId(MsgId msg) throws Exception{
    	MsgId IdAnswer = new MsgId(playerColor, (playerColor == 1 ? "Server : Black" : "Server : White") + " player connected");
    	IdAnswer.envia(output);
    }
    
    public void processaMsgTurn(MsgTurn msg)  throws Exception{
    	//We don't have to process any data, we will only send it to the client
    }
    
    /*We play the move received. */
    public void processaMsgMove(MsgMove msg)  throws Exception{
    	System.out.println("Move of " + (msg.getPlayerColor()==1?"black":"white") + " player received");
    	control.playTurn(msg.getMove(), msg.getPlayerColor());
    }
  
    public void processaMsgOver(MsgOver msg)  throws Exception{
    	System.out.println("Winner is "+(msg.getPlayerColor()==1?"black":"white")+" player");
    	control.removePlayer(this);
    	done=true;
    }

    public int getNumber() {return playerColor;}
}
