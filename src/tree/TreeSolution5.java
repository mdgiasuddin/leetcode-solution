package tree;

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

    public QuadNode(boolean val, boolean isLeaf, QuadNode topLeft, QuadNode topRight, QuadNode bottomLeft, QuadNode bottomRight) {
        this.val = val;
        this.isLeaf = isLeaf;
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
    }
}
