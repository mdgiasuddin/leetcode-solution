package dp;

import pair.Pair;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class RecursiveDP3 {

    // Leetcode problem: 87
    /*
     * Scramble String.
     * Explanation: https://www.youtube.com/watch?v=MDmZm_aVDF8
     * */
    public boolean isScramble(String s1, String s2) {
        return isScramble(s1, s2, new HashMap<>());
    }

    private boolean isScramble(String s1, String s2, Map<String, Boolean> map) {
        if (s1.length() == 1) {
            return s1.equals(s2);
        }
        if (s1.equals(s2)) {
            return true;
        }

        String key = s1 + ":" + s2;
        if (map.containsKey(key)) {
            return map.get(key);
        }

        for (int i = 1; i < s1.length(); i++) {
            if (
                    (isScramble(s1.substring(0, i), s2.substring(0, i), map) &&
                            isScramble(s1.substring(i), s2.substring(i), map)) ||
                            (isScramble(s1.substring(0, i), s2.substring(s2.length() - i), map) &&
                                    isScramble(s1.substring(i), s2.substring(0, s2.length() - i), map))
            ) {
                map.put(key, true);
                return true;
            }
        }

        map.put(key, false);
        return false;
    }

    // Leetcode problem: 1048
    /*
     * Longest String Chain.
     * Explanation: https://www.youtube.com/watch?v=7b0V1gT_TIk
     * */
    public int longestStrChain(String[] words) {
        Arrays.sort(words, Comparator.comparingInt(String::length).reversed());
        Map<String, Integer> idxMap = new HashMap<>();
        int n = words.length;
        for (int i = 0; i < n; i++) {
            idxMap.put(words[i], i);
        }
        Map<Integer, Integer> dp = new HashMap<>();
        int res = 0;
        for (int i = 0; i < n; i++) {
            res = Math.max(res, dfsWordChain(i, words, idxMap, dp));
        }
        return res;
    }

    private int dfsWordChain(int idx, String[] words, Map<String, Integer> idxMap, Map<Integer, Integer> dp) {
        if (dp.containsKey(idx)) {
            return dp.get(idx);
        }

        int res = 1;
        for (int i = 0; i < words[idx].length(); i++) {
            String substr = words[idx].substring(0, i) + words[idx].substring(i + 1);
            if (idxMap.containsKey(substr)) {
                res = Math.max(res, 1 + dfsWordChain(idxMap.get(substr), words, idxMap, dp));
            }
        }
        dp.put(idx, res);
        return res;
    }

    // Leetcode problem: 1130
    /*
     * Minimum Cost Tree From Leaf Values.
     * Explanation: https://www.youtube.com/watch?v=LDiD9fr28tc
     * TO-DO => Try to solve by bottom-up approach.
     * */
    public int mctFromLeafValues(int[] arr) {
        int n = arr.length;
        Map<Pair, Integer> maxIJ = new HashMap<>();
        for (int i = 0; i < n; i++) {
            maxIJ.put(new Pair(i, i), arr[i]);
            for (int j = i + 1; j < n; j++) {
                maxIJ.put(new Pair(i, j), Math.max(arr[j], maxIJ.get(new Pair(i, j - 1))));
            }
        }
        return mctFromLeafValues(arr, 0, arr.length - 1, new HashMap<>(), maxIJ);
    }

    private int mctFromLeafValues(int[] arr, int left, int right, Map<Pair, Integer> dp, Map<Pair, Integer> maxIJ) {
        if (left == right) {
            return 0;
        }

        Pair key = new Pair(left, right);
        if (dp.containsKey(key)) {
            return dp.get(key);
        }

        int res = Integer.MAX_VALUE;
        for (int i = left; i < right; i++) {
            res = Math.min(
                    res,
                    maxIJ.get(new Pair(left, i)) * maxIJ.get(new Pair(i + 1, right)) +
                            mctFromLeafValues(arr, left, i, dp, maxIJ) + mctFromLeafValues(arr, i + 1, right, dp, maxIJ)
            );
        }

        dp.put(key, res);
        return res;
    }

    // Leetcode problem: 1155
    /*
     * Number of Dice Rolls with Target Sum.
     * Explanation: https://www.youtube.com/watch?v=JfRxkDOP7-4
     * */
    public int numRollsToTarget(int n, int k, int target) {
        int[][] dp = new int[n + 1][target + 1];
        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }

        return dfsDice(n, k, target, dp);
    }

    private int dfsDice(int n, int k, int target, int[][] dp) {
        if (n < 0) {
            return 0;
        }
        if (n == 0 && target == 0) {
            return 1;
        }
        if (dp[n][target] != -1) {
            return dp[n][target];
        }

        int ways = 0;
        for (int i = 1; i <= Math.min(k, target); i++) { // Try with all possible options of nth Dice.
            ways = (ways + dfsDice(n - 1, k, target - i, dp)) % 1000000007;
        }
        dp[n][target] = ways;
        return ways;
    }

    // Leetcode problem: 1665
    /*
     * Cherry Pickup II.
     * Explanation: https://www.youtube.com/watch?v=c1stwk2TbNk
     * */
    public int cherryPickup(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][][] dp = new int[rows][cols][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for (int k = 0; k < cols; k++) {
                    dp[i][j][k] = -1;
                }
            }
        }

        return dfsCherryPickup(0, 0, cols - 1, grid, dp);

    }

    private int dfsCherryPickup(int r, int c1, int c2, int[][] grid, int[][][] dp) {
        // We will avoid same column, it can never return maximum value.
        if (c1 == c2 || c1 < 0 || c2 < 0 || c1 >= grid[0].length || c2 >= grid[0].length) {
            return 0;
        }

        if (r == grid.length - 1) {
            return grid[r][c1] + grid[r][c2];
        }

        if (dp[r][c1][c2] != -1) {
            return dp[r][c1][c2];
        }

        int cherries = 0;
        for (int dc1 = -1; dc1 <= 1; dc1++) {
            for (int dc2 = -1; dc2 <= 1; dc2++) {
                cherries = Math.max(cherries, dfsCherryPickup(r + 1, c1 + dc1, c2 + dc2, grid, dp));
            }
        }
        cherries += grid[r][c1] + grid[r][c2];
        dp[r][c1][c2] = cherries;
        return cherries;
    }
}
