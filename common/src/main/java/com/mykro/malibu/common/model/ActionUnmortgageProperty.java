package com.mykro.malibu.common.model;

/**
 * An Action that unmortgages a property owned by a Player
 */
public class ActionUnmortgageProperty extends Action implements Actionable {

	private MarketProperty property;
	
	public ActionUnmortgageProperty(Player p) {
		super(p);
	}

	public void setProperty(MarketProperty p) {
		this.property = p;
	}

	public MarketProperty getProperty() {
		return property;
	}

	@Override
	public String getDescription() {
		if ( property != null ) {
			return "Unmortgage Property '" + property.getName() + "' for $" + property.calculateUnmortgageCost();
		} else {
			return "Unmortgage a Property.";
		}
	}
	
}
