package stack;

import string.CharCounter;

import java.util.Stack;

public class StackSolution2 {
    public static void main(String[] args) {
        StackSolution2 stackSolution2 = new StackSolution2();

        System.out.println(stackSolution2.removeDuplicates("deeedbbcccbdaa", 3));
    }

    public String removeDuplicates(String s, int k) {
        Stack<CharCounter> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (!stack.isEmpty() && stack.peek().ch == ch) {
                stack.peek().count++;
                if (stack.peek().count == k) {
                    stack.pop();
                }
            } else {
                stack.push(new CharCounter(ch, 1));
            }
        }

        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            CharCounter charCounter = stack.pop();
            StringBuilder str = new StringBuilder();
            str.append(String.valueOf(charCounter.ch).repeat(Math.max(0, charCounter.count)));

            result.insert(0, str);
        }

        return result.toString();
    }


}
