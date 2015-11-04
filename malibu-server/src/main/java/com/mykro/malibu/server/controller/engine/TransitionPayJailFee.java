package com.mykro.malibu.server.controller.engine;

import com.mykro.malibu.common.model.BaseProperty;
import com.mykro.malibu.common.model.JailProperty;
import com.mykro.malibu.common.model.Player;
import com.mykro.malibu.common.model.World;
import com.mykro.malibu.common.model.World.WorldState;

/**
 * A Transition that causes a player to pay to exit a jail
 */
public class TransitionPayJailFee extends Transition {

	private boolean custom = false;
	private Player customplayer;
	private JailProperty customproperty;
	
	/**
	 * The default constructor will cause the world's current player
	 * to pay to exit the jail they are currently in.
	 */
	public TransitionPayJailFee() {
	}
	
	/**
	 * This constructor will cause the specified player to pay to 
	 * exit the specified property.
	 */
	public TransitionPayJailFee( Player player, JailProperty property ) {
		this.custom = true;
		this.customplayer = player;
		this.customproperty = property;
	}
	
	@Override
	public World execute(World world) throws TransitionException {
		
		Player player;
		BaseProperty property = null;
		
		//----- PLAYER VALIDATIONS -----
		if ( custom ) {
			player = customplayer;
		} else {
			player = world.getCurrentPlayer();
		}

		if (player == null) {
			throw new TransitionException(this, world, "Player not specified.");
		}
		if (!world.getPlayers().contains(player)) {
			throw new TransitionException(this, world, "Player is not in this world.");
		}
		
		//----- PROPERTY VALIDATIONS -----
		if ( custom ) {
			property = customproperty;
		} else {
			property = player.getCurrentProperty();
		}
		if (property == null) {
			throw new TransitionException(this, world, "Property not specified.");
		}
		JailProperty jailproperty;
		if ( property instanceof JailProperty ) {
			jailproperty = (JailProperty)property;
		} else {
			throw new TransitionException(this, world, "Not a jail property.");
		}
		
		//TODO: validate that property is in the world
		if (!jailproperty.getJailedPlayers().contains(player)) {
			throw new TransitionException(this, world, "Player is not jailed in this property.");
		}
		int cost = jailproperty.getJailFee();
		if ( !player.hasCash(cost)) {
			throw new TransitionException(this, world, "Player does not have enough money.");
		}		
		
		//----- EXECUTION -----
		
		try {
			player.adjustCash(-cost);
			jailproperty.freePlayer(player);
		} finally {
			world.setState(WorldState.PLAYER_STEADY);
		}
				
		describe( player.getName() + " pays $" + cost + " to get out of " + jailproperty.getJailName() + "." );

 		return world;
	}

}
