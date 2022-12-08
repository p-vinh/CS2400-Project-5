// 
//  Name:		Pham, Vinh 
//  Project:	5
//  Due:		9 December 2022 
//  Course:		cs-2400-02-f22 
// 
//  Description: 
//		Reads airport information from .csv files and constructs a directed graph.
//		Outputs a menu that the user can choose from. The program can determine the
//		cheapest path between two airports.
//

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AirportApp {

	public static void main(String[] args) {
		// Objects
		Scanner scan = new Scanner(System.in);
		DictionaryInterface<String, AirportInfo> dict = new MapDictionary<>();
		GraphInterface<String> graph = new DiGraph<>();
		String[] command;

		System.out.println("Airports v0.1 by V. Pham\n");

		try {
			// Change readAirport to ArrayList and convert to adjacency matrix
			Scanner readAirport = new Scanner(new File("./airports.csv"));
			Scanner readDist = new Scanner(new File("./distances.csv"));

			while (readAirport.hasNextLine()) {
				AirportInfo airport = new AirportInfo(readAirport.nextLine());
				dict.add(airport.getIata(), airport);
				graph.addVertex(airport.getIata());
			}

			while (readDist.hasNextLine()) {
				String[] temp = readDist.nextLine().split(",");
				graph.addEdge(temp[0], temp[1], Double.parseDouble(temp[2]));
			}
		} catch (FileNotFoundException fe) {
			System.out.println("File could not be read.");
			fe.printStackTrace();
		}

		do {
			System.out.println();
			System.out.print("Command? ");
			command = scan.nextLine().toUpperCase().split(" ");

			switch (command[0]) {
				case "E":
					break;
				// Prints Menu
				case "H":
					printMenu();
					break;
				case "Q":
					// Query Airport Information: Uses Map to get O(1) access

					for (int i = 1; i < command.length; i++) {
						if (dict.getValue(command[i]) == null) {
							System.out.println("Airport code unknown");
							continue;
						} else {
							System.out.print(command[i] + " - ");
							AirportInfo airport = dict.getValue(command[i]);
							System.out.println(airport.toString());
						}
					}
					break;
				case "D":
					// Outputs the shortest distance between two airports
					try {
						if (!(dict.getValue(command[1]) == null) && !(dict.getValue(command[2]) == null)) {
							StackInterface<String> stack = new LinkedStack<>();

							double pathLength = graph.getCheapestPath(command[1], command[2], stack);

							if (pathLength == 0) {
								System.out.println("Airports not connected");
							} else {
								System.out.println(dict.getValue(command[1]).toString() + " to "
										+ dict.getValue(command[2]).toString() + " is " + pathLength
										+ " through the route:");

								while (!stack.isEmpty()) {
									AirportInfo origin = dict.getValue(stack.pop());
									System.out.println(origin.getIata() + " - " + origin.toString());
								}
							}

						} else
							System.out.println("Airport code unknown");
						break;
					} catch (ArrayIndexOutOfBoundsException ae) {
						ae.printStackTrace();
					}
				case "I":
					double dist;
					// Test to see if theres an input for distance and if its a number
					try {
						dist = Double.parseDouble(command[3]);
					} catch (ArrayIndexOutOfBoundsException ae) {
						dist = -1;
					} catch (NumberFormatException ne) {
						dist = -1;
					}

					if (dist > 0) {
						if (dict.getValue(command[1]) != null && dict.getValue(command[2]) != null) {
							// Returns true if there isnt already a connection
							boolean result = graph.addEdge(command[1], command[2], dist);

							if (result == true) {
								System.out.println(
										dict.getValue(command[1]).toString() + " to "
												+ dict.getValue(command[2]).toString()
												+ " with a distance of " + dist + " added.");
							} else
								System.out.println("The connection already exist.");
						} else
							System.out.println("Unknown Airport Code");
					} else
						System.out.println("Insertion failed. Distance is less than 0.");
					break;

				case "R":
					if (dict.getValue(command[1]) != null && dict.getValue(command[2]) != null) {
						if (graph.hasEdge(command[1], command[2])) {
							boolean result = graph.addEdge(command[1], command[2]);
							if (result == true) {
								System.out.println(dict.getValue(command[1]).toString() + " and " + dict.getValue(command[2]).toString() + " removed.");
							}
						} else {
							System.out.println("Airports aren't Connected");
						}
					} else
						System.out.println("Unknown Airport Code");
					break;
				default:
					System.out.println();
					System.out.println("Invalid Command");
					System.out.println();
					printMenu();
					break;
			}
		} while (!command[0].equals("E"));

		scan.close();

	}

	private static void printMenu() {
		System.out.println("H Print Menu");
		System.out.println("Q Query the airport information by entering the airport code.");
		System.out.println("D Find the minimum distance between two airports.");
		System.out.println("I Insert a connection between two airports.");
		System.out.println("R Remove a connection between two airports.");
		System.out.println("E Exit.");
	}
}