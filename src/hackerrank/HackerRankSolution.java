package hackerrank;

import java.util.*;

public class HackerRankSolution {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(
                278, 576, 496, 727, 410, 124, 338, 149, 209, 702, 282, 718, 771, 575, 436
        );

        System.out.println(nonDivisibleSubset(7, list));
    }

    /*
     * Hacker Rank: https://www.hackerrank.com/challenges/non-divisible-subset/problem?isFullScreen=true
     * Explanation: https://www.geeksforgeeks.org/subset-no-pair-sum-divisible-k/
     * */
    public static int nonDivisibleSubset(int k, List<Integer> s) {
        int[] count = new int[k];

        for (int num : s) {
            count[num % k] += 1;
        }

        if (k % 2 == 0) {
            count[k / 2] = Math.min(count[k / 2], 1);
        }

        int res = Math.min(count[0], 1);
        for (int i = 1; i <= k / 2; i++) {
            res += Math.max(count[i], count[k - i]);
        }

        return res;

    }

    /*
     * Hacker Rank: https://www.hackerrank.com/challenges/queens-attack-2/problem?isFullScreen=true
     * */
    public static int queensAttack(int n, int k, int r_q, int c_q, List<List<Integer>> obstacles) {

        Set<List<Integer>> obstaclesSet = new HashSet<>(obstacles);
        int[][] directions = {
                {1, 0}, {-1, 0}, {0, -1}, {0, 1}, {1, -1}, {1, 1}, {-1, -1}, {-1, 1}
        };

        int res = 0;
        for (int[] dir : directions) {
            int r = r_q + dir[0];
            int c = c_q + dir[1];

            while (r >= 1 && r <= n && c >= 1 && c <= n && !obstaclesSet.contains(Arrays.asList(r, c))) {
                res += 1;
                r += dir[0];
                c += dir[1];
            }
        }

        return res;

    }

    /*
     * Hacker Rank: https://www.hackerrank.com/challenges/flatland-space-stations/problem?isFullScreen=true
     * Multi-source BFS
     * */
    static int flatlandSpaceStations(int n, int[] c) {
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        for (int st : c) {
            visited[st] = true;
            queue.add(st);
        }

        int distance = -1;
        while (!queue.isEmpty()) {
            int qSize = queue.size();

            while (qSize-- > 0) {
                int node = queue.poll();
                if (node - 1 >= 0 && !visited[node - 1]) {
                    visited[node - 1] = true;
                    queue.add(node - 1);
                }

                if (node + 1 < n && !visited[node + 1]) {
                    visited[node + 1] = true;
                    queue.add(node + 1);
                }
            }

            distance += 1;
        }

        return distance;
    }

    /*
     * Hacker Rank: https://www.hackerrank.com/challenges/3d-surface-area/problem?isFullScreen=true
     * Explanation: https://www.youtube.com/watch?v=tIG0GF2EqjE
     * */
    public static int surfaceArea(List<List<Integer>> A) {
        int rows = A.size();
        int cols = A.get(0).size();

        int area = 2 * rows * cols;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int val = A.get(r).get(c);

                int prevRowVal;
                if (r == 0) {
                    prevRowVal = 0;
                } else {
                    prevRowVal = A.get(r - 1).get(c);
                }

                area += Math.abs(val - prevRowVal);

                int prevColVal;
                if (c == 0) {
                    prevColVal = 0;
                } else {
                    prevColVal = A.get(r).get(c - 1);
                }

                area += Math.abs(val - prevColVal);

                if (c == cols - 1) {
                    area += A.get(r).get(c);
                }

                if (r == rows - 1) {
                    area += A.get(r).get(c);
                }
            }
        }

        return area;
    }


    /*
     * Hacker Rank: https://www.hackerrank.com/challenges/absolute-permutation/problem?isFullScreen=true
     * Explanation: https://www.youtube.com/watch?v=Z0bpJiVz0no
     * */
    public static List<Integer> absolutePermutation(int n, int k) {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            list.add(i);
        }

        if (k == 0) {
            return list;
        }

        Set<Integer> set = new HashSet<>(list);
        List<Integer> res = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            int low = i - k;
            int high = i + k;

            if (set.contains(low)) {
                res.add(low);
                set.remove(low);
            } else if (set.contains(high)) {
                res.add(high);
                set.remove(high);
            } else {
                return Collections.singletonList(-1);
            }
        }

        return res;
    }
}
