package com.mykro.malibu.server.controller.agent.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.mykro.malibu.common.controller.ActionExecutor;
import com.mykro.malibu.common.controller.ActionExecutorException;
import com.mykro.malibu.common.controller.ActionExecutorFactory;
import com.mykro.malibu.common.controller.PlayerActions;
import com.mykro.malibu.common.model.ActionMortgageProperty;
import com.mykro.malibu.common.model.Action;
import com.mykro.malibu.common.model.MarketProperty;
import com.mykro.malibu.common.model.Player;
import com.mykro.malibu.common.model.World;
import com.mykro.malibu.common.util.Debug;
import com.mykro.malibu.server.controller.agent.Agent;

/**
 * TODO: The "console loop" is currently driven by the engine's events.
	Like any client it should have its own thread and loop, and process the engine's incoming events
	asynchronously.  This keeps the client/console responsive even if the engine gets 
	bogged down in something or is waiting for another player.
 */

/**
 * TODO: A lot of this code should be moved to the client codebase.
 */

/**
 * An agent that interacts with the server's console (stdin/stdout).
 * 
 * This class combines View and Controller functions.
 * 
 * It is only intended for simple and quick testing.  
 * In reality users should be talking to us via a ClientAgent.
 */
public class ServerAgentConsole extends Agent implements Runnable {

	private String lastDescription;
	private ActionExecutorFactory caFactory = new ActionExecutorFactory();
	private World serverLastWorld;
	private List<Action> serverLastAvailableActions;
	private boolean serverLastAvailableActionsUpdated = false;

	public ServerAgentConsole(String name) {
		super(name);
		
		// initialise the Client Action Executor Factory
		caFactory.registerActionExecutor(ClientActionViewPlayer.class, 		ClientActionExecutorViewPlayer.class);
	}

	@Override
	public void run() {
		view_initialiseDisplay();
		runLoop();
	}
	
	private void runLoop() {
		while(true) {
			synchronized(this) {
				try {
					wait(1000);  // wait for a second, or until something wakes us up
					view_refreshDisplay();
				}	catch(InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	
	@Override
	public void handleEventPlayerActionsUpdate( World world, List<Action> availableActions ) {
		// we keep all the possible actions for all players.  
		// As we are the console "super admin" we can pick any of them.

		// store the information from the server
		serverLastWorld = world;
		serverLastAvailableActions = availableActions;
		serverLastAvailableActionsUpdated = true;
		
		// wake up the loop thread
		synchronized(this) {
            this.notify();
        }
	}

	
	@Override
	public void handleEventWorldUpdate(World world) {
		// store the information from the server
		serverLastWorld = world;

		// wake up the loop thread
        synchronized(this) {
            this.notify();
        }
	}

	private void view_initialiseDisplay() {
		print(lastDescription);
	}
	
	
	
	/**
	 * This gets called at intervals or whenever there is an update.
	 * This method is responsible for figuring out what has changed and updating the user.
	 */
	private synchronized void view_refreshDisplay() {
		// we received a server update
		if ( serverLastAvailableActionsUpdated ) {
			serverLastAvailableActionsUpdated = false;
			
			// start with the server actions
			PlayerActions pa = new PlayerActions();
			pa.addActions(serverLastAvailableActions);
			
			// append the client actions for each player
			for ( Player p : serverLastWorld.getPlayers() ) {
				pa.addActions(getPlayerClientActions(serverLastWorld, p));
			}

			// generate a quick description for the current player
			print("");
			WorldDescriber describer = new WorldDescriber();
			print(describer.describeUpdate(serverLastWorld).toString());

			// generate the options for all players, starting with the current player
			ActionOptions ao = new ActionOptions();
			for ( Player p : serverLastWorld.getPlayers().getAllFromCurrentOnwards((serverLastWorld.getCurrentPlayer() ) ) ) {
				ao.addActions(pa.getPlayerActions(p));
			}
			
			view_promptUserAction(ao);
		}

		// TODO: maybe put something in to present the user a new display if nothing has happened for a while?
	}
	
	private List<Action> getPlayerClientActions(World world, Player p) {
		List<Action> clientActions = new ArrayList<Action>();

		// view other players
		for ( Player other : world.getPlayers() ) {
			if ( !p.equals(other)) {
				includeActionIfInitiatable(clientActions, new ClientActionViewPlayer(p, other) );
			}
		}
		
		return clientActions;
	}
	
	
	/**
	 * Helper function that adds an Action to the list if it is initiatable.
	 * @param list
	 * @param p
	 * @param a
	 */
	private void includeActionIfInitiatable(List<Action> list, Action a) {
		ActionExecutor ae = caFactory.getActionExecutor(a);
		if ( ae != null && ae.isInitiatable(a) ) {
			list.add(a);
		}
	}

	@Override
	protected void publishPlayerAction(Action action) {
		if ( action instanceof ClientAction ) {
			performClientAction(action);
		} else {
			super.publishPlayerAction(action);
		}
	}

	private void performClientAction(Action action) {
		ActionExecutor ae = caFactory.getActionExecutor(action);
		if ( ae != null ) {
			try {
				ae.execute(action);
			} catch (ActionExecutorException aee) {
				print( "Exception: " + aee.getMessage() );
			}
		}
		// print output
		if ( action instanceof ClientAction ) {
			String display = ((ClientAction)action).getOutput();
			if ( display != null & display != "" ) {
				print(display);
			}
		}
	}



	private void view_presentActions(ActionOptions options) {
		print("You can:");
		for ( Integer i : options.keySet() ) {
			String d = "";
			Action a = options.get(i);
			d += "[as " + a.getPlayer().getName() + "] ";
			d += a.getDescription();
			print(i + " = " + d);
			i++;
		}
	}

	private void view_promptUserAction(ActionOptions options) {
		view_presentActions(options);
		Action selAction = view_getUserActionChoice(options);
		print("");  // carriage return
		
		// TODO: get additional data for certain actions.  Populate selAction.
		if ( selAction instanceof ActionMortgageProperty ) {
			List<MarketProperty> unmortgagedProperties = selAction.getPlayer().getProperties();
			PropertyOptions propertyOptions = new PropertyOptions();
			propertyOptions.addProperties(unmortgagedProperties);
			MarketProperty selectedProperty = view_getUserPropertyChoice(propertyOptions);
			((ActionMortgageProperty) selAction).setProperty(selectedProperty);
		}
		
		if ( selAction != null ) {
			Debug.debug("Submitting action: " + selAction.getDescription() + " ..." );
			publishPlayerAction(selAction);
		} else {
			print("Invalid action.");
		}
	}

	private Action view_getUserActionChoice(ActionOptions options) {
		Action result = null;
		while ( result == null ) {
			printNoCR("Choose action:");
			String s ="";
			try {
				InputStreamReader sr = new InputStreamReader(System.in);
				BufferedReader br = new BufferedReader(sr);
				s = br.readLine();
			} catch (IOException e) {
				print("Bad action");
			}
			try {
				int v = Integer.valueOf(s);
				result = options.get(v);
			} catch (NumberFormatException e) {
				print("Bad action");
			}
		}
		return result;
	}

	private MarketProperty view_getUserPropertyChoice(PropertyOptions options) {
		print("The properties you can mortgage are:");
		for ( Integer i : options.keySet() ) {
			String d = "";
			MarketProperty a = options.get(i);
			print(i + " = " + a.getName() + "(for $" + a.calculateMortgageWorth() + ")");
			i++;
		}
		
		MarketProperty result = null;
		while ( result == null ) {
			printNoCR("Choose property:");
			String s ="";
			try {
				InputStreamReader sr = new InputStreamReader(System.in);
				BufferedReader br = new BufferedReader(sr);
				s = br.readLine();
			} catch (IOException e) {
				print("Bad selection");
			}
			try {
				int v = Integer.valueOf(s);
				result = options.get(v);
			} catch (NumberFormatException e) {
				print("Bad selection");
			}
		}
		return result;
	}

	
	private void printNoCR( String string ) {
		System.out.print(string);
	}
	private void print( String string ) {
		System.out.println(string);
	}
	private void print( ArrayList<String> strings ) {
		for ( String s : strings ) {
			System.out.println(s);
		}
	}


}
