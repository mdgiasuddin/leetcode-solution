package array;

public class NumMatrix {

    // Leetcode problem: 304
    int[][] dp;

    public NumMatrix(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;

        dp = new int[m + 1][n + 1];

        for (int r = 1; r <= m; r++)
            dp[r][0] = dp[r - 1][0] + matrix[r - 1][0];

        for (int c = 1; c <= n; c++)
            dp[0][c] = dp[0][c - 1] + matrix[0][c - 1];

        for (int r = 1; r <= m; r++) {
            for (int c = 1; c <= n; c++) {
                dp[r][c] = matrix[r - 1][c - 1] + dp[r - 1][c] + dp[r][c - 1] - dp[r - 1][c - 1];
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        return dp[row2 + 1][col2 + 1] - dp[row1][col2 + 1] - dp[row2 + 1][col1] + dp[row1][col1];
    }
}
