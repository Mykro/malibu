package com.mykro.malibu.common.model;

/**
 * An Action that ends the Players turn
 */
public class ActionEndTurn extends Action implements Actionable {

	public ActionEndTurn(Player p) {
		super(p);
	}

	@Override
	public String getDescription() {
		return "End Turn";
	}

}
