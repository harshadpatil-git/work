import java.util.*;

public class LRUPageReplacement {
    public static void main(String[] args) {
        int pages[] = {2, 3, 2, 1, 5, 2, 4, 5, 3, 2, 5, 2};
        int framesCount = 3;

        List<Integer> frames = new ArrayList<>();
        Map<Integer, Integer> recentUse = new HashMap<>();
        int pageFaults = 0;

        System.out.println("Page Reference Sequence: " + Arrays.toString(pages));
        System.out.println("LRU Page Replacement:\n");

        for (int i = 0; i < pages.length; i++) 
        {
            int page = pages[i];

            if (!frames.contains(page)) {
                if (frames.size() == framesCount) {
                    int lruPage = findLRU(recentUse);
                    frames.remove(Integer.valueOf(lruPage));
                    recentUse.remove(lruPage);
                }
                frames.add(page);
                pageFaults++;
                System.out.println("Page " + page + " -> Fault | Frames: " + frames);
            } else {
                System.out.println("Page " + page + " -> No Fault | Frames: " + frames);
            }

            recentUse.put(page, i);
        }

        System.out.println("\nTotal Page Faults: " + pageFaults);
    }

    private static int findLRU(Map<Integer, Integer> recentUse) {
        int min = Integer.MAX_VALUE, lruPage = -1;

        for (Map.Entry<Integer, Integer> entry : recentUse.entrySet()) {
            if (entry.getValue() < min) {
                min = entry.getValue();
                lruPage = entry.getKey(); 
            }
        }
        return lruPage;
    }
}
