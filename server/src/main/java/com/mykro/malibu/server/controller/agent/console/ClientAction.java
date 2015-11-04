package com.mykro.malibu.server.controller.agent.console;

/**
 * Actions initiated on the client-side that have no impact on the server.
 * 
 * For example, refreshing or reprinting world data.
 */
public interface ClientAction {
	public String getOutput();
}
