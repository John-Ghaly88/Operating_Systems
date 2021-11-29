import java.util.ArrayList;

public class Process extends Thread {
	private int PID;
	private ProcessState State;
	private OperatingSystem os;

	public Process(int id, OperatingSystem o) {
		PID = id;
		State = ProcessState.NEW;
		os = o;
		o.table.add(this);
	}

	public void Process1() {
		os.printData("Input the filename you want to print from");
		String filePath = os.takeInput();
		os.printData(os.readFile(filePath));
	}

	public void Process2() {
		os.printData("Input the filename you want to write to");
		String filename = os.takeInput();
		os.printData("Input the data you want to write");
		String data = os.takeInput();
		os.writeData(data, filename);
	}

	public void countAndDisplay(int lb, int ub) {
		for (int i = lb; i <= ub; i++)
			os.printData(Integer.toString(i));
	}

	public void Process5() {
		os.printData("Input the lower bound");
		int lb = Integer.parseInt(os.takeInput());
		os.printData("Input the upper bound");
		int ub = Integer.parseInt(os.takeInput());
		String file = os.createFile();
		String r = "";
		for (int i = lb; i <= ub; i++)
			r = r + Integer.toString(i) + "\n";
		os.writeData(r, file);
	}

	public void run() {
		State = ProcessState.RUNNING;
		if (PID == 1) {
			this.Process1();
		}

		else if (PID == 2) {
			this.Process2();
		}

		else if (PID == 3) {
			this.countAndDisplay(0, 300);
		}

		else if (PID == 4) {
			this.countAndDisplay(500, 1000);
		}

		else if (PID == 5) {
			this.Process5();
		}

		State = ProcessState.TERMINATED;
	}

	public String toString() {
		return this.PID + " " + this.State;
	}

	public static void main(String args[]) {
		OperatingSystem os = new OperatingSystem();
		//os.createProcess(1);
		//os.createProcess(2);
		//os.createProcess(3);
		//os.createProcess(4);
		//os.createProcess(5);
	}
}
