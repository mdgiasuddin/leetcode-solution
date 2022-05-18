package lintcode;

import java.util.Arrays;
import java.util.List;

public class LintCodeSolution {
    public static void main(String[] args) {

    }

    // Lintcode problem: 919
    // Leetcode problem: 253
    public int minMeetingRooms(List<Interval> intervals) {
        // Write your code here

        int[] starts = new int[intervals.size()];
        int[] ends = new int[intervals.size()];

        int i = 0;
        for (Interval interval : intervals) {
            starts[i] = interval.start;
            ends[i] = interval.end;
            i++;
        }

        Arrays.sort(starts);
        Arrays.sort(ends);

        i = 0;
        int j = 0, room = 0, maxRoom = 0;
        while (j < intervals.size()) {

            /*
             * If start time comes before end increase room count. Update max room needed.
             * Else decrement room count.
             * */
            if (i < intervals.size() && starts[i] < ends[j]) {
                i++;
                room++;
                maxRoom = Math.max(maxRoom, room);
            } else {
                j++;
                room--;
            }
        }

        return maxRoom;
    }
}
