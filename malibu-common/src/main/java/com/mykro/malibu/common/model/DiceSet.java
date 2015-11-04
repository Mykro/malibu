package com.mykro.malibu.common.model;

import java.util.Vector;

/**
 * A collection of Dice.
 */
public class DiceSet {

	private Vector<Dice> diceSet = new Vector<Dice>();
	
	public DiceSet( int numberOfDice ) {
		for ( int i=0;i<numberOfDice;i++) {
			diceSet.add(new Dice("dice"+i));
		}
	}
	
	public int getValue() {
		int value = 0;
		for ( Dice dice : diceSet ) {
			value += dice.getValue();
		}
		return value;
	}

	public Vector<Dice> getDice() {
		return diceSet;
	}
	public int getValue(int diceIndex) {
		return diceSet.get(diceIndex).getValue();
	}

	public void setValue(int diceIndex, int value ) {
		diceSet.get(diceIndex).setValue(value);
	}

}
