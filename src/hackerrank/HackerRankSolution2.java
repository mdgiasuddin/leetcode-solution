package hackerrank;

import java.util.*;

public class HackerRankSolution2 {

    public static void main(String[] args) {
    }

    /*
     * https://www.hackerrank.com/challenges/equal-stacks/problem?isFullScreen=true
     * */
    public static int equalStacks(List<Integer> h1, List<Integer> h2, List<Integer> h3) {

        int totalHeight1 = 0;
        int totalHeight2 = 0;
        int totalHeight3 = 0;

        for (int height1 : h1) {
            totalHeight1 += height1;
        }

        for (int height2 : h2) {
            totalHeight2 += height2;
        }

        for (int height3 : h3) {
            totalHeight3 += height3;
        }

        int i = 0;
        int j = 0;
        int k = 0;

        while (i < h1.size() && j < h2.size() && k < h3.size()) {
            if (totalHeight1 == totalHeight2 && totalHeight1 == totalHeight3) {
                return totalHeight3;
            }

            if (totalHeight1 >= totalHeight2 && totalHeight1 >= totalHeight3) {
                totalHeight1 -= h1.get(i);
                i += 1;
            } else if (totalHeight2 >= totalHeight1 && totalHeight2 >= totalHeight3) {
                totalHeight2 -= h2.get(j);
                j += 1;
            } else {
                totalHeight3 -= h3.get(k);
                k += 1;
            }

        }

        return 0;
    }

    /*
     * Hacker Rank: https://www.hackerrank.com/challenges/castle-on-the-grid/problem?h_r=internal-search
     * Explanation: https://www.youtube.com/watch?v=oL7Hpzoo1CA&list=PL_8jNcohs27XFAHGaWEf90O-cCcOfF8Kd&index=9
     * */
    public static int minimumMoves(List<String> grid, int startX, int startY, int goalX, int goalY) {
        int n = grid.size();
        boolean[][] visited = new boolean[n][n];

        Queue<int[]> queue = new LinkedList<>();
        int[][] directions = {
                {0, -1}, {0, 1}, {-1, 0}, {1, 0}
        };

        queue.add(new int[]{startX, startY});
        visited[startX][startY] = true;

        int moves = 0;
        while (!queue.isEmpty()) {
            int qSize = queue.size();

            while (qSize-- > 0) {
                int[] posXY = queue.poll();
                if (posXY[0] == goalX && posXY[1] == goalY) {
                    return moves;
                }

                for (int[] direction : directions) {
                    int x = posXY[0] + direction[0];
                    int y = posXY[1] + direction[1];

                    while (x >= 0 && x < n && y >= 0 && y < n && grid.get(x).charAt(y) == '.') {
                        if (!visited[x][y]) {
                            visited[x][y] = true;
                            queue.add(new int[]{x, y});
                        }

                        x += direction[0];
                        y += direction[1];
                    }

                }

            }

            moves += 1;
        }

        return -1;
    }

    /*
     * Hacker Rank: https://www.hackerrank.com/challenges/poisonous-plants/problem?h_r=internal-search
     * Explanation: https://www.youtube.com/watch?v=QKkShXV-2cY&list=PL_8jNcohs27XFAHGaWEf90O-cCcOfF8Kd&index=11
     * */
    public static int poisonousPlants(List<Integer> p) {
        int maxDay = 0;
        Stack<int[]> stack = new Stack<>();

        for (int ps : p) {
            int day = 0;

            // All the greater left values will be ignored.
            while (!stack.isEmpty() && stack.peek()[0] >= ps) {
                day = Math.max(day, stack.pop()[1]);
            }

            // If any other less value left, it will take 1 more day to die.
            // Otherwise, it will be reset.
            if (stack.isEmpty()) {
                day = 0;
            } else {
                day += 1;
            }

            maxDay = Math.max(maxDay, day);
            stack.push(new int[]{ps, day});
        }

        return maxDay;

    }

