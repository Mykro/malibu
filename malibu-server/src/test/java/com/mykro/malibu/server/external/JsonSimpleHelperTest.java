package com.mykro.malibu.server.external;

import java.io.File;

import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mykro.malibu.common.util.ObjectDumper;
import com.mykro.malibu.server.controller.Session;

public class JsonSimpleHelperTest 
{
	private Session s;

	@Before
	public void setUp() {
		s = new Session();
	}

	@Test
    public void testExistsBoardResource_Classic() throws Exception
    {
		File f = JsonSimpleHelper.getResourceFile("boards/classic-board.json");
		Assert.assertNotNull( f );
    }

	@Test
    public void testLoadBoardResource_Classic() throws Exception
    {
		File f = JsonSimpleHelper.getResourceFile("boards/classic-board.json");
		JSONObject root = JsonSimpleHelper.loadJSONObjectsFromFile(f);
		Assert.assertNotNull( root );
    }

	@Test
    public void testImportBoard_Classic()
    {
		JsonSimpleHelper.importBoard("boards/classic-board.json", s.getWorld());

		Assert.assertEquals(10, s.getWorld().getTokens().getTokens().size());
		Assert.assertEquals(40, s.getWorld().getBoard().getProperties().size());
		Assert.assertEquals(40, s.getWorld().getProperties().size());
		Assert.assertEquals(14, s.getWorld().getCards(1).size());
		Assert.assertEquals(17, s.getWorld().getCards(2).size());
		
		// System.out.println( ObjectDumper.dump(s.getWorld().getTokens()));
		
		/*
		System.out.println( ObjectDumper.dump(s.getWorld().getBoard().getProperties(), 50, 50,  
				new String[] { ":serialVersionUID", ":propertyGroup", "PropertyGroup" }
				) );
*/		
    }
	
}
