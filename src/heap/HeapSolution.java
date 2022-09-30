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

    // Leetcode problem: 1383
    public int maxPerformance(int n, int[] speed, int[] efficiency, int k) {

        final int MOD = 1000000007;
        int[][] spEff = new int[n][2];

        for (int i = 0; i < n; i++) {
            spEff[i][0] = speed[i];
            spEff[i][1] = efficiency[i];
        }

        // Sort the array based on efficiency desc.
        Arrays.sort(spEff, Comparator.comparingInt(a -> -a[1]));

        PriorityQueue<Integer> queue = new PriorityQueue<>();

        long res = 0;
        long s = 0;
        for (int[] se : spEff) {

            // Remove the minimum speed if size overflows.
            if (queue.size() == k) {
                s -= queue.poll();
            }

            s += se[0];
            queue.add(se[0]);

            // Total speed is multiplied by minimum efficiency & se[1] contains minimum efficiency so far.
            res = Math.max(res, s * se[1]);
        }

        return (int) (res % MOD);
    }

    // Leetcode problem: 1834
    public int[] getOrder(int[][] tasks) {

        // Store all the tasks sorted by initial enqueue time.
        // So that after at a time all the task can be added to available task based on enqueue time.
        PriorityQueue<int[]> allTasks = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));

        // Available task that is processed one by one based on processing time.
        PriorityQueue<int[]> available = new PriorityQueue<>((a, b) -> a[2] == b[2] ? a[0] - b[0] : a[2] - b[2]);

        for (int i = 0; i < tasks.length; i++) {
            allTasks.add(new int[]{i, tasks[i][0], tasks[i][1]});
        }

        int[] res = new int[tasks.length];

        // First processing time will be the enqueue time of the first task.
        int t = allTasks.peek()[1];
        int i = 0;
        while (!allTasks.isEmpty() || !available.isEmpty()) {

            // Move task to available if enqueue time crosses.
            while (!allTasks.isEmpty() && allTasks.peek()[1] <= t) {
                available.add(allTasks.poll());
            }
            if (!available.isEmpty()) {

                // If available task then process it & the next processing time will be the finish time of it.
                int[] task = available.poll();
                res[i++] = task[0];
                t += task[2];
            } else {

                // Else next processing time will the enqueue time of the next task.
                t = allTasks.peek()[1];
            }
        }

        return res;
    }

    // Leetcode problem: 973
    /*
     * Sort the based on the distance from origin & take first k points.
     * Time complexity of sort is O(n*log(n)). To minimize time complexity build up a min heap.
     * Build min heap complexity is O(n). Then extract k points complexity is O(k*log(n)).
     * */
    public int[][] kClosest(int[][] points, int k) {

        List<Point2D> list = new ArrayList<>();
        for (int[] point : points) {
            list.add(new Point2D(point[0] * point[0] + point[1] * point[1], point[0], point[1]));
        }

        PriorityQueue<Point2D> queue = new PriorityQueue<>(list);

        int[][] res = new int[k][2];
        for (int i = 0; i < k; i++) {
            Point2D point = queue.poll();
            res[i] = new int[]{point.x, point.y};
        }

        return res;
    }

}
