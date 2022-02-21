package string;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringSolution {

    public static void main(String[] args) {
        StringSolution stringSolution = new StringSolution();
//        System.out.println(stringSolution.lengthOfLongestSubstring("abcdab"));
        System.out.println(stringSolution.convert("PAYPALISHIRING", 3));
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
            } else {
                indexMap.put(ch, i);
                maxLength = Math.max(maxLength, i - start + 1);
            }
        }

        return maxLength;
    }


    // Leetcode problem: 5
    /*
     * for every index traverse left and right simultaneously if palindrome found
     * palindrome can be odd length or even length
     * check both even and odd length
     */
    public String longestPalindrome(String s) {
        if (s.length() == 0)
            return s;

        int maxLength = 1;
        int start = 0, len = s.length(), high, low;
        for (int i = 0; i < s.length(); i++) {
            // even length substring
            high = i;
            low = i - 1;
            while (low >= 0 && high < len && s.charAt(low) == s.charAt(high)) {
                if (high - low + 1 > maxLength) {
                    maxLength = high - low + 1;
                    start = low;
                }
                high++;
                low--;
            }

            // odd length substring
            high = i + 1;
            low = i - 1;
            while (low >= 0 && high < len && s.charAt(low) == s.charAt(high)) {
                if (high - low + 1 > maxLength) {
                    maxLength = high - low + 1;
                    start = low;
                }
                high++;
                low--;
            }
        }

        return s.substring(start, start + maxLength);
    }

    // Leetcode problem: 6
    /*
     * Zigzag conversion to number of rows
     * Efficient solution without space
     * Find the next character every time to insert
     * Traverse the number of row incremental stage
     */
    public String convert(String s, int numRows) {
        if (numRows == 1)
            return s;

        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < numRows; i++) {
            int inc = 2 * (numRows - 1);
            for (int j = i; j < s.length(); j += inc) {

                // Character for incremental stage
                ans.append(s.charAt(j));

                // Character for decremental stage
                if (i > 0 && i < numRows - 1 && j + inc - 2 * i < s.length())
                    ans.append(s.charAt(j + inc - 2 * i));
            }
        }
        return ans.toString();
    }

}
