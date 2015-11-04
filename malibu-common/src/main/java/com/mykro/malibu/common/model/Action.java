package com.mykro.malibu.common.model;

/**
 * The base Action class.  
 * 
 * All Actions are assumed to originate from a Player.
 *
 * Actions may contain other data payloads (eg. property, amount) that may be filled in over time.
 */
public abstract class Action implements Actionable {

	private Player player;

	public abstract String getDescription();
	
	public Action(Player p) {
		this.player = p;
	}
	
	public Player getPlayer() {
		return player;
	}
}
