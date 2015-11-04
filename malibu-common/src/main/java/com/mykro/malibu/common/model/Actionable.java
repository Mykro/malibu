package com.mykro.malibu.common.model;

/**
 * Actions are initiated by a Player.
 * 
 * Some Actions require additional data, marked with (+) in the list below.
 * TODO - establish a mechanism to pass this data.
 * 
 * The Potential Actions:
 *  Roll Dice To Move
 *  Roll Dice To Escape Jail  
 * 	Buy Property
 * 	Decline To Buy Property (sends it to Auction)
 *  Bid on Property ==> this is a lot like Propose Trade
 *  Pass on Property ==> this is a lot like Reject Trade
 *  Mortgage Property (+ property)
 *  Unmortgage Property (+ property)
 *  Buy House (+ group, property)
 *  Sell House (+ group, property)
 *  Pay Jail Fee
 *  End Turn
 *  Propose Trade (+ details: property, cash, card)
 *  Cancel Trade
 *  Accept Trade
 *  Reject Trade
 *  Quit
 *  
 */
public interface Actionable {

	/**
	 * Return a descriptive name for this Action
	 * @return
	 */
	// public String getDescription();
	
	/**
	 * The Action is fully formed and contains all the necessary data to be executed
	 * @return
	 */
	// public boolean isFullyFormed();
}
