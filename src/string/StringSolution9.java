package string;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringSolution9 {

    // Leetcode problem: 30

    /**
     * Substring with Concatenation of All Words.
     * Explanation: https://www.youtube.com/watch?v=EVHQ48RM5tw
     * Count the words. Starting from every index found if possible to get all the words.
     * */
    public List<Integer> findSubstring(String s, String[] words) {
        Map<String, Integer> countMap = new HashMap<>();
        int n = words.length;
        int len = words[0].length();
        for (String word : words) {
            countMap.put(word, countMap.getOrDefault(word, 0) + 1);
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i <= s.length() - n * len; i++) {
            Map<String, Integer> copyMap = new HashMap<>(countMap);
            int j = 0;
            for (; j < n; j++) {
                String str = s.substring(i + j * len, i + (j + 1) * len);
                int count = copyMap.getOrDefault(str, 0);
                if (count == 0) {
                    break;
                }

                if (count > 1) {
                    copyMap.put(str, count - 1);
                } else {
                    copyMap.remove(str);
                }

            }
            if (j == n) {
                result.add(i);
            }
        }

        return result;
    }

    // Leetcode problem: 890

    /**
     * Find and Replace Pattern.
     * This problem is similar to : Isomorphic Strings (Leetcode problem: 205).
     * */
    public List<String> findAndReplacePattern(String[] words, String pattern) {
        int n = pattern.length();
        List<String> result = new ArrayList<>();
        for (String word : words) {
            Map<Character, Character> w2p = new HashMap<>();
            Map<Character, Character> p2w = new HashMap<>();

            int i = 0;
            for (; i < n; i++) {
                if ((w2p.containsKey(word.charAt(i)) && w2p.get(word.charAt(i)) != pattern.charAt(i)) ||
                        (p2w.containsKey(pattern.charAt(i)) && p2w.get(pattern.charAt(i)) != word.charAt(i))) {
                    break;
                }

                w2p.put(word.charAt(i), pattern.charAt(i));
                p2w.put(pattern.charAt(i), word.charAt(i));
            }

            if (i == n) {
                result.add(word);
            }
        }

        return result;
    }

    // Leetcode problem: 2381

    /**
     * Shifting Letters II.
     * Explanation: https://www.youtube.com/watch?v=eEUjVY7wK3k
     * Create a difference array.
     * For the left index store the difference & right index + 1 the reversed difference.
     * Sequentially add the difference & apply the shift.
     * */
    public String shiftingLetters(String s, int[][] shifts) {
        int n = s.length();
        int[] diffs = new int[n + 1];

        for (int[] shift : shifts) {
            int diff = shift[2] == 1 ? 1 : -1;
            diffs[shift[0]] += diff;
            diffs[shift[1] + 1] -= diff;
        }

        StringBuilder res = new StringBuilder();
        int diff = 0;
        for (int i = 0; i < n; i++) {
            diff += diffs[i];
            char ch = (char) ('a' + ((s.charAt(i) - 'a' + diff) % 26 + 26) % 26);
            res.append(ch);
        }

        return res.toString();
    }
}
