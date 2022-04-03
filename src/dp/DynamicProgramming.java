package dp;

import java.util.*;

public class DynamicProgramming {

    public static void main(String[] args) {
        DynamicProgramming dynamicProgramming = new DynamicProgramming();

        int[] array = {1, 2, 5};
        System.out.println(dynamicProgramming.change(5, array));
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

        int fibN_2 = 1, fibN_1 = 2, fibN = 2;

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

    // Leetcode problem: 435 greedy
}
