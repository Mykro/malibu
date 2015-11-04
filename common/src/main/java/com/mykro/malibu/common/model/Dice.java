package com.mykro.malibu.common.model;

/**
 * A Single Die.
 */
public class Dice extends BaseObject {

	private int faces = 6;
	private int currentFace = 1;

	public Dice(String name) {
		super(name);
	}
	
	public int getFaces() {
		return faces;
	}
	public int getValue() {
		return currentFace;
	}
	public void setValue(int value) {
		currentFace = value;
	}
}
