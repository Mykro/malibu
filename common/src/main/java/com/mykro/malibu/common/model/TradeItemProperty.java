package com.mykro.malibu.common.model;

/**
 * A property transfer that forms part of a Trade.
 */
public class TradeItemProperty extends TradeItem {

	private MarketProperty property;

	public TradeItemProperty( Player source, Player destination, MarketProperty property) {
		super( source, destination );
		this.property = property;
	}
	
	public MarketProperty getProperty() {
		return property;
	}

}
