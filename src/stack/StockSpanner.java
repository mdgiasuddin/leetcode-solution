package stack;

import java.util.Stack;

public class StockSpanner {

    // Leetcode problem: 901
    Stack<int[]> stack;

    public StockSpanner() {
        stack = new Stack<>();
    }

    public int next(int price) {
        int count = 1;
        while (!stack.isEmpty() && price >= stack.peek()[0]) {
            count += stack.pop()[1];
        }
        int[] arr = {price, count};
        stack.push(arr);

        return count;
    }
}
