package com.mykro.malibu.server.controller.engine;

import com.mykro.malibu.common.model.Player;
import com.mykro.malibu.common.model.World;

/**
 * A Transition that moves the Player 
 * and updates observers
 */
public class TransitionPlayerAdvance extends Transition {

	private boolean custom = false;
	private Player customplayer = null;
	int customplaces;

	/**
	 * The default constructor will advance the world's current player
	 * by the number of spaces currently shown on the dice.
	 */
	public TransitionPlayerAdvance() {
	}
	
	/**
	 * This constructor will advance the specified player
	 * by the number of spaces specified.
	 */
	public TransitionPlayerAdvance( Player player, int places ) {
		this.custom = true;
		this.customplayer = player;
		this.customplaces = places;
	}
	
	@Override
	public World execute(World world) throws TransitionException {

		Player player;
		int places;
		if ( custom ) {
			player = customplayer;
			places = customplaces;
		} else {
			player = world.getCurrentPlayer();
			places = world.getDice().getValue();
		}
		
		//----- VALIDATIONS -----
		
		if (player == null) {
			throw new TransitionException(this, world, "Player not specified.");
		}
		if (places == 0) {
			throw new TransitionException(this, world, "Places not specified.");
		}
		if (!world.getPlayers().contains(player)) {
			throw new TransitionException(this, world, "Player is not in this world.");
		}
		if (player.isInJail()) {
			throw new TransitionException(this, world, "Player cannot advance from jail.");
		}

		//----- EXECUTION -----
		
		for( int i=0; i < places ;i++) {
			player.moveForwardOnePosition();

			player.getCurrentProperty().playerPasses(player);
			
			if ( i < places-1) {
				describe(player.getName() + " passes " + 
						player.getCurrentProperty().getName());
			} else {
				describe(player.getName() + " lands on " + 
						player.getCurrentProperty().getName());
			}
		}
		
		return world;
	}

}
