package com.mykro.malibu.server.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mykro.malibu.server.controller.agent.Agent;

/**
 * A singleton that manages all Agents and Sessions
 */
public class Global {

	private List<Agent> agents = new ArrayList<Agent>();
	private List<Session> sessions = new ArrayList<Session>();
	private HashMap<String,Session> agentSessions = new HashMap<String,Session>();
	
	public Global() {
	}

	/**
	 * Add a new Agent to the Global system
	 * @param newAgent
	 * @return
	 */
	public boolean registerAgent(Agent newAgent) {
		for ( Agent a : agents ) {
			if (a.getName().equals(newAgent.getName())) {
				return false;
			}
		}
		agents.add(newAgent);
		return true;
	}

	/**
	 * Add a new Session to the Global system
	 * @param newSession
	 * @return
	 */
	public boolean registerSession(Session newSession) {
		sessions.add(newSession);
		return true;
	}
	
	/**
	 * Add an Agent to a Session.
	 * 
	 * Since an Agent can only belong to one session at a time, they 
	 * will automatically leave any current session.
	 * @param agent
	 * @param session
	 */
	public void setAgentSession(Agent agent,Session session) {
		Session oldSession = agentSessions.get(agent.getName());
		if ( oldSession.equals(session) ) {
			// already in this session
			return;
		}
		if ( oldSession != null ) {
			// exit the old session
			oldSession.removeAgentAndPlayer(agent);
		}
		agentSessions.put(agent.getName(),session);
		session.addAgentAndPlayer(agent);
	}
	
	public Session getAgentSession(Agent agent) {
		return agentSessions.get(agent.getName());
		
	}
	
}
