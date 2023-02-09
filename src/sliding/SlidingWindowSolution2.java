package sliding;

import java.util.Arrays;
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

    // Leetcode problem: 1423
    /*
     * Since card can be selected only from left end or right, decide how many cards from left & right end.
     * */
    public int maxScore(int[] cardPoints, int k) {
        int sum = 0, n = cardPoints.length;

        // First take all the cards from left end.
        for (int i = 0; i < k; i++) {
            sum += cardPoints[i];
        }

        int res = sum;

        // Then try adding from right end & removing from left end. Find which one gives maximum.
        for (int i = 1; i <= k; i++) {
            sum += (cardPoints[n - i] - cardPoints[k - i]);
            res = Math.max(res, sum);
        }

        return res;
    }

    // Leetcode problem: 1888
    /*
     * Minimum Number of Flips to Make the Binary String Alternating.
     * Removing character from left actually rotates the string left.
     * So, concat the original string to get all the rotations.
     * */
    public int minFlips(String s) {
        int n = s.length();
        s = s + s;

        StringBuilder alt1 = new StringBuilder();
        StringBuilder alt2 = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (i % 2 == 0) {
                alt1.append('0');
                alt2.append('1');
            } else {
                alt1.append('1');
                alt2.append('0');
            }
        }

        int l = 0;
        int diff1 = 0;
        int diff2 = 0;
        int res = n;

        for (int r = 0; r < s.length(); r++) {
            if (s.charAt(r) != alt1.charAt(r)) {
                diff1 += 1;
            }
            if (s.charAt(r) != alt2.charAt(r)) {
                diff2 += 1;
            }

            if (r - l + 1 > n) {
                if (s.charAt(l) != alt1.charAt(l)) {
                    diff1 -= 1;
                }
                if (s.charAt(l) != alt2.charAt(l)) {
                    diff2 -= 1;
                }
                l += 1;
            }

            if (r - l + 1 == n) {
                res = Math.min(res, Math.min(diff1, diff2));
            }
        }

        return res;
    }

    // Leetcode problem: 2516
    /*
     * Take K of Each Character From Left and Right.
     * Explanation: https://www.youtube.com/watch?v=fbe3R7Bd-LE&t=777s
     * Calculate the maximum length of substring without which each character occurs k times.
     * */
    public int takeCharacters(String s, int k) {
        int[] count = new int[3];

        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a'] += 1;
        }

        if (count[0] < k || count[1] < k || count[2] < k) {
            return -1;
        }

        int l = 0;
        int r = 0;

        int max = 0;
        while (r < s.length()) {
            count[s.charAt(r) - 'a'] -= 1;

            while (l <= r && (count[0] < k || count[1] < k || count[2] < k)) {
                count[s.charAt(l++) - 'a'] += 1;
            }

            r += 1;
            max = Math.max(max, r - l);
        }

        return s.length() - max;
    }

    // Leetcode problem: 1343
    /*
     * Number of Sub-arrays of Size K and Average Greater than or Equal to Threshold.
     * */
    public int numOfSubarrays(int[] arr, int k, int threshold) {
        int expected = threshold * k;
        int current = 0;
        int i = 0;
        while (i < k) {
            current += arr[i];
            i += 1;
        }

        int res = current >= expected ? 1 : 0;
        while (i < arr.length) {
            current += arr[i] - arr[i - k];
            if (current >= expected) {
                res += 1;
            }
            i += 1;
        }
        return res;
    }

    // Leetcode problem: 2090
    /*
     * K Radius Subarray Averages.
     * */
    public int[] getAverages(int[] nums, int k) {
        int n = nums.length;
        int[] res = new int[n];
        Arrays.fill(res, -1);
        int groupSize = 2 * k + 1;
        if (n < groupSize) {
            return res;
        }

        int i = 0;
        long current = 0;
        while (i < groupSize) {
            current += nums[i];
            i += 1;
        }

        res[k] = (int) (current / groupSize);
        while (i < n) {
            current += nums[i] - nums[i - groupSize];
            res[i - k] = (int) (current / groupSize);
            i += 1;
        }

        return res;
    }
}
