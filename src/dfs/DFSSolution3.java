package dfs;

import java.util.*;

class Employee {
    public int id;
    public int importance;
    public List<Integer> subordinates;
}

public class DFSSolution3 {

    public static void main(String[] args) {
        DFSSolution3 dfsSolution3 = new DFSSolution3();

        int[][] flights = {{0, 1, 100}, {1, 2, 100}, {2, 0, 100}, {1, 3, 600}, {2, 3, 200}};
        System.out.println(dfsSolution3.findCheapestPrice(4, flights, 0, 3, 1));
    }

    // Leetcode problem: 690
    public int getImportance(List<Employee> employees, int id) {
        Map<Integer, Employee> employeeMap = new HashMap<>();

        for (Employee employee : employees) {
            employeeMap.put(employee.id, employee);
        }

        return dfsSubordinate(id, employeeMap);
    }

    private int dfsSubordinate(int id, Map<Integer, Employee> employeeMap) {

        int res = employeeMap.get(id).importance;
        for (int subordinate : employeeMap.get(id).subordinates) {
            res += dfsSubordinate(subordinate, employeeMap);
        }

        return res;
    }

    // Leetcode problem: 841
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        Set<Integer> visited = new HashSet<>();
        dfsRoom(0, rooms, visited);

        return visited.size() == rooms.size();
    }

    public void dfsRoom(int room, List<List<Integer>> rooms, Set<Integer> visited) {
        if (visited.contains(room)) {
            return;
        }

        visited.add(room);
        for (int neighbor : rooms.get(room)) {
            dfsRoom(neighbor, rooms, visited);
        }
    }

    // Leetcode problem: 1254

    /**
     * Number of closed island. 0 for land 1 for water.
     * First dfs from boundary land which are reachable from boundary land.
     * Then dfs from inside land. If a can reach to boundary land, it is not closed.
     * */
    public int closedIsland(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        for (int r = 0; r < m; r++) {
            dfsFromBoundary(r, 0, grid);
            dfsFromBoundary(r, n - 1, grid);
        }

        for (int c = 1; c < n - 1; c++) {
            dfsFromBoundary(0, c, grid);
            dfsFromBoundary(m - 1, c, grid);
        }

        int res = 0;
        for (int r = 1; r < m - 1; r++) {
            for (int c = 1; c < n - 1; c++) {
                if (grid[r][c] == 0 && dfsFromIsland(r, c, grid)) {
                    res += 1;
                }
            }
        }

        return res;
    }

    private void dfsFromBoundary(int r, int c, int[][] grid) {
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length || grid[r][c] != 0)
            return;

        grid[r][c] = -1;

        dfsFromBoundary(r - 1, c, grid);
        dfsFromBoundary(r + 1, c, grid);
        dfsFromBoundary(r, c - 1, grid);
        dfsFromBoundary(r, c + 1, grid);
    }

    private boolean dfsFromIsland(int r, int c, int[][] grid) {

        // Reached boundary land. So discard it.
        if (grid[r][c] == -1)
            return false;

        // Reached to water. Accept it.
        if (grid[r][c] == 1)
            return true;

        // Mark as visited & change it as a water boundary.
        grid[r][c] = 1;

        // Check whether all four side is surrounded by water or not.
        boolean res = dfsFromIsland(r - 1, c, grid);
        res = res && dfsFromIsland(r + 1, c, grid);
        res = res && dfsFromIsland(r, c - 1, grid);
        res = res && dfsFromIsland(r, c + 1, grid);

        return res;
    }

    // Leetcode problem: 1020

    /**
     * Separate the lands reachable from boundary. Then count land from inside.
     * */
    public int numEnclaves(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        for (int r = 0; r < m; r++) {
            dfsFromBoundaryLand(r, 0, grid);
            dfsFromBoundaryLand(r, n - 1, grid);
        }
        for (int c = 1; c < n - 1; c++) {
            dfsFromBoundaryLand(0, c, grid);
            dfsFromBoundaryLand(m - 1, c, grid);
        }

        int res = 0;
        for (int r = 1; r < m - 1; r++) {
            for (int c = 1; c < n - 1; c++) {
                if (grid[r][c] == 1)
                    res += 1;
            }
        }

        return res;
    }

    private void dfsFromBoundaryLand(int r, int c, int[][] grid) {
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length || grid[r][c] != 1) {
            return;
        }

        grid[r][c] = -1;

        dfsFromBoundaryLand(r - 1, c, grid);
        dfsFromBoundaryLand(r + 1, c, grid);
        dfsFromBoundaryLand(r, c - 1, grid);
        dfsFromBoundaryLand(r, c + 1, grid);
    }

    // Leetcode problem: 787

    /**
     * This solution exceeds the time limit.
     * */
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        Map<Integer, List<int[]>> edges = new HashMap<>();

        for (int i = 0; i < n; i++) {
            edges.put(i, new ArrayList<>());
        }

        for (int[] flight : flights) {
            edges.get(flight[0]).add(new int[]{flight[1], flight[2]});
        }

        boolean[] visited = new boolean[n];
        int[] minCost = {Integer.MAX_VALUE};

        // Number of stops is k. Add src & dst node to it. So total path length = k + 2;
        findCheapestPrice(src, dst, k + 2, 0, minCost, edges, visited);

        return minCost[0] == Integer.MAX_VALUE ? -1 : minCost[0];
    }

    private void findCheapestPrice(int src, int dst, int k, int currentTotal, int[] minCost, Map<Integer, List<int[]>> edges, boolean[] visited) {
        if (k == 0 || visited[src]) {
            return;
        }

        // Update minimum cost.
        if (src == dst) {
            minCost[0] = Math.min(minCost[0], currentTotal);
            return;
        }

        visited[src] = true;
        for (int[] neighbor : edges.get(src)) {

            // If we get already a minimum path, discard it.
            if (currentTotal + neighbor[1] < minCost[0]) {
                findCheapestPrice(neighbor[0], dst, k - 1, currentTotal + neighbor[1], minCost, edges, visited);
            }
        }

        visited[src] = false;
    }

    // Leetcode problem: 332

    /**Note::
     *->The solution is tricky.
     *->The main idea is to traverse every edge at most once
     *->And we are starting from JFK airport
     *->We use Priority Queue to store the adjacent airport in Lexically sorted manner
     *->We use a topological sort like approach for displaying the result, i.e, we start from an no in-dependency edge to the most in-dependenncy edge
     *->We are considering the euler path to traverse the graph
     *->Priority Queue is also helping us keep track of the visited and non-visited edge
     *->Hash Map is Used like a adjacency list here
     */
    public List<String> findItinerary(List<List<String>> tickets) {

        Map<String, PriorityQueue<String>> adjacencyList = new HashMap<>();
        for (List<String> ticket : tickets) {
            PriorityQueue<String> pq = adjacencyList.getOrDefault(ticket.get(0), new PriorityQueue<>());
            pq.add(ticket.get(1));
            adjacencyList.put(ticket.get(0), pq);
        }

        LinkedList<String> visited = new LinkedList<>();
        dfsItinerary("JFK", adjacencyList, visited);

        return visited;
    }

    private void dfsItinerary(String src, Map<String, PriorityQueue<String>> adjacencyList, LinkedList<String> visited) {
        PriorityQueue<String> pq = adjacencyList.get(src);

        while (pq != null && !pq.isEmpty()) {
            String tmp = pq.poll();
            dfsItinerary(tmp, adjacencyList, visited);
        }

        visited.addFirst(src);
    }

    // Leetcode problem: 886

    /**
     * Possible Bipartition.
     * A graph is bi-partite if 2-color is possible.
     * */
    public boolean possibleBipartition(int n, int[][] dislikes) {

        // Build up the graph by adjacency list.
        List<List<Integer>> adjacent = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            adjacent.add(new ArrayList<>());
        }

        for (int[] dislike : dislikes) {
            adjacent.get(dislike[0]).add(dislike[1]);
            adjacent.get(dislike[1]).add(dislike[0]);
        }

        int[] colors = new int[n + 1];
        Arrays.fill(colors, -1);

        for (int i = 1; i <= n; i++) {
            if (colors[i] == -1 && !colorVertex(i, 0, colors, adjacent)) {
                return false;
            }

        }

        return true;
    }

    private boolean colorVertex(int u, int color, int[] colors, List<List<Integer>> adjacent) {

        // Already colored with same color.
        if (colors[u] == color) {
            return true;
        }

        // Already colored with different color.
        if (colors[u] == (color + 1) % 2) {
            return false;
        }

        colors[u] = color;
        for (int v : adjacent.get(u)) {
            if (!colorVertex(v, (color + 1) % 2, colors, adjacent)) {
                return false;
            }
        }

        return true;
    }

    // Leetcode problem: 419

    /**
     * Battleships in a Board.
     * The problem is completely same as: Number of Islands (Leetcode problem: 200)
     * */
    public int countBattleships(char[][] board) {

        int res = 0;
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c] == 'X') {
                    res += 1;
                    countBattleships(r, c, board);
                }
            }
        }

        return res;
    }

    public void countBattleships(int r, int c, char[][] board) {
        if (r < 0 || r >= board.length || c < 0 || c >= board[0].length || board[r][c] != 'X') {
            return;
        }

        board[r][c] = '*';
        countBattleships(r - 1, c, board);
        countBattleships(r + 1, c, board);
        countBattleships(r, c - 1, board);
        countBattleships(r, c + 1, board);
    }

    // Leetcode problem: 1319

    /**
     * Number of Operations to Make Network Connected
     * */
    public int makeConnected(int n, int[][] connections) {

        // To connect all the nodes in a single network, there must have n-1 edges.
        if (n > connections.length + 1) {
            return -1;
        }

        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] connection : connections) {
            graph.get(connection[0]).add(connection[1]);
            graph.get(connection[1]).add(connection[0]);
        }

        // Count the number of connected components.
        boolean[] visited = new boolean[n];
        int components = 0;
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                makeConnected(i, graph, visited);
                components += 1;
            }
        }

        // Add cables between the networks.
        return components - 1;
    }

    public void makeConnected(int u, List<List<Integer>> graph, boolean[] visited) {
        if (visited[u]) {
            return;
        }

        visited[u] = true;
        for (int v : graph.get(u)) {
            makeConnected(v, graph, visited);
        }
    }

    // Leetcode problem: 174

    /**
     * Dungeon Game.
     * Code source: https://www.youtube.com/watch?v=4uUGxZXoR5o&t=250s
     * Solve by bottom-up approach for bottom-right cell.
     * Positive value in DP means we can reach to bottom-right cell with 0 power.
     * So make it 0.
     * */
    public int calculateMinimumHP(int[][] dungeon) {
        int m = dungeon.length;
        int n = dungeon[0].length;

        int[][] dp = new int[m][n];
        dp[m - 1][n - 1] = Math.min(0, dungeon[m - 1][n - 1]);

        for (int r = m - 2; r >= 0; r--) {
            dp[r][n - 1] = Math.min(0, dungeon[r][n - 1] + dp[r + 1][n - 1]);
        }

        for (int c = n - 2; c >= 0; c--) {
            dp[m - 1][c] = Math.min(0, dungeon[m - 1][c] + dp[m - 1][c + 1]);
        }

        for (int r = m - 2; r >= 0; r--) {
            for (int c = n - 2; c >= 0; c--) {
                dp[r][c] = Math.min(0, dungeon[r][c] + Math.max(dp[r][c + 1], dp[r + 1][c]));
            }
        }

        return 1 + Math.abs(dp[0][0]);
    }
}
