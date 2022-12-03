import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AirportApp {

	public static void main(String[] args) {

		// Objects
		Scanner scan = new Scanner(System.in);
		DictionaryInterface<String, String[]> dict = new MapDictionary<>();
		GraphInterface<String> graph = new DirectedGraph<>();
		String[] command;

		printMenu();

		do {
			System.out.println();
			System.out.println("Command? ");
			command = scan.nextLine().split(" ");

			if (command[0].equals("E")) {
				break;
			} 
			else if (command[0].equals("H")) {
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
				} catch (FileNotFoundException fe) {
					fe.printStackTrace();
				}
			} 
			else if (command[0].equals("Q")) {
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
			} 
			else if (command[0].equals("D")) {
				System.out.println(graph.getShortestPath(command[1], command[2], new LinkedStack<>()));
			} 
			else {

				System.out.println();
				System.out.println("Invalid Command");
				System.out.println();
				printMenu();

			}
		} while (true);

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