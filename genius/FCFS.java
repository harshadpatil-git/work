import java.util.*;

class Process1 {
    int pid, arrival, burst, completion, tat, wt;

    Process1(int pid, int arrival, int burst) {
        this.pid = pid;
        this.arrival = arrival;
        this.burst = burst;
    }
}

public class FCFS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        Process1[] p = new Process1[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Enter Arrival and Burst time for P" + (i + 1) + ": ");
            int at = sc.nextInt();
            int bt = sc.nextInt();
            p[i] = new Process1(i + 1, at, bt);
        }

        Arrays.sort(p, Comparator.comparingInt(a -> a.arrival));

        int time = 0;
        double totalTAT = 0, totalWT = 0;

        for (int i = 0; i < n; i++) {
            if (time < p[i].arrival)
                time = p[i].arrival;
            time += p[i].burst;
            p[i].completion = time;
            p[i].tat = p[i].completion - p[i].arrival;
            p[i].wt = p[i].tat - p[i].burst;
            totalTAT += p[i].tat;
            totalWT += p[i].wt;
        }

        System.out.println("\nPID\tAT\tBT\tCT\tTAT\tWT");
        for (Process1 proc : p) {
            System.out.printf("P%d\t%d\t%d\t%d\t%d\t%d\n",
                    proc.pid, proc.arrival, proc.burst, proc.completion, proc.tat, proc.wt);
        }

        System.out.printf("\nAverage TAT = %.2f", totalTAT / n);
        System.out.printf("\nAverage WT  = %.2f\n", totalWT / n);
    }
}
