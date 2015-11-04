package com.mykro.malibu.server.controller.engine;

import com.mykro.malibu.common.model.World;

/**
 * A Transition that moves the World from one state to another.
 * 
 * If a Transition cannot execute, it will throw a TransitionException.
 * 
 * Transitions should have the bare minimum validation to maintain a valid world, ie.
 * Transitions should not assume things like the current player, this allows them to be 
 * executed in any context.
 */
public abstract class Transition {
	
	/**
	 * Executes this Transition in the given World
	 * and returns the same World modified.  
	 * @param world
	 * @return
	 */
	public abstract World execute(World board) throws TransitionException;

	public void describe(String s) {
		System.out.println("[transition] " + s);
	}
}
