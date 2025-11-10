package dfs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class DFSSolution5 {

    public static void main(String[] args) {
        int[] array = {0, 1, 2};
        DFSSolution5 dfsSolution5 = new DFSSolution5();
        System.out.println("Nesting: " + dfsSolution5.arrayNesting(array));
    }

    // Leetcode problem: 2101

    /**
     * Detonate the Maximum Bombs.
     * Explanation: https://www.youtube.com/watch?v=8NPbAvVXKR4
     *
     */
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

    /**
     * Array Nesting
     * https://www.youtube.com/watch?v=91rjaoOnt6Q&list=PLEvw47Ps6OBDcuLQ8FJZ7gc8rLOVXMjbL
     * 0 -> 2 -> 5 -> 1 -> 0 :=> All elements lies in the same cycle always returns same result.
     * So, don't need to traverse the element that are already visited.
     * Find the maximum length of path from an unvisited element.
     *
     */
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

    /**
     * All Paths From Source to Target
     *
     */
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

    // Leetcode problem: 947

    /**
     * Most Stones Removed with Same Row or Column.
     * Find the number of connected components.
     * For each connected component only 1 stone will be alive.
     * 2 Stones are neighbor if they are in the same row or column.
     */
    public int removeStones(int[][] stones) {
        int n = stones.length;
        List<List<Integer>> graph = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (stones[i][0] == stones[j][0] || stones[i][1] == stones[j][1]) {
                    graph.get(i).add(j);
                    graph.get(j).add(i);
                }
            }
        }

        boolean[] visited = new boolean[n];
        int component = 0;

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                component += 1;
                dfsStones(i, graph, visited);
            }
        }

        return n - component;
    }

    private void dfsStones(int node, List<List<Integer>> graph, boolean[] visited) {
        visited[node] = true;

        for (int nei : graph.get(node)) {
            if (!visited[nei]) {
                dfsStones(nei, graph, visited);
            }
        }
    }

    // Leetcode problem: 2115

    /**
     * Find All Possible Recipes from Given Supplies
     *
     */
    public List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {
        int n = recipes.length;
        Map<String, List<String>> graph = new HashMap<>();

        for (int i = 0; i < n; i++) {
            graph.put(recipes[i], new ArrayList<>());
            for (String ingredient : ingredients.get(i)) {
                graph.get(recipes[i]).add(ingredient);
            }
        }

        Map<String, Boolean> supplyMap = new HashMap<>();
        for (String supply : supplies) {
            supplyMap.put(supply, true);
        }

        List<String> result = new ArrayList<>();
        for (String recipe : recipes) {
            if (dfsRecipe(recipe, graph, supplyMap)) {
                result.add(recipe);
            }
        }

        return result;
    }

    private boolean dfsRecipe(String recipe, Map<String, List<String>> graph, Map<String, Boolean> supplyMap) {

        if (supplyMap.containsKey(recipe)) {
            return supplyMap.get(recipe);
        }

        if (!graph.containsKey(recipe)) {
            return false;
        }

        supplyMap.put(recipe, false);
        for (String ingredient : graph.get(recipe)) {
            if (!dfsRecipe(ingredient, graph, supplyMap)) {
                return false;
            }
        }

        supplyMap.put(recipe, true);
        return true;
    }

    // Leetcode problem: 3607

    /**
     * Power Grid Maintenance.
     * Explanation: https://www.youtube.com/watch?v=c_TCs1dnIzo
     */
    public int[] processQueries(int c, int[][] connections, int[][] queries) {
        List<List<Integer>> graph = new ArrayList<>(c + 1);

        // Build the graph.
        for (int i = 0; i <= c; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] connection : connections) {
            graph.get(connection[0]).add(connection[1]);
            graph.get(connection[1]).add(connection[0]);
        }

        Map<Integer, PriorityQueue<Integer>> queues = new HashMap<>();
        Map<Integer, Integer> groups = new HashMap<>();
        Set<Integer> online = new HashSet<>();

        // Create the connected components.
        for (int i = 1; i <= c; i++) {
            if (!online.contains(i)) {
                queues.put(i, new PriorityQueue<>());
                dfsGraph(i, i, graph, groups, queues, online);
            }
        }

        int qCount = 0;
        for (int[] query : queries) {
            if (query[0] == 1) {
                qCount += 1;
            }
        }
        int i = 0;
        int[] res = new int[qCount];
        for (int[] query : queries) {
            if (query[0] == 1) {
                if (online.contains(query[1])) {
                    res[i++] = query[1];
                    continue;
                }
                int groupId = groups.get(query[1]);
                PriorityQueue<Integer> queue = queues.get(groupId);
                while (!queue.isEmpty() && !online.contains(queue.peek())) {
                    queue.poll();
                }
                if (!queue.isEmpty()) {
                    res[i++] = queue.peek();
                } else {
                    res[i++] = -1;
                }
            } else {
                online.remove(query[1]);
            }
        }

        return res;

    }

    private void dfsGraph(int u, int groupId, List<List<Integer>> graph, Map<Integer, Integer> groups, Map<Integer, PriorityQueue<Integer>> queues, Set<Integer> online) {
        online.add(u);
        groups.put(u, groupId);
        queues.get(groupId).add(u);

        for (int adjacent : graph.get(u)) {
            if (!online.contains(adjacent)) {
                dfsGraph(adjacent, groupId, graph, groups, queues, online);
            }
        }
    }
}
