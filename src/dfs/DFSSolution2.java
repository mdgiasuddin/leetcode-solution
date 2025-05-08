package dfs;

import graph.Node;
import pair.Pair;

import java.util.*;

public class DFSSolution2 {

    public static void main(String[] args) {
        DFSSolution2 dfsSolution2 = new DFSSolution2();

        int[][] edges = {{0, 1}, {1, 3}, {2, 3}, {4, 0}, {4, 5}};

//        String[] words = {"wrt", "wrf", "er", "ett", "rftt"};
        System.out.println(dfsSolution2.minReorder(6, edges));
    }

    // Leetcode problem: 130

    /**
     * DFS through the boundary and mark all 'O' to any special character
     * Then traverse the whole array and reset the special character to 'O', other position to 'X'
     * */
    public void solve(char[][] board) {

        int m = board.length, n = board[0].length;
        if (m < 2 || n < 2)
            return;

        for (int col = 0; col < n; col++) {
            dfsBoundary(board, 0, col);
            dfsBoundary(board, m - 1, col);
        }

        for (int row = 0; row < m; row++) {
            dfsBoundary(board, row, 0);
            dfsBoundary(board, row, n - 1);
        }

        for (int row = 0; row < m; row++) {
            for (int col = 0; col < n; col++) {
                if (board[row][col] == '#') {
                    board[row][col] = 'O';
                } else {
                    board[row][col] = 'X';
                }
            }
        }
    }

    public void dfsBoundary(char[][] board, int row, int col) {
        if (row < 0 || col < 0 || row >= board.length || col >= board[0].length || board[row][col] != 'O')
            return;

        board[row][col] = '#';
        dfsBoundary(board, row - 1, col); // Up
        dfsBoundary(board, row + 1, col); // Down
        dfsBoundary(board, row, col - 1); // Left
        dfsBoundary(board, row, col + 1); // Up

    }

