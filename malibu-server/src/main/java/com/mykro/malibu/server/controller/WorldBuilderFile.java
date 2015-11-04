package com.mykro.malibu.server.controller;

import com.mykro.malibu.server.external.JsonSimpleHelper;
import com.mykro.malibu.common.model.Player;
import com.mykro.malibu.common.model.World;
import com.mykro.malibu.common.model.World.WorldState;

/**
 * Initialises a World instance from an external file.
 */

/*
 * TODO: hook this into the startup process to replace WorldBuilderInternal
 */
public class WorldBuilderFile implements WorldBuilder {

	private String boardFilePath;
	
	public WorldBuilderFile( String path ) {
		boardFilePath = path;
	}
	
	public void initialiseEverything(World world) {

		JsonSimpleHelper.importBoard(boardFilePath, world);
		initialisePlayers(world);
		world.setState(WorldState.PLAYER_STEADY);
	}

	public void initialisePlayer(World world, Player p) {
		p.setCash(1500);
		p.setCurrentProperty(world.getProperties().getPropertyByPosition(0));
		p.getProperties().clear();
		p.getCards().clear();
		p.setRollsToEscape(0);
		
		// TODO: mechanism to allow a player to select or change their token
		initialisePlayerTokenAutomatically(world,p);
	}

	private void initialisePlayerTokenAutomatically(World world, Player p) {
		if ( p.getToken() == null ) {
			p.setToken( world.getFirstAvailableToken() );
		}
	}
	
	private void initialisePlayers(World world) {
		for ( Player p : world.getPlayers() ) {
			initialisePlayer(world, p);
		}
	}

}
