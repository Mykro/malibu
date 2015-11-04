package com.mykro.malibu.server.controller.engine;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import com.mykro.malibu.common.controller.ActionExecutor;
import com.mykro.malibu.common.controller.ActionExecutorException;
import com.mykro.malibu.common.controller.ActionExecutorFactory;
import com.mykro.malibu.common.controller.InstructionExecutorFactory;
import com.mykro.malibu.common.model.*;
import com.mykro.malibu.common.util.Debug;

/**
 * The Engine provides an external interface to:
 * 1) request a Player's view of the model (in whole or part)
 * 3) execute an Action to manipulate the world
 * 2) notify a Player that the model has changed
 * 3) notify a Player that an Action is required
 * 
 * The Players use Actions to manipulate the world.  Each Player can at any time
 * request a list of actions available to them.
 * 
 * The Engine processes Actions sequentially. For each Action it finds the right ActionExecutor 
 * which will first validate that the action can in fact be executed, and then it can 
 * execute the action.
 * 
 * Incoming actions are queued.  The engine must complete the current Action and all of 
 * its Transitions before it can process the next one.  This ensures that the world remains 
 * in a known state. This also means that Actions in themselves cant take a long time, they
 * must execute quickly.  
 *
 * The Engine never truly "blocks" waiting for an action.  At times it may seem the Engine
 * is waiting for a single player to do something, but in reality there are out-of-turn Actions 
 * available to other players eg. quit, trade, mortgage.
 */
public class Engine {

	private World world;
	
	// handlers that register to receive whole world updates
	private List<EventWorldUpdate> worldUpdateHandlers = new ArrayList<EventWorldUpdate>();

	// handlers that register to receive action updates
	private List<EventPlayerActionsUpdate> playerActionsUpdateHandlers = new ArrayList<EventPlayerActionsUpdate>();

	private ActionExecutorFactory aeFactory = new ActionExecutorFactory();
	private InstructionExecutorFactory ieFactory = InstructionExecutorFactory.getGlobalInstance();
	private LinkedList<Action> actionQueue = new LinkedList<Action>();

	public Engine(World model) {
		this.world = model;
		
		// initialise the Action Executor Factory
		aeFactory.registerActionExecutor(ActionBuyProperty.class, 		ActionExecutorBuyProperty.class);
		aeFactory.registerActionExecutor(ActionEndTurn.class, 			ActionExecutorEndTurn.class);
		aeFactory.registerActionExecutor(ActionMortgageProperty.class, 	ActionExecutorMortgageProperty.class);
		aeFactory.registerActionExecutor(ActionPayJailFee.class, 		ActionExecutorPayJailFee.class);
		aeFactory.registerActionExecutor(ActionQuit.class, 				ActionExecutorQuit.class);
		aeFactory.registerActionExecutor(ActionRollDice.class, 			ActionExecutorRollDice.class);
		aeFactory.registerActionExecutor(ActionRollDiceToEscape.class,  ActionExecutorRollDiceToEscape.class);
		aeFactory.registerActionExecutor(ActionUnmortgageProperty.class, ActionExecutorUnmortgageProperty.class);
		
		// initialise the Instruction Executor Factory
		ieFactory.registerInstructionExecutor(InstructionAdvanceToProperty.class, 	InstructionExecutorAdvance.class);
		
	}

	protected World getModel() {
		return world;
	}

	/** Used only for test functions */
	protected ActionExecutorFactory getActionExecutorFactory_test() {
		return aeFactory;
	}
	public void registerWorldUpdateHandler( EventWorldUpdate wuh ) {
		worldUpdateHandlers.add(wuh);
	}
	public void unregisterWorldUpdateHandler( EventWorldUpdate wuh ) {
		worldUpdateHandlers.remove(wuh);
	}

	private void publishWorldUpdated() {
		for ( EventWorldUpdate wuh : worldUpdateHandlers ) {
			wuh.handleEventWorldUpdate(world);
		}
	}

