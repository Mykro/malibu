package com.mykro.malibu.common.model;

/**
 * A property that represents the Community Chest cards
 */
public class CommunityChestProperty extends BaseProperty {

	CardDeck cards;
	
	public CommunityChestProperty(String name, CardDeck cards) {
		super(name);
		this.cards = cards;
	}

	@Override
	public void playerPasses(Player player) {
	}

	@Override
	public void playerArrives(Player player) {
		Card card = cards.drawCard();
	}

}
