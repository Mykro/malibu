package com.mykro.malibu.server.controller.engine;

import com.mykro.malibu.common.controller.InstructionExecutor;
import com.mykro.malibu.common.controller.InstructionExecutorException;
import com.mykro.malibu.common.controller.InstructionExecutorFactory;
import com.mykro.malibu.common.model.Card;
import com.mykro.malibu.common.model.CardDeck;
import com.mykro.malibu.common.model.Instruction;
import com.mykro.malibu.common.model.Player;
import com.mykro.malibu.common.model.World;
import com.mykro.malibu.common.model.World.WorldState;

/**
 * A Transition that draws a card for the Player 
 */
public class TransitionPlayerDrawCard extends Transition {

	private Player player;
	private CardDeck cardpile;
	
	public TransitionPlayerDrawCard( Player player, CardDeck cardpile ) {
		this.player = player;
		this.cardpile = cardpile;
	}
	
	@Override
	public World execute(World world) throws TransitionException {

		//----- VALIDATIONS -----
		
		if (player == null) {
			throw new TransitionException(this, world, "Player not specified.");
		}
		if (cardpile == null) {
			throw new TransitionException(this, world, "Card Deck not specified.");
		}
		if (!world.getPlayers().contains(player)) {
			throw new TransitionException(this, world, "Player is not in this world.");
		}
		//TODO: validate that card deck is in the world
		//TODO: validate that card deck has a card to draw
		
		//----- EXECUTION -----
		
		Card newCard = cardpile.drawCard();
		for ( Instruction i : newCard.getInstructions()) {
			// HACK: we don't have a good way to access the Engine here, so we share a global instance with the Engine.
			InstructionExecutor ie = InstructionExecutorFactory.getGlobalInstance().getInstructionExecutor(i);
			if ( ie != null ) {
				try {
					ie.execute(world, i);
				} catch (InstructionExecutorException iee) {
					// report it
				}
			}
		}
		
		world.setState(WorldState.PLAYER_STEADY);
		return world;
	}



}
