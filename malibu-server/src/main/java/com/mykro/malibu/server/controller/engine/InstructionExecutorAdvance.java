package com.mykro.malibu.server.controller.engine;

import com.mykro.malibu.common.controller.InstructionExecutor;
import com.mykro.malibu.common.controller.InstructionExecutorException;
import com.mykro.malibu.common.model.BaseProperty;
import com.mykro.malibu.common.model.Instruction;
import com.mykro.malibu.common.model.InstructionAdvancePositions;
import com.mykro.malibu.common.model.InstructionAdvanceToGroup;
import com.mykro.malibu.common.model.InstructionAdvanceToProperty;
import com.mykro.malibu.common.model.PropertyGroup;
import com.mykro.malibu.common.model.World;

/**
 * The user advances to the given location.
 * 
 * NOTE: This Executor can handle 3 different types of advance instructions.
 */
public class InstructionExecutorAdvance implements InstructionExecutor {

	@Override
	public boolean execute(World world, Instruction instruction) throws InstructionExecutorException {

		BaseProperty curr = world.getCurrentPlayer().getCurrentProperty();
		int offset = 0;

		// calculate the offset
		if ( instruction instanceof InstructionAdvancePositions ) {
			offset = ((InstructionAdvancePositions)instruction).getOffset();
		} else if ( instruction instanceof InstructionAdvanceToProperty ) {
			BaseProperty dest = ((InstructionAdvanceToProperty)instruction).getProperty();
			offset = world.getBoard().getDistance( curr, dest );
		} else if ( instruction instanceof InstructionAdvanceToGroup ) {
			PropertyGroup destgroup = ((InstructionAdvanceToGroup)instruction).getPropertyGroup();
			BaseProperty dest = world.getBoard().getProperties().getNextInGroup(destgroup, curr);
			offset = world.getBoard().getDistance( curr, dest );
		} else {
			throw new InstructionExecutorException(this, "Unrecognised instruction.");
		}

		if ( offset != 0 ) {
			world.getCurrentTurn().setAdvancingPositions(offset);
			
			// TODO: PERFORM TRANSITIONS!!!
			// normal movement
			//TransitionPlayerAdvance t2 = new TransitionPlayerAdvance();
			//t2.execute(world);

			//TransitionPlayerLanding t3 = new TransitionPlayerLanding();
			//t3.execute(world);
		}
		
		return true;
	}

}
