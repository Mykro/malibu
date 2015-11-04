package com.mykro.malibu.common.model;

/**
 * An Action that accepts a Proposed Trade. 
 * The Trade can be fully formed (specific source and destination players)
 * or partially formed (open to all players).
 */
public class ActionProposeTrade extends Action implements Actionable {

	private Trade trade;
	
	public ActionProposeTrade(Player p, Trade t) {
		super(p);
		this.trade = t;
	}

	public Trade getTrade() {
		return trade;
	}

	@Override
	public String getDescription() {
		return "Propose a trade.";
	}
}
