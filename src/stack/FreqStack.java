package stack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FreqStack {

    // Leetcode problem: 895
    /*
     * Build up count map for each element and map for max count vs element list.
     * */
    Map<Integer, Integer> count;
    Map<Integer, List<Integer>> stack;
    int maxCount;

    public FreqStack() {
        count = new HashMap<>();
        stack = new HashMap<>();
        maxCount = 0;
    }

    public void push(int val) {
        int c = 1 + count.getOrDefault(val, 0);
        count.put(val, c);

        // For new max count save the element in max count list.
        if (c > maxCount) {
            maxCount = c;
            stack.put(c, new ArrayList<>());
        }

        stack.get(c).add(val);
    }

    public int pop() {
        List<Integer> popList = stack.get(maxCount);
        int res = popList.remove(popList.size() - 1);

        count.put(res, count.get(res) - 1);

        // If max count list empty, there no more element in max count. So max count is decreased.
        if (popList.isEmpty()) {
            maxCount--;
        }

        return res;
    }
}

