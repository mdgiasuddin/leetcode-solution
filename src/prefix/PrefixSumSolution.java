package prefix;

import java.util.HashMap;
import java.util.Map;

public class PrefixSumSolution {

    public static void main(String[] args) {

    }

    // Leetcode problem: 238

    /**
     * The problem can be solved by storing prefix and suffix multiplication.
     * To reduce the memory we will store only the prefix.
     * Then multiply with the suffix.
     *
     */
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];

        // For first element prefix is 1.
        result[0] = 1;

        // result[i] stores product of [0...i-1].
        for (int i = 1; i < n; i++) {
            result[i] = result[i - 1] * nums[i - 1];
        }

        // Then multiply with the suffix. For last element suffix is 1.
        int right = 1;
        for (int i = n - 1; i >= 0; i--) {
            result[i] = result[i] * right;
            right *= nums[i];
        }

        return result;
    }

    // Leetcode problem: 560

    /**
     * This problem is similar to: Subarray Sums Divisible by K (Leetcode problem: 974)
     */
    public int subarraySum(int[] nums, int k) {

        // Prefix sum stores how many times current sum is present from the start position.
        Map<Integer, Integer> prefixSum = new HashMap<>();
        int res = 0, currentSum = 0;
        prefixSum.put(0, 1);

        for (int num : nums) {
            currentSum += num;

            // If current - k is present in the prefix sum, then we can take the sub-array window
            // after sum k to current.
            res += prefixSum.getOrDefault(currentSum - k, 0);

            int prev = prefixSum.getOrDefault(currentSum, 0);
            prefixSum.put(currentSum, 1 + prev);
        }

        return res;
    }

    // Leetcode problem: 974

    /**
     * Subarray Sums Divisible by K.
     * This is similar to: Subarray Sum Equals K (Leetcode problem: 560).
     * Challenging part is to avoid negative reminder.
     *
     */
    public int subarraysDivByK(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        int res = 0;
        map.put(0, 1);
        int currentSum = 0;

        for (int num : nums) {
            currentSum = ((currentSum + num % k) % k + k) % k;
            int count = map.getOrDefault(currentSum, 0);
            res += count;
            map.put(currentSum, count + 1);
        }

        return res;
    }

    // Leetcode problem: 523

    /**
     * This problem is similar to: Subarray Sum Equals K. (Leetcode problem: 560)
     * Instead of storing the current sum, store the reminder.
     *
     */
    public boolean checkSubarraySum(int[] nums, int k) {
        Map<Integer, Integer> prefixMap = new HashMap<>();

        prefixMap.put(0, -1);
        int currentSum = 0;
        for (int i = 0; i < nums.length; i++) {
            currentSum = (currentSum + nums[i]) % k;

            // Since sub-array size >= 2, check the difference of previous index > 1.
            if (prefixMap.containsKey(currentSum) && i - prefixMap.get(currentSum) > 1)
                return true;

            // If the reminder already stored don't need to store again.
            if (!prefixMap.containsKey(currentSum))
                prefixMap.put(currentSum, i);

        }

        return false;
    }

    // Leetcode problem: 1590

    /**
     * Make Sum Divisible by P.
     * This problem is similar to: Leetcode problem: 523, 560, 974
     * Reference: https://www.youtube.com/watch?v=7FJrMTpadRI
     */
    public int minSubarray(int[] nums, int p) {
        long sum = 0;
        int n = nums.length;
        for (int num : nums) {
            sum += num;
        }

        long rem = sum % p;
        if (rem == 0) {
            return 0;
        }

        Map<Long, Integer> map = new HashMap<>();
        int rLen = n;
        long cur = 0;

        map.put(cur, -1);
        for (int i = 0; i < n; i++) {
            cur = (cur + nums[i]) % p;
            long prev = (cur - rem + p) % p;
            if (map.containsKey(prev)) {
                rLen = Math.min(rLen, i - map.get(prev));
            }
            map.put(cur, i);
        }

        return rLen == n ? -1 : rLen;
    }
}
