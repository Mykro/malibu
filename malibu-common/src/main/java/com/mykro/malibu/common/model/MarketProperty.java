package com.mykro.malibu.common.model;

/**
 * A Market Property is part of a PropertyGroup 
 * and can be purchased, rented and mortgaged.
 */
public class MarketProperty extends BaseProperty {

	private Player owner;
	PropertyGroup propertyGroup;
	private int purchaseCost;
	private boolean mortgaged = false;
	
	public MarketProperty(String name, PropertyGroup group, int purchaseCost) {
		super(name);
		this.purchaseCost = purchaseCost;
		this.propertyGroup = group;
		propertyGroup.addProperty(this);
	}
	
	@Override
	public void playerArrives(Player entity) {
		if ( owner == null ) {
			purchase(entity);
		} else if ( owner.equals(entity)) { 
			// already own property
		} else {
			rent(owner, entity);
		}
	}

	@Override
	public void playerPasses(Player player) {
		// TODO Auto-generated method stub
		
	}

	
	public Player getOwner() {
		return owner;
	}
	public void setOwner(Player player) {
		owner = player;
	}
	public PropertyGroup getGroup() {
		return propertyGroup;
	}
	public int getPurchaseCost() {
		return purchaseCost;
	}
	
	public boolean isPurchasable() {
		return ( getOwner() == null );
	}
	
	protected void purchase(Player landingEntity) {
		
	}

	
	protected void rent(Player landlordEntity, Player tenantEntity) {
		
	}

	public int calculateMortgageWorth() {
		return purchaseCost/2;		
	}
	public int calculateUnmortgageCost() {
		return (int) Math.round( calculateMortgageWorth() * 1.1 );
	}

	public boolean isMortgaged() {
		return mortgaged;
	}

	public void setMortgaged() {
		mortgaged = true;
	}
	public void setUnmortgaged() {
		mortgaged = false;
	}

	protected void mortgage() {
		if ( mortgaged ) {
			
		}
		if ( owner == null ) {
			
		}
		owner.adjustCash(calculateMortgageWorth());
		mortgaged = true;
	}

	protected void ummortgage() {
		if ( !mortgaged ) {
			
		}
		if ( owner == null ) {
			
		}
		owner.adjustCash(-calculateUnmortgageCost());
		mortgaged = false;
	}


	
}
