package sliding;

import java.util.HashMap;
import java.util.Map;

public class SlidingWindowSolution2 {
    public static void main(String[] args) {
        SlidingWindowSolution2 slidingWindowSolution2 = new SlidingWindowSolution2();

        System.out.println(slidingWindowSolution2.minWindow("ADOBECODEBANC", "ABC"));
    }

    // Leetcode problem: 76
    public String minWindow(String s, String t) {
        if (s.isEmpty() || t.isEmpty())
            return "";

        Map<Character, Integer> tCount = new HashMap<>();

        for (int i = 0; i < t.length(); i++) {
            char ch = t.charAt(i);
            int c = tCount.getOrDefault(ch, 0);
            tCount.put(ch, c + 1);
        }

        int matched = 0, left = 0, right = 0, minLength = s.length() + 1;
        String result = "";

        while (right < s.length()) {
            char ch = s.charAt(right);

            if (tCount.containsKey(ch)) {
                int c = tCount.get(ch);
                tCount.put(ch, c - 1);

                // If character is required now. If extra then no need to increase matched.
                if (c > 0)
                    matched++;

                // If all characters matched then try to slide left pointer.
                while (matched == t.length()) {

                    if (right - left + 1 < minLength) {
                        minLength = right - left + 1;
                        result = s.substring(left, right + 1);
                    }

                    char cl = s.charAt(left);
                    if (tCount.containsKey(cl)) {
                        int lc = tCount.get(cl);
                        tCount.put(cl, lc + 1);

                        if (lc >= 0)
                            matched--;
                    }

                    left++;
                }
            }

            right++;
        }

        return result;
    }
}
