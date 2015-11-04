package com.mykro.malibu.common.controller;

/**
 * Exception raised during an ActionExecutor
 */
public class ActionExecutorException extends Exception {
	
	private static final long serialVersionUID = -6705015098827281570L;
	private ActionExecutor actionexecutor;
	
	public ActionExecutorException( ActionExecutor ae, String message ) {
		super(message);
		this.actionexecutor = ae;
	}
	
	public ActionExecutor getActionExecutor() {
		return actionexecutor;
	}
}