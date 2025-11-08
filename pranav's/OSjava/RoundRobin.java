import java.util.*;

class Process {
    int pid, arrival, burst, remaining, completion, tat, wt;

    Process(int pid, int arrival, int burst) {
        this.pid = pid;
        this.arrival = arrival;
        this.burst = burst;
        this.remaining = burst;
    }
}

public class RoundRobin {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        System.out.print("Enter Time Quantum: ");
        int tq = sc.nextInt();

        Process[] p = new Process[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Enter Arrival and Burst time for P" + (i + 1) + ": ");
            int at = sc.nextInt();
            int bt = sc.nextInt();
            p[i] = new Process(i + 1, at, bt);
        }

        // Sort processes by arrival time initially
        Arrays.sort(p, Comparator.comparingInt(a -> a.arrival));

        Queue<Process> q = new LinkedList<>();
        int time = 0, completed = 0;
        double totalTAT = 0, totalWT = 0;
        boolean[] inQueue = new boolean[n];

        // Start simulation
        q.add(p[0]);
        inQueue[0] = true;
        time = p[0].arrival;

        while (completed < n) {
            Process current = q.poll();
            if (current.remaining > tq) {
                current.remaining -= tq;
                time += tq;
            } else {
                time += current.remaining;
                current.remaining = 0;
                current.completion = time;
                current.tat = current.completion - current.arrival;
                current.wt = current.tat - current.burst;
                totalTAT += current.tat;
                totalWT += current.wt;
                completed++;
            }

            // add newly arrived processes
            for (int i = 0; i < n; i++) {
                if (!inQueue[i] && p[i].arrival <= time && p[i].remaining > 0) {
                    q.add(p[i]);
                    inQueue[i] = true;
                }
            }

            // if current process still has burst time left, add back to queue
            if (current.remaining > 0) {
                q.add(current);
            }

            // if queue empty, jump to next arrival
            if (q.isEmpty()) {
                for (int i = 0; i < n; i++) {
                    if (p[i].remaining > 0) {
                        q.add(p[i]);
                        inQueue[i] = true;
                        time = Math.max(time, p[i].arrival);
                        break;
                    }
                }
            }
        }

        // Print table
        System.out.println("\nPID\tAT\tBT\tCT\tTAT\tWT");
        for (Process proc : p) {
            System.out.printf("P%d\t%d\t%d\t%d\t%d\t%d\n",
                    proc.pid, proc.arrival, proc.burst, proc.completion, proc.tat, proc.wt);
        }

        System.out.printf("\nAverage TAT = %.2f", totalTAT / n);
        System.out.printf("\nAverage WT  = %.2f\n", totalWT / n);
    }
}
