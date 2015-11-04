package com.mykro.malibu.common.model;

/**
 * A cash transfer that forms part of a Trade.
 */
public class TradeItemCash extends TradeItem {

	private int cash;

	public TradeItemCash( Player source, Player destination, int cash) {
		super( source, destination );
		this.cash = cash;
	}

	public int getCash() {
		return cash;
	}
	
}
