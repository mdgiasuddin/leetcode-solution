package string;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringSolution9 {

    // Leetcode problem: 30
    /*
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
}
