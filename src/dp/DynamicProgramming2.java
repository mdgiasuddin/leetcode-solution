package dp;

import java.util.*;

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

    // Leetcode problem: 746
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        int first = cost[0], second = cost[1];

        for (int i = 2; i < n; i++) {
            int third = cost[i] + Math.min(first, second);
            first = second;
            second = third;
        }

        return Math.min(first, second);
    }

    // Leetcode problem: 740
    /*
     * This problem is similar to House Robber I (Leetcode problem: 198).
     * Sort the numbers and build up a counter of the elements and remove the duplicates.
     * */
    public int deleteAndEarn(int[] nums) {
        Arrays.sort(nums);

        List<Integer> uniqueNums = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();

        map.values();

        uniqueNums.add(nums[0]);
        int count = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1]) {
                uniqueNums.add(nums[i]);
                map.put(nums[i - 1], count);
                count = 1;
            } else {
                count++;
            }
        }

        map.put(nums[nums.length - 1], count);

        int[] dp = new int[uniqueNums.size()];
        dp[0] = uniqueNums.get(0) * map.get(uniqueNums.get(0));
        if (uniqueNums.size() > 1) {
            int included = (uniqueNums.get(1) * map.get(uniqueNums.get(1))) + (uniqueNums.get(1) - 1 == uniqueNums.get(0) ? 0 : dp[0]);
            dp[1] = Math.max(included, dp[0]);
        }

        for (int i = 2; i < uniqueNums.size(); i++) {
            int included = (uniqueNums.get(i) * map.get(uniqueNums.get(i))) + (uniqueNums.get(i) - 1 == uniqueNums.get(i - 1) ? dp[i - 2] : dp[i - 1]);

            dp[i] = Math.max(included, dp[i - 1]);

        }

        return dp[uniqueNums.size() - 1];
    }

    // Leetcode problem: 1911
    /*
     * The solution is little tricky.
     * */
    public long maxAlternatingSum(int[] nums) {
        long sumEven = 0, sumOdd = 0;

        /*
         * Even sum can be made by previous even sum or previous odd sum + num
         * Odd sum can be made by previous odd sum or previous even sum - num
         * */
        for (int num : nums) {
            long tmpEven = Math.max(sumOdd + num, sumEven);
            long tmpOdd = Math.max(sumEven - num, sumOdd);
            sumEven = tmpEven;
            sumOdd = tmpOdd;
        }

        return sumEven;
    }

    // Leetcode problem: 1866
    /*
     * If the largest stick is set to the last then 1 stick always visible.
     * If another stick smaller than the largest set to the last, the stick will always invisible.
     * So, DP[n][k] = dp[n - 1][k - 1] + (n - 1) * dp[n - 1][k].
     * */
    public int rearrangeSticks(int n, int k) {
        long[][] dp = new long[n + 1][k + 1];
        dp[1][1] = 1;
        long MOD = 1000000007;

        for (int N = 2; N <= n; N++) {
            for (int K = 1; K <= k; K++) {
                long res = (dp[N - 1][K - 1] + (N - 1) * dp[N - 1][K]) % MOD;
                dp[N][K] = res;
            }
        }

        return (int) dp[n][k];
    }

    // Leetcode problem: 72
    public int minDistance(String word1, String word2) {
        int len1 = word1.length(), len2 = word2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 1; i <= len2; i++)
            dp[0][i] = i;

        for (int i = 1; i <= len1; i++)
            dp[i][0] = i;

        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(
                            Math.min(
                                    dp[i][j - 1],   // Insert
                                    dp[i - 1][j]    // Delete
                            ),
                            dp[i - 1][j - 1]    // Replace
                    );
                }
            }
        }

        return dp[len1][len2];
    }

    // Lintcode problem: 515
    // Leetcode problem: 256
    public int minCost(int[][] costs) {
        // write your code here

        int[] dp = {0, 0, 0};

        /*
         * Two adjacent house cannot be painted with same color.
         * So, if costs[0] is taken for the current house, then take the minimum or dp[0], dp[1] of the previous house.
         * dp[0], dp[1], d[2] stores the cost of painting ith house with color 0, 1, 2 respectively.
         * */
        for (int[] cost : costs) {
            int dp0 = cost[0] + Math.min(dp[1], dp[2]);
            int dp1 = cost[1] + Math.min(dp[0], dp[2]);
            int dp2 = cost[2] + Math.min(dp[0], dp[1]);

            dp[0] = dp0;
            dp[1] = dp1;
            dp[2] = dp2;
        }

        return Math.min(dp[0], Math.min(dp[1], dp[2]));
    }

    // Leetcode problem: 97
    public boolean isInterleave(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length())
            return false;

        boolean[][] dp = new boolean[s1.length() + 1][s2.length() + 1];
        dp[0][0] = true;

        for (int j = 1; j <= s2.length(); j++)
            dp[0][j] = dp[0][j - 1] && s3.charAt(j - 1) == s2.charAt(j - 1);

        for (int i = 1; i <= s1.length(); i++)
            dp[i][0] = dp[i - 1][0] && s3.charAt(i - 1) == s1.charAt(i - 1);

        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                dp[i][j] = (dp[i - 1][j] && s3.charAt(i + j - 1) == s1.charAt(i - 1))
                        || (dp[i][j - 1] && s3.charAt(i + j - 1) == s2.charAt(j - 1));
            }
        }

        return dp[s1.length()][s2.length()];
    }

    // Leetcode problem: 1220
    /*
     * This problem is similar to knight dialer (Leetcode problem: 935).
     * a -> e, e - > (a, i), i -> (a, e, o, u), o -> (i, u), u -> a.
     * So, a <- (e, i, u), e <- (a, i), i <- (e, o), o <- i, u <- (i, o).
     * */
    public int countVowelPermutation(int n) {
        int[] dp = {1, 1, 1, 1, 1};
        int MOD = 1000000007;

        for (int j = 2; j <= n; j++) {
            int a = ((dp[1] + dp[2]) % MOD + dp[4]) % MOD;
            int e = (dp[0] + dp[2]) % MOD;
            int i = (dp[1] + dp[3]) % MOD;
            int o = dp[2];
            int u = (dp[2] + dp[3]) % MOD;

            dp[0] = a;
            dp[1] = e;
            dp[2] = i;
            dp[3] = o;
            dp[4] = u;
        }

        int res = 0;
        for (int c : dp)
            res = (res + c) % MOD;

        return res;
    }

    // Leetcode problem: 940
    public int distinctSubseqII(String s) {
        int strLen = s.length();

        long[] dp = new long[strLen + 1];
        dp[0] = 1; // 1 for ""
        dp[1] = 2; // 1 for "" and another for "s[0]"
        int[] lastSeen = new int[26];
        lastSeen[s.charAt(0) - 'a'] = 1;

        long MOD = 1000000007;

        for (int i = 2; i <= strLen; i++) {
            char ch = s.charAt(i - 1);

            /*
             * If the character is new then dp[i] will be doubled dp[i-1].
             * For example "abcbd" -> dp[2] = "", "a", "b", "ab" dp[3] = will be the previous all and 'b' appended with all previous.
             * So, extra string will be added. "c", "ac", "bc", "abc".
             * For dp[3] 'b' is not new. It has been seen at position 2. So, 'b' appended with all strings dp[1] ("b", "ab") will be duplicate.
             * So, subtract dp[1] from dp[3] calculated doubling dp[2].
             * */
            dp[i] = (2 * dp[i - 1]) % MOD;

            if (lastSeen[ch - 'a'] > 0) {
                dp[i] = (dp[i] - dp[lastSeen[ch - 'a'] - 1]) % MOD;
            }

            lastSeen[ch - 'a'] = i;
        }

        // Deduct empty string from final result
        return (int) ((dp[strLen] - 1 + MOD) % MOD);
    }
}
