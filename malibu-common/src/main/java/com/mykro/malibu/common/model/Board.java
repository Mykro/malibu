package com.mykro.malibu.common.model;

import java.util.Vector;

/**
 * The arrangement and layout of properties.
 */
public class Board {

	private PropertyGroup jails;
	private Vector<PropertyGroup> groups = new Vector<PropertyGroup>();
	private PropertySequence props = new PropertySequence();

	public Board() {
	}
	public JailProperty getJailProperty() {
		return (JailProperty) getProperties().getNextInGroup(jails, null);
	}
	public PropertyGroup getJailGroup() {
		return jails;
	}
	public void setJailGroup(PropertyGroup group) {
		jails = group;
	}
	
	public BaseProperty getPropertyByName(String name) {
		return props.getPropertyByName(name);
	}
	
	public PropertySequence getProperties() {
		return props;
	}
	
	public PropertyGroup addGroup(String name) {
		PropertyGroup g = new PropertyGroup(name);
		groups.add(g);
		return g;
	}

	public PropertyGroup addGroupSafely(String name) {
		if ( name==null ) {
			return null;
		}
		PropertyGroup g = getGroupByName(name);
		if ( g==null) {
			g = addGroup(name);
		}
		return g;
	}
	
	public PropertyGroup getGroupByName(String name) {
		if ( name==null ) {
			return null;
		}
		for ( PropertyGroup group : groups ) {
			if ( group.getName().equals(name) ) {
				return group;
			}
		}
		return null;
	}
	
	public int getDistance( BaseProperty firstProperty, BaseProperty secondProperty) {
		Vector<BaseProperty> ahead = getProperties().getAllPropertiesAhead(firstProperty);
		return ahead.indexOf(secondProperty)+1;
	}

}
