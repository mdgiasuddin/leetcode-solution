package stack;

import pair.Pair;
import string.CharCounter;

import java.util.Stack;

public class StackSolution2 {
    public static void main(String[] args) {
        StackSolution2 stackSolution2 = new StackSolution2();

        System.out.println(stackSolution2.removeDuplicates("deeedbbcccbdaa", 3));
    }

    // Leetcode problem: 1209
    public String removeDuplicates(String s, int k) {
        Stack<CharCounter> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (!stack.isEmpty() && stack.peek().ch == ch) {
                // Check the current character builds up consecutive k character.
                stack.peek().count++;
                if (stack.peek().count == k) {
                    stack.pop();
                }
            } else {
                // Else save as new character with count 1.
                stack.push(new CharCounter(ch, 1));
            }
        }

        // Build up result with remaining characters.
        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            CharCounter charCounter = stack.pop();
            StringBuilder str = new StringBuilder();
            str.append(String.valueOf(charCounter.ch).repeat(Math.max(0, charCounter.count)));

            result.insert(0, str);
        }

        return result.toString();
    }

    // Leetcode problem: 456
    public boolean find132pattern(int[] nums) {
        Stack<Pair> stack = new Stack<>();

        // Current min stores the minimum element before ith element.
        int currentMin = nums[0];

        for (int i = 1; i < nums.length; i++) {
            while (!stack.isEmpty() && stack.peek().first <= nums[i]) {
                stack.pop();
            }

            if (!stack.isEmpty() && stack.peek().second < nums[i]) {
                return true;
            }

            stack.push(new Pair(nums[i], currentMin));
            currentMin = Math.min(currentMin, nums[i]);
        }

        return false;
    }

}
