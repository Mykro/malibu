package com.mykro.malibu.common.model;

public class RailroadProperty extends MarketProperty {

	private int rentCost;
	
	public RailroadProperty(String name, PropertyGroup group, int purchaseCost, int rentCost) {
		super(name, group, purchaseCost);
		this.rentCost = rentCost;
	}

	public int getCalculatedRent() {
		if ( propertyGroup != null ) {
			return rentCost * propertyGroup.getOwnerHoldings(this.getOwner());
		} else {
			return rentCost;
		}
	}

	
}
