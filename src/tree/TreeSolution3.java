package tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

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

    // Leetcode problem: 1028
    /*
     * Build up a stack and find out right parent
     * */
    public TreeNode recoverFromPreorder(String traversal) {
        int i = 0;
        Stack<TreeNode> stack = new Stack<>();
        while (i < traversal.length()) {
            int level = 0;

            while (traversal.charAt(i) == '-') {
                level++;
                i++;
            }

            int numStartIndex = i;
            while (i < traversal.length() && Character.isDigit(traversal.charAt(i))) {
                i++;
            }
            int num = Integer.parseInt(traversal.substring(numStartIndex, i));

            TreeNode node = new TreeNode(num);

            if (stack.isEmpty()) {
                stack.push(node);
                continue;
            }

            // Find out right parent.
            while (stack.size() > level) {
                stack.pop();
            }

            TreeNode top = stack.peek();
            // First try to insert at left child.
            if (top.left == null) {
                top.left = node;
            } else {
                top.right = node;
            }

            stack.push(node);
        }

        while (stack.size() > 1) {
            stack.pop();
        }

        return stack.pop();
    }

    // Leetcode problem: 958
    /*
     * Solve the problem by level order traversal.
     * */
    public boolean isCompleteTree(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();

        queue.add(root);
        int requiredNode = 1;
        while (!queue.isEmpty()) {
            int qSize = queue.size();

            // Check this level is full or not.
            boolean full = (qSize == requiredNode);

            boolean leftFull = true;
            while (qSize-- > 0) {
                TreeNode node = queue.poll();

                /*
                 * Already null child found in the left but there are child in current node or
                 * - left child is null but right child is not null, then it is not complete.
                 * */
                if ((!leftFull && (node.left != null || node.right != null))
                        || (node.left == null && node.right != null))
                    return false;

                // Update leftFull for right side.
                leftFull = node.left != null && node.right != null;

                if (node.left != null)
                    queue.add(node.left);
                if (node.right != null)
                    queue.add(node.right);
            }

            // Current level is not full & there are children in the next level.
            if (!full && !queue.isEmpty())
                return false;

            // Required node will be doubled for the next level.
            requiredNode *= 2;
        }

        return true;
    }
}
