package com.mykro.malibu.common.controller;

import java.util.ArrayList;
import java.util.List;

import com.mykro.malibu.common.model.Action;

/**
 * Manage the available Actions.
 */
public class ActionFactory {
	
	private List<Class<? extends Action>> actionClasses = new ArrayList<Class<? extends Action>>();
	
	/**
	 * Register an ActionExecutor for a given Action class. 
	 * @param actionClass
	 * @param executorClass
	 */
	public boolean registerActionClass( Class<? extends Action> actionClass ) {

		if ( !actionClasses.contains(actionClass) ) {
			try {
				actionClasses.add(actionClass);
				return true;
			} catch ( UnsupportedOperationException uoe ) {
				return false;
			} catch ( ClassCastException cce ) {
				return false;
			} catch ( NullPointerException npe ) {
				return false;
			} catch ( IllegalArgumentException iae ) {
				return false;
			}
		} else {
			return false;  // already registered
		}
	}
	
	
}
