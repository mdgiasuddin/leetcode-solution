package dp;

import java.util.Arrays;
import java.util.List;

public class DynamicProgramming {

    public static void main(String[] args) {

    }

    // Leetcode problem: 62
    /*
     * dp[i][j] = dp[i-1][j] + dp[i][j-1]
     * Either from left or from up
     * */
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];

        for (int row = 0; row < m; row++) {
            dp[row][0] = 1;
        }

        for (int col = 0; col < n; col++) {
            dp[0][col] = 1;
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }

        return dp[m - 1][n - 1];

        // This problem can be solved mathematically
        // It is (m+n)Cm

//        int N = m + n - 2, r = Math.min(m, n) - 1;
//        if (N <= 1)
//            return 1;
//
//        Long result = 1L;
//        for (int i = 1; i <= r; i++) {
//            result = (result * (N - i + 1)) / i;
//        }
//
//        return result.intValue();
    }

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length, n = obstacleGrid[0].length;

        // If there is obstacles in first position then it is impossible to move forward
        if (obstacleGrid[0][0] == 1) {
            return 0;
        }

        int[][] dp = new int[m][n];
        dp[0][0] = 1;

        for (int row = 1; row < m; row++) {

            // If there is any obstacles in first column then next position cannot be reached
            if (obstacleGrid[row][0] == 0 && dp[row - 1][0] == 1) {
                dp[row][0] = 1;
            }
        }

        for (int col = 1; col < n; col++) {
            if (obstacleGrid[0][col] == 0 && dp[0][col - 1] == 1) {
                dp[0][col] = 1;
            }
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 0) {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }

        return dp[m - 1][n - 1];
    }

    // Leetcode problem: 70
    /*
     * This is same as fibonacci series
     * */
    public int climbStairs(int n) {
        if (n <= 2)
            return n;

        int fibN_2 = 1, fibN_1 = 2, fibN = 2;

        for (int i = 3; i <= n; i++) {
            fibN = fibN_1 + fibN_2;
            fibN_2 = fibN_1;
            fibN_1 = fibN;
        }

        return fibN;
    }

    // Leetcode problem: 120
    /*
     *           [[2],[3,4],[6,5,7],[4,1,8,3]]
     *                       2
     *                     3   4
     *                    6  5  7
     *                   4  1  8 3
     * In the last level sum is equal to the element
     * Then calculate the sum for middle element
     * */
    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[] dp = new int[triangle.size()];
        for (int i = 0; i < n; i++)
            dp[i] = triangle.get(n - 1).get(i);

        for (int i = triangle.size() - 2; i >= 0; i--) {
            for (int j = 0; j < triangle.get(i).size(); j++) {
                dp[j] = Math.min(dp[j], dp[j + 1]) + triangle.get(i).get(j);
            }
        }

        return dp[0];
    }

    // Leetcode problem: 279
    /*
     * Build up a dp table
     * dp[i] = minimum of 1 + dp[i-j*j] for any i-j*j >= 0
     * 1 for 1 square j*j and others for dp[i-j*j]
     * */
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; i - j * j >= 0; j++) {
                dp[i] = Math.min(dp[i], 1 + dp[i - j * j]);
            }
        }
        return dp[n];
    }
}
