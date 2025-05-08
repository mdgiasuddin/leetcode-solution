package heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class HeapSolution {

    public static void main(String[] args) {

        HeapSolution heapSolution = new HeapSolution();
    }

    // Leetcode problem: 1094

    /**
     * Sort the trips base on the start point
     * After reaching each trip find all the trip before it whether any passenger can be dropped,
     * Then add the passenger with the current passenger and check whether it overflows
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

    // Leetcode problem: 1882

    /**
    * Process Tasks Using Servers.
    * */
    public int[] assignTasks(int[] servers, int[] tasks) {
        PriorityQueue<int[]> available = new PriorityQueue<>((a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);

        PriorityQueue<int[]> unavailable = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));

        for (int i = 0; i < servers.length; i++) {
            int[] server = {servers[i], i, 0};
            available.add(server);
        }

        int[] res = new int[tasks.length];
        int i = 0;
        int t = 0;
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
        // So that after at a time, all the tasks can be added to an available task based on enqueue time.
        PriorityQueue<int[]> allTasks = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));

        // Available task that is processed one by one based on processing time.
        PriorityQueue<int[]> available = new PriorityQueue<>((a, b) -> a[2] == b[2] ? a[0] - b[0] : a[2] - b[2]);

        for (int i = 0; i < tasks.length; i++) {
            allTasks.add(new int[]{i, tasks[i][0], tasks[i][1]});
        }

        int[] res = new int[tasks.length];

        // The First processing time will be the enqueue time of the first task.
        int t = allTasks.peek()[1];
        int i = 0;
        while (!allTasks.isEmpty() || !available.isEmpty()) {

            // Move a task to available if enqueue time crosses.
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

    /**
     * Sort the points based on the distance from origin and take the first k points.
     * Time complexity of sort is O(n*log(n)).
     * To minimize time complexity, build up a min heap.
     * Build min heap complexity is O(n).
     * Then extract k points complexity is O(k*log(n)).
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

    // Leetcode problem: 1296
    // Leetcode problem: 846
    public boolean isPossibleDivide(int[] nums, int k) {
        if (nums.length % k != 0) {
            return false;
        }

        Map<Integer, Integer> map = new HashMap<>();

        // Calculate the count of each element.
        for (int num : nums) {
            int count = map.getOrDefault(num, 0);
            map.put(num, count + 1);
        }

        PriorityQueue<Integer> queue = new PriorityQueue<>(map.keySet());

        while (!queue.isEmpty()) {
            int min = queue.peek();

            // Check all numbers exist or not.
            for (int i = min; i < min + k; i++) {
                int count = map.getOrDefault(i, 0);
                if (count == 0)
                    return false;

                count -= 1;
                map.put(i, count);

                // If this is not the minimum but the count is 0.
                if (count == 0 && queue.peek() != i) {
                    return false;
                }

                // Remove the number from the queue.
                if (count == 0) {
                    queue.poll();
                }
            }
        }

        return true;
    }

    // Leetcode problem: 621
    public int leastInterval(char[] tasks, int n) {
        Queue<int[]> queue = new LinkedList<>();

        Map<Character, Integer> map = new HashMap<>();
        for (char task : tasks) {
            int count = map.getOrDefault(task, 0);
            map.put(task, count - 1);
        }

        // Since we added the counts negative values, it'll work as a max priority queue.
        PriorityQueue<Integer> maxQueue = new PriorityQueue<>(map.values());

        int time = 0;
        while (!maxQueue.isEmpty() || !queue.isEmpty()) {
            time += 1;

            // Restore the task to max queue that can be done at time t.
            while (!queue.isEmpty() && queue.peek()[1] <= time) {
                maxQueue.add(queue.poll()[0]);
            }

            // If there is an available task, do this.
            if (!maxQueue.isEmpty()) {
                int task = maxQueue.poll();

                // If there is the same more task, add it to the queue with next available time.
                if (task < -1) {
                    queue.add(new int[]{task + 1, time + n + 1});
                }
            }

        }

        return time;
    }

    /**
     * Nearly sorted.
     * Practice: https://practice.geeksforgeeks.org/problems/nearly-sorted-1587115620/1
     * Explanation: https://www.youtube.com/watch?v=tJK7vjNKdLY&list=PLEJXowNB4kPyP2PdMhOUlTY6GrRIITx28&index=12
     * */
    ArrayList<Integer> nearlySorted(int[] arr, int num, int k) {
        // your code here

        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int i = 0; i <= k && i < num; i++) {
            queue.add(arr[i]);
        }

        ArrayList<Integer> res = new ArrayList<>();
        for (int i = k + 1; i < num; i++) {
            res.add(queue.poll());
            queue.add(arr[i]);
        }

        while (!queue.isEmpty()) {
            res.add(queue.poll());
        }

        return res;
    }

    // Leetcode problem: 2530

    /**
     * Maximal Score After Applying K Operations.
     * */
    public long maxKelements(int[] nums, int k) {
        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(a -> -a));
        for (int num : nums) {
            queue.add(num);
        }

        long score = 0;
        while (k-- > 0) {
            int num = queue.poll();
            score += num;
            queue.add((num + 2) / 3); // Ceiling after / by 3.
        }

        return score;
    }

    // Leetcode problem: 2542

    /**
     * Maximum Subsequence Score.
     * This problem is similar to: Maximum Performance of a Team (Leetcode problem: 1383).
     * Explanation: https://www.youtube.com/watch?v=N8oNUI6JM0I&list=PLy38cn8b_xMfO7CGsUDIsYGps37yKaQ9X&index=50
     * */
    public long maxScore(int[] nums1, int[] nums2, int k) {
        int n = nums1.length;
        int[][] combined = new int[n][2];

        for (int i = 0; i < n; i++) {
            combined[i][0] = nums1[i];
            combined[i][1] = nums2[i];
        }

        Arrays.sort(combined, Comparator.comparingInt(a -> -a[1]));
        long prefix = 0;
        int i = 0;
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        while (i < k) {
            prefix += combined[i][0];
            queue.add(combined[i][0]);
            i += 1;
        }

        long res = prefix * combined[k - 1][1];
        while (i < n) {
            prefix -= queue.poll();
            prefix += combined[i][0];
            queue.add(combined[i][0]);
            res = Math.max(res, prefix * combined[i][1]);

            i += 1;
        }

        return res;
    }

    // Leetcode problem: 2454

    /**
     * Next Greater Element IV.
     * Explanation: https://www.youtube.com/watch?v=YZrQrQehM64&list=PLy38cn8b_xMeo28nZcPQTV-z3-DcaQOaY&index=2
     * First find the next greater, then find the second greater element.
     * */
    public int[] secondGreaterElement(int[] nums) {
        List<List<Integer>> nextGreater = new ArrayList<>();
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            nextGreater.add(new ArrayList<>());
        }
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < nums.length; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
                nextGreater.get(i).add(stack.pop());
            }

            stack.push(i);
        }

        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        int[] res = new int[n];
        Arrays.fill(res, -1);
        for (int i = 0; i < n; i++) {
            while (!queue.isEmpty() && nums[i] > queue.peek()[0]) {
                res[queue.poll()[1]] = nums[i];
            }

            for (int idx : nextGreater.get(i)) {
                queue.add(new int[]{nums[idx], idx});
            }
        }

        return res;
    }

}
