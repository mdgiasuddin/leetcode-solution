package indefinite;

import java.util.*;

public class RandomizedSet {

    // Leetcode problem: 380
    Map<Integer, Integer> indexMap;
    List<Integer> elements;

    public RandomizedSet() {
        indexMap = new HashMap<>();
        elements = new ArrayList<>();
    }

    public boolean insert(int val) {
        if (indexMap.containsKey(val))
            return false;

        indexMap.put(val, elements.size());
        elements.add(val);

        return true;
    }

    public boolean remove(int val) {
        if (!indexMap.containsKey(val))
            return false;

        // Swap with the last element and delete the last.
        int idx = indexMap.get(val);
        int last = elements.get(elements.size() - 1);
        elements.set(idx, last);
        elements.remove(elements.size() - 1);

        // Update the index of last element.
        indexMap.put(last, idx);
        indexMap.remove(val);

        return true;
    }

    public int getRandom() {
        Random r = new Random();

        int idx = r.nextInt(elements.size());

        return elements.get(idx);
    }
}
