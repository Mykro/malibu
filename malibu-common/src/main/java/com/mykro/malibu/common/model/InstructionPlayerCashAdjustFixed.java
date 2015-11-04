package com.mykro.malibu.common.model;

/**
 * Adjust the player's cash by a fixed amount
 */
public class InstructionPlayerCashAdjustFixed extends Instruction {

	int amount;
	
	public InstructionPlayerCashAdjustFixed( int amount ) {
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}

}
