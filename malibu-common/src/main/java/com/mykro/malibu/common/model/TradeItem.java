package com.mykro.malibu.common.model;

/**
 * Abstract class to represent an item traded from one Player to another.
 * 
 * If one of the Players is null, that means that side of the trade is yet to be 
 * determined.  For example, if sourcePlayer is null, then destinationPlayer is
 * asking everyone for the item.  If destinationPlayer is null then
 * sourcePlayer is offering the item to all takers.
 */
public abstract class TradeItem {

	private Player sourcePlayer;
	private Player destinationPlayer;

	public TradeItem(Player source, Player destination ) {
		this.sourcePlayer = source;
		this.destinationPlayer = destination;
	}

	public void setSourcePlayer( Player player ) {
		this.sourcePlayer = player;
	}
	public Player getSourcePlayer() {
		return sourcePlayer;
	}
	public void setDestinationPlayer( Player player ) {
		this.destinationPlayer = player;
	}
	public Player getDestinationPlayer() {
		return destinationPlayer;
	}
}
