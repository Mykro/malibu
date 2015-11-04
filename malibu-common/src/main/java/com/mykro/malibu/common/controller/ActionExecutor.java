package com.mykro.malibu.common.controller;

import com.mykro.malibu.common.model.Action;

/**
 * Executes an associated Action.
 * 
 * An ActionExecutor can determine at any given moment if the Action can be initiated or executed.
 * 
 * Some actions can be executed immediately (initiated and executed).
 * 
 * Some actions can be initiated but then need to have additional data provided before they can be executed.
 * 
 * An ActionExecutor can execute an Action, producing one or more Transition(s) 
 * in the engine.
 * 
 * IMPORTANT:
 * All ActionExecuter implementations should be stateless methods, ie they should not
 * retain any data between calls.  This is because the ActionExecutors are 
 * reused over and over by the ActionExecutorFactory.
 */
public interface ActionExecutor {

	public abstract boolean isInitiatable(Action action);
	public abstract boolean isExecutable(Action action);
	public abstract boolean execute(Action action) throws ActionExecutorException;
}
