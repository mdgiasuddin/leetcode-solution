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

    // Leetcode problem: 226
    public TreeNode invertTree(TreeNode root) {
        if (root == null)
            return null;

        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        invertTree(root.left);
        invertTree(root.right);
        return root;
    }

    // Leetcode problem: 1448
    public int goodNodes(TreeNode root) {
        return goodNodes(root, root.val);
    }

    public int goodNodes(TreeNode node, int currentMax) {
        if (node == null)
            return 0;

        int res = node.val >= currentMax ? 1 : 0;
        currentMax = Math.max(currentMax, node.val);

        res += goodNodes(node.left, currentMax);
        res += goodNodes(node.right, currentMax);

        return res;
    }

}