	public void registerPlayerActionsUpdateHandler( EventPlayerActionsUpdate wrah ) {
		playerActionsUpdateHandlers.add(wrah);
	}
	public void unregisterPlayerActionsUpdateHandler( EventPlayerActionsUpdate wrah ) {
		playerActionsUpdateHandlers.remove(wrah);
	}

	private void print( String string) {
		System.out.println(string);
	}
	private void print( ArrayList<String> strings) {
		for ( String s : strings ) {
			System.out.println(s);
		}
	}

	/**
	 * This method is synchronized as an added layer of protection to ensure we 
	 * don't have multiple threads accessing the queue or using the same ActionExecutor.
	 */
	private synchronized void processNextActionInQueue() {
		try {
			Action a = actionQueue.removeLast();
			ActionExecutor ae = aeFactory.getActionExecutor(a);
			if ( ae != null ) {
				if ( ae.isExecutable(a) ) {
					print("Executing action: " + a.getDescription() );
					try {
						ae.execute(a);
					} catch ( ActionExecutorException aee ) {
						print("Failed to execute action:" + aee.getMessage() );
					}
				} else {
					print("Cannot execute action:" + a.getDescription() );
				}
			}
		} catch ( NoSuchElementException nsee ) {
			// no Actions pending, do nothing
		}
	}


	/**
	 * Generate a list of available Actions for the Player.
	 * 
	 * This is done by iterating through the ActionExecutors and creating new Action objects.
	 * Those that can't be executed at this time are discarded.
	 * 
	 * Synchronized to prevent multiple threads from getting slightly different lists.
	 * 
	 * @param player
	 * @return
	 */
	private synchronized List<Action> createPlayerActions(Player player) {

		ArrayList<Action> list = new ArrayList<Action>();

		includeActionIfInitiatable(list, new ActionRollDice(player));
		if ( player.getCurrentProperty() instanceof MarketProperty ) {
			includeActionIfInitiatable(list, new ActionBuyProperty(player, (MarketProperty) player.getCurrentProperty()));
			//TODO add more
		}
		includeActionIfInitiatable(list, new ActionPayJailFee(player));
		includeActionIfInitiatable(list, new ActionMortgageProperty(player));
		includeActionIfInitiatable(list, new ActionUnmortgageProperty(player));
		includeActionIfInitiatable(list, new ActionEndTurn(player));
		includeActionIfInitiatable(list, new ActionQuit(player));
		return list;
	}

	private void publishAllPlayerActions() {
		List<Action> allPlayerActions = new ArrayList<Action>();
		for ( Player p : world.getPlayers() ) {
			allPlayerActions.addAll( createPlayerActions(p) );
		}
		// send to handlers
		for ( EventPlayerActionsUpdate wrah : playerActionsUpdateHandlers ) {
			wrah.handleEventPlayerActionsUpdate(world, allPlayerActions);
		}
	}
	
	/**
	 * Run the main loop for the Engine.
	 */
	/*
	public void runLoop() {
		while ( world.getState() != WorldState.WORLD_GAME_OVER ) {
			performEngineTick();
		}
	}
*/
	public void performEngineTick() {
		Debug.debug( "engine: beginning tick." );
		publishWorldUpdated();
		processNextActionInQueue();
		publishAllPlayerActions();
		Debug.debug( "engine: ending tick." );
	}

	public List<Action> requestPlayerActions(Player player) {
		return createPlayerActions(player);
	}

	/**
	 * Submit an action to the Engine.
	 * @param a
	 */
	public void submitAction( Action a ) {
		Debug.debug( "engine: action added to queue." );
		actionQueue.push(a);
	}

	/**
	 * Helper function that adds an Action to the list if it is executable.
	 * @param list
	 * @param p
	 * @param a
	 */
	private void includeActionIfInitiatable(List<Action> list, Action a) {
		ActionExecutor ae = aeFactory.getActionExecutor(a);
		if ( ae.isInitiatable(a) ) {
			list.add(a);
		}
	}

	
}
