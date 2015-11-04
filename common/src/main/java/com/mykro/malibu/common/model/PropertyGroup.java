package com.mykro.malibu.common.model;

import java.util.Vector;

/**
 * A logical group of related properties
 */
public class PropertyGroup extends BaseObject {
	Vector<BaseProperty> properties = new Vector<BaseProperty>();
	
	public PropertyGroup( String name ) {
		super(name);
	}

	public void addProperty( BaseProperty property ) {
		if ( !properties.contains(property)) {
			properties.add(property);
		}
	}
	
	public boolean isFullyOwned() {
		if ( properties.size() == 0 ) {
			return false;
		}
		Player firstOwner = null;
		for ( BaseProperty property : properties ) {
			if (! (property instanceof MarketProperty) ) {
				return false;
			}
			if ( ((MarketProperty)property).getOwner() == null ) {
				return false;
			}
			if ( firstOwner == null ) {
				firstOwner = ((MarketProperty)property).getOwner();
			} else {
				if ( firstOwner != ((MarketProperty)property).getOwner()) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Get the number of properties owned by this player
	 * @return
	 */
	public int getOwnerHoldings(Player p) {
		int owned = 0;
		for ( BaseProperty property : properties ) {
			if ( ( property instanceof MarketProperty ) && 
					( ((MarketProperty)property).getOwner() == p ) ) {
				owned++;
			}
		}
		return owned;
	}

}
