package com.mykro.malibu.server.controller.engine;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mykro.malibu.common.controller.ActionExecutor;
import com.mykro.malibu.common.controller.ActionExecutorFactory;
import com.mykro.malibu.common.model.ActionEndTurn;
import com.mykro.malibu.common.model.ActionRollDice;
import com.mykro.malibu.common.model.Player;
import com.mykro.malibu.common.model.World;
import com.mykro.malibu.common.model.World.WorldState;
import com.mykro.malibu.server.controller.Session;

/**
 * Unit test for simple App.
 */
public class EngineActionsTest 
{
	private Session s;

	@Before
	public void setUp() {
		s = new Session();
	}
	
	
    /**
     * Simple test to retrieve the executor for an action.
     */
	@Test
    public void testActionExecutorEndTurn()
    {
    	Player p = s.getWorld().addPlayer("Frank");
    	ActionExecutorFactory aef = s.getEngine_test().getActionExecutorFactory_test();
    	
    	// create the End Turn action
    	ActionEndTurn a = new ActionEndTurn(p);
    	
    	// obtain the right Executor for it
    	ActionExecutor ae = aef.getActionExecutor(a);
    	
    	Assert.assertNotNull(ae);
    	Assert.assertTrue(ae instanceof ActionExecutorEndTurn);
    }

    /**
     * Place a player on the board and roll the dice (randomly)
     */
	@Test
    public void testPlayerRollDice()
    {
		World w = s.getWorld();
    	Player p = w.addPlayer("Frank");
    	w.getCurrentPlayer().setCurrentProperty(
    			s.getWorld().getBoard().getPropertyByName("Free Parking"));

    	ActionRollDice a = new ActionRollDice(p);
    	s.getEngine_test().submitAction(a);
    	s.getEngine_test().performEngineTick();
    }

    /**
     * Start 2 players on the board and roll to specific locations
     */
	@Test
    public void testTwoPlayersRollOut()
    {
		World w = s.getWorld();
    	Player p = w.addPlayer("Frank");
    	Player p2 = w.addPlayer("Tank");
    	w.addFutureDiceRoll(1,1);  	
    	w.addFutureDiceRoll(2,2);  	
    	ActionRollDice a11 = new ActionRollDice(p);
    	s.getEngine_test().submitAction(a11);
    	s.getEngine_test().performEngineTick();

    	ActionEndTurn a12 = new ActionEndTurn(p);
    	s.getEngine_test().submitAction(a12);
    	s.getEngine_test().performEngineTick();
    	
       	ActionRollDice a21 = new ActionRollDice(p2);
        s.getEngine_test().submitAction(a21);
    	s.getEngine_test().performEngineTick();
    }
	
    /**
     * Place a player on the board and roll a 5 + 5
     */
	@Test
    public void testPlayerRollDice10()
    {
		World w = s.getWorld();
    	Player p = w.addPlayer("Frank");
    	w.getCurrentPlayer().setCurrentProperty(
    			s.getWorld().getBoard().getPropertyByName("Free Parking"));
    	w.addFutureDiceRoll(5, 5);  	
    	ActionRollDice a = new ActionRollDice(p);
    	s.getEngine_test().submitAction(a);
    	s.getEngine_test().performEngineTick();
    }
	
    /**
     * Legacy Test.
     * Place a player on the board and advance them with transitions.
     * NB: this should properly be done with Actions.
     */
	@Test
    public void testPlayerAdvanceWithTransitionsFromFreeParkingToGoToJail() throws TransitionException
    {
		World w = s.getWorld();
    	Player p = w.addPlayer("Frank");
    	w.getCurrentPlayer().setCurrentProperty(
    			s.getWorld().getBoard().getPropertyByName("Free Parking"));
    	w.getDice().setValue(0, 5);
		w.getDice().setValue(1, 5);
		w.setState(WorldState.PLAYER_ADVANCING);
		w.getCurrentTurn().setAdvancingPositions(w.getDice().getValue());
		TransitionPlayerAdvance t2 = new TransitionPlayerAdvance(p, w.getDice().getValue());
		t2.execute(w);
		TransitionPlayerLanding t3 = new TransitionPlayerLanding(p, p.getCurrentProperty());
		t3.execute(w);	
    }

		
	
}
