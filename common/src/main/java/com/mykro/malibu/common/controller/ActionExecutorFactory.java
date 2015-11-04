package com.mykro.malibu.common.controller;

import java.util.HashMap;

import com.mykro.malibu.common.model.Action;

/**
 * Return an ActionExecutor instance for a given Action instance.
 */
public class ActionExecutorFactory {
	
	private HashMap<Class<? extends Action>,ActionExecutor> actionExecutors = new HashMap<Class<? extends Action>,ActionExecutor>();
	private HashMap<ActionExecutor,Class<? extends Action>> actionClasses = new HashMap<ActionExecutor,Class<? extends Action>>();
	
	/**
	 * Register an ActionExecutor for a given Action class. 
	 * @param actionClass
	 * @param executorClass
	 */
	public boolean registerActionExecutor( Class<? extends Action> actionClass, Class<? extends ActionExecutor> executorClass ) {

		if ( !actionExecutors.containsKey(actionClass) ) {
			try {
				ActionExecutor ActionExecutor = executorClass.newInstance();
				actionExecutors.put(actionClass,ActionExecutor);

				// register the reverse lookup
				actionClasses.put(ActionExecutor,actionClass);
				
				return true;
			} catch ( InstantiationException ie ) {
				return false;
			} catch ( IllegalAccessException iae ) {
				return false;
			}
		} else {
			return false;  // already registered
		}
	}
	
	/**
	 * Given an instantiated Action, return the suitable ActionExecutor for that Action's class.
	 * 
	 * @param action
	 * @return
	 */
	public ActionExecutor getActionExecutor( Action action ) {
		return actionExecutors.get(action.getClass());
	}

	/**
	 * Given an ActionExecutor, create a new Action instance.
	 * 
	 * @param action
	 * @return
	 */
	public Action createAction( ActionExecutor actionExecutor ) {
		Class<? extends Action> actionClass = actionClasses.get(actionExecutor);
		try {
			Action newAction = actionClass.newInstance();
			return newAction;
		} catch ( InstantiationException ie ) {
			return null;
		} catch ( IllegalAccessException iae ) {
			return null;
		}

	}
	
}
