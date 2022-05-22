package array;

import java.util.Arrays;

public class ArraySolution5 {

    public static void main(String[] args) {

    }

    // Leetcode problem: 1288
    public int removeCoveredIntervals(int[][] intervals) {
        // Sort based on start ascending. If ties then by end descending.
        Arrays.sort(intervals, (a, b) -> (a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]));

        int[] prev = intervals[0];
        int result = 1;
        for (int i = 1; i < intervals.length; i++) {
            // Test if current interval can be covered by prev interval.
            if (intervals[i][1] > prev[1]) {
                prev = intervals[i];
                result++;
            }
        }

        return result;
    }
}
