package com.mykro.malibu.common.model;

/**
 * An Action that rejects a proposed Trade. 
 * 
 * If the initiating person rejects the trade, it cancels the whole trade.
 */
public class ActionRejectTrade extends Action implements Actionable {

	private Trade trade;
	
	public ActionRejectTrade(Player p, Trade t) {
		super(p);
		this.trade = t;
	}

	public Trade getTrade() {
		return trade;
	}

	@Override
	public String getDescription() {
		return "Reject this trade.";
	}
}
