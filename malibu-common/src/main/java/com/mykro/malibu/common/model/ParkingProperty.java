package com.mykro.malibu.common.model;

/**
 * A property that represents Free Parking.
 */
public class ParkingProperty extends BaseProperty {

	private int kitty = 0;
	
	public ParkingProperty(String name) {
		super(name);
	}

	@Override
	public void playerPasses(Player player) {
	}

	@Override
	public void playerArrives(Player player) {
		if ( kitty > 0 ) {
			player.adjustCash(kitty);
			kitty = 0;
		}
	}

	public void add(int amount) {
		kitty += amount;
	}
}
