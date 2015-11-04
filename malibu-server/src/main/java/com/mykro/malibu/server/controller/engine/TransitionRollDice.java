package com.mykro.malibu.server.controller.engine;

import com.mykro.malibu.common.model.World;

/**
 * An Transition that rolls the Dice in the world
 * and updates observers
 */
public class TransitionRollDice extends Transition {

	@Override
	public World execute(World world) throws TransitionException {

		/*
		 * The DiceRoller can support pre-determined values
		 */
		DiceRoller.rollDiceSet( world, world.getDice() );
		
		describe("The dice roll is " + world.getDice().getValue() + 
				" (" + world.getDice().getValue(0) + 
				" and " + world.getDice().getValue(1) + ")");
		
		return world;
	}
}
