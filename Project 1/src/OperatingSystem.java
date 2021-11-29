import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class OperatingSystem {
	public ArrayList<Process> table;

	public OperatingSystem() {
		table = new ArrayList<Process>();
	}

	public void createProcess(int pid) {
		Process p = new Process(pid, this);
		p.start();
	}

	public String readFile(String filePath) {
		String text = "";
		try {
			File myFile = new File(filePath);
			Scanner myReader = new Scanner(myFile);
			while (myReader.hasNextLine()) {
				text = text + myReader.nextLine();
			}
			myReader.close();
		} catch (FileNotFoundException x) {
			System.out.println("File not found!");

		}
		return text;
	}

	public void writeData(String in, String filePath) {
		try {
			FileWriter myWriter = new FileWriter(filePath);
			myWriter.write(in);
			myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred!");
			e.printStackTrace();
		}
	}

	public String createFile() {
		File myObj = new File("newfile.txt");
		return myObj.getName();
	}

	public void printData(String out) {
		System.out.println(out);
	}

	public String takeInput() {
		Scanner sc = new Scanner(System.in);
		String out = sc.nextLine();
		return out;
	}
}
