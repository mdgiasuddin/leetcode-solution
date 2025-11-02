package graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class GraphSolution3 {
    public static void main(String[] args) {
        GraphSolution3 graph = new GraphSolution3();
        int[][] edges = {{0, 5, 0}, {3, 7, 2}, {7, 6, 0}, {2, 4, 1}, {0, 4, 1}, {7, 1, 0}};
        int[][] query = {{7, 1}, {1, 5}, {6, 1}, {1, 3}, {2, 0}, {4, 7}, {3, 2}, {3, 2}, {5, 2}};

        graph.minimumCost(8, edges, query);
    }

    // Leetcode problem: 1976
    /**
     * Number of Ways to Arrive at Destination.
     */
    public int countPaths(int n, int[][] roads) {
        List<List<int[]>> graph = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] road : roads) {
            graph.get(road[0]).add(new int[]{road[1], road[2]});
            graph.get(road[1]).add(new int[]{road[0], road[2]});
        }

        PriorityQueue<long[]> queue = new PriorityQueue<>(Comparator.comparingLong(a -> a[1]));
        boolean[] visited = new boolean[n];
        long[][] pathCount = new long[n][2];
        for (int i = 0; i < n; i++) {
            pathCount[i][0] = Long.MAX_VALUE;
        }

        pathCount[0][0] = 0;
        pathCount[0][1] = 1;
        queue.add(new long[]{0, 0});
        long mod = 1000000007;

        while (!queue.isEmpty()) {
            long[] rd = queue.poll();
            visited[(int) rd[0]] = true;

            for (int[] nei : graph.get((int) rd[0])) {
                long newDistance = rd[1] + nei[1];
                if (!visited[nei[0]] && newDistance <= pathCount[nei[0]][0]) {
                    if (newDistance == pathCount[nei[0]][0]) {
                        pathCount[nei[0]][1] = (pathCount[nei[0]][1] + pathCount[(int) rd[0]][1]) % mod;
                    } else {
                        pathCount[nei[0]][1] = pathCount[(int) rd[0]][1];
                        queue.add(new long[]{nei[0], newDistance});
                    }
                    pathCount[nei[0]][0] = newDistance;
                }
            }
        }

        return (int) pathCount[n - 1][1];
    }

    public int[] minimumCost(int n, int[][] edges, int[][] query) {
        int[] parent = new int[n];
        int[] rank = new int[n];
        int[] cost = new int[n];
        int allOne = ~0;

        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 1;
            cost[i] = allOne;
        }

        for (int[] edge : edges) {
            union(edge, parent, rank, cost);
        }

//        System.out.println(Arrays.toString(parent));
//        System.out.println(Arrays.toString(rank));
//        System.out.println(Arrays.toString(cost));

        int[] res = new int[query.length];
        int i = 0;
        for (int q[] : query) {
            int p1 = findParent(q[0], parent);
            int p2 = findParent(q[1], parent);

            if (p1 != p2) {
                res[i] = -1;
            } else {
                res[i] = cost[p1];
            }
            i += 1;
        }

        return res;
    }

    private void union(int[] edge, int[] parent, int[] rank, int[] cost) {
        int p1 = findParent(edge[0], parent);
        int p2 = findParent(edge[1], parent);

        if (rank[p1] > rank[p2]) {
            parent[p2] = p1;
            rank[p1] += rank[p2];
            cost[p1] &= (cost[p2] & edge[2]);
        } else {
            parent[p1] = p2;
            rank[p2] += rank[p1];
            cost[p2] &= edge[2];
        }
    }

    private int findParent(int u, int[] parent) {
        while (parent[u] != u) {
            u = parent[u];
        }

        return u;
    }
}
