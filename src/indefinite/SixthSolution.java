package indefinite;

import java.util.Stack;

public class SixthSolution {

    public boolean parseBoolExpr(String expression) {
        Stack<Character> stack = new Stack<>();

        stack.push(expression.charAt(0));
        for (int i = 1; i < expression.length(); i++) {
            if (expression.charAt(i) == ')') {
                char and = 't', or = 'f', not = 'f';
                while (stack.peek() != '(') {
                    char ch = stack.pop();
                    if (ch == 't') {
                        or = 't';
                        not = 'f';
                    } else {
                        and = 'f';
                        not = 't';
                    }
                }
                stack.pop();
                char op = stack.pop();
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

    public int maxProfit2(int[] prices) {
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                profit += prices[i] - prices[i - 1];
            }
        }
        return profit;
    }

    public int maxProfit(int[] prices) {
        int first = 0, second = 0;

        int buy = prices[0];
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > buy) {
                if (prices[i] - buy > first) {
                    second = first;
                    first = prices[i] - buy;
                } else if (prices[i] - buy > second) {
                    second = prices[i] - buy;
                }
            } else {
                buy = prices[i];
            }
        }

        return 0;
    }

    public int numOfStrings(String[] patterns, String word) {
        int count = 0;
        for (String pattern : patterns) {
            if (word.indexOf(pattern) != -1) count++;
        }
        return count;
    }


}
