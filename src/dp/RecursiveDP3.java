package dp;

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
}
