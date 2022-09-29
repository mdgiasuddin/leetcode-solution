package facebook;

import java.util.HashMap;
import java.util.Map;

public class FacebookSolution {

    public static void main(String[] args) {
        FacebookSolution facebookSolution = new FacebookSolution();

        int[] nums = {1, 4, 2, 10, 23, 3, 1, 0, 20};
        System.out.println(facebookSolution.longestSubstringWithKUniqueCharacter("aabacbebebe", 3));
    }

    public int smallestSubArrayGreaterSum(int[] nums, int x) {
        int l = 0, r = 0, sum = nums[0];

        int minLen = nums.length + 1;
        while (r < nums.length) {
            if (sum <= x) {
                r += 1;
                if (r < nums.length) {
                    sum += nums[r];
                }
            } else {
                minLen = Math.min(minLen, r - l + 1);
                sum -= nums[l++];
            }
        }

        return minLen;
    }

    public int maximumSumOfSizeK(int[] nums, int k) {
        int sum = 0;
        for (int i = 0; i < k; i++) {
            sum += nums[i];
        }

        int maxSum = sum;

        for (int i = k; i < nums.length; i++) {
            sum += (nums[i] - nums[i - k]);

            maxSum = Math.max(maxSum, sum);
        }

        return maxSum;
    }

    public int longestSubstringWithKUniqueCharacter(String s, int k) {
        Map<Character, Integer> map = new HashMap<>();
        int maxLen = 0;

        int l = 0, r = 0;

        while (r < s.length()) {
            char ch = s.charAt(r);
            int prev = map.getOrDefault(ch, 0);
            map.put(ch, prev + 1);

            while (map.size() > k) {
                int c = map.get(s.charAt(l)) - 1;

                if (c == 0) {
                    map.remove(s.charAt(l));
                } else {
                    map.replace(s.charAt(l), c);
                }

                l += 1;
            }

            System.out.println(ch + " : " + map);
            maxLen = Math.max(maxLen, r - l + 1);
            r += 1;
        }

        return maxLen;
    }
}
