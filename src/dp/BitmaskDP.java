package dp;

import java.util.HashMap;
import java.util.Map;

public class BitmaskDP {

    // Leetcode problem: 2002
    /*
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
}
