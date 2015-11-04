package com.mykro.malibu.server.controller.agent;

import java.util.ArrayList;
import java.util.List;

import com.mykro.malibu.common.model.Action;
import com.mykro.malibu.server.controller.engine.EventPlayerActionsUpdate;
import com.mykro.malibu.server.controller.engine.EventWorldUpdate;

/**
 * An entity that interacts in a Session.
 * Connected with an IO interface.
 */
public abstract class Agent  implements EventWorldUpdate, EventPlayerActionsUpdate {
	private String name;

	// handlers that register to receive Agent actions
	private List<EventAgentAction> agentActionHandlers = new ArrayList<EventAgentAction>();

	public Agent(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void registerPlayerActionHandler( EventAgentAction pah ) {
		agentActionHandlers.add(pah);
	}
	public void unregisterPlayerActionHandler( EventAgentAction pah ) {
		agentActionHandlers.remove(pah);
	}

	protected void publishPlayerAction(Action action) {
		for ( EventAgentAction pah : agentActionHandlers ) {
			pah.handleEventAgentAction(this, action);
		}
	}

}
