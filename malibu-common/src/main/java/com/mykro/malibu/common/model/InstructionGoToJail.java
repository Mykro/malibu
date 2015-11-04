package com.mykro.malibu.common.model;

/**
 * Relocate directly to a given property without advancing through
 * the intervening properties.  Enter the jail at the destination.
 */
public class InstructionGoToJail extends Instruction {

	JailProperty property;
	PropertyGroup group;
	
	public InstructionGoToJail( JailProperty jail ) {
		this.property = jail;
	}
	public InstructionGoToJail( PropertyGroup group ) {
		this.group = group;
	}

	public JailProperty getProperty() {
		return property;
	}
	public PropertyGroup getGroup() {
		return group;
	}
	
}
