package tree;

import java.util.LinkedList;
import java.util.Queue;

public class TreeSolution3 {
    public static void main(String[] args) {

    }

    // Leetcode problem: 1315
    public int sumEvenGrandparent(TreeNode root) {
        if (root == null)
            return 0;

        int res = 0;
        if (root.val % 2 == 0) {
            res += getChildNodeSum(root.left) + getChildNodeSum(root.right);
        }

        res += sumEvenGrandparent(root.left) + sumEvenGrandparent(root.right);

        return res;
    }

    private int getChildNodeSum(TreeNode node) {
        if (node == null)
            return 0;

        return (node.left == null ? 0 : node.left.val) + (node.right == null ? 0 : node.right.val);
    }

    // Leetcode problem: 1302
    /*
     * Solve the problem by level order traversal.
     * Go to each level. Reset the current sum & calculate sum for each level.
     * The last level sum will be the answer.
     * */
    public int deepestLeavesSum(TreeNode root) {
        int sum = 0;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int qSize = queue.size();
            sum = 0;

            while (qSize-- > 0) {
                TreeNode node = queue.poll();
                sum += node.val;

                if (node.left != null)
                    queue.add(node.left);
                if (node.right != null)
                    queue.add(node.right);
            }
        }

        return sum;
    }

    // Leetcode problem: 1022
    public int sumRootToLeaf(TreeNode root) {
        return sumRootToLeaf(root, 0);

    }

    public int sumRootToLeaf(TreeNode node, int current) {
        if (node == null)
            return 0;

        current = current * 2 + node.val;
        if (node.left == null && node.right == null)
            return current;

        return sumRootToLeaf(node.left, current) + sumRootToLeaf(node.right, current);
    }
}
