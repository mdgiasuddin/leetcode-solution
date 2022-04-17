package stack;

import pair.Pair;

import java.util.Stack;

public class StackSolution {
    public static void main(String[] args) {
        StackSolution stackSolution = new StackSolution();
        String[] strings = {"5", "2", "C", "D", "+"};

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
        for (int i = 0; i < histograms.length; i++) {
            maxArea = Math.max(maxArea, largestRectangleArea(histograms[i]));
        }

        return maxArea;
    }

    // Leetcode problem: 402
    /*
     * Maintain a stack
     * If digits comes in increasing order just push it
     * Whenever digit comes in decreasing order then remove all the bigger digits upto k
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

}
