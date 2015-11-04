package com.mykro.malibu.server.controller.engine;

import com.mykro.malibu.common.controller.ActionExecutor;
import com.mykro.malibu.common.controller.ActionExecutorException;
import com.mykro.malibu.common.model.ActionUnmortgageProperty;
import com.mykro.malibu.common.model.Action;

/**
 * An Action that unmortgages a property
 */
public class ActionExecutorUnmortgageProperty implements ActionExecutor {

	private ActionUnmortgageProperty castAction(Action action) {
		return (ActionUnmortgageProperty)action;
	}

	@Override
	public boolean isInitiatable(Action action) {
		return ( castAction(action).getPlayer().hasMortgagedProperties() );
	}

	@Override
	public boolean isExecutable(Action action) {
		return ( isInitiatable(action)
				&& ( castAction(action).getProperty() != null )
				&& ( castAction(action).getPlayer().getProperties().contains(castAction(action).getProperty()))
				&& ( castAction(action).getProperty().isMortgaged() )   );
	}
	
	@Override
	public boolean execute(Action action) throws ActionExecutorException {

		TransitionUnmortgageProperty t1 = new TransitionUnmortgageProperty(castAction(action).getPlayer(), castAction(action).getProperty());
		try {
			t1.execute(castAction(action).getPlayer().getWorld());
		} catch ( TransitionException te ) {
			throw new ActionExecutorException(this, te.getMessage() );
		}
		
		return true;
	}

}
