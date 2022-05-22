package dp;

public class DynamicProgramming2 {

    public static void main(String[] args) {

    }

    // Leetcode problem 343
    public int integerBreak(int n) {
        int[] dp = new int[n + 1];

        for (int i = 1; i <= n; i++) {

            // If i is any middle number, we can use i without breaking it
            if (i < n)
                dp[i] = i;

            // Try to get maximum by breaking
            // Since there is j & i - j, we can loop only left side
            for (int j = 1; j <= i / 2; j++) {
                dp[i] = Math.max(dp[i], dp[j] * dp[i - j]);
            }
        }

        return dp[n];
    }

    // Leetcode problem: 152
    /*
     * This solution is tricky.
     * */
    public int maxProduct(int[] nums) {
        int currentMax = 1, currentMin = 1;

        // First assume the maximum value of the array is the max product.
        int result = nums[0];
        for (int num : nums) {
            result = Math.max(result, num);
        }

        for (int num : nums) {

            // If num is 0, no need to continue with previous sub-array. reset it.
            if (num == 0) {
                currentMax = 1;
                currentMin = 1;
                continue;
            }

            // Current max and min are the maximum and minimum product respectively including
            // this number.
            int tmp = num * currentMax;
            currentMax = Math.max(num, Math.max(tmp, num * currentMin));
            currentMin = Math.min(num, Math.min(tmp, num * currentMin));

            result = Math.max(result, currentMax);
        }

        return result;
    }
}
