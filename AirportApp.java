import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AirportApp {

	public static void main(String[] args) {

		// Objects
		File distance = new File("./distances.csv");
		Scanner scan = new Scanner(System.in);
		InterfaceDictionary<String, String[]> dict = new MapDictionary<>();
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
					Scanner read = new Scanner(new File("./airports.csv"));

					while (read.hasNextLine()) {
						temp = read.nextLine().split(",");
						dict.add(temp[0], temp);

						// input distance data into vertices
					}
				} catch (FileNotFoundException fe) {
					fe.printStackTrace();
				}
			} else if (command[0].equals("Q")) {
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
			} else {

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