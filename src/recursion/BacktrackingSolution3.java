package recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BacktrackingSolution3 {

    public static void main(String[] args) {
        BacktrackingSolution3 backtrackingSolution3 = new BacktrackingSolution3();
    }

    // Leetcode problem: 980
    public int uniquePathsIII(int[][] grid) {
        int M = grid.length, N = grid[0].length;

        int sx, sy;
        int zeroCells = 0;

        sx = sy = -1;

        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                if (grid[r][c] == 0) {
                    zeroCells++;
                } else if (grid[r][c] == 1) {
                    sx = r;
                    sy = c;
                }
            }
        }

        boolean[][] visited = new boolean[M][N];

        // zeroCells + 1 (+1 for starting cell).
        return dfsPath(sx, sy, grid, 0, zeroCells + 1, visited);
    }

    private int dfsPath(int r, int c, int[][] grid, int coveredCells, int zeroCells, boolean[][] visited) {
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length || visited[r][c] || grid[r][c] == -1
                || (grid[r][c] == 2 && coveredCells != zeroCells))
            return 0;

        if (grid[r][c] == 2) {
            return 1;
        }

        visited[r][c] = true;
        int res = 0;
        res += dfsPath(r + 1, c, grid, coveredCells + 1, zeroCells, visited);
        res += dfsPath(r - 1, c, grid, coveredCells + 1, zeroCells, visited);
        res += dfsPath(r, c + 1, grid, coveredCells + 1, zeroCells, visited);
        res += dfsPath(r, c - 1, grid, coveredCells + 1, zeroCells, visited);

        // Backtrack.
        visited[r][c] = false;

        return res;
    }

    // Leetcode problem: 842
    public List<Integer> splitIntoFibonacci(String num) {
        List<List<Integer>> result = new ArrayList<>();

        splitIntoFibonacci(num, 0, result, new ArrayList<>());

        return result.isEmpty() ? new ArrayList<>() : result.get(0);
    }

    public void splitIntoFibonacci(String num, int idx, List<List<Integer>> result, List<Integer> current) {

        // We need any 1 fibonacci combination. So, if 1 already found no need to go further.
        if (!result.isEmpty())
            return;

        // Check if satisfies fibonacci condition.
        if (current.size() > 2) {
            if (current.get(current.size() - 1) != current.get(current.size() - 2) + current.get(current.size() - 3))
                return;
        }

        if (idx == num.length()) {

            // All characters visited but haven't found 3 numbers, then don't add.
            if (current.size() > 2)
                result.add(new ArrayList<>(current));

            return;
        }

        int currentNum = 0;
        for (int i = idx; i < num.length(); i++) {

            // Avoid leading 0.
            if (num.charAt(idx) == '0' && i > idx)
                return;

            currentNum = currentNum * 10 + num.charAt(i) - '0';

            // Integer overflow.
            if (currentNum < 0)
                return;

            current.add(currentNum);
            splitIntoFibonacci(num, i + 1, result, current);
            current.remove(current.size() - 1);
        }
    }

    // Leetcode problem: 1049

    /**
     * Last Stone Weight II.
     * Explanation: https://www.youtube.com/watch?v=gdXkkmzvR3c
     * */
    public int lastStoneWeightII(int[] stones) {
        int total = 0;
        for (int stone : stones) {
            total += stone;
        }

        return lastStoneWeightII(stones, 0, 0, (total + 1) / 2, total, new HashMap<>());
    }

    public int lastStoneWeightII(int[] stones, int idx, int current, int target, int total, Map<List<Integer>, Integer> map) {
        if (current >= target || idx >= stones.length) {
            return Math.abs(current - (total - current));
        }

        List<Integer> list = Arrays.asList(idx, current);
        if (map.containsKey(list)) {
            return map.get(list);
        }

        int res = Math.min(lastStoneWeightII(stones, idx + 1, current + stones[idx], target, total, map),
                lastStoneWeightII(stones, idx + 1, current, target, total, map));
        map.put(list, res);
        return res;
    }

    // Leetcode problem: 282

    /**
     * Expression Add Operators.
     * Explanation: https://www.youtube.com/watch?v=WcgjFrZceU8
     * */
    public List<String> addOperators(String num, int target) {
        List<String> result = new ArrayList<>();
        dfsAddOperators(0, num, "", 0, 0, target, result);
        return result;
    }

    private void dfsAddOperators(int idx, String num, String path, long sumSoFar, long prev, int target, List<String> result) {
        if (idx == num.length()) {
            if (sumSoFar == target) {
                result.add(path);
            }
            return;
        }

        for (int i = idx; i < num.length(); i++) {
            if (i > idx && num.charAt(idx) == '0') {
                return;
            }
            String curStr = num.substring(idx, i + 1);
            long current = Long.parseLong(curStr);
            if (idx == 0) {
                dfsAddOperators(i + 1, num, curStr, current, current, target, result);
            } else {
                dfsAddOperators(i + 1, num, path + "+" + curStr, sumSoFar + current, current, target, result);
                dfsAddOperators(i + 1, num, path + "-" + curStr, sumSoFar - current, -current, target, result);
                dfsAddOperators(i + 1, num, path + "*" + curStr, sumSoFar - prev + prev * current, prev * current, target, result);
            }
        }
    }
}
