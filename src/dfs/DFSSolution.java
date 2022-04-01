package dfs;

import indefinite.TreeNode;
import pair.Pair;

public class DFSSolution {

    public static void main(String[] args) {

    }

    // Leetcode problem: 337
    /*
     * If a node value is taken then skip the child value
     * */
    public int rob(TreeNode root) {

        Pair pair = bfsRobber(root);

        return Math.max(pair.first, pair.second);
    }

    public Pair bfsRobber(TreeNode node) {
        if (node == null) {
            return new Pair(0, 0);
        }

        Pair leftPair = bfsRobber(node.left);
        Pair rightPair = bfsRobber(node.right);

        int withNode = node.val + leftPair.second + rightPair.second;
        int withoutNode = Math.max(leftPair.first, leftPair.second) + Math.max(rightPair.first, rightPair.second);

        return new Pair(withNode, withoutNode);
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
}
