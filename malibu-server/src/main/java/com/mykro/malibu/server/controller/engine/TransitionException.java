package com.mykro.malibu.server.controller.engine;

import com.mykro.malibu.common.model.World;

/**
 * Exception raised during a Transition.
 */
public class TransitionException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7577219982898295378L;
	private Transition transition;
	private World world;
	
	public TransitionException( Transition t, World w, String message ) {
		super(message);
		this.transition = t;
		this.world = w;
	}
	
	public Transition getTransition() {
		return transition;
	}
	public World getWorld() {
		return world;
	}
}