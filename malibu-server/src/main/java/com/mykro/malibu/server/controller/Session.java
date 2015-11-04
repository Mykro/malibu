package com.mykro.malibu.server.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mykro.malibu.common.model.ActionQuit;
import com.mykro.malibu.common.model.Action;
import com.mykro.malibu.common.model.Player;
import com.mykro.malibu.common.model.World;
import com.mykro.malibu.common.model.World.WorldState;
import com.mykro.malibu.server.controller.agent.Agent;
import com.mykro.malibu.server.controller.agent.EventAgentAction;
import com.mykro.malibu.server.controller.engine.Engine;
import com.mykro.malibu.server.controller.engine.EventPlayerActionsUpdate;
import com.mykro.malibu.server.controller.engine.EventWorldUpdate;

/**
 * An instance where a World, an Engine and a set of Agents are live and interacting.
 */
public class Session implements Runnable, 
	EventWorldUpdate, EventPlayerActionsUpdate, EventAgentAction {

	private World world = new World();
	private WorldBuilder builder;
	private Engine engine;
	
	// Each agent (external entity) joins to a player in the world (internal entity).
	private List<Agent> agents = new ArrayList<Agent>();
	private HashMap<Agent,Player> agentPlayers = new HashMap<Agent,Player>();

	//TODO: alternatively, a single mapping that allows an agent to control multiple players or more than one agent to control a player
	private HashMap<Agent,List<Player>> agentsAndPlayers = new HashMap<Agent,List<Player>>();
	
	public Session() {
		builder = new WorldBuilderInternal();
		builder.initialiseEverything(world);
		engine = new Engine(world);
	}


	/**
	 * Convenience function to add a new Agent and automatically give them a new Player that has their name.
	 * @param agent
	 */
	public void addAgentAndPlayer(Agent agent) {
		if (!agents.contains(agent)) {
			// first add their player
			Player p = addPlayer(agent.getName());
			agentPlayers.put(agent, p);
			// add them as a listener
			addAgent(agent);
		}
	}

	public Player addPlayer(String name) {
		Player p = world.addPlayer(name);
		builder.initialisePlayer(world, p);
		return p;
	}

	/**
	 * Add a new Agent that can listen to the world.
	 * @param agent
	 */
	public void addAgent(Agent agent) {
		if (!agents.contains(agent)) {
			agents.add(agent);
			// the agent wants to handle the events coming from the engine
			engine.registerWorldUpdateHandler(agent);
			engine.registerPlayerActionsUpdateHandler(agent);
			// we (the session) want to handle actions coming from the agent
			agent.registerPlayerActionHandler(this);
		}
	}

	/**
	 * The Agent leaves and takes their attached player with them.
	 * @param agent
	 */
	public void removeAgentAndPlayer(Agent agent) {
		if ( agents.remove(agent) ) {
			Player p = agentPlayers.get(agent);
			if ( p != null ) {
				// tell the engine to execute the player's Quit action (which removes the Player from the world)
				engine.submitAction(new ActionQuit(p));
			}
			// finally, detach the Agent from their Player
			detachAgent(agent);
			// the agent no longer wants to handle the events coming from the engine
			engine.unregisterWorldUpdateHandler(agent);
			engine.unregisterPlayerActionsUpdateHandler(agent);
			// we (the session) no longer want to handle actions coming from the agent
			agent.unregisterPlayerActionHandler(this);
		}
	}
	
	/**
	 * Attach this Agent to an existing Player (if it doesn't have an Agent).
	 * The idea is that attach/detach can be used to "swap" Agents in and out while Players are unaffected.
	 * Note that the Player's name will not automatically change.
	 */
	public void attachAgent(Agent agent, Player p) {
		if ( agentPlayers.get(agent)==null) {
			agentPlayers.put(agent, p);
		}
	}
	
	/**
	 * Detach this Agent from their current Player.  If the Player is still in the world, 
	 * this will leave that Player alive but "uncontrolled".
	 * @param agent
	 * @return
	 */
	public Player detachAgent(Agent agent) {
		Player p = agentPlayers.get(agent);
		if ( p !=null ) {
			agentPlayers.put(agent, null);
			return p;
		}
		return null;
	}


	/** Used only for test functions */
	public Engine getEngine_test() {
		return engine;
	}

	public World getWorld() {
		return world;
	}
	
	private boolean isUnderway() {
		return world.getState() != WorldState.WORLD_GAME_OVER;
	}
	
	@Override
	public void run() {
		engine.registerWorldUpdateHandler(this);
		engine.registerPlayerActionsUpdateHandler(this);
		runLoop();
	}

	private void runLoop() {
		while(isUnderway()) {
			synchronized(this) {
				try {
					wait(1000);  // wait for a second, or until something wakes us up
					engine.performEngineTick();
				}	catch(InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}	
	
	@Override
	public void handleEventWorldUpdate(World world) {
		// TODO: any global functionality at the Session level.  Note that Agents have their own handlers.
	}

	@Override
	public void handleEventPlayerActionsUpdate( World world, List<Action> availableActions ) {
		// TODO: any global functionality at the Session level.  Note that Agents have their own handlers.
		// TODO: security issue? should we only publish each agent's actions to them only?
		
	}

	/**
	 * An Action has come from an Agent.  We push it into the Engine.
	 */
	@Override
	public void handleEventAgentAction(Agent agent, Action action) {
		// TODO: verify the agent is "authorised" to execute this action
		engine.submitAction(action);
		
		// TODO: handle response to Action, ie. callback... direct it back to the Agent.
	}
	
}
