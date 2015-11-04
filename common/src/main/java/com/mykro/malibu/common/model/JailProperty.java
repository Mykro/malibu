package com.mykro.malibu.common.model;

import java.util.Vector;

/**
 * A property that represents a Jail, two squares in one
 */
public class JailProperty extends BaseProperty {

	private PropertyGroup group; 
	private String jailName;
	private Vector<Player> jailedPlayers = new Vector<Player>();
	private int jailFee;
	private int doubleAttempts;
	
	public JailProperty(String outsideName, String jailName, PropertyGroup group, int jailFee, int doubleAttempts ) {
		super(outsideName);
		this.jailName = jailName;
		this.group = group;
		this.jailFee = jailFee;
		this.doubleAttempts = doubleAttempts;
		group.addProperty(this);
	}

	public String getJailName() {
		return jailName;
	}
	public int getJailFee() {
		return jailFee;
	}
	public int getDoubleAttempts() {
		return doubleAttempts;
	}
	
	public String getNameForPresentPlayer(Player p) {
		if (jailedPlayers.contains(p)) {
			return jailName;
		} else {
			return getName();
		}
	}

	public Vector<Player> getJailedPlayers() {
		return jailedPlayers;
	}
	public void jailPlayer(Player p) {
		p.setCurrentProperty( this );
		jailedPlayers.add(p);
	}
	public void freePlayer(Player p) {
		jailedPlayers.remove(p);
	}
	
	@Override
	public void playerPasses(Player player) {
	}

	@Override
	public void playerArrives(Player player) {
	}

	/**
	 * Player has been sent to Jail.  The player is locked up.
	 * @param player
	 */
	public void lockupPlayer(Player player) {
	}

}
