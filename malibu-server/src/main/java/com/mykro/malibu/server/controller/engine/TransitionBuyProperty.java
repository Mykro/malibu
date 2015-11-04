package com.mykro.malibu.server.controller.engine;

import com.mykro.malibu.common.model.BaseProperty;
import com.mykro.malibu.common.model.MarketProperty;
import com.mykro.malibu.common.model.Player;
import com.mykro.malibu.common.model.World;
import com.mykro.malibu.common.model.World.WorldState;

/**
 * A Transition that causes a player to purchase a property.
 */
public class TransitionBuyProperty extends Transition {

	private boolean custom = false;
	private Player customplayer;
	private BaseProperty customproperty;
	
	
	/**
	 * The default constructor will cause the world's current player
	 * to purchase the property they are currently on.
	 */
	public TransitionBuyProperty() {
	}
	
	/**
	 * This constructor will cause the specified player to purchase
	 * the specified property.
	 */
	public TransitionBuyProperty( Player player, BaseProperty property ) {
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
		
		MarketProperty purchasableproperty;
		if ( property instanceof MarketProperty ) {
			purchasableproperty = (MarketProperty)property;
		} else {
			throw new TransitionException(this, world, "Not a purchasable property.");
		}
		//TODO: validate that property is in the world
		if ( purchasableproperty.getOwner() != null ) {
			if ( purchasableproperty.getOwner() == player ) {
				throw new TransitionException(this, world, "Player already owns this property.");
			} else {
				throw new TransitionException(this, world, "Another player already owns this property.");
			}
		}
		int cost = purchasableproperty.getPurchaseCost();
		if ( !player.hasCash(cost)) {
			throw new TransitionException(this, world, "Player does not have enough money.");
		}

		//----- EXECUTION -----
		
		try {
			player.adjustCash(-cost);
			player.addProperty(purchasableproperty);
		} finally {
			world.setState(WorldState.PLAYER_STEADY);
		}
				
		describe( player.getName() + " purchases " + purchasableproperty.getName() + " for $" + cost + "." );

		return world;
	}

}
