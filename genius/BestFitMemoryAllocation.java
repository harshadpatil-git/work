import java.util.Scanner;

public class BestFitMemoryAllocation {
    public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);

            // Memory partitions
            int partitions[] = {100, 500, 200, 300, 600};
            int n = partitions.length;

            System.out.print("Enter number of processes: ");
            int m = sc.nextInt();

        int processSize[] = new int[m];
        for (int i = 0; i < m; i++) {
            System.out.print("Enter size of process " + (i + 1) + ": ");
            processSize[i] = sc.nextInt();
        }

        // Stores index of partition allocated to each process (-1 means not allocated)
        int allocation[] = new int[m];
        for (int i = 0; i < m; i++)
            allocation[i] = -1;

        // Best Fit Allocation
        for (int i = 0; i < m; i++) {
            int bestIndex = -1;
            for (int j = 0; j < n; j++) {
                if (partitions[j] >= processSize[i]) {
                    if (bestIndex == -1 || partitions[j] < partitions[bestIndex]) {
                        bestIndex = j;
                    }
                }
            }

            // If a best partition found
            if (bestIndex != -1) {
                allocation[i] = bestIndex;
                partitions[bestIndex] -= processSize[i];
            }
        }

        System.out.println("\nProcess No.\tProcess Size\tPartition No.");
        for (int i = 0; i < m; i++) {
            System.out.print("   " + (i + 1) + "\t\t" + processSize[i] + "\t\t");
            if (allocation[i] != -1)
                System.out.println(allocation[i] + 1);
            else
                System.out.println("Not Allocated");
        }
    }
}
