package com.mykro.malibu.server.controller.engine;

import com.mykro.malibu.common.controller.ActionExecutor;
import com.mykro.malibu.common.controller.ActionExecutorException;
import com.mykro.malibu.common.model.ActionBuyPropertyDecline;
import com.mykro.malibu.common.model.Auction;
import com.mykro.malibu.common.model.Action;
import com.mykro.malibu.common.model.World.WorldState;

/**
 * Execute the Decline To Buy Property Action.
 */
public class ActionExecutorBuyPropertyDecline implements ActionExecutor {

	private ActionBuyPropertyDecline castAction(Action action) {
		return (ActionBuyPropertyDecline)action;
	}
	
	@Override
	public boolean isInitiatable(Action action) {
		return ( ( castAction(action).getPlayer() == castAction(action).getPlayer().getWorld().getCurrentPlayer() )
	 			  && ( castAction(action).getPlayer().getWorld().getState() == WorldState.PLAYER_STEADY )
				  && ( castAction(action).getPlayer().getCurrentProperty() == castAction(action).getProperty() )
				  && ( castAction(action).getProperty().isPurchasable() ) );
	}
	
	@Override
	public boolean isExecutable(Action action) {
		return ( isInitiatable(action) );
	}
	
	@Override
	public boolean execute(Action action) throws ActionExecutorException {
		
		TransitionBuyPropertyDecline t1 = new TransitionBuyPropertyDecline(castAction(action).getPlayer(), castAction(action).getProperty());
		try {
			t1.execute(castAction(action).getPlayer().getWorld());
		} catch ( TransitionException te ) {
			throw new ActionExecutorException(this, te.getMessage() );
		}
		
		// Official: the property is sent to auction.
		// House Rule Modifier: the property is ignored.
		Auction auction = new Auction(null); // bank is the vendor!
		
		// TODO: get the Auction Controller for the property and start it.
		// IDEA: put the TransitionAuction into a transition queue.

		// describe( player.getName() + " purchases " + property.getName() + " for $" + cost + "." );

		
		return true;
	}



}
