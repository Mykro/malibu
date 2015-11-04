package com.mykro.malibu.server.controller.engine;

import com.mykro.malibu.common.model.MarketProperty;
import com.mykro.malibu.common.model.Player;
import com.mykro.malibu.common.model.World;
import com.mykro.malibu.common.model.World.WorldState;

/**
 * A Transition that unmortgages a property.
 */
public class TransitionUnmortgageProperty extends Transition {

	private Player player;
	private MarketProperty property;
	
	public TransitionUnmortgageProperty( Player player, MarketProperty property ) {
		this.player = player;
		this.property = property;
	}
	
	@Override
	public World execute(World world) throws TransitionException {

		//----- VALIDATIONS -----
		
		if (player == null) {
			throw new TransitionException(this, world, "Player not specified.");
		}
		if (property == null) {
			throw new TransitionException(this, world, "Property not specified.");
		}
		if (!world.getPlayers().contains(player)) {
			throw new TransitionException(this, world, "Player is not in this world.");
		}
		//TODO: validate that property is in the world	
		if ( property.getOwner() == null ) {
			throw new TransitionException(this, world, "Player does not own this property.");
		}
		if ( property.getOwner() != player ) {
			throw new TransitionException(this, world, "Another player already owns this property.");
		}
		if ( !property.isMortgaged() ) {
			throw new TransitionException(this, world, "The property is not mortgaged.");
		}
		int cost = property.calculateUnmortgageCost();
		if ( !player.hasCash(cost) ) {
			throw new TransitionException(this, world, "Player does not have sufficient cash.");
		}
		//----- EXECUTION -----
		
		try {
			player.adjustCash(cost);
			property.setUnmortgaged();
		} finally {
			world.setState(WorldState.PLAYER_STEADY);
		}
				
		describe( player.getName() + " unmortgages " + property.getName() + " for $" + cost + "." );
		
		return world;
	}

}
