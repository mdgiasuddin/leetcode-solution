package string;

import java.util.*;

public class StringSolution8 {

    public static void main(String[] args) {
        StringSolution8 stringSolution8 = new StringSolution8();

        System.out.println(stringSolution8.findStrobogrammatic(3));
    }

    // Leetcode problem: 246
    // Lintcode problem: 644
    /*
     * Strobogrammatic Number.
     * */
    public boolean isStrobogrammatic(String num) {
        Map<Character, Character> mirrors = new HashMap<>();
        mirrors.put('0', '0');
        mirrors.put('1', '1');
        mirrors.put('6', '9');
        mirrors.put('8', '8');
        mirrors.put('9', '6');

        int len = num.length();
        for (int i = 0; i <= len / 2; i++) {
            if (!mirrors.containsKey(num.charAt(i)) || mirrors.get(num.charAt(i)) != num.charAt(len - 1 - i)) {
                return false;
            }
        }

        return true;
    }

    // Leetcode problem: 247
    // Lintcode problem: 776
    /*
     * Strobogrammatic Number II.
     * Backtracking solution.
     * */
    public List<String> findStrobogrammatic(int n) {
        Map<Character, Character> mirrors = new HashMap<>();
        mirrors.put('0', '0');
        mirrors.put('1', '1');
        mirrors.put('6', '9');
        mirrors.put('8', '8');
        mirrors.put('9', '6');

        List<String> result = new ArrayList<>();
        if (n % 2 == 1) {
            findStrobogrammatic(n - 1, "0", result, mirrors);
            findStrobogrammatic(n - 1, "1", result, mirrors);
            findStrobogrammatic(n - 1, "8", result, mirrors);
        } else {
            findStrobogrammatic(n, "", result, mirrors);
        }

        return result;
    }

    public void findStrobogrammatic(int n, String current, List<String> result, Map<Character, Character> mirrors) {
        if (n == 0) {
            result.add(current);
            return;
        }

        for (Map.Entry<Character, Character> entry : mirrors.entrySet()) {
            // Skip leading 0.
            if (n == 2 && entry.getKey() == '0') {
                continue;
            }
            findStrobogrammatic(n - 2, entry.getKey() + current + entry.getValue()
                    , result, mirrors);

        }
    }

    // Leetcode problem: 1930
    /*
     * Unique Length-3 Palindromic Subsequences.
     * */
    public int countPalindromicSubsequence(String s) {
        int n = s.length();
        int[] left = new int[26];
        int[] right = new int[26];
        Arrays.fill(left, -1);
        Arrays.fill(right, -1);

        for (int i = 0; i < n; i++) {
            if (left[s.charAt(i) - 'a'] == -1) {
                left[s.charAt(i) - 'a'] = i;
            }

            if (right[s.charAt(n - 1 - i) - 'a'] == -1) {
                right[s.charAt(n - 1 - i) - 'a'] = n - 1 - i;
            }
        }

        Set<String> result = new HashSet<>();
        for (int i = 1; i < n - 1; i++) {
            for (char ch = 'a'; ch <= 'z'; ch++) {
                if (left[ch - 'a'] != -1 && left[ch - 'a'] < i && right[ch - 'a'] > i) {
                    result.add(ch + "" + s.charAt(i) + "" + ch);
                }
            }
        }

        return result.size();
    }
}
