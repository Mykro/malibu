package com.mykro.malibu.server.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.mykro.malibu.common.util.ObjectDumper;

public class JsonSimpleHelperTest 
{
	private Session s;

	@Before
	public void setUp() {
		s = new Session();
	}

	@Test
    public void testBoardClassic()
    {
		JsonSimpleHelper.importFromBoardFile("boards/classic-board.json", s.getWorld());

		System.out.println( ObjectDumper.dump(s.getWorld().getBoard().getProperties(), 50, 50,  
				new String[] { ":serialVersionUID", ":propertyGroup", "PropertyGroup" }
				) );
		
		Assert.assertEquals( 40, s.getWorld().getBoard().getProperties().size() );
    }
	
}
