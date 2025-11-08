import java.util.Scanner;

public class FirstFit {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int[] partitions = {100, 500, 200, 300, 600};
        int n = partitions.length;

        System.out.print("Enter number of processes: ");
        int m = sc.nextInt();

        int[] processSize = new int[m];
        for (int i = 0; i < m; i++) {
            System.out.print("Enter size of process " + (i + 1) + ": ");
            processSize[i] = sc.nextInt();
        }

        int[] allocation = new int[m];
        for (int i = 0; i < m; i++) allocation[i] = -1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (partitions[j] >= processSize[i]) {
                    allocation[i] = j;
                    partitions[j] -= processSize[i];
                    break; // ✅ First fit stops at the first available block
                }
            }
        }

        System.out.println("\nProcess No.\tProcess Size\tBlock No.");
        for (int i = 0; i < m; i++) {
            System.out.println((i + 1) + "\t\t" + processSize[i] + "\t\t" +
                    (allocation[i] != -1 ? allocation[i] + 1 : "Not Allocated"));
        }

        sc.close();
    }
}