    /*
     * Hacker Rank: https://www.hackerrank.com/challenges/icecream-parlor/problem?h_r=internal-search
     * 2-sum problem.
     * */
    public static List<Integer> icecreamParlor(int m, List<Integer> arr) {

        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < arr.size(); i++) {
            if (map.containsKey(m - arr.get(i))) {
                return Arrays.asList(map.get(m - arr.get(i)), i + 1);
            }

            map.put(arr.get(i), i + 1);
        }

        return new ArrayList<>();

    }

    /*
     * Hacker Rank: https://www.hackerrank.com/challenges/pairs/problem?h_r=internal-search
     * Variation of 2-sum problem.
     * */
    public static int pairs(int k, List<Integer> arr) {
        Map<Integer, Integer> countMap = new HashMap<>();

        for (int a : arr) {
            int count = countMap.getOrDefault(a, 0);
            countMap.put(a, count + 1);
        }

        int res = 0;
        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            if (entry.getKey() > k && countMap.containsKey(entry.getKey() - k)) {
                res += entry.getValue() * countMap.get(entry.getKey() - k);
            }
        }

        return res;

    }

    public static List<Integer> rotateLeft(int d, List<Integer> arr) {
        List<Integer> newList = arr.subList(d, arr.size());
        newList.addAll(arr.subList(0, d));

        return newList;
    }

    /*
     * Hacker Rank: https://www.hackerrank.com/challenges/sherlock-and-cost/problem?h_r=internal-search
     * Explanation: https://www.youtube.com/watch?v=coH3k4DhLCM
     * */
    public static int cost(List<Integer> B) {
        int prev1 = 0;
        int prev2 = 0;
        int max1 = 0;
        int max2 = 0;

        for (int i = 1; i < B.size(); i++) {
            // max1 indicates B[i] is selected.
            max1 = Math.max(prev1 + Math.abs(B.get(i) - B.get(i - 1))
                    , prev2 + Math.abs(1 - B.get(i)));
            // 1 is selected. => Math.max(prev1 + Math.abs(1 - B.get(i - 1)), prev2 + (1 - 1))
            max2 = prev1 + Math.abs(1 - B.get(i - 1));

            prev1 = max1;
            prev2 = max2;
        }

        return Math.max(max1, max2);

    }

    /*
     * Hacker Rank: https://www.hackerrank.com/challenges/sam-and-substrings/problem?h_r=internal-search
     * Explanation: https://www.youtube.com/watch?v=1azkxwT_WZc
     * */
    public static int substrings(String n) {
        long prev = 0;
        long mod = 1000000007L;
        long total = 0;

        for (int i = 0; i < n.length(); i++) {
            int digit = n.charAt(i) - '0';

            /*
             * "123"
             * prev = 0 * 10 + 1 * 1 = 1 total = 1 ("1")
             * prev = 1 * 10 + 2 * 2 = 14 (2 * 2 => 12 & 2) total = 15 ("12")
             * prev = 14 * 10 + 3 * 3 = 149 (3 * 3 => 123, 23 & 3) total = 149
             * */
            prev = (prev * 10 + (long) digit * (i + 1)) % mod;
            total = (total + prev) % mod;
        }

        return (int) total;

    }

    /*
     * https://www.hackerrank.com/challenges/abbr/problem?h_r=internal-search
     * Dynamic Programming.
     * */
    public static String abbreviation(String a, String b) {
        int m = a.length();
        int n = b.length();
        boolean[][] dp = new boolean[m + 1][n + 1];

        dp[0][0] = true;
        for (int r = 1; r <= m; r++) {
            dp[r][0] = dp[r - 1][0] && Character.isLowerCase(a.charAt(r - 1));
        }

        for (int r = 1; r <= m; r++) {
            for (int c = 1; c <= n; c++) {
                // If lowercase then try to remove it.
                if (Character.isLowerCase(a.charAt(r - 1))) {
                    dp[r][c] = dp[r - 1][c];
                }

                // If 2 characters are equal, try to match other parts.
                if (Character.toUpperCase(a.charAt(r - 1)) == b.charAt(c - 1)) {
                    dp[r][c] |= dp[r - 1][c - 1];
                }
            }
        }

        return dp[m][n] ? "YES" : "NO";
    }
}
