package recursion;

public class BacktrackingSolution3 {

    public static void main(String[] args) {
        BacktrackingSolution3 backtrackingSolution3 = new BacktrackingSolution3();
    }

    // Leetcode problem: 980
    public int uniquePathsIII(int[][] grid) {
        int M = grid.length, N = grid[0].length;

        int sx, sy;
        int zeroCells = 0;

        sx = sy = -1;

        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                if (grid[r][c] == 0) {
                    zeroCells++;
                } else if (grid[r][c] == 1) {
                    sx = r;
                    sy = c;
                }
            }
        }

        boolean[][] visited = new boolean[M][N];

        // zeroCells + 1 (+1 for starting cell).
        return dfsPath(sx, sy, grid, 0, zeroCells + 1, visited);
    }

    private int dfsPath(int r, int c, int[][] grid, int coveredCells, int zeroCells, boolean[][] visited) {
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length || visited[r][c] || grid[r][c] == -1 || (grid[r][c] == 2 && coveredCells != zeroCells))
            return 0;

        if (grid[r][c] == 2) {
            return 1;
        }

        visited[r][c] = true;
        int res = 0;
        res += dfsPath(r + 1, c, grid, coveredCells + 1, zeroCells, visited);
        res += dfsPath(r - 1, c, grid, coveredCells + 1, zeroCells, visited);
        res += dfsPath(r, c + 1, grid, coveredCells + 1, zeroCells, visited);
        res += dfsPath(r, c - 1, grid, coveredCells + 1, zeroCells, visited);

        // Backtrack.
        visited[r][c] = false;

        return res;
    }
}
