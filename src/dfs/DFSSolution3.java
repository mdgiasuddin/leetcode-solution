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
    /*
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
    /*
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
    /*
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
}
