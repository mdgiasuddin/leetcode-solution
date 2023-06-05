package dfs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DFSSolution5 {

    // Leetcode problem: 2101
    /*
     * Detonate the Maximum Bombs.
     * Explanation: https://www.youtube.com/watch?v=8NPbAvVXKR4
     * */
    public int maximumDetonation(int[][] bombs) {
        List<List<Integer>> graph = new ArrayList<>();
        int n = bombs.length;
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                double d = Math.sqrt((1.0 * bombs[i][0] - 1.0 * bombs[j][0]) * (1.0 * bombs[i][0] - 1.0 * bombs[j][0]) + (1.0 * bombs[i][1] - 1.0 * bombs[j][1]) * (1.0 * bombs[i][1] - 1.0 * bombs[j][1]));

                // Center of another bomb lies inside the circle...
                if (d <= bombs[i][2]) {
                    graph.get(i).add(j);
                }
                if (d <= bombs[j][2]) {
                    graph.get(j).add(i);
                }
            }
        }

        int res = 0;
        for (int i = 0; i < n; i++) {
            res = Math.max(res, dfsBomb(i, graph, new HashSet<>()));
        }

        return res;
    }

    private int dfsBomb(int idx, List<List<Integer>> graph, Set<Integer> visited) {
        if (visited.contains(idx)) {
            return 0;
        }

        visited.add(idx);
        for (int nei : graph.get(idx)) {
            dfsBomb(nei, graph, visited);
        }

        return visited.size();
    }
}
