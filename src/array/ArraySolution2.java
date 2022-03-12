package array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArraySolution2 {

    public static void main(String[] args) {

    }

    // Leetcode problem: 45
    /*
     * Jump Game
     * BFS
     * */
    public int jump(int[] nums) {
        if (nums.length < 2) {
            return 0;
        }
        int[] array = new int[nums.length];
        Arrays.fill(array, Integer.MAX_VALUE);

        array[0] = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j <= i + nums[i] && j < array.length; j++) {
                array[j] = Math.min(array[j], array[i] + 1);
                if (j == array.length - 1)
                    return array[j];
            }
        }

        return array[array.length - 1];
    }

    // Leetcode problem: 55
    /*
     * This problem can be solved by similar way to Jump game II (Leetcode problem: 45)
     * Since the Jump game II use BFS, it is inefficient
     * This problem doesn't need BFS, because shortest path is not required
     * */
    public boolean canJump(int[] nums) {

        if (nums.length == 1)
            return true;
        if (nums[0] == 0)
            return false;
        int last = nums.length - 1;
        for (int i = nums.length - 2; i >= 0; i--) {

            // Last position is reachable from ith position
            if (nums[i] >= last - i) {
                last = i;
            }
        }
        return last == 0;
    }

    // Leetcode problem: 48
    /*
     * Rotate matrix 90 degree clockwise
     * */
    public void rotate(int[][] matrix) {
        int n = matrix.length;

        for (int i = 0; i < n / 2; i++) {
            for (int j = i; j < n - 1 - i; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n - 1 - j][i];
                matrix[n - 1 - j][i] = matrix[n - 1 - i][n - 1 - j];
                matrix[n - 1 - i][n - 1 - j] = matrix[j][n - 1 - i];
                matrix[j][n - 1 - i] = temp;
            }
        }
    }

    // Leetcode problem: 41
    /*
     * First missing positive number
     * First reset the negative numbers to 0
     * For every number set that index to negative
     * If 0 is at an index then set such a negative number so that it cannot affect further calculations.
     * It may be any number less than -(len of the array)
     * If there is already a negative number do nothing
     * For negative number index take absolute value
     * */
    public int firstMissingPositive(int[] nums) {
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            if (nums[i] < 0) {
                nums[i] = 0;
            }
        }

        for (int num : nums) {
            int absNum = Math.abs(num);

            if (absNum - 1 >= 0 && absNum - 1 < len) {
                nums[absNum - 1] = nums[absNum - 1] > 0 ? -nums[absNum - 1] :
                        (nums[absNum - 1] == 0 ? -(len + 1) : nums[absNum - 1]);
            }
        }

        for (int i = 1; i <= len; i++) {
            if (nums[i - 1] >= 0) {
                return i;
            }
        }

        return len + 1;
    }

    // Leetcode problem: 54
    /*
     * Traverse left to right direction first and change direction and limit of traversed line
     * */
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> list = new ArrayList<>();
        int left = 0, right = matrix[0].length - 1, up = 0, down = matrix.length - 1;

        while (left <= right && up <= down) {
            for (int i = left; i <= right; i++) {
                list.add(matrix[up][i]);
            }
            up++;

            for (int i = up; i <= down; i++) {
                list.add(matrix[i][right]);
            }
            right--;

            for (int i = right; i >= left; i--) {
                list.add(matrix[down][i]);
            }
            down--;

            for (int i = down; i >= up; i--) {
                list.add(matrix[left][i]);
            }
            left++;

        }

        return list;
    }
}
