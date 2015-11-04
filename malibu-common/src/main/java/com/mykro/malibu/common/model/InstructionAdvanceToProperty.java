package com.mykro.malibu.common.model;

/**
 * Advance normally to a given property or the first property in a given group.
 */
public class InstructionAdvanceToProperty extends Instruction {

	BaseProperty property;
	
	public InstructionAdvanceToProperty( BaseProperty p ) {
		this.property = p;
	}
	
	public BaseProperty getProperty() {
		return property;
	}
}
