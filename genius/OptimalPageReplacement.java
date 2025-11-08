import java.util.*;

public class OptimalPageReplacement {
    public static void main(String[] args) {
        int pages[] = {2, 3, 2, 1, 5, 2, 4, 5, 3, 2, 5, 2};
        int framesCount = 3;

        List<Integer> frames = new ArrayList<>();
        int pageFaults = 0;

        System.out.println("Page Reference Sequence: " + Arrays.toString(pages));
        System.out.println("Optimal Page Replacement:\n");

        for (int i = 0; i < pages.length; i++) {
            int page = pages[i];

            if (!frames.contains(page)) {
                if (frames.size() == framesCount) {
                    int indexToReplace = findOptimalIndex(frames, pages, i + 1);
                    frames.set(indexToReplace, page);
                } else {
                    frames.add(page);
                }
                pageFaults++;
                System.out.println("Page " + page + " -> Fault | Frames: " + frames);
            } else {
                System.out.println("Page " + page + " -> No Fault | Frames: " + frames);
            }
        }

        System.out.println("\nTotal Page Faults: " + pageFaults);
    }

    private static int findOptimalIndex(List<Integer> frames, int[] pages, int start) {
        int farthest = start, replaceIndex = -1;

        for (int i = 0; i < frames.size(); i++) {
            int frame = frames.get(i);
            int nextUse = Integer.MAX_VALUE;

            for (int j = start; j < pages.length; j++) {
                if (pages[j] == frame) {
                    nextUse = j;
                    break;
                }
            }

            if (nextUse > farthest) {
                farthest = nextUse;
                replaceIndex = i;
            }
        }

        return (replaceIndex == -1) ? 0 : replaceIndex;
    }
}
