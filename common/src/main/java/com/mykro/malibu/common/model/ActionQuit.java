package com.mykro.malibu.common.model;

/**
 * An Action that quits the Game
 */
public class ActionQuit extends Action implements Actionable {

	public ActionQuit(Player p) {
		super(p);
	}

	@Override
	public String getDescription() {
		return "Quit Game";
	}

}
