package com.mykro.malibu.common.model;

/**
 * An Action that sell a single House on a property
 * (or elsewhere in that property group if current buildings
 *  don't permit selling a house on that property)
 */
public class ActionSellHouse extends Action implements Actionable {

	private StreetProperty property;
	
	public ActionSellHouse(Player p, StreetProperty pr) {
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
