package string;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class StringSolution {

    public static void main(String[] args) {
        StringSolution stringSolution = new StringSolution();
//        System.out.println(stringSolution.lengthOfLongestSubstring("abcdab"));
        System.out.println(stringSolution.convert("PAYPALISHIRING", 3));
    }

    // Leetcode problem: 3
    /*
     * Build a map to store index of every character
     * If a character repeats then update the index and start index of substring
     * Else update the maximum length so far
     */
    public int lengthOfLongestSubstring(String s) {
        int maxLength = 0, start = 0;
        Map<Character, Integer> indexMap = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (indexMap.containsKey(ch)) {
                indexMap.replace(ch, i);
                start = indexMap.get(ch) + 1;
            } else {
                indexMap.put(ch, i);
                maxLength = Math.max(maxLength, i - start + 1);
            }
        }

        return maxLength;
    }

    // Leetcode problem: 5
    /*
     * for every index traverse left and right simultaneously if palindrome found
     * palindrome can be odd length or even length
     * check both even and odd length
     */
    public String longestPalindrome(String s) {
        if (s.length() == 0)
            return s;

        int maxLength = 1;
        int start = 0, len = s.length(), high, low;
        for (int i = 0; i < s.length(); i++) {
            // even length substring
            high = i;
            low = i - 1;
            while (low >= 0 && high < len && s.charAt(low) == s.charAt(high)) {
                if (high - low + 1 > maxLength) {
                    maxLength = high - low + 1;
                    start = low;
                }
                high++;
                low--;
            }

            // odd length substring
            high = i + 1;
            low = i - 1;
            while (low >= 0 && high < len && s.charAt(low) == s.charAt(high)) {
                if (high - low + 1 > maxLength) {
                    maxLength = high - low + 1;
                    start = low;
                }
                high++;
                low--;
            }
        }

        return s.substring(start, start + maxLength);
    }

    // Leetcode problem: 6
    /*
     * Zigzag conversion to number of rows
     * Efficient solution without space
     * Find the next character every time to insert
     * Traverse the number of row incremental stage
     */
    public String convert(String s, int numRows) {
        if (numRows == 1)
            return s;

        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < numRows; i++) {
            int inc = 2 * (numRows - 1);
            for (int j = i; j < s.length(); j += inc) {

                // Character for incremental stage
                ans.append(s.charAt(j));

                // Character for decremental stage
                if (i > 0 && i < numRows - 1 && j + inc - 2 * i < s.length())
                    ans.append(s.charAt(j + inc - 2 * i));
            }
        }
        return ans.toString();
    }

    // Leetcode problem: 20
    /*
     * Valid Parenthesis
     * Use stack to solve the problem
     */
    public boolean isValid(String s) {

        Stack<Character> stack = new Stack<>();
        for (char ch : s.toCharArray()) {
            if (ch == '(' || ch == '{' || ch == '[') {
                stack.push(ch);
            } else {
                if (stack.isEmpty())
                    return false;
                char top = stack.pop();
                if ((top == '(' && ch != ')') || (top == '{' && ch != '}') || (top == '[' && ch != ']'))
                    return false;
            }
        }

        return stack.isEmpty();
    }

    // Leetcode problem: 43
    /*
     * Multiply two number by string
     */
    public String multiply(String num1, String num2) {
        int m = num1.length();
        int n = num2.length();
        char[] sum = new char[m + n];
        int i, j;
        for (i = 0; i < m + n; i++)
            sum[i] = '0';

        for (i = m - 1; i >= 0; i--) {
            int carry = 0;
            for (j = n - 1; j >= 0; j--) {
                int temp = (num1.charAt(i) - '0') * (num2.charAt(j) - '0') + sum[i + j + 1] - '0' + carry;
                sum[i + j + 1] = (char) (temp % 10 + '0');
                carry = temp / 10;
            }
            sum[i] = (char) (sum[i] + carry);
        }

        i = 0;
        while (sum[i] == '0')
            i++;

        return String.copyValueOf(sum, i, m + n - i);
    }

    // Leetcode problem: 44
    /*
     * Wildcard matching
     * Dynamic programming
     * M[i][j] = M[i-1][j-1] if s[i] = p[j] || p[j] = ?
     * M[i][j] = M[i-1][j] || M[i][j-1] if p[j] = * , else false
     */
    public boolean isMatch(String s, String p) {
        int m = s.length(), n = p.length();
        boolean[][] matrix = new boolean[m + 1][n + 1];
        matrix[0][0] = true;
        for (int i = 1; i <= m; i++) {
            matrix[i][0] = false;
        }
        for (int j = 1; j <= n; j++) {
            if (p.charAt(j - 1) == '*')
                matrix[0][j] = matrix[0][j - 1];
            else
                matrix[0][j] = false;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '?')
                    matrix[i][j] = matrix[i - 1][j - 1];
                else if (p.charAt(j - 1) == '*')
                    matrix[i][j] = matrix[i][j - 1] || matrix[i - 1][j];
                else
                    matrix[i][j] = false;
            }
        }

        return matrix[m][n];
    }


}
