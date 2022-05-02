package tree;

import indefinite.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TreeSolution {

    public static void main(String[] args) {

    }

    // Leetcode problem: 108
    public TreeNode sortedArrayToBST(int[] nums) {

        return sortedArrayToBST(nums, 0, nums.length - 1);
    }

    public TreeNode sortedArrayToBST(int[] nums, int left, int right) {
        if (left > right)
            return null;

        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = sortedArrayToBST(nums, left, mid - 1);
        root.right = sortedArrayToBST(nums, mid + 1, right);

        return root;
    }

    // Leetcode problem: 98
    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public boolean isValidBST(TreeNode root, long leftBoundary, long rightBoundary) {
        if (root == null)
            return true;

        if (root.val <= leftBoundary || root.val >= rightBoundary)
            return false;

        return isValidBST(root.left, leftBoundary, root.val)
                && isValidBST(root.right, root.val, rightBoundary);
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

    // Leetcode problem: 129
    public int sumNumbers(TreeNode root) {
        return sumNumbers(root, 0);
    }

    public int sumNumbers(TreeNode node, int currentSum) {
        if (node == null) {
            return 0;
        }

        currentSum = currentSum * 10 + node.val;

        if (node.left == null && node.right == null) {
            return currentSum;
        }

        return sumNumbers(node.left, currentSum) + sumNumbers(node.right, currentSum);
    }

    // Leetcode problem: 112
    /*
     * This problem is similar to sum of numbers (Leetcode problem: 129)
     * */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        return hasPathSum(root, 0, targetSum);
    }

    public boolean hasPathSum(TreeNode node, int currentSum, int targetSum) {
        if (node == null) {
            return false;
        }

        currentSum += node.val;

        if (node.left == null && node.right == null) {
            return currentSum == targetSum;
        }

        return hasPathSum(node.left, currentSum, targetSum) || hasPathSum(node.right, currentSum, targetSum);
    }

    // Leetcode problem: 199
    /*
     * This problem can be solved by level order traversal
     * */
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();

        Queue<TreeNode> queue = new LinkedList<>();
        if (root != null) {
            queue.add(root);
        }

        while (!queue.isEmpty()) {
            int qSize = queue.size();

            // Add the rightmost node to result
            result.add(queue.peek().val);

            // Add all the children to the queue
            while (qSize-- > 0) {
                TreeNode node = queue.poll();
                if (node.right != null) {
                    queue.add(node.right);
                }
                if (node.left != null) {
                    queue.add(node.left);
                }

            }
        }

        return result;
    }

    // Leetcode problem: 199
    /*
     * This is recursive version
     * */
    public List<Integer> rightSideViewRecursive(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        rightSideViewRecursive(root, result, 0);
        return result;

    }

    public void rightSideViewRecursive(TreeNode root, List<Integer> result, int level) {
        if (root == null)
            return;

        if (level == result.size()) {
            // For the first seen, add it.
            result.add(root.val);
        }
        rightSideViewRecursive(root.right, result, level + 1);
        rightSideViewRecursive(root.left, result, level + 1);
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

    // Leetcode problem: 230
    /*
     * Solve the problem by inorder traversal.
     * Keep track how many node have been visited already.
     * */
    int kthMinimum = Integer.MAX_VALUE, visitedNode = 0;

    public int kthSmallest(TreeNode root, int k) {
        kthSmallestAux(root, k);
        return kthMinimum;
    }

    public void kthSmallestAux(TreeNode root, int k) {
        if (root == null)
            return;

        kthSmallestAux(root.left, k);
        visitedNode++;
        if (visitedNode == k)
            kthMinimum = root.val;
        kthSmallestAux(root.right, k);
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
