package dfs;

import pair.Pair;

import java.util.HashSet;
import java.util.Set;

public class DFSSolution2 {

    public static void main(String[] args) {

    }

    // Leetcode problem: 695
    public int maxAreaOfIsland(int[][] grid) {
        int maxArea = 0;

        Set<Pair> visited = new HashSet<>();
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == 1 && !visited.contains(new Pair(r, c))) {
                    maxArea = Math.max(maxArea, dfsArea(r, c, grid, visited));
                }
            }
        }

        return maxArea;
    }

    private int dfsArea(int r, int c, int[][] grid, Set<Pair> visited) {
        if (r < 0 || c < 0 || r >= grid.length || c >= grid[0].length || grid[r][c] == 0
                || visited.contains(new Pair(r, c))) {

            return 0;
        }

        visited.add(new Pair(r, c));
        return 1 + dfsArea(r - 1, c, grid, visited)
                + dfsArea(r + 1, c, grid, visited)
                + dfsArea(r, c - 1, grid, visited)
                + dfsArea(r, c + 1, grid, visited);
    }
}
