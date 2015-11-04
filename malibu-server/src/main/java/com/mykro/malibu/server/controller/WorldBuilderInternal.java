package com.mykro.malibu.server.controller;

import com.mykro.malibu.common.model.Card;
import com.mykro.malibu.common.model.CardDeck;
import com.mykro.malibu.common.model.ChanceProperty;
import com.mykro.malibu.common.model.CommunityChestProperty;
import com.mykro.malibu.common.model.GoProperty;
import com.mykro.malibu.common.model.InstructionAdvancePositions;
import com.mykro.malibu.common.model.InstructionAdvanceToGroup;
import com.mykro.malibu.common.model.InstructionAdvanceToProperty;
import com.mykro.malibu.common.model.InstructionGetOutOfJail;
import com.mykro.malibu.common.model.InstructionKeepCard;
import com.mykro.malibu.common.model.InstructionPlayerCashAdjustFixed;
import com.mykro.malibu.common.model.InstructionPlayerCashAdjustHouses;
import com.mykro.malibu.common.model.InstructionPlayerCashAdjustRivals;
import com.mykro.malibu.common.model.InstructionGoToJail;
import com.mykro.malibu.common.model.JailProperty;
import com.mykro.malibu.common.model.ParkingProperty;
import com.mykro.malibu.common.model.Player;
import com.mykro.malibu.common.model.PropertyGroup;
import com.mykro.malibu.common.model.PropertySequence;
import com.mykro.malibu.common.model.RailroadProperty;
import com.mykro.malibu.common.model.SendToJailProperty;
import com.mykro.malibu.common.model.StreetProperty;
import com.mykro.malibu.common.model.TaxProperty;
import com.mykro.malibu.common.model.TokenSet;
import com.mykro.malibu.common.model.UtilityProperty;
import com.mykro.malibu.common.model.World;
import com.mykro.malibu.common.model.World.WorldState;

// TODO: remove this class from the codebase and replace it with a World Builder that pulls all data from an external config file. This is to ensure the engine remains generic.

/**
 * Initialises a World instance with a built-in classic configuration.
 */ 
public class WorldBuilderInternal implements WorldBuilder {
	
	public void initialiseEverything(World world) {
		initialiseTokens(world);
		initialiseClassicUKBoard(world);
		initialiseChanceCards(world);
		initialiseChestCards(world);
		initialisePlayers(world);
		world.setState(WorldState.PLAYER_STEADY);
	}

	public void initialisePlayer(World world, Player p) {
		p.setCash(1500);
		p.setCurrentProperty(world.getProperties().getPropertyByPosition(0));
		p.getProperties().clear();
		p.getCards().clear();
		p.setRollsToEscape(0);
		
		// TODO: mechanism to allow a player to select or change their token
		initialisePlayerTokenAutomatically(world,p);
	}

	private void initialisePlayerTokenAutomatically(World world, Player p) {
		if ( p.getToken() == null ) {
			p.setToken( world.getFirstAvailableToken() );
		}
	}
	
	private void initialisePlayers(World world) {
		for ( Player p : world.getPlayers() ) {
			initialisePlayer(world, p);
		}
	}
	
	private void initialiseTokens(World world) {
		TokenSet tokens = world.getTokens();
		tokens.addToken("Top Hat");
		tokens.addToken("Dog");
		tokens.addToken("Car");
		tokens.addToken("Boot");
		tokens.addToken("Iron");
		tokens.addToken("Ship");
	}

	final int CARDPILE_CHANCE=1;
	final int CARDPILE_COMMUNITYCHEST=2;

