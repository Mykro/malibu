package com.mykro.malibu.common.model;

/**
 * An Action that mortgages a property owned by a Player
 */
public class ActionMortgageProperty extends Action implements Actionable {

	private MarketProperty property;
	
	public ActionMortgageProperty(Player p) {
		super(p);
	}

	public void setProperty(MarketProperty p) {
		property = p;
	}
	
	public MarketProperty getProperty() {
		return property;
	}

	@Override
	public String getDescription() {
		if ( property != null ) {
			return "Mortgage Property '" + property.getName() + "' for $" + property.calculateMortgageWorth();
		} else {
			return "Mortgage a property.";
		}
	}
	
}
