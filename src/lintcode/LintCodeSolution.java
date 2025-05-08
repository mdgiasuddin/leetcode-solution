package lintcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LintCodeSolution {
    public static void main(String[] args) {

        int[] arr = new int[10];
        arr = new int[20];

        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();

        arrayList.add(10);
        linkedList.add(10);

        LintCodeSolution solution = new LintCodeSolution();
        solution.doSomething(new LinkedList<>());
    }

    public void doSomething(List<Integer> list) {

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

            /**
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
