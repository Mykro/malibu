package com.mykro.malibu.common.model;

/**
 * An Action that accepts a proposed Trade. 
 * 
 * The Trade will need to be fully formed (all source and destination players defined).
 */
public class ActionAcceptTrade extends Action implements Actionable {

	private Trade trade;
	
	public ActionAcceptTrade(Player p, Trade t) {
		super(p);
		this.trade = t;
	}
	
	public Trade getTrade() {
		return trade;
	}

	@Override
	public String getDescription() {
		return "Accept this trade.";
	}
}
