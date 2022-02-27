package string;

import java.util.Arrays;
import java.util.Stack;

public class StringSolution3 {
    public static void main(String[] args) {
        StringSolution3 stringSolution3 = new StringSolution3();
        System.out.println(stringSolution3.nextGreaterElement(12443322));
    }

    // Leetcode problem: 556
    /*
     * Traverse from right side and find first digit which is greater than left digit
     * Sort the right side
     * Find the appropriate place for the digit which is smaller
     * */
    public int nextGreaterElement(int n) {
        String numString = String.valueOf(n);

        int i;
        for (i = numString.length() - 1; i > 0; i--) {
            if (numString.charAt(i) > numString.charAt(i - 1))
                break;
        }
        if (i == 0) return -1;

        char[] rightPart = numString.substring(i).toCharArray();
        Arrays.sort(rightPart);
        StringBuilder stringBuilder = new StringBuilder();
        int j = 0;
        char ch = numString.charAt(i - 1);
        while (j < rightPart.length) {
            if (rightPart[j] <= ch) {
                j++;
                continue;
            }
            ch = rightPart[j];
            rightPart[j] = numString.charAt(i - 1);
            break;
        }
        stringBuilder.append(ch);
        stringBuilder.append(String.copyValueOf(rightPart));

        long numLong = Long.parseLong(numString.substring(0, i - 1) + stringBuilder);

        return numLong > Integer.MAX_VALUE ? -1 : (int) numLong;
    }

    // Leetcode problem: 678
    /*
     * Valid parenthesis
     * Maintain 2 stacks (1 for '(' and 1 for '*')
     * Balance ')' using '(' if failed then '*'
     * Balance remaining '(' with '*'
     * */
    public boolean checkValidString(String s) {
        Stack<Integer> parentheses = new Stack<>();
        Stack<Integer> stars = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(') {
                parentheses.push(i);
            } else if (ch == '*') {
                stars.push(i);
            } else {
                if (!parentheses.isEmpty()) {
                    parentheses.pop();
                } else if (!stars.isEmpty()) {
                    stars.pop();
                } else {
                    return false;
                }
            }
        }

        while (!parentheses.isEmpty()) {
            if (stars.isEmpty() || parentheses.pop() > stars.pop()) {
                return false;
            }
        }
        return true;
    }

}
