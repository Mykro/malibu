package com.mykro.malibu.server.controller.engine;

import com.mykro.malibu.common.controller.ActionExecutor;
import com.mykro.malibu.common.controller.ActionExecutorException;
import com.mykro.malibu.common.model.ActionMortgageProperty;
import com.mykro.malibu.common.model.Action;

/**
 * An Action that mortgages a property
 */
public class ActionExecutorMortgageProperty implements ActionExecutor {

	private ActionMortgageProperty castAction(Action action) {
		return (ActionMortgageProperty)action;
	}

	@Override
	public boolean isInitiatable(Action action) {
		return ( castAction(action).getPlayer().hasUnmortgagedProperties() );
	}
	
	@Override
	public boolean isExecutable(Action action) {
		return ( isInitiatable(action)
				&& ( castAction(action).getProperty() != null )
				&& ( castAction(action).getPlayer().getProperties().contains(castAction(action).getProperty()))
				&& ( !castAction(action).getProperty().isMortgaged() )   );
	}
	
	@Override
	public boolean execute(Action action) throws ActionExecutorException {

		TransitionMortgageProperty t1 = new TransitionMortgageProperty(castAction(action).getPlayer(), castAction(action).getProperty());
		try {
			t1.execute(castAction(action).getPlayer().getWorld());
		} catch ( TransitionException te ) {
			throw new ActionExecutorException(this, te.getMessage() );
		}
		
		return true;
	}

}
