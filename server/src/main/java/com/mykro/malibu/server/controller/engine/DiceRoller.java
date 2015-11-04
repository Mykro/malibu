package com.mykro.malibu.server.controller.engine;

import com.mykro.malibu.common.model.Dice;
import com.mykro.malibu.common.model.DiceSet;
import com.mykro.malibu.common.model.World;

/**
 * This static class is in charge of rolling Dice.
 * 
 * It applies a new value to each of the Dice it is given.
 * 
 * Typically it would use random rolls but for testing purposes
 * it also supports a "stack" of pre-determined rolls which 
 * it can find in the World model.
 */
public class DiceRoller {

	private static int getNextDiceNumber(World w, int faces ) {
		if ( w.hasFutureDiceRoll() ) {
			// pull from the World's stack
			return w.getFutureDiceRoll();
		} else {
			// generate a random number
			return (int) ( Math.random() * faces ) + 1;
		}
	}
	
	/**
	 * Roll one die.
	 */
	public static int rollDice(World w, Dice d) {
		int i = getNextDiceNumber(w, d.getFaces());
		d.setValue(i);
		return i;
	}
	
	/**
	 * Roll all the dice.
	 */
	public static int rollDiceSet(World w, DiceSet ds) {
		int value = 0;
		for ( Dice dice : ds.getDice() ) {
			value += rollDice(w,dice);
		}
		return value;
		
	}
}
