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

    // Leetcode problem: 2063
    /*
     * Count vowels of all substrings
     * */
    public long countVowels(String word) {
        /*
                        bcdefgh
                          ''
                          d
                         cd
                        bcd
                           e
                           ef
                           efg
                           efgh
                       (i+1)*(len-i)
        * */

        long count = 0;
        long len = word.length();

        for (int i = 0; i < len; i++) {
            char ch = word.charAt(i);
            if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                count += (i + 1) * (len - i);
            }
        }

        return count;
    }

    // Leetcode problem: 193


    // Leetcode problem: 2116
    /*
     * Check if the parenthesis can be balanced after flipping certain position
     * We have to traverse from both left to right and right to left
     * While traversing left to right, check whether there is enough opening bracket to balance closing bracket. Flip + Open >= Close must be satisfied.
     * While traversing right to left, check whether there is enough closing bracket to balance opening bracket. Flip + Close >= Open must be satisfied.
     * If the condition is always satisfied then parenthesis can be balanced
     * Otherwise, return false
     * */
    public boolean canBeValid(String s, String locked) {
        int len = s.length();

        // If length is odd, then it never can be balanced
        if (len % 2 == 1) {
            return false;
        }

        int opening = 0, closing = 0, flipPossible = 0;

        // Traverse from left to right
        for (int i = 0; i < len; i++) {
            if (locked.charAt(i) == '0') {
                flipPossible++;
            } else if (s.charAt(i) == '(') {
                opening++;
            } else {
                closing++;
            }

            if (flipPossible + opening < closing) {
                return false;
            }
        }

        // Reset opening, closing and flip possible
        opening = closing = flipPossible = 0;

        // Traverse from right to left
        for (int i = len - 1; i >= 0; i--) {
            if (locked.charAt(i) == '0') {
                flipPossible++;
            } else if (s.charAt(i) == '(') {
                opening++;
            } else {
                closing++;
            }

            if (flipPossible + closing < opening) {
                return false;
            }
        }

        return true;
    }

    // Leetcode problem: 1044
    // Leetcode problem: 1048
    // Leetcode problem: 1063
}
