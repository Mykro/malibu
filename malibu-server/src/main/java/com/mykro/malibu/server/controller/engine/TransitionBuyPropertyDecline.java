package com.mykro.malibu.server.controller.engine;

import com.mykro.malibu.common.model.MarketProperty;
import com.mykro.malibu.common.model.Player;
import com.mykro.malibu.common.model.World;
import com.mykro.malibu.common.model.World.WorldState;

/**
 * A Transition that causes a player to decline to purchase a property.
 */
public class TransitionBuyPropertyDecline extends Transition {

	private Player player;
	private MarketProperty property;
	
	/**
	 * This constructor will cause the specified player to decline to purchase
	 * the specified property.
	 */
	public TransitionBuyPropertyDecline( Player player, MarketProperty property ) {
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
		if ( property.getOwner() != null ) {
			if ( property.getOwner() == player ) {
				throw new TransitionException(this, world, "Player already owns this property.");
			} else {
				throw new TransitionException(this, world, "Another player already owns this property.");
			}
		}

		//----- EXECUTION -----
		int cost = property.getPurchaseCost();
		
		// TODO: record the declination in the model somewhere.
		
		describe( player.getName() + " declines to purchase " + property.getName() + " for $" + cost + "." );
		
		try {
			// nawt
		} finally {
			world.setState(WorldState.PLAYER_STEADY);
		}
				

		return world;
	}

}
