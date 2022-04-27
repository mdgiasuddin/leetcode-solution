package bfs;

import pair.Pair;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class BFSSolution {
    public static void main(String[] args) {

    }

    // Leetcode problem: 994
    public int orangesRotting(int[][] grid) {

        Queue<Pair> queue = new LinkedList<>();
        Set<Pair> visited = new HashSet<>();

        // Run BFS from rotten position
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == 2) {
                    queue.add(new Pair(r, c));
                    visited.add(new Pair(r, c));
                }
            }
        }

        int minTime = 0;
        while (!queue.isEmpty()) {
            int qSize = queue.size();

            while (qSize-- > 0) {
                Pair pair = queue.poll();

                rotOrange(pair.first - 1, pair.second, grid, queue, visited);
                rotOrange(pair.first + 1, pair.second, grid, queue, visited);
                rotOrange(pair.first, pair.second - 1, grid, queue, visited);
                rotOrange(pair.first, pair.second + 1, grid, queue, visited);
            }

            // Increase only if it goes to next level.
            if (!queue.isEmpty()) {
                minTime++;
            }
        }

        // Check if any good orange exists.
        for (int[] ints : grid) {
            for (int c = 0; c < grid[0].length; c++) {
                if (ints[c] == 1) {
                    return -1;
                }
            }
        }

        return minTime;
    }

    private void rotOrange(int r, int c, int[][] grid, Queue<Pair> queue, Set<Pair> visited) {
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length || grid[r][c] != 1
                || visited.contains(new Pair(r, c))) {

            return;
        }

        grid[r][c] = 2;
        queue.add(new Pair(r, c));
        visited.add(new Pair(r, c));
    }
}
