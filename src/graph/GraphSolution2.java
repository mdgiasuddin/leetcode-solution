package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class GraphSolution2 {

    // Leetcode problem: 721
    /*
     * Accounts Merge.
     * Union Find algorithm.
     * */
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        int n = accounts.size();
        int[] parent = new int[n];
        int[] rank = new int[n];
        for (int i = 0; i < n; i++) {
            rank[i] = 1;
            parent[i] = i;
        }

        Map<String, Integer> emailMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < accounts.get(i).size(); j++) {
                if (emailMap.containsKey(accounts.get(i).get(j))) {
                    // Join 2 accounts.
                    union(i, emailMap.get(accounts.get(i).get(j)), parent, rank);
                } else {
                    emailMap.put(accounts.get(i).get(j), i);
                }
            }
        }

        // Group the emails with same parent.
        Map<Integer, List<String>> emailGroup = new HashMap<>();
        for (Map.Entry<String, Integer> entry : emailMap.entrySet()) {
            int leader = findParent(entry.getValue(), parent);
            emailGroup.putIfAbsent(leader, new ArrayList<>());
            emailGroup.get(leader).add(entry.getKey());
        }

        List<List<String>> result = new ArrayList<>();
        for (Map.Entry<Integer, List<String>> entry : emailGroup.entrySet()) {
            List<String> account = new ArrayList<>();
            // Add the name.
            account.add(accounts.get(entry.getKey()).get(0));
            // Add the emails in sorted order.
            account.addAll(entry.getValue().stream().sorted().toList());

            result.add(account);
        }

        return result;

    }

    private int findParent(int node, int[] parent) {
        while (parent[node] != node) {
            node = parent[node];
        }

        return node;
    }

    private boolean union(int node1, int node2, int[] parent, int[] rank) {
        int p1 = findParent(node1, parent);
        int p2 = findParent(node2, parent);

        if (p1 == p2) {
            return false;
        }

        if (rank[p1] >= rank[p2]) {
            parent[p2] = p1;
            rank[p1] += rank[p2];
        } else {
            parent[p1] = p2;
            rank[p2] += rank[p1];
        }

        return true;
    }

    // Leetcode problem: 827
    /*
     * Making A Large Island.
     * Explanation: https://www.youtube.com/watch?v=JKRJUE74fm8&list=PLEvw47Ps6OBA3ysUO1XOho_6bFsqQ9NQ8
     * Union find algorithm.
     * */
    public int largestIsland(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        // Initialize parent & rank.
        int[] parent = new int[m * n];
        int[] rank = new int[m * n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int idx = i * n + j;
                parent[idx] = idx;
                rank[idx] = grid[i][j];
            }
        }

        // Union left<-->right in each row.
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (grid[i][j] == 1 && grid[i][j + 1] == 1) {
                    union(i * n + j, i * n + j + 1, parent, rank);
                }
            }
        }

        // Union up<-->down in each column.
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m - 1; i++) {
                if (grid[i][j] == 1 && grid[i + 1][j] == 1) {
                    union(i * n + j, (i + 1) * n + j, parent, rank);
                }
            }
        }

        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    int parentIdx = findParent(i * n + j, parent);
                    res = Math.max(res, rank[parentIdx]);
                    continue;
                }

                Set<Integer> set = new HashSet<>();
                if (i - 1 >= 0 && grid[i - 1][j] == 1) {
                    set.add(findParent((i - 1) * n + j, parent));
                }
                if (i + 1 < m && grid[i + 1][j] == 1) {
                    set.add(findParent((i + 1) * n + j, parent));
                }
                if (j - 1 >= 0 && grid[i][j - 1] == 1) {
                    set.add(findParent(i * n + j - 1, parent));
                }
                if (j + 1 < n && grid[i][j + 1] == 1) {
                    set.add(findParent(i * n + j + 1, parent));
                }
                int islandSize = 1;
                for (int parentIdx : set) {
                    islandSize += rank[parentIdx];
                }
                res = Math.max(res, islandSize);

            }
        }

        return res;
    }

    // Leetcode problem: 1579
    /*
     * Remove Max Number of Edges to Keep Graph Fully Traversable.
     * Explanation: https://www.youtube.com/watch?v=booGwg5wYm4
     * */
    public int maxNumEdgesToRemove(int n, int[][] edges) {
        UnionFind alice = new UnionFind(n);
        UnionFind bob = new UnionFind(n);

        int requiredEdge = 0;

        for (int[] edge : edges) {
            if (edge[0] == 3) {
                requiredEdge += (alice.union(edge[1] - 1, edge[2] - 1)
                        | bob.union(edge[1] - 1, edge[2] - 1));
            }
        }

        for (int[] edge : edges) {
            if (edge[0] == 1) {
                requiredEdge += alice.union(edge[1] - 1, edge[2] - 1);
            } else if (edge[0] == 2) {
                requiredEdge += bob.union(edge[1] - 1, edge[2] - 1);
            }
        }

        if (alice.getComponents() > 1 || bob.getComponents() > 1) {
            return -1;
        }

        return edges.length - requiredEdge;
    }

    // Leetcode problem: 1192
    /*
     * Critical Connections in a Network.
     * Explanation: https://www.youtube.com/watch?v=Rhxs4k6DyMM
     * Tarzan's Algorithm.
     * */
    int time = 0;

    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        List<List<Integer>> graph = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (List<Integer> connection : connections) {
            graph.get(connection.get(0)).add(connection.get(1));
            graph.get(connection.get(1)).add(connection.get(0));
        }
        List<List<Integer>> res = new ArrayList<>();
        int[] disc = new int[n];
        int[] low = new int[n];
        int[] parent = new int[n];

        Arrays.fill(disc, -1);
        Arrays.fill(low, -1);
        Arrays.fill(parent, -1);
        dfsConnection(0, graph, disc, low, parent, res);

        return res;
    }

    private void dfsConnection(int u, List<List<Integer>> graph, int[] disc, int[] low, int[] parent, List<List<Integer>> res) {
        disc[u] = low[u] = time;
        time += 1;

        for (int v : graph.get(u)) {
            if (disc[v] == -1) {
                parent[v] = u;
                dfsConnection(v, graph, disc, low, parent, res);
                low[u] = Math.min(low[u], low[v]);

                if (low[v] > disc[u]) {
                    res.add(Arrays.asList(u, v));
                }
            } else if (v != parent[u]) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }
    }

    // Leetcode problem: 2290
    /*
     * Minimum Obstacle Removal to Reach Corner.
     * Run BFS.
     * For every node run DFS to find all the nodes in the same level.
     * */
    public int minimumObstacles(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int obstacles = grid[0][0];
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[m][n];
        dfs(0, 0, true, grid, visited, queue);

        while (!queue.isEmpty()) {
            int size = queue.size();

            while (size-- > 0) {
                int[] rc = queue.poll();
                if (rc[0] == m - 1 && rc[1] == n - 1) {
                    return obstacles;
                }

                dfs(rc[0] - 1, rc[1], true, grid, visited, queue);
                dfs(rc[0] + 1, rc[1], true, grid, visited, queue);
                dfs(rc[0], rc[1] - 1, true, grid, visited, queue);
                dfs(rc[0], rc[1] + 1, true, grid, visited, queue);
            }
            obstacles += 1;
        }

        return -1;
    }

    private void dfs(int r, int c, boolean start, int[][] grid, boolean[][] visited, Queue<int[]> queue) {
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length || visited[r][c] || (!start && grid[r][c] == 1)) {
            return;
        }

        queue.add(new int[]{r, c});
        visited[r][c] = true;
        dfs(r - 1, c, false, grid, visited, queue);
        dfs(r + 1, c, false, grid, visited, queue);
        dfs(r, c - 1, false, grid, visited, queue);
        dfs(r, c + 1, false, grid, visited, queue);
    }

    // Leetcode problem: 1368
    /*
     * Minimum Cost to Make at Least One Valid Path in a Grid
     * BFS + DFS
     * Similar to: Minimum Obstacle Removal to Reach Corner (Leetcode problem: 2290).
     * */
    public int minCost(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[m][n];
        dfsMinCost(0, 0, grid, visited, queue);
        int cost = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] rc = queue.poll();
                if (rc[0] == m - 1 && rc[1] == n - 1) {
                    return cost;
                }

                dfsMinCost(rc[0], rc[1] + 1, grid, visited, queue);
                dfsMinCost(rc[0], rc[1] - 1, grid, visited, queue);
                dfsMinCost(rc[0] + 1, rc[1], grid, visited, queue);
                dfsMinCost(rc[0] - 1, rc[1], grid, visited, queue);
            }

            cost += 1;

        }

        return -1;

    }

    private void dfsMinCost(int r, int c, int[][] grid, boolean[][] visited, Queue<int[]> queue) {
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length || visited[r][c]) {
            return;
        }

        visited[r][c] = true;
        queue.add(new int[]{r, c});
        if (grid[r][c] == 1) {
            dfsMinCost(r, c + 1, grid, visited, queue);
        }
        if (grid[r][c] == 2) {
            dfsMinCost(r, c - 1, grid, visited, queue);
        }
        if (grid[r][c] == 3) {
            dfsMinCost(r + 1, c, grid, visited, queue);
        }
        if (grid[r][c] == 4) {
            dfsMinCost(r - 1, c, grid, visited, queue);
        }
    }

}

class UnionFind {
    private int components;
    private final int[] parent;
    private final int[] rank;

    public UnionFind(int nodes) {
        this.components = nodes;
        this.parent = new int[nodes];
        this.rank = new int[nodes];

        Arrays.fill(rank, 1);
        for (int i = 0; i < nodes; i++) {
            parent[i] = i;
        }
    }

    public int getComponents() {
        return components;
    }

    private int findParent(int u) {
        while (parent[u] != u) {
            u = parent[u];
        }

        return u;
    }

    public int union(int u, int v) {
        int pu = findParent(u);
        int pv = findParent(v);

        if (pu == pv) {
            return 0;
        }

        if (rank[pu] >= rank[pv]) {
            parent[pv] = pu;
            rank[pu] += rank[pv];
        } else {
            parent[pu] = pv;
            rank[pv] += rank[pu];
        }

        this.components -= 1;

        return 1;
    }
}
