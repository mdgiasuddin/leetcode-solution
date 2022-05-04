package tree;

import indefinite.TreeNode;

import java.util.*;

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

    // Leetcode problem: 100
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null)
            return true;

        if (p == null || q == null || p.val != q.val)
            return false;

        return isSameTree(p.left, q.left) &&
                isSameTree(p.right, q.right);
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
    public int kthSmallest(TreeNode root, int k) {
        int[] arr = {0, 0};
        kthSmallestAux(root, k, arr);
        return arr[1];
    }

    public void kthSmallestAux(TreeNode root, int k, int[] arr) {
        if (root == null)
            return;

        kthSmallestAux(root.left, k, arr);
        arr[0]++;
        if (arr[0] == k)
            arr[1] = root.val;
        kthSmallestAux(root.right, k, arr);
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

    // Leetcode problem: 543
    /*
     * The solution is tricky.
     * */
    public int diameterOfBinaryTree(TreeNode root) {
        int[] result = new int[1];

        diameterOfBinaryTree(root, result);

        return result[0];
    }

    public int diameterOfBinaryTree(TreeNode node, int[] result) {
        if (node == null) {
            return -1;
        }

        int left = diameterOfBinaryTree(node.left, result);
        int right = diameterOfBinaryTree(node.right, result);

        // Update the diameter. Here 2 for left and right outgoing edge.
        // If all the children are null, it will be minimized by -1.
        result[0] = Math.max(result[0], 2 + left + right);

        // Return the height of the node to its parent.
        return 1 + Math.max(left, right);
    }

    // Leetcode problem: 105
    /*
     * Build tree from preorder and inorder traversal
     * */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        Map<Integer, Integer> map = new HashMap<>();
        int[] indices = new int[1];
        for (int i = 0; i < inorder.length; i++)
            map.put(inorder[i], i);

        return buildTree(preorder, inorder, indices, 0, preorder.length - 1, map);
    }

    private TreeNode buildTree(int[] preorder, int[] inorder, int[] indices, int left, int right, Map<Integer, Integer> map) {
        if (left > right)
            return null;

        TreeNode root = new TreeNode(preorder[indices[0]]);
        int inorderPos = map.get(preorder[indices[0]]);

        indices[0]++;

        // For post order traversal, first build up right child
        root.left = buildTree(preorder, inorder, indices, left, inorderPos - 1, map);
        root.right = buildTree(preorder, inorder, indices, inorderPos + 1, right, map);

        return root;
    }

    // Leetcode problem: 513
    /*
     * This problem can be solved by level order traversal.
     * */
    public int findBottomLeftValue(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();

        int result = root.val;
        queue.add(root);

        while (!queue.isEmpty()) {
            result = queue.peek().val;

            int qSize = queue.size();

            while (qSize-- > 0) {
                TreeNode node = queue.poll();

                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }

        return result;
    }

}
