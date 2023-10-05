package bfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

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
        PriorityQueue<NodeProbability> queue =
            new PriorityQueue<>(Comparator.comparingDouble(a -> -a.getProbability()));

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
     * Shortest Path with Alternating Colors.
     * */
    public int[] shortestAlternatingPaths(int n, int[][] redEdges, int[][] blueEdges) {
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] red : redEdges) {
            graph.get(red[0]).add(new int[] {red[1], 0});
        }

        for (int[] blue : blueEdges) {
            graph.get(blue[0]).add(new int[] {blue[1], 1});
        }

        // Track the visited node with specified color.
        List<Set<Integer>> visited = new ArrayList<>();
        visited.add(new HashSet<>());
        visited.add(new HashSet<>());

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] {0, -1});
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
                        queue.add(new int[] {child[0], child[1]});

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

    // Leetcode problem: 399
    /*
     * Evaluate Division.
     * Explanation: https://www.youtube.com/watch?v=Uei1fwDoyKk&t=1s
     * */
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {

        Map<String, List<Variable>> graph = new HashMap<>();
        int i = 0;
        for (List<String> equation : equations) {
            List<Variable> list1 = graph.getOrDefault(equation.get(0), new ArrayList<>());
            list1.add(new Variable(equation.get(1), values[i]));
            graph.put(equation.get(0), list1);

            List<Variable> list2 = graph.getOrDefault(equation.get(1), new ArrayList<>());
            list2.add(new Variable(equation.get(0), 1 / values[i]));
            graph.put(equation.get(1), list2);

            i += 1;
        }

        double[] res = new double[queries.size()];
        for (i = 0; i < queries.size(); i++) {
            res[i] = bfsEquation(queries.get(i).get(0), queries.get(i).get(1), graph);
        }

        return res;
    }

    private double bfsEquation(String src, String dst, Map<String, List<Variable>> graph) {
        if (!graph.containsKey(src) || !graph.containsKey(dst)) {
            return -1;
        }

        Queue<Variable> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.add(new Variable(src, 1));
        visited.add(src);

        while (!queue.isEmpty()) {
            Variable u = queue.poll();
            if (u.name.equals(dst)) {
                return u.value;
            }

            for (Variable v : graph.get(u.name)) {
                if (!visited.contains(v.name)) {
                    visited.add(v.name);
                    queue.add(new Variable(v.name, u.value * v.value));
                }
            }
        }

        return -1;
    }

    // Leetcode problem: 1376
    /*
     * Time Needed to Inform All Employees
     * */
    public int numOfMinutes(int n, int headID, int[] manager, int[] informTime) {
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < n; i++) {
            if (manager[i] == -1) {
                continue;
            }

            graph.get(manager[i]).add(new int[] {i, informTime[manager[i]]});
        }

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] {headID, 0});
        int time = 0;

        while (!queue.isEmpty()) {
            int[] node = queue.poll();
            time = Math.max(time, node[1]);

            for (int[] nei : graph.get(node[0])) {
                queue.add(new int[] {nei[0], node[1] + nei[1]});
            }
        }

        return time;
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

    // Leetcode problem: 1631
    /*
     * Path with Minimum Effort.
     * Dijkstra Algorithm.
     * Explanation: https://www.youtube.com/watch?v=XQlxCCx2vI4
     * */
    public int minimumEffortPath(int[][] heights) {
        int m = heights.length;
        int n = heights[0].length;

        boolean[][] visited = new boolean[m][n];
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        queue.add(new int[] {0, 0, 0});
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!queue.isEmpty()) {
            int[] xy = queue.poll();
            if (xy[1] == m - 1 && xy[2] == n - 1) {
                return xy[0];
            }

            if (!visited[xy[1]][xy[2]]) {
                visited[xy[1]][xy[2]] = true;
                for (int[] dir : directions) {
                    int x = xy[1] + dir[0];
                    int y = xy[2] + dir[1];
                    if (x >= 0 && x < m && y >= 0 && y < n && !visited[x][y]) {
                        queue.add(new int[] {Math.max(xy[0], Math.abs(heights[x][y] - heights[xy[1]][xy[2]])), x, y});
                    }
                }
            }
        }

        return -1;
    }
}

class Variable {
    String name;
    double value;

    public Variable(String name, double value) {
        this.name = name;
        this.value = value;
    }
}