	private void initialiseClassicUKBoard(World world) {

		world.addCardPile(CARDPILE_CHANCE, "Chance");
		world.addCardPile(CARDPILE_COMMUNITYCHEST, "Community Chest");
		
		PropertySequence props = world.getProperties();
		props.clear();

		PropertyGroup brown = world.getBoard().addGroup("brown");
		PropertyGroup cyan = world.getBoard().addGroup("cyan");
		PropertyGroup purple = world.getBoard().addGroup("purple");
		PropertyGroup orange = world.getBoard().addGroup("orange");
		PropertyGroup red = world.getBoard().addGroup("red");
		PropertyGroup yellow = world.getBoard().addGroup("yellow");
		PropertyGroup green = world.getBoard().addGroup("green");
		PropertyGroup blue = world.getBoard().addGroup("blue");
		PropertyGroup railroad = world.getBoard().addGroup("railroad");
		PropertyGroup utility = world.getBoard().addGroup("utility");
		PropertyGroup jails = world.getBoard().addGroup("jail");
		world.getBoard().setJailGroup(jails);
		
		JailProperty jail = new JailProperty("Just Visiting", "Jail", jails, 50,3);

		props.add(new GoProperty("GO"));
		props.add(new StreetProperty("Old Kent Road", brown, 60, 50, 2, 10, 30, 90, 160, 250 ));
		props.add(new CommunityChestProperty("Community Chest", world.getCards(CARDPILE_COMMUNITYCHEST)));
		props.add(new StreetProperty("Whitechapel Road", brown, 60, 50, 4, 20, 60, 180, 320, 450 ));
		props.add(new TaxProperty("Income Tax", 200, 10));
		props.add(new RailroadProperty("Kings Cross Station", railroad, 200,50));
		props.add(new StreetProperty("The Angel Islington", cyan, 100, 50, 6, 30, 90, 270, 400, 550));
		props.add(new ChanceProperty("Chance", world.getCards(CARDPILE_CHANCE)));
		props.add(new StreetProperty("Euston Road", cyan, 100, 50, 6, 30, 90, 270, 400, 550));
		props.add(new StreetProperty("Pentonville Road", cyan, 120, 50, 8, 40, 100, 300, 450, 600));
		props.add(jail);
		props.add(new StreetProperty("Pall Mall", purple, 140, 100, 10, 50, 150, 450, 625, 750));
		props.add(new UtilityProperty("Electric Company", utility, 150));
		props.add(new StreetProperty("White Hall", purple, 140, 100, 10, 50, 150, 450, 625, 750));
		props.add(new StreetProperty("Northumberland Avenue", purple, 160, 100, 12, 60, 180, 500, 700, 900));
		props.add(new RailroadProperty("Marylebone Station", railroad, 200,50));
		props.add(new StreetProperty("Bow Street", orange, 180, 100, 14, 70, 200, 550, 750, 950));
		props.add(new CommunityChestProperty("Community Chest", world.getCards(CARDPILE_COMMUNITYCHEST)));
		props.add(new StreetProperty("Marlborough St", orange, 180, 100, 14, 70, 200, 550, 750, 950));
		props.add(new StreetProperty("Vine St", orange, 200, 100, 16, 80, 220, 600, 800, 1000));
		props.add(new ParkingProperty("Free Parking"));
		props.add(new StreetProperty("Strand", red, 220, 150, 18, 90, 250, 700, 875, 1050));
		props.add(new ChanceProperty("Chance", world.getCards(CARDPILE_CHANCE)));
		props.add(new StreetProperty("Fleet Street", red, 220, 150, 18, 90, 250, 700, 875, 1050));
		props.add(new StreetProperty("Trafalgar Square", red, 240, 150, 20, 100, 300, 750, 925, 1100));
		props.add(new RailroadProperty("Fenchurch Street Station", railroad, 200,50));
		props.add(new StreetProperty("Leicester Square", yellow, 260, 150, 22, 110, 330, 800, 975, 1150));
		props.add(new StreetProperty("Coventry Street", yellow, 260, 150, 22, 110, 330, 800, 975, 1150));
		props.add(new UtilityProperty("Water Works", utility, 150));
		props.add(new StreetProperty("Picadilly", yellow, 280, 150, 24, 120, 360, 850, 1025, 1200));
		props.add(new SendToJailProperty("Go To Jail", jail));
		props.add(new StreetProperty("Regent Street", green, 300, 200, 26, 130, 390, 900, 1100, 1275));
		props.add(new StreetProperty("Oxford Street", green, 300, 200, 26, 130, 390, 900, 1100, 1275));
		props.add(new CommunityChestProperty("Community Chest", world.getCards(CARDPILE_COMMUNITYCHEST)));
		props.add(new StreetProperty("Bond Street", green, 320, 200, 28, 150, 450, 1000, 1200, 1400));
		props.add(new RailroadProperty("Liverpool Street Station", railroad, 200,50));
		props.add(new ChanceProperty("Chance", world.getCards(CARDPILE_CHANCE)));
		props.add(new StreetProperty("Park Lane", blue, 350, 200, 35, 175, 500, 1100, 1300, 1500));
		props.add(new TaxProperty("Super Tax", 100, 0));
		props.add(new StreetProperty("Mayfair", blue, 400, 200, 50, 200, 600, 1400, 1700, 2000));
	}
	
