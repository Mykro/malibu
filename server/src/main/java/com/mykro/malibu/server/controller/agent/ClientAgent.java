package com.mykro.malibu.server.controller.agent;

import java.util.List;

import com.mykro.malibu.common.model.Action;
import com.mykro.malibu.common.model.World;

/**
 * An agent that communicates with us via our IO interface.
 * Typically a human or an automated remote client.
 */
public class ClientAgent extends Agent {

	public ClientAgent(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleEventWorldUpdate(World world) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleEventPlayerActionsUpdate(World world,
			List<Action> availableActions) {
		// TODO Auto-generated method stub
		
	}


}
