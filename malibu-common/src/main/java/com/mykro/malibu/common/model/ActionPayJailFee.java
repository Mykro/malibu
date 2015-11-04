package com.mykro.malibu.common.model;

/**
 * An Action that pays to exit jail
 */
public class ActionPayJailFee extends Action implements Actionable {

	public ActionPayJailFee(Player p) {
		super(p);
	}
	
	@Override
	public String getDescription() {
		return "Pay To Get Out Of Jail";
	}
}
