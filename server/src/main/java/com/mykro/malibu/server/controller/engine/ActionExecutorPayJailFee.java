package com.mykro.malibu.server.controller.engine;

import com.mykro.malibu.common.controller.ActionExecutor;
import com.mykro.malibu.common.controller.ActionExecutorException;
import com.mykro.malibu.common.model.ActionPayJailFee;
import com.mykro.malibu.common.model.Action;
import com.mykro.malibu.common.model.JailProperty;
import com.mykro.malibu.common.model.World.WorldState;

/**
 * The user pays a fee to exit jail
 */
public class ActionExecutorPayJailFee implements ActionExecutor {

	private ActionPayJailFee castAction(Action action) {
		return (ActionPayJailFee)action;
	}
	
	@Override
	public boolean isInitiatable(Action action) {
		return ( ( castAction(action).getPlayer() == castAction(action).getPlayer().getWorld().getCurrentPlayer() )
				&& ( castAction(action).getPlayer().getWorld().getState() == WorldState.PLAYER_STEADY )
				&& ( castAction(action).getPlayer().isInJail() )
				&& ( castAction(action).getPlayer().getWorld().getCurrentTurn().isRollRequired() )   );
	}

	@Override
	public boolean isExecutable(Action action) {
		return ( isInitiatable(action) );
	}
	
	@Override
	public boolean execute(Action action) throws ActionExecutorException {

		// TODO: support multiple jail properties, currently this assumes there is only one
		JailProperty property = castAction(action).getPlayer().getWorld().getJailProperty();
				
		TransitionPayJailFee t1 = new TransitionPayJailFee(castAction(action).getPlayer(), property);
		try {
			t1.execute(castAction(action).getPlayer().getWorld());
		} catch ( TransitionException te ) {
			throw new ActionExecutorException(this, te.getMessage() );
		}
		
		return true;
	}

}
