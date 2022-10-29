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
}
