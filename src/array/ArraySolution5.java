package array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class ArraySolution5 {

    public static void main(String[] args) {

    }

    // Leetcode problem: 1288
    public int removeCoveredIntervals(int[][] intervals) {
        // Sort based on start ascending. If ties then by end descending.
        Arrays.sort(intervals, (a, b) -> (a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]));

        int[] prev = intervals[0];
        int result = 1;
        for (int i = 1; i < intervals.length; i++) {
            // Test if current interval can be covered by prev interval.
            if (intervals[i][1] > prev[1]) {
                prev = intervals[i];
                result++;
            }
        }

        return result;
    }

    // Leetcode problem: 179
    public String largestNumber(int[] nums) {
        List<String> strings = new java.util.ArrayList<>(Arrays.stream(nums).mapToObj(String::valueOf).toList());

        strings.sort((a, b) -> (b + a).compareTo(a + b));

        StringBuilder result = new StringBuilder();
        for (String str : strings) {
            result.append(str);
        }

        if (result.toString().startsWith("0"))
            return "0";
        return result.toString();
    }

    // Leetcode problem: 88
    /*
     * Merge from the last position.
     * */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int last = m + n - 1;
        while (m > 0 && n > 0) {
            if (nums2[n - 1] >= nums1[m - 1]) {
                nums1[last] = nums2[n - 1];
                n--;
            } else {
                nums1[last] = nums1[m - 1];
                m--;
            }
            last--;
        }

        // If second array haven't been merged yet, merge them.
        while (n > 0) {
            nums1[last--] = nums2[--n];
        }
    }

    // Leetocode problem: 1096
    /*
     * Build up a max priority queue to get maximum number every time.
     * */
    public int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> b - a);

        for (int stone : stones) {
            queue.add(stone);
        }

        while (queue.size() > 1) {
            int first = queue.poll();
            int second = queue.poll();

            if (first > second) {
                queue.add(first - second);
            }
        }

        return queue.isEmpty() ? 0 : queue.poll();
    }

    // Leetcode problem: 605
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        if (n == 0)
            return true;

        for (int i = 0; i < flowerbed.length; i++) {

            // Check if a flower can be placed.
            if (flowerbed[i] == 0 && (i == 0 || flowerbed[i - 1] == 0)
                    && (i == flowerbed.length - 1 || flowerbed[i + 1] == 0)) {
                flowerbed[i] = 1;
                n--;
            }
        }

        return n <= 0;
    }

    // Leetcode problem: 448
    /*
     * For every number make its index element negative.
     * Then check in which index numbers are still positive.
     * */
    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> result = new ArrayList<>();

        for (int num : nums) {
            int index = Math.abs(num) - 1;

            if (nums[index] > 0)
                nums[index] = -nums[index];
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                result.add(i + 1);
            }
        }

        return result;
    }
}
