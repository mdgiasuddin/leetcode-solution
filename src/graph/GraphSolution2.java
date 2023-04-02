package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphSolution2 {

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
}
