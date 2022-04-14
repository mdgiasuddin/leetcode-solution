package indefinite;

import pair.Pair;

import java.util.*;

public class FifthSolution {

    public int minJumps(int[] arr) {
        if (arr.length == 1)
            return 0;

        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (j == i + 1 || arr[j] == arr[i]) {
                    graph.get(i).add(j);
                    graph.get(j).add(i);
                }
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[arr.length];
        int[] distance = new int[arr.length];
        queue.add(0);
        visited[0] = true;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v : graph.get(u)) {
                if (!visited[v]) {
                    queue.add(v);
                    distance[v] = 1 + distance[u];
                    visited[v] = true;
                    if (v == arr.length - 1)
                        return distance[v];
                }
            }
        }

        return distance[distance.length - 1];
    }

    public int hIndex2(int[] citations) {
        int left = 0, n = citations.length, right = n - 1, ans = 0;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (citations[mid] < n - mid) {
                left = mid + 1;
            } else {
                ans = n - mid;
                right = mid - 1;
            }
        }

        return ans;
    }

    private boolean solveNQueen(int[][] board, int n, int row) {
        if (row >= n)
            return true;

        for (int i = 0; i < n; i++) {
            if (isQueenSafe(board, n, row, i)) {
                board[row][i] = 1;
                if (solveNQueen(board, n, row + 1))
                    return true;
                board[row][i] = 0;
            }
        }
        return false;
    }

    private boolean isQueenSafe(int[][] board, int n, int row, int col) {
        // check col
        for (int i = row - 1; i >= 0; i--) {
            if (board[i][col] == 1)
                return false;
        }
        // check lower lef diagonal

        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1)
                return false;
        }
        // check lower right diagonal
        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
            if (board[i][j] == 1)
                return false;
        }

        return true;

    }

    public void solveNQueen(int n) {
        int[][] board = new int[n][n];
        board[0][0] = 1;

        if (solveNQueen(board, n, 0)) {
            UtilClass.print2DArray(board);
        }
    }

    private boolean nightsTour(int[][] board, int[][] move, int n, int curX, int curY, int currentMove) {
//        System.out.println("Current Move: " + currentMove);
        if (currentMove >= n * n)
            return true;

        for (int i = 0; i < 8; i++) {
            int nextX = curX + move[0][i];
            int nextY = curY + move[1][i];

            if (isValidNightMove(board, n, nextX, nextY)) {
                board[nextX][nextY] = currentMove;
//                UtilClass.print2DArray(board);
                if (nightsTour(board, move, n, nextX, nextY, 1 + currentMove))
                    return true;
                board[nextX][nextY] = -1;
            }
        }

        return false;

    }

    private boolean isValidNightMove(int[][] board, int n, int row, int col) {
        return row >= 0 && row < n && col >= 0 && col < n && board[row][col] == -1;
    }

    public void nightsTour(int n) {
        int[][] board = new int[n][n];
        int[][] move = new int[][]{
                {-2, -2, -1, -1, 2, 2, 1, 1},
                {-1, 1, -2, 2, -1, -1, -2, 2}
        };
        for (int[] boardRow : board) {
            Arrays.fill(boardRow, -1);
        }
        board[0][0] = 0;

        if (nightsTour(board, move, n, 0, 0, 1)) {
            UtilClass.print2DArray(board);
        }
    }

    private boolean rateMaze(int[][] maze, int m, int n, int row, int col) {
        if (row >= m - 1 && col >= n - 1) {
            return true;
        }

        if (row + 1 < m && maze[row + 1][col] == 1) {
            maze[row + 1][col] = 2;
            if (rateMaze(maze, m, n, row + 1, col))
                return true;
            maze[row + 1][col] = 1;
        }
        if (col + 1 < n && maze[row][col + 1] == 1) {
            maze[row][col + 1] = 2;
            if (rateMaze(maze, m, n, row, col + 1))
                return true;
            maze[row][col + 1] = 1;
        }

        return false;
    }

    public void rateMaze() {
        int[][] maze = {
                {1, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 0, 1, 0},
                {1, 1, 1, 1}
        };
        if (maze[0][0] == 0)
            return;

        int m = maze.length, n = maze[0].length;
        maze[0][0] = 2;
        if (rateMaze(maze, m, n, 0, 0)) {
            UtilClass.print2DArray(maze);
        }

    }

    private void combine(int n, int k, List<List<Integer>> result, List<Integer> currentList, int current) {
        if (currentList.size() >= k) {
            result.add(new ArrayList<>(currentList));
            return;
        }
        for (int i = current; i <= n; i++) {
            if (n - i + 1 < k - currentList.size()) {
                break;
            }
            currentList.add(i);
            combine(n, k, result, currentList, i + 1);
            currentList.remove(currentList.size() - 1);
        }

    }

    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        combine(n, k, result, new ArrayList<>(), 1);

        return result;
    }

    private void combinationSum3(int k, int n, List<List<Integer>> result, List<Integer> currentList, int currentSum, int current) {
        if (currentList.size() >= k && currentSum == n) {
            result.add(new ArrayList<>(currentList));
            return;
        }
        if (currentList.size() >= k || currentSum >= n) {
            return;
        }

        for (int i = current; i <= 9; i++) {
            if (10 - i < k - currentList.size() || currentSum + i > n) {
                break;
            }
            currentList.add(i);
            combinationSum3(k, n, result, currentList, currentSum + i, i + 1);
            currentList.remove(currentList.size() - 1);
        }

    }

    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> result = new ArrayList<>();
        combinationSum3(k, n, result, new ArrayList<>(), 0, 1);

        return result;
    }

    private void generateParenthesis(int n, List<String> result, String current, int open, int close) {
        if (open + close == 2 * n) {
            result.add(current);
            return;
        }
        if (open < n)
            generateParenthesis(n, result, current + "(", open + 1, close);
        if (close < open)
            generateParenthesis(n, result, current + ")", open, close + 1);
    }

    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        generateParenthesis(n, result, "(", 1, 0);

        return result;
    }

    private void subsets(int[] nums, List<List<Integer>> result, List<Integer> currentList, int current) {
        result.add(new ArrayList<>(currentList));

        for (int i = current; i < nums.length; i++) {
            currentList.add(nums[i]);
            subsets(nums, result, currentList, i + 1);
            currentList.remove(currentList.size() - 1);
        }
    }

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        subsets(nums, result, new ArrayList<>(), 0);

        return result;
    }

    private void subsetsWithDup(int[] nums, List<List<Integer>> result, List<Integer> currentList, int current, Integer prev) {
        result.add(new ArrayList<>(currentList));
        for (int i = current; i < nums.length; i++) {
            if (nums[i] == prev)
                continue;
            currentList.add(nums[i]);
            prev = -20;
            subsetsWithDup(nums, result, currentList, i + 1, prev);
            prev = currentList.get(currentList.size() - 1);
            currentList.remove(currentList.size() - 1);
        }
    }

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        subsetsWithDup(nums, result, new ArrayList<>(), 0, -20);

        return result;
    }

    public boolean PredictTheWinner(int[] nums) {
        Pair[][] dp = new Pair[nums.length][nums.length];
        for (int i = 0; i < nums.length; i++) {
            dp[i][i] = new Pair(nums[i], 0);
        }
        for (int i = 0; i < nums.length - 1; i++) {
            dp[i][i + 1] = new Pair(Math.max(nums[i], nums[i + 1]), Math.min(nums[i], nums[i + 1]));
        }

        int i = 0, j = 2, initJ = j;
        while (!(i == 0 && j == nums.length - 1)) {
            int first, second;
            System.out.println(i + " i, j " + j);
            if (nums[i] + dp[i + 1][j].second > nums[j] + dp[i][j - 1].second) {
                first = nums[i] + dp[i + 1][j].second;
                second = dp[i + 1][j].first;
            } else {
                first = nums[j] + dp[i][j - 1].second;
                second = dp[i][j - 1].first;
            }
            dp[i][j] = new Pair(first, second);
            i++;
            j++;
            if (j == nums.length) {
                i=0;
                j = ++initJ;
            }
        }

        return dp[0][nums.length - 1].first >= dp[0][nums.length - 1].second;
    }

}
