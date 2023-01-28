package tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

// Leetcode problem: 352
/*
 * Data Stream as Disjoint Intervals.
 * Explanation: https://www.youtube.com/watch?v=FavoZjPIWpo
 * */
public class SummaryRanges {
    Set<Integer> numSet;

    public SummaryRanges() {
        numSet = new TreeSet<>();
    }

    public void addNum(int value) {
        numSet.add(value);
    }

    public int[][] getIntervals() {
        List<int[]> result = new ArrayList<>();

        for (int value : numSet) {
            if (!result.isEmpty() && result.get(result.size() - 1)[1] == value - 1) {
                result.get(result.size() - 1)[1] = value;
            } else {
                result.add(new int[]{value, value});
            }
        }

        int[][] resArray = new int[result.size()][2];

        for (int i = 0; i < result.size(); i++) {
            resArray[i][0] = result.get(i)[0];
            resArray[i][1] = result.get(i)[1];
        }

        return resArray;
    }
}