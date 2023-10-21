package dp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // Leetcode problem: 446
    /*
     * Arithmetic Slices II - Subsequence
     * Explanation: https://www.youtube.com/watch?v=YiQYhXorMAI
     * */
    public int numberOfArithmeticSlices(int[] nums) {
        int n = nums.length;
        List<Map<Integer, Integer>> dp = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            dp.add(new HashMap<>());
        }

        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i - 1; j >= 0; j--) {
                long diff = nums[i];
                diff -= nums[j];

                if (diff < Integer.MIN_VALUE || diff > Integer.MAX_VALUE) {
                    continue;
                }

                int count1 = dp.get(i).getOrDefault((int) diff, 0);
                int count2 = dp.get(j).getOrDefault((int) diff, 0);
                dp.get(i).put((int) diff, count1 + count2 + 1);
                res += count2;
            }
        }

        return res;
    }

    // Leetcode problem: 718
    /*
     * Maximum Length of Repeated Subarray.
     * */
    public int findLength(int[] nums1, int[] nums2) {
        int n1 = nums1.length;
        int n2 = nums2.length;

        int res = 0;
        int[][] dp = new int[n1 + 1][n2 + 1];

        for (int i = 1; i <= n1; i++) {
            for (int j = 1; j <= n2; j++) {
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                    res = Math.max(res, dp[i][j]);
                }
            }
        }

        return res;
    }

    // Leetcode problem: 650
    /*
     * 2 Keys Keyboard.
     * */
    public int minSteps(int n) {
        if (n == 1) {
            return 0;
        }
        if (n < 4) {
            return n;
        }

        int[] dp = new int[n + 1];
        dp[0] = dp[1] = 0;
        dp[2] = 2;
        dp[3] = 3;

        for (int i = 4; i <= n; i++) {
            dp[i] = i;
            for (int j = 2; j <= i / 2; j++) {
                if (i % j == 0) {
                    // Create j-length string and copy-paste.
                    // 1 step for copy & (i / j) - 1 step for paste. Total => (i / j)
                    dp[i] = Math.min(dp[i], dp[j] + (i / j));
                }

            }

        }

        return dp[n];
    }

    // Leetcode problem: 583
    /*
     * Delete Operation for Two Strings.
     * Find the LCS. Then return the sum of deletion from 2 strings.
     * */
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return (m - dp[m][n]) + (n - dp[m][n]);
    }
}
