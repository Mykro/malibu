package com.mykro.malibu.server.controller.engine;

import com.mykro.malibu.common.model.Player;
import com.mykro.malibu.common.model.World;
import com.mykro.malibu.common.model.World.WorldState;

/**
 * A Transition that ends the turn of the Player 
 */
public class TransitionPlayerEndTurn extends Transition {

	private boolean custom = false;
	private Player customplayer;
	
	/**
	 * The default constructor will cause the world's current player
	 * to end their turn.
	 */
	public TransitionPlayerEndTurn() {
	}
	
	/**
	 * This constructor will cause the specified player
	 * to end their turn.
	 */
	public TransitionPlayerEndTurn( Player player ) {
		this.custom = true;
		this.customplayer = player;
	}
	
	@Override
	public World execute(World world) throws TransitionException {
		Player player;
		if ( custom ) {
			player = customplayer;
		} else {
			player = world.getCurrentPlayer();
		}
		
		//----- VALIDATIONS -----
		
		if (player == null) {
			throw new TransitionException(this, world, "Player not specified.");
		}
		if (!world.getPlayers().contains(player)) {
			throw new TransitionException(this, world, "Player is not in this world.");
		}
		if (world.getCurrentPlayer() != player) {
			throw new TransitionException(this, world, "It is not currently the player's turn.");
		}
		
		//----- EXECUTION -----
		
		describe( player.getName() + " ends their turn." );

		world.resetPlayerTurn();
		world.setCurrentPlayer( world.getPlayers().getNext(world.getCurrentPlayer()) );
		world.setState(WorldState.PLAYER_STEADY);
		
		describe("It is now " + world.getCurrentPlayer().getName() + "'s turn." );
		
		return world;
	}



}
