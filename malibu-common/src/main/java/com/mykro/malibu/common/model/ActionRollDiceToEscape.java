package com.mykro.malibu.common.model;

/**
 * An Action that Rolls the Dice to get out of jail
 */
public class ActionRollDiceToEscape extends Action implements Actionable {

	public ActionRollDiceToEscape(Player p) {
		super(p);
	}
	
	@Override
	public String getDescription() {
		return "Roll Dice To Escape";
	}
	
}
