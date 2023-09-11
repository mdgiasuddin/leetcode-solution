package dfs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DFSSolution5 {

    public static void main(String[] args) {
        int[] array = {0, 1, 2};
        DFSSolution5 dfsSolution5 = new DFSSolution5();
        System.out.println("Nesting: " + dfsSolution5.arrayNesting(array));
    }

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
                double d = Math.sqrt((1.0 * bombs[i][0] - 1.0 * bombs[j][0]) * (1.0 * bombs[i][0] - 1.0 * bombs[j][0]) +
                    (1.0 * bombs[i][1] - 1.0 * bombs[j][1]) * (1.0 * bombs[i][1] - 1.0 * bombs[j][1]));

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

    // Leetcode problem: 565
    /*
     * Array Nesting
     * https://www.youtube.com/watch?v=91rjaoOnt6Q&list=PLEvw47Ps6OBDcuLQ8FJZ7gc8rLOVXMjbL
     * 0 -> 2 -> 5 -> 1 -> 0 :=> All elements lies in the same cycle always returns same result.
     * So, don't need to traverse the element that are already visited.
     * Find the maximum length of path from an unvisited element.
     * */
    public int arrayNesting(int[] nums) {
        int n = nums.length;
        boolean[] visited = new boolean[n];
        int res = 0;
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                res = Math.max(res, dfsArray(i, 0, nums, visited));
            }
        }

        return res;
    }

    private int dfsArray(int k, int count, int[] nums, boolean[] visited) {
        if (visited[k]) {
            return count;
        }
        visited[k] = true;
        return dfsArray(nums[k], count + 1, nums, visited);
    }

    // Leetcode problem: 797
    /*
     * All Paths From Source to Target
     * */
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {

        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();
        current.add(0);
        dfsGraph(0, graph, result, current);
        return result;
    }

    private void dfsGraph(int node, int[][] graph, List<List<Integer>> result, List<Integer> current) {
        if (node == graph.length - 1) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int nei : graph[node]) {
            current.add(nei);
            dfsGraph(nei, graph, result, current);
            current.remove(current.size() - 1);
        }
    }
}
