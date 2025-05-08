package greedy;

import java.util.Arrays;
import java.util.Comparator;

public class GreedySolution {
    public static void main(String[] args) {

    }

    // Leetcode problem: 376

    /**
     * This solution is tricky
     * Count the ups and down of the array
     * If up or down come consecutively then consider it a single element
     * */
    public int wiggleMaxLength(int[] nums) {

        if (nums.length == 1)
            return 1;

        int up, down;
        up = down = 1;

        /**
         * If up or down values comes consecutively then up values then nothing will happen
         * If they come alternatively then up and down will be increased
         * */
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                up = down + 1;
            } else if (nums[i] < nums[i - 1]) {
                down = up + 1;
            }
        }

        return Math.max(up, down);

    }

    // Leetcode problem: 435

    /**
     * Sort the intervals based on start.
     * Compare adjacent intervals whether overlap with previous one.
     * If no overlap then only update the previous end.
     * Else update result and update previous.
     * Always remove the value with larger end if overlapped.
     * */
    public int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

        int prevEnd = intervals[0][1], result = 0;

        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] >= prevEnd) {
                prevEnd = intervals[i][1];
            } else {
                result++;
                prevEnd = Math.min(prevEnd, intervals[i][1]);
            }
        }

        return result;
    }

    // Leetcode problem: 1029

    /**
     * Sort the array based on the difference between ai and bi
     * From first half take ai and from second half take bi
     * */
    public int twoCitySchedCost(int[][] costs) {
        Arrays.sort(costs, Comparator.comparingInt(a -> a[0] - a[1]));

        int result = 0;

        for (int i = 0; i < costs.length / 2; i++) {
            result += costs[i][0];
        }

        for (int i = costs.length / 2; i < costs.length; i++) {
            result += costs[i][1];
        }

        return result;
    }

}
