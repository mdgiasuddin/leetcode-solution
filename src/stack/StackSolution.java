package stack;

import pair.Pair;

import java.util.Stack;

public class StackSolution {
    public static void main(String[] args) {
        StackSolution stackSolution = new StackSolution();
        String[] strings = {"5", "2", "C", "D", "+"};
        System.out.println(stackSolution.calPoints(strings));
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
