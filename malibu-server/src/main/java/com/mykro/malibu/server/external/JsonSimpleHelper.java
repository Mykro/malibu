package com.mykro.malibu.server.external;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mykro.malibu.common.model.BaseProperty;
import com.mykro.malibu.common.model.Card;
import com.mykro.malibu.common.model.CardDeck;
import com.mykro.malibu.common.model.CardPileProperty;
import com.mykro.malibu.common.model.GoProperty;
import com.mykro.malibu.common.model.InstructionAdvancePositions;
import com.mykro.malibu.common.model.InstructionAdvanceToGroup;
import com.mykro.malibu.common.model.InstructionAdvanceToProperty;
import com.mykro.malibu.common.model.InstructionGetOutOfJail;
import com.mykro.malibu.common.model.InstructionGoToJail;
import com.mykro.malibu.common.model.InstructionKeepCard;
import com.mykro.malibu.common.model.InstructionPayLandlordDiceMultiplier;
import com.mykro.malibu.common.model.InstructionPayLandlordRentMultiplier;
import com.mykro.malibu.common.model.InstructionPlayerCashAdjustFixed;
import com.mykro.malibu.common.model.InstructionPlayerCashAdjustHouses;
import com.mykro.malibu.common.model.InstructionPlayerCashAdjustRivals;
import com.mykro.malibu.common.model.JailProperty;
import com.mykro.malibu.common.model.ParkingProperty;
import com.mykro.malibu.common.model.PropertyGroup;
import com.mykro.malibu.common.model.PropertySequence;
import com.mykro.malibu.common.model.RailroadProperty;
import com.mykro.malibu.common.model.StreetProperty;
import com.mykro.malibu.common.model.TaxProperty;
import com.mykro.malibu.common.model.UtilityProperty;
import com.mykro.malibu.common.model.World;

/**
 * Import data structures from JSON to the model.
 * 
 * Assumes the use of the json.simple third-party library.
 */
public class JsonSimpleHelper {

	/**
	 * Import the world's board configuration from a *-board.json file.
	 * The file location is given via a relative path.
	 * Tries internal resources and on-disk.
	 * @param path
	 * @param world
	 */
	public static void importBoard( String path, World world ) {
		// First try our resources
		File f = getResourceFile(path);
		// Then try on-disk
		if ( !f.exists() ) {
			f = new File(path);
		}
		try {
			Object obj = loadJSONObjectsFromFile(f);
	        JSONObject jsonRootObject =  (JSONObject) obj;
	        importJSONObjectsToWorld(jsonRootObject, world);
		} catch ( ParseException pe ) {
			
		} catch ( IOException ioe ) {
			
		}
	}

	public static File getResourceFile(String path) {
		ClassLoader classLoader = JsonSimpleHelper.class.getClassLoader();
		File file = new File(classLoader.getResource(path).getFile());
		return file;
	}

