package com.mykro.malibu.common.model;

/**
 * Adjust the player's cash by the number of their houses and hotels.
 * POSITIVE amounts are given to player, NEGATIVE amounts deducted
 */
public class InstructionPlayerCashAdjustHouses extends Instruction {

	int amountForHouse;
	int amountForHotel;
	
	public InstructionPlayerCashAdjustHouses( int amountForHouse , int amountForHotel ) {
		this.amountForHouse = amountForHouse;
		this.amountForHotel = amountForHotel;
	}
	
	public int getAmountForHouse() {
		return amountForHouse;
	}
	public int getAmountForHotel() {
		return amountForHotel;
	}


}
