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
}
