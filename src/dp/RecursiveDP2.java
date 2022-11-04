package dp;

import java.util.Arrays;

public class RecursiveDP2 {
    public static void main(String[] args) {

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
}
