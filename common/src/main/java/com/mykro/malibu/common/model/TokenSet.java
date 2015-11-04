package com.mykro.malibu.common.model;

import java.util.Vector;

/**
 * A collection of Tokens
 */
public class TokenSet {

	private Vector<Token> tokens = new Vector<Token>();

	public void addToken(String name) {
		tokens.add(new Token(name));
	}
	public Vector<Token> getTokens() {
		return tokens;
	}
	public Token getTokenByName(String s) {
		for ( Token t : tokens ) {
			if ( t.getName().equalsIgnoreCase(s) ) {
				return t;
			}
		}
		return null;
	}
}
