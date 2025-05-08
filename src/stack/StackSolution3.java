package stack;

import java.util.Arrays;
import java.util.Stack;

public class StackSolution3 {

    // Leetcode problem: 503

    /**
     * Next Greater Element II.
     * Traverse the array 2 times to get the next greater element.
     * */
    public int[] nextGreaterElements(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        int n = nums.length;
        int[] result = new int[n];
        Arrays.fill(result, -1);

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
                result[stack.pop()] = nums[i];
            }

            stack.push(i);
        }

        // Do not insert any element in the stack during second travers.
        for (int num : nums) {
            while (!stack.isEmpty() && nums[stack.peek()] < num) {
                result[stack.pop()] = num;
            }
        }

        return result;
    }
}
