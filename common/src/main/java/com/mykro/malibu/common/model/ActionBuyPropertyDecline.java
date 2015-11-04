package com.mykro.malibu.common.model;

/**
 * An Action that declines to purchase the specific property
 */
public class ActionBuyPropertyDecline extends Action implements Actionable {

	private MarketProperty property;
	
	public ActionBuyPropertyDecline(Player p, MarketProperty pr) {
		super(p);
		this.property = pr;
	}

	public MarketProperty getProperty() {
		return property;
	}

	@Override
	public String getDescription() {
		return "Don't Buy Property: " + property.getName();
	}
}
