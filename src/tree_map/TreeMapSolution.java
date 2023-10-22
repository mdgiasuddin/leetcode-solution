package tree_map;

import java.util.*;

public class TreeMapSolution {
    public static void main(String[] args) {
        TreeMapSolution treeMapSolution = new TreeMapSolution();
        int[][] buildings = {
                {0, 2, 3}, {2, 5, 3}
        };

        treeMapSolution.getSkyline(buildings);
    }

    // Leetcode problem: 218.
    /*
     * The Skyline Problem.
     * Explanation: https://www.youtube.com/watch?v=GSBLe8cKu0s
     * Insert into the queue at left position & remove from the queue at end position.
     * */
    public List<List<Integer>> getSkyline(int[][] buildings) {
        List<Building> buildingList = new ArrayList<>();
        for (int[] building : buildings) {
            buildingList.add(new Building(building[0], building[2], true));
            buildingList.add(new Building(building[1], building[2], false));
        }

        Collections.sort(buildingList);
        TreeMap<Integer, Integer> countMap = new TreeMap<>(Comparator.comparingInt(a -> -a));
        countMap.put(0, 1);
        int maxHeight = 0;
        List<List<Integer>> result = new ArrayList<>();
        for (Building building : buildingList) {
            if (building.start) {
                int count = countMap.getOrDefault(building.y, 0);
                countMap.put(building.y, count + 1);

                if (countMap.firstKey() != maxHeight) {
                    maxHeight = countMap.firstKey();
                    result.add(Arrays.asList(building.x, maxHeight));
                }
            } else {
                int count = countMap.get(building.y);
                if (count == 1) {
                    countMap.remove(building.y);
                    if (countMap.firstKey() != maxHeight) {
                        maxHeight = countMap.firstKey();
                        result.add(Arrays.asList(building.x, maxHeight));
                    }
                } else {
                    countMap.put(building.y, count - 1);
                }
            }
        }

        return result;
    }

    // Leetcode problem: 480
    /*
    * Sliding Window Median.
    * Explanation: https://www.youtube.com/watch?v=NT5Lp5vaMm0
    * This problem is similar to: Find Median in Data Stream (Leetcode problem: 295).
    * */
    public double[] medianSlidingWindow(int[] nums, int k) {
        Comparator<Integer> comparator = (a, b) -> nums[a] == nums[b]
                ? Integer.compare(a, b) : Integer.compare(nums[a], nums[b]);

        TreeSet<Integer> maxHeap = new TreeSet<>(comparator.reversed());
        TreeSet<Integer> minHeap = new TreeSet<>(comparator);

        int n = nums.length;
        int idx = 0;
        double[] result = new double[n - k + 1];
        for (int i = 0; i < n; i++) {
            // Remove invalid index.
            if (i >= k) {
                maxHeap.remove(i - k);
                minHeap.remove(i - k);
            }

            maxHeap.add(i);
            // Balance the heaps.
            while (maxHeap.size() > minHeap.size() + 1) {
                minHeap.add(maxHeap.pollFirst());
            }
            if (!minHeap.isEmpty() && nums[minHeap.first()] < nums[maxHeap.first()]) {
                maxHeap.add(minHeap.pollFirst());
                minHeap.add(maxHeap.pollFirst());
            }

            if (i >= k - 1) {
                result[idx++] = k % 2 == 1 ? nums[maxHeap.first()]
                        : ((double) nums[maxHeap.first()] + (double) nums[minHeap.first()]) / 2;
            }
        }

        return result;
    }
}

class Building implements Comparable<Building> {
    public final int x;
    public final int y;
    public final boolean start;

    public Building(int x, int y, boolean start) {
        this.x = x;
        this.y = y;
        this.start = start;
    }

    /*
     * Sort based on x. If x is equal => If both are start then based on height(desc)
     * If both are end then height(asc).
     * If one is start and another is end then start first.
     * */
    @Override
    public int compareTo(Building building) {
        if (this.x == building.x) {
            if (this.start && building.start) {
                return Integer.compare(building.y, this.y);
            } else if (!this.start && !building.start) {
                return Integer.compare(this.y, building.y);
            }

            return this.start ? -1 : 1;
        }
        return Integer.compare(this.x, building.x);
    }

}
