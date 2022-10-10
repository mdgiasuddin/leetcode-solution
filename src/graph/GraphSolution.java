package graph;

import java.util.*;

public class GraphSolution {

    public static void main(String[] args) {
        GraphSolution graphSolution = new GraphSolution();

        int[][] delay = {{2, 1, 1}, {2, 3, 1}, {3, 4, 1}};
        System.out.println(graphSolution.networkDelayTime(delay, 4, 2));
    }

    // Leetcode problem: 684
    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;

        int[] parent = new int[n + 1];
        int[] rank = new int[n + 1];

        // Initially every node is parent of itself. Rank is the number of connected nodes. Initially 1.
        for (int i = 0; i <= n; i++) {
            parent[i] = i;
            rank[i] = 1;
        }

        for (int[] edge : edges) {
            if (!union(edge[0], edge[1], parent, rank)) {
                return new int[]{edge[0], edge[1]};
            }
        }

        return new int[0];
    }

    private int findParent(int node, int[] parent) {
        int p = parent[node];

        // p == parent means we have reached the root. Return it as the parent of the connected part.
        while (p != parent[p]) {
            parent[p] = parent[parent[p]];
            p = parent[p];
        }

        return p;
    }

    private boolean union(int node1, int node2, int[] parent, int[] rank) {
        int p1 = findParent(node1, parent);
        int p2 = findParent(node2, parent);

        // Both node have same parents means the edge for a cycle.
        if (p1 == p2)
            return false;

        if (rank[p1] >= rank[p2]) {
            parent[p2] = p1;
            rank[p1] += rank[p2];
        } else {
            parent[p1] = p2;
            rank[p2] += rank[p1];
        }

        return true;
    }

    // Leetcode problem: 743
    public int networkDelayTime(int[][] times, int n, int k) {
        Map<Integer, List<int[]>> edges = new HashMap<>();

        for (int i = 1; i <= n; i++) {
            edges.put(i, new ArrayList<>());
        }

        for (int[] time : times) {
            edges.get(time[0]).add(new int[]{time[1], time[2]});
        }

        Set<Integer> visited = new HashSet<>();
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        queue.add(new int[]{k, 0});

        int t = 0;
        while (!queue.isEmpty()) {
            int[] node = queue.poll();

            // If already got lower value for this node. So skip the greater value.
            if (!visited.contains(node[0])) {
                t = Math.max(t, node[1]);
                visited.add(node[0]);
                List<int[]> neighbors = edges.get(node[0]);
                for (int[] neighbor : neighbors) {
                    if (!visited.contains(neighbor[0])) {
                        queue.add(new int[]{neighbor[0], node[1] + neighbor[1]});
                    }
                }
            }

        }

        return visited.size() == n ? t : -1;
    }
}
