package heap;

import java.util.*;

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

    // Leetcode problem: 1851
    public int[] minInterval(int[][] intervals, int[] queries) {

        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        int[] sortedQueries = new int[queries.length];
        System.arraycopy(queries, 0, sortedQueries, 0, queries.length);
        Arrays.sort(sortedQueries);

        PriorityQueue<List<Integer>> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a.get(0)));
        Map<Integer, Integer> resMap = new HashMap<>();

        int i = 0;
        for (int q : sortedQueries) {
            // Insert the intervals that may contain q.
            while (i < intervals.length && intervals[i][0] <= q) {
                // Store the interval range & end value.
                queue.add(Arrays.asList(intervals[i][1] - intervals[i][0] + 1, intervals[i][1]));
                i++;
            }

            // Remove the intervals that are far behind and cannot contain q or >q intervals.
            while (!queue.isEmpty() && queue.peek().get(1) < q)
                queue.poll();

            if (!queue.isEmpty())
                resMap.put(q, queue.peek().get(0));
        }

        int[] resArray = new int[queries.length];
        for (i = 0; i < queries.length; i++)
            resArray[i] = resMap.getOrDefault(queries[i], -1);

        return resArray;
    }

    public int[] assignTasks(int[] servers, int[] tasks) {
        PriorityQueue<int[]> available = new PriorityQueue<>((a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);

        PriorityQueue<int[]> unavailable = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));

        for (int i = 0; i < servers.length; i++) {
            int[] server = {servers[i], i, 0};
            available.add(server);
        }

        int[] res = new int[tasks.length];
        int i = 0, t = 0;
        while (i < tasks.length) {

            while (!unavailable.isEmpty() && unavailable.peek()[2] == t) {
                int[] server = unavailable.poll();
                available.add(server);
            }

            while (!available.isEmpty() && i < tasks.length && i <= t) {
                int[] server = available.poll();
                res[i] = server[1];
                server[2] = t + tasks[i];
                unavailable.add(server);
                i++;
            }
            t++;
        }

        return res;
    }

}
