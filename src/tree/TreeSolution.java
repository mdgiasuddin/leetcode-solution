package tree;

import indefinite.TreeNode;

public class TreeSolution {

    public static void main(String[] args) {

    }


    // Leetcode problem: 110
    public boolean isBalanced(TreeNode root) {
        return findBalance(root).balanced;
    }

    public TreeBalance findBalance(TreeNode node) {
        if (node == null) {
            return new TreeBalance(true, 0);
        }

        TreeBalance leftBalance = findBalance(node.left);
        TreeBalance rightBalance = findBalance(node.right);

        boolean balanced = leftBalance.balanced && rightBalance.balanced
                && Math.abs(leftBalance.height - rightBalance.height) <= 1;

        return new TreeBalance(balanced, 1 + Math.max(leftBalance.height, rightBalance.height));

    }
}