	/**
	 * Retrieve a hierarchy of JSONObject instances from the given File handler.
	 * Note that File may be on-disk or a resource.
	 * @param file a File instance
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	public static JSONObject loadJSONObjectsFromFile( File file ) 
			throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(file));
        return (JSONObject) obj;
	}
	
	public static void importJSONObjectsToWorld( JSONObject jsonRootObject, World world ) {
        JSONObject jsonBoardObject =  (JSONObject) jsonRootObject.get("board");

        // initialise card pile ids first, so that locations can reference them.
        JSONArray jsonCardPiles = (JSONArray) jsonBoardObject.get("cardpiles");
        for ( Object jsonCardPileObj : jsonCardPiles ) {
        	JSONObject jsonCardPile = (JSONObject) jsonCardPileObj;
        	int id = getJSONInteger( jsonCardPile.get("id") );
            String title = (String) jsonCardPile.get("title");
            world.addCardPile(id, title);
        }
        
        // initialise board locations
		PropertySequence props = world.getProperties();
		props.clear();
		
        JSONArray jsonLocationsUnsorted = (JSONArray) jsonBoardObject.get("locations");
        JSONArray jsonLocations = sortJsonArrayByNumberKey(jsonLocationsUnsorted, "position");
        for ( Object jsonLocationObj : jsonLocations ) {
        	JSONObject jsonLocation = (JSONObject) jsonLocationObj;
        	int position = getJSONInteger( jsonLocation.get("position") );
            String title = (String) jsonLocation.get("title");
            String type = (String) jsonLocation.get("type");
            switch ( type ) {
            	case "JAIL" :
                    String jail_title = (String) jsonLocation.get("jail_title");
                    int jail_fee = getJSONInteger( jsonLocation.get("jail_fee") );
                    PropertyGroup jails = world.getBoard().addGroupSafely("jail");
            		world.getBoard().setJailGroup(jails);
            		JailProperty jail = new JailProperty(title, jail_title, jails, jail_fee,3);
            		props.add(jail);
                    break;

            	case "SPECIAL" :
            		int pass_income = getJSONInteger( jsonLocation.get("special_pass_income") );
                    if ( pass_income > 0 ) {
                		props.add(new GoProperty(title));
                		break;
                    }
                    int tax_flat = getJSONInteger( jsonLocation.get("special_tax_amount") );
                    double tax_wealth = getJSONDouble( jsonLocation.get("special_tax_wealth_multiple") );
                    if ( tax_flat > 0 ) {
                        if ( tax_wealth > 0 ) {
                        	props.add(new TaxProperty(title, tax_flat, (int)(tax_wealth*100) ));
                    		break;
                        } else {
                        	props.add(new TaxProperty(title, tax_flat, 0));
                    		break;
                        }
                    }
                    int cardpile = getJSONInteger( jsonLocation.get("special_cardpile") );
                    if ( cardpile > 0 ) {
                    	// this should have been instantiated already at the top of this function
                    	CardDeck cp = world.getCards(cardpile);
                    	if ( cp != null ) {
                    		props.add(new CardPileProperty(title, cp));  
                    		break;
                    	}
                    }
                    
                    // fallthrough, we'll just make it a free parking property.
            		props.add(new ParkingProperty(title));                    
                    break;
            	case "MARKET" :
                    String group = (String) jsonLocation.get("group");
                    PropertyGroup g = world.getBoard().addGroupSafely(group);
                    
                	int purchase_price = getJSONInteger( jsonLocation.get("purchase_price") );
                	int house_cost = getJSONInteger( jsonLocation.get("house_cost") );
                    JSONArray jsonRents = (JSONArray) jsonLocation.get("rents");
                    if (house_cost>0 ) {
                    	if ( jsonRents.size()>=6) {
	                		props.add(new StreetProperty(title, g, purchase_price, house_cost, 
	                				getJSONInteger( jsonRents.get(0) ), 
	                				getJSONInteger( jsonRents.get(1) ), 
	                				getJSONInteger( jsonRents.get(2) ), 
	                				getJSONInteger( jsonRents.get(3) ), 
	                				getJSONInteger( jsonRents.get(4) ), 
	                				getJSONInteger( jsonRents.get(5) ) ));
                    	} else {
                    		// not enough rents provided
                    	}
                    } else {
                    	if ( jsonRents != null ) {
                    		props.add(new RailroadProperty(title, g, purchase_price,getJSONInteger( jsonRents.get(0) ) ));
                    	} else {
                    		// JSONArray jsonRentsDice = (JSONArray) jsonLocation.get("rents_dice");
                    		props.add(new UtilityProperty(title, g, purchase_price));
                        }

                    }
                    break;
                default :
                    	
            }  // switch
        }  // end of properties loop
        
        // Initialise cards last.  
        world.clearCardPiles();
        for ( Object jsonCardPileObj : jsonCardPiles ) {
        	JSONObject jsonCardPile = (JSONObject) jsonCardPileObj;
        	int id = getJSONInteger( jsonCardPile.get("id") );
        	String title = getJSONString( jsonCardPile.get("title") );
            CardDeck cp = world.addCardPile(id, title);
            if ( cp != null) {
                JSONArray jsonCards = (JSONArray) jsonCardPile.get("cards");
                for ( Object jsonCardObj : jsonCards ) {
                	JSONObject jsonCard = (JSONObject) jsonCardObj;
                	
                	String description = getJSONString( jsonCard.get("description"));
                	String type = getJSONString( jsonCard.get("type"));

                    switch ( type ) {
	                	case "advance" :
	                        int advance_position = getJSONInteger( jsonCard.get("advance_position") );
	                        if ( advance_position > 0 ) {
	                        	BaseProperty p = world.getProperties().getPropertyByPosition(advance_position);
	                        	Card c = new Card(description);
	                        	c.addInstruction(new InstructionAdvanceToProperty(p));
	                        	cp.add(c);
		                		break;
	                        }
	                        
	                        String advance_group = getJSONString( jsonCard.get("advance_group") );
	                        if ( advance_group != "" ) {
	                        	PropertyGroup g = world.getBoard().getGroupByName(advance_group);
	                        	Card c = new Card(description);
	                        	c.addInstruction(new InstructionAdvanceToGroup(g));
	                        	cp.add(c);

		                        Integer dice_multiplier = getJSONInteger( jsonCard.get("dice_multiplier") );
		                        if ( dice_multiplier != 0 ) {
		                        	c.addInstruction(new InstructionPayLandlordDiceMultiplier(2, dice_multiplier));
		                        	cp.add(c);
		                        } else {
			                        Integer rent_multiplier = getJSONInteger( jsonCard.get("rent_multiplier") );
			                        if ( rent_multiplier != 0 ) {
			                        	c.addInstruction(new InstructionPayLandlordRentMultiplier(rent_multiplier));
			                        	cp.add(c);
			                        }
		                        }
		                		break;
	                        }
	                		
	                        int advance_offset = getJSONInteger( jsonCard.get("advance_offset") );
	                        if ( advance_offset != 0 ) {
	                        	Card c = new Card(description);
	                        	c.addInstruction(new InstructionAdvancePositions(advance_offset));
	                        	cp.add(c);
		                		break;
	                        }

	                        break;
	                        
	                	case "amount_from_bank" :
	                        int amount = getJSONInteger( jsonCard.get("amount") );
	                        if ( amount != 0 ) {
	                        	Card c = new Card(description);
	                        	c.addInstruction(new InstructionPlayerCashAdjustFixed(amount));
	                        	cp.add(c);
		                		break;
	                        }
	                		
	                		break;

	                	case "amount_from_players" :
	                        int amount2 = getJSONInteger( jsonCard.get("amount") );
	                        if ( amount2 != 0 ) {
	                        	Card c = new Card(description);
	                        	c.addInstruction(new InstructionPlayerCashAdjustRivals(amount2));
	                        	cp.add(c);
		                		break;
	                        }
	                		
	                		break;
	                		
	                	case "repairs" :
	                        int house_cost = getJSONInteger( jsonCard.get("repair_house_cost") );
	                        int hotel_cost = getJSONInteger( jsonCard.get("repair_hotel_cost") );
	                        if ( house_cost != 0 || hotel_cost != 0 ) {
	                        	Card c = new Card(description);
	                        	c.addInstruction(new InstructionPlayerCashAdjustHouses(house_cost, hotel_cost));
	                        	cp.add(c);
		                		break;
	                        }
	                		
	                		break;
	                		
	                	case "gotojail" :
	                        int jail_position = getJSONInteger( jsonCard.get("jail_position") );
	                        if ( jail_position > 0 ) {
	                        	BaseProperty p = world.getProperties().getPropertyByPosition(jail_position);
	                        	if ( p instanceof JailProperty ) {
		                        	Card c = new Card(description);
		                        	c.addInstruction(new InstructionGoToJail((JailProperty)p));
		                        	cp.add(c);
			                		break;
	                        	}
	                        }
	                		
	                		break;

	                	case "getoutofjailfree" :
                        	Card c = new Card(description);
                        	InstructionKeepCard ikc = new InstructionKeepCard();
                        	ikc.addDeployInstruction(new InstructionGetOutOfJail());
                        	c.addInstruction(ikc);
                        	cp.add(c);
	                		break;
	                		
	                	default :
	                		System.out.println( "IMPORT ERROR: Unknown card type: " + type );
	                		break;
                    }
                }   // end of cards loop
            }  // end of card decks loop
        }
        
        // initialise tokens 
        JSONArray jsonTokens = (JSONArray) jsonBoardObject.get("tokens");
        if ( jsonTokens != null ) {
        	// replace any existing tokens
        	world.removeTokens();
            for ( Object jsonTokenObj : jsonTokens ) {
            	JSONObject jsonToken = (JSONObject) jsonTokenObj;
            	String name = getJSONString( jsonToken.get("name") );
            	if ( name != "" ) {
                	world.getTokens().addToken(name);
            	}
            }
        }
	}

	private static String getJSONString( Object jsonStringProperty ) {
        return jsonStringProperty != null ? (String)jsonStringProperty : "";
	}
	private static Integer getJSONInteger( Object jsonNumberProperty ) {
        return jsonNumberProperty != null ? (int) (long) jsonNumberProperty : 0;		
	}
	private static Double getJSONDouble( Object jsonNumberProperty ) {
        return jsonNumberProperty != null ? ((Number) jsonNumberProperty).doubleValue() : 0;		
	}
	
	@SuppressWarnings(value = { "all" })
    private static JSONArray sortJsonArrayByNumberKey(JSONArray json, String key)
    {
        SortedMap<Long, JSONObject> map = new TreeMap<Long,JSONObject>();
 
        for (Object o : json) {
            JSONObject tmp = (JSONObject) o;
            Number keyValue = (Number) tmp.get(key);
            map.put((Long)keyValue,tmp);
        }
 
        Set<Long> numbers = map.keySet();
 
        JSONArray sorted = new JSONArray();
        for (Long number : numbers) {
        	JSONObject tmp = map.get(number);
        	sorted.add(tmp);
        }

        return sorted;
	}

}
