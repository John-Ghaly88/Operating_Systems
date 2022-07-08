import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		// Same numbers as those on PA4 for an easy testing/checking
		Process A = new Process(0, 3);
		Process B = new Process(2, 6);
		Process C = new Process(4, 4);
		Process D = new Process(6, 5);
		Process E = new Process(8, 2);

		Process p[] = { A, B, C, D, E };

		Scanner sc = new Scanner(System.in);
		System.out.println("Choose the scheduling policy you want to simulate, Input:");
		System.out.println("'RR' for Round Robin policy");
		System.out.println("'FCFS' for First Come First Served policy");
		System.out.println("'SJF' for Non-Preemptive Shortest Job First policy");
		System.out.println("'PSJF' for Preemptive Shortest Job First policy");
		String policy = sc.next();

		if (policy.equals("RR")) {
			System.out.println("Input the time slice");
			int Q = sc.nextInt();
			RR(p, Q);
		} else if (policy.equals("FCFS"))
			FCFS(p);
		else if (policy.equals("SJF"))
			SJF(p);
		else if (policy.equals("PSJF"))
			PSJF(p);
		else
			System.out.println(
					"You didn't choose one of the 4 scheduling policies, please check your input and try again");
	}

	public static void RR(Process proc[], int timeslots) {
		QueueObj q = new QueueObj(proc.length);
		for (int i = 0; i < proc.length; i++)
			q.enqueue(proc[i]);
		int qs = q.size();
		int time = 0;
		int done = 0;
		QueueObj arrived = new QueueObj(qs);
		boolean finished = false;
		while (!(done == qs)) {

			for (int i = 0; i < q.size(); i++) {
				if (((Process) q.peek()).arrivalTime < time + 1) {
					arrived.enqueue(q.peek());
					q.dequeue();
					break;
				}
				q.enqueue(q.dequeue());
			}
			if (!finished)
				arrived.enqueue(arrived.dequeue());
			if (!arrived.isEmpty()) {

				for (int i = 0; i < timeslots; i++) {
					((Process) arrived.peek()).serviceTime--;
					finished = false;
					System.out.println("at time " + time + " running process "
							+ ((Process) arrived.peek()).pid + ", "
							+ ((Process) arrived.peek()).serviceTime
							+ " time slots left");
					if (((Process) arrived.peek()).serviceTime < 1) {
						finished = true;
						arrived.dequeue();
						done++;
						time++;
						break;
					}
					time++;
				}
			}
			if (arrived.isEmpty())
				time++;
		}
	}

	static void FCFS(Process processes[]) {
		System.out.println("�First Come First Served (FCFS)");
		int wt[] = new int[processes.length];
		int st[] = new int[processes.length];

		for (int i = 0; i < processes.length; i++)
			st[i] = processes[i].serviceTime;

		System.out.println("At time 0 process " + processes[0].pid
				+ " is running");
		wt[0] = 0;

		for (int i = 1; i < processes.length; i++) {
			wt[i] = st[i - 1] + wt[i - 1];
			System.out.println("At time " + wt[i] + " process "
					+ processes[i].pid + " is running");
		}
	}

	public static void SJF(Process proc[]) {
		System.out.println("�Non-Preemptive Shortest Job First (SJF)");
		int t = 0, complete = 0;
		QueueObj p = new QueueObj(proc.length);
		for (int i = 0; i < proc.length; i++)
			p.enqueue(proc[i]);

		while (complete != proc.length) {
			PriorityQueue pr = new PriorityQueue(p.size());

			QueueObj temp2 = new QueueObj(p.size());
			while (!p.isEmpty()) {
				Process c1 = (Process) p.dequeue();
				temp2.enqueue(c1);
				if (c1.arrivalTime <= t)
					pr.insert(c1);
			}
			while (!temp2.isEmpty())
				p.enqueue(temp2.dequeue());

			while (!pr.isEmpty()) {
				Process current = (Process) pr.remove();
				complete++;

				QueueObj temp = new QueueObj(p.size());
				while (!p.isEmpty()) {
					Process c = (Process) p.dequeue();
					if (c.pid != current.pid)
						temp.enqueue(c);
				}
				while (!temp.isEmpty())
					p.enqueue(temp.dequeue());

				int anchor = t + current.serviceTime;
				for (int i = t; i < anchor; i++) {
					System.out.println("At time " + t + ": Process_"
							+ current.pid);
					t++;
				}
			}
		}
	}

	public static void PSJF(Process proc[]) {
		System.out.println("�Preemptive Shortest Job First (PSJF)");
		System.out.println("OR Shortest Time-to-Completion First (STCF):");
		int waitingTime[] = new int[proc.length];
		int remainingTime[] = new int[proc.length];
		for (int i = 0; i < proc.length; i++)
			remainingTime[i] = proc[i].serviceTime;
		int complete = 0, t = 0, min = Integer.MAX_VALUE, shortest = 0;
		boolean flag = false;
		while (complete != proc.length) {
			for (int i = 0; i < proc.length; i++) {
				if ((proc[i].arrivalTime <= t) && (remainingTime[i] < min)
						&& remainingTime[i] > 0) {
					min = remainingTime[i];
					shortest = i;
					flag = true;
				}
			}
			if (flag == false) {
				t++;
				continue;
			}
			remainingTime[shortest]--;
			min = remainingTime[shortest];
			if (min == 0)
				min = Integer.MAX_VALUE;
			if (remainingTime[shortest] == 0) {
				complete++;
				flag = false;
				int finish_time = t + 1;
				waitingTime[shortest] = finish_time
						- proc[shortest].serviceTime
						- proc[shortest].arrivalTime;
			}
			System.out.println("At time " + t + ": Process_" + (shortest + 1));
			t++;
		}
	}
}
