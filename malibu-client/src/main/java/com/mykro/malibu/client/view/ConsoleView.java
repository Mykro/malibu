package com.mykro.malibu.client.view;


/**
 * Manages a Console connection.
 * A Console connection is not dynamic. It does not
 * display anything new until a prompt is required from
 * the user.  At this time it displays the latest model.
 */
/*
public class ConsoleView implements ClientView {

	private String lastDescription;
	private Vector<ActionHandler> actionHandlers = new Vector<ActionHandler>();
	
	public ConsoleView() {
	}

	public void refreshDisplay() {
		print(lastDescription);
		
	}
	public void modelUpdated(World world) {
		// generate a description of the world
		// we will use this when displaying a prompt
		WorldDescriber describer = new WorldDescriber();
		lastDescription = describer.describe(world).toString();
	}

	@Override
	public void registerActionHandler(ActionHandler actionHandler) {
		actionHandlers.add(actionHandler);
	}

	private void presentActions(Vector<ActionExecutor> actions) {
		print("You can:");
		int i=1;
		for ( ActionExecutor a : actions ) {
			print(i + " = " + a.getName());
			i++;
		}
	}

	public void promptUserAction(Vector<ActionExecutor> actions) {
		presentActions(actions);
		ActionExecutor selAction = getUserActionChoice(actions);
		print("");  // carriage return
		if ( selAction != null ) {
			print("Sending action: " + selAction.getName() );
			sendActionToHandlers( selAction );
		} else {
			print("Invalid action.");
		}
	}

	private void sendActionToHandlers(ActionExecutor action) {
		for( ActionHandler handler : actionHandlers ) {
			handler.handleAction(action);
		}
	}

	private ActionExecutor getUserActionChoice(Vector<ActionExecutor> actions) {
		ActionExecutor result = null;
		while ( result == null ) {
			System.out.print("Choose action:");
			String s ="";
			try {
				InputStreamReader sr = new InputStreamReader(System.in);
				BufferedReader br = new BufferedReader(sr);
				s = br.readLine();
			} catch (IOException e) {
				System.out.print("Bad action");
			}
			try {
				int v = Integer.valueOf(s);
				if ( v >= 1 && v <= actions.size()) {
					result = actions.get(v-1);
				}
			} catch (NumberFormatException e) {
				System.out.print("Bad action");
			}
		}
		return result;
	}

	private void print( String string) {
		System.out.println(string);
	}
	private void print( ArrayList<String> strings) {
		for ( String s : strings ) {
			System.out.println(s);
		}
	}


}
*/