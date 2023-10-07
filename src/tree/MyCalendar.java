package tree;

import java.util.Map;
import java.util.TreeMap;

public class MyCalendar {

    Map<Integer, Integer> map;

    public MyCalendar() {
        map = new TreeMap<>();
    }

    public boolean book(int start, int end) {
        int cs = map.getOrDefault(start, 0);
        int ce = map.getOrDefault(end, 0);
        map.put(start, cs + 1);
        map.put(end, ce - 1);

        int count = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            count += entry.getValue();
            if (count > 1) {
                map.put(start, cs);
                map.put(end, ce);
                return false;
            }
        }

        return true;
    }
}
