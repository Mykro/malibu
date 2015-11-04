package com.mykro.malibu.common.model;

import java.util.LinkedList;

/**
 * A Community Chest or Chance card.
 * Can contain a sequence of multiple instructions.
 */
public class Card extends BaseObject {

	// the player is holding this card in their hand (eg get out of jail)
	private Player owner;

	private LinkedList<Instruction> instructions = new LinkedList<Instruction>();
	
	public Card(String name) {
		super(name);
	}

	public Card addInstruction(Instruction instruction) {
		instructions.add(instruction);
		return this;
	}
	
	public LinkedList<Instruction> getInstructions() {
		return instructions;
	}
	
	public boolean inDeck() {
		return ( owner == null );
	}

	public Player getOwner() {
		return owner;
	}
	public void setOwner(Player owner) {
		this.owner = owner;
	}



	
}
