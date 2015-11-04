package com.mykro.malibu.common.model;

// TODO: FIX BAD DESIGN
//import com.mykro.malibu.controller.TransitionPlayerAdvance;
//import com.mykro.malibu.controller.TransitionPlayerLanding;

/**
 * Advance normally to a given property or the first property in a given group.
 */
public class InstructionAdvanceToGroup extends Instruction {

	PropertyGroup group;
	
	public InstructionAdvanceToGroup( PropertyGroup g ) {
		this.group = g;
	}
	public PropertyGroup getPropertyGroup() {
		return group;
	}
}
