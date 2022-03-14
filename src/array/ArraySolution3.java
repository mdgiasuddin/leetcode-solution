package array;

import indefinite.TreeNode;

import java.util.HashMap;
import java.util.Map;

public class ArraySolution3 {

    public static void main(String[] args) {
        ArraySolution3 arraySolution3 = new ArraySolution3();

    }

    // Leetcode problem: 106
    /*
     * Build tree from postorder and inorder traversal
     * */
    int index;

    public TreeNode buildTree(int[] inorder, int[] postorder, int left, int right, Map<Integer, Integer> map) {
        if (left > right)
            return null;

        TreeNode root = new TreeNode(postorder[index]);
        int inorderIndex = map.get(postorder[index]);
        index--;
        root.right = buildTree(inorder, postorder, inorderIndex + 1, right, map);
        root.left = buildTree(inorder, postorder, left, inorderIndex - 1, map);

        return root;
    }

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        index = postorder.length - 1;
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }

        return buildTree(inorder, postorder, 0, postorder.length - 1, map);
    }

    // Leetcode problem: 64
    /*
     * Since move directions are only right and down, sum distance of every index will be gid[i][j] + Min(left, up)
     * */
    public int minPathSum(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] dp = new int[m][n];

        dp[0][0] = grid[0][0];

        for (int col = 1; col < n; col++) {
            dp[0][col] += (grid[0][col] + dp[0][col - 1]);
        }

        for (int row = 1; row < m; row++) {
            dp[row][0] += (grid[row][0] + dp[row - 1][0]);
        }

        for (int row = 1; row < m; row++) {
            for (int col = 1; col < n; col++) {
                dp[row][col] = grid[row][col] + Math.min(dp[row - 1][col], dp[row][col - 1]);
            }
        }

        return dp[m - 1][n - 1];
    }

    // Leetcode problem
    /*
     * Take 2 arrays to save the rowZero and colZero
     * Then make the position to
     * Memory optimization can be possible
     * We can store rowZero to first row and colZero to First Column
     * [0][0] is overlapping, So take 2 extra variable to store the 0'th row and 0'th column
     * */
    public void setZeroes(int[][] matrix) {

        int rowZero = 1, colZero = 1, m = matrix.length, n = matrix[0].length;

        // Traverse the first row to find any 0
        for (int col = 0; col < n; col++) {
            if (matrix[0][col] == 0) {
                rowZero = 0;
                break;
            }
        }

        // Traverse the first column to find any 0
        for (int row = 0; row < m; row++) {
            if (matrix[row][0] == 0) {
                colZero = 0;
                break;
            }
        }

        for (int row = 1; row < m; row++) {
            for (int col = 1; col < n; col++) {
                if (matrix[row][col] == 0) {
                    matrix[row][0] = matrix[0][col] = 0; // Make that row and col to 0
                }
            }
        }

        for (int row = 1; row < m; row++) {
            for (int col = 1; col < n; col++) {
                // If there is any zero in that row or colum then make it to zero
                if (matrix[row][0] == 0 || matrix[0][col] == 0) {
                    matrix[row][col] = 0;
                }
            }
        }

        // Make the first row to 0
        if (rowZero == 0) {
            for (int col = 0; col < n; col++) {
                matrix[0][col] = 0;
            }
        }

        // Make the first colum to zero
        if (colZero == 0) {
            for (int row = 0; row < m; row++) {
                matrix[row][0] = 0;
            }
        }
    }


    // Leetcode problem: 74
    /*
     * Search in 2D Matrix
     * First Binary search row wise to find the row in which target can be found
     * Then Binary search in that row
     * */
    public boolean searchMatrix(int[][] matrix, int target) {

        int m = matrix.length, n = matrix[0].length;
        if (target < matrix[0][0] || target > matrix[m - 1][n - 1]) {
            return false;
        }

        int fistRow = 0, lastRow = m - 1, midRow = 0;
        while (fistRow <= lastRow) {
            midRow = fistRow + (lastRow - fistRow) / 2;

            if (matrix[midRow][0] <= target && matrix[midRow][n - 1] >= target) {
                break;
            } else if (target < matrix[midRow][0]) {
                lastRow = midRow - 1;
            } else {
                fistRow = midRow + 1;
            }
        }

        int left = 0, right = n - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (matrix[midRow][mid] == target) {
                return true;
            } else if (target < matrix[midRow][mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return false;
    }
}
