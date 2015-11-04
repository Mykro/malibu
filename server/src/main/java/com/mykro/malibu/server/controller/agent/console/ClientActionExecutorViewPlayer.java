package com.mykro.malibu.server.controller.agent.console;

import com.mykro.malibu.common.controller.ActionExecutor;
import com.mykro.malibu.common.model.Action;

/**
 */
public class ClientActionExecutorViewPlayer implements ActionExecutor {

	private ClientActionViewPlayer castAction(Action action) {
		return (ClientActionViewPlayer)action;
	}

	@Override
	public boolean isInitiatable(Action action) {
		return true;
	}
	
	@Override
	public boolean isExecutable(Action action) {
		return true;
	}
	
	@Override
	public boolean execute(Action action) {
		
		WorldDescriber wd = new WorldDescriber();
		castAction(action).output = 
				wd.describePlayer(castAction(action).getViewedPlayer()).toString() + 
				wd.describePlayerProperties(castAction(action).getViewedPlayer()).toString();
		
		return true;
	}

}
