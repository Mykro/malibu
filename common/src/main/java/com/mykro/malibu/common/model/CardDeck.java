package com.mykro.malibu.common.model;

import java.util.LinkedList;

/**
 * A rotating deck of cards.
 * 
 * TODO: transfer cards between decks.
 */
public class CardDeck extends LinkedList<Card> {

	private static final long serialVersionUID = -761685352327986827L;

	/**
	 * Draw a card and at the same time return it to the bottom of the deck
	 * @return
	 */
	public Card drawCard() {
		Card drawn = pop();
		while ( !drawn.inDeck()) {
			push(drawn);
			drawn = pop();
		}
		push(drawn);
		return drawn;
	}
	
	/**
	 * A card that is deployed to the possession of
	 * a Player is taken out of the
	 * rotation of the deck, until it is returned.
	 * @param card
	 */
	public boolean giveCardToOwner(Card card, Player owner) {
		if ( contains(card)) {
			card.setOwner(owner);
			return true;
		}
		return false;
	}
	
}
