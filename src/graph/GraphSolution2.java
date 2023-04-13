package graph;

import java.util.*;

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
}
