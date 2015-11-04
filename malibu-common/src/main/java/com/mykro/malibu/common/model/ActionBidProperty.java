package com.mykro.malibu.common.model;

/**
 * An Action that bids on the specific property
 */
public class ActionBidProperty extends Action implements Actionable {

	private MarketProperty property;
	private int amount;
	
	public ActionBidProperty(Player p, MarketProperty pr, int amount) {
		super(p);
		this.property = pr;
		this.amount = amount;
	}

	public MarketProperty getProperty() {
		return property;
	}

	public int getAmount() {
		return amount;
	}

	@Override
	public String getDescription() {
		return "Bid $" + amount + " on '" + property.getName() + "'.";
	}
}
