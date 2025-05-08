package tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class TreeSolution4 {

    // Leetcode problem: 1372

    /**
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

    /**
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

    /**
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

    public List<List<Integer>> verticalTraversal(TreeNode root) {
        Map<Integer, PriorityQueue<TraversalNode>> map = new HashMap<>();
        int[] width = {0, 0};

        verticalTraversal(root, 0, 0, map, width);

        List<List<Integer>> result = new ArrayList<>();
        for (int i = width[0]; i <= width[1]; i++) {
            List<Integer> list = new ArrayList<>();
            PriorityQueue<TraversalNode> nodes = map.get(i);

            while (!nodes.isEmpty()) {
                list.add(nodes.poll().value);
            }

            result.add(list);
        }

        return result;
    }

    private void verticalTraversal(TreeNode node, int vLevel, int hLevel, Map<Integer, PriorityQueue<TraversalNode>> map, int[] width) {
        if (node == null) {
            return;
        }

        width[0] = Math.min(width[0], vLevel);
        width[1] = Math.max(width[1], vLevel);

        map.putIfAbsent(vLevel, new PriorityQueue<>());
        map.get(vLevel).add(new TraversalNode(node.val, hLevel));

        verticalTraversal(node.left, vLevel - 1, hLevel + 1, map, width);
        verticalTraversal(node.right, vLevel + 1, hLevel + 1, map, width);
    }

    // Leetcode problem: 2471

    /**
     * Minimum Number of Operations to Sort a Binary Tree by Level.
     * Explanation: https://www.youtube.com/watch?v=6mONZ_54rZg
     * */
    public int minimumOperations(TreeNode root) {
        int swap = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int qSize = queue.size();
            List<Integer> list = new ArrayList<>(qSize);
            int i = 0;
            while (qSize-- > 0) {
                TreeNode node = queue.poll();
                list.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }

            swap += countSwap(list);
        }

        return swap;
    }

    private int countSwap(List<Integer> list) {
        int n = list.size();
        Map<Integer, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            indexMap.put(list.get(i), i);
        }

        int swap = 0;
        List<Integer> sorted = list.stream().sorted().toList();
        for (int i = 0; i < n - 1; i++) {
            if (!list.get(i).equals(sorted.get(i))) {
                swap += 1;
                int j = indexMap.get(sorted.get(i));
                list.set(j, list.get(i));
                indexMap.put(list.get(j), j);
            }
        }

        return swap;
    }

    // Leetcode problem: 1026

    /**
     * Maximum Difference Between Node and Ancestor.
     * Explanation: https://www.youtube.com/watch?v=I7AbFkrBhdE&list=PLy38cn8b_xMfO7CGsUDIsYGps37yKaQ9X&index=32
     * Keep track & update maximum & minimum value along the path.
     * When a leaf node is reached return the difference.
     * */
    public int maxAncestorDiff(TreeNode root) {
        return maxAncestorDiff(root, Integer.MAX_VALUE, 0);
    }

    public int maxAncestorDiff(TreeNode node, int min, int max) {
        if (node == null) {
            return max - min;
        }

        min = Math.min(min, node.val);
        max = Math.max(max, node.val);

        return Math.max(maxAncestorDiff(node.left, min, max), maxAncestorDiff(node.right, min, max));
    }

    // Leetcode problem: 2509

    /**
     * Cycle Length Queries in a Tree.
     * Explanation: https://www.youtube.com/watch?v=VPRzdxjtuDc&list=PLy38cn8b_xMcndJK8oK6Wmk_JPvnIrGFT&index=7
     * Determine the path length from the nodes to the lowest common ancestor.
     * */
    public int[] cycleLengthQueries(int n, int[][] queries) {
        int[] result = new int[queries.length];

        int i = 0;
        for (int[] query : queries) {
            int a = query[0];
            int b = query[1];

            int res = 1;
            while (a != b) {
                if (a > b) {
                    // Move a to its parent.
                    a >>= 1;
                } else {
                    // Move b to its parent.
                    b >>= 1;
                }

                res += 1;
            }

            result[i++] = res;
        }

        return result;
    }

    // Leetcode problem: 652

    /**
     * Find Duplicate Subtrees.
     * */
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        List<TreeNode> result = new ArrayList<>();
        inorderDuplicateSubtrees(root, new HashMap<>(), result);
        return result;
    }

    private String inorderDuplicateSubtrees(TreeNode node, Map<String, Integer> subTreeCount, List<TreeNode> result) {

        if (node == null) {
            return "";
        }

        String str = "(";
        str += inorderDuplicateSubtrees(node.left, subTreeCount, result);
        str += node.val;
        str += inorderDuplicateSubtrees(node.right, subTreeCount, result);
        str += ")";

        int count = subTreeCount.getOrDefault(str, 0);
        if (count == 1) {
            result.add(node);
        }
        subTreeCount.put(str, count + 1);

        return str;
    }
}

class TraversalNode implements Comparable<TraversalNode> {
    int value;
    int hLevel;

    public TraversalNode(int value, int hLevel) {
        this.value = value;
        this.hLevel = hLevel;
    }

    @Override
    public int compareTo(TraversalNode o) {
        if (hLevel == o.hLevel) {
            return this.value - o.value;
        }

        return hLevel - o.hLevel;
    }
}
