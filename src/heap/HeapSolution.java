package heap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class HeapSolution {

    public static void main(String[] args) {

        HeapSolution heapSolution = new HeapSolution();

        int[][] nums = {{7, 5, 6}, {6, 7, 8}, {10, 1, 6}};
    }

    // Leetcode problem: 1094
    /*
     * Sort the trips base on the start point
     * After reaching each trip find all the trip before it whether any passenger can be dropped
     * Then add the passenger with current passenger and check whether it overflows
     * */
    public boolean carPooling(int[][] trips, int capacity) {
        Arrays.sort(trips, Comparator.comparingInt(a -> a[1]));
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));

        int currentPassenger = 0;

        for (int[] trip : trips) {

            while (!queue.isEmpty() && queue.peek()[2] <= trip[1]) {
                int[] dropTrip = queue.poll();
                currentPassenger -= dropTrip[0];
            }

            queue.add(trip);
            currentPassenger += trip[0];

            if (currentPassenger > capacity) {
                return false;
            }
        }

        return true;
    }
}
