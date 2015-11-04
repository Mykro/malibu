package com.mykro.malibu.common.model;

/**
 * An Instruction that forms part of a Card.  They can be queued up.
 * 
 * Instructions basically execute a series of transitions.  In this way they are
 * much like Actions, except the player is unknown until the Instruction is executed.
 * 
 * Instructions:
 * 	Advance To Property   
	Advance To First Property In A Group (utility, railroad)
	Advance X spaces (backwards or forward)
	After Advance Pay X * Rent
	After Advance Pay X * Y Dice Roll in Rent
	Player Cash Adjustment
	All Rivals Cash Adjustment
	Player Cash Adjustment Based On Houses
	Player Cash Adjustment Based On Net Worth
	Player Cash Adjustment Based On Dice Roll	
	Go To Jail
	Get Out Of Jail
 */
public abstract class Instruction extends BaseObject {
	
//	public abstract void execute(World board, Player player);
}
