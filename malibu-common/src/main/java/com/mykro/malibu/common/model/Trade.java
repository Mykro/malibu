package com.mykro.malibu.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A trade snapshot.
 * Captures all the parts of a trade.
 */
public class Trade {

	private Player initiatingPlayer;
	private List<TradeItem> items = new ArrayList<TradeItem>();

	public Trade() {
	}
	
	public Trade( Player initiatingPlayer ) {
		this.initiatingPlayer = initiatingPlayer;
	}

	public Player getInitiatingPlayer() {
		return initiatingPlayer;
	}
	
	public List<TradeItem> getItems() {
		return items;
	}
	
	/*
	 * Add acceptance or rejection to the Trade.
	 */
	public TradeItemAcceptance addAcceptance(Player s, boolean accept) {
		TradeItemAcceptance tia = new TradeItemAcceptance(s, accept);
		items.add(tia);
		return tia;
	}
	
	/*
	 * Add cash into the Trade.
	 */
	public TradeItemCash addCash(int amount, Player s, Player d) {
		TradeItemCash tica = new TradeItemCash(s, d, amount);
		items.add(tica);
		return tica;
	}

	/*
	 * Add (or re-add) a property into the Trade.
	 */
	public TradeItemProperty addProperty(MarketProperty p, Player d) {
		removeProperty(p);
		TradeItemProperty tip = new TradeItemProperty(p.getOwner(), d, p);
		items.add(tip);
		return tip;
	}

	public void removeProperty(MarketProperty p) {
		for ( TradeItem ti : items ) {
			if (   ( ti instanceof TradeItemProperty ) 
				&& ((TradeItemProperty) ti).getProperty().equals(p) ) {
				items.remove(ti);
				return;
			}
		}
	}

	
	/*
	 * Add (or re-add) a Card into the Trade.
	 */
	public void addCard(Card c, Player d) {
		removeCard(c);
		TradeItemCard tic = new TradeItemCard(c.getOwner(), d, c);
		items.add(tic);
	}

	public void removeCard(Card c) {
		for ( TradeItem ti : items ) {
			if (   ( ti instanceof TradeItemCard ) 
				&& ((TradeItemCard) ti).getCard().equals(c) ) {
				items.remove(ti);
				return;
			}
		}
	}
	
}
