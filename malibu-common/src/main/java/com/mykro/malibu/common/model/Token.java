package com.mykro.malibu.common.model;

/**
 * A Single Player Token
 */
public class Token extends BaseObject {

	private Player owner;

	public Token(String name) {
		super(name);
	}
	public void setOwner(Player p) {
		owner = p;
	}
	public Player getOwner() {
		return owner;
	}
}
