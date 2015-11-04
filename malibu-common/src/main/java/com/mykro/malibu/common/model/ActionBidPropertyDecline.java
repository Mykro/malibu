package com.mykro.malibu.common.model;

/**
 * An Action that declines to bid on the specific property
 * 
 * This allows all players to abort the timer early.
 */
public class ActionBidPropertyDecline extends Action implements Actionable {

	private MarketProperty property;
	
	public ActionBidPropertyDecline(Player p, MarketProperty pr) {
		super(p);
		this.property = pr;
	}

	public MarketProperty getProperty() {
		return property;
	}

	@Override
	public String getDescription() {
		return "Withdraw from bidding on '" + property.getName() + "'.";
	}
}
