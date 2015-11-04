package com.mykro.malibu.common.model;

import java.util.LinkedList;
import java.util.Vector;

/**
 * The sequence or loop of properties that forms the board.
 */
public class PropertySequence {

	private LinkedList<BaseProperty> properties = new LinkedList<BaseProperty>();
	
	public BaseProperty getNext(BaseProperty current) {
		if ( current == null ) {
			return properties.getFirst();
		}
		BaseProperty next = null;
		int i = properties.indexOf(current); 
		if ( i >= 0 ) {
			if ( i >= properties.size()-1 ) {
				next = properties.get(0);
			} else {
				next = properties.get(i+1);
			}
		}
		return next;
	}

	public BaseProperty getNextInGroup( PropertyGroup group, BaseProperty current ) {
		Vector<BaseProperty> props = getAllPropertiesAhead(current);
		for ( BaseProperty p : props ) {
			if ( group.properties.contains(p)) {
				return p;  // found it
			}
		}
		return null;  // not found
	}

	/**
	 * Get a collection of all properties starting from after the current one, and looping
	 * around until the current one again.
	 * @param current
	 * @return
	 */
	public Vector<BaseProperty> getAllPropertiesAhead( BaseProperty current ) {
		Vector<BaseProperty> props = new Vector<BaseProperty>();
		BaseProperty next = getNext(current);
		BaseProperty first = next;
		while ( next != null ) {
			props.add(next);
			next = getNext(next);
			if ( next == first ) {
				return props; // looped around
			}
		}
		return props;  // reached the end (not a loop)
	}
	
	public BaseProperty getPropertyByName(String name) {
		for ( BaseProperty p : properties) {
			if ( p.getName().equals(name)) {
				return p;
			}
		}
		return null;
	}

	public BaseProperty getPropertyByPosition(int position) {
		return properties.get(position);
	}

	public void add( BaseProperty property ) {
		properties.add(property);
	}
	
	public void clear() {
		properties.clear();
	}
	public int size() {
		return properties.size();
	}
}
