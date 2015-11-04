package com.mykro.malibu.server.controller.engine;

import com.mykro.malibu.common.controller.ActionExecutor;
import com.mykro.malibu.common.controller.ActionExecutorException;
import com.mykro.malibu.common.model.ActionEndTurn;
import com.mykro.malibu.common.model.Action;
import com.mykro.malibu.common.model.World.WorldState;

/**
 * An Action that ends the Players turn
 */
public class ActionExecutorEndTurn implements ActionExecutor {

	private ActionEndTurn castAction(Action action) {
		return (ActionEndTurn)action;
	}

	@Override
	public boolean isInitiatable(Action action) {
		return ( ( castAction(action).getPlayer() == castAction(action).getPlayer().getWorld().getCurrentPlayer() )
				&& ( castAction(action).getPlayer().getWorld().getState() == WorldState.PLAYER_STEADY )
				&& ( !castAction(action).getPlayer().getWorld().getCurrentTurn().isRollRequired() )
				);
	}

	@Override
	public boolean isExecutable(Action action) {
		return ( isInitiatable(action) );
	}
	
	@Override
	public boolean execute(Action action) throws ActionExecutorException {

		TransitionPlayerEndTurn t1 = new TransitionPlayerEndTurn(castAction(action).getPlayer());
		try {
			t1.execute(castAction(action).getPlayer().getWorld());
		} catch ( TransitionException te ) {
			throw new ActionExecutorException(this, te.getMessage() );
		}
		
		return true;
	}

}
