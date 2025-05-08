package bfs;

import java.util.LinkedList;
import java.util.Queue;

public class BFSSolution4 {

    // Leetcode problem: 1765

    /**
     * Map of Highest Peak.
     * Multi-sources BFS.
     * */
    public int[][] highestPeak(int[][] isWater) {
        int m = isWater.length;
        int n = isWater[0].length;
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[m][n];
        int[][] peaks = new int[m][n];
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (isWater[r][c] == 1) {
                    visited[r][c] = true;
                    queue.add(new int[]{r, c});
                }
            }
        }

        int height = 1;
        while (!queue.isEmpty()) {
            int qSize = queue.size();
            while (qSize-- > 0) {
                int[] rc = queue.poll();
                for (int[] dir : dirs) {
                    int r = rc[0] + dir[0];
                    int c = rc[1] + dir[1];
                    if (r >= 0 && r < m && c >= 0 && c < n && !visited[r][c]) {
                        peaks[r][c] = height;
                        queue.add(new int[]{r, c});
                        visited[r][c] = true;
                    }
                }
            }

            height += 1;
        }

        return peaks;
    }
}
