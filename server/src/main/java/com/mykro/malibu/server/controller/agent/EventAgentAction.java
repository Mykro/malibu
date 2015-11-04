package com.mykro.malibu.server.controller.agent;

import com.mykro.malibu.common.model.Action;

/**
 * Handles an Action coming from an Agent.
 */
public interface EventAgentAction {
	public void handleEventAgentAction( Agent agent, Action action );
}
