package string;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class StringSolution4 {

    public static void main(String[] args) {
        StringSolution4 stringSolution4 = new StringSolution4();

        System.out.println(stringSolution4.removeInvalidParentheses("()())()"));
    }

    // Leetcode problem: 301
    /*
     * Remove invalid parenthesis
     * First calculate the number of opening and closing bracket must need to be removed
     * Traverse every character, remove it and check if it is valid recursively
     * */
    public List<String> removeInvalidParentheses(String s) {
        Set<String> resultSet = new HashSet<>();

        Stack<Character> invalidStack = checkParenthesisValidity(s);
        if (invalidStack.isEmpty()) {
            resultSet.add(s);
            return resultSet.stream().toList();
        }

        int opening = 0, closing = 0;
        while (!invalidStack.isEmpty()) {
            char ch = invalidStack.pop();
            if (ch == '(')
                opening++;
            else
                closing++;
        }

        Set<String> visited = new HashSet<>();
        removeInvalidParentheses(s, resultSet, visited, opening, closing);

        return resultSet.stream().toList();
    }

    public Stack<Character> checkParenthesisValidity(String s) {
        Stack<Character> stack = new Stack<>();

        for (char ch : s.toCharArray()) {
            if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                if (!stack.isEmpty() && stack.peek() == '(') {
                    stack.pop();
                } else {
                    stack.push(ch);
                }
            }
        }
        return stack;
    }

    public boolean isValidParenthesis(String s) {
        int parenthesis = 0;

        for (char ch : s.toCharArray()) {
            if (ch == '(') {
                parenthesis++;
            } else if (ch == ')') {
                if (parenthesis-- == 0)
                    return false;
            }
        }

        return parenthesis == 0;
    }

    public void removeInvalidParentheses(String s, Set<String> resultSet, Set<String> visited, int openingInvalid, int closingInvalid) {

        if (visited.contains(s))
            return;

        visited.add(s);
        if (openingInvalid < 0 || closingInvalid < 0)
            return;

        if (openingInvalid == 0 && closingInvalid == 0) {
            if (isValidParenthesis(s)) {
                resultSet.add(s);
                return;
            }
        }

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            String left = s.substring(0, i);
            String right = s.substring(i + 1);

            if (ch == '(') {
                removeInvalidParentheses(left + right, resultSet, visited, openingInvalid - 1, closingInvalid);
            } else if (ch == ')') {
                removeInvalidParentheses(left + right, resultSet, visited, openingInvalid, closingInvalid - 1);
            }
        }
    }
}
