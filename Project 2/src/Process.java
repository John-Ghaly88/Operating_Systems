public class Process implements Comparable {
	int pid;
	int arrivalTime;
	int serviceTime;

	static int id = 0;

	public Process(int at, int st) {
		pid = ++id;
		arrivalTime = at;
		serviceTime = st;
	}

	@Override
	// For the SJF
	public int compareTo(Object o) {
		Process p = (Process) o;
		if (serviceTime == p.serviceTime)
			return arrivalTime - p.arrivalTime;
		return serviceTime - p.serviceTime;
	}

	public String toString() {
		return this.pid + " " + this.arrivalTime + " " + this.serviceTime
				+ " --- ";
	}
}
