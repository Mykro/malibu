package com.mykro.malibu.common.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The rotation of players
 */
public class PlayerSequence extends LinkedList<Player> {

	private static final long serialVersionUID = -6163828958071589407L;

	public Player getNext(Player current) {
		if ( current == null ) {
			return getFirst();
		}
		Player next = null;
		int i = indexOf(current); 
		if ( i >= 0 ) {
			if ( i >= size()-1 ) {
				next = get(0);
			} else {
				next = get(i+1);
			}
		}
		return next;
	}
	
	public List<Player> getAllFromCurrentOnwards(Player current) {
		if ( current == null ) {
			current = getFirst();
		}
		List<Player> sorted = new ArrayList<Player>();
		sorted.add(current);

		Player next = getNext(current);
		while ( next != current ) {
			sorted.add(next);
			next = getNext(next);
		}
		return sorted;
	}
	
}
