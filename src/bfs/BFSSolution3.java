package bfs;

import java.util.*;

public class BFSSolution3 {

    // Leetcode problem: 1345
    /*
     * Jump Game IV.
     * */
    public int minJumps(int[] arr) {
        Map<Integer, List<Integer>> graph = new HashMap<>();

        // Find all the nodes that have same values.
        for (int i = 0; i < arr.length; i++) {
            List<Integer> list = graph.getOrDefault(arr[i], new ArrayList<>());
            list.add(i);
            graph.put(arr[i], list);
        }

        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[arr.length];
        queue.add(0);
        visited[0] = true;
        int distance = 0;

        while (!queue.isEmpty()) {
            int qSize = queue.size();
            while (qSize-- > 0) {
                int u = queue.poll();
                if (u == arr.length - 1) {
                    return distance;
                }

                if (u + 1 < arr.length && !visited[u + 1]) {
                    queue.add(u + 1);
                    visited[u + 1] = true;
                }

                if (u - 1 >= 0 && !visited[u - 1]) {
                    queue.add(u - 1);
                    visited[u - 1] = true;
                }
                for (int v : graph.get(arr[u])) {
                    if (!visited[v]) {
                        queue.add(v);
                        visited[v] = true;
                    }
                }
                // The nodes with value arr[i] are already visited. So clear it from the list.
                graph.get(arr[u]).clear();
            }

            distance += 1;
        }

        return -1;
    }
}
