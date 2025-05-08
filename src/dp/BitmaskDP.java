package dp;

import java.util.HashMap;
import java.util.Map;

public class BitmaskDP {

    // Leetcode problem: 2002

    /**
     * Maximum Product of the Length of Two Palindromic Subsequences.
     * For all subsequences check whether they are palindrome or not.
     * Then calculate the maximum product of length.
     * */
    public int maxProduct(String s) {
        int n = s.length();
        Map<Integer, Integer> map = new HashMap<>();

        for (int mask = 1; mask < (1 << n); mask++) {
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < n; i++) {
                if ((mask & 1 << i) != 0) {
                    str.append(s.charAt(i));
                }
            }

            if (isPalindrome(str)) {
                map.put(mask, str.length());
            }
        }

        int res = 0;
        for (Map.Entry<Integer, Integer> entry1 : map.entrySet()) {
            for (Map.Entry<Integer, Integer> entry2 : map.entrySet()) {
                if ((entry1.getKey() & entry2.getKey()) == 0) {
                    res = Math.max(res, entry1.getValue() * entry2.getValue());
                }
            }
        }

        return res;
    }

    private boolean isPalindrome(StringBuilder str) {
        int l = 0;
        int r = str.length() - 1;

        while (l < r) {
            if (str.charAt(l) != str.charAt(r)) {
                return false;
            }
            l += 1;
            r -= 1;
        }

        return true;
    }

    // Leetcode problem: 2151

    /**
     * Maximum Good People Based on Statements.
     * Explanation: https://www.youtube.com/watch?v=v7sr-1Zbh6k&list=PLy38cn8b_xMcjNmLdBfY0D8mByFzbpX9Z
     * */
    public int maximumGood(int[][] statements) {
        int n = statements.length;
        int res = 0;

        for (int i = 1; i < (1 << n); i++) {
            if (isValid(i, statements)) {
                res = Math.max(res, countOneBits(i));
            }
        }

        return res;
    }

    private boolean isValid(int mask, int[][] statements) {
        int n = statements.length;
        for (int i = 0; i < n; i++) {
            if (isGoodPerson(mask, i)) {
                for (int j = 0; j < n; j++) {
                    if ((statements[i][j] == 0 && isGoodPerson(mask, j)) || (statements[i][j] == 1 && !isGoodPerson(mask, j))) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private boolean isGoodPerson(int mask, int person) {
        return (mask & (1 << person)) != 0;
    }

    private int countOneBits(int num) {
        int res = 0;
        while (num > 0) {
            res += num & 1;
            num >>= 1;
        }
        return res;
    }
}
