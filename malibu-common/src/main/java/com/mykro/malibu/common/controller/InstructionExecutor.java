package com.mykro.malibu.common.controller;

import com.mykro.malibu.common.model.Instruction;
import com.mykro.malibu.common.model.World;

/**
 * Executes an associated Instruction.
 * 
 * An InstructionExecutor can execute an Instruction, producing one or more Transition(s) 
 * in the engine.
 * 
 * IMPORTANT:
 * All InstructionExecuter implementations should be stateless methods, ie they should not
 * retain any data between calls.  This is because the InstructionExecutors are 
 * reused over and over by the InstructionExecutorFactory.
 */
public interface InstructionExecutor {

	public abstract boolean execute(World world, Instruction Instruction) throws InstructionExecutorException;
}
