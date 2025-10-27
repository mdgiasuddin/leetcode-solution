package graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class GraphSolution3 {
    public static void main(String[] args) {
        GraphSolution3 solution3 = new GraphSolution3();
        int[][] roads = {{0, 6, 7}, {0, 1, 2}, {1, 2, 3}, {1, 3, 3}, {6, 3, 3}, {3, 5, 1}, {6, 5, 1}, {2, 5, 1}, {0, 4, 5}, {4, 6, 2}};
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
}
