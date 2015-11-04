package com.mykro.malibu.server.controller.engine;

import com.mykro.malibu.common.model.JailProperty;
import com.mykro.malibu.common.model.World;
import com.mykro.malibu.common.model.World.WorldState;

/**
 * An Transition that rolls the Dice in the world to escape jail
 */
public class TransitionRollDiceInJail extends Transition {

	@Override
	public World execute(World world) throws TransitionException {

		if ( world.getCurrentPlayer().isInJail() ) {
			TransitionRollDice t1 = new TransitionRollDice();
			t1.execute(world);

			if ( !(world.getCurrentPlayer().getCurrentProperty() instanceof JailProperty) ) {
				describe( "Not a Jail property!");
				return world;
			}
			JailProperty jp = (JailProperty) world.getCurrentPlayer().getCurrentProperty();
			
			if ( !world.isDiceDoubles()) {
				// failed to escape
				describe( world.getCurrentPlayer().getName() + 
						" did not escape from " + jp.getJailName() +".");
				world.setState(WorldState.PLAYER_STEADY);
				world.getCurrentTurn().setRollNotRequired();
				return world;
			} else {
				describe( world.getCurrentPlayer().getName() + 
						" escaped from " + jp.getJailName() +".");
				jp.freePlayer(world.getCurrentPlayer());
				world.setState(WorldState.PLAYER_STEADY);
				world.getCurrentTurn().setRollNotRequired();
				return world;
				
				// TODO: they move out of jail that number of spaces.
			}
		}
		return world;
	}
}
