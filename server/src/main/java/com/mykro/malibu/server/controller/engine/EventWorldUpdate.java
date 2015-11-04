package com.mykro.malibu.server.controller.engine;

import com.mykro.malibu.common.model.World;

/**
 * Handles a World update.  
 * With this handler we only know something has changed, but not what.
 * This is good for doing "whole world" refreshes.
 * TODO: define some other handlers to take care of small delta changes.
 */
public interface EventWorldUpdate {

	public void handleEventWorldUpdate( World world );
}
