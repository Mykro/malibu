package com.mykro.malibu.common.util;

/**
 * Print debug messages - currently to stdout.
 */
public class Debug {

	private static boolean DEBUG_ENABLED = false;
	
	public static void debug( String s ) {
		if ( DEBUG_ENABLED ) {
			System.out.println("[debug] " + s );
		}
	}
}
