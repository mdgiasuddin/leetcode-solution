package tree;

import java.util.*;

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

    // Leetcode problem: 938
    public int rangeSumBST(TreeNode root, int low, int high) {
        if (root == null)
            return 0;

        int sum = root.val >= low && root.val <= high ? root.val : 0;
        if (root.val > low)
            sum += rangeSumBST(root.left, low, high);

        if (root.val < high)
            sum += rangeSumBST(root.right, low, high);

        return sum;
    }

    // Leetcode problem: 783
    /*
     * Solve the problem by inorder traversal.
     * */
    public int minDiffInBST(TreeNode root) {
        TreeNode[] prev = {null};
        int[] res = {Integer.MAX_VALUE};

        minDiffInBST(root, prev, res);

        return res[0];
    }

    private void minDiffInBST(TreeNode node, TreeNode[] prev, int[] res) {
        if (node == null)
            return;

        minDiffInBST(node.left, prev, res);

        // If already a visited node then update result.
        if (prev[0] != null)
            res[0] = Math.min(res[0], node.val - prev[0].val);
        prev[0] = node;

        minDiffInBST(node.right, prev, res);
    }

    // Leetcode problem: 623
    public TreeNode addOneRow(TreeNode root, int val, int depth) {
        if (depth == 1) {
            TreeNode newRoot = new TreeNode(val);
            newRoot.left = root;
            return newRoot;
        }

        return addOneRow(root, val, depth, 2);
    }

    private TreeNode addOneRow(TreeNode node, int val, int depth, int parentOfLevel) {

        if (node == null)
            return null;

        if (parentOfLevel < depth) {

            // Go to the expected level.
            node.left = addOneRow(node.left, val, depth, parentOfLevel + 1);
            node.right = addOneRow(node.right, val, depth, parentOfLevel + 1);
        } else if (parentOfLevel == depth) {

            // Add a layer.
            TreeNode newLeft = new TreeNode(val), newRight = new TreeNode(val);

            newLeft.left = node.left;
            node.left = newLeft;

            newRight.right = node.right;
            node.right = newRight;
        }

        return node;
    }

    // Leetcode problem: 404
    public int sumOfLeftLeaves(TreeNode root) {
        return sumOfLeftLeaves(root, false);
    }

    public int sumOfLeftLeaves(TreeNode node, boolean isLeft) {
        if (node == null)
            return 0;

        if (isLeft && node.left == null && node.right == null)
            return node.val;

        return sumOfLeftLeaves(node.left, true) + sumOfLeftLeaves(node.right, false);
    }

    // Leetcode problem: 111
    public int minDepth(TreeNode root) {
        if (root == null)
            return 0;

        if (root.left == null && root.right == null)
            return 1;

        if (root.left == null)
            return 1 + minDepth(root.right);

        if (root.right == null)
            return 1 + minDepth(root.left);

        return 1 + Math.min(minDepth(root.left), minDepth(root.right));
    }

    // Leetcode problem: 113
    /*
     * Backtracking solution.
     * */
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();

        pathSum(root, targetSum, 0, result, current);
        return result;
    }

    public void pathSum(TreeNode root, int targetSum, int sum, List<List<Integer>> finalResult, List<Integer> current) {

        if (root == null)
            return;

        sum += root.val;
        current.add(root.val);

        if (root.left == null && root.right == null && sum == targetSum)
            finalResult.add(new ArrayList<>(current));

        if (root.left != null)
            pathSum(root.left, targetSum, sum, finalResult, current);
        if (root.right != null)
            pathSum(root.right, targetSum, sum, finalResult, current);

        // Backtrack.
        current.remove(current.size() - 1);
    }

    // Leetcode problem: 671
    public int findSecondMinimumValue(TreeNode root) {
        long[] res = {Long.MAX_VALUE};
        findSecondMinimumValue(root, root.val, res);

        return res[0] == Long.MAX_VALUE ? -1 : (int) res[0];
    }

    public void findSecondMinimumValue(TreeNode node, int minVal, long[] res) {
        if (node == null)
            return;

        if (minVal < node.val && node.val < res[0])
            res[0] = node.val;
        findSecondMinimumValue(node.left, minVal, res);
        findSecondMinimumValue(node.right, minVal, res);

    }

    // Leetcode problem: 662
    public int widthOfBinaryTree(TreeNode root) {
        int[] maxWidth = {0};
        Map<Integer, Integer> leftPos = new HashMap<>();

        widthOfBinaryTree(root, leftPos, maxWidth, 0, 0);

        return maxWidth[0];
    }

    private void widthOfBinaryTree(TreeNode node, Map<Integer, Integer> leftPos, int[] maxWidth, int level, int pos) {
        if (node == null)
            return;

        // Save the first left position for each level.
        leftPos.putIfAbsent(level, pos);

        // Compute the maximum width.
        maxWidth[0] = Math.max(maxWidth[0], pos - leftPos.get(level) + 1);

        // Next pos for left & right is very important.
        widthOfBinaryTree(node.left, leftPos, maxWidth, level + 1, 2 * pos + 1);
        widthOfBinaryTree(node.right, leftPos, maxWidth, level + 1, 2 * pos + 2);
    }

    // Leetcode problem: 1325
    /*
     * Postorder traversal.
     * Go to the deepest level & delete the leaves with value = target.
     * */
    public TreeNode removeLeafNodes(TreeNode root, int target) {
        if (root == null) {
            return null;
        }

        root.left = removeLeafNodes(root.left, target);
        root.right = removeLeafNodes(root.right, target);

        if (root.val == target && root.left == null && root.right == null) {
            return null;
        }

        return root;
    }

    // Leetcode problem: 99

    /*
     * Inorder traversal.
     * Find in which node the increasing order violates.
     * */
    public void recoverTree(TreeNode root) {
        TreeNode[] prev = {null};
        TreeNode[] first = {null};
        TreeNode[] second = {null};

        recoverTree(root, prev, first, second);
        int tmp = first[0].val;
        first[0].val = second[0].val;
        second[0].val = tmp;
    }

    public void recoverTree(TreeNode node, TreeNode[] prev, TreeNode[] first, TreeNode[] second) {
        if (node == null) {
            return;
        }

        recoverTree(node.left, prev, first, second);

        if (prev[0] != null && prev[0].val > node.val) {
            if (first[0] == null) {
                first[0] = prev[0];
            }
            second[0] = node;
        }
        prev[0] = node;

        recoverTree(node.right, prev, first, second);
    }


}
