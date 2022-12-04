// 
//  Name:		Pham, Vinh 
//  Project:	5
//  Due:		9 December 2022 
//  Course:		cs-2400-02-f22 
// 
//  Description: 
//
//
// 

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AirportApp {

	public static void main(String[] args) {
		// Objects
		DictionaryInterface<String, String[]> dict = new MapDictionary<>();
		Scanner scan = new Scanner(System.in);
		GraphInterface<String> graph = new DiGraph<>();
		String[] command;
		printMenu();

		do {
			System.out.println();
			System.out.println("Command? ");
			command = scan.nextLine().split(" ");

			switch (command[0]) {
				case "E":
					break;
				// Reads in airport information to map and distance into a directed graph
				case "H":
					String[] temp;
					try {
						Scanner readAirport = new Scanner(new File("./airports.csv"));
						Scanner readDist = new Scanner(new File("./distances.csv"));

						while (readAirport.hasNextLine()) {
							temp = readAirport.nextLine().split(",");
							dict.add(temp[0], temp);
							graph.addVertex(temp[0]);
						}

						while (readDist.hasNextLine()) {
							temp = readDist.nextLine().split(",");
							graph.addEdge(temp[0], temp[1], Double.parseDouble(temp[2]));
						}
						break;
					} catch (FileNotFoundException fe) {
						fe.printStackTrace();
					}
				// Query Airport Information: Uses Map to get O(1) access
				case "Q":
					for (int i = 1; i < command.length; i++) {
						System.out.print(command[i] + " - ");

						if (dict.getValue(command[i]) == null) {
							System.out.println("Airport code unknown");
							continue;
						} else {
							String[] value = dict.getValue(command[i]);
							for (int j = 1; j < value.length; j++) {
								System.out.print(value[j] + " ");
							}
						}
						System.out.println();
					}
					break;
				case "D":
					if (!(dict.getValue(command[1]) == null) && !(dict.getValue(command[2]) == null)) {
						StackInterface<String> stack = new LinkedStack<>();
						int pathLength = graph.getShortestPath(command[1], command[2], stack);

						if (pathLength == 0) {
							System.out.println("Airports not connected");
						} else
							while (!stack.isEmpty()) {
								String[] value = dict.getValue(stack.pop());
								System.out.print(value[0] + " - ");
								for (int j = 1; j < value.length; j++)
									System.out.print(value[j] + " ");
								System.out.println();
							}

					} else
						System.out.println("Airport code unknown");

					break;
				default:
					System.out.println();
					System.out.println("Invalid Command");
					System.out.println();
					printMenu();
			}
		} while (!command[0].equals("E"));

		scan.close();

	}

	private static void printMenu() {
		System.out.println("Airports v0.1 by F. Last\n");
		System.out.println("H Read data file");
		System.out.println("Q Query the airport information by entering the airport code.");
		System.out.println("D Find the minimum distance between two airports.");
		System.out.println("E Exit.");
	}
}