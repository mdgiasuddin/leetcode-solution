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

    // Leetcode problem: 1514
    /*
     * Path with Maximum Probability.
     * Dijkstra's Algorithm.
     * Explanation: https://www.youtube.com/watch?v=kPsDTGcrzGM
     * */
    public double maxProbability(int n, int[][] edges, double[] succProb, int start, int end) {
        PriorityQueue<NodeProbability> queue = new PriorityQueue<>(Comparator.comparingDouble(a -> -a.getProbability()));

        List<List<NodeProbability>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edges.length; i++) {
            graph.get(edges[i][0]).add(new NodeProbability(edges[i][1], succProb[i]));
            graph.get(edges[i][1]).add(new NodeProbability(edges[i][0], succProb[i]));
        }

        boolean[] visited = new boolean[n];
        queue.add(new NodeProbability(start, 1));

        while (!queue.isEmpty()) {
            NodeProbability np = queue.poll();
            if (np.getNode() == end) {
                return np.getProbability();
            }

            visited[np.getNode()] = true;

            for (NodeProbability child : graph.get(np.getNode())) {
                if (!visited[child.getNode()]) {
                    queue.add(new NodeProbability(child.getNode(), np.getProbability() * child.getProbability()));
                }
            }
        }

        return 0;
    }

    // Leetcode problem: 1129
    /*
     * Shortest Path with Alternating Colors
     * */
    public int[] shortestAlternatingPaths(int n, int[][] redEdges, int[][] blueEdges) {
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] red : redEdges) {
            graph.get(red[0]).add(new int[]{red[1], 0});
        }

        for (int[] blue : blueEdges) {
            graph.get(blue[0]).add(new int[]{blue[1], 1});
        }

        // Track the visited node with specified color.
        List<Set<Integer>> visited = new ArrayList<>();
        visited.add(new HashSet<>());
        visited.add(new HashSet<>());

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{0, -1});
        int[] distance = new int[n];
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[0] = 0;

        int d = 1;
        while (!queue.isEmpty()) {
            int qSize = queue.size();
            while (qSize-- > 0) {
                int[] node = queue.poll();
                for (int[] child : graph.get(node[0])) {

                    // Check the child node color is different from parent
                    // & child node is not already visited with the same color
                    if (child[1] != node[1] && !visited.get(child[1]).contains(child[0])) {
                        visited.get(child[1]).add(child[0]);
                        queue.add(new int[]{child[0], child[1]});

                        // Shortest distance may already be found.
                        distance[child[0]] = Math.min(distance[child[0]], d);
                    }
                }
            }

            d += 1;
        }

        for (int i = 0; i < n; i++) {
            if (distance[i] == Integer.MAX_VALUE) {
                distance[i] = -1;
            }
        }

        return distance;
    }
}

class NodeProbability {
    private int node;
    private double probability;

    public NodeProbability(int node, double probability) {
        this.node = node;
        this.probability = probability;
    }

    public int getNode() {
        return node;
    }

    public double getProbability() {
        return probability;
    }
}
