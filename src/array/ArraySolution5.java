package array;

import java.util.*;

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

    // Leetcode problem: 1189
    public int maxNumberOfBalloons(String text) {
        // Index and count of chars in balloon 'a', 'b', 'l', 'n', 'o'
        int[] indices = {0, 1, 11, 13, 14};
        int[] balloon = {1, 1, 2, 1, 2};
        int[] count = new int[26];

        for (int i = 0; i < text.length(); i++) {
            count[text.charAt(i) - 'a']++;

        }

        // Result is the minimum multiplication of all characters in balloon.
        int res = text.length();
        for (int i = 0; i < 5; i++) {
            res = Math.min(res, count[indices[i]] / balloon[i]);
        }

        return res;
    }

    // Leetcode problem: 2001
    /*
     * Build up count matrix for the ratio of width and height.
     * */
    public long interchangeableRectangles(int[][] rectangles) {
        Map<Double, Long> rectCount = new HashMap<>();

        for (int[] rectangle : rectangles) {
            double ratio = (rectangle[0] * 1.0) / rectangle[1];
            long c = rectCount.getOrDefault(ratio, 0L);

            rectCount.put(ratio, c + 1);
        }

        long res = 0;
        for (Map.Entry<Double, Long> entry : rectCount.entrySet()) {
            if (entry.getValue() > 1) {
                res += (entry.getValue() * (entry.getValue() - 1)) / 2;
            }
        }

        return res;
    }

    // Leetcode problem: 1985
    /*
     * Sort the number and find the minimum of all k-elements slides.
     * */
    public int minimumDifference(int[] nums, int k) {
        Arrays.sort(nums);

        int res = Integer.MAX_VALUE;
        for (int i = 0; i <= nums.length - k; i++) {
            res = Math.min(res, nums[i + k - 1] - nums[i]);
        }

        return res;
    }

    // Leetcode problem: 135
    public int candy(int[] ratings) {
        /*int[] result = new int[ratings.length];
        Arrays.fill(result, 1);

        // First traverse left to right.
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1])
                result[i] = result[i - 1] + 1;
        }

        // Then traverse right to left.
        for (int i = ratings.length - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1] && result[i] <= result[i + 1])
                result[i] = result[i + 1] + 1;
        }

        int sum = 0;

        for (int r : result)
            sum += r;

        return sum;*/

        // This is memory optimized version.

        int ans = 1; // For first index;

        int up, down, peak;
        up = down = peak = 0;
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                up++;
                peak = up;
                down = 0;

                ans += up + 1;
            } else if (ratings[i] == ratings[i - 1]) {
                up = down = peak = 0;
                ans += 1;
            } else {
                down++;
                up = 0;

                ans += down;

                // Increase the peak by 1. 1,2,3  3,2,1. To keep more candy in the previous.
                ans += (down > peak ? 1 : 0);
            }
        }

        return ans;
    }
}
