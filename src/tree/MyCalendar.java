package tree;

import java.util.Map;
import java.util.TreeMap;

// Leetcode problem: 729
/*
 * My Calendar I.
 * Explanation: https://www.youtube.com/watch?v=dg0tCWF2ta8
 * */
public class MyCalendar {

    TreeMap<Integer, Integer> map;

    public MyCalendar() {
        map = new TreeMap<>();
    }

    public boolean book(int start, int end) {
        Map.Entry<Integer, Integer> floorEntry = map.floorEntry(start);
        Map.Entry<Integer, Integer> ceilingEntry = map.ceilingEntry(start);

        if ((floorEntry != null && start < floorEntry.getValue()) ||
            (ceilingEntry != null && ceilingEntry.getKey() < end)) {
            return false;
        }

        map.put(start, end);
        return true;
    }
}
