package com.mykro.malibu.common.model;

/**
 * A property that represents GO.
 */
public class GoProperty extends BaseProperty {

	private int passReward = 200;
	
	public GoProperty(String name) {
		super(name);
	}

	@Override
	public void playerPasses(Player player) {
		player.adjustCash(passReward);
	}

	@Override
	public void playerArrives(Player player) {
		// nothing
	}
	
}
