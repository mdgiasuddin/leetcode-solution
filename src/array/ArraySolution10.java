package array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ArraySolution10 {
    public static void main(String[] args) {
        ArraySolution10 sol = new ArraySolution10();
        int[] nums = {1, -3, 2, 3, -4};
        System.out.println(sol.maxAbsoluteSum(nums));
    }

    // Leetcode problem: 2033

    /**
     * Minimum Operations to Make a Uni-Value Grid.
     * Explanation: https://www.youtube.com/watch?v=2LfVNDlx8mY
     */
    public int minOperations(int[][] grid, int x) {
        int m = grid.length;
        int n = grid[0].length;
        int total = 0;
        int elements = m * n;

        List<Integer> list = new ArrayList<>(elements);
        for (int[] row : grid) {
            for (int num : row) {
                if (num % x != grid[0][0] % x) {
                    return -1;
                }
                list.add(num / x);
                total += num / x;
            }
        }
        list.sort(Integer::compareTo);
        int res = Integer.MAX_VALUE;
        int prefix = 0;
        for (int i = 0; i < elements; i++) {
            int left = list.get(i) * i - prefix;
            int right = total - prefix - list.get(i) * (elements - i);
            res = Math.min(res, left + right);
            prefix += list.get(i);
        }

        return res;
    }


    // Leetcode problem: 3394

    /**
     * Check if Grid can be Cut into Sections.
     * Explanation: https://www.youtube.com/watch?v=X9QtxzsAsYo
     * This problem is similar to: Non-overlapping Intervals (Leetcode problem: 435).
     * The value of n will be ignored.
     */
    public boolean checkValidCuts(int n, int[][] rectangles) {
        Arrays.sort(rectangles, Comparator.comparingInt(a -> a[0]));

        int cutX = 0;
        int prev = rectangles[0][2];
        for (int i = 1; i < rectangles.length; i++) {
            if (prev <= rectangles[i][0]) {
                cutX += 1;
            }
            prev = Math.max(prev, rectangles[i][2]);
        }

        if (cutX >= 2) {
            return true;
        }

        Arrays.sort(rectangles, Comparator.comparingInt(a -> a[1]));
        int cutY = 0;
        prev = rectangles[0][3];

        for (int i = 1; i < rectangles.length; i++) {
            if (prev <= rectangles[i][1]) {
                cutY += 1;
            }
            prev = Math.max(prev, rectangles[i][3]);
        }

        return cutY >= 2;
    }

    /**
     * Count Days Without Meetings.
     * This problem is similar to: Merge Intervals (Leetcode problem: 56).
     */
    public int countDays(int days, int[][] meetings) {
        Arrays.sort(meetings, Comparator.comparingInt(a -> a[0]));
        int[] prev = meetings[0];
        int occupied = 0;

        for (int i = 1; i < meetings.length; i++) {
            if (prev[1] < meetings[i][0]) {
                occupied += prev[1] - prev[0] + 1;
                prev = meetings[i];
            } else {
                prev[1] = Math.max(prev[1], meetings[i][1]);
            }
        }
        occupied += prev[1] - prev[0] + 1;

        return days - occupied;
    }

    // Leetcode problem: 3289

    /**
     * The Two Sneaky Numbers of Digitville.
     * This problem is similar to: Single Number III (Leetcode problem: 260)
     */
    public int[] getSneakyNumbers(int[] nums) {
        int xor = 0;
        for (int num : nums) {
            xor ^= num;
        }

        for (int i = 0; i < nums.length - 2; i++) {
            xor ^= i;
        }

        xor &= -xor;
        int[] res = new int[2];
        for (int num : nums) {
            if ((num & xor) == 0) {
                res[0] ^= num;
            } else {
                res[1] ^= num;
            }
        }

        for (int i = 0; i < nums.length - 2; i++) {
            if ((i & xor) == 0) {
                res[0] ^= i;
            } else {
                res[1] ^= i;
            }
        }

        return res;
    }

    // Leetcode problem: 2523

    /**
     * Closest Prime Numbers in Range.
     * Find out all the prime numbers [1, right] using Sieve Algorithm.
     * Then check the pair with minimum difference in the range [left, right].
     */
    public int[] closestPrimes(int left, int right) {
        boolean[] composites = new boolean[right + 1];
        composites[1] = true;

        for (int i = 2; i * i <= right; i++) {
            if (!composites[i]) {
                for (int j = 2; i * j <= right; j++) {
                    composites[i * j] = true;
                }
            }
        }

        int num1 = -1;
        int num2 = -1;
        int prev = -1;
        for (int i = left; i <= right; i++) {
            if (!composites[i]) {
                if (num1 == -1) {
                    num1 = i;
                } else if (num2 == -1) {
                    num2 = i;
                } else if (i - prev < num2 - num1) {
                    num1 = prev;
                    num2 = i;
                }

                prev = i;
            }
        }

        return (num1 == -1 || num2 == -1) ? new int[]{-1, -1} : new int[]{num1, num2};
    }

    // Leetcode problem: 1749

    /**
     * Maximum Absolute Sum of Any Subarray.
     */
    public int maxAbsoluteSum(int[] nums) {
        int res = Math.abs(nums[0]);
        int maxSum = nums[0];
        int minSum = nums[0];

        for (int i = 1; i < nums.length; i++) {
            maxSum = Math.max(nums[i], maxSum + nums[i]);
            minSum = Math.min(nums[i], minSum + nums[i]);

            res = Math.max(res, Math.max(Math.abs(maxSum), Math.abs(minSum)));
        }

        return res;
    }
}
