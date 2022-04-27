package dfs;

import pair.Pair;

import java.util.HashSet;
import java.util.Set;

public class DFSSolution2 {

    public static void main(String[] args) {

    }

    // Leetcode problem: 130
    /*
     * DFS through the boundary and mark all 'O' to any special character
     * Then traverse the whole array and reset the special character to 'O', other position to 'X'
     * */
    public void solve(char[][] board) {

        int m = board.length, n = board[0].length;
        if (m < 2 || n < 2)
            return;

        for (int col = 0; col < n; col++) {
            dfsBoundary(board, 0, col);
            dfsBoundary(board, m - 1, col);
        }

        for (int row = 0; row < m; row++) {
            dfsBoundary(board, row, 0);
            dfsBoundary(board, row, n - 1);
        }

        for (int row = 0; row < m; row++) {
            for (int col = 0; col < n; col++) {
                if (board[row][col] == '#') {
                    board[row][col] = 'O';
                } else {
                    board[row][col] = 'X';
                }
            }
        }
    }

    public void dfsBoundary(char[][] board, int row, int col) {
        if (row < 0 || col < 0 || row >= board.length || col >= board[0].length || board[row][col] != 'O')
            return;

        board[row][col] = '#';
        dfsBoundary(board, row - 1, col); // Up
        dfsBoundary(board, row + 1, col); // Down
        dfsBoundary(board, row, col - 1); // Left
        dfsBoundary(board, row, col + 1); // Up

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
