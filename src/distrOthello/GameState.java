package distrOthello;

import java.awt.Point;
import java.util.ArrayList;


public class GameState{

	private OthelloServer mediator;
	private int[][] board = new int[8][8];
    private boolean[][] boolMoves = new boolean[8][8];
	private Player currentPlayer;
	private int bs;
	private int ws;
	private int turn = 0;
	
	public static final int BLACK = 1, WHITE = 2;
	
	/*Constructor used to clone a previous game state*/
	public GameState (GameState clone) {
		this.mediator = clone.mediator;
		this.board=clone.getBoard();
		this.currentPlayer=clone.getCurrentPlayer();
		this.turn = clone.getTurn();
		this.bs=clone.getPlayerScore(mediator.players.get(0));
		this.ws=clone.getPlayerScore(mediator.players.get(1));
	}
	/*Constructor for the initial game state*/
	public GameState(OthelloServer mediator) {
		this.mediator=mediator;
		this.board[3][3]=WHITE;
		this.board[4][4]=WHITE;
		this.board[3][4]=BLACK;
		this.board[4][3]=BLACK;
		this.boolMoves=getAllLegalMovesBool(mediator.players.get(0));
		this.bs=2;
		this.ws=2;
		this.turn=0;
		this.currentPlayer=mediator.players.get(0);
	}
	/*Changes the state of the game. If the received move is legal it will be saved in the transcription.
	 * If the game is over we will also store the results */
	public void runGame(Point move,int playerColor) {
		if(!gameFinished() && currentPlayer.getNumber()==playerColor) {//We check that it is the turn of the player that sent the move
			GameState newstate = playMove(move,this.currentPlayer);
			mediator.getTranscription().addMove(move.y,move.x,this.turn);
			mediator.setGameState(newstate);
			if(newstate.gameFinished()){
				mediator.getTranscription().setBlackScore(this.getPlayerScore(mediator.players.get(0)));
				mediator.getTranscription().setWhiteScore(this.getPlayerScore(mediator.players.get(1)));
				mediator.getTranscription().saveGame();	
			}
		}
	}
	
	/*Returns the turn the game is at.
	 * */
	public int getTurn() {return turn;}
	/*Increments the turn.*/
	public void incTurn() {turn++;}

	/*Returns the int[][] board global variable*/
	public int[][] getBoard(){return this.board;}
	
	/*Sets the new board*/
	public void setBoard(int [][] newBoard) {this.board= newBoard;}	
	
	public void setCurrentPlayer(Player player) {this.currentPlayer=player;}
	public Player getCurrentPlayer() {return this.currentPlayer;}
	
	/*Returns the player with the same number (identificator) as the parameter*/
	public Player getPlayer(int number) {
		for(Player pl : mediator.players) {
			if(number == pl.getNumber()) {return pl;}
		}
		throw new IllegalArgumentException("No player with number=" + number);
	}
	/*Returns the number of pieces the player pl has on the board.*/
	public int getPlayerScore(Player pl) {
		int total = 0;
		for(int i=0;i<=7;i++){
			for(int j=0;j<=7;j++) {
				if (board[i][j]==pl.getNumber())
					total++;
			}
		}
		return total;
	}

	
	/* Will check one point of the board in a chosen direction. If the player has a piece in the chosen square
	 * we check if there is a free square in the horizontal, vertical or diagonal direction.
	 * If there is and between both squares are only pieces from the other player, the move is legal.
	 * */
	private ArrayList<Point> getLegalMovesFrom(Player p, int initialrow, int initialcol, int incrow, int inccol) {
		ArrayList <Point> legalMoves = new ArrayList<Point>();
		int row = initialrow + incrow;
		int col = initialcol + inccol;
		int numflips =0;
		
		if(this.getBoard()[initialrow][initialcol]==0) {
			while (row >= 0 && row < 8 && col >= 0 && col < 8) {
				if(this.getBoard()[row][col]==p.getNumber()) {
					if(numflips>0) {
						legalMoves.add(new Point(initialrow,initialcol));
					}
					break;
				}			
				else
					if(this.getBoard()[row][col]!=0){
						numflips++;
					}
					else {
						break;
					}			
				row+=incrow;
				col+=inccol;
			}
		}
		return legalMoves;
	}
	
	
	public boolean hasLegalMoves(Player p) {
		ArrayList<Point> legalMoves = getAllLegalMoves(p);
		if(legalMoves.isEmpty()==true)
			return false;
		else
			return true;
	}
	
	/*
	 * Returns a bidimensional boolean array that will be used by the Cells to represent the
	 * legal moves on the board*/
	public boolean[][] getAllLegalMovesBool(Player p) {
		ArrayList<Point> legalMoves = getAllLegalMoves(p);
		for(Point legalMove : legalMoves) {
			boolMoves[legalMove.x][legalMove.y] = true;
		}
			return boolMoves;
	}
	
