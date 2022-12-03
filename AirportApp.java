import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AirportApp {

	public static void main(String[] args) {

		File distance = new File("./distances.csv");
		Scanner scan = new Scanner(System.in);

		String input = "";
		System.out.println("Airports v0.1 by F. Last\n");
		System.out.println("""
				H Read data files
				Q Query the airport information by entering the airport code.
				D Find the minimum distance between two airports.
				E Exit.
					""");

		while (!input.equals("E")) {
			System.out.println("Command? ");
			input = scan.nextLine();

			if (input.equals("H")) {
				try {
					Scanner read = new Scanner(new File("./airports.csv"));

					while (read.hasNextLine()) {
						// input airport into hash map
						// intput distance data into vertices
					}

				} catch (FileNotFoundException fe) {
					fe.printStackTrace();
				}
			}
		}

		scan.close();
	}
}