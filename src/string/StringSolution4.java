package string;

import java.util.*;

public class StringSolution4 {

    public static void main(String[] args) {
        StringSolution4 stringSolution4 = new StringSolution4();

//        System.out.println(stringSolution4.isNumber("3."));
        System.out.println(stringSolution4.numDistinct("rabbbit", "rabbit"));
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

    // Leetcode problem: 65
    /*
     * Is valid number
     * Check condition for each possible case exponential, decimal & integer
     * */
    public boolean isNumber(String s) {
        int dotCount = 0, expCount = 0, dotIndex = -1, expIndex = -1;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '.') {
                dotIndex = i;
                dotCount++;
            } else if (s.charAt(i) == 'e' || s.charAt(i) == 'E') {
                expIndex = i;
                expCount++;
            }
        }

        if (dotCount > 1 || expCount > 1) {
            return false;
        }

        if (dotCount == 0 && expCount == 0) {
            return isInteger(s);
        }

        if (expCount == 1) {
            return isExponential(s, expIndex, dotIndex);
        }
        return isDecimal(s, dotIndex);
    }

    public boolean isExponential(String s, int expIndex, int dotIndex) {
        if (dotIndex > expIndex) {
            return false;
        }

        if (dotIndex != -1) {
            return isDecimal(s.substring(0, expIndex), dotIndex) && isInteger(s.substring(expIndex + 1));
        }

        return isInteger(s.substring(0, expIndex)) && isInteger(s.substring(expIndex + 1));
    }

    public boolean isDecimal(String s, int dotIndex) {
        if (s.length() < 2)
            return false;

        int i = 0;
        boolean signExists = false;
        if (s.charAt(0) == '+' || s.charAt(0) == '-') {
            i++;
            signExists = true;
        }

        while (i < dotIndex) {
            if (s.charAt(i) < '0' || s.charAt(i) > '9')
                return false;
            i++;
        }

        i++; // i = dot index now. Move forward after dot index

        while (i < s.length()) {
            if (s.charAt(i) < '0' || s.charAt(i) > '9')
                return false;
            i++;
        }

        return !signExists || s.length() > 2;
    }

    public boolean isInteger(String s) {
        if (s.isEmpty())
            return false;

        int i = 0;
        boolean signExists = false;
        if (s.charAt(0) == '+' || s.charAt(0) == '-') {
            i++;
            signExists = true;
        }

        while (i < s.length()) {
            if (s.charAt(i) < '0' || s.charAt(i) > '9')
                return false;
            i++;
        }

        return !signExists || s.length() > 1;
    }

    // Leetcode problem: 115
    /*
     * If character match either take the character or not take the character
     * dp(i, j) = dp(i-1, j-1) + dp(i, j-1) if match
     * dp(i, j) = dp(i, j-1) if not match
     * */
    public int numDistinct(String s, String t) {
        int[][] dp = new int[t.length() + 1][s.length() + 1];
        Arrays.fill(dp[0], 1);

        for (int i = 1; i <= t.length(); i++) {
            for (int j = 1; j <= s.length(); j++) {
                if (t.charAt(i - 1) == s.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + dp[i][j - 1];
                } else {
                    dp[i][j] = dp[i][j - 1];
                }
            }
            System.out.println(Arrays.toString(dp[i]));
        }

        return dp[t.length()][s.length()];
    }
}
