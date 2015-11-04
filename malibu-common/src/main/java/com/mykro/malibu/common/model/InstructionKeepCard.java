package com.mykro.malibu.common.model;

import java.util.LinkedList;

/**
 * Keep the card in the player's hand until a later time
 * where it can be "played".
 */
public class InstructionKeepCard extends Instruction {

	// a set of Delayed Instructions that are to be executed later when the card is later played.
	private LinkedList<Instruction> deployInstructions = new LinkedList<Instruction>();
	
	public InstructionKeepCard() {
	}

	public void addDeployInstruction(Instruction instruction) {
		deployInstructions.add(instruction);
	}
	

}
