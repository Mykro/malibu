package com.mykro.malibu.server.controller.engine;

import com.mykro.malibu.common.model.Player;
import com.mykro.malibu.common.model.World;

/**
 * A Transition that moves the Player to jail
 */
public class TransitionPlayerJail extends Transition {

	private boolean custom = false;
	private Player customplayer;
	
	/**
	 * The default constructor will send the world's current player to jail.
	 */
	public TransitionPlayerJail() {
	}
	/**
	 * This constructor will send the specified player to jail.
	 */
	public TransitionPlayerJail( Player player ) {
		this.custom = true;
		this.customplayer = player;
	}
	
	@Override
	public World execute(World world) throws TransitionException {

		Player player;
		
		//----- PLAYER VALIDATIONS -----
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
		
		//----- EXECUTION -----
		
		world.getJailProperty().jailPlayer(player);
		
		describe(world.getCurrentPlayer().getName() + " is sent to " + 
				world.getJailProperty().getJailName());

		return world;
	}

}
