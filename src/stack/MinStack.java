package stack;

import java.util.Stack;

class StackPair {
    int val, minimum;

    public StackPair(int val, int minimum) {
        this.val = val;
        this.minimum = minimum;
    }
}

public class MinStack {

    Stack<StackPair> stack;

    public MinStack() {
        stack = new Stack<>();
    }

    public void push(int val) {
        int minimum;
        if (stack.isEmpty()) {
            minimum = val;
        } else {
            minimum = Math.min(val, stack.peek().minimum);
        }

        stack.push(new StackPair(val, minimum));
    }

    public void pop() {
        stack.pop();
    }

    public int top() {
        return stack.peek().val;
    }

    public int getMin() {
        return stack.peek().minimum;
    }
}
