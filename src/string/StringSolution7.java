package string;

import java.util.Stack;

public class StringSolution7 {
    public static void main(String[] args) {
        StringSolution7 stringSolution7 = new StringSolution7();

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

    // Leetcode problem: 1044
    // Leetcode problem: 1048
    // Leetcode problem: 1063
}
