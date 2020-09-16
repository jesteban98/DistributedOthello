package distrOthello;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;



public class OthelloClient extends JFrame implements Runnable{

	private static final long serialVersionUID = 1L;
	public ScorePanel scorepanel;
	public GamePanel gamepanel;
	public JFrame frame;
	public JMenuItem[] mitems;
	public JMenuBar mbar;
	
    Socket connection;
    ObjectInputStream input;
    ObjectOutputStream output;
    Thread inputThread;
    
    boolean done=false;    
    int me;
	
	/*Constructor*/
	public OthelloClient() {      
		createGUI();
		this.scorepanel = new ScorePanel();
		this.gamepanel = new GamePanel(this);
    }
	
	/*Get methods*/
	public ScorePanel getScorePanel() {return this.scorepanel;}
	public GamePanel getGamePanel() {return this.gamepanel;}
	public JFrame getFrame() {return this.frame;}
	public JMenuItem[] getMenuItem() {return this.mitems;}
	public JMenuBar getmenuBar() {return this.mbar;}



	
	/*Set methods*/
	public void setScorePanel(ScorePanel score) {this.scorepanel=score;}
	public void setGamePanel(GamePanel gp) {this.gamepanel=gp;}
	public void setFrame(JFrame frame) {this.frame=frame;}
	public void setMenuItems(JMenuItem[] mitems) {this.mitems=mitems;}
	public void setMenuBar(JMenuBar mbar) {this.mbar=mbar;}

	
	public void loadGameGUI() {
		frame.add(scorepanel,BorderLayout.EAST);
        frame.add(gamepanel,BorderLayout.CENTER);
        frame.pack();
    	//scorepanel.updateScore(2,2,0);
    	//gamepanel.updateGamePanel(msg.getBoard(),msg.getLegalMoves());
	}
	
	/*Creates the GUI*/
	public void createGUI() {
        //Create and set up the main frame.
        setFrame(new JFrame("Othello"));
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);
        frame.setResizable(false);
        
        //Define new buttons for the JMenuBar
        setMenuItems(new JMenuItem[3]);
        mitems[0]= new JMenuItem("Play online");
        mitems[1]= new JMenuItem("How to play");
        mitems[2]= new JMenuItem("Leave the game");
        
        //Add the buttons to the JMenuBar
        setMenuBar(new JMenuBar());
        for(int i=0;i<3;i++) {
        	mbar.add(mitems[i]);
        	}
        
      //Display the main window.
        frame.setJMenuBar(mbar);
        frame.setVisible(true);

        //Set the listeners for the buttons of the menu bar
        
        //Play vs AI
        mitems[0].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                start();
                loadGameGUI();
            }});
        //How to play
        mitems[1].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                Tutorial tutorial = new Tutorial();
                tutorial.setVisible(true);
            }});
        //Quit
        mitems[2].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	MsgOver end = new MsgOver(me);
            	try {
					end.envia(output);
					done=true;
					System.exit(0);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
            }});
        
        
	}
	
	
    public static void main(String[] args) {
    	SwingUtilities.invokeLater(new Runnable() {
    		public void run() {
    			new OthelloClient();
    		}
    	});
    }

	public void run(){
		try {
            do{
                processaMsg(Protocol.recebe(input));
            } while( !done );
			connection.close();
        }
        catch( Exception e ) 
        {
            e.printStackTrace();
        }
		System.exit(0);		
	}
	
    public void start(){
        try 
        {
            connection	= new Socket(InetAddress.getByName("localhost"), 5000 );
            output		= new ObjectOutputStream(connection.getOutputStream());
            input		= new ObjectInputStream(connection.getInputStream());
            
			inputThread = new Thread( this );
			inputThread.start();

			MsgId msg = new MsgId();
			msg.envia(output);
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
    
    /*We get a color assigned*/
    public void processaMsgId(MsgId msg) throws Exception{
    	me=msg.getPlayerColor();
    	System.out.println(msg.getAnswer());
    }
    
    /*We update the GUI*/
    public void processaMsgTurn(MsgTurn msg)  throws Exception{
    	System.out.println("bs: " + msg.getBlackScore() + " ws: " +msg.getWhiteScore() + " turno: " + msg.getTurn());
    	scorepanel.updateScore(msg.getBlackScore(),msg.getWhiteScore(),msg.getTurn());
    	gamepanel.updateGamePanelPieces(msg.getBoard());
    	/*If it is our turn we can see the possible mooves we have*/
    	if(msg.getPlayerColor()==me) {
    		gamepanel.displayLegalMoves(msg.getLegalMoves());
    	}
    }
    
    public void processaMsgMove(MsgMove msg)  throws Exception{
      //We don't have to process instances of MsgMove, we only send them to the server
    	
    }
  
    public void processaMsgOver(MsgOver msg)  throws Exception{
    	scorepanel.labels[4].setText(msg.getAnswer());
    	//done=true;
    }

}
