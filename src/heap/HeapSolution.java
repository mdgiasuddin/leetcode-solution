package heap;

import pair.Pair;

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

        int[] sortedQueries = Arrays.stream(queries).sorted().toArray();
        Map<Integer, Integer> result = new HashMap<>();

        PriorityQueue<Pair> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a.first));

        int i = 0;
        for (int query : sortedQueries) {
            // Add all the intervals that may cover the current query.
            while (i < intervals.length && intervals[i][0] <= query) {
                queue.add(new Pair(intervals[i][1] - intervals[i][0] + 1, intervals[i][1]));
                i++;
            }

            // Remove the intervals which are far left from the query and cannot cover query anymore.
            while (!queue.isEmpty() && queue.peek().second < query) {
                queue.poll();
            }

            result.put(query, queue.isEmpty() ? -1 : queue.peek().first);
        }

        int[] resultArray = new int[queries.length];
        for (int j = 0; j < queries.length; j++) {
            resultArray[j] = result.get(queries[i]);
        }

        return resultArray;
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
