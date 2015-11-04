package com.mykro.malibu.common.model;

/**
 * Advance backward or forward a given number of positions.
 */
public class InstructionAdvancePositions extends Instruction {

	int offset = 0;
	
	public InstructionAdvancePositions( int offset ) {
		this.offset = offset;
	}
	
	public int getOffset() {
		return offset;
	}
	
}
