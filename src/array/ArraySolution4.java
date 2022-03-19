package array;

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
}
