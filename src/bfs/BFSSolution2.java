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
}
