package dp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecursiveDP2 {
    public static void main(String[] args) {
        RecursiveDP2 recursiveDP2 = new RecursiveDP2();
        int[] nums = {8828, 9581, 49, 9818, 9974, 9869, 9991, 10000, 10000, 10000, 9999, 9993, 9904, 8819, 1231, 6309};
        System.out.println(recursiveDP2.minOperations(nums, 134365));
    }

    // Leetcode problem: 309
    /*
     * Code source: https://www.youtube.com/watch?v=4wNXkhAky3s
     * */
    public int maxProfit(int[] prices) {

        // This is the efficient solution.
        /*
        int n = prices.length;
        int [] noStock = new int[n];
        int [] bought = new int[n];
        int [] sold = new int[n];

        bought[0] = -prices[0];

        for (int i = 1; i < n; i++) {
            noStock[i] = Math.max(noStock[i - 1], sold[i - 1]);
            bought[i] = Math.max(bought[i - 1], noStock[i - 1] - prices[i]);
            sold[i] = bought[i - 1] + prices[i];
        }

        return Math.max(noStock[n - 1], sold[n - 1]);
        */

        int[] dp = new int[prices.length];
        Arrays.fill(dp, -1);

        return maxProfit(0, dp, prices);
    }

    public int maxProfit(int idx, int[] dp, int[] prices) {
        if (idx >= prices.length) {
            return 0;
        }

        if (dp[idx] != -1) {
            return dp[idx];
        }

        int maxVal = 0;
        for (int i = idx + 1; i < prices.length; i++) {
            if (prices[i] > prices[idx]) {
                maxVal = Math.max(maxVal, prices[i] - prices[idx] + maxProfit(i + 2, dp, prices));
            }
        }

        maxVal = Math.max(maxVal, maxProfit(idx + 1, dp, prices));
        dp[idx] = maxVal;
        return maxVal;
    }

    // Leetcode problem: 714
    /*
     * This solution is similar to the efficient approach of buy, sell with cool down -
     * Leetcode problem: 309
     * */
    public int maxProfit(int[] prices, int fee) {
        int n = prices.length;
        int[] buy = new int[n];
        int[] sell = new int[n];

        buy[0] = -prices[0] - fee;
        for (int i = 1; i < n; i++) {
            buy[i] = Math.max(buy[i - 1], sell[i - 1] - prices[i] - fee);
            sell[i] = Math.max(sell[i - 1], buy[i - 1] + prices[i]);
        }

        return sell[n - 1];
    }

    // Leetcode problem: 1658
    /*
     * Minimum Operations to Reduce X to Zero.
     * Recursive solution exceeds the time limit.
     * Optimize the code like Sub-array Sum Equals K (Leetcode problem: 560).
     * Find the longest sub-array having sum (total sum - X).
     * The result will be the other elements not in the sub-array.
     * */
    public int minOperations(int[] nums, int x) {

        Map<Integer, Integer> map = new HashMap<>();
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        int target = sum - x;

        int maxLength = -1;
        int currentSum = 0;
        map.put(0, -1);
        for (int i = 0; i < nums.length; i++) {
            currentSum += nums[i];
            if (map.containsKey(currentSum - target)) {
                maxLength = Math.max(maxLength, i - map.get(currentSum - target));
            }
            map.putIfAbsent(currentSum, i);
        }

        return maxLength == -1 ? -1 : nums.length - maxLength;
//        int res = minOperations(nums, x, 0, 0, nums.length - 1, new HashMap<>());
//
//        return res > nums.length ? -1 : res;
    }

    public int minOperations(int[] nums, int x, int count, int left, int right, Map<List<Integer>, Integer> map) {
        if (x == 0) {
            return count;
        }
        if (left > right || x < 0) {
            return nums.length + 1;
        }

        List<Integer> list = Arrays.asList(left, right, count);
        if (map.containsKey(list)) {
            return map.get(list);
        }

        int res = Math.min(minOperations(nums, x - nums[left], count + 1, left + 1, right, map), minOperations(nums, x - nums[right], count + 1, left, right - 1, map));

        map.put(list, res);
        return res;
    }

    // Leetcode problem: 1335
    /*
     * Minimum Difficulty of a Job Schedule.
     * Code source: https://www.youtube.com/watch?v=pmQAtRZ3CuE
     * */
    public int minDifficulty(int[] jobDifficulty, int d) {
        int n = jobDifficulty.length;
        if (n < d) {
            return -1;
        }
        int[][] dp = new int[d + 1][n];
        for (int[] p : dp) {
            Arrays.fill(p, -1);
        }

        return dfsDifficulty(0, jobDifficulty, d, dp);
    }

    public int dfsDifficulty(int idx, int[] jobDifficulty, int d, int[][] dp) {
        if (idx == jobDifficulty.length) {
            return 0;
        }
        if (dp[d][idx] != -1) {
            return dp[d][idx];
        }
        if (d == 1) {
            int max = 0;
            while (idx < jobDifficulty.length) {
                max = Math.max(max, jobDifficulty[idx++]);
            }

            return max;
        }

        int max = 0;
        int result = Integer.MAX_VALUE;
        for (int i = idx; i < jobDifficulty.length - d + 1; i++) {
            max = Math.max(max, jobDifficulty[i]);
            result = Math.min(result, max + dfsDifficulty(i + 1, jobDifficulty, d - 1, dp));
        }

        return dp[d][idx] = result;
    }

    // Leetcode problem: 741
    /*
     * Cherry Pickup.
     * Explanation: https://www.youtube.com/watch?v=ZV0sUzfA7Eg
     * */
    public int cherryPickup(int[][] grid) {
        int n = grid.length;
        int[][][] dp = new int[n][n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    dp[i][j][k] = -1;
                }
            }
        }

        return Math.max(0, cherryPickup(0, 0, 0, grid, dp));
    }

    private int cherryPickup(int r1, int c1, int r2, int[][] grid, int[][][] dp) {
        int c2 = r1 + c1 - r2;
        if (r1 >= grid.length || c1 >= grid[0].length || r2 >= grid.length || c2 >= grid[0].length || grid[r1][c1] == -1 || grid[r2][c2] == -1) {
            return Integer.MIN_VALUE;
        }

        if (r1 == grid.length - 1 && c1 == grid[0].length - 1) {
            return grid[r1][c1];
        }

        if (dp[r1][c1][r2] != -1) {
            return dp[r1][c1][r2];
        }

        int cherries = (r1 == r2 && c1 == c2) ? grid[r1][c1] : grid[r1][c1] + grid[r2][c2];
        int hh = cherryPickup(r1, c1 + 1, r2, grid, dp);
        int hv = cherryPickup(r1, c1 + 1, r2 + 1, grid, dp);
        int vh = cherryPickup(r1 + 1, c1, r2, grid, dp);
        int vv = cherryPickup(r1 + 1, c1, r2 + 1, grid, dp);

        cherries += Math.max(Math.max(hh, hv), Math.max(vh, vv));
        dp[r1][c1][r2] = cherries;

        return cherries;
    }

    public int findMaxForm(String[] strs, int m, int n) {
        return findMaxForm(strs, m, n, 0, new HashMap<>());
    }

    // Leetcode problem: 474
    /*
     * Ones and Zeroes.
     * Explanation: https://www.youtube.com/watch?v=miZ3qV04b1g
     * */
    private int findMaxForm(String[] strs, int m, int n, int idx, Map<String, Integer> dp) {
        if (idx >= strs.length) {
            return 0;
        }

        String key = m + ":" + n + ":" + idx;
        if (dp.containsKey(key)) {
            return dp.get(key);
        }

        int res = findMaxForm(strs, m, n, idx + 1, dp);
        int[] count = countZeroAndOne(strs[idx]);
        if (count[0] <= m && count[1] <= n) {
            res = Math.max(res, 1 + findMaxForm(strs, m - count[0], n - count[1], idx + 1, dp));
        }

        dp.put(key, res);
        return res;

    }

    private int[] countZeroAndOne(String str) {
        int[] count = new int[2];

        for (int i = 0; i < str.length(); i++) {
            count[str.charAt(i) - '0'] += 1;
        }

        return count;
    }
}
