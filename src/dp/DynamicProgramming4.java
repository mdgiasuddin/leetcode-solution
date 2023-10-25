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

    // Leetcode problem: 887
    /*
     * Super Egg Drop.
     * Explanation: https://www.geeksforgeeks.org/egg-dropping-puzzle-dp-11/
     * TO-DO -> Optimize the solution as it exceeds the time limit.
     * */
    public int superEggDrop(int k, int n) {
        int[][] dp = new int[k + 1][n + 1];
        for (int i = 1; i <= k; i++) {
            dp[i][1] = 1;
            dp[i][0] = 0;
        }
        for (int i = 1; i <= n; i++) {
            dp[1][i] = i;
        }

        for (int i = 2; i <= k; i++) {
            for (int j = 2; j <= n; j++) {
                dp[i][j] = Integer.MAX_VALUE;
                for (int l = 1; l <= j; l++) {
                    dp[i][j] = Math.min(
                            dp[i][j],
                            1 + Math.max(dp[i - 1][l - 1], dp[i][j - l])
                    );
                }
            }
        }

        return dp[k][n];
    }

    // Leetcode problem: 688
    /*
     * Knight Probability in Chessboard.
     * Explanation: https://www.youtube.com/watch?v=54nJhM2AZv4
     * Maintain 2 state => current & next.
     * */
    public double knightProbability(int n, int k, int row, int column) {
        double[][] current = new double[n][n];
        double[][] next = new double[n][n];

        int[][] dirs = {
                {-2, -1}, {-1, -2}, {-2, 1}, {-1, 2}, {2, -1}, {1, -2}, {2, 1}, {1, 2}
        };

        current[row][column] = 1.0;

        for (int i = 0; i < k; i++) {
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++) {
                    if (current[r][c] != 0) {
                        for (int[] dir : dirs) {
                            int x = r + dir[0];
                            int y = c + dir[1];

                            if (x >= 0 && x < n && y >= 0 && y < n) {
                                next[x][y] += current[r][c] / 8;
                            }
                        }
                    }
                }
            }
            current = next;
            next = new double[n][n];
        }

        double res = 0;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                res += current[r][c];
            }
        }

        return res;
    }

    // Leetcode problem: 1043
    /*
     * Partition Array for Maximum Sum.
     * Explanation: https://www.youtube.com/watch?v=YtOzNodX_aw
     * This solution is tricky.
     * */
    public int maxSumAfterPartitioning(int[] arr, int k) {
        int n = arr.length;
        int[] dp = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            int max = Integer.MIN_VALUE;
            int best = Integer.MIN_VALUE;

            for (int j = 1; j <= k && i - j >= 0; j++) {
                /*
                 * arr = [1,15,7,9,2,5,10], k = 3
                 * For element 9 => dp[0, 1, 30, 45, ...]
                 * j = 1 => keep 9 single => dp[i] = 9 + 45.
                 * j = 2 => (9, 7) => 30 + 2 * 9
                 * j = 3 => (9, 7, 15) => 1 + 3 * 15
                 * So, the best result is 9 + 45 = 54.
                 * */
                max = Math.max(max, arr[i - j]);
                best = Math.max(best, dp[i - j] + max * j);
            }
            dp[i] = best;
        }

        return dp[n];
    }
}
