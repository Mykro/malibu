package com.mykro.malibu.server.controller.agent.console;

import java.util.HashMap;
import java.util.List;

import com.mykro.malibu.common.model.MarketProperty;

/**
 * Collects the actions available along with an option identifer.
 */
public class PropertyOptions extends HashMap<Integer,MarketProperty> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3092584771502351295L;
	private Integer nextID = 1;

	public void addProperties(List<MarketProperty> props) {
		if ( props == null ) {
			return;
		}
		for (MarketProperty a : props) {
			addProperty(a);
		}
	}
	
	public void addProperty(MarketProperty a) {
		this.put(nextID, a);
		nextID++;
	}

	
}
