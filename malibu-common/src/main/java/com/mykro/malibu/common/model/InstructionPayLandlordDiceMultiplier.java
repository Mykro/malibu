package com.mykro.malibu.common.model;

/**
 * Roll Dice and pay the landlord a multiplier of the result
 */
public class InstructionPayLandlordDiceMultiplier extends Instruction {

	int dice;
	double multiplier;
	
	public InstructionPayLandlordDiceMultiplier( int dice, double multiplier ) {
		this.dice = dice;
		this.multiplier = multiplier;
	}

	public int getDice() {
		return dice;
	}
	public double getMultiplier() {
		return multiplier;
	}

}
