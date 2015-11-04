package com.mykro.malibu.server.controller.engine;

import com.mykro.malibu.common.model.BaseProperty;
import com.mykro.malibu.common.model.CardDeck;
import com.mykro.malibu.common.model.CardPileProperty;
import com.mykro.malibu.common.model.GoProperty;
import com.mykro.malibu.common.model.JailProperty;
import com.mykro.malibu.common.model.MarketProperty;
import com.mykro.malibu.common.model.ParkingProperty;
import com.mykro.malibu.common.model.Player;
import com.mykro.malibu.common.model.RailroadProperty;
import com.mykro.malibu.common.model.SendToJailProperty;
import com.mykro.malibu.common.model.StreetProperty;
import com.mykro.malibu.common.model.TaxProperty;
import com.mykro.malibu.common.model.UtilityProperty;
import com.mykro.malibu.common.model.World;
import com.mykro.malibu.common.model.World.WorldState;

/**
 * A Transition that executes the player's arrival at a property.
 */
public class TransitionPlayerLanding extends Transition {

	private boolean custom = false;
	private Player customplayer;
	private BaseProperty customproperty;
	
	/**
	 * The default constructor will "land" the world's current player
	 * on their current property.
	 */
	public TransitionPlayerLanding() {
	}
	/**
	 * This constructor will "land" the specified player at the specified property.
	 */
	public TransitionPlayerLanding( Player player,BaseProperty property ) {
		this.custom = true;
		this.customplayer = player;
		this.customproperty = property;
	}

	@Override
	public World execute(World world) throws TransitionException {

		Player player;
		BaseProperty p = null;
		
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
			p = customproperty;
		} else {
			p = player.getCurrentProperty();
		}
		
		if (p == null) {
			throw new TransitionException(this, world, "Property not specified.");
		}

		//----- EXECUTION -----

		if ( p instanceof MarketProperty ) {
			MarketProperty mp = (MarketProperty)p;
			if ( mp.getOwner() != null ) {
				if ( mp.getOwner() != player ) {
					// player has to pay rent to another player
					int rent = 0;
					if ( mp instanceof StreetProperty ) {
						rent = ((StreetProperty)mp).getCalculatedRent();
					} else if ( mp instanceof RailroadProperty ) {
						rent = ((RailroadProperty)mp).getCalculatedRent();
					} else if ( mp instanceof UtilityProperty ) {
						if ( mp.getGroup() != null && mp.getGroup().isFullyOwned() ) {
							rent = world.getDice().getValue() * 10;
						} else {
							rent = world.getDice().getValue() * 4;
						}
					}
					if ( !player.hasCash(rent) ) {
						world.setState(WorldState.PLAYER_RAISING_CASH);
						// TODO: option 1: create new transition here and launch it???  this pauses the entire engine however.
						
						// TODO: better option?: set a "debt" flag (a Liability object in model?) on the player, which has
						// the effect of disabling other available actions including ending turn.
						// adds two new actions, (a) "pay debt" which the player can
						// invoke when they have raised the funds, and (b) declare bankruptcy.
						// this allows other parts of the engine to keep working.
					}

					// player has the funds, execute an automatic payment
					player.adjustCash(-rent);
					mp.getOwner().adjustCash(rent);
					
					describe(player.getName() + " pays $" + 
							rent + " to " + mp.getOwner().getName() + "");
					
					world.setState(WorldState.PLAYER_STEADY);
					return world;
				} else {
					// player's own property
					world.setState(WorldState.PLAYER_STEADY);
					return world;
				}
			} else {
				// purchasable
				world.setState(WorldState.PLAYER_STEADY);
				return world;
			}
		} else if ( p instanceof JailProperty ) {
			// just visiting
			world.setState(WorldState.PLAYER_STEADY);
			return world;
		} else if ( p instanceof SendToJailProperty ) {
			// go to jail and end turn
			TransitionPlayerJail tj = new TransitionPlayerJail(player);
			tj.execute(world);
			return world;
		} else if ( p instanceof CardPileProperty ) {
			CardDeck carddeck = ((CardPileProperty)p).getCards();
			if ( carddeck == null) {
				throw new TransitionException(this, world, "Card Deck not found.");
			}
			TransitionPlayerDrawCard tpdc = new TransitionPlayerDrawCard(player, carddeck);
			try {
				tpdc.execute(world);
			} finally {
				world.setState(WorldState.PLAYER_STEADY);
			}
			return world;
		} else if ( p instanceof ParkingProperty ) {
			// free parking
			world.setState(WorldState.PLAYER_STEADY);
			return world;
		} else if ( p instanceof TaxProperty ) {
			// pay tax
			player.adjustCash(-150);
			world.setState(WorldState.PLAYER_STEADY);
			return world;
		} else if ( p instanceof GoProperty ) {
			// landed on go. Already adjusted cash when passing through it.
			world.setState(WorldState.PLAYER_STEADY);
			return world;
		}
		return world;
	}


}
