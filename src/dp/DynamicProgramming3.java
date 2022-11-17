package dp;

import java.util.Arrays;
import java.util.Comparator;

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

    // Leetcode problem: 1143
    public int longestCommonSubsequence(String text1, String text2) {
        int n1 = text1.length();
        int n2 = text2.length();
        int[][] dp = new int[n1 + 1][n2 + 1];

        for (int i = 1; i <= n1; i++) {
            for (int j = 1; j <= n2; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[n1][n2];
    }

    // Leetcode problem: 1092
    /*
     * This problem is the extended version of the Longest Common Subsequence (Leetcode problem: 1143).
     * Explanation: https://www.youtube.com/watch?v=pHXntFeu6f8&list=PLEJXowNB4kPxBwaXtRO1qFLpCzF75DYrS&index=20
     * */
    public String shortestCommonSupersequence(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // Find the LCS String.
        StringBuilder lcs = new StringBuilder();
        int i = m;
        int j = n;
        while (i > 0 && j > 0) {
            if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                lcs.insert(0, str1.charAt(i - 1));

                i -= 1;
                j -= 1;
            } else if (dp[i][j] == dp[i - 1][j]) {
                i -= 1;
            } else {
                j -= 1;
            }
        }

        // Build up the SCS String.
        i = 0;
        j = 0;
        int k = 0;
        StringBuilder scs = new StringBuilder();
        while (i < str1.length() && j < str2.length() && k < lcs.length()) {
            while (str1.charAt(i) != lcs.charAt(k)) {
                scs.append(str1.charAt(i));
                i += 1;
            }
            while (str2.charAt(j) != lcs.charAt(k)) {
                scs.append(str2.charAt(j));
                j += 1;
            }
            scs.append(lcs.charAt(k));
            k += 1;
            i += 1;
            j += 1;
        }

        while (i < str1.length()) {
            scs.append(str1.charAt(i));
            i += 1;
        }
        while (j < str2.length()) {
            scs.append(str2.charAt(j));
            j += 1;
        }

        return scs.toString();
    }

    /*
     * Box Stacking.
     * Practice: https://practice.geeksforgeeks.org/problems/box-stacking/1
     * Explanation: https://www.youtube.com/watch?v=kLucR6-Q0GA&list=PLEJXowNB4kPxBwaXtRO1qFLpCzF75DYrS&index=25
     * Similar to the Longest Increasing Subsequence.
     * */
    public int maxHeight(int[] height, int[] width, int[] length, int n) {
        // Code here

        int len = n * 3;
        int[][] boxes = new int[len][3];

        // Create boxes by rotating.
        for (int i = 0; i < n; i++) {
            boxes[3 * i][0] = height[i];
            boxes[3 * i][1] = Math.max(width[i], length[i]);
            boxes[3 * i][2] = Math.min(width[i], length[i]);

            boxes[3 * i + 1][0] = width[i];
            boxes[3 * i + 1][1] = Math.max(height[i], length[i]);
            boxes[3 * i + 1][2] = Math.min(height[i], length[i]);

            boxes[3 * i + 2][0] = length[i];
            boxes[3 * i + 2][1] = Math.max(height[i], width[i]);
            boxes[3 * i + 2][2] = Math.min(height[i], width[i]);
        }

        // Sort based on the base area descending order.
        Arrays.sort(boxes, Comparator.comparingInt(a -> -a[1] * a[2]));

        int ans = 0;
        int[] lis = new int[len];
        for (int i = 0; i < len; i++) {
            lis[i] = boxes[i][0];

            for (int j = i - 1; j >= 0; j--) {
                if (boxes[j][1] > boxes[i][1] && boxes[j][2] > boxes[i][2]) {
                    lis[i] = Math.max(lis[i], boxes[i][0] + lis[j]);
                }
            }

            ans = Math.max(ans, lis[i]);
        }

        return ans;
    }

    // Leetcode problem: 354
    /*
     * Russian Doll Envelopes.
     * This is a variation of the Longest Increasing Subsequence.
     * */
    public int maxEnvelopes(int[][] envelopes) {
        Arrays.sort(envelopes, Comparator.comparingInt(a -> a[0] * a[1]));
        int n = envelopes.length;
        int[] lis = new int[n];

        int ans = 0;
        for (int i = 0; i < n; i++) {
            lis[i] = 1;

            for (int j = i - 1; j >= 0; j--) {
                if (envelopes[i][0] > envelopes[j][0] && envelopes[i][1] > envelopes[j][1]) {
                    lis[i] = Math.max(lis[i], 1 + lis[j]);
                }
            }

            ans = Math.max(ans, lis[i]);
        }

        return ans;
    }

    /*
     * Maximum sum increasing subsequence.
     * Practice: https://practice.geeksforgeeks.org/problems/maximum-sum-increasing-subsequence4749/1
     * This is a variation of the Longest Increasing Subsequence.
     * */
    public int maxSumIS(int[] arr, int n) {
        int[] msis = new int[n];

        int ans = 0;
        for (int i = 0; i < n; i++) {
            msis[i] = arr[i];

            for (int j = i - 1; j >= 0; j--) {
                if (arr[i] > arr[j]) {
                    msis[i] = Math.max(msis[i], arr[i] + msis[j]);
                }
            }

            ans = Math.max(ans, msis[i]);
        }

        return ans;
    }
}
