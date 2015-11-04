package com.mykro.malibu.server;

import com.mykro.malibu.server.controller.Global;
import com.mykro.malibu.server.controller.Session;
import com.mykro.malibu.server.controller.agent.console.ServerAgentConsole;
import com.mykro.malibu.server.controller.io.CommServer;

/**
 * Main class for the malibu server.
 */
public class App 
{
	public static void main( String[] args )
    {
		System.out.println( "Starting malibu-server." );
		new App().execute();
    }
	
	private static int COMMUNICATION_PORT = 8080;
	
	public void execute() {
        int port = COMMUNICATION_PORT;

        // start the communications server in a thread
        try {
        	CommServer c = new CommServer(port);
        	Thread t = new Thread(c);
        	t.start();
        } catch ( Exception e ) {
            System.err.println( e.getMessage() );
            return;
        }

        // set up the Global space
        Global g = new Global();
        
        // start the console agent in a thread
        ServerAgentConsole sac;
        try {
        	sac = new ServerAgentConsole("console");
        	Thread t2 = new Thread(sac);
        	t2.start();
        } catch ( Exception e ) {
            System.err.println( e.getMessage() );
            return;
        }
        g.registerAgent(sac);

        // TODO: make a lobby so the players can join before a game starts
        
        // automatically start a simple Session
        // the console can control all players but gets assigned one
        Session s = new Session();
        g.registerSession(s);
        s.addAgent(sac);
        s.addPlayer("Player1");
        s.addPlayer("Player2");

        // launch the default session
    	Thread t3 = new Thread(s);
    	t3.start();
        
        // main loop - wait for explicit application kill
        while ( true ) {
        	try {
            	Thread.sleep(5);
        	} catch ( InterruptedException ie ) {
        		//
        	}
        }
		
	}
}
