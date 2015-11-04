package com.mykro.malibu.common.model;

/**
 * A Street Property is a Market Property 
 * (that can be purchased, rented and mortgaged)
 * and that can also have houses and hotels built upon it.
 */
public class StreetProperty extends MarketProperty {

	int houseCount = 0;  // 5 houses is a hotel
	int houseCost;
	int houseRent[] = new int[6];
	
	public StreetProperty(String name, PropertyGroup group, int purchaseCost, int houseCost, 
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

	public int getHouseCount() {
		return houseCount;
	}

	public int getCalculatedRent() {
		if ( houseCount == 0 ) {
			if ( propertyGroup != null && propertyGroup.isFullyOwned() ) {
				return houseRent[0] * 2;
			} else {
				return houseRent[0];
			}
		} else {
			return houseRent[houseCount];
		}
			
	}
}
