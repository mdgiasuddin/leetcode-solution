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

    // Leetcode problem: 1106
    public boolean parseBoolExpr(String expression) {
        Stack<Character> stack = new Stack<>();

        stack.push(expression.charAt(0));
        for (int i = 1; i < expression.length(); i++) {
            if (expression.charAt(i) == ')') {
                char and = 't', or = 'f', not = 'f';
                while (stack.peek() != '(') {
                    char ch = stack.pop();

                    /*
                     * 1 True value will return True for '|' operation
                     * 1 False value will return False for '&' operation]
                     * '!' will be opposite for True or False
                     * */
                    if (ch == 't') {
                        or = 't';
                        not = 'f';
                    } else {
                        and = 'f';
                        not = 't';
                    }
                }
                stack.pop(); // pop '('

                char op = stack.pop(); // After '(' there will be operator

                // Push the value according to operator
                if (op == '|')
                    stack.push(or);
                else if (op == '&')
                    stack.push(and);
                else if (op == '!')
                    stack.push(not);

            } else if (expression.charAt(i) != ',') {
                stack.push(expression.charAt(i));
            }
        }

        return stack.peek() == 't';
    }

    // Leetcode problem: 1047
    /*
     * Use stack.
     * */
    public String removeDuplicates(String s) {
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            // Remove the character if it is in the top of stack.
            if (!stack.isEmpty() && stack.peek() == ch) {
                stack.pop();
            } else {
                stack.push(ch);
            }
        }

        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            result.insert(0, stack.pop());
        }

        return result.toString();
    }

    // Leetcode problem: 394
    /*
     * Maintain a stack
     * Push character until ']' appears
     * When ']' appears then pop all the characters after '[' and push after processing
     * */
    public String decodeString(String s) {
        Stack<String> stack = new Stack<>();

        int i = 0;
        while (i < s.length()) {
            char ch = s.charAt(i);
            if (ch != ']') {
                stack.push(String.valueOf(ch));
            } else {
                StringBuilder substr = new StringBuilder();
                while (!stack.peek().equals("[")) {
                    substr.insert(0, stack.pop());
                }
                stack.pop(); // pop '['

                // Calculate the number before '['
                StringBuilder numString = new StringBuilder();
                while (!stack.isEmpty() && Character.isDigit(stack.peek().charAt(0))) {
                    numString.insert(0, stack.pop());
                }
                stack.push(substr.toString().repeat(Integer.parseInt(numString.toString())));

            }

            i++;
        }

        StringBuilder str = new StringBuilder();
        while (!stack.isEmpty()) {
            str.insert(0, stack.pop());
        }

        return str.toString();
    }

    // Leetcode problem: 331
    /*
     * Use stack
     * When '#' comes then pop last two and push #
     * */
    public boolean isValidSerialization(String preorder) {
        Stack<String> stack = new Stack<>();

        int i = 0;
        while (i < preorder.length()) {
            // If digit found traverse until digit
            char ch = preorder.charAt(i);
            if (ch >= '0' && ch <= '9') {
                int j = i;
                while (j < preorder.length() && preorder.charAt(j) >= '0' && preorder.charAt(j) <= '9') {
                    j++;
                }
                stack.push(preorder.substring(i, j));
                i = j - 1;
            } else if (ch == '#') {
                while (!stack.isEmpty() && stack.peek().equals("#")) {
                    stack.pop();
                    if (stack.isEmpty() || stack.peek().equals("#"))
                        return false;
                    stack.pop();
                }
                stack.push("#");
            }
            i++;
        }

        return stack.size() == 1 && stack.peek().equals("#");
    }

    // Leetcode problem: 856
    /*
     * Score the parenthesis
     * Use stack
     * Whenever '(' comes save the score calculated so far and reset the score
     * Whenever ')' comes pop last score saved and update the current score
     * */
    public int scoreOfParentheses(String s) {
        Stack<Integer> stack = new Stack<>();
        int currentScore = 0;

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (ch == '(') {
                stack.push(currentScore);
                currentScore = 0;
            } else {
                // stack.pop give the score stored before '(' to which the ')' is balancing
                // Current score (Before updating) = 0 means ')' comes exactly after '(', So Math.max(...) will give 1
                // Otherwise will be the double of the previous calculated score
                currentScore = stack.pop() + Math.max(currentScore * 2, 1);
            }
        }

        return currentScore;
    }

    // Leetcode problem: 844
    public boolean backspaceCompare(String s, String t) {
        Stack<Character> stack1 = new Stack<>();
        Stack<Character> stack2 = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '#') {
                if (!stack1.isEmpty()) {
                    stack1.pop();
                }
            } else {
                stack1.push(ch);
            }
        }

        for (int i = 0; i < t.length(); i++) {
            char ch = t.charAt(i);
            if (ch == '#') {
                if (!stack2.isEmpty()) {
                    stack2.pop();
                }
            } else {
                stack2.push(ch);
            }
        }

        return stack1.equals(stack2);
    }

    // Leetcode problem: 1249
    /*
     * Minimum Remove to Make Valid Parentheses.
     * */
    public String minRemoveToMakeValid(String s) {
        int n = s.length();
        boolean[] remove = new boolean[n];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            if (ch == '(') {
                stack.push(i);
            } else if (ch == ')') {
                if (stack.isEmpty()) {
                    remove[i] = true;
                } else {
                    stack.pop();
                }
            }
        }

        while (!stack.isEmpty()) {
            remove[stack.pop()] = true;
        }

        StringBuilder res = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (!remove[i]) {
                res.append(s.charAt(i));
            }
        }

        return res.toString();
    }

    // Leetcode problem: 907
    /*
     * Sum of Subarray Minimums.
     * Monotonically increasing stack.
     * Explanation: https://www.youtube.com/watch?v=BqrO3lMwfRM&list=PLy38cn8b_xMfO7CGsUDIsYGps37yKaQ9X&index=16
     * */
    public int sumSubarrayMins(int[] arr) {
        long[] newArray = new long[arr.length + 2];
        newArray[0] = newArray[newArray.length - 1] = 0;
        for (int i = 0; i < arr.length; i++) {
            newArray[i + 1] = arr[i];
        }
        Stack<Integer> stack = new Stack<>();
        long mod = 1000000007;
        long res = 0;
        for (int i = 0; i < newArray.length; i++) {
            while (!stack.isEmpty() && newArray[stack.peek()] > newArray[i]) {
                int idx = stack.pop();
                res = (res + newArray[idx] * (idx - stack.peek()) * (i - idx)) % mod;
            }

            stack.push(i);
        }

        return (int) res;
    }

}
