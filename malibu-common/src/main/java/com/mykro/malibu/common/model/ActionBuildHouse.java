package com.mykro.malibu.common.model;

/**
 * An Action that buys a single House on a property
 * (or elsewhere in that property group if current buildings
 *  don't permit buying a house on that property)
 */
public class ActionBuildHouse extends Action implements Actionable {

	private StreetProperty property;
	
	public ActionBuildHouse(Player p, StreetProperty pr) {
		super(p);
		this.property = pr;
	}

	public StreetProperty getProperty() {
		return property;
	}

	@Override
	public String getDescription() {
		return "Build a House on '" + property.getName() + "'.";
	}
}
