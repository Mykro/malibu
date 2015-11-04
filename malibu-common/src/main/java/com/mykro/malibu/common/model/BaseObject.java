package com.mykro.malibu.common.model;

/**
 * A core object
 * @author daniel
 *
 */
public class BaseObject extends Object {
	 
	private static int globalIDCounter=1;
	
 	private static int getNextID() {
 		int n = globalIDCounter;
 		globalIDCounter++;
 		return n;
 	}
 	
 	private int ID;
 	private String name;

	public BaseObject() {
		this.ID = getNextID();
	}
	
	public BaseObject(String name) {
		this.ID = getNextID();
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public int getID() {
		return ID;
	}


}
