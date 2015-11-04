package com.mykro.malibu.common.model;

/**
 * A property that has an associated card pile
 */
public class CardPileProperty extends BaseProperty {

	CardDeck cards;
	
	public CardPileProperty(String name, CardDeck cards) {
		super(name);
		this.cards = cards;
	}

	public CardDeck getCards() {
		return cards;
	}
	
	@Override
	public void playerPasses(Player player) {
	}

	@Override
	public void playerArrives(Player player) {
		Card card = cards.drawCard();
	}

}
