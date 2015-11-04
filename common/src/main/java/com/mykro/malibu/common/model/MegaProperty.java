package com.mykro.malibu.common.model;

/**
 * A property that can be anything:
 * - adjusts cash of the player passing through it (GO)
 * - adjusts cash of the player landing on it (GO, tax, free parking custom rule)
 * - (!) redirects the player landing on it (go to jail)
 * - invokes a card draw when landed on (chance, chest)
 * - a market property (buy, rent)
 * - build on (houses, hotels)
 * In theory a property could have multiple of these attributes, though those
 * marked (!) would not be very compatible with other attributes.
 * 
 * DEFUNCT - NOT YET NEEDED
 */
public class MegaProperty extends MarketProperty {

	int houseCost = 0;
	int houseRent[] = new int[5];
	
	public MegaProperty(String name, PropertyGroup group, int purchaseCost, int houseCost, 
			int emptyRent, int house1Rent, int house2Rent, 
			int house3Rent, int house4Rent, int hotelRent ) {
		super(name, group, purchaseCost);
		this.houseCost = houseCost;
		this.houseRent[0] = emptyRent;
		this.houseRent[1] = house1Rent;
		this.houseRent[2] = house2Rent;
		this.houseRent[3] = house3Rent;
		this.houseRent[4] = house4Rent;
		this.houseRent[5] = hotelRent;
	}

	
}
