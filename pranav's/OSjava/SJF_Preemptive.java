import java.util.*;

class Process2 {
    int pid, arrival, burst, remaining, completion, tat, wt;

    Process2(int pid, int arrival, int burst) {
        this.pid = pid;
        this.arrival = arrival;
        this.burst = burst;
        this.remaining = burst;
    }
}

public class SJF_Preemptive {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        Process2[] p = new Process2[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Enter Arrival and Burst time for P" + (i + 1) + ": ");
            int at = sc.nextInt();
            int bt = sc.nextInt();
            p[i] = new Process2(i + 1, at, bt);
        }

        int completed = 0, time = 0;
        double totalTAT = 0, totalWT = 0;
        int minRemaining, idx;

        while (completed < n) {
            idx = -1;
            minRemaining = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                if (p[i].arrival <= time && p[i].remaining > 0 && p[i].remaining < minRemaining) {
                    minRemaining = p[i].remaining;
                    idx = i;
                }
            }

            if (idx == -1) {
                time++;
                continue;
            }

            p[idx].remaining--;
            time++;

            if (p[idx].remaining == 0) {
                p[idx].completion = time;
                p[idx].tat = p[idx].completion - p[idx].arrival;
                p[idx].wt = p[idx].tat - p[idx].burst;
                totalTAT += p[idx].tat;
                totalWT += p[idx].wt;
                completed++;
            }
        }

        System.out.println("\nPID\tAT\tBT\tCT\tTAT\tWT");
        for (Process2 proc : p) {
            System.out.printf("P%d\t%d\t%d\t%d\t%d\t%d\n",
                    proc.pid, proc.arrival, proc.burst, proc.completion, proc.tat, proc.wt);
        }

        System.out.printf("\nAverage TAT = %.2f", totalTAT / n);
        System.out.printf("\nAverage WT  = %.2f\n", totalWT / n);
    }
}
