package com.mykro.malibu.common.model;

/**
 * A property transfer that forms part of a Trade.
 */
public class TradeItemCard extends TradeItem {

	private Card card;

	public TradeItemCard( Player source, Player destination, Card card) {
		super( source, destination );
		this.card = card;
	}
	
	public Card getCard() {
		return card;
	}

}
