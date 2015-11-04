package com.mykro.malibu.server.controller.engine;

import com.mykro.malibu.common.controller.ActionExecutor;
import com.mykro.malibu.common.controller.ActionExecutorException;
import com.mykro.malibu.common.model.ActionQuit;
import com.mykro.malibu.common.model.Action;
import com.mykro.malibu.common.model.World;
import com.mykro.malibu.common.model.World.WorldState;

/**
 * A Player executes this action to quit the world
 */
public class ActionExecutorQuit implements ActionExecutor {

	private ActionQuit castAction(Action action) {
		return (ActionQuit)action;
	}

	@Override
	public boolean isInitiatable(Action action) {
		// should be possible at any time (so players can be yanked out of the world instantly)
		return true;
	}
	
	@Override
	public boolean isExecutable(Action action) {
		// should be possible at any time (so players can be yanked out of the world instantly)
		return true;
	}
	
	@Override
	public boolean execute(Action action) throws ActionExecutorException {
		World world = castAction(action).getPlayer().getWorld();

		// if its the current player, pass the turn to the next person
		if ( castAction(action).getPlayer() == world.getCurrentPlayer() ) {
			TransitionPlayerEndTurn t1 = new TransitionPlayerEndTurn(castAction(action).getPlayer());
			try {
				t1.execute(castAction(action).getPlayer().getWorld());
			} catch ( TransitionException te ) {
				throw new ActionExecutorException(this, te.getMessage() );
			}
		}

		// update the world model
		world.removePlayer(castAction(action).getPlayer());

		// the last player has left
		if ( world.getPlayers().size() == 0 ) {
			world.setState(WorldState.WORLD_GAME_OVER);
		}
		return true;
	}

}
