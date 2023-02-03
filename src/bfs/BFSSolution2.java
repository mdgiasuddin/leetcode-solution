package bfs;

import java.util.*;

public class BFSSolution2 {
    public static void main(String[] args) {

    }

    // Leetcode problem: 854
    /*
     * K-Similar Strings
     * Go to every child after swapping a character
     * */
    public int kSimilarity(String s1, String s2) {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(s1);
        visited.add(s1);
        int maxSwap = 0;
        while (!queue.isEmpty()) {
            int queueSize = queue.size();

            while (queueSize-- > 0) {
                String temp = queue.poll();
                if (temp.equals(s2)) {
                    return maxSwap;
                }

                for (String str : getSwappedString(temp, s2)) {
                    if (!visited.contains(str)) {
                        queue.add(str);
                        visited.add(str);
                    }
                }
            }
            maxSwap++;
        }

        return maxSwap;
    }

    List<String> getSwappedString(String str, String target) {
        List<String> result = new ArrayList<>();

        int i = 0;
        // If character of target and str matches no need to swap
        while (str.charAt(i) == target.charAt(i)) i++;

        for (int j = i + 1; j < target.length(); j++) {
            if (str.charAt(j) == target.charAt(i)) {
                result.add(str.substring(0, i) + str.charAt(j) + str.substring(i + 1, j) + str.charAt(i) + str.substring(j + 1));
            }
        }
        return result;
    }

