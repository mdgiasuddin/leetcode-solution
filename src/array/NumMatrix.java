package array;

public class NumMatrix {

    // Leetcode problem: 304
    int[][] sum;

    public NumMatrix(int[][] matrix) {
        int ROW = matrix.length;
        int COL = matrix[0].length;

        sum = new int[ROW][COL];
        sum[0][0] = matrix[0][0];

        for (int r = 1; r < ROW; r++) {
            sum[r][0] = matrix[r][0] + sum[r - 1][0];
        }

        for (int c = 1; c < COL; c++) {
            sum[0][c] = matrix[0][c] + sum[0][c - 1];
        }

        for (int r = 1; r < ROW; r++) {
            for (int c = 1; c < COL; c++) {
                sum[r][c] = matrix[r][c] + sum[r - 1][c] + sum[r][c - 1] - sum[r - 1][c - 1];
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        return sum[row2][col2] - (row1 == 0 ? 0 : sum[row1 - 1][col2]) - (col1 == 0 ? 0 : sum[row2][col1 - 1]) + (row1 == 0 || col1 == 0 ? 0 : sum[row1 - 1][col1 - 1]);
    }
}
