package stack;

import pair.Pair;

import java.util.Arrays;
import java.util.Stack;

public class StackSolution {
    public static void main(String[] args) {
        StackSolution stackSolution = new StackSolution();
        String[] strings = {"5", "2", "C", "D", "+"};

        int[] nums = {1, 2, 3, 2};

        int[] position = {10, 8, 0, 5, 3};
        int[] speed = {2, 4, 1, 1, 3};
        System.out.println(stackSolution.carFleet(12, position, speed));

    }

    // Leetcode problem: 20
    /*
     * Valid Parenthesis
     * */
    public boolean isValid(String s) {

        Stack<Character> stack = new Stack<>();
        for (char ch : s.toCharArray()) {
            if (ch == '(' || ch == '{' || ch == '[') {
                stack.push(ch);
            } else {
                if (stack.isEmpty())
                    return false;
                char top = stack.pop();
                if ((top == '(' && ch != ')') || (top == '{' && ch != '}') || (top == '[' && ch != ']'))
                    return false;
            }
        }

        return stack.isEmpty();
    }

    // Leetcode problem: 71
    public String simplifyPath(String path) {

        Stack<String> stack = new Stack<>();
        path = path + "/";
        StringBuilder current = new StringBuilder();

        for (int i = 0; i < path.length(); i++) {

            if (path.charAt(i) == '/') {
                if (current.toString().equals("..")) {
                    if (!stack.isEmpty())
                        stack.pop();
                } else if (current.length() > 0 && !current.toString().equals(".")) {
                    // For consecutive '/' or '.' just ignore it
                    stack.push(current.toString());
                }
                // Reset current string after pushing
                current = new StringBuilder();
            } else {
                // Build up the current directory
                current.append(path.charAt(i));
            }
        }

        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            result.insert(0, "/" + stack.pop());
        }

