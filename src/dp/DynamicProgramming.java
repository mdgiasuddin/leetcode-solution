package dp;

import java.util.*;

public class DynamicProgramming {

    public static void main(String[] args) {
        DynamicProgramming dynamicProgramming = new DynamicProgramming();

        int[] array = {100, -1, -100, -1, 100};
        System.out.println(dynamicProgramming.maxResult(array, 2));
    }

    // Leetcode problem: 62
    /*
     * dp[i][j] = dp[i-1][j] + dp[i][j-1]
     * Either from left or from up
     * */
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];

        for (int row = 0; row < m; row++) {
            dp[row][0] = 1;
        }

        for (int col = 0; col < n; col++) {
            dp[0][col] = 1;
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }

        return dp[m - 1][n - 1];

        // This problem can be solved mathematically
        // It is (m+n)Cm

//        int N = m + n - 2, r = Math.min(m, n) - 1;
//        if (N <= 1)
//            return 1;
//
//        Long result = 1L;
//        for (int i = 1; i <= r; i++) {
//            result = (result * (N - i + 1)) / i;
//        }
//
//        return result.intValue();
    }

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length, n = obstacleGrid[0].length;

        // If there is obstacles in first position then it is impossible to move forward
        if (obstacleGrid[0][0] == 1) {
            return 0;
        }

        int[][] dp = new int[m][n];
        dp[0][0] = 1;

        for (int row = 1; row < m; row++) {

            // If there is any obstacles in first column then next position cannot be reached
            if (obstacleGrid[row][0] == 0 && dp[row - 1][0] == 1) {
                dp[row][0] = 1;
            }
        }

        for (int col = 1; col < n; col++) {
            if (obstacleGrid[0][col] == 0 && dp[0][col - 1] == 1) {
                dp[0][col] = 1;
            }
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 0) {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }

        return dp[m - 1][n - 1];
    }

    // Leetcode problem: 70
    /*
     * This is same as fibonacci series
     * */
    public int climbStairs(int n) {
        if (n <= 2)
            return n;

        int fibN_2 = 1;
        int fibN_1 = 2;
        int fibN = 2;

        for (int i = 3; i <= n; i++) {
            fibN = fibN_1 + fibN_2;
            fibN_2 = fibN_1;
            fibN_1 = fibN;
        }

        return fibN;
    }

    // Leetcode problem: 120
    /*
     *           [[2],[3,4],[6,5,7],[4,1,8,3]]
     *                       2
     *                     3   4
     *                    6  5  7
     *                   4  1  8 3
     * In the last level sum is equal to the element
     * Then calculate the sum for middle element
     * */
    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[] dp = new int[triangle.size()];
        for (int i = 0; i < n; i++)
            dp[i] = triangle.get(n - 1).get(i);

        for (int i = triangle.size() - 2; i >= 0; i--) {
            for (int j = 0; j < triangle.get(i).size(); j++) {
                dp[j] = Math.min(dp[j], dp[j + 1]) + triangle.get(i).get(j);
            }
        }

        return dp[0];
    }

    // Leetcode problem: 279
    /*
     * Build up a dp table
     * dp[i] = minimum of 1 + dp[i-j*j] for any i-j*j >= 0
     * 1 for 1 square j*j and others for dp[i-j*j]
     * */
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; i - j * j >= 0; j++) {
                dp[i] = Math.min(dp[i], 1 + dp[i - j * j]);
            }
        }
        return dp[n];
    }

    // Leetcode problem: 377
    public int combinationSum4(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;

        for (int i = 1; i <= target; i++) {
            dp[i] = 0;
            for (int num : nums) {
                // dp[i-num] already built and add num with it
                if (num <= i && dp[i - num] > 0) {
                    dp[i] += dp[i - num];
                }
            }
        }

        return dp[target];
    }


    // Leetcode problem: 403
    /*
     * Build up a set for every stone how many jumps are possible
     * */
    public boolean canCross(int[] stones) {
        Map<Integer, HashSet<Integer>> map = new HashMap<>();

        for (int stone : stones) {
            map.put(stone, new HashSet<>());
        }

        // First jump is always 1.
        map.get(0).add(1);

        for (int stone : stones) {
            HashSet<Integer> jumps = map.get(stone);

            for (int jump : jumps) {
                int reach = stone + jump;

                if (reach == stones[stones.length - 1])
                    return true;

                // If a stone is reached, update the jump option for it
                if (map.containsKey(reach)) {
                    if (jump - 1 > 0) {
                        // Ignore 0 as jump option
                        map.get(reach).add(jump - 1);
                    }
                    map.get(reach).add(jump);
                    map.get(reach).add(jump + 1);
                }
            }
        }

        return false;
    }

    // Leetcode problem: 518
    public int change(int amount, int[] coins) {
        /*int[][] dp = new int[coins.length][amount + 1];

        // For amount 0 the option is always 1 (Omit all the coins)
        for (int row = 0; row < coins.length; row++) {
            dp[row][0] = 1;
        }

        for (int row = 0; row < coins.length; row++) {
            for (int col = 1; col <= amount; col++) {
                // For first row try to take the first coin, else 0
                if (row == 0 && col - coins[row] >= 0) {
                    dp[row][col] = dp[row][col - coins[row]];
                } else if (row > 0) {
                    // For other rows, if coin can be taken, take it or omit it
                    if (col - coins[row] >= 0) {
                        dp[row][col] = dp[row][col - coins[row]] + dp[row - 1][col];
                    }
                    // If coin cannot be taken just omit it
                    else {
                        dp[row][col] = dp[row - 1][col];
                    }
                }
            }
        }

        return dp[coins.length - 1][amount];*/

        // Memory can be optimized by storing only last 2 rows
        int[][] dp = new int[2][amount + 1];
        dp[0][0] = dp[1][0] = 1;

        for (int row = 0; row < coins.length; row++) {
            for (int col = 1; col <= amount; col++) {
                // For first row try to take the first coin, else 0
                if (row == 0 && col - coins[row] >= 0) {
                    dp[0][col] = dp[0][col - coins[row]];
                } else if (row > 0) {
                    // For other rows, if coin can be taken, take it or omit it
                    if (col - coins[row] >= 0) {
                        dp[1][col] = dp[1][col - coins[row]] + dp[0][col];
                    }
                    // If coin cannot be taken just omit it
                    else {
                        dp[1][col] = dp[0][col];
                    }
                }
            }
            // Store the last calculated row to the first
            if (row > 0)
                dp[0] = dp[1];
        }

        return dp[0][amount];
    }

    // Leetcode problem: 413
    /*
     * [1, 2, 3, 4, 5, 7, 9, 11] => [1, 2, 3, 4, 5] & [5, 7, 9, 11] can be used to build slices
     * With [1, 2, 3, 4, 5] 1 + 2 + 3 slices can be build with 5, 4, 3 elements respectively
     * When an element's difference is matched with previous one then the slice number for this element
     * will be 1 more than the previous index
     * */
    public int numberOfArithmeticSlices(int[] nums) {
        /*int[] dp = new int[nums.length];
        int result = 0;

        for (int i = 2; i < nums.length; i++) {
            if (nums[i] - nums[i - 1] == nums[i - 1] - nums[i - 2]) {
                dp[i] = 1 + dp[i - 1];
                result += dp[i];
            }
        }

        return result;*/

        // Here memory can be optimized because dp[i] depends only on dp[i-1], store dp[i-1] only

        int dp = 0, result = 0;
        for (int i = 2; i < nums.length; i++) {
            if (nums[i] - nums[i - 1] == nums[i - 1] - nums[i - 2]) {
                result += ++dp;
            } else {
                // Restore dp
                dp = 0;
            }
        }

        return result;

    }

    // Leetcode problem: 673
    /*
     * Build up 2 array. 1 for length of LIS, another for the count of path to build the LIS
     * Sum up the count of path for maximum LIS
     * */
    public int findNumberOfLIS(int[] nums) {
        int[] lenLIS = new int[nums.length];
        int[] countLIS = new int[nums.length];

        Arrays.fill(lenLIS, 1);
        Arrays.fill(countLIS, 1);

        int maxLIS = 1;
        for (int i = 1; i < nums.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (nums[j] < nums[i]) {
                    // When LIS if updated then reset the count of path
                    if (1 + lenLIS[j] > lenLIS[i]) {
                        lenLIS[i] = 1 + lenLIS[j];
                        countLIS[i] = countLIS[j];
                    }
                    // If same length LIS  found the add the count of [j] with the existing count of [i]
                    else if (1 + lenLIS[j] == lenLIS[i]) {
                        countLIS[i] += countLIS[j];
                    }
                }
            }
            maxLIS = Math.max(maxLIS, lenLIS[i]);
        }

        int result = 0;
        for (int i = 0; i < lenLIS.length; i++) {
            if (lenLIS[i] == maxLIS) {
                result += countLIS[i];
            }
        }

        return result;
    }

    // Letcode problem: 397
    /*
     * DP with memoization
     * It can be done by greedy solution
     * For odd, if n = 4x + 1 or 3 then go to n-1 otherwise n + 1
     * */
    public int integerReplacement(int n) {
        return integerReplaceMemoization(n, new HashMap<>());
    }

    public int integerReplaceMemoization(int n, Map<Integer, Integer> map) {
        if (n == 1)
            return 0;

        if (map.containsKey(n))
            return map.get(n);

        if (n == Integer.MAX_VALUE) {
            int result = 2 + integerReplaceMemoization(n - 1, map);
            map.put(n, result);

            return result;
        }

        if (n % 2 == 0) {
            int result = 1 + integerReplaceMemoization(n / 2, map);
            map.put(n, result);
            return result;
        }

        int result = 1 + Math.min(integerReplaceMemoization(n - 1, map), integerReplaceMemoization(n + 1, map));
        map.put(n, result);
        return result;
    }

    // Leetcode problem: 714

    // Leetcode problem: 935
    /*
     *          1    2   3
     *          4    5   6
     *          7    8   9
     *               0
     * */
    public int knightDialer(int n) {
        // paths[i] means the position from which i is reachable => 0 is reachable from {4, 6}, 1 is reachable from {6, 8} ...
        int[][] paths = {{4, 6}, {6, 8}, {7, 9}, {4, 8}, {0, 3, 9}, {}, {0, 1, 7}, {2, 6}, {1, 3}, {2, 4}};

        int MOD = 1000000007;

        /*int[][] dp = new int[n][10];
        // dp[i][j] means the number of path after i step ending at j
        // After 1 step, ending at any position the path will be 1. Just place the night to that position
        Arrays.fill(dp[0], 1);
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < 10; j++) {

                // From every position from which j is reachable, update the value of dp[i][j]
                for (int pos : paths[j]) {
                    dp[i][j] = (dp[i][j] + dp[i - 1][pos]) % MOD;
                }
            }
        }

        // Sum up the values stored for every position in last row
        int result = 0;
        for (int val : dp[n - 1]) {
            result = (result + val) % MOD;
        }

        return result;*/

        // Here memory can be optimized by storing last 2 rows only

        int[][] dp = new int[2][10];
        // dp[i][j] means the number of path after i step ending at j
        // After 1 step, ending at any position the path will be 1. Just place the night to that position
        Arrays.fill(dp[0], 1);
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < 10; j++) {

                // From every position from which j is reachable, update the value of dp[i][j]
                for (int pos : paths[j]) {
                    dp[1][j] = (dp[1][j] + dp[0][pos]) % MOD;
                }
            }

            System.arraycopy(dp[1], 0, dp[0], 0, 10);
            Arrays.fill(dp[1], 0);
        }

        int result = 0;
        for (int val : dp[0]) {
            result = (result + val) % MOD;
        }

        return result;

    }

    // Leetcode problem: 1696 Have to solve
    public int maxResult(int[] nums, int k) {
        int[] dp = new int[nums.length];
        dp[0] = nums[0];

        for (int i = 1; i < nums.length; i++) {
            int maxFromPrev = dp[i - 1];
            for (int j = i - 2; j >= i - k && j >= 0; j--) {
                maxFromPrev = Math.max(maxFromPrev, dp[j]);
            }
            dp[i] = maxFromPrev + nums[i];
        }

        return dp[nums.length - 1];
    }

}
