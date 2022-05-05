package tree;

public class TreeSolution2 {

    public static void main(String[] args) {

    }

    // Leetcode problem: 96
    /*
     * Dynamic Programming.
     * DP(4) => root(1) -> left = 0[], right = 3[2,3,4], root(2) -> left = 1[1], right = 2[3,4]
     * root(3) -> left = 2[1,2], right 1[4], root(4) -> left = 3[1,2,3] right = 0[]
     * */
    public int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                dp[i] += dp[j] * dp[(i - 1) - j];
            }
        }

        return dp[n];
    }
}
