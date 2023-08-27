package array;

public class ArraySolution8 {

    // Leetcode problem: 764
    /*
     * Largest Plus Sign.
     * Explanation: https://www.youtube.com/watch?v=Sa5SUrf04g0&list=PLEvw47Ps6OBDcuLQ8FJZ7gc8rLOVXMjbL&index=7
     * Calculate prefix sum of four sides => left, right, up, down.
     * Take the minimum length of the four sides for each index.
     * */
    public int orderOfLargestPlusSign(int n, int[][] mines) {
        int[][] left = new int[n][n];
        int[][] right = new int[n][n];
        int[][] up = new int[n][n];
        int[][] down = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                left[i][j] = right[i][j] = up[i][j] = down[i][j] = 1;
            }
        }

        for (int[] mine : mines) {
            left[mine[0]][mine[1]] = right[mine[0]][mine[1]] = up[mine[0]][mine[1]] = down[mine[0]][mine[1]] = 0;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < n; j++) {
                left[i][j] = left[i][j] == 1 ? left[i][j] + left[i][j - 1] : 0;
                right[i][n - j - 1] = right[i][n - j - 1] == 1 ? right[i][n - j - 1] + right[i][n - j] : 0;
            }
        }

        for (int j = 0; j < n; j++) {
            for (int i = 1; i < n; i++) {
                up[i][j] = up[i][j] == 1 ? up[i][j] + up[i - 1][j] : 0;
                down[n - i - 1][j] = down[n - i - 1][j] == 1 ? down[n - i - 1][j] + down[n - i][j] : 0;
            }
        }

        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res = Math.max(res, Math.min(Math.min(left[i][j], right[i][j]), Math.min(up[i][j], down[i][j])));
            }
        }

        return res;
    }
}