    // Leetcode problem: 695
    public int maxAreaOfIsland(int[][] grid) {
        int maxArea = 0;

        Set<Pair> visited = new HashSet<>();
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == 1 && !visited.contains(new Pair(r, c))) {
                    maxArea = Math.max(maxArea, dfsArea(r, c, grid, visited));
                }
            }
        }

        return maxArea;
    }

    private int dfsArea(int r, int c, int[][] grid, Set<Pair> visited) {
        if (r < 0 || c < 0 || r >= grid.length || c >= grid[0].length || grid[r][c] == 0
                || visited.contains(new Pair(r, c))) {

            return 0;
        }

        visited.add(new Pair(r, c));
        return 1 + dfsArea(r - 1, c, grid, visited)
                + dfsArea(r + 1, c, grid, visited)
                + dfsArea(r, c - 1, grid, visited)
                + dfsArea(r, c + 1, grid, visited);
    }

    // Leetcode problem: 329
    public int longestIncreasingPath(int[][] matrix) {
        int ROWS = matrix.length, COLS = matrix[0].length;
        int[][] result = new int[ROWS][COLS];

        int LIP = 1;

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c <= COLS; c++) {
                // All values in matrix is >= 0, so send a negative value as previous.
                LIP = Math.max(LIP, dfsIncreasing(r, c, matrix, -1, result));
            }
        }

        return LIP;
    }

    public int dfsIncreasing(int r, int c, int[][] matrix, int prev, int[][] result) {
        if (r < 0 || r >= matrix.length || c < 0 || c >= matrix[0].length || matrix[r][c] <= prev) {
            return 0;
        }

        if (result[r][c] > 0) {
            return result[r][c];
        }

        int res = 1;
        res = Math.max(res, 1 + dfsIncreasing(r - 1, c, matrix, matrix[r][c], result));
        res = Math.max(res, 1 + dfsIncreasing(r + 1, c, matrix, matrix[r][c], result));
        res = Math.max(res, 1 + dfsIncreasing(r, c - 1, matrix, matrix[r][c], result));
        res = Math.max(res, 1 + dfsIncreasing(r, c + 1, matrix, matrix[r][c], result));

        result[r][c] = res;
        return res;
    }

    // Leetcode problem: 133
    public Node cloneGraph(Node node) {
        Map<Node, Node> map = new HashMap<>();

        return cloneGraph(node, map);
    }

    private Node cloneGraph(Node node, Map<Node, Node> map) {
        if (node == null)
            return null;

        if (map.containsKey(node))
            return map.get(node);

        Node copy = new Node(node.val);
        map.put(node, copy);

        for (Node neighbor : node.neighbors) {
            copy.neighbors.add(cloneGraph(neighbor, map));
        }

        return copy;
    }

    // Leetcode problem: 261
    // Lintcode problem: 178
    public boolean validTree(int n, int[][] edges) {

        Set<Integer> visited = new HashSet<>();

        Map<Integer, List<Integer>> edgeMap = new HashMap<>();
        for (int i = 0; i < n; i++)
            edgeMap.put(i, new ArrayList<>());

        for (int[] edge : edges) {
            edgeMap.get(edge[0]).add(edge[1]);
            edgeMap.get(edge[1]).add(edge[0]);
        }

        // Return if no loop found and visited all nodes.
        return dfsValidTree(0, -1, edgeMap, visited) && visited.size() == n;
    }

    private boolean dfsValidTree(int v, int p, Map<Integer, List<Integer>> edgeMap, Set<Integer> visited) {
        if (visited.contains(v))
            return false;

        visited.add(v);
        for (int nei : edgeMap.get(v)) {
            // Go to all neighbor except the parent.
            if (nei != p && !dfsValidTree(nei, v, edgeMap, visited))
                return false;
        }

        return true;
    }

    // Leetcode problem: 323

    /**
     * Number of connected components in an undirected graph.
     * Run DFS and count the components.
     * */
    public int countComponents(int n, int[][] edges) {
        Set<Integer> visited = new HashSet<>();

        Map<Integer, List<Integer>> edgeMap = new HashMap<>();
        for (int i = 0; i < n; i++)
            edgeMap.put(i, new ArrayList<>());

        for (int[] edge : edges) {
            edgeMap.get(edge[0]).add(edge[1]);
            edgeMap.get(edge[1]).add(edge[0]);
        }

        int res = 0;
        for (int i = 0; i < n; i++) {
            if (!visited.contains(i)) {
                res++;
                dfsCountComponent(i, edgeMap, visited);
            }
        }

        return res;
    }

    private void dfsCountComponent(int v, Map<Integer, List<Integer>> edgeMap, Set<Integer> visited) {
        visited.add(v);
        for (int nei : edgeMap.get(v)) {
            if (!visited.contains(nei))
                dfsCountComponent(nei, edgeMap, visited);
        }
    }

    // Leetcode problem: 241
    public List<Integer> diffWaysToCompute(String expression) {

        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (ch == '+' || ch == '-' || ch == '*') {
                List<Integer> leftResult = diffWaysToCompute(expression.substring(0, i));
                List<Integer> rightResult = diffWaysToCompute(expression.substring(i + 1));

                for (int left : leftResult) {
                    for (int right : rightResult) {
                        if (ch == '+') {
                            result.add(left + right);
                        } else if (ch == '-') {
                            result.add(left - right);
                        } else {
                            result.add(left * right);
                        }
                    }
                }
            }
        }

        // If no operation (+,-,*) found, just return the number.
        if (result.size() == 0) {
            result.add(Integer.valueOf(expression));
        }

        return result;
    }

    // Leetcode problem: 269
    // Lintcode problem: 892
    public String alienOrder(String[] words) {
        Map<Character, Set<Character>> map = new HashMap<>();

        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                map.put(word.charAt(i), new HashSet<>());
            }
        }

        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];

            int minLen = Math.min(word1.length(), word2.length());
            if (word1.length() > word2.length() && word1.substring(0, minLen).equals(word2))
                return "";

            for (int j = 0; j < minLen; j++) {
                if (word1.charAt(j) != word2.charAt(j)) {
                    Set<Character> adj = map.get(word1.charAt(j));
                    adj.add(word2.charAt(j));
                    map.put(word1.charAt(j), adj);

                    break;
                }
            }
        }

        StringBuilder res = new StringBuilder();
        Map<Character, Boolean> visited = new HashMap<>();
        System.out.println("Map: " + map);
        for (char ch : map.keySet()) {
            if (dfsAlienOrder(ch, map, visited, res)) {
                return "";
            }
        }

        return res.reverse().toString();
    }

    private boolean dfsAlienOrder(char ch, Map<Character, Set<Character>> map, Map<Character, Boolean> visited, StringBuilder res) {
        if (visited.containsKey(ch)) {
            return visited.get(ch);
        }

        visited.put(ch, true);
        for (char adj : map.get(ch)) {
            if (dfsAlienOrder(adj, map, visited, res)) {
                return true;
            }
        }

        visited.put(ch, false);
        res.append(ch);

        return false;
    }

    // Leetcode problem: 1466
    public int minReorder(int n, int[][] connections) {
        Set<List<Integer>> edges = new HashSet<>();
        Map<Integer, List<Integer>> adjacencyList = new HashMap<>();
        for (int i = 0; i < n; i++) {
            adjacencyList.put(i, new ArrayList<>());
        }

        // Add neighbors in both direction. Add the edges in hash set for first searching.
        for (int[] connection : connections) {
            adjacencyList.get(connection[0]).add(connection[1]);
            adjacencyList.get(connection[1]).add(connection[0]);

            edges.add(Arrays.asList(connection[0], connection[1]));
        }

        boolean[] visited = new boolean[n];

        return dfs(0, edges, adjacencyList, visited);
    }

    private int dfs(int city, Set<List<Integer>> edges, Map<Integer, List<Integer>> adjacencyList, boolean[] visited) {

        visited[city] = true;
        int res = 0;
        for (int neighbor : adjacencyList.get(city)) {
            if (visited[neighbor])
                continue;

            // If there is no edge directed towards 0.
            if (!edges.contains(Arrays.asList(neighbor, city))) {
                res += 1;
            }

            res += dfs(neighbor, edges, adjacencyList, visited);
        }

        return res;
    }

    // Leetcode problem: 547
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;

        boolean[] visited = new boolean[n];
        int province = 0;
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfsGraph(i, n, isConnected, visited);
                province += 1;
            }
        }

        return province;
    }

    private void dfsGraph(int node, int n, int[][] isConnected, boolean[] visited) {
        if (visited[node])
            return;

        visited[node] = true;
        for (int i = 0; i < n; i++) {
            if (isConnected[node][i] == 1) {
                dfsGraph(i, n, isConnected, visited);
            }
        }
    }
}
