package com.mykro.malibu.server.controller;

import com.mykro.malibu.common.model.Player;
import com.mykro.malibu.common.model.World;

/**
 * Initialises a starting World.
 */
public interface WorldBuilder {
	
	public void initialiseEverything(World world);
	public void initialisePlayer(World world, Player p);  // update list of players
}
