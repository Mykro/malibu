package com.mykro.malibu.common.controller;

/**
 * Exception raised during an InstructionExecutor
 */
public class InstructionExecutorException extends Exception {
	
	private static final long serialVersionUID = -6705015098827281570L;
	private InstructionExecutor instructionexecutor;
	
	public InstructionExecutorException( InstructionExecutor ae, String message ) {
		super(message);
		this.instructionexecutor = ae;
	}
	
	public InstructionExecutor getActionExecutor() {
		return instructionexecutor;
	}
}