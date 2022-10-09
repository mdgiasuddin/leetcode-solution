package graph;

public class GraphSolution {

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
}
