package string;

import java.util.HashMap;
import java.util.Map;

public class StringSolution {

    public static void main(String[] args) {
        StringSolution stringSolution = new StringSolution();
        System.out.println(stringSolution.lengthOfLongestSubstring("abcdab"));
    }

    // Leetcode problem: 3
    /*
    * Build a map to store index of every character
    * If a character repeats then update the index and start index of substring
    * Else update the maximum length so far
    */
    public int lengthOfLongestSubstring(String s) {
        int maxLength = 0, start = 0;
        Map<Character, Integer> indexMap = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (indexMap.containsKey(ch)) {
                indexMap.replace(ch, i);
                start = indexMap.get(ch) + 1;
            } else{
                indexMap.put(ch, i);
                maxLength = Math.max(maxLength, i - start + 1);
            }
        }

        return maxLength;
    }

}
