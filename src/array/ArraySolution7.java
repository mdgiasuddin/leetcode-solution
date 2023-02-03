package array;

import java.util.*;

public class ArraySolution7 {

    // Leetcode problem: 60
    /*
     * Permutation Sequence.
     * Explanation: https://www.youtube.com/watch?v=W9SIlE2jhBQ&list=PLEJXowNB4kPzC3OYy2LRovf_xb8JjAMEF&index=2
     * */
    public String getPermutation(int n, int k) {
        int[] factorial = new int[n];
        List<Integer> digits = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            digits.add(i);
        }

        factorial[0] = 1;
        for (int i = 1; i < n; i++) {
            factorial[i] = factorial[i - 1] * i;
        }

        StringBuilder res = new StringBuilder();
        int pos = n;

        // Run the loop until the last digit.
        while (pos > 1) {
            int blockSize = factorial[pos - 1];

            // Get the ceiling.
            int idx = (k + blockSize - 1) / blockSize;
            res.append(digits.get(idx));
            digits.remove(idx);
            k -= blockSize * (idx - 1);
            pos -= 1;
        }

        // Add the last digit.
        res.append(digits.get(1));

        return res.toString();
    }

    // Leetcode problem: 149
    /*
     * Max Points on a Line.
     * Calculate slope of every point with ith point and count the max.
     * */
    public int maxPoints(int[][] points) {
        int n = points.length;
        int res = 1;
        for (int i = 0; i < n - 1; i++) {
            int[] point1 = points[i];
            Map<Double, Integer> map = new HashMap<>();
            for (int j = i + 1; j < n; j++) {
                int[] point2 = points[j];

                double slope;
                if (point1[0] == point2[0]) {
                    slope = Double.MAX_VALUE;
                } else {
                    slope = 1.0 * (point2[1] - point1[1]) / (point2[0] - point1[0]);
                }

                // Be careful in this line.
                if (slope == -0.0) {
                    slope = 0.0;
                }
                int count = map.getOrDefault(slope, 0) + 1;
                map.put(slope, count);
                res = Math.max(res, count + 1);
            }
        }

        return res;
    }

    // Leetcode problem: 2515
    /*
     * Shortest Distance to Target String in a Circular Array.
     * */
    public int closetTarget(String[] words, String target, int startIndex) {
        int n = words.length;
        int ans = n;

        for (int i = 0; i < n; i++) {
            if (words[i].equals(target)) {

                // Minimum difference lies in either side. Array is circular. So take the %.
                ans = Math.min(ans, Math.min((i - startIndex + n) % n, (startIndex - i + n) % n));
            }
        }

        return ans == n ? -1 : ans;
    }

    // Leetcode problem: 498
    /*
     * Diagonal Traverse.
     * Explanation: https://www.youtube.com/watch?v=Njt7aZYq0wA&list=PLy38cn8b_xMfO7CGsUDIsYGps37yKaQ9X&index=18
     * For all the diagonal element (i + j) is the same.
     * For (i + j) is even then down-left to up-right & for odd then up-right to down-left.
     * */
    public int[] findDiagonalOrder(int[][] mat) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        int m = mat.length;
        int n = mat[0].length;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                List<Integer> list = map.getOrDefault(i + j, new ArrayList<>());
                list.add(mat[i][j]);
                map.put(i + j, list);
            }
        }

        int[] res = new int[m * n];
        int k = 0;
        for (int i = 0; i <= m + n - 2; i++) {
            List<Integer> list = map.get(i);

            if (i % 2 == 1) {
                // Same order.
                for (int num : list) {
                    res[k++] = num;
                }
            } else {
                // Reverse order.
                for (int j = list.size() - 1; j >= 0; j--) {
                    res[k++] = list.get(j);
                }
            }
        }

        return res;
    }

    // Leetcode problem: 974
    /*
     * Subarray Sums Divisible by K.
     * This is similar to: Subarray Sum Equals K (Leetcode problem: 560).
     * Challenging part is to avoid negative reminder.
     * */
    public int subarraysDivByK(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        int res = 0;
        map.put(0, 1);
        int currentSum = 0;

        for (int num : nums) {
            currentSum = ((currentSum + num % k) % k + k) % k;
            int count = map.getOrDefault(currentSum, 0);
            res += count;
            map.put(currentSum, count + 1);
        }

        return res;
    }

    // Leetcode problem: 896
    /*
     * Monotonic Array.
     * Explanation: https://www.youtube.com/watch?v=BYH6dreENEA&list=PLy38cn8b_xMfO7CGsUDIsYGps37yKaQ9X&index=21
     * */
    public boolean isMonotonic(int[] nums) {
        boolean increasing = false;
        boolean decreasing = false;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                increasing = true;
            } else if (nums[i] < nums[i - 1]) {
                decreasing = true;
            }

            if (increasing && decreasing) {
                return false;
            }
        }

        return true;
    }

    // Leetcode problem: 462
    /*
     * Minimum Moves to Equal Array Elements II.
     * Distance from the median is always minimum.
     * */
    public int minMoves2(int[] nums) {
        Arrays.sort(nums);

        int median = nums[nums.length / 2];
        int res = 0;

        for (int num : nums) {
            res += Math.abs(num - median);
        }

        return res;
    }
}
