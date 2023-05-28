package dp;

public class DynamicProgramming4 {

    // Leetcode problem: 2140
    /*
     * Solving Questions With Brainpower
     * Explanation: https://www.youtube.com/watch?v=D7TD_ArkfkA
     * */
    public long mostPoints(int[][] questions) {
        int n = questions.length;
        long[] dp = new long[n + 1];

        for (int i = n - 1; i >= 0; i--) {
            dp[i] = Math.max(
                    dp[i + 1], // Skip ith question
                    questions[i][0] + (i + questions[i][1] + 1 > n ? 0 : dp[i + questions[i][1] + 1] // Solve ith question
                    ));
        }

        return dp[0];
    }
}
