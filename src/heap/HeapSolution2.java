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

    // Leetcode problem: 1675
    /*
     * Minimize Deviation in Array.
     * Explanation: https://www.youtube.com/watch?v=boHNFptxo2A&t=3s
     * The maximum deviation is the difference between the highest & the lowest value.
     * */
    public int minimumDeviation(int[] nums) {
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        int maxVal = 0;

        // First insert the lowest possible value & track the highest value it can be.
        for (int num : nums) {
            int tmp = num;
            while (tmp % 2 == 0) {
                tmp /= 2;
            }

            maxVal = Math.max(maxVal, tmp);
            queue.add(new int[]{tmp, Math.max(2 * tmp, num)});
        }

        int res = Integer.MAX_VALUE;

        // Run until any other option is possible.
        while (queue.size() == nums.length) {
            int[] num = queue.poll();
            res = Math.min(res, maxVal - num[0]);

            // If any greater value can be taken, take it.
            if (num[1] > num[0]) {
                maxVal = Math.max(maxVal, num[0] * 2);
                queue.add(new int[]{2 * num[0], num[1]});
            }
        }

        return res;
    }
}
