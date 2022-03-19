package array;

import java.util.Arrays;

public class ArraySolution4 {
    public static void main(String[] args) {
        ArraySolution4 arraySolution4 = new ArraySolution4();
    }

    // Leetcode problem: 240
    /*
     * Start from top-right or bottom left index
     * Compare with target
     * Move according to the target value
     * */
    public boolean searchMatrix(int[][] matrix, int target) {
        int row = 0, col = matrix[0].length - 1;

        while (row < matrix.length && col >= 0) {
            if (matrix[row][col] == target) {
                return true;
            }
            if (target < matrix[row][col]) {
                col--;
            } else {
                row++;
            }
        }

        // This can also be done starting from bottom-left index
        /*int row = matrix.length - 1, col = 0;

        while (row >= 0 && col < matrix[0].length) {
            if (matrix[row][col] == target) {
                return true;
            }
            if (target < matrix[row][col]) {
                row--;
            } else {
                col++;
            }
        }*/

        return false;
    }

    // Leetcode problem: 274
    /*
     * A scientist has an index h if h of their n papers have at least h citations each
     * */
    public int hIndex(int[] citations) {
        Arrays.sort(citations);
        int left = 0, n = citations.length, right = n - 1, ans = 0;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (citations[mid] < n - mid) {
                // Is not h index, so move right
                left = mid + 1;
            } else {
                // Is h index, update answer and try for greater h index
                ans = n - mid;
                right = mid - 1;
            }
        }

        return ans;
    }

    // Leetcode problem: 289
    // Leetcode problem: 304
    // Leetcode problem: 322
}
