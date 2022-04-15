package dfs;

import indefinite.TreeNode;
import pair.Pair;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class DFSSolution {

    public static void main(String[] args) {

    }

    // Leetcode problem: 337
    /*
     * If a node value is taken then skip the child value
     * */
    public int rob(TreeNode root) {

        Pair pair = bfsRobber(root);

        return Math.max(pair.first, pair.second);
    }

    public Pair bfsRobber(TreeNode node) {
        if (node == null) {
            return new Pair(0, 0);
        }

        Pair leftPair = bfsRobber(node.left);
        Pair rightPair = bfsRobber(node.right);

        int withNode = node.val + leftPair.second + rightPair.second;
        int withoutNode = Math.max(leftPair.first, leftPair.second) + Math.max(rightPair.first, rightPair.second);

        return new Pair(withNode, withoutNode);
    }

    // Leetcode problem 343
    public int integerBreak(int n) {
        int[] dp = new int[n + 1];

        for (int i = 1; i <= n; i++) {

            // If i is any middle number, we can use i without breaking it
            if (i < n)
                dp[i] = i;

            // Try to get maximum by breaking
            // Since there is j & i - j, we can loop only left side
            for (int j = 1; j <= i / 2; j++) {
                dp[i] = Math.max(dp[i], dp[j] * dp[i - j]);
            }
        }

        return dp[n];
    }

    // Leetcode problem: 1306
    /*
     * Jump Game III
     * */
    public boolean canReach(int[] arr, int start) {
        boolean[] visited = new boolean[arr.length];
        return canReach(arr, visited, start);
    }

    public boolean canReach(int[] arr, boolean[] visited, int current) {
        if (current >= 0 && current < arr.length && arr[current] == 0) {
            return true;
        }
        if (current < 0 || current >= arr.length || visited[current]) {
            return false;
        }

        visited[current] = true;
        if (canReach(arr, visited, current + arr[current])
                || canReach(arr, visited, current - arr[current]))
            return true;
        visited[current] = false;
        return false;
    }

    // Leetcode problem 934
    /*
     * First run dfs to find first island
     * Then run bfs to find the shortest path to second island from first island
     * */
    public int shortestBridge(int[][] grid) {
        Set<Pair> visited = new HashSet<>();
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] == 1) {
                    dfsGrid(grid, directions, visited, row, col);

                    Queue<Pair> queue = new LinkedList<>(visited);
                    return bfsGrid(grid, directions, visited, queue);
                }
            }
        }
        return 0;
    }

    private boolean isInvalid(int[][] grid, int row, int col) {
        return row < 0 || row >= grid.length || col < 0 || col >= grid[0].length;
    }

    private void dfsGrid(int[][] grid, int[][] directions, Set<Pair> visited, int row, int col) {
        if (isInvalid(grid, row, col) || grid[row][col] == 0 || visited.contains(new Pair(row, col)))
            return;

        visited.add(new Pair(row, col));

        for (int[] direction : directions) {
            dfsGrid(grid, directions, visited, row + direction[0], col + direction[1]);
        }
    }

    private int bfsGrid(int[][] grid, int[][] directions, Set<Pair> visited, Queue<Pair> queue) {

        int result = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            while (size-- > 0) {
                Pair pair = queue.poll();
                for (int[] direction : directions) {
                    int rowNew = pair.first + direction[0];
                    int colNew = pair.second + direction[1];
                    if (isInvalid(grid, rowNew, colNew) || visited.contains(new Pair(rowNew, colNew))) {
                        continue;

                    }

                    // If second island is reached, return the path length
                    if (grid[rowNew][colNew] == 1) {
                        return result;
                    }

                    visited.add(new Pair(pair.first + direction[0], pair.second + direction[1]));
                    queue.add(new Pair(pair.first + direction[0], pair.second + direction[1]));
                }
            }

            result++;

        }

        return result;
    }
}
