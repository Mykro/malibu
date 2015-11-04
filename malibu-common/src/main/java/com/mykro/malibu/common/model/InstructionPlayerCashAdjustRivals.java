package com.mykro.malibu.common.model;

/**
 * Adjust the player's and all rivals' cash by a fixed amount.
 * 
 * A positive amount is ADDED to the player and DEDUCTED from rivals.
 * A negative amount is TAKEN from player and GIVEN to rivals.
 */
public class InstructionPlayerCashAdjustRivals extends Instruction {

	int amount;
	
	public InstructionPlayerCashAdjustRivals( int amount ) {
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}
}
