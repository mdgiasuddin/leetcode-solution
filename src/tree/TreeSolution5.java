package tree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TreeSolution5 {

    // Leetcode problem: 427
    /*
     * Construct Quad Tree.
     * Explanation: https://www.youtube.com/watch?v=UQ-1sBMV0v4&t=44s
     * */
    public QuadNode construct(int[][] grid) {
        return construct(grid, grid.length, 0, 0);
    }

    public QuadNode construct(int[][] grid, int n, int r, int c) {
        boolean isSame = true;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[r + i][c + j] != grid[r][c]) {
                    isSame = false;
                    break;
                }
            }
        }

        if (isSame) {
            return new QuadNode(grid[r][c] == 1, true);
        }

        n /= 2;
        QuadNode topLeft = construct(grid, n, r, c);
        QuadNode topRight = construct(grid, n, r, c + n);
        QuadNode bottomLeft = construct(grid, n, r + n, c);
        QuadNode bottomRight = construct(grid, n, r + n, c + n);

        return new QuadNode(false, false, topLeft, topRight, bottomLeft, bottomRight);
    }

    // Leetcode problem: 1339
    /*
     * Maximum Product of Splitted Binary Tree.
     * */
    public int maxProduct(TreeNode root) {
        long[] ans = {0};

        // Calculate the total sum. ans will not be modified.
        long sum = dfsTree(root, 0, ans);
        dfsTree(root, sum, ans);
        return (int) (ans[0] % 1000000007);
    }

    private long dfsTree(TreeNode root, long sum, long[] ans) {
        if (root == null) {
            return 0;
        }

        long current = root.val + dfsTree(root.left, sum, ans) + dfsTree(root.right, sum, ans);

        // For the first call, sum is 0. So current * (sum - current) < 0.
        // So ans will not be modified.
        ans[0] = Math.max(ans[0], current * (sum - current));
        return current;
    }

    // Leetcode problem: 117
    /*
     * Populating Next Right Pointers II.
     * Explanation: https://www.youtube.com/watch?v=yl-fdkyQD8A
     * */
    public Node connect(Node root) {
        Node head = root;

        while (head != null) {
            Node dummy = new Node(0);
            Node tmp = dummy;

            while (head != null) {
                if (head.left != null) {
                    tmp.next = head.left;
                    tmp = tmp.next;
                }
                if (head.right != null) {
                    tmp.next = head.right;
                    tmp = tmp.next;
                }
                head = head.next;
            }
            head = dummy.next;
        }

        return root;
    }

    // Leetcode problem: 979
    /*
     * Distribute Coins in Binary Tree.
     * DFS.
     * Explanation: https://www.youtube.com/watch?v=yYcKQdGEdDY + (Description).
     * */
    public int distributeCoins(TreeNode root) {
        int[] res = {0};
        distributeCoins(root, res);
        return res[0];
    }

    private int distributeCoins(TreeNode node, int[] res) {
        if (node == null) {
            return 0;
        }

        int leftCoin = distributeCoins(node.left, res);
        int rightCoin = distributeCoins(node.right, res);

        res[0] += Math.abs(leftCoin) + Math.abs(rightCoin);
        return node.val + leftCoin + rightCoin - 1;
    }

    // Leetcode problem: 437
    /*
     * Path Sum III.
     * Explanation: https://www.youtube.com/watch?v=Vam9gldRapY
     * 2 Cases => Include the root, exclude the root.
     * => O(n * height)
     * */
    public int pathSum(TreeNode root, long targetSum) {
        if (root == null) {
            return 0;
        }

        return pathSum(root.left, targetSum) + pathSum(root.right, targetSum) // Exclude the root.
            + pathSumInc(root, targetSum); // Include the root.
    }

    private int pathSumInc(TreeNode root, long targetSum) {
        if (root == null) {
            return 0;
        }

        int res = root.val == targetSum ? 1 : 0; // If target sum is reached increase the res.
        res += pathSumInc(root.left, targetSum - root.val) + pathSumInc(root.right, targetSum - root.val);

        return res;
    }


    // Leetcode problem: 1110
    /*
     * Delete Nodes And Return Forest.
     * Explanation: https://www.youtube.com/watch?v=BmpXMtA0oF8
     * Post order traversal.
     * */
    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        Set<Integer> deleteSet = new HashSet<>();
        for (int e : to_delete) {
            deleteSet.add(e);
        }

        List<TreeNode> result = new ArrayList<>();
        if (!deleteSet.contains(root.val)) {
            result.add(root);
        }

        delNodes(root, deleteSet, result);
        return result;
    }

    private TreeNode delNodes(TreeNode root, Set<Integer> deleteSet, List<TreeNode> result) {
        if (root == null) {
            return null;
        }

        root.left = delNodes(root.left, deleteSet, result);
        root.right = delNodes(root.right, deleteSet, result);

        if (deleteSet.contains(root.val)) {
            if (root.left != null) {
                result.add(root.left);
            }
            if (root.right != null) {
                result.add(root.right);
            }

            return null;
        }

        return root;
    }
}

class QuadNode {
    public boolean val;
    public boolean isLeaf;
    public QuadNode topLeft;
    public QuadNode topRight;
    public QuadNode bottomLeft;
    public QuadNode bottomRight;


    public QuadNode() {
        this.val = false;
        this.isLeaf = false;
        this.topLeft = null;
        this.topRight = null;
        this.bottomLeft = null;
        this.bottomRight = null;
    }

    public QuadNode(boolean val, boolean isLeaf) {
        this.val = val;
        this.isLeaf = isLeaf;
        this.topLeft = null;
        this.topRight = null;
        this.bottomLeft = null;
        this.bottomRight = null;
    }

    public QuadNode(boolean val, boolean isLeaf, QuadNode topLeft, QuadNode topRight, QuadNode bottomLeft,
                    QuadNode bottomRight) {
        this.val = val;
        this.isLeaf = isLeaf;
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
    }
}
