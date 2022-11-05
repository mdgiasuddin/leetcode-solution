package tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // Leetcode problem: 508
    /*
     * Most Frequent Subtree Sum.
     * Calculate sum for each subtree by postorder traversal & store the count of the sum.
     * Then find the sum which has the maximum count.
     * */
    public int[] findFrequentTreeSum(TreeNode root) {

        Map<Integer, Integer> map = new HashMap<>();
        findFrequentTreeSum(root, map);

        int maxCount = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            maxCount = Math.max(maxCount, entry.getValue());
        }

        List<Integer> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() == maxCount) {
                result.add(entry.getKey());
            }
        }

        int[] res = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            res[i] = result.get(i);
        }

        return res;
    }

    public int findFrequentTreeSum(TreeNode node, Map<Integer, Integer> map) {
        if (node == null) {
            return 0;
        }

        int sum = node.val + findFrequentTreeSum(node.left, map) + findFrequentTreeSum(node.right, map);
        int c = map.getOrDefault(sum, 0);
        map.put(sum, c + 1);

        return sum;
    }

    // Leetcode problem: 236
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root.val == p.val || root.val == q.val) {
            return root;
        }

        if (root.left == null && root.right == null) {
            return null;
        }

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if (left != null && right != null) {
            return root;
        }

        return left == null ? right : left;
    }

    // Leetcode problem: 993
    public boolean isCousins(TreeNode root, int x, int y) {
        if (root.val == x || root.val == y) {
            return false;
        }

        int[] xParent = {Integer.MIN_VALUE};
        int[] yParent = {Integer.MIN_VALUE};

        int xHeight = findHeightAndParent(root, x, 0, xParent);
        int yHeight = findHeightAndParent(root, y, 0, yParent);
        return xHeight == yHeight && xParent[0] != yParent[0];
    }

    public int findHeightAndParent(TreeNode node, int val, int height, int[] parent) {
        if (node == null) {
            return -1;
        }
        if (node.val == val) {
            return height;
        }
        parent[0] = node.val;
        int lHeight = findHeightAndParent(node.left, val, height + 1, parent);
        if (lHeight != -1) {
            return lHeight;
        }

        // Must restore. Because it got update in previous recursive call.
        parent[0] = node.val;
        return findHeightAndParent(node.right, val, height + 1, parent);

    }

    // Leetcode problem: 1008
    public TreeNode bstFromPreorder(int[] preorder) {
        return bstFromPreorder(preorder, 0, preorder.length - 1);
    }

    private TreeNode bstFromPreorder(int[] preorder, int left, int right) {
        if (left > right) {
            return null;
        }

        TreeNode root = new TreeNode(preorder[left]);
        int mid = findLastLeftChild(preorder[left], preorder, left + 1, right);

        root.left = bstFromPreorder(preorder, left + 1, mid);
        root.right = bstFromPreorder(preorder, mid + 1, right);

        return root;
    }

    private int findLastLeftChild(int val, int[] preorder, int left, int right) {
        int ans = left - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (preorder[mid] < val) {
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return ans;
    }

    // Leetcode problem: 222
    /*
     * Code source: Tech Dose
     * */
    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftLevel = 1;
        TreeNode leftNode = root.left;
        while (leftNode != null) {
            leftLevel += 1;
            leftNode = leftNode.left;
        }

        int rightLevel = 1;
        TreeNode rightNode = root.right;
        while (rightNode != null) {
            rightNode = rightNode.right;
            rightLevel += 1;
        }

        if (leftLevel == rightLevel) {
            return (int) Math.pow(2, leftLevel) - 1;
        }

        return 1 + countNodes(root.left) + countNodes(root.right);
    }
}
