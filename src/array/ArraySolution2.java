package array;

import java.util.Arrays;

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
}
