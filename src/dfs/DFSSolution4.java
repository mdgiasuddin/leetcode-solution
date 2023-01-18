package dfs;

import java.util.ArrayList;
import java.util.List;

public class DFSSolution4 {

    // Leetcode problem: 1443
    /*
     * Minimum Time to Collect All Apples in a Tree.
     * */
    public int minTime(int n, int[][] edges, List<Boolean> hasApple) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        return dfsGraph(graph, hasApple, 0, -1);
    }

    private int dfsGraph(List<List<Integer>> graph, List<Boolean> hasApple, int node, int parent) {
        int res = 0;

        for (int child : graph.get(node)) {
            if (child == parent) {
                continue;
            }

            int childRes = dfsGraph(graph, hasApple, child, node);

            if (childRes > 0 || Boolean.TRUE.equals(hasApple.get(child))) {
                res += 2 + childRes;
            }
        }

        return res;
    }
}
