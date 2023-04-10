package heap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class HeapSolution2 {

    // Leetcode problem: 502
    /*
     * IPO.
     * */
    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        int n = profits.length;

        int[][] cp = new int[n][2];
        for (int i = 0; i < n; i++) {
            cp[i][0] = capital[i];
            cp[i][1] = profits[i];
        }

        // Sort based on the capital needed.
        Arrays.sort(cp, Comparator.comparingInt(a -> a[0]));
        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(a -> -a));

        // Add which project capital needed <= than existing w.
        int i = 0;
        while (i < n && cp[i][0] <= w) {
            queue.add(cp[i][1]);
            i += 1;
        }

        while (k > 0 && !queue.isEmpty()) {
            // Take the most profitable project.
            w += queue.poll();
            k -= 1;

            // w updated. Add more projects which capital needed <= than existing w.
            while (i < n && cp[i][0] <= w) {
                queue.add(cp[i][1]);
                i += 1;
            }
        }

        return w;
    }

    // Leetcode problem: 1338
    /*
     * Reduce Array Size to The Half.
     * Count the occurrences of each element.
     * Remove the elements with greater count one by one to reduce the size into half.
     * */
    public int minSetSize(int[] arr) {
        Arrays.sort(arr);

        int removal = (arr.length + 1) / 2;
        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(a -> -a));
        int count = 1;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] != arr[i - 1]) {
                queue.add(count);
                count = 1;
            } else {
                count += 1;
            }
        }
        queue.add(count);

        int res = 0;
        while (removal > 0) {
            res += 1;
            removal -= queue.poll();
        }

        return res;
    }
}
