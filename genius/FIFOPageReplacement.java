import java.util.*;

public class FIFOPageReplacement {
    public static void main(String[] args) {
        int pages[] = {2, 3, 2, 1, 5, 2, 4, 5, 3, 2, 5, 2};
        int framesCount = 3;

        Set<Integer> frames = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        int pageFaults = 0;

        System.out.println("Page Reference Sequence: " + Arrays.toString(pages));
        System.out.println("FIFO Page Replacement:\n");

        for (int page : pages) {
            if (!frames.contains(page)) {
                if (frames.size() == framesCount) {
                    int removed = queue.poll();
                    frames.remove(removed);
                }
                frames.add(page);
                queue.add(page);
                pageFaults++;
                System.out.println("Page " + page + " -> hit | Frames: " + frames);
            } else {
                System.out.println("Page " + page + " -> miss | Frames: " + frames);
            }
        }

        System.out.println("\nTotal Page miss: " + pageFaults);
        System.out.println("\nTotal Page hit: " + (pages.length- pageFaults));
    }
}
