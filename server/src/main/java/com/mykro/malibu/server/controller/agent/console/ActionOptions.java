package com.mykro.malibu.server.controller.agent.console;

import java.util.HashMap;
import java.util.List;

import com.mykro.malibu.common.model.Action;

/**
 * Collects the actions available along with an option identifer.
 */
public class ActionOptions extends HashMap<Integer,Action> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6299895801863745962L;
	private Integer nextID = 1;

	public void addActions(List<Action> actions) {
		if ( actions == null ) {
			return;
		}
		for (Action a : actions) {
			addAction(a);
		}
	}
	
	public void addAction(Action a) {
		this.put(nextID, a);
		nextID++;
	}

	
}
