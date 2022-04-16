package dp;

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
}
