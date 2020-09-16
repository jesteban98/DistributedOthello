package distrOthello;

import java.awt.Point;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class OthelloServer{
	ServerSocket server;
	
	GameState game;
	ArrayList<Player>players;
	Transcription writtengame;
	
	public OthelloServer(){
		try {
			server = new ServerSocket(5000,2);
			}
		catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		writtengame = new Transcription();
		players = new ArrayList<Player>();
	}
	
	public void execute() {
		while(players.size()<2) {
			try {
				players.add(new Player( server.accept(), this, players.size()+1 ));
				players.get(players.size() - 1).start();
				System.out.println("New player connected");
            } 
            catch ( IOException e) {
                e.printStackTrace();
                System.exit( 1 );
            }
		}    	
		game = new GameState(this);
		MsgTurn turno = new MsgTurn(1,
				game.getBoard(),
				game.getAllLegalMovesBool(players.get(0)),
				2,
				2,
				0);
		try {
		turno.envia(players.get(0).output);
		turno.envia(players.get(1).output);
		
		} catch (Exception e) {
		e.printStackTrace();
		}
	}
	
	public boolean gameOver() {
		return game.gameFinished();
	}
	
	public void removePlayer(Player p) {
		players.remove(p);
	}
	
	/*Updates the state of the game and sends the new information to both players.*/
	public void playTurn(Point move, int playerColor) {
		game.runGame(move, playerColor);
		MsgTurn turno = new MsgTurn(game.getCurrentPlayer().getNumber(),
				game.getBoard(),
				game.getAllLegalMovesBool(game.getCurrentPlayer()),
				game.getPlayerScore(players.get(0)),
				game.getPlayerScore(players.get(1)),
				game.getTurn());
		try {
		turno.envia(players.get(0).output);
		turno.envia(players.get(1).output);
		
		} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		if(game.gameFinished()) {
			MsgOver end = new MsgOver(game.getWinner(game),"Winner is "+(game.getWinner(game)==1?"black":"white")+" player");
        	try {
				end.envia(players.get(0).output);
				end.envia(players.get(1).output);
				//System.exit(0);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	
	public void setGameState(GameState state) {this.game=state;}
	public Transcription getTranscription() {return this.writtengame;}
	
    public static void main( String args[] ) 
    {
        OthelloServer othello = new OthelloServer();
        System.out.println("Server initiated");
        othello.execute();
        System.out.println("Players connected");

    }
}