package com.mykro.malibu.client;


/**
 * Manages a Console connection.
 * A Console connection is not dynamic. It does not
 * display anything new until a prompt is required from
 * the user.  At this time it displays the latest model.
 */
/*
public class ConsoleController implements ClientController {

	private ConsoleView view = new ConsoleView();
	public boolean stopRun = false;
	private Player perspectivePlayer;
	private Vector<ActionExecutor> promptActions = null;
	
	public ConsoleController() {
	}

	public void run() {
		while (!stopRun) {
			if ( promptActions != null ) {
				view.refreshDisplay();
				view.promptUserAction(promptActions);
				promptActions = null;
			}
		}
	}

	// TODO:
	// Player Prompts need to be embedded in the model
	// So we can pick it out in the modelUpdated event
	public void modelUpdated(World world) {
		view.modelUpdated(world);
	}

	@Override
	public void promptForUserAction(World model, Vector<ActionExecutor> availableActions) {
		// do we care about this player?
		boolean thisPlayer = perspectivePlayer.equals(model.getCurrentPlayer());
		if ( thisPlayer) {
			promptActions = availableActions;
		}
	}

	@Override
	public void registerActionHandler(ActionHandler actionHandler) {
		view.registerActionHandler(actionHandler);
	}


}
*/
