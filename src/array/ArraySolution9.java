package array;

import java.util.HashMap;
import java.util.Map;

public class ArraySolution9 {

    // Leetcode problem: 659
    /*
     * Split Array Into Consecutive Sequence.
     * Explanation: https://www.youtube.com/watch?v=Ty8EZlxVNC8
     * Create group of list. For a number always try to add this in an existing group.
     * If it is not possible then create a new group.
     * */
    public boolean isPossible(int[] nums) {
        Map<Integer, Integer> available = new HashMap<>();
        Map<Integer, Integer> vacancy = new HashMap<>();

        for (int num : nums) {
            available.put(num, available.getOrDefault(num, 0) + 1);
        }

        for (int num : nums) {
            if (available.get(num) == 0) {
                continue;
            }

            available.put(num, available.get(num) - 1);
            if (vacancy.getOrDefault(num, 0) > 0) { // Can be fit in existing group.
                vacancy.put(num, vacancy.get(num) - 1);

                // Create a new vacancy for the next element.
                vacancy.put(num + 1, vacancy.getOrDefault(num + 1, 0) + 1);
            } else if (available.getOrDefault(num + 1, 0) > 0 &&
                    available.getOrDefault(num + 2, 0) > 0) { // Create a new group.
                available.put(num + 1, available.get(num + 1) - 1);
                available.put(num + 2, available.get(num + 2) - 1);

                // Create a new vacancy for the next element.
                vacancy.put(num + 3, vacancy.getOrDefault(num + 3, 0) + 1);
            } else {
                return false;
            }
        }

        return true;
    }
}
