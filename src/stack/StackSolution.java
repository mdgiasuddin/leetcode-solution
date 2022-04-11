package stack;

import java.util.Stack;

public class StackSolution {
    public static void main(String[] args) {
        StackSolution stackSolution = new StackSolution();
        String[] strings = {"5", "2", "C", "D", "+"};
        System.out.println(stackSolution.calPoints(strings));
    }

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

            System.out.println("Stack: " + stack);
        }

        int result = 0;
        while (!stack.isEmpty()) {
            result += stack.pop();
        }

        return result;
    }
}
