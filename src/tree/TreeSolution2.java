package tree;

import java.util.*;

public class TreeSolution2 {

    public static void main(String[] args) {

    }

    // Leetcode problem: 96
    /*
     * Dynamic Programming.
     * DP(4) => root(1) -> left = 0[], right = 3[2,3,4], root(2) -> left = 1[1], right = 2[3,4]
     * root(3) -> left = 2[1,2], right 1[4], root(4) -> left = 3[1,2,3] right = 0[]
     * */
    public int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                dp[i] += dp[j] * dp[(i - 1) - j];
            }
        }

        return dp[n];
    }

    // Leetcode problem: 235
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (p.val < root.val && q.val < root.val) {
            return lowestCommonAncestor(root.left, p, q);
        }
        if (p.val > root.val && q.val > root.val) {
            return lowestCommonAncestor(root.right, p, q);
        }

        return root;
    }

    // Leetcode problem: 951
    public boolean flipEquiv(TreeNode root1, TreeNode root2) {
        if (root1 == null || root2 == null)
            return root1 == null && root2 == null;

        if (root1.val != root2.val)
            return false;

        boolean notFlip = flipEquiv(root1.left, root2.left) && flipEquiv(root1.right, root2.right);

        return notFlip || flipEquiv(root1.left, root2.right) && flipEquiv(root1.right, root2.left);
    }

    // Leetcode problem: 572
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (subRoot == null)
            return true;

        if (root == null)
            return false;

        if (isSameTree(root, subRoot))
            return true;

        return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
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

    // Leetcode problem: 103
    /*
     * The problem is same as level order traversal. Just reverse the row of even index.
     * */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();

        Queue<TreeNode> queue = new LinkedList<>();
        if (root != null) {
            queue.add(root);
        }

        boolean reverse = false;
        while (!queue.isEmpty()) {
            List<Integer> row = new ArrayList<>();
            int qSize = queue.size();

            while (qSize-- > 0) {
                TreeNode node = queue.poll();
                row.add(node.val);

                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }

            if (reverse) {
                Collections.reverse(row);
            }
            reverse = !reverse;

            result.add(row);
        }

        return result;
    }

    // Leetcode problem: 669
    public TreeNode trimBST(TreeNode root, int low, int high) {

        if (root == null)
            return null;

        // All the left size is out of range. Go to right side.
        if (root.val < low)
            return trimBST(root.right, low, high);

        // All the right size is out of range. Go to left side.
        if (root.val > high)
            return trimBST(root.left, low, high);

        root.left = trimBST(root.left, low, high);
        root.right = trimBST(root.right, low, high);

        return root;
    }

    // Leetcode problem: 538
    /*
     * Inorder traversal (right->mid->left).
     * */
    public TreeNode convertBST(TreeNode root) {

        // Use a global variable to store the current addition.
        int[] addition = new int[1];

        convertBST(root, addition);

        return root;
    }

    public void convertBST(TreeNode node, int[] addition) {
        if (node == null)
            return;

        convertBST(node.right, addition);

        node.val += addition[0];
        addition[0] = node.val;

        convertBST(node.left, addition);

    }

    // Leetcode problem: 114
    /*
     * Preorder traversal.
     * */
    public void flatten(TreeNode root) {
        TreeNode[] node = {new TreeNode(0)};
        flatten(root, node);
    }

    public void flatten(TreeNode root, TreeNode[] node) {
        if (root == null)
            return;

        // Save left and right for next recursion.
        TreeNode left = root.left;
        TreeNode right = root.right;

        // Process current node.
        node[0].right = root;
        node[0].left = null;
        node[0] = root;

        flatten(left, node);
        flatten(right, node);
    }

    // Leetcode problem: 124
    /*
     * This problem is similar to diameter of binary tree (Leetcode problem: 543)
     * */
    public int maxPathSum(TreeNode root) {
        int[] result = {root.val};

        maxPathSum(root, result);

        return result[0];
    }

    public int maxPathSum(TreeNode node, int[] result) {
        if (node == null)
            return 0;

        int left = Math.max(0, maxPathSum(node.left, result));
        int right = Math.max(0, maxPathSum(node.right, result));

        result[0] = Math.max(result[0], node.val + left + right);

        return node.val + Math.max(left, right);
    }

    // Leetcode problem: 101
    /*
     * Solve by level order traversal (BFS).
     * */
    public boolean isSymmetric(TreeNode root) {
        if (root == null)
            return true;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root.left);
        queue.add(root.right);

        while (!queue.isEmpty()) {
            TreeNode tempLeft = queue.poll();
            TreeNode tempRight = queue.poll();

            if (tempLeft == null && tempRight == null)
                continue;

            if (tempLeft == null || tempRight == null || tempLeft.val != tempRight.val)
                return false;

            // All children are null. So don't need to traverse next level.
            if (tempLeft.left == null && tempLeft.right == null
                    && tempRight.left == null && tempRight.right == null)
                continue;

            queue.add(tempLeft.left);
            queue.add(tempRight.right);

            queue.add(tempLeft.right);
            queue.add(tempRight.left);
        }

        return true;
    }

    // This is recursive solution of Leetcode problem: 101
    public boolean isSymmetricRec(TreeNode root) {
        return isSymmetric(root.left, root.right);
    }

    public boolean isSymmetric(TreeNode tLeft, TreeNode tRight) {
        if (tLeft == null && tRight == null)
            return true;

        if (tLeft == null || tRight == null || tLeft.val != tRight.val)
            return false;

        return isSymmetric(tLeft.left, tRight.right) && isSymmetric(tLeft.right, tRight.left);
    }

    public boolean findTarget(TreeNode root, int k) {
        Set<Integer> set = new HashSet<>();

        return findTarget(root, k, set);
    }

    // Leetcode problem: 653
    /*
     * Two sum in binary tree.
     * */
    public boolean findTarget(TreeNode node, int k, Set<Integer> set) {
        if (node == null)
            return false;

        if (set.contains(k - node.val))
            return true;

        set.add(node.val);

        return findTarget(node.left, k, set) || findTarget(node.right, k, set);
    }

    // Leetcode problem: 1609
    /*
     * Level order traversal. Check value of each level.
     * */
    public boolean isEvenOddTree(TreeNode root) {

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        boolean even = true;
        while (!queue.isEmpty()) {
            int queueSize = queue.size();

            int minMax = even ? Integer.MIN_VALUE : Integer.MAX_VALUE;

            while (queueSize-- > 0) {
                TreeNode node = queue.poll();

                if (even) {
                    if (node.val % 2 == 0 || node.val <= minMax)
                        return false;
                } else {
                    if (node.val % 2 == 1 || node.val >= minMax)
                        return false;
                }
                minMax = node.val;

                if (node.left != null)
                    queue.add(node.left);
                if (node.right != null)
                    queue.add(node.right);
            }

            even = !even;
        }

        return true;
    }

    // Leetcode problem: 872
    /*
     * Get all leaves of both trees and compare if they are equals.
     * */
    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        getLeafNodes(root1, list1);
        getLeafNodes(root2, list2);

        return list1.equals(list2);
    }

    public void getLeafNodes(TreeNode root, List<Integer> list) {
        if (root == null)
            return;
        if (root.left == null && root.right == null) {
            list.add(root.val);
            return;
        }

        getLeafNodes(root.left, list);
        getLeafNodes(root.right, list);
    }

    // Leetcode problem: 94
    /*
     * Inorder traversal iterative solution.
     * Go to left child until face null.
     * Then go to right child.
     * */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();

        TreeNode current = root;
        Stack<TreeNode> stack = new Stack<>();

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            current = stack.pop();
            result.add(current.val);
            current = current.right;
        }

        return result;
    }

    // Leetcode problem: 144
    /*
     * Preorder traversal (Iterative approach).
     * */
    public List<Integer> preorderTraversal(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        List<Integer> result = new ArrayList<>();

        if (root != null)
            stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.add(node.val);

            // First right then left so that we traverse left side first.
            if (node.right != null)
                stack.push(node.right);
            if (node.left != null)
                stack.push(node.left);
        }

        return result;
    }


    // Leetcode problem: 145
    /*
     * Postorder traversal (Iterative approach).
     * */
    public List<Integer> postorderTraversal(TreeNode root) {
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();

        if (root != null)
            stack1.push(root);

        while (!stack1.isEmpty()) {
            TreeNode node = stack1.pop();
            stack2.push(node);

            if (node.left != null)
                stack1.push(node.left);
            if (node.right != null)
                stack1.push(node.right);
        }

        List<Integer> result = new ArrayList<>();
        while (!stack2.isEmpty()) {
            result.add(stack2.pop().val);
        }

        return result;
    }

}
