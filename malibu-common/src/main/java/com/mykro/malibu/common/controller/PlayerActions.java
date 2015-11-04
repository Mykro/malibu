package com.mykro.malibu.common.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mykro.malibu.common.model.Action;
import com.mykro.malibu.common.model.Player;

/**
 * Collects the actions available to each player, and then makes them available.
 */
public class PlayerActions {
	
	private HashMap<Player,List<Action>> actions = new  HashMap<Player,List<Action>>();

	public void addActions(List<Action> actions) {
		if ( actions == null ) {
			return;
		}
		for (Action a : actions) {
			addAction(a);
		}
	}
	
	public void addAction(Action a) {
		if ( a.getPlayer() == null ) {
			return; // bad action?
		}
		List<Action> playerActionList = actions.get(a.getPlayer());
		if ( playerActionList == null ) {
			playerActionList = new ArrayList<Action>();
			actions.put( a.getPlayer(), playerActionList );
		}
		playerActionList.add(a);
	}

	public List<Action> getPlayerActions(Player p) {
		return actions.get(p);
	}
	
}
