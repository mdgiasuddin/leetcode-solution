package tree;

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
}
