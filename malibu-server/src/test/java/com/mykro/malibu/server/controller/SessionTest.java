package com.mykro.malibu.server.controller;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mykro.malibu.common.model.Player;
import com.mykro.malibu.common.model.Token;

public class SessionTest 
{
	private Session s;

	@Before
	public void setUp() {
		s = new Session();
	}

	@Test
    public void testWorldTokensAvailable()
    {
    	Token t = s.getWorld().getFirstAvailableToken();
    	Assert.assertNotNull(t);
    	Assert.assertEquals(t.getName(), "Top Hat");
    }

	@Test
    public void testWorldFirstPlayerToken()
    {
		Player p = s.getWorld().addPlayer("Frank");
    	Assert.assertNotNull(p.getToken());
    	Assert.assertEquals(p.getToken().getName(), "Top Hat");
    }	
	
	@Test
    public void testWorldSecondTokenAvailable()
    {
		Player p = s.getWorld().addPlayer("Frank");
    	Assert.assertNotNull(p.getToken());
    	Assert.assertEquals(p.getToken().getName(), "Top Hat");
    	
    	Token t2 = s.getWorld().getFirstAvailableToken();
    	Assert.assertNotNull(t2);
    	Assert.assertEquals(t2.getName(), "Dog");
    }
	
}
