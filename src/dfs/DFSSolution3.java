package dfs;

import java.util.*;

class Employee {
    public int id;
    public int importance;
    public List<Integer> subordinates;
}

public class DFSSolution3 {

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
}
