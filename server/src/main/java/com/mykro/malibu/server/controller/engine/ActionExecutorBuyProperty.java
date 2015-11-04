package com.mykro.malibu.server.controller.engine;

import com.mykro.malibu.common.controller.ActionExecutor;
import com.mykro.malibu.common.controller.ActionExecutorException;
import com.mykro.malibu.common.model.ActionBuyProperty;
import com.mykro.malibu.common.model.Action;
import com.mykro.malibu.common.model.World.WorldState;

/**
 * Execute the Buy Property Action.
 */
public class ActionExecutorBuyProperty implements ActionExecutor {

	private ActionBuyProperty castAction(Action action) {
		return (ActionBuyProperty)action;
	}
	
	@Override
	public boolean isInitiatable(Action action) {
		return ( ( castAction(action).getPlayer() == castAction(action).getPlayer().getWorld().getCurrentPlayer() )
	 			  && ( castAction(action).getPlayer().getWorld().getState() == WorldState.PLAYER_STEADY )
				  && ( castAction(action).getPlayer().getCurrentProperty() == castAction(action).getProperty() )
				  && ( castAction(action).getProperty().isPurchasable() )
				  && ( castAction(action).getPlayer().hasCash(castAction(action).getProperty().getPurchaseCost() ) ) );
	}
	
	@Override
	public boolean isExecutable(Action action) {
		return ( isInitiatable(action) );
	}
	
	@Override
	public boolean execute(Action action) throws ActionExecutorException {
		
		TransitionBuyProperty t1 = new TransitionBuyProperty(castAction(action).getPlayer(), castAction(action).getProperty());
		try {
			t1.execute(castAction(action).getPlayer().getWorld());
		} catch ( TransitionException te ) {
			throw new ActionExecutorException(this, te.getMessage() );
		}
		
		return true;
	}



}
