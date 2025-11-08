import java.util.*;

class Priority {
    static class Process {
        String pid;
        int arrivalTime, burstTime, priority;
        int completionTime, waitingTime, turnaroundTime;
        boolean isCompleted = false;

        Process(String pid, int arrivalTime, int burstTime, int priority) {
            this.pid = pid;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.priority = priority;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        Process[] p = new Process[n];

     
        for (int i = 0; i < n; i++) {
            System.out.print("Enter Process ID: ");
            String pid = sc.next();
            System.out.print("Enter Arrival Time: ");
            int at = sc.nextInt();
            System.out.print("Enter Burst Time: ");
            int bt = sc.nextInt();
            System.out.print("Enter Priority (lower number = higher priority): ");
            int pr = sc.nextInt();
            p[i] = new Process(pid, at, bt, pr);
        }

        int completed = 0, time = 0;
        int totalWT = 0, totalTAT = 0;

  
        while (completed != n) {
            int idx = -1;
            int highestPriority = Integer.MAX_VALUE;

            
            for (int i = 0; i < n; i++) {
                if (!p[i].isCompleted && p[i].arrivalTime <= time) {
                    if (p[i].priority < highestPriority) {
                        highestPriority = p[i].priority;
                        idx = i;
                    } else if (p[i].priority == highestPriority) {
                        
                        if (idx == -1 || p[i].arrivalTime < p[idx].arrivalTime) {
                            idx = i;
                        }
                    }
                }
            }

            if (idx == -1) {
                time++; 
            } else {
                time += p[idx].burstTime;
                p[idx].completionTime = time;
                p[idx].turnaroundTime = p[idx].completionTime - p[idx].arrivalTime;
                p[idx].waitingTime = p[idx].turnaroundTime - p[idx].burstTime;
                p[idx].isCompleted = true;
                completed++;

                totalWT += p[idx].waitingTime;
                totalTAT += p[idx].turnaroundTime;
            }
        }

        
        System.out.println("\nPID\tAT\tBT\tPR\tCT\tTAT\tWT");
        for (Process pr : p) {
            System.out.println(pr.pid + "\t" +
                    pr.arrivalTime + "\t" +
                    pr.burstTime + "\t" +
                    pr.priority + "\t" +
                    pr.completionTime + "\t" +
                    pr.turnaroundTime + "\t" +
                    pr.waitingTime);
        }

        System.out.println("\nAverage Waiting Time: " + (float) totalWT / n);
        System.out.println("Average Turnaround Time: " + (float) totalTAT / n);
    }
}
