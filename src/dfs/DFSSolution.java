package dfs;

import pair.Pair;
import tree.TreeNode;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DFSSolution {

    public static void main(String[] args) {

        String format = "yyyy-MM-dd'T'HH:mm:ss'Z'";

        System.out.println(LocalDate.now().atStartOfDay().format(DateTimeFormatter.ofPattern(format)));
    }

    // Leetcode problem: 337

    /**
     * If a node value is taken then skip the child value
     * */
    public int rob(TreeNode root) {

        Pair pair = dfsRobber(root);

        return Math.max(pair.first, pair.second);
    }

    public Pair dfsRobber(TreeNode node) {
        if (node == null) {
            return new Pair(0, 0);
        }

        Pair leftPair = dfsRobber(node.left);
        Pair rightPair = dfsRobber(node.right);

        int withNode = node.val + leftPair.second + rightPair.second;
        int withoutNode = Math.max(leftPair.first, leftPair.second) + Math.max(rightPair.first, rightPair.second);

        return new Pair(withNode, withoutNode);
    }

    // Leetcode problem: 1306

    /**
     * Jump Game III
     * */
    public boolean canReach(int[] arr, int start) {
        boolean[] visited = new boolean[arr.length];
        return canReach(arr, visited, start);
    }

    public boolean canReach(int[] arr, boolean[] visited, int current) {
        if (current >= 0 && current < arr.length && arr[current] == 0) {
            return true;
        }
        if (current < 0 || current >= arr.length || visited[current]) {
            return false;
        }

        visited[current] = true;
        return canReach(arr, visited, current + arr[current])
                || canReach(arr, visited, current - arr[current]);
    }

    // Leetcode problem 934

    /**
     * First run dfs to find first island
     * Then run bfs to find the shortest path to second island from first island
     * */
    public int shortestBridge(int[][] grid) {
        int N = grid.length;

        boolean[][] visited = new boolean[N][N];
        Queue<int[]> queue = new LinkedList<>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (grid[i][j] == 1) {
                    dfsGrid(i, j, grid, visited, queue);
                    break;
                }
            }

            if (!queue.isEmpty())
                break;
        }

        int flip = 0;
        int[][] neighbors = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        while (!queue.isEmpty()) {
            int qSize = queue.size();

            while (qSize-- > 0) {
                int[] pos = queue.poll();

                for (int[] neighbor : neighbors) {
                    int r = pos[0] + neighbor[0];
                    int c = pos[1] + neighbor[1];
                    if (r < 0 || r >= N || c < 0 || c >= N || visited[r][c])
                        continue;

                    if (grid[r][c] == 1)
                        return flip;

                    queue.add(new int[]{r, c});
                    grid[r][c] = 1;
                    visited[r][c] = true;

                }

            }

            flip += 1;


        }

        return flip;
    }

    private void dfsGrid(int r, int c, int[][] grid, boolean[][] visited, Queue<int[]> queue) {
        if (r < 0 || r >= grid.length || c < 0 || c >= grid.length || grid[r][c] == 0 || visited[r][c])
            return;

        visited[r][c] = true;
        queue.add(new int[]{r, c});

        dfsGrid(r - 1, c, grid, visited, queue);
        dfsGrid(r + 1, c, grid, visited, queue);
        dfsGrid(r, c - 1, grid, visited, queue);
        dfsGrid(r, c + 1, grid, visited, queue);
    }

    // Leetcode problem: 802

    /**
     * A node is a terminal node if there are no outgoing edges.
     * A node is a safe node if every possible path starting from that node leads to a terminal node.
     * */
    public List<Integer> eventualSafeNodes(int[][] graph) {
        Map<Integer, Boolean> isSafe = new HashMap<>();

        List<Integer> safeNodes = new ArrayList<>();
        for (int i = 0; i < graph.length; i++) {
            if (dfsSafeNode(i, graph, isSafe)) {
                safeNodes.add(i);
            }
        }

        return safeNodes;
    }

    private boolean dfsSafeNode(int node, int[][] graph, Map<Integer, Boolean> isSafe) {

        // If node is already visited then return
        if (isSafe.containsKey(node)) {
            return isSafe.get(node);
        }

        // Initially set it false
        isSafe.put(node, false);

        for (int neighbor : graph[node]) {
            // If any of the neighbors is not safe then the node is not safe
            if (!dfsSafeNode(neighbor, graph, isSafe)) {
                return false;
            }
        }

        // If all the neighbors are safe then the node is safe
        isSafe.replace(node, true);
        return true;
    }

    // Leetcode problem: 207
    public boolean canFinish(int numCourses, int[][] prerequisites) {

        // Build up a map for course and dependencies for faster access
        Map<Integer, List<Integer>> courseMap = new HashMap<>();
        for (int i = 0; i < numCourses; i++) {
            courseMap.put(i, new ArrayList<>());
        }
        for (int[] preRequisite : prerequisites) {
            courseMap.get(preRequisite[0]).add(preRequisite[1]);
        }

        Set<Integer> visited = new HashSet<>();

        for (int i = 0; i < numCourses; i++) {
            if (!dfsCourse(i, courseMap, visited)) {
                return false;
            }
        }

        return true;
    }

    private boolean dfsCourse(int course, Map<Integer, List<Integer>> courseMap, Set<Integer> visited) {

        // If a loop is created then return false.
        if (visited.contains(course)) {
            return false;
        }

        // If there is no dependency for this course return true.
        if (courseMap.get(course).isEmpty()) {
            return true;
        }

        visited.add(course);
        // Check all the dependencies.
        for (int preReq : courseMap.get(course)) {
            if (!dfsCourse(preReq, courseMap, visited)) {
                return false;
            }
        }

        // Once calculated for this course, prevent further calculation.
        visited.remove(course);
        courseMap.get(course).clear();
        return true;
    }

    // Leetcode problem: 210
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        // Build up a map for course and dependencies for faster access
        Map<Integer, List<Integer>> courseMap = new HashMap<>();
        for (int i = 0; i < numCourses; i++) {
            courseMap.put(i, new ArrayList<>());
        }
        for (int[] preRequisite : prerequisites) {
            courseMap.get(preRequisite[0]).add(preRequisite[1]);
        }

        Set<Integer> visited = new HashSet<>();
        Set<Integer> cycle = new HashSet<>();
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < numCourses; i++) {
            if (!dfsCourse(i, courseMap, visited, cycle, result)) {
                return new int[0];
            }
        }

        return result.stream().mapToInt(i -> i).toArray();
    }

    private boolean dfsCourse(int course, Map<Integer, List<Integer>> courseMap, Set<Integer> visited, Set<Integer> cycle, List<Integer> result) {

        // If a loop is created then return false.
        if (cycle.contains(course)) {
            return false;
        }

        // If there is no dependency for this course return true.
        if (visited.contains(course)) {
            return true;
        }

        cycle.add(course);
        // Check all the dependencies.
        for (int preReq : courseMap.get(course)) {
            if (!dfsCourse(preReq, courseMap, visited, cycle, result)) {
                return false;
            }
        }

        // Once calculated for this course, prevent further calculation.
        cycle.remove(course);
        visited.add(course);
        result.add(course);
        return true;
    }

    // Leetcode problem: 417
    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        Set<Pair> pacificIsland = new HashSet<>();
        Set<Pair> atlanticIsland = new HashSet<>();

        int ROW = heights.length;
        int COL = heights[0].length;

        for (int c = 0; c < COL; c++) {
            dfsWaterFlow(0, c, heights, pacificIsland, heights[0][c]);
            dfsWaterFlow(ROW - 1, c, heights, atlanticIsland, heights[ROW - 1][c]);
        }

        for (int r = 0; r < ROW; r++) {
            dfsWaterFlow(r, 0, heights, pacificIsland, heights[r][0]);
            dfsWaterFlow(r, COL - 1, heights, atlanticIsland, heights[r][COL - 1]);
        }

        List<List<Integer>> result = new ArrayList<>();
        for (int r = 0; r < ROW; r++) {
            for (int c = 0; c < COL; c++) {
                if (pacificIsland.contains(new Pair(r, c)) && atlanticIsland.contains(new Pair(r, c))) {
                    result.add(Arrays.asList(r, c));
                }
            }
        }

        return result;
    }

    private void dfsWaterFlow(int row, int col, int[][] height, Set<Pair> visited, int pevHeight) {
        if (row < 0 || col < 0 || row >= height.length || col >= height[0].length || visited.contains(new Pair(row, col))
                || height[row][col] < pevHeight) {
            return;
        }

        visited.add(new Pair(row, col));
        dfsWaterFlow(row + 1, col, height, visited, height[row][col]);
        dfsWaterFlow(row - 1, col, height, visited, height[row][col]);
        dfsWaterFlow(row, col + 1, height, visited, height[row][col]);
        dfsWaterFlow(row, col - 1, height, visited, height[row][col]);
    }

    // Leetcode problem: 463

    /**
     * First determine the island by DFS.
     * Then calculate the perimeter.
     * */
    public int islandPerimeter(int[][] grid) {
        Set<Pair> island = new HashSet<>();

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == 1) {
                    dfsIsland(r, c, grid, island);

                    return calculatePerimeter(grid, island);
                }
            }
        }

        return 0;
    }

    private void dfsIsland(int r, int c, int[][] grid, Set<Pair> visited) {
        if (r < 0 || c < 0 || r >= grid.length || c >= grid[0].length || grid[r][c] == 0
                || visited.contains(new Pair(r, c))) {

            return;
        }

        visited.add(new Pair(r, c));
        dfsIsland(r + 1, c, grid, visited);
        dfsIsland(r - 1, c, grid, visited);
        dfsIsland(r, c + 1, grid, visited);
        dfsIsland(r, c - 1, grid, visited);
    }

    private int calculatePerimeter(int[][] grid, Set<Pair> isLand) {

        int perimeter = 0;
        for (Pair pair : isLand) {
            perimeter += (pair.first == 0 || grid[pair.first - 1][pair.second] == 0) ? 1 : 0; // Upper boundary
            perimeter += (pair.first == grid.length - 1 || grid[pair.first + 1][pair.second] == 0) ? 1 : 0; // Lower boundary
            perimeter += (pair.second == 0 || grid[pair.first][pair.second - 1] == 0) ? 1 : 0; // Left boundary
            perimeter += (pair.second == grid[0].length - 1 || grid[pair.first][pair.second + 1] == 0) ? 1 : 0; // Right boundary
        }

        return perimeter;
    }

    // Leetcode problem: 1905

    /**
     * DFS every island of grid2 & determine whether it is a sub island of grid1.
     * */
    public int countSubIslands(int[][] grid1, int[][] grid2) {

        int count = 0;

        boolean[][] visited = new boolean[grid2.length][grid2[0].length];

        for (int r = 0; r < grid2.length; r++) {
            for (int c = 0; c < grid2[0].length; c++) {
                if (grid2[r][c] == 1 && !visited[r][c] && dfsCountIsland(r, c, grid1, grid2, visited)) {
                    count++;
                }
            }
        }

        return count;
    }

    private boolean dfsCountIsland(int r, int c, int[][] grid1, int[][] grid2, boolean[][] visited) {
        if (r < 0 || c < 0 || r >= grid2.length || c >= grid2[0].length || grid2[r][c] == 0
                || visited[r][c]) {
            return true;
        }

        visited[r][c] = true;
        boolean subIsland = grid1[r][c] != 0;

        // subIsland = subIsland && dfsCountIsland(...) cannot be written.
        // It will take previously saved value of subIsland & give the wrong answer.
        subIsland &= dfsCountIsland(r - 1, c, grid1, grid2, visited);
        subIsland &= dfsCountIsland(r + 1, c, grid1, grid2, visited);
        subIsland &= dfsCountIsland(r, c - 1, grid1, grid2, visited);
        subIsland &= dfsCountIsland(r, c + 1, grid1, grid2, visited);

        return subIsland;
    }
}
