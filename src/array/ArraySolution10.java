package array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ArraySolution10 {
    public static void main(String[] args) {
        ArraySolution10 sol = new ArraySolution10();
        int[][] grid = {{1, 5}, {2, 3}};
        System.out.println(sol.minOperations(grid, 1));
    }

    // Leetcode problem: 2033

    /**
     * Minimum Operations to Make a Uni-Value Grid.
     * Explanation: https://www.youtube.com/watch?v=2LfVNDlx8mY
     */
    public int minOperations(int[][] grid, int x) {
        int m = grid.length;
        int n = grid[0].length;
        int total = 0;
        int elements = m * n;

        List<Integer> list = new ArrayList<>(elements);
        for (int[] row : grid) {
            for (int num : row) {
                if (num % x != grid[0][0] % x) {
                    return -1;
                }
                list.add(num / x);
                total += num / x;
            }
        }
        list.sort(Integer::compareTo);
        int res = Integer.MAX_VALUE;
        int prefix = 0;
        for (int i = 0; i < elements; i++) {
            int left = list.get(i) * i - prefix;
            int right = total - prefix - list.get(i) * (elements - i);
            res = Math.min(res, left + right);
            prefix += list.get(i);
        }

        return res;
    }


    // Leetcode problem: 3394

    /**
     * Check if Grid can be Cut into Sections.
     * Explanation: https://www.youtube.com/watch?v=X9QtxzsAsYo
     * This problem is similar to: Non-overlapping Intervals (Leetcode problem: 435).
     * The value of n will be ignored.
     */
    public boolean checkValidCuts(int n, int[][] rectangles) {
        Arrays.sort(rectangles, Comparator.comparingInt(a -> a[0]));

        int cutX = 0;
        int prev = rectangles[0][2];
        for (int i = 1; i < rectangles.length; i++) {
            if (prev <= rectangles[i][0]) {
                cutX += 1;
            }
            prev = Math.max(prev, rectangles[i][2]);
        }

        if (cutX >= 2) {
            return true;
        }

        Arrays.sort(rectangles, Comparator.comparingInt(a -> a[1]));
        int cutY = 0;
        prev = rectangles[0][3];

        for (int i = 1; i < rectangles.length; i++) {
            if (prev <= rectangles[i][1]) {
                cutY += 1;
            }
            prev = Math.max(prev, rectangles[i][3]);
        }

        return cutY >= 2;
    }

    /**
     * Count Days Without Meetings.
     * This problem is similar to: Merge Intervals (Leetcode problem: 56).
     */
    public int countDays(int days, int[][] meetings) {
        Arrays.sort(meetings, Comparator.comparingInt(a -> a[0]));
        int[] prev = meetings[0];
        int occupied = 0;

        for (int i = 1; i < meetings.length; i++) {
            if (prev[1] < meetings[i][0]) {
                occupied += prev[1] - prev[0] + 1;
                prev = meetings[i];
            } else {
                prev[1] = Math.max(prev[1], meetings[i][1]);
            }
        }
        occupied += prev[1] - prev[0] + 1;

        return days - occupied;
    }
}
