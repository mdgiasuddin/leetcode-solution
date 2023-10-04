package heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

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
            queue.add(new int[] {tmp, Math.max(2 * tmp, num)});
        }

        int res = Integer.MAX_VALUE;

        // Run until any other option is possible.
        while (queue.size() == nums.length) {
            int[] num = queue.poll();
            res = Math.min(res, maxVal - num[0]);

            // If any greater value can be taken, take it.
            if (num[1] > num[0]) {
                maxVal = Math.max(maxVal, num[0] * 2);
                queue.add(new int[] {2 * num[0], num[1]});
            }
        }

        return res;
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

    // Leetcode problem: 373
    /*
     * Find K Pairs with Smallest Sum.
     * Explanation: https://www.youtube.com/watch?v=Youk8DDnLU8
     * */
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> result = new ArrayList<>();
        PriorityQueue<long[]> queue = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));
        Set<List<Integer>> visited = new HashSet<>();

        // [nums1[0], nums2[0]] will always be selected.
        queue.add(new long[] {nums1[0] + nums2[0], 0, 0});
        visited.add(Arrays.asList(0, 0));

        while (k > 0 && !queue.isEmpty()) {
            long[] arr = queue.poll();
            int i = (int) arr[1];
            int j = (int) arr[2];

            result.add(Arrays.asList(nums1[i], nums2[j]));
            k -= 1;

            // Try with the adjacent pair.
            if (i + 1 < nums1.length && !visited.contains(Arrays.asList(i + 1, j))) {
                queue.add(new long[] {nums1[i + 1] + nums2[j], i + 1, j});
                visited.add(Arrays.asList(i + 1, j));
            }
            if (j + 1 < nums2.length && !visited.contains(Arrays.asList(i, j + 1))) {
                queue.add(new long[] {nums1[i] + nums2[j + 1], i, j + 1});
                visited.add(Arrays.asList(i, j + 1));
            }
        }

        return result;

    }
}
