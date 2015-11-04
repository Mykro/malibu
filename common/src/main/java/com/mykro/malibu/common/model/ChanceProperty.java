package com.mykro.malibu.common.model;

/**
 * A property that represents the Chance cards.
 */
public class ChanceProperty extends BaseProperty {

	private CardDeck cards;
	
	public ChanceProperty(String name, CardDeck cards) {
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
