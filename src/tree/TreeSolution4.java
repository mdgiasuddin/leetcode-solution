package tree;

public class TreeSolution4 {

    // Leetcode problem: 1372
    /*
     * Amazon interview question.
     * */
    public int longestZigZag(TreeNode root) {
        // For root node, no direction is set. Put any character exception 'L' or 'R'.
        return longestZigZag(root, 'N', 0);
    }

    private int longestZigZag(TreeNode node, char direction, int len) {
        if (node == null) {
            return len - 1;
        }

        // If direction is changed then continue with previous len. Otherwise, reset.
        int left = direction == 'L' ? 1 : len + 1;
        int right = direction == 'R' ? 1 : len + 1;

        int lLen = longestZigZag(node.left, 'L', left);
        int rLen = longestZigZag(node.right, 'R', right);

        return Math.max(lLen, rLen);
    }
}