	/*
	 * Returns an ArrayList that will be used by the JButtons to get the
	 * legal moves on the board*/
	public ArrayList<Point> getAllLegalMoves(Player p) {
		ArrayList<Point> legalMoves = new ArrayList<Point>();
		for(int i=0;i<8;i++) {
			for (int j=0;j<8;j++) {
				legalMoves.addAll(getLegalMovesFrom(p,i,j,1,0));
				legalMoves.addAll(getLegalMovesFrom(p,i,j,-1,0));
				legalMoves.addAll(getLegalMovesFrom(p,i,j,0,1));
				legalMoves.addAll(getLegalMovesFrom(p,i,j,0,-1));
				legalMoves.addAll(getLegalMovesFrom(p,i,j,1,1));
				legalMoves.addAll(getLegalMovesFrom(p,i,j,-1,-1));
				legalMoves.addAll(getLegalMovesFrom(p,i,j,-1,1));
				legalMoves.addAll(getLegalMovesFrom(p,i,j,1,-1));
			}
		}
			return legalMoves;
	}
	
	/*It will put the number of the player p at the point po and will flip the pieces from the
	 * other player between them. After that, it will return the new state of the game.
	 * */
	public GameState playMove(Point po, Player p) {
		boolean[][] legalMoves = getAllLegalMovesBool(p);
		GameState newState = new GameState(this);
		if(legalMoves[po.x][po.y]==true) {	
			int[][] lines = getLines(po,p);
			/*Here we put the new piece and flip the pieces*/
			newState.getBoard()[po.x][po.y]=p.getNumber();
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					int lineLength = lines[i][j];
					int localRow = po.x;
					int localCol = po.y;
					while (lineLength > 0) {
						switch(i) {
						case 0:
							if(j==1) {localRow += 0;
										localCol += 1;}
							if(j==2) {localRow += 0;
										localCol -= 1;}
							break;
						case 1:
							if(j==0) {localRow += 1;
										localCol += 0;}
							if(j==1) {localRow -= 1;
										localCol += 0;}
							if(j==2) {localRow += 1;
										localCol += 1;}
							break;
						case 2:
							if(j==0) {localRow += 1;
										localCol -= 1;}
							if(j==1) {localRow -= 1;
										localCol += 1;}
							if(j==2) {localRow -= 1;
										localCol -= 1;}
							break;
						}

						newState.getBoard()[localRow][localCol]=p.getNumber();
						lineLength--;
					}
				}
			}
			newState.incTurn();
			if(this.currentPlayer.getNumber()==BLACK) {
				newState.setCurrentPlayer(mediator.players.get(1));
			}
			else {
				newState.setCurrentPlayer(mediator.players.get(0));
			}
			newState.bs=getPlayerScore(mediator.players.get(0));
			newState.ws=getPlayerScore(mediator.players.get(1));
			return new GameState(newState);
		}
		else {
			throw new IllegalArgumentException("No legal move at the chosen square");
		}
	}
	
	/*
	 * Returns the number of pieces of the other player in a certain direction, counting from a given point
	 * (initialrow,initialcol)
	 * */
	private int getLineLength(Player p, int initialrow, int initialcol, int incrow, int inccol) {
		int row = initialrow + incrow;
		int col = initialcol + inccol;
		int numflips =0;
		
		while (row >= 0 && row < 8 && col >= 0 && col < 8) {
			if(this.getBoard()[row][col]==p.getNumber()) {
				return numflips;
			}			
			else
				if(this.getBoard()[row][col]!=0){
					numflips++;
				}
				else {
						return 0;
					}
			
			row+=incrow;
			col+=inccol;
		}
		
		return 0;
	}
	
	/*
	 * Returns a bidimensional array with the length of the lines in each direction.
	 * If the length is 3, 3 pieces can be changed in that direction.
	 * */
	private int[][] getLines(Point po, Player p) {
		int[][] lines = new int[3][3];
		lines[0][0] = 0;
		lines[0][1] = getLineLength(p,po.x, po.y, 0, 1);
		lines[0][2] = getLineLength(p,po.x, po.y, 0, -1);
		lines[1][0] = getLineLength(p,po.x, po.y, 1, 0);
		lines[1][1] = getLineLength(p,po.x, po.y, -1, 0);
		lines[1][2] = getLineLength(p,po.x, po.y, 1, 1);
		lines[2][0] = getLineLength(p,po.x, po.y, 1, -1);
		lines[2][1] = getLineLength(p,po.x, po.y, -1, 1);
		lines[2][2] = getLineLength(p,po.x, po.y, -1, -1);
		return lines;
	}
	
	public int getWinner(GameState g) {
		int winner;
		winner = (g.getPlayerScore(g.getPlayer(BLACK))>g.getPlayerScore(g.getPlayer(WHITE)))?BLACK:WHITE;
		return winner;
	}
	
	/*Returns true if there are no more legal moves for any of the players, false otherwise*/
	public boolean gameFinished() {return !hasLegalMoves(getPlayer(BLACK))&&!hasLegalMoves(getPlayer(WHITE));}

	
}

