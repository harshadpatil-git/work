import java.util.*;

class SJF_Preemptive {
    static class Process {
        String pid;
        int arrivalTime, burstTime, remainingTime;
        int completionTime, waitingTime, turnaroundTime;

        Process(String pid, int arrivalTime, int burstTime) {
            this.pid = pid;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.remainingTime = burstTime;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 🔹 Input number of processes
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        Process[] p = new Process[n];

        // 🔹 Input process details
        for (int i = 0; i < n; i++) {
            System.out.print("Enter Process ID: ");
            String pid = sc.next();
            System.out.print("Enter Arrival Time: ");
            int at = sc.nextInt();
            System.out.print("Enter Burst Time: ");
            int bt = sc.nextInt();
            p[i] = new Process(pid, at, bt);
        }

        int completed = 0, time = 0;
        int totalWT = 0, totalTAT = 0;
        boolean[] isCompleted = new boolean[n];

        // 🔹 Main scheduling loop
        while (completed != n) {
            int minIndex = -1;
            int minRemaining = Integer.MAX_VALUE;

            // Find the process with the shortest remaining time among those that have arrived
            for (int i = 0; i < n; i++) {
                if (p[i].arrivalTime <= time && !isCompleted[i] && p[i].remainingTime < minRemaining) {
                    minRemaining = p[i].remainingTime;
                    minIndex = i;
                }
            }

            if (minIndex == -1) {
                time++;
                continue;
            }

            p[minIndex].remainingTime--;
            time++;

            if (p[minIndex].remainingTime == 0) {
                p[minIndex].completionTime = time;
                p[minIndex].turnaroundTime = p[minIndex].completionTime - p[minIndex].arrivalTime;
                p[minIndex].waitingTime = p[minIndex].turnaroundTime - p[minIndex].burstTime;

                totalWT += p[minIndex].waitingTime;
                totalTAT += p[minIndex].turnaroundTime;

                isCompleted[minIndex] = true;
                completed++;
            }
        }

        // 🔹 Output results
        System.out.println("\nPID\tAT\tBT\tCT\tTAT\tWT");
        for (Process pr : p) {
            System.out.println(pr.pid + "\t" +
                    pr.arrivalTime + "\t" +
                    pr.burstTime + "\t" +
                    pr.completionTime + "\t" +
                    pr.turnaroundTime + "\t" +
                    pr.waitingTime);
        }

        System.out.println("\nAverage Waiting Time: " + (float) totalWT / n);
        System.out.println("Average Turnaround Time: " + (float) totalTAT / n);
    }
}
