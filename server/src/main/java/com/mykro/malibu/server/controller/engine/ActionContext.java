package com.mykro.malibu.server.controller.engine;

import com.mykro.malibu.common.model.Player;

/**
 * The Context against which an Action is evaluated and executed.
 * At this time the context is merely a single Player, because from the Player object
 * we can deduce the World and everything else we need.
 * 
 * TO BE DELETED... NOT NEEDED ANY MORE
 */
public class ActionContext {

	Player player = null;
	
	public ActionContext(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}
}
