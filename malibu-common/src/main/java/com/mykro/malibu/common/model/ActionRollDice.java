package com.mykro.malibu.common.model;

/**
 * An Action that Rolls the Dice to move
 */
public class ActionRollDice extends Action implements Actionable {

	public ActionRollDice(Player p) {
		super(p);
	}
	
	@Override
	public String getDescription() {
		return "Roll Dice";
	}
	
}
