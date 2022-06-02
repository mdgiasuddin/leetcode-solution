package dp;

import java.util.*;

public class DynamicProgramming2 {

    public static void main(String[] args) {

    }

    // Leetcode problem 343
    public int integerBreak(int n) {
        int[] dp = new int[n + 1];

        for (int i = 1; i <= n; i++) {

            // If i is any middle number, we can use i without breaking it
            if (i < n)
                dp[i] = i;

            // Try to get maximum by breaking
            // Since there is j & i - j, we can loop only left side
            for (int j = 1; j <= i / 2; j++) {
                dp[i] = Math.max(dp[i], dp[j] * dp[i - j]);
            }
        }

        return dp[n];
    }

    // Leetcode problem: 152
    /*
     * This solution is tricky.
     * */
    public int maxProduct(int[] nums) {
        int currentMax = 1, currentMin = 1;

        // First assume the maximum value of the array is the max product.
        int result = nums[0];
        for (int num : nums) {
            result = Math.max(result, num);
        }

        for (int num : nums) {

            // If num is 0, no need to continue with previous sub-array. reset it.
            if (num == 0) {
                currentMax = 1;
                currentMin = 1;
                continue;
            }

            // Current max and min are the maximum and minimum product respectively including
            // this number.
            int tmp = num * currentMax;
            currentMax = Math.max(num, Math.max(tmp, num * currentMin));
            currentMin = Math.min(num, Math.min(tmp, num * currentMin));

            result = Math.max(result, currentMax);
        }

        return result;
    }

    // Leetcode problem: 746
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        int first = cost[0], second = cost[1];

        for (int i = 2; i < n; i++) {
            int third = cost[i] + Math.min(first, second);
            first = second;
            second = third;
        }

        return Math.min(first, second);
    }

    // Leetcode problem: 740
    /*
     * This problem is similar to House Robber I (Leetcode problem: 198).
     * Sort the numbers and build up a counter of the elements and remove the duplicates.
     * */
    public int deleteAndEarn(int[] nums) {
        Arrays.sort(nums);

        List<Integer> uniqueNums = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();

        uniqueNums.add(nums[0]);
        int count = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1]) {
                uniqueNums.add(nums[i]);
                map.put(nums[i - 1], count);
                count = 1;
            } else {
                count++;
            }
        }

        map.put(nums[nums.length - 1], count);

        int[] dp = new int[uniqueNums.size()];
        dp[0] = uniqueNums.get(0) * map.get(uniqueNums.get(0));
        if (uniqueNums.size() > 1) {
            int included = (uniqueNums.get(1) * map.get(uniqueNums.get(1))) + (uniqueNums.get(1) - 1 == uniqueNums.get(0) ? 0 : dp[0]);
            dp[1] = Math.max(included, dp[0]);
        }

        for (int i = 2; i < uniqueNums.size(); i++) {
            int included = (uniqueNums.get(i) * map.get(uniqueNums.get(i))) + (uniqueNums.get(i) - 1 == uniqueNums.get(i - 1) ? dp[i - 2] : dp[i - 1]);

            dp[i] = Math.max(included, dp[i - 1]);

        }

        return dp[uniqueNums.size() - 1];
    }

    // Leetcode problem: 1911
    /*
     * The solution is little tricky.
     * */
    public long maxAlternatingSum(int[] nums) {
        long sumEven = 0, sumOdd = 0;

        /*
         * Even sum can be made by previous even sum or previous odd sum + num
         * Odd sum can be made by previous odd sum or previous even sum - num
         * */
        for (int num : nums) {
            long tmpEven = Math.max(sumOdd + num, sumEven);
            long tmpOdd = Math.max(sumEven - num, sumOdd);
            sumEven = tmpEven;
            sumOdd = tmpOdd;
        }

        return sumEven;
    }

    // Leetcode problem: 1866
    /*
     * If the largest stick is set to the last then 1 stick always visible.
     * If another stick smaller than the largest set to the last, the stick will always invisible.
     * So, DP[n][k] = dp[n - 1][k - 1] + (n - 1) * dp[n - 1][k].
     * */
    public int rearrangeSticks(int n, int k) {
        long[][] dp = new long[n + 1][k + 1];
        dp[1][1] = 1;
        long MOD = 1000000007;

        for (int N = 2; N <= n; N++) {
            for (int K = 1; K <= k; K++) {
                long res = (dp[N - 1][K - 1] + (N - 1) * dp[N - 1][K]) % MOD;
                dp[N][K] = res;
            }
        }

        return (int) dp[n][k];
    }
}
