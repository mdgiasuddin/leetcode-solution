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

    // Leetcode problem: 31
    /*
     * Next Permutation.
     * Explanation: https://www.youtube.com/watch?v=6qXO72FkqwM
     * */
    public void nextPermutation(int[] nums) {
        int n = nums.length;
        int i = n - 1;

        while (i > 0) {
            if (nums[i] > nums[i - 1]) {
                break;
            }

            i -= 1;
        }

        // No peek found. Reverse the array.
        if (i == 0) {
            reverseArray(nums, 0, n - 1);
            return;
        }

        /*
         * Smaller element is in the index (i - 1). Find the greater element than [i - 1] on the right side.
         * Swap with [i - 1]
         * Reverse the right side of i - 1 so that it will be ascending order.
         * */
        for (int j = n - 1; j >= i; j--) {
            if (nums[j] > nums[i - 1]) {
                int tmp = nums[i - 1];
                nums[i - 1] = nums[j];
                nums[j] = tmp;
                break;
            }
        }
        reverseArray(nums, i, n - 1);
    }

    private void reverseArray(int[] array, int l, int r) {
        while (l < r) {
            int tmp = array[l];
            array[l] = array[r];
            array[r] = tmp;

            l += 1;
            r -= 1;
        }
    }
}
