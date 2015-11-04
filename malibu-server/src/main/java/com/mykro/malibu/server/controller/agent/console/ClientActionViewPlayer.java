package com.mykro.malibu.server.controller.agent.console;

import com.mykro.malibu.common.model.Action;
import com.mykro.malibu.common.model.Player;

/**
 * An Action that views another Player's visible information
 */
public class ClientActionViewPlayer extends Action implements ClientAction {

	private Player viewedPlayer;
	protected String output;
	
	public ClientActionViewPlayer(Player viewer, Player viewed) {
		super(viewer);
		this.viewedPlayer = viewed;
	}

	public Player getViewedPlayer() {
		return viewedPlayer;
	}

	@Override
	public String getDescription() {
		return "View player '" + viewedPlayer.getName() + "'";
	}

	@Override
	public String getOutput() {
		return output;
	}
}
