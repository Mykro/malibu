package com.mykro.malibu.common.model;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * A snapshot of the entire game.
 * Captures a hierarchy of information that captures every
 * aspect of the game that can be saved, restored or 
 * transmitted to a client.
 */
public class World {
	public enum WorldState {
		PLAYER_STEADY,
		PLAYER_ROLLING_DICE,
		PLAYER_ADVANCING,
		PLAYER_LANDING,
		PLAYER_LANDED,
		PLAYER_RAISING_CASH,
		PLAYER_PAYING_RENT,
		PLAYER_DECIDING_JAIL_EXIT,
		PLAYER_PAYING_JAIL_EXIT,
		PLAYERS_TRADING,
		PLAYER_BUILDING,
		PLAYER_DRAWING_CARD,
		PLAYER_FOLLOWING_INSTRUCTION,
		PLAYER_ENDING_TURN,
		WORLD_GAME_OVER
	}

	public enum PlayerState {
		

	}
	
	private WorldState state;
	private DiceSet dice = new DiceSet(2);
	private LinkedList<Integer> diceFuture = new LinkedList<Integer>();
	private TokenSet tokens = new TokenSet();
	private HashMap<Integer,CardDeck> cardPiles = new HashMap<Integer,CardDeck>();
	private Board board = new Board();
	private PlayerSequence players = new PlayerSequence();
	private PlayerTurn currentTurn = new PlayerTurn();
	private Player currentPlayer;
	
	public World() {
		// TODO: get rid of
		CardDeck chanceCards = new CardDeck();
		cardPiles.put(1, chanceCards);
		CardDeck chestCards = new CardDeck();
		cardPiles.put(2, chestCards);
	}

	public PlayerSequence getPlayers() {
		return players;
	}
	public PropertySequence getProperties() {
		return board.getProperties();
	}
	public JailProperty getJailProperty() {
		return board.getJailProperty();
	}
	public PropertyGroup getJailGroup() {
		return board.getJailGroup();
	}
	public TokenSet getTokens() {
		return tokens;
	}
	public CardDeck addCardPile(int id, String title) {
		CardDeck cp = cardPiles.get(id);
		if ( cp == null ) {
			cp = new CardDeck();
			cardPiles.put(id, cp);
		}
		return cp;
	}
	
	public Board getBoard() {
		return board;
	}

	public CardDeck getCards(int deckID) {
		return cardPiles.get(deckID);
	}
	
	public DiceSet getDice() {
		return dice;
	}
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public synchronized Player addPlayer(String name) {
		Player p = new Player(this,name);
		// give them a token
		Token t = getFirstAvailableToken();  
		p.setToken(t);
		t.setOwner(p);
		// put them at the start
		p.setCurrentProperty(getBoard().getProperties().getPropertyByPosition(0));
		
		getPlayers().add(p);
		if ( currentPlayer == null ) {
			currentPlayer = p;
		}
		return p;
	}
	
	public synchronized Player removePlayer(Player player) {
		// free up the Token
		Token t = player.getToken();
		t.setOwner(null);
		player.setToken(null);
		// fix up the current player if needed (but should have ended turn already)
		if ( player == currentPlayer ) {
			currentPlayer = players.getNext(currentPlayer);
		}
		if ( player == currentPlayer ) {
			currentPlayer = null;  // this was the last player, there's no more
		}
		players.remove(player);
		return player;
	}

	public WorldState getState() {
		return state;
	}
	public void setState( WorldState s ) {
		state = s;
	}

	public void setCurrentPlayer(Player player) {
		currentPlayer = player;
	}

	public PlayerTurn getCurrentTurn() {
		return currentTurn;
	}
	public int getRollsThrown() {
		return currentTurn.getRollsThrown();
	}
	public void resetPlayerTurn() {
		currentTurn.reset();
	}
	public void incrementRollsThrown() {
		currentTurn.incrementRollsThrown();
	}
	public boolean isDiceDoubles() {
		return ( getDice().getValue(0) == getDice().getValue(1));
	}
	/**
	 * Add a pair of dice values to the predetermined queue
	 */
	public void addFutureDiceRoll( int i, int j ) {
		diceFuture.add(i);
		diceFuture.add(j);
	}
	/**
	 * Add one dice value to the predetermined queue
	 */
	public void addFutureDiceRoll( int i ) {
		diceFuture.add(i);
	}
	/**
	 * Pop one dice value from the predetermined queue
	 */
	public int getFutureDiceRoll() {
		return diceFuture.poll();
	}
	public boolean hasFutureDiceRoll() {
		return ( !diceFuture.isEmpty() );
	}

	
	public Token getFirstAvailableToken() {
		for ( Token t : tokens.getTokens() ) {
			boolean tokenUsed = false;
			for ( Player p : players ) {
				if ( t == p.getToken() ) {
					tokenUsed = true;
					break; // don't check any more players
				}
			}
			if ( !tokenUsed) {
				return t;
			}
		}
		return null;
	}

	
}
