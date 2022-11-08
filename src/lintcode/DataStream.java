package lintcode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

// Lintcode problem: 960
/*
 * First Unique Number in Data Stream
 * */

public class DataStream {
    LinkedList<Integer> list;
    Map<Integer, Integer> map;

    public DataStream() {
        list = new LinkedList<>();
        map = new HashMap<>();
    }

    /**
     * @param num: next number in stream
     * @return: nothing
     */
    public void add(int num) {
        int count = map.getOrDefault(num, 0) + 1;
        map.put(num, count);

        if (count == 1) {
            list.addLast(num);
        }
    }

    /**
     * @return: the first unique number in stream
     */
    public int firstUnique() {
        while (!list.isEmpty() && map.get(list.getFirst()) > 1) {
            list.removeFirst();
        }

        return list.getFirst();
    }
}
