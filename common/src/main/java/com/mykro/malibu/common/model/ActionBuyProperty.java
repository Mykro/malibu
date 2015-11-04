package com.mykro.malibu.common.model;

/**
 * An Action that purchases the specific property
 */
public class ActionBuyProperty extends Action implements Actionable {

	private MarketProperty property;
	
	public ActionBuyProperty(Player p, MarketProperty pr) {
		super(p);
		this.property = pr;
	}

	public MarketProperty getProperty() {
		return property;
	}

	@Override
	public String getDescription() {
		return "Buy Property: " + property.getName();
	}
}
