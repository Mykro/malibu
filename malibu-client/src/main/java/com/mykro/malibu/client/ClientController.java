package com.mykro.malibu.client;

import java.util.Vector;

import com.mykro.malibu.common.controller.ActionExecutor;
import com.mykro.malibu.common.model.World;

/**
 * Manages a ClientController that talks to the Controller
 * Needs to:
 * - Publish model updates to the view
 * - Receive unprompted inputs from the Agent
 * - Prompt for input from the Agent
 */
public interface ClientController {

	/**
	 * Pass a model update to the view
	 * @param model
	 */
	public void modelUpdated(World model);

	public void promptForUserAction(World model, Vector<ActionExecutor> availableActions);

//	public void registerActionHandler(ActionHandler actionHandler);
}
