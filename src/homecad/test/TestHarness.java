/**
 * <b>COSC COSC2082 - Programming 2</b><br/>
 * 
 * This class will test the HomeCAD system.
 * 
 * USAGE -----
 * 
 * Run it for an interactive test menu.
 * 
 * Test #15 ("Test all") will dump any debugging output made by the test
 * program, your code, and the JRE, into a text file named "tests.txt".
 * 
 * It will also estimate the completeness of the "functionality" section
 * of the Assignment.
 * 
 * The test #15 can be invoked from the command line with the "all" argument.
 * 
 */

package homecad.test;

import homecad.model.facade.HomeCADEngine;
import homecad.model.ElectronicAppliance;
import homecad.model.ExitPoint;
import homecad.model.HouseholdItem;
import homecad.model.Room;
import homecad.model.RoomReference;
import homecad.model.exception.*;
import homecad.model.facade.*;

import java.io.*;
import java.util.*;

import homecad.view.MainView;

public class TestHarness {
	private static HomeCADModel model;
	private static BufferedReader stdin = new BufferedReader(
			new InputStreamReader(System.in));
	private static RoomReference ref1 = new RoomReference(1, 1, 1);
	private static RoomReference ref2 = new RoomReference(1, 2, 1);
	private static RoomReference ref3 = new RoomReference(1, 1, 2);

	public static void initialiseEngine(String name, int budget) {
		System.out.println("Initialising HomeCAD engine with owner: " + name
				+ ", and budget " + budget + "...");
		// this is the line that creates a HomeCAD engine instance
		model = new HomeCADEngine(name, budget);
	}

	// ************************************************************************
	// ************************************************************************
	// ************************************************************************
	// ************************************************************************
	// ************************************************************************

	// BE VERY CAREFUL CHANGING ANYTHING PAST HERE

	// You may add your own lines of code while debugging
	// your work, but DO NOT change the existing code ...

	// YOUR SYSTEM MUST BE ABLE TO WORK WITH ANYTHING AFTER
	// THIS POINT, LEFT UNMODIFIED !!

	// ************************************************************************
	// ************************************************************************
	// ************************************************************************
	// ************************************************************************
	// ************************************************************************

	// --- test1 ----------------------------------------------------------
	public static boolean test1() throws Exception {
		initialiseEngine("Test Owner", 1000);

		// check owner string representation
		String s = model.getOwner().toString();
		System.out.print("Owner string is: " + s);
		if (s.indexOf("Test Owner:1000:1000") != 0) {
			System.out.println(" ... not correct");
			return false;
		} else {
			System.out.println(" ... correct");
			return true;
		}
	}

