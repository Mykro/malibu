package com.mykro.malibu.server.controller.engine;

import java.util.List;

import com.mykro.malibu.common.model.Action;
import com.mykro.malibu.common.model.World;

/**
 * Handles a request coming from the World with a list of available actions for Player(s).
 */
public interface EventPlayerActionsUpdate {
	public void handleEventPlayerActionsUpdate( World world, List<Action> availableActions );
}
