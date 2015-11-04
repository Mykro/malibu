package com.mykro.malibu.common.controller;

import java.util.HashMap;

import com.mykro.malibu.common.model.Instruction;

/**
 * Return an InstructionExecutor instance for a given Instruction instance.
 */
public class InstructionExecutorFactory {
	
	private static InstructionExecutorFactory _globalInstance;
	public static InstructionExecutorFactory getGlobalInstance() {
		if ( _globalInstance == null ) {
			_globalInstance = new InstructionExecutorFactory();
		}
		return _globalInstance;
	}
	
	private HashMap<Class<? extends Instruction>,InstructionExecutor> InstructionExecutors = new HashMap<Class<? extends Instruction>,InstructionExecutor>();
	private HashMap<InstructionExecutor,Class<? extends Instruction>> InstructionClasses = new HashMap<InstructionExecutor,Class<? extends Instruction>>();
	
	/**
	 * Register an InstructionExecutor for a given Instruction class. 
	 * @param InstructionClass
	 * @param executorClass
	 */
	public boolean registerInstructionExecutor( Class<? extends Instruction> InstructionClass, Class<? extends InstructionExecutor> executorClass ) {

		if ( !InstructionExecutors.containsKey(InstructionClass) ) {
			try {
				InstructionExecutor InstructionExecutor = executorClass.newInstance();
				InstructionExecutors.put(InstructionClass,InstructionExecutor);

				// register the reverse lookup
				InstructionClasses.put(InstructionExecutor,InstructionClass);
				
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
	 * Given an instantiated Instruction, return the suitable InstructionExecutor for that Instruction's class.
	 * 
	 * @param Instruction
	 * @return
	 */
	public InstructionExecutor getInstructionExecutor( Instruction Instruction ) {
		return InstructionExecutors.get(Instruction.getClass());
	}

	/**
	 * Given an InstructionExecutor, create a new Instruction instance.
	 * 
	 * @param Instruction
	 * @return
	 */
	public Instruction createInstruction( InstructionExecutor InstructionExecutor ) {
		Class<? extends Instruction> InstructionClass = InstructionClasses.get(InstructionExecutor);
		try {
			Instruction newInstruction = InstructionClass.newInstance();
			return newInstruction;
		} catch ( InstantiationException ie ) {
			return null;
		} catch ( IllegalAccessException iae ) {
			return null;
		}

	}
	
}
