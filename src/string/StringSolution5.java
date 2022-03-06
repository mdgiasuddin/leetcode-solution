package string;

import java.util.ArrayList;
import java.util.List;

public class StringSolution5 {
    public static void main(String[] args) {

    }

    // Leetcode problem: 433

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
}
