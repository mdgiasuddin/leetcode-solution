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
        if (d == 1) {
            int max = 0;
            while (idx < jobDifficulty.length) {
                max = Math.max(max, jobDifficulty[idx++]);
            }

            return max;
        }
        if (dp[d][idx] != -1) {
            return dp[d][idx];
        }

        int max = 0;
        int result = Integer.MAX_VALUE;
        for (int i = idx; i < jobDifficulty.length - d + 1; i++) {
            max = Math.max(max, jobDifficulty[i]);
            result = Math.min(result, max + dfsDifficulty(i + 1, jobDifficulty, d - 1, dp));
        }

        return dp[d][idx] = result;
    }
}
