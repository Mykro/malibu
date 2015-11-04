package com.mykro.malibu.common.model;

/**
 * 
 */
public abstract class BaseProperty extends BaseObject {
	
	public BaseProperty(String name) {
		super(name);
	}
	
	/**
	 * Triggered when a player's token passes through the
	 * property as part of a normal movement.
	 * @param player
	 */
	public abstract void playerPasses(Player player);

	/**
	 * Triggered when a player's token lands on the property
	 * at the end of a normal movement.  
	 * @param player
	 */
	public abstract void playerArrives(Player player);

	public String getNameForPresentPlayer(Player p) {
		return getName();
	}

}
