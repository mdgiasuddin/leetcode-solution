package sliding;

import java.util.*;

public class SlidingWindowSolution {
    public static void main(String[] args) {

    }

    // Leetcode problem: 3
    /*
     * Build a map to store index of every character
     * If a character repeats then update the index and start index of substring
     * Else update the maximum length so far
     * */
    public int lengthOfLongestSubstring(String s) {
        int maxLength = 0, start = 0;
        Map<Character, Integer> indexMap = new HashMap<>();

        for (int end = 0; end < s.length(); end++) {
            char ch = s.charAt(end);
            if (indexMap.containsKey(ch)) {
                indexMap.replace(ch, end);

                // Slide start to the next position of non-repeating index
                start = indexMap.get(ch) + 1;
            } else {
                indexMap.put(ch, end);
                maxLength = Math.max(maxLength, end - start + 1);
            }
        }

        return maxLength;
    }

    // Leetcode problem: 121
    /*
     * Maintain two value, left for buy and right for sell
     * */
    public int maxProfit(int[] prices) {
        int left = 0, right = 1, maxProfit = 0;

        while (right < prices.length) {
            if (prices[left] < prices[right]) {
                // Can make profit, Check whether it is maximum
                maxProfit = Math.max(maxProfit, prices[right] - prices[left]);
            } else {
                // Lower price found, buy in this day
                left = right;
            }

            right++;
        }

        return maxProfit;
    }

    // Leetode problem: 187
    /*
     * Solve the problem by sliding window
     * Instead of storing string calculate hash value and store.
     * Keep track if the string has been taken in result or not
     * Calculate hash value by 4-based integer where A=0, C=1, G=2, T=3
     * */
    public List<String> findRepeatedDnaSequences(String s) {
        List<String> resultList = new ArrayList<>();

        if (s.length() <= 10)
            return resultList;

        Map<Character, Integer> dnaCharValue = new HashMap<>() {{
            put('A', 0);
            put('C', 1);
            put('G', 2);
            put('T', 3);
        }};

        Map<Integer, Boolean> hashValueMap = new HashMap<>();

        int hashValue = 0;
        for (int i = 0; i < 10; i++) {
            hashValue = (hashValue << 2) + dnaCharValue.get(s.charAt(i));
        }

        hashValueMap.put(hashValue, false);

        int leftPos = 0;
        int fourPower = 262144; // 4^9
        for (int i = 10; i < s.length(); i++) {
            hashValue -= (fourPower * dnaCharValue.get(s.charAt(leftPos++))); // Subtract the value left character
            hashValue <<= 2; // Shift the position to left
            hashValue += dnaCharValue.get(s.charAt(i)); // Add new character

            if (hashValueMap.containsKey(hashValue)) {
                if (!hashValueMap.get(hashValue)) {
                    resultList.add(s.substring(leftPos, i + 1));
                    hashValueMap.replace(hashValue, true);
                }
            } else {
                hashValueMap.put(hashValue, false);
            }
        }

        return resultList;
    }

    // Leetcode problem: 239
    public int[] maxSlidingWindow(int[] nums, int k) {
        Deque<Integer> deque = new ArrayDeque<>();

        int[] result = new int[nums.length - k + 1];
        int left, right;
        left = right = 0;

        while (right < nums.length) {
            while (!deque.isEmpty() && nums[right] > nums[deque.peekLast()]) {
                deque.pollLast();
            }
            deque.offerLast(right);

            // Pop the left value if the slide crosses
            if (left > deque.peekFirst()) {
                deque.pollFirst();
            }

            // For every slide determine the maximum
            if (right + 1 >= k) {
                result[left] = nums[deque.peekFirst()];
                left++;
            }

            right++;
        }

        return result;
    }

    // Leetcode problem: 567
    /*
     * Compare with counter by sliding window
     * */
    public boolean checkInclusion(String s1, String s2) {
        int remaining = s1.length();

        int[] frequency = new int[26];

        for (int i = 0; i < s1.length(); i++) {
            frequency[s1.charAt(i) - 'a']++;
        }

        for (int i = 0, ws = 0; i < s2.length(); i++) {
            char ch = s2.charAt(i);
            if (frequency[ch - 'a'] > 0) { // Character is present in pattern
                remaining--;
            }

            frequency[ch - 'a']--;

            if (remaining == 0) { // All characters of pattern matched
                return true;
            }

            /*
             * If i traversed more than the pattern length then restore the counter of leftmost character of current window
             * */
            if (i >= s1.length() - 1) {
                if (++frequency[s2.charAt(ws) - 'a'] > 0) {
                    remaining++;
                }
                ws++;
            }
        }

        return false;
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
                // The character is present in p, so decrease the remaining
                remaining--;
                if (remaining == 0) {
                    // All characters covered, so add it to the result
                    result.add(start);
                }
            }

            // Decrease the counter. If the character is not present it will be negative
            counter[ch - 'a']--;

            if (i >= p.length() - 1) {
                if (++counter[s.charAt(start) - 'a'] > 0) {

                    // If the first character taken is present in p, then increase the remaining.
                    remaining++;
                }
                start++;
            }
        }

        return result;
    }

    // Leetcode problem: 978
    public int maxTurbulenceSize(int[] arr) {
        int start = 0, end, len = arr.length, maxLength = 1;


        while (start + 1 < len) {

            // Skip repeating number is start
            if (arr[start] == arr[start + 1]) {
                start++;
                continue;
            }

            end = start + 1;
            while (end + 1 < len && isTurbulent(arr, end))
                end++;

            maxLength = Math.max(maxLength, end - start + 1);

            // Slide start to end position
            start = end;

        }

        return maxLength;
    }

    private boolean isTurbulent(int[] arr, int k) {
        return (arr[k] > arr[k - 1] && arr[k] > arr[k + 1])
                || (arr[k] < arr[k - 1] && arr[k] < arr[k + 1]);
    }

    // Leetcode problem: 1025
    public boolean divisorGame(int n) {

        // Anyone who will get odd number will always lose and even will always win
        // For odd number, there is no option except odd number. He will leave even n for another

        return n % 2 == 0;
    }
}
