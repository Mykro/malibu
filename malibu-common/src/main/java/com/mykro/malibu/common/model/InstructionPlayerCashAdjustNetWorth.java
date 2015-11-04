package com.mykro.malibu.common.model;

/**
 * Adjust the player's cash by a percentage of networth
 */
public class InstructionPlayerCashAdjustNetWorth extends Instruction {

	int percent;
	
	public InstructionPlayerCashAdjustNetWorth( int percent ) {
		this.percent = percent;
	}
	public int getPercent() {
		return percent;
	}

}
