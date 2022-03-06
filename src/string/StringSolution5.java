package string;

import java.util.*;

public class StringSolution5 {
    public static void main(String[] args) {

    }

    // Leetcode problem: 433
    /*
     * BFS Search
     * See Leetcode problem: 127
     * */
    public int minMutation(String start, String end, String[] bank) {
        Set<String> bankSet = new HashSet<>(List.of(bank));

        if (!bankSet.contains(end)) {
            return -1;
        }

        Queue<String> queue = new LinkedList<>();
        int maxLength = 0;
        queue.add(start);
        char[] dnaChars = {'A', 'C', 'G', 'T'};

        while (!queue.isEmpty()) {
            int currentSize = queue.size();
            maxLength++;
            while (currentSize-- > 0) {
                String top = queue.poll();
                for (int i = 0; i < top.length(); i++) {
                    for (char dnaChar : dnaChars) {
                        String temp = top.substring(0, i) + dnaChar + top.substring(i + 1);
                        if (temp.equals(top))
                            continue;

                        if (temp.equals(end)) {
                            return maxLength;
                        }
                        if (bankSet.contains(temp)) {
                            queue.add(temp);
                            bankSet.remove(temp);
                        }
                    }
                }
            }
        }

        return -1;
    }

    // Leetcode problem: 438
    /*
     * Solve the problem by sliding window
     * See Leetcode problem: 567
     * */
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();

        if (s.length() < p.length())
            return result;

        int[] counter = new int[26];
        for (int i = 0; i < p.length(); i++) {
            counter[p.charAt(i) - 'a']++;
        }

        int remaining = p.length(), start = 0;

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (counter[ch - 'a'] > 0) {
                remaining--;
                if (remaining == 0) {
                    result.add(start);
                }
            }
            counter[ch - 'a']--;

            if (i >= p.length() - 1) {
                if (++counter[s.charAt(start) - 'a'] > 0) {
                    remaining++;
                }
                start++;
            }
        }

        return result;
    }

    // Leetcode problem: 516
    /*
     * Dynamic programming
     * See Leetcode problem: 132 & 647
     * */
    public int longestPalindromeSubseq(String s) {
        int strLen = s.length();
        int[][] palindromeTable = new int[strLen][strLen];

        for (int i = 0; i < strLen; i++) {
            palindromeTable[i][i] = 1;
        }
        for (int i = 0; i < strLen - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                palindromeTable[i][i + 1] = 2;
            } else {
                palindromeTable[i][i + 1] = 1;
            }
        }

        for (int currentLen = 3; currentLen <= strLen; currentLen++) {
            for (int i = 0; i < strLen - (currentLen - 1); i++) {
                int j = i + currentLen - 1;

                if (s.charAt(i) == s.charAt(j)) {
                    palindromeTable[i][j] = 2 + palindromeTable[i + 1][j - 1];
                } else {
                    palindromeTable[i][j] = Math.max(palindromeTable[i][j - 1], palindromeTable[i + 1][j]);
                }
            }
        }

        return palindromeTable[0][strLen - 1];
    }

    // Leetcode problem: 639
    // Leetcode problem: 686
    /*
     * Math.ceil(b.length() / a.length()) or Math.ceil(b.length() / a.length()) + 1
     * */

    // Leetcode problem: 1028
}
