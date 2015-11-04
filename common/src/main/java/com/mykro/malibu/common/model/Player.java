package com.mykro.malibu.common.model;

import java.util.Vector;


/**
 * A decision-maker. Can be a player or AI.  Possibly even the bank.
 * @author daniel
 *
 */
public class Player extends BaseObject {
	private Token token;
	private World board;
	private int cash;
	private BaseProperty location;
	private Vector<MarketProperty> properties = new Vector<MarketProperty>();
	private Vector<Card> cards = new Vector<Card>();
	private int rollsToEscapeJail = 0;

	public Player(World board, String name) {
		super(name);
		this.board = board;
	}
	public World getWorld() {
		return board;
	}
	public int getCash() {
		return cash;
	}
	public void setCash(int cash) {
		this.cash = cash;
	}
	public BaseProperty getCurrentProperty() {
		return location;
	}
	public void setToken(Token t) {
		token = t;
		// token.setOwner(this);
	}
	public Token getToken() {
		return token;
	}
	public void setCurrentProperty(BaseProperty location) {
		this.location = location;
	}
	public void setRollsToEscape(int i){
		rollsToEscapeJail = i;
	}
	
	public boolean hasCash(int amount) {
		return ( cash >= amount );
	}
	
	public boolean adjustCash(int delta) {
		if ( cash + delta >= 0 ) {
			cash = cash + delta;
			return true;
		}
		return false;
	}
	
	public boolean addProperty( MarketProperty property ) {
		if (properties.contains(property)) {
			return false;
		}
		properties.add( property );
		property.setOwner(this);
		return true;
	}

	public boolean removeProperty( MarketProperty property ) {
		if (!properties.contains(property)) {
			return false;
		}
		properties.remove( property );
		property.setOwner(null);
		return true;
	}

	public boolean isCurrentPropertyPurchasable() {
		if ( ( getCurrentProperty() != null ) && 
			 ( getCurrentProperty() instanceof MarketProperty ) ) {
			MarketProperty mp = (MarketProperty)getCurrentProperty();
			if ( mp.getOwner() == null ) {
				return true;
			}
		}
		return false;
	}

	public boolean hasMortgagedProperties() {
		boolean result = false;
		for ( BaseProperty p : getProperties() ) {
			if ( p instanceof MarketProperty ) {
				if ( ((MarketProperty) p).isMortgaged() ) {
					return true;
				}
			}
			
		}
		return false;
	}

	public boolean hasUnmortgagedProperties() {
		boolean result = false;
		for ( BaseProperty p : getProperties() ) {
			if ( p instanceof MarketProperty ) {
				if ( !((MarketProperty) p).isMortgaged() ) {
					result = true;
				}
			}
			
		}
		return result;
	}
	
	public boolean isInJail() {
		if ( ( getCurrentProperty() != null ) && 
				 ( getCurrentProperty() instanceof JailProperty ) ) {
			JailProperty jp = (JailProperty)getCurrentProperty();
			if ( jp.getJailedPlayers().contains(this) ) {
				return true;
			}
		}
		return false;
	}
	
	public Vector<MarketProperty> getProperties() {
		return properties;
	}
	public Vector<Card> getCards() {
		return cards;
	}

	public void moveForward( BaseProperty destination ) {
		//TODO: validate that destination exists on the board
		Vector<BaseProperty> props = board.getProperties().getAllPropertiesAhead( getCurrentProperty() );
		for ( BaseProperty p : props ) {
			location = p;
			if ( location.equals(destination)) {
				return;
			}
		}
	}

	public void moveForward( PropertyGroup group ) {
		BaseProperty dest = board.getProperties().getNextInGroup(group,getCurrentProperty());
		moveForward(dest);
	}

	public void moveForwardOnePosition() {
		Vector<BaseProperty> props = board.getProperties().getAllPropertiesAhead( getCurrentProperty() );
		if ( props.get(0) != null ) {
			location = props.get(0);	
		}
	}

	public void relocate( BaseProperty destination ) {
		location = destination;
	}

}
