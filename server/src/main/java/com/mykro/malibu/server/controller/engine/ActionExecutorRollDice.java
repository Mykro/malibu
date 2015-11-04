package com.mykro.malibu.server.controller.engine;

import com.mykro.malibu.common.controller.ActionExecutor;
import com.mykro.malibu.common.controller.ActionExecutorException;
import com.mykro.malibu.common.model.ActionRollDice;
import com.mykro.malibu.common.model.Action;
import com.mykro.malibu.common.model.Player;
import com.mykro.malibu.common.model.World;
import com.mykro.malibu.common.model.World.WorldState;

/**
 * The user rolls the dice (to move)
 */
public class ActionExecutorRollDice implements ActionExecutor {

	private ActionRollDice castAction(Action action) {
		return (ActionRollDice)action;
	}

	@Override
	public boolean isInitiatable(Action action) {
		return ( ( castAction(action).getPlayer() == castAction(action).getPlayer().getWorld().getCurrentPlayer() )
				&& ( castAction(action).getPlayer().getWorld().getState() == WorldState.PLAYER_STEADY )
				&& ( !castAction(action).getPlayer().isInJail() )
				&& castAction(action).getPlayer().getWorld().getCurrentTurn().isRollRequired() );
	}
	
	@Override
	public boolean isExecutable(Action action) {
		return ( isInitiatable(action) );
	}
	
	@Override
	public boolean execute(Action action) throws ActionExecutorException {

		Player player = castAction(action).getPlayer();
		World world = castAction(action).getPlayer().getWorld();
		
		TransitionRollDice t1 = new TransitionRollDice();
		try {
			t1.execute(world);
		} catch ( TransitionException te ) {
			throw new ActionExecutorException(this, te.getMessage() );
		}

		world.incrementRollsThrown();

		// check for third double in a row
		if ( ( world.getCurrentTurn().getRollsThrown() > 2 ) &&
			world.isDiceDoubles() ) {
			// send to jail
			TransitionPlayerJail tj = new TransitionPlayerJail(player);
			try {
				tj.execute(world);
			} catch ( TransitionException te ) {
				throw new ActionExecutorException(this, te.getMessage() );
			}
			return true;
		}
		
		// the player advances the amount shown on the dice
		world.setState(WorldState.PLAYER_ADVANCING);
		
		// normal movement
		world.getCurrentTurn().setAdvancingPositions(world.getDice().getValue());

		TransitionPlayerAdvance t2 = new TransitionPlayerAdvance(player, world.getDice().getValue());
		try {
			t2.execute(world);
		} catch ( TransitionException te ) {
			throw new ActionExecutorException(this, te.getMessage() );
		}

		// player landing
		TransitionPlayerLanding tpl = new TransitionPlayerLanding(player, player.getCurrentProperty());
		try {
			tpl.execute(world);
		} catch ( TransitionException te ) {
			throw new ActionExecutorException(this, te.getMessage() );
		}
	
		if ( !world.isDiceDoubles() ) {
			// ensure no more rolls
			world.getCurrentTurn().setRollNotRequired();
		}
		
		return true;
	}

}