    // Leetcode problem: 286
    // Lintcode problem: 663
    public void wallsAndGates(int[][] rooms) {
        Queue<int[]> queue = new LinkedList<>();
        int m = rooms.length;
        int n = rooms[0].length;
        int infinity = Integer.MAX_VALUE;

        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}
        };

        // Find all the gates. Its distance is 0.
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (rooms[r][c] == 0) {
                    queue.add(new int[]{r, c, 0});
                }
            }
        }

        // Run BFS from each gate & update the distance of each room.
        while (!queue.isEmpty()) {
            int[] room = queue.poll();

            for (int[] direction : directions) {
                int r = room[0] + direction[0];
                int c = room[1] + direction[1];

                if (r >= 0 && r < m && c >= 0 && c < n && rooms[r][c] == infinity) {
                    rooms[r][c] = room[2] + 1;
                    queue.add(new int[]{r, c, room[2] + 1});
                }
            }
        }
    }

    // Leetcode problem: 1162
    /*
     * Maximum distance from land.
     * BFS solution.
     * */
    public int maxDistance(int[][] grid) {
        int n = grid.length;

        Queue<int[]> queue = new LinkedList<>();
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == 1) {
                    queue.add(new int[]{r, c});
                }
            }
        }

        // If all are water or land.
        if (queue.isEmpty() || queue.size() == n * n)
            return -1;

        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}
        };

        int max = -1;
        while (!queue.isEmpty()) {
            int qSize = queue.size();

            while (qSize-- > 0) {
                int[] pos = queue.poll();

                for (int[] direction : directions) {
                    int r = pos[0] + direction[0];
                    int c = pos[1] + direction[1];

                    if (r >= 0 && r < n && c >= 0 && c < n && grid[r][c] == 0) {
                        grid[r][c] = 1;
                        queue.add(new int[]{r, c});
                    }
                }
            }

            max += 1;
        }

        return max;
    }

    // Leetcode problem: 1926
    public int nearestExit(char[][] maze, int[] entrance) {
        int m = maze.length;
        int n = maze[0].length;

        Queue<int[]> queue = new LinkedList<>();
        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}
        };

        maze[entrance[0]][entrance[1]] = '#';
        queue.add(entrance);
        int nearest = 0;

        while (!queue.isEmpty()) {
            int qSize = queue.size();

            while (qSize-- > 0) {
                int[] pos = queue.poll();

                // Exit must not be equal to entrance. So skip this. Consider only nearest > 0.
                if (nearest > 0 && (pos[0] == 0 || pos[0] == m - 1 || pos[1] == 0 || pos[1] == n - 1))
                    return nearest;

                for (int[] direction : directions) {
                    int r = pos[0] + direction[0];
                    int c = pos[1] + direction[1];

                    if (r >= 0 && r < m && c >= 0 && c < n && maze[r][c] == '.') {
                        maze[r][c] = '#';
                        queue.add(new int[]{r, c});
                    }
                }

            }

            nearest += 1;
        }

        return -1;
    }

    // Leetcode problem: 1293
    /*
     * Shortest Path in a Grid with Obstacles Elimination.
     * Track how many obstacles still can be removed.
     * Visited array will be 3 dimensional. Add extra layer of obstacles remove capacity.
     * */
    public int shortestPath(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;

        Queue<int[]> queue = new LinkedList<>();
        boolean[][][] visited = new boolean[m][n][k + 1];
        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}
        };

        queue.add(new int[]{0, 0, k});
        visited[0][0][k] = true;
        int res = 0;

        while (!queue.isEmpty()) {
            int qSize = queue.size();

            while (qSize-- > 0) {
                int[] pos = queue.poll();
                if (pos[0] == m - 1 && pos[1] == n - 1)
                    return res;

                for (int[] direction : directions) {
                    int r = pos[0] + direction[0];
                    int c = pos[1] + direction[1];

                    if (r < 0 || r >= m || c < 0 || c >= n)
                        continue;

                    if (grid[r][c] == 0 && !visited[r][c][pos[2]]) {
                        queue.add(new int[]{r, c, pos[2]});
                        visited[r][c][pos[2]] = true;
                    } else if (grid[r][c] == 1 && pos[2] > 0 && !visited[r][c][pos[2] - 1]) {
                        queue.add(new int[]{r, c, pos[2] - 1});
                        visited[r][c][pos[2] - 1] = true;
                    }
                }
            }

            res += 1;
        }

        return -1;
    }

    // Leetcode problem: 542
    public int[][] updateMatrix(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;

        boolean[][] visited = new boolean[m][n];
        Queue<int[]> queue = new LinkedList<>();

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (mat[r][c] == 0) {
                    queue.add(new int[]{r, c});
                }
            }
        }

        int dist = 1;
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        while (!queue.isEmpty()) {
            int qSize = queue.size();

            while (qSize-- > 0) {
                int[] pos = queue.poll();
                for (int[] dir : directions) {
                    int r = pos[0] + dir[0];
                    int c = pos[1] + dir[1];

                    if (r >= 0 && r < m && c >= 0 && c < n && mat[r][c] != 0 && !visited[r][c]) {
                        mat[r][c] = dist;
                        visited[r][c] = true;
                        queue.add(new int[]{r, c});
                    }
                }
            }

            dist += 1;
        }

        return mat;
    }

    // Leetcode problem: 838
    /*
     * Push Dominoes.
     * */
    public String pushDominoes(String dominoes) {
        Queue<DominoPair> queue = new ArrayDeque<>();
        char[] dominoArray = dominoes.toCharArray();
        int n = dominoes.length();

        for (int i = 0; i < n; i++) {
            char ch = dominoes.charAt(i);

            if (ch != '.') {
                queue.add(new DominoPair(ch, i));
            }
        }

        while (!queue.isEmpty()) {
            DominoPair p = queue.poll();

            if (p.domino == 'L' && p.index > 0 && dominoArray[p.index - 1] == '.') {
                // Don't need to check previous 'R'. If 'R' exists then 'L' will already minimized.
                dominoArray[p.index - 1] = 'L';
                queue.add(new DominoPair('L', p.index - 1));
            } else if (p.domino == 'R') {
                if (p.index + 1 < n && dominoArray[p.index + 1] == '.') {
                    if (p.index + 2 < n && dominoArray[p.index + 2] == 'L') {
                        // The effect or 'R' is minimized by 'L'.
                        queue.poll();
                    } else {
                        dominoArray[p.index + 1] = 'R';
                        queue.add(new DominoPair('R', p.index + 1));
                    }
                }
            }
        }

        return String.valueOf(dominoArray);
    }

    // Leetcode problem: 834
    /*
     * Sum of Distances in Tree.
     * Explanation: https://www.youtube.com/watch?v=T81Bpx2OmS4
     * */
    public int[] sumOfDistancesInTree(int n, int[][] edges) {

        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        int[] parents = new int[n];
        int[] subtree = new int[n];
        Arrays.fill(parents, -1);

        int[] res = new int[n];
        dfsNode(0, -1, graph, parents, subtree, res, 0);

        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);
        while (!queue.isEmpty()) {
            int node = queue.poll();

            for (int child : graph.get(node)) {
                if (parents[child] == node) {
                    queue.add(child);
                    // This formula is important.
                    res[child] = res[node] - 2 * subtree[child] + n;
                }
            }
        }

        return res;

    }

    /*
     * Calculate distance for the root node (0) & number of nodes rooted at all nodes.
     * */
    private int dfsNode(int node, int parent, List<List<Integer>> graph, int[] parents, int[] subtree, int[] res, int depth) {
        int total = 1;
        res[0] += depth;
        for (int child : graph.get(node)) {
            if (child != parent) {
                parents[child] = node;
                total += dfsNode(child, node, graph, parents, subtree, res, depth + 1);
            }
        }

        subtree[node] = total;
        return total;
    }

    // Leetcode problem: 1091
    /*
     * Shortest Path in Binary Matrix.
     * */
    public int shortestPathBinaryMatrix(int[][] grid) {
        int n = grid.length;
        if (grid[0][0] == 1 || grid[n - 1][n - 1] == 1) {
            return -1;
        }

        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };

        boolean[][] visited = new boolean[n][n];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{0, 0});
        visited[0][0] = true;
        int pathLen = 1;

        while (!queue.isEmpty()) {
            int qSize = queue.size();

            while (qSize-- > 0) {
                int[] xy = queue.poll();
                if (xy[0] == n - 1 && xy[1] == n - 1) {
                    return pathLen;
                }

                for (int[] direction : directions) {
                    int x = xy[0] + direction[0];
                    int y = xy[1] + direction[1];

                    if (x >= 0 && x < n && y >= 0 && y < n && !visited[x][y] && grid[x][y] == 0) {
                        queue.add(new int[]{x, y});
                        visited[x][y] = true;
                    }
                }
            }

            pathLen += 1;
        }

        return -1;

    }
}

class DominoPair {
    char domino;
    int index;

    public DominoPair(char domino, int index) {
        this.domino = domino;
        this.index = index;
    }
}