	// --- test2 ----------------------------------------------------------
	public static boolean test2() throws Exception {
		initialiseEngine("TEST OWNER", 1000);
		String[] expected = { "1,1,1:3,4,2:RoomA::", "1,2,1:5,4,3:RoomB::",
				"1,1,2:2,2,2:RoomC::" };
		String[] expected1 = { "1,1,2:2,2,2:RoomC::" };

		try {
			System.out.print("Creating first room...");
			model.addRoom(new Room(ref1, "RoomA", 3, 4, 2));

			System.out.print("Creating second room...");
			model.addRoom(new Room(ref2, "RoomB", 5, 4, 3));

			System.out.print("Creating third room...");
			model.addRoom(new Room(ref3, "RoomC", 2, 2, 2));

			// find first room
			System.out.print("Finding first room with getRoom()...");
			if (failureCheck(model.getRoom(ref1).toString().equalsIgnoreCase(
					"1,1,1:3,4,2:RoomA::"), true))
				return false;

			// find second room
			System.out.print("Finding second room with getRoom()...");
			if (failureCheck(model.getRoom(ref2).toString().equalsIgnoreCase(
					"1,2,1:5,4,3:RoomB::"), true))
				return false;

			// find third room
			System.out.print("Finding third room with getRoom()...");
			if (failureCheck(model.getRoom(ref3).toString().equalsIgnoreCase(
					"1,1,2:2,2,2:RoomC::"), true))
				return false;

			System.out.print("Checking rooms with getAllRooms()...");
			if (failureCheck(checkForRooms(model.getAllRooms(), expected), true))
				return false;

			System.out.print("Checking rooms with getStoreyRooms()...");
			if (failureCheck(checkForRooms(model.getStoreyRooms(2), expected1),
					true))
				return false;
		} catch (HomeCADException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	// --- test3 ----------------------------------------------------------
	public static boolean test3() throws Exception {
		initialiseEngine("TEST OWNER", 1000);
		String[] expected = { "1,2,1:5,4,3:RoomB::" };

		try {
			System.out.print("Creating first room...");
			model.addRoom(new Room(ref1, "RoomA", 3, 4, 2));

			System.out.print("Creating second room...");
			model.addRoom(new Room(ref2, "RoomB", 5, 4, 3));
			// remove first room
			System.out.print("Removing first room with removeRoom()...");
			model.removeRoom(ref1);

			System.out.print("Checking rooms with getAllRooms()...");
			if (failureCheck(checkForRooms(model.getAllRooms(), expected), true))
				return false;

			// remove second room
			System.out.println("Removing second room with removeRoom()...");
			model.removeRoom(ref2);

			System.out.println("Checking rooms with getRoom()...");
			if (model.getRoom(ref2) != null)
				return false;
			System.out.println("Checking rooms with getAllRooms()...");
			if (model.getAllRooms() != null)
				return false;
			System.out.println("Checking rooms with getStoreyRooms()...");
			if (model.getStoreyRooms(ref2.getZ()) != null)
				return false;
		} catch (HomeCADException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	// --- test4 ----------------------------------------------------------
	public static boolean test4() throws Exception {
		initialiseEngine("TEST OWNER", 1000);
		String[] expected = { "Table-40", "TV-60-15" };

		try {
			// add room
			System.out.print("Creating a room...");
			model.addRoom(new Room(ref1, "RoomA", 3, 4, 2));

			// add first item
			System.out.print("Adding first item...");
			if (failureCheck(model
					.addItem(ref1, new HouseholdItem("Table", 40)), true))
				return false;

			// add second item
			System.out.print("Adding second item...");
			if (failureCheck(model.addItem(ref1, new ElectronicAppliance("TV",
					60)), true))
				return false;

			// add item to non-existent room
			System.out.print("Adding item to 'unknown' room ...");
			if (failureCheck(model.addItem(ref2, new ElectronicAppliance("TV",
					60)), false))
				return false;

			// check for items
			System.out.print("Checking items in the room...");
			if (failureCheck(checkForItems(model.getRoom(ref1), expected), true))
				return false;
		} catch (HomeCADException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;

	}

	// --- test5 ----------------------------------------------------------
	public static boolean test5() throws Exception {
		initialiseEngine("TEST OWNER", 1000);
		String[] expected = { "Table-40", "TV-60-15" };
		String[] expected1 = { "TV-60-15" };
		try {
			// add room
			System.out.print("Creating a room...");
			model.addRoom(new Room(ref1, "RoomA", 3, 4, 2));

			// add first item
			System.out.print("Adding first item...");
			if (failureCheck(model
					.addItem(ref1, new HouseholdItem("Table", 40)), true))
				return false;

			// add second item
			System.out.print("Adding second item...");
			if (failureCheck(model.addItem(ref1, new ElectronicAppliance("TV",
					60)), true))
				return false;

			// / check for items
			System.out.print("Checking items in the room...");
			if (failureCheck(checkForItems(model.getRoom(ref1), expected), true))
				return false;

			// remove item that doesn't exist
			System.out.print("Removing item that doesn't exist...");
			if (failureCheck(model.removeItem(ref1, "ItemX"), false))
				return false;

			// check for items
			System.out.print("Checking items in the room...");
			if (failureCheck(checkForItems(model.getRoom(ref1), expected), true))
				return false;

			// remove item that does exist
			System.out.print("Removing item that does exist...");
			if (failureCheck(model.removeItem(ref1, "Table"), true))
				return false;

			// check for items
			System.out.print("Checking items in the room...");
			if (failureCheck(checkForItems(model.getRoom(ref1), expected1),
					true))
				return false;
		} catch (HomeCADException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	// --- test6 ----------------------------------------------------------
	public static boolean test6() throws Exception {
		initialiseEngine("TEST OWNER", 2000);
		String[] expected = { "ExitAtoB", "ExitAtoC" };

		System.out.print("Creating first room...");
		model.addRoom(new Room(ref1, "RoomA", 3, 4, 2));

		System.out.print("Creating second room...");
		model.addRoom(new Room(ref2, "RoomB", 3, 4, 5));

		System.out.print("Creating third room...");
		model.addRoom(new Room(ref3, "RoomC", 3, 2, 3));

		// add first exit
		System.out.print("Adding first exit point...");
		if (failureCheck(model.addExitPoint(ref1, new ExitPoint("ExitAtoB",
				ref2)), true))
			return false;

		// add second exit
		System.out.print("Adding second exit point...");
		if (failureCheck(model.addExitPoint(ref1, new ExitPoint("ExitAtoC",
				ref3)), true))
			return false;

		// add reference to non-existent room
		System.out
				.print("Adding exit point using reference to non-existent room ...");
		if (failureCheck(model.addExitPoint(ref2, new ExitPoint("ExitAtoC",
				new RoomReference(5, 5, 5))), false))
			return false;

		// check for exits
		System.out.print("Checking exits in the room...");
		if (failureCheck(checkForExits(model.getRoom(ref1), expected), true))
			return false;

		return true;
	}

	// --- test7 ----------------------------------------------------------
	public static boolean test7() throws Exception {
		initialiseEngine("TEST OWNER", 2000);
		String[] expected1 = { "ExitAtoB", "ExitAtoC" };
		String[] expected2 = { "ExitAtoC" };
		// add first room
		System.out.print("Creating first room...");
		model.addRoom(new Room(ref1, "RoomA", 3, 4, 2));

		System.out.print("Creating second room...");
		model.addRoom(new Room(ref2, "RoomB", 3, 4, 5));

		System.out.print("Creating third room...");
		model.addRoom(new Room(ref3, "RoomC", 3, 2, 3));
		// add first exit
		System.out.print("Adding first exit point...");
		if (failureCheck(model.addExitPoint(ref1, new ExitPoint("ExitAtoB",
				ref2)), true))
			return false;
		// add second exit
		System.out.print("Adding second exit point...");
		if (failureCheck(model.addExitPoint(ref1, new ExitPoint("ExitAtoC",
				ref3)), true))
			return false;
		// check for exits
		if (failureCheck(checkForExits(model.getRoom(ref1), expected1), true))
			return false;
		// remove exit that doesn't exist
		System.out.print("Removing exit that doesn't exist...");
		if (failureCheck(model.removeExitPoint(ref1, "ExitZ"), false))
			return false;
		// check for items
		System.out.print("Checking exits in the room...");
		if (failureCheck(checkForExits(model.getRoom(ref1), expected1), true))
			return false;
		// remove exit that does exist
		System.out.print("Removing exit that does exist...");
		if (failureCheck(model.removeExitPoint(ref1, "ExitAtoB"), true))
			return false;
		// check for items
		System.out.print("Checking exits in the room...");
		if (failureCheck(checkForExits(model.getRoom(ref1), expected2), true))
			return false;

		return true;
	}

	// --- test8 ----------------------------------------------------------
	public static boolean test8() throws Exception {
		initialiseEngine("TEST OWNER", 10000);
		int expected_size = 775;

		// check initial size
		System.out.print("Checking empty house size");
		if (model.calculateHouseSize() != 0) {
			System.out.println(" ... not correct");
			return false;
		} else
			System.out.println(" ... correct");
		try {
			System.out.print("Creating first room...");
			model.addRoom(new Room(ref1, "RoomA", 4, 3, 2));
			System.out.print("Creating second room...");
			model.addRoom(new Room(ref2, "RoomB", 10, 15, 5));
			System.out.print("Creating third room...");
			model.addRoom(new Room(ref3, "RoomC", 1, 1, 1));

			System.out.println("Checking size of the house");
			if (model.calculateHouseSize() != expected_size) {
				System.out.println(" ... not correct");
				return false;
			} else
				System.out.println(" The total size of the house is "
						+ expected_size + "m3 ... correct");

			System.out.println("Checking storey size");
			if (model.calculateStoreySize(ref1.getZ()) != expected_size - 1) {
				System.out.println(" ... not correct");
				return false;
			} else
				System.out.println(" The total size of the storey "
						+ ref1.getZ() + " is "
						+ model.calculateStoreySize(ref1.getZ())
						+ "m3 ... correct");
		} catch (HomeCADException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	// --- test9 ----------------------------------------------------------
	public static boolean test9() throws Exception {
		initialiseEngine("TEST OWNER", 10000);
		int expected_cost = 2865;

		System.out.print("Checking empty house cost");
		if (model.calculateHouseCost() != 0) {
			System.out.println(" ... not correct");
			return false;
		} else
			System.out.println(" ... correct");
		try {
			System.out.print("Creating first room...");
			model.addRoom(new Room(ref1, "RoomA", 4, 3, 2));
			System.out.print("Creating second room...");
			model.addRoom(new Room(ref2, "RoomB", 5, 10, 5));
			System.out.print("Creating third room...");
			model.addRoom(new Room(ref3, "RoomC", 1, 1, 1));

			// add first item
			System.out.print("Adding first item...");
			if (failureCheck(model
					.addItem(ref1, new HouseholdItem("Table", 40)), true))
				return false;

			// add second item
			System.out.print("Adding second item...");
			if (failureCheck(model.addItem(ref1, new ElectronicAppliance("TV",
					60)), true))
				return false;

			System.out.println("Checking cost of the house");
			if (model.calculateHouseCost() != expected_cost) {
				System.out.println(" ... not correct");
				return false;
			} else
				System.out.println(" The total cost of the house is "
						+ expected_cost + "$ ... correct");

			System.out.println("Checking storey cost");
			if (model.calculateStoreyCost(ref1.getZ()) != expected_cost - 10) {
				System.out.println(" ... not correct");
				return false;
			} else
				System.out.println(" The total cost of the storey "
						+ ref1.getZ() + " is "
						+ model.calculateStoreyCost(ref1.getZ())
						+ "$ ... correct");
			System.out.println("Checking room cost");

			if (model.calculateRoomCost(ref1) != 355) {
				System.out.println(" ... not correct");
				return false;
			} else
				System.out.println(" The  cost of the room is "
						+ model.calculateRoomCost(ref1) + "$ ... correct");

		} catch (HomeCADException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	// --- test10 ----------------------------------------------------------
	public static boolean test10() throws Exception {
		initialiseEngine("TEST OWNER", 10000);
		boolean flag = false;
		try {
			// add room to invalid grid reference
			System.out.print("Adding room to invalid reference...");
			model.addRoom(new Room(new RoomReference(0, 1, -1), "InvalidRoom",
					4, 3, 2));
		} catch (StructuralException e) {
			System.out.println(e.getMessage() + "  ... correct");
			flag = true;
		}
		try {
			System.out.print("Creating first room... ");
			model.addRoom(new Room(ref1, "RoomA", 4, 3, 2));
			// add 'duplicate' room (same reference)
			System.out.print("Creating 'duplicate' room... ");
			model.addRoom(new Room(ref1, "RoomB", 5, 4, 3));
			return false;
		} catch (StructuralException e) {
			System.out.println(e.getMessage() + "  ... correct");
		}
		try {
			System.out.print("Removing non-existent room... ");
			model.removeRoom(new RoomReference(5, 1, 6));
			return false;
		} catch (StructuralException e) {
			System.out.println(e.getMessage() + "  ... correct");
		}
		return flag;
	}

	// --- test11 ----------------------------------------------------------
	public static boolean test11() throws Exception {
		initialiseEngine("TEST OWNER", 10000);
		String[] expected = { "1,1,1:3,4,2:RoomA::", "1,2,1:5,4,3:RoomB::",
				"1,1,2:2,2,2:RoomC::" };
		String[] expected1 = { "1,1,1:3,4,2:RoomA::" };

		try {
			System.out.print("Creating first room...");
			model.addRoom(new Room(ref1, "RoomA", 3, 4, 2));
			System.out.print("Creating second room...");
			model.addRoom(new Room(ref2, "RoomB", 5, 4, 3));

			// adding unsupported room
			System.out.print("Adding unsupported room ...");
			model.addRoom(new Room(new RoomReference(2, 2, 2),
					"UnsupportedRoom", 5, 4, 3));
			return false;
		} catch (StructuralException e) {
			System.out.println(e.getMessage() + "  ... correct");
		}
		try {
			System.out.print("Creating valid third room...");
			model.addRoom(new Room(ref3, "RoomC", 2, 2, 2));
			System.out.print("Checking rooms with getAllRooms()...");
			if (failureCheck(checkForRooms(model.getAllRooms(), expected), true))
				return false;
		} catch (StructuralException e) {
			System.out.println(e.getMessage());
			return false;
		}
		try {
			// remove 'support' room
			System.out.print("Removing first 'support' room ...");
			model.removeRoom(ref1);
			return false;
		} catch (StructuralException e) {
			System.out.println(e.getMessage() + "  ... correct");
		}
		try {
			System.out.println("Checking that room wasn't removed ...");
			if (model.getRoom(ref1) == null)
				return false;
			// remove second 'non-support' room
			System.out.print("Removing second room with removeRoom()...");
			model.removeRoom(ref2);
			System.out.print("Checking rooms with getStoreyRooms()...");
			if (failureCheck(checkForRooms(model.getStoreyRooms(ref1.getZ()),
					expected1), true))
				return false;
		} catch (StructuralException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	// --- test12 ----------------------------------------------------------
	public static boolean test12() throws Exception {
		initialiseEngine("TEST OWNER", 1000);
		String[] expected = { "TEST OWNER:1000:45", "TEST OWNER:1000:85",
				"TEST OWNER:1000:400" };
		try {
			System.out.print("Creating first room...");
			model.addRoom(new Room(ref1, "RoomA", 3, 4, 2));
			System.out.print("Creating second room...");
			model.addRoom(new Room(ref2, "RoomB", 5, 4, 3));
			System.out.print("Adding first item...");
			if (failureCheck(model
					.addItem(ref1, new HouseholdItem("Table", 40)), true))
				return false;
			System.out.print("Adding second item...");
			if (failureCheck(model.addItem(ref1, new ElectronicAppliance("TV",
					60)), true))
				return false;

			// check owner string representation
			String s = model.getOwner().toString();
			System.out.print("Owner string is: " + s);
			if (!s.equals(expected[0])) {
				System.out.println(" ... not correct");
				return false;
			} else
				System.out.println(" ... correct");

			System.out.println("Removing item...");
			model.removeItem(ref1, "Table");
			// check owner string representation
			s = model.getOwner().toString();
			System.out.print("Owner string is: " + s);
			if (!s.equals(expected[1])) {
				System.out.println(" ... not correct");
				return false;
			} else
				System.out.println(" ... correct");

			System.out.println("Removing room ...");
			model.removeRoom(ref1);
			// check owner string representation
			s = model.getOwner().toString();
			System.out.print("Owner string is: " + s);
			if (!s.equals(expected[2])) {
				System.out.println(" ... not correct");
				return false;
			} else
				System.out.println(" ... correct");
		} catch (StructuralException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	// --- test13 ----------------------------------------------------------
	public static boolean test13() throws Exception {
		initialiseEngine("TEST OWNER", 1000);
		String expected = "TEST OWNER:1000:380";
		boolean flag = false;

		try {
			System.out.print("Creating first room...");
			model.addRoom(new Room(ref1, "RoomA", 3, 5, 2));
			System.out.println("Creating second room...");
			model.addRoom(new Room(ref2, "RoomB", 4, 4, 2));
			System.out.print("Creating third room - 'overlimit' ");
			model.addRoom(new Room(ref3, "RoomB", 6, 4, 2));
		} catch (StructuralException e) {
			System.out.println(e.getMessage());
			return false;
		} catch (FinanceException e) {
			System.out.println(e.getMessage() + "  ... correct");
			flag = true;
		}
		try {
			System.out.print("Adding item - 'overlimit' ");
			if (failureCheck(model.addItem(ref1,
					new HouseholdItem("Table", 381)), true))
				return false;
		} catch (FinanceException e) {
			System.out.println(e.getMessage() + "  ... correct");
		}

		// check owner string representation
		String s = model.getOwner().toString();
		System.out.print("Owner string is: " + s);
		if (!s.equals(expected)) {
			System.out.println(" ... not correct");
			return false;
		} else
			System.out.println(" ... correct");

		return true;
	}

	// --- test14 ----------------------------------------------------------
	public static boolean test14() throws Exception {
		initialiseEngine("TEST OWNER", 10000);
		String expected = "TEST OWNER:10000:10000";
		String expected1 = "TEST OWNER:10000:500";
		String[] expected2 = { "1,1,1:10,30,3:RoomA:Table-500:" };

		try {
			System.out.print("Creating a room...");
			model.addRoom(new Room(ref1, "RoomA", 10, 30, 3));
			System.out.print("Adding an item...");
			if (failureCheck(model.addItem(ref1,
					new HouseholdItem("Table", 500)), true))
				return false;

			System.out.print("Checking rooms with getAllRooms()...");
			if (failureCheck(checkForRooms(model.getAllRooms(), expected2),
					true))
				return false;
			System.out.print("Check owner string...");
			String s = model.getOwner().toString();
			System.out.print("Owner string is: " + s);
			if (!s.equals(expected1)) {
				System.out.println(" ... not correct");
				return false;
			} else
				System.out.println(" ... correct");

			// reset HomeCAD system
			System.out.println("***RESET***");
			model.reset();
			System.out.print("Checking rooms with getAllRooms()...");
			if (model.getAllRooms() != null)
				return false;
			else 
				System.out.println("... correct");
			System.out.print("Check owner string...");
			s = model.getOwner().toString();
			System.out.print("Owner string is: " + s);
			if (!s.equals(expected)) {
				System.out.println(" ... not correct");
				return false;
			} else
				System.out.println(" ... correct");
		} catch (HomeCADException e) {
			System.out.println(e.getMessage() + "  ... not correct");
		}
		return true;
	}

	/***************************************************************************
	 * ************************************************************************ //
	 * ************************************************************************ //
	 * ************************************************************************ //
	 * ************************************************************************ //
	 * ************************************************************************ //
	 * DO NOT TOUCH ANYTHING PAST THIS POINT //
	 * ************************************************************************ //
	 * ************************************************************************ //
	 * ************************************************************************ //
	 * ************************************************************************ //
	 **************************************************************************/

	public static void main(String[] args) {

		int menuOption = -1;
		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("all")) {
				test15();
				System.exit(0);
			}
		}

		// repeat processing menu items until quit is entered
		while (menuOption != 16) {
			menuOption = mainMenu();
			try {
				switch (menuOption) {
				case 1:
					if (test1())
						System.out.println("**PASS**");
					else
						System.out.println("**FAIL**");
					break;
				case 2:
					if (test2())
						System.out.println("**PASS**");
					else
						System.out.println("**FAIL**");
					break;
				case 3:
					if (test3())
						System.out.println("**PASS**");
					else
						System.out.println("**FAIL**");
					break;
				case 4:
					if (test4())
						System.out.println("**PASS**");
					else
						System.out.println("**FAIL**");
					break;
				case 5:
					if (test5())
						System.out.println("**PASS**");
					else
						System.out.println("**FAIL**");
					break;
				case 6:
					if (test6())
						System.out.println("**PASS**");
					else
						System.out.println("**FAIL**");
					break;
				case 7:
					if (test7())
						System.out.println("**PASS**");
					else
						System.out.println("**FAIL**");
					break;
				case 8:
					if (test8())
						System.out.println("**PASS**");
					else
						System.out.println("**FAIL**");
					break;
				case 9:
					if (test9())
						System.out.println("**PASS**");
					else
						System.out.println("**FAIL**");
					break;
				case 10:
					if (test10())
						System.out.println("**PASS**");
					else
						System.out.println("**FAIL**");
					break;
				case 11:
					if (test11())
						System.out.println("**PASS**");
					else
						System.out.println("**FAIL**");
					break;
				case 12:
					if (test12())
						System.out.println("**PASS**");
					else
						System.out.println("**FAIL**");
					break;
				case 13:
					if (test13())
						System.out.println("**PASS**");
					else
						System.out.println("**FAIL**");
					break;
				case 14:
					if (test14())
						System.out.println("**PASS**");
					else
						System.out.println("**FAIL**");
					break;
				case 15:
					test15();
				}
			} catch (Exception e) {
				System.out.println("Unexpected exception! " + e.getMessage());
				e.printStackTrace();
				System.out.println("**FAIL**");
			}
		}
	}

	// --- test15 ----------------------------------------------------------
	public static void test15() {
		boolean anyFail = false;
		int count = 0;
		int completed = 0;

		int[] testMarks = { 5, 8, 7, 7, 7, 7, 7, 7, 7, 7, 10, 7, 7, 7 };

		System.out.println("These tests failed: ");
		PrintStream realErr, realOut;

		PrintStream outFile = null;
		try {
			outFile = new PrintStream(new FileOutputStream(
					new File("tests.txt")));
		} catch (FileNotFoundException e) {
			System.out
					.println("test harness can't open a file for summarising");
			return;
		}

		realErr = System.err;
		realOut = System.out;
		System.setErr(outFile);
		System.setOut(outFile);

		// remember here that System.out and System.err are now the file
		// the real output streams are in realOut and realErr!
		try {
			System.out.println("--- Test 1 --------------------------");
			if (!test1()) {
				realOut.println("Test 1 **FAIL** (incorrect implementation)");
				anyFail = true;
				count++;
				completed += testMarks[0];
			}
		} catch (Exception e) {
			realOut.println("Test 1 **FAIL** (unexpected exception occurred)");
			anyFail = true;
			count++;
			completed += testMarks[0];
		}
		try {
			System.out.println("--- Test 2 --------------------------");
			if (!test2()) {
				realOut.println("Test 2 **FAIL** (incorrect implementation)");
				anyFail = true;
				count++;
				completed += testMarks[1];
			}
		} catch (Exception e) {
			realOut.println("Test 2 **FAIL** (unexpected exception occurred)");
			anyFail = true;
			count++;
			completed += testMarks[1];
		}
		try {
			System.out.println("--- Test 3 --------------------------");
			if (!test3()) {
				realOut.println("Test 3 **FAIL** (incorrect implementation)");
				anyFail = true;
				count++;
				completed += testMarks[2];
			}
		} catch (Exception e) {
			realOut.println("Test 3 **FAIL** (unexpected exception occurred)");
			anyFail = true;
			count++;
			completed += testMarks[2];
		}
		try {
			System.out.println("--- Test 4 --------------------------");
			if (!test4()) {
				realOut.println("Test 4 **FAIL** (incorrect implementation)");
				anyFail = true;
				count++;
				completed += testMarks[3];
			}
		} catch (Exception e) {
			realOut.println("Test 4 **FAIL** (unexpected exception occurred)");
			anyFail = true;
			count++;
			completed += testMarks[3];
		}
		try {
			System.out.println("--- Test 5 --------------------------");
			if (!test5()) {
				realOut.println("Test 5 **FAIL** (incorrect implementation)");
				anyFail = true;
				count++;
				completed += testMarks[4];
			}
		} catch (Exception e) {
			realOut.println("Test 5 **FAIL** (unexpected exception occurred)");
			anyFail = true;
			count++;
			completed += testMarks[4];
		}
		try {
			System.out.println("--- Test 6 --------------------------");
			if (!test6()) {
				realOut.println("Test 6 **FAIL** (incorrect implementation)");
				anyFail = true;
				count++;
				completed += testMarks[5];
			}
		} catch (Exception e) {
			realOut.println("Test 6 **FAIL** (unexpected exception occurred)");
			anyFail = true;
			count++;
			completed += testMarks[5];
		}
		try {
			System.out.println("--- Test 7 --------------------------");
			if (!test7()) {
				realOut.println("Test 7 **FAIL** (incorrect implementation)");
				anyFail = true;
				count++;
				completed += testMarks[6];
			}
		} catch (Exception e) {
			realOut.println("Test 7 **FAIL** (unexpected exception occurred)");
			anyFail = true;
			count++;
			completed += testMarks[6];
		}
		try {
			System.out.println("--- Test 8 --------------------------");
			if (!test8()) {
				realOut.println("Test 8 **FAIL** (incorrect implementation)");
				anyFail = true;
				count++;
				completed += testMarks[7];
			}
		} catch (Exception e) {
			realOut.println("Test 8 **FAIL** (unexpected exception occurred)");
			anyFail = true;
			count++;
			completed += testMarks[7];
		}
		try {
			System.out.println("--- Test 9 --------------------------");
			if (!test9()) {
				realOut.println("Test 9 **FAIL** (incorrect implementation)");
				anyFail = true;
				count++;
				completed += testMarks[8];
			}
		} catch (Exception e) {
			realOut.println("Test 9 **FAIL** (unexpected exception occurred)");
			anyFail = true;
			count++;
			completed += testMarks[8];
		}
		try {
			System.out.println("--- Test 10 --------------------------");
			if (!test10()) {
				realOut.println("Test 10 **FAIL** (incorrect implementation)");
				anyFail = true;
				count++;
				completed += testMarks[9];
			}
		} catch (Exception e) {
			realOut.println("Test 10 **FAIL** (unexpected exception occurred)");
			anyFail = true;
			count++;
			completed += testMarks[9];
		}
		try {
			System.out.println("--- Test 11 --------------------------");
			if (!test11()) {
				realOut.println("Test 11 **FAIL** (incorrect implementation)");
				anyFail = true;
				count++;
				completed += testMarks[10];
			}
		} catch (Exception e) {
			realOut.println("Test 11 **FAIL** (unexpected exception occurred)");
			anyFail = true;
			count++;
			completed += testMarks[10];
		}

		try {
			System.out.println("--- Test 12 --------------------------");
			if (!test12()) {
				realOut.println("Test 12 **FAIL** (incorrect implementation)");
				anyFail = true;
				count++;
				completed += testMarks[11];
			}
		} catch (Exception e) {
			realOut.println("Test 12 **FAIL** (unexpected exception occurred)");
			anyFail = true;
			count++;
			completed += testMarks[11];
		}
		try {
			System.out.println("--- Test 13 --------------------------");
			if (!test13()) {
				realOut.println("Test 13 **FAIL** (incorrect implementation)");
				anyFail = true;
				count++;
				completed += testMarks[12];
			}
		} catch (Exception e) {
			realOut.println("Test 13 **FAIL** (unexpected exception occurred)");
			anyFail = true;
			count++;
			completed += testMarks[12];
		}
		try {
			System.out.println("--- Test 14 --------------------------");
			if (!test14()) {
				realOut.println("Test 14 **FAIL** (incorrect implementation)");
				anyFail = true;
				count++;
				completed += testMarks[13];
			}
		} catch (Exception e) {
			realOut.println("Test 14 **FAIL** (unexpected exception occurred)");
			anyFail = true;
			count++;
			completed += testMarks[13];
		}

		// set the output streams back to normal, and close the file
		System.setErr(realErr);
		System.setOut(realOut);
		outFile.close();

		if (!anyFail)
			System.out.println("NONE -- Fully Working HomeCAD engine.");
		else
			System.out.println("Some tests failed...");

		System.out.println("\nTests failed:  " + count);
		System.out.println("Tests passed:  " + (14 - count));
		System.out
				.println("\nFunctionality Completed (estimation only - based on the preliminary test weights): "
						+ (100 - completed) + "%");
	}

	/**
	 * Display the test menu.
	 */
	public static int mainMenu() {
		int answer = -1;
		boolean valid = false;

		// continue prompting until valid data given
		while (!valid) {
			// display menu and prompt for entry
			System.out.println();
			System.out
					.println("===============================================================================");
			System.out.println("Tests");
			System.out
					.println("===============================================================================");
			System.out.println();
			System.out.println("HomeCAD SYSTEM TESTS                      ");
			System.out
					.println(" 1. blank house                          10. structural checks - basic");
			System.out
					.println(" 2. adding a room                        11. structural feasibility - adding/removing room");
			System.out
					.println(" 3. removing a room                      12. financial feasibility - room related");
			System.out
					.println(" 4. adding an item                       13. financial feasibility - item related");
			System.out
					.println(" 5. removing an item                     14. reset");
			System.out.println(" 6. adding an exit point");
			System.out
					.println(" 7. removing an exit point               15. TEST ALL");
			System.out.println(" 8. calculate size");
			System.out
					.println(" 9. calculate cost                       16. quit");

			System.out.println();
			System.out.print("Enter an option: ");

			// try to read and validate entered data
			try {
				answer = Integer.parseInt(stdin.readLine());
				System.out.println();
				if ((answer >= 1) && (answer <= 16))
					valid = true;
			} catch (NumberFormatException e) {
				System.out.println("Unparsable number entered");
			} catch (IOException e) {
				System.out.println("I/O Exception");
				System.exit(1);
			}
			// if data was invalid, print an error
			if (!valid) {
				System.out.println();
				System.out.println("Please enter a valid option (1-16).");
			}
		}
		// return the entered data
		return answer;
	}

	public static boolean checkForRooms(Room[] rooms, String[] expected)
			throws Exception {
		try {
			// if number of rooms is different from expected, return failure
			if (rooms.length != expected.length)
				return false;
			// make some spots to tick
			boolean[] tickbox = new boolean[expected.length];

			// for each expected room string...
			for (int i = 0; i < expected.length; i++) {
				// try to find a match
				boolean found = false;
				for (int j = 0; (j < rooms.length) && (!found); j++) {
					if (expected[i].equalsIgnoreCase(rooms[j].toString()))
						found = true;
				}
				// store result for this room string in the tick box
				tickbox[i] = found;
			}
			// check there's a tick in every box
			for (int i = 0; i < tickbox.length; i++) {
				if (!tickbox[i])
					return false;
			}
		} catch (Exception e) {
			System.out
					.println("Parsing error: could not check room list successfully!");
			return false;
		}
		return true;
	}

	public static boolean failureCheck(boolean check, boolean expected) {
		if (check == expected) {
			System.out.println(" OK");
			return false;
		} else {
			System.out.println(" not OK!");
			return true;
		}
	}

	public static boolean checkForItems(Room room, String[] expected)
			throws Exception {
		try {
			// make some spots to tick
			boolean[] tickbox = new boolean[expected.length];

			// split the room string into sub-parts
			String[] split = room.toString().split(":");

			// check the correct number of parts are there
			if (split.length != 4)
				return false;

			// take the 'items' part and split it into individual items
			String[] itemList = split[3].split(",");

			// for each expected item string...
			for (int i = 0; i < expected.length; i++) {
				// try to find a match
				boolean found = false;
				for (int j = 0; (j < itemList.length) && (!found); j++) {
					if (expected[i].equalsIgnoreCase(itemList[j].toString()))
						found = true;
				}
				// store result for this room string in the tick box
				tickbox[i] = found;
			}
			// check there's a tick in every box
			for (int i = 0; i < tickbox.length; i++) {
				if (!tickbox[i])
					return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean checkForExits(Room room, String[] expected)
			throws Exception {
		try {
			// make some spots to tick
			boolean[] tickbox = new boolean[expected.length];
			// split the room string into its parts
			String[] split = room.toString().split(":");
			// check the correct number of parts are there
			if (split.length != 5)
				return false;
			// take the exit part and split it into individual exits
			String[] exitList = split[4].split(",");

			// for each expected item string...
			for (int i = 0; i < expected.length; i++) {
				// try to find a match in the actual item list.
				boolean found = false;
				for (int j = 0; (j < exitList.length) && (!found); j++) {
					if (expected[i].equalsIgnoreCase(exitList[j].toString()))
						found = true;
				}
				// store result for this room string in the tick box
				tickbox[i] = found;
			}
			// check there's a tick in every box
			for (int i = 0; i < tickbox.length; i++) {
				if (!tickbox[i])
					return false;
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
