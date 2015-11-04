package com.mykro.malibu.common.model;

/**
 * Pay the landlord a multiplier of the normal rent
 */
public class InstructionPayLandlordRentMultiplier extends Instruction {

	double multiplier;
	
	public InstructionPayLandlordRentMultiplier( double multiplier ) {
		this.multiplier = multiplier;
	}

	public double getMultiplier() {
		return multiplier;
	}
}
