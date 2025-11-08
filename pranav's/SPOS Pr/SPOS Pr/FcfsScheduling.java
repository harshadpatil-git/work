import java.util.*;

class Process {
    String pid;
    int arrivalTime, burstTime;
    int startTime, completionTime, turnaroundTime, waitingTime;

    Process(String pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }
}

public class FcfsScheduling {
    public static void main(String[] args) {
      
        Process[] processes = {
            new Process("P1", 0, 3),
            new Process("P2", 2, 6),
            new Process("P3", 4, 4),
            new Process("P4", 6, 5),
            new Process("P5", 8, 2)
        };

        
        Arrays.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));

        int currentTime = 0;
        double totalTAT = 0, totalWT = 0;

        System.out.println("PID\tAT\tBT\tST\tCT\tTAT\tWT");

        for (Process p : processes) {
            if (currentTime < p.arrivalTime) {
                currentTime = p.arrivalTime; 
            }

            p.startTime = currentTime;
            p.completionTime = p.startTime + p.burstTime;
            p.turnaroundTime = p.completionTime - p.arrivalTime;
            p.waitingTime = p.turnaroundTime - p.burstTime;

            currentTime = p.completionTime;

            totalTAT += p.turnaroundTime;
            totalWT += p.waitingTime;

            System.out.println(p.pid + "\t" +
                    p.arrivalTime + "\t" +
                    p.burstTime + "\t" +
                    p.startTime + "\t" +
                    p.completionTime + "\t" +
                    p.turnaroundTime + "\t" +
                    p.waitingTime);
        }

        int n = processes.length;
        System.out.println("\nAverage Turnaround Time: " + (totalTAT / n));
        System.out.println("Average Waiting Time: " + (totalWT / n));
    }
}
