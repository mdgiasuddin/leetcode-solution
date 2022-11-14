package dp;

public class DynamicProgramming3 {

    // Leetcode problem: 494
    public int findTargetSumWays(int[] nums, int target) {
        int sum = 0;
        int zeroCount = 0;

        // If positive target exists, negative target also exists by reversing the signs.
        target = Math.abs(target);
        for (int num : nums) {
            sum += num;
            if (num == 0) {
                zeroCount += 1;
            }
        }
        if ((sum + target) % 2 == 1) {
            return 0;
        }

        // Isolate 0 valued elements.
        int n = nums.length;
        if (zeroCount > 0) {
            int[] copyNums = new int[n - zeroCount];
            int i = 0;
            for (int num : nums) {
                if (num > 0) {
                    copyNums[i++] = num;
                }
            }
            nums = copyNums;
            n = copyNums.length;
        }

        target = (sum + target) / 2;

        int[][] dp = new int[n + 1][target + 1];

        for (int i = 0; i <= n; i++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= target; j++) {
                dp[i][j] = dp[i - 1][j];

                if (nums[i - 1] <= j) {
                    dp[i][j] += dp[i - 1][j - nums[i - 1]];
                }
            }
        }

        // Now 0 valued can be either '+' or '-', so there is 2 options for each 0.
        int zeroPower = (int) Math.pow(2, zeroCount);
        return dp[n][target] * zeroPower;
    }
}
