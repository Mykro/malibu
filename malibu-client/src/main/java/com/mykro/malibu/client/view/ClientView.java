package com.mykro.malibu.client.view;

import com.mykro.malibu.common.model.World;

/**
 * Manages a ClientView 
 * Needs to:
 * - Publish model updates to the Agent
 * - Receive unprompted inputs from the Agent
 * - Prompt for input from the Agent
 */
public interface ClientView {

	/**
	 * Pass a model update to the view
	 * @param model
	 */
	public void modelUpdated(World model);

//	public void registerActionHandler(ActionHandler actionHandler);
}
