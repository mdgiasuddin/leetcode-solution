package prefix;

import java.util.HashMap;
import java.util.Map;

public class PrefixSumSolution {

    public static void main(String[] args) {

    }

    // Leetcode problem: 238
    /*
     * The problem can be solved by storing prefix and suffix multiplication.
     * To reduce the memory we will store only the prefix.
     * Then multiply with the suffix.
     * */
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
}