        return (result.length() == 0) ? "/" : result.toString();
    }

    // Leetcode problem: 84
    /*
     * Use stack to maintain last height
     * If new height is in increasing order then simply push it to the stack
     * Else pop all the height greater than to new height & update the maximum area
     * Then push new height
     * Finally pop all the height and check it forms maximum area or not
     * */
    public int largestRectangleArea(int[] heights) {
        int maxArea = 0;
        Stack<Pair> stack = new Stack<>();

        for (int i = 0; i < heights.length; i++) {
            int start = i;

            while (!stack.isEmpty() && stack.peek().second > heights[i]) {
                Pair indexHeight = stack.pop();
                maxArea = Math.max(maxArea, indexHeight.second * (i - indexHeight.first));
                start = indexHeight.first;
            }

            stack.push(new Pair(start, heights[i]));
        }

        while (!stack.isEmpty()) {
            Pair indexHeight = stack.pop();
            maxArea = Math.max(maxArea, indexHeight.second * (heights.length - indexHeight.first));
        }

        return maxArea;
    }

    // Leetcode problem: 85
    /*
     * This problem is similar to the largest rectangle in histogram (Leetcode problem: 85)
     * Build up histogram for every row
     * Then use the rectangle in histogram
     * */
    public int maximalRectangle(char[][] matrix) {
        int[][] histograms = new int[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix[0].length; i++) {
            histograms[0][i] = matrix[0][i] - '0';
        }

        for (int i = 1; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == '1') {
                    histograms[i][j] = 1 + histograms[i - 1][j];
                } else {
                    histograms[i][j] = 0;
                }
            }
        }

        int maxArea = 0;
        for (int[] histogram : histograms) {
            maxArea = Math.max(maxArea, largestRectangleArea(histogram));
        }

        return maxArea;
    }

    // Leetcode problem: 150
    /*
     * This problem is similar to post operation equation in data structures course
     * */
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();

        for (String token : tokens) {
            if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")) {
                int second = stack.pop();
                int first = stack.pop();

                if (token.equals("+")) {
                    stack.push(first + second);
                } else if (token.equals("-")) {
                    stack.push(first - second);
                } else if (token.equals("*")) {
                    stack.push(first * second);
                } else {
                    stack.push(first / second);
                }
            } else {
                stack.push(Integer.parseInt(token));
            }
        }

        return stack.pop();
    }

    // Leetcode problem: 402
    /*
     * Maintain a stack
     * If digits comes in increasing order just push it
     * Whenever digit comes in decreasing order then remove all the bigger digits up to k
     * */
    public String removeKdigits(String num, int k) {
        if (num.length() <= k)
            return "0";
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < num.length(); i++) {
            char ch = num.charAt(i);

            while (!stack.isEmpty() && stack.peek() > ch && k > 0) {
                stack.pop();
                k--;
            }
            stack.push(ch);
        }

        // If more digits need to remove the remove from the last
        while (k > 0) {
            stack.pop();
            k--;
        }

        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            result.insert(0, stack.pop());
        }

        // Remove leading zeros
        String resultString = result.toString();
        int i = 0;
        while (i < resultString.length() && resultString.charAt(i) == '0') i++;

        return i == resultString.length() ? "0" : resultString.substring(i);
    }

    // Leetcode problem: 682
    public int calPoints(String[] ops) {
        Stack<Integer> stack = new Stack<>();

        for (String op : ops) {
            if (op.equals("C")) {
                stack.pop();
            } else if (op.equals("D")) {
                stack.add(stack.peek() * 2);
            } else if (op.equals("+")) {
                int top = stack.pop();
                int doubled = top + stack.peek();
                stack.add(top);
                stack.add(doubled);
            } else {
                stack.add(Integer.parseInt(op));
            }
        }

        int result = 0;
        while (!stack.isEmpty()) {
            result += stack.pop();
        }

        return result;
    }

    // Leetcode problem: 739
    public int[] dailyTemperatures(int[] temperatures) {
        Stack<Pair> stack = new Stack<>();
        int[] result = new int[temperatures.length];

        for (int i = 0; i < temperatures.length; i++) {

            // Update the result for all previous day's of which temperature is lower
            while (!stack.isEmpty() && stack.peek().second < temperatures[i]) {
                Pair top = stack.pop();
                result[top.first] = i - top.first;
            }

            // Push new temperature with index
            stack.push(new Pair(i, temperatures[i]));
        }

        return result;
    }

    // Leetcode problem: 735
    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> stack = new Stack<>();

        for (int asteroid : asteroids) {
            while (!stack.isEmpty() && asteroid < 0 && stack.peek() > 0) {
                int diff = stack.peek() + asteroid;

                if (diff > 0) {
                    // New asteroid is smaller. It will explode
                    asteroid = 0;
                } else if (diff < 0) {
                    // Previous is smaller. It will explode
                    stack.pop();
                } else {
                    // Two are equal. Both will explode
                    asteroid = 0;
                    stack.pop();
                }
            }

            if (asteroid != 0) {
                stack.push(asteroid);
            }
        }

        int[] result = new int[stack.size()];
        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = stack.pop();
        }

        return result;
    }

    // Leetcode problem: 1856
    /*
     * The problem is tricky
     * Build up a monotonic stack (Non-decreasing stack)
     * The process of this problem is similar to the largest rectangle in histogram (Leetcode problem: 85)
     * */
    public int maxSumMinProduct(int[] nums) {
        long[] prefix = new long[nums.length + 1];
        long result = 0;

        // Build up prefix sum for all the indices
        for (int i = 1; i <= nums.length; i++) {
            prefix[i] = nums[i - 1] + prefix[i - 1];
        }

        Stack<Pair> stack = new Stack<>();

        for (int i = 0; i < nums.length; i++) {
            int start = i;

            while (!stack.isEmpty() && stack.peek().second > nums[i]) {
                Pair top = stack.pop();
                start = top.first;
                long total = prefix[i] - prefix[start];
                result = Math.max(result, total * top.second);
            }
            stack.add(new Pair(start, nums[i]));
        }

        while (!stack.isEmpty()) {
            Pair top = stack.pop();
            int start = top.first;
            long total = prefix[nums.length] - prefix[start];

            result = Math.max(result, total * top.second);
        }

        return (int) (result % 1000000007);
    }

    // Leetcode problem: 853
    public int carFleet(int target, int[] position, int[] speed) {

        Pair[] pairs = new Pair[position.length];

        for (int i = 0; i < position.length; i++) {
            pairs[i] = new Pair(position[i], speed[i]);
        }

        /*
         * Sort the car revered way based on the position
         * Any car in behind cannot cross the front car
         * */

        Arrays.sort(pairs, (a, b) -> b.first - a.first);

        Stack<Double> timeStack = new Stack<>();
        for (Pair pair : pairs) {
            double time = (target - pair.first) * 1.0 / pair.second;
            /*
             * If any behind car need less time, then it will fleet with front car and reach the destination with the front car.
             * So don't add this in stack
             * */
            if (!timeStack.isEmpty() && time <= timeStack.peek()) {
                continue;
            }
            timeStack.push(time);
        }

        return timeStack.size();
    }

}
