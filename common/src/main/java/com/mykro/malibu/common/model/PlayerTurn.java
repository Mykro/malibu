package com.mykro.malibu.common.model;

/**
 * Captures the state of the player's turn
 */
public class PlayerTurn extends BaseObject {

	private int rollsThrown = 0;
	private boolean rollRequired = true;
	private int advancingPositions = 0;

	public PlayerTurn() {
		super("");
	}
	public int getRollsThrown() {
		return rollsThrown;
	}
	public void reset() {
		rollsThrown = 0;
		rollRequired = true;
	}
	public void incrementRollsThrown() {
		rollsThrown++;
	}
	public void setRollNotRequired() {
		rollRequired = false;
	}
	public boolean isRollRequired() {
		return rollRequired;
	}
	public int getAdvancingPositions() {
		return advancingPositions;
	}
	public void setAdvancingPositions(int p) {
		this.advancingPositions = p;
	}
}
