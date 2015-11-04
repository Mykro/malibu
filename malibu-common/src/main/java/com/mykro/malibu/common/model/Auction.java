package com.mykro.malibu.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A generic data model for capturing auction information.  It includes:
 * 1. A predefined 'lot'. 
 *    - This is a Trade construct that can contain properties, houses or unplayed cards.
 * 2. a list of registered/approved participant players.
 * 3. a series of increasing bids placed by participants.  
 *    - These bids replace each other as the cash component of the Trade.
 *    - When the bid passes the reserve, the owner issues a TradeAcceptance.
 * 4. an initiating or owning player, who may or may not be in the participants.
 * 
 * The Auction itself is "run" by an AuctionController.
 */
public class Auction {

	private Player vendorPlayer;
	private List<Player> participants = new ArrayList<Player>();
	private Trade trade = new Trade();
	private TradeItemCash lastbiditem = null;
	private TradeItemAcceptance lastbidacceptanceitem = null;
	private TradeItemAcceptance acceptanceitem = null;

	public Auction( Player vendor ) {
		this.vendorPlayer = vendor;
	}

	public Player getVendor() {
		return vendorPlayer;
	}
	
	public Player getHighestBidder() {
		Player lastbidder = null;
		if ( lastbiditem != null) {
			lastbidder = lastbiditem.getSourcePlayer();
		}
		return lastbidder;
	}
	
	public int getHighestBid() {
		int lastbid = 0;
		if ( lastbiditem != null) {
			lastbid = lastbiditem.getCash();
		}
		return lastbid;
	}

	public boolean isLive() {
		return ( getHighestBid() > 0 );
	}
	
	/*
	 * submit the Vendor's acceptance (e.g. passing a reserve).
	 */
	public boolean submitVendorAcceptance(Player s, boolean accept) {
		boolean lastaccepted = false;
		if ( acceptanceitem != null ) {
			lastaccepted = acceptanceitem.getAcceptance();
		}
		if ( lastaccepted == false && accept == true ) {
			if ( acceptanceitem != null) {
				trade.getItems().remove(acceptanceitem);
			}
			acceptanceitem = trade.addAcceptance(s, accept);
			return true;
		}
		return false;
	}
	
	/*
	 * Submit a bid to the Auction.
	 */
	public boolean submitBid(int amount, Player bidder) {
		int lastbid = getHighestBid();
		if ( amount > lastbid ) {
			if ( lastbiditem != null) {
				trade.getItems().remove(lastbiditem);
			}
			if ( lastbidacceptanceitem != null) {
				trade.getItems().remove(lastbidacceptanceitem);
			}
			lastbiditem = trade.addCash(amount, bidder, vendorPlayer);
			// fix acceptance
			lastbidacceptanceitem = trade.addAcceptance(bidder, true);
			// fix destination of lot items
			for ( TradeItem ti : trade.getItems() ) {
				if ( ti != lastbiditem && ti != lastbidacceptanceitem ) {
					ti.setDestinationPlayer(bidder);
				}
			}
			return true;
		}
		return false;
	}

	/*
	 * Conclude the Auction.
	 */
	public boolean concludeAuction() {

		// TODO: validate the auction and execute the trade
		// trade.Execute();
		return true;
	}
	
	/*
	 * Add a property into the Auction lot.
	 */
	public void addVendorProperty(MarketProperty p) {
		if ( getVendor() != p.getOwner() ) {
			return;
		}
		trade.addProperty(p, p.getOwner());
	}
	
	/*
	 * Add a Card into the Auction lot.
	 */
	public void addVendorCard(Card c) {
		if ( getVendor() != c.getOwner() ) {
			return;
		}
		trade.addCard(c,c.getOwner());
	}
	
	// TODO: add a house or hotel to auction lot
}
