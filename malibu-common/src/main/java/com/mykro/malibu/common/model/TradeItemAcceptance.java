package com.mykro.malibu.common.model;

/**
 * A player acceptance that forms part of a Trade.
 * 
 * When a player adds this to a trade they are agreeing to the trade or rejecting the trade.
 * 
 * When a player removes this from a trade they are undecided about the trade.
 */
public class TradeItemAcceptance extends TradeItem {

	private boolean accept;

	public TradeItemAcceptance( Player source, boolean accept) {
		super( source, null );
		this.accept = accept;
	}

	public boolean getAcceptance() {
		return accept;
	}
}
