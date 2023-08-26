package string;

import java.util.*;

public class StringSolution4 {

    public static void main(String[] args) {
        StringSolution4 stringSolution4 = new StringSolution4();
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

    // Leetcode problem:
    // 424
    /*
     * Build up a counter of each character.
     * Update the maximum character count.
     * In every window check if the replacement required is less than or equal to k.
     * If condition fulfilled update result value.
     * Otherwise, move left pointer and decrement the counter of left character.
     * During decrement maximum character need not update because only when counter of any character crosses current maximum.
     * - the result will be updated, otherwise it has no effect.
     * */
    public int characterReplacement(String s, int k) {
        int[] charCount = new int[26];

        int left = 0, maxLength = 0, result = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            charCount[ch - 'A']++;
            maxLength = Math.max(maxLength, charCount[ch - 'A']);

            while ((i - left + 1) - maxLength > k) {
                charCount[s.charAt(left++) - 'A']--;
            }

            result = Math.max(result, i - left + 1);
        }

        return result;
    }

    // Leetcode problem: 395
    /*
     * This problem is tricky.
     * Count the occurrences of all characters.
     * If there exists no character whose count is < k then return entire string.
     * If any character exists recursively call left side and right of that character and take the max.
     * */
    public int longestSubstring(String s, int k) {
        return longestSubstring(s.toCharArray(), 0, s.length(), k);
    }

    public int longestSubstring(char[] charArray, int start, int end, int k) {
        if (end - start < k) {
            return 0;
        }

        int[] counter = new int[26];
        for (int i = start; i < end; i++) {
            counter[charArray[i] - 'a']++;
        }

        for (int i = start; i < end; i++) {
            if (counter[charArray[i] - 'a'] < k) {
                int j = i;

                // Skip all characters whose count < k.
                while (j < end && counter[charArray[j] - 'a'] < k) j++;

                return Math.max(longestSubstring(charArray, start, i, k), longestSubstring(charArray, j, end, k));
            }
        }

        // If no such character found whose counter < k, return entire string
        return end - start;
    }

    // Leetcode problem: 306
    /*
     * Build up first 2 numbers and check whether any third number exist so that fist + second = third
     * If exists then recursively call for next where second and third will be first and second respectively
     * */
    public boolean isAdditiveNumber(String num) {
        for (int i = 1; i < num.length() - 1; i++) {
            long first = Long.parseLong(num.substring(0, i));

            // Leading 0 is not allowed
            if (String.valueOf(first).length() < i)
                break;

            for (int j = i + 1; j < num.length(); j++) {
                long second = Long.parseLong(num.substring(i, j));

                // Leading 0 is not allowed
                if (String.valueOf(second).length() < j - i)
                    break;

                // Not first = false means third number has not been formatted yet
                if (isAdditiveNumber(num.substring(j), first, second, false))
                    return true;
            }
        }

        return false;
    }

    private boolean isAdditiveNumber(String num, long first, long second, boolean notFirst) {
        if (num.isEmpty() && notFirst)
            return true;

        long third = first + second;
        String thirdString = String.valueOf(third);
        if (thirdString.length() > num.length() || !thirdString.equals(num.substring(0, thirdString.length())))
            return false;

        // Third number has been formatted, so not first is true now
        return isAdditiveNumber(num.substring(thirdString.length()), second, third, true);
    }

    // Leetcode problem: 383
    public boolean canConstruct(String ransomNote, String magazine) {
        int[] count = new int[26];

        for (int i = 0; i < magazine.length(); i++) {
            count[magazine.charAt(i) - 'a']++;
        }

        for (int i = 0; i < ransomNote.length(); i++) {
            if (--count[ransomNote.charAt(i) - 'a'] < 0)
                return false;
        }

        return true;
    }

}
