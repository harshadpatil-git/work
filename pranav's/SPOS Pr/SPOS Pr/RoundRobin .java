import java.util.*;

class RoundRobin {

    static class Process {
        String id;
        int arrivalTime, burstTime, remainingTime;
        int completionTime, waitingTime, turnaroundTime;

        Process(String id, int arrivalTime, int burstTime) {
            this.id = id;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.remainingTime = burstTime;
        }
    }

    public static void main(String[] args) {
        // 🔹 You can change this or make it user input
        int timeQuantum = 2;

        // 🔹 Hardcoded processes (you can take input if you like)
        Process[] processes = {
            new Process("P1", 0, 6),
            new Process("P2", 1, 4),
            new Process("P3", 4, 8),
            new Process("P4", 3, 3)
        };

        int n = processes.length;
        int currentTime = 0;
        int completed = 0;

        Queue<Process> queue = new LinkedList<>();
        boolean[] isInQueue = new boolean[n];

        // Add first process that arrives at time 0
        queue.add(processes[0]);
        isInQueue[0] = true;

        // 🔹 Main scheduling loop
        while (completed != n) {
            if (queue.isEmpty()) {
                currentTime++;
                for (int i = 0; i < n; i++) {
                    if (processes[i].arrivalTime <= currentTime && !isInQueue[i] && processes[i].remainingTime > 0) {
                        queue.add(processes[i]);
                        isInQueue[i] = true;
                    }
                }
                continue;
            }

            Process current = queue.poll();
            int execTime = Math.min(timeQuantum, current.remainingTime);
            current.remainingTime -= execTime;
            currentTime += execTime;

            // Check for newly arrived processes during execution
            for (int i = 0; i < n; i++) {
                if (processes[i].arrivalTime <= currentTime && !isInQueue[i] && processes[i].remainingTime > 0) {
                    queue.add(processes[i]);
                    isInQueue[i] = true;
                }
            }

            // If not finished, put it back in queue
            if (current.remainingTime > 0) {
                queue.add(current);
            } else {
                current.completionTime = currentTime;
                current.turnaroundTime = current.completionTime - current.arrivalTime;
                current.waitingTime = current.turnaroundTime - current.burstTime;
                completed++;
            }
        }

        // 🔹 Display Results
        System.out.println("------------------------------------------------------------");
        System.out.println("Process\tArrival\tBurst\tCompletion\tTurnaround\tWaiting");
        System.out.println("------------------------------------------------------------");

        double totalWT = 0, totalTAT = 0;
        for (Process p : processes) {
            totalWT += p.waitingTime;
            totalTAT += p.turnaroundTime;
            System.out.printf("%s\t%d\t%d\t%d\t\t%d\t\t%d\n",
                p.id, p.arrivalTime, p.burstTime, p.completionTime, p.turnaroundTime, p.waitingTime);
        }

        System.out.println("------------------------------------------------------------");
        System.out.printf("Average Turnaround Time = %.2f\n", totalTAT / n);
        System.out.printf("Average Waiting Time = %.2f\n", totalWT / n);
    }
}
