package dp;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class RecursiveDP3 {

    // Leetcode problem: 87
    /*
     * Scramble String.
     * Explanation: https://www.youtube.com/watch?v=MDmZm_aVDF8
     * */
    public boolean isScramble(String s1, String s2) {
        return isScramble(s1, s2, new HashMap<>());
    }

    private boolean isScramble(String s1, String s2, Map<String, Boolean> map) {
        if (s1.length() == 1) {
            return s1.equals(s2);
        }
        if (s1.equals(s2)) {
            return true;
        }

        String key = s1 + ":" + s2;
        if (map.containsKey(key)) {
            return map.get(key);
        }

        for (int i = 1; i < s1.length(); i++) {
            if (
                (isScramble(s1.substring(0, i), s2.substring(0, i), map) &&
                    isScramble(s1.substring(i), s2.substring(i), map)) ||
                    (isScramble(s1.substring(0, i), s2.substring(s2.length() - i), map) &&
                        isScramble(s1.substring(i), s2.substring(0, s2.length() - i), map))
            ) {
                map.put(key, true);
                return true;
            }
        }

        map.put(key, false);
        return false;
    }

    // Leetcode problem: 1048
    /*
     * Longest String Chain.
     * Explanation: https://www.youtube.com/watch?v=7b0V1gT_TIk
     * */
    public int longestStrChain(String[] words) {
        Arrays.sort(words, Comparator.comparingInt(String::length).reversed());
        Map<String, Integer> idxMap = new HashMap<>();
        int n = words.length;
        for (int i = 0; i < n; i++) {
            idxMap.put(words[i], i);
        }
        Map<Integer, Integer> dp = new HashMap<>();
        int res = 0;
        for (int i = 0; i < n; i++) {
            res = Math.max(res, dfsWordChain(i, words, idxMap, dp));
        }
        return res;
    }

    private int dfsWordChain(int idx, String[] words, Map<String, Integer> idxMap, Map<Integer, Integer> dp) {
        if (dp.containsKey(idx)) {
            return dp.get(idx);
        }

        int res = 1;
        for (int i = 0; i < words[idx].length(); i++) {
            String substr = words[idx].substring(0, i) + words[idx].substring(i + 1);
            if (idxMap.containsKey(substr)) {
                res = Math.max(res, 1 + dfsWordChain(idxMap.get(substr), words, idxMap, dp));
            }
        }
        dp.put(idx, res);
        return res;
    }
}