	private void initialiseChestCards(World world) {
		CardDeck cards = world.getCards(CARDPILE_COMMUNITYCHEST);
		
		cards.add(new Card("Advance to Go (Collect $200)")
				.addInstruction(new InstructionAdvanceToProperty(world.getBoard().getPropertyByName("GO"))));

		cards.add(new Card("Bank error in your favour - Collect $75")
				.addInstruction(new InstructionPlayerCashAdjustFixed(75)));

		cards.add(new Card("Doctor's fees - Pay $50")
				.addInstruction(new InstructionPlayerCashAdjustFixed(-50)));
		
		InstructionKeepCard getOut = new InstructionKeepCard();
		getOut.addDeployInstruction(new InstructionGetOutOfJail());
		cards.add(new Card("Get out of jail free – this card may be kept until needed, or sold")
				.addInstruction(getOut) );
		
		cards.add(new Card("Go to jail – go directly to jail – Do not pass Go, do not collect $200")
				.addInstruction(new InstructionGoToJail(world.getJailGroup())));
				
		cards.add(new Card("It is your birthday Collect $10 from each player")
				.addInstruction(new InstructionPlayerCashAdjustRivals(10)));

		cards.add(new Card("Income Tax Refund - collect $20")
		.addInstruction(new InstructionPlayerCashAdjustFixed(20)));
		
		cards.add(new Card("Life Insurance Matures – collect $100")
		.addInstruction(new InstructionPlayerCashAdjustFixed(100)));
		
		cards.add(new Card("Pay Hospital Fees of $100")
		.addInstruction(new InstructionPlayerCashAdjustFixed(-100)));
		
		cards.add(new Card("Pay School Fees of $50")
		.addInstruction(new InstructionPlayerCashAdjustFixed(-50)));
		
		cards.add(new Card("Receive for Services $25")
		.addInstruction(new InstructionPlayerCashAdjustFixed(25)));
		
		cards.add(new Card("You are assessed for street repairs – $40 per house, $115 per hotel")
		.addInstruction(new InstructionPlayerCashAdjustHouses(-40,-115)));

		cards.add(new Card("You have won second prize in a beauty contest– collect $10")
		.addInstruction(new InstructionPlayerCashAdjustFixed(10)));
		
		cards.add(new Card("You inherit $100")
		.addInstruction(new InstructionPlayerCashAdjustFixed(100)));
		
 		cards.add(new Card("From sale of stock you get $50")
		.addInstruction(new InstructionPlayerCashAdjustFixed(50)));
 		
		cards.add(new Card("Holiday Fund matures - Receive $100")
		.addInstruction(new InstructionPlayerCashAdjustFixed(100)));
	}

	private void initialiseChanceCards(World world) {
		CardDeck cards = world.getCards(CARDPILE_CHANCE);
		cards.add(new Card("Advance to Go (Collect $200)")
				.addInstruction(new InstructionAdvanceToProperty(world.getBoard().getPropertyByName("GO"))) );
		cards.add(new Card("Advance to Trafalgar Square")
				.addInstruction(new InstructionAdvanceToProperty(world.getBoard().getPropertyByName("Trafalgar Square"))) );
		cards.add(new Card("Advance token to nearest Utility. If unowned you may buy it from the bank. If owned, throw dice and pay owner a total ten times the amount thrown.")
				.addInstruction(new InstructionAdvanceToGroup(world.getBoard().getGroupByName("utility"))) );
		cards.add(new Card("Advance token to the nearest Railroad and pay owner twice the rental to which he/she is otherwise entitled. If Railroad is unowned, you may buy it from the Bank.")
				.addInstruction(new InstructionAdvanceToGroup(world.getBoard().getGroupByName("railroad"))) );
		cards.add(new Card("Advance token to the nearest Railroad and pay owner twice the rental to which he/she is otherwise entitled. If Railroad is unowned, you may buy it from the Bank.")
				.addInstruction(new InstructionAdvanceToGroup(world.getBoard().getGroupByName("railroad"))) ); // yes there are two
		cards.add(new Card("Advance to Pall Mall - if you pass Go, collect $200")
				.addInstruction(new InstructionAdvanceToProperty(world.getBoard().getPropertyByName("Pall Mall"))) );
		cards.add(new Card("Bank pays you dividend of $50")
				.addInstruction(new InstructionPlayerCashAdjustFixed(50)));
		InstructionKeepCard getOut = new InstructionKeepCard();
		getOut.addDeployInstruction(new InstructionGetOutOfJail());
		cards.add(new Card("Get out of jail free – this card may be kept until needed, or sold")
				.addInstruction(getOut) );
		cards.add(new Card("Go back 3 spaces")
				.addInstruction(new InstructionAdvancePositions(-3)) );
		cards.add(new Card("Go directly to Jail – do not pass Go, do not collect $200")
				.addInstruction(new InstructionGoToJail(world.getJailGroup())));
		cards.add(new Card("Make general repairs on all your property - for each house pay $25 - for each hotel $100")
				.addInstruction(new InstructionPlayerCashAdjustHouses(-25,-100)));
		cards.add(new Card("Pay poor tax of $15")
				.addInstruction(new InstructionPlayerCashAdjustFixed(-15)));
		cards.add(new Card("Take a trip to Marylebone Station – if you pass Go collect $200")
				.addInstruction(new InstructionAdvanceToProperty(world.getBoard().getPropertyByName("Marylebone Station"))) );
		cards.add(new Card("Take a walk on the Boardwalk – advance token to Boardwalk")
				.addInstruction(new InstructionAdvanceToProperty(world.getBoard().getPropertyByName("Boardwalk"))) );
		cards.add(new Card("You have been elected chairman of the board – pay each player $50")
				.addInstruction(new InstructionPlayerCashAdjustRivals(-50)));
		cards.add(new Card("Your building and loan matures – collect $150")
				.addInstruction(new InstructionPlayerCashAdjustFixed(150)));
	}

}
