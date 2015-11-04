package com.mykro.malibu.server.controller.agent.console;

import java.util.ArrayList;

import com.mykro.malibu.common.model.BaseProperty;
import com.mykro.malibu.common.model.MarketProperty;
import com.mykro.malibu.common.model.Player;
import com.mykro.malibu.common.model.StreetProperty;
import com.mykro.malibu.common.model.World;

/**
 * Describe the World in text.
 * 
 * Useful for text-based interfaces and for logging.
 */
public class WorldDescriber {
	
	public WorldDescriber() {
	}
	
	public ArrayList<String> describeEverything(World world) {
		ArrayList<String> s = new ArrayList<String>();

		// describe the current player
		s.add( describePlayer(world.getCurrentPlayer()) );
		
		// describe the current player's properties
		if ( world.getCurrentPlayer().getProperties().size() > 0 ) {
			s.add( describePlayerProperties(world.getCurrentPlayer()) );
		}
		
		// describe the current property.
		BaseProperty p = world.getCurrentPlayer().getCurrentProperty();
		if ( p instanceof MarketProperty ) {
			MarketProperty mp = (MarketProperty)p;
			if ( mp.getOwner() != null ) {
				String a = mp.getName() + " is owned by " + mp.getOwner().getName(); 
				if ( mp.isMortgaged() ) {
					a += " and is mortgaged.";
				} else {
					if ( mp.getGroup().isFullyOwned() ) {
						a += " with full ownership.";
						if ( mp instanceof StreetProperty ) {
							StreetProperty sp = (StreetProperty)mp;
							a += " and " + sp.getHouseCount() + " houses.";
						}
					}
				}
				s.add( a );
			} else {
				s.add( mp.getName() + " is unowned and costs $" + mp.getPurchaseCost() + ".");
			}
			
		}
		
		// describe the other players
		for ( Player other : world.getPlayers() ) {
			if ( !other.equals(world.getCurrentPlayer())) {
				s.add( describePlayer(other) );
			}
		}
		
		return s;
	}

	public String describeUpdate(World world) {
		// describe the current player
		String s = describePlayer(world.getCurrentPlayer());
		
		return s;
	}
	
	public String describePlayer(Player p) {
		BaseProperty prop = p.getCurrentProperty();
		return p.getName() +
				" (" + p.getToken().getName() + ")" +
				" is on " + ( prop != null ? prop.getNameForPresentPlayer(p) : "no property" ) + 
				" with $" + p.getCash() + 
				" and " + ( p.getProperties().size() > 0 ? p.getProperties().size() : "zero" ) + " properties.";
	}
	public String describePlayerProperties(Player player) {
		if ( player.getProperties().size() == 0 ) {
			return "";
		}
		String s = player.getName() +
				" owns ";
		for ( int i=0; i<player.getProperties().size(); i++ ) {
			BaseProperty p = player.getProperties().get(i);
			s += p.getName();
			if ( i < (player.getProperties().size()-2) ) {
				s+= ", ";
			} else if ( i == (player.getProperties().size()-2) ) {
				s+= " and ";
			}
		}
		s += ".";
		return s;
	}
}
