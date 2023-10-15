package heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class HeapSolution2 {

    // Leetcode problem: 502
    /*
     * IPO.
     * */
    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        int n = profits.length;

        int[][] cp = new int[n][2];
        for (int i = 0; i < n; i++) {
            cp[i][0] = capital[i];
            cp[i][1] = profits[i];
        }

        // Sort based on the capital needed.
        Arrays.sort(cp, Comparator.comparingInt(a -> a[0]));
        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(a -> -a));

        // Add which project capital needed <= than existing w.
        int i = 0;
        while (i < n && cp[i][0] <= w) {
            queue.add(cp[i][1]);
            i += 1;
        }

        while (k > 0 && !queue.isEmpty()) {
            // Take the most profitable project.
            w += queue.poll();
            k -= 1;

            // w updated. Add more projects which capital needed <= than existing w.
            while (i < n && cp[i][0] <= w) {
                queue.add(cp[i][1]);
                i += 1;
            }
        }

        return w;
    }

    // Leetcode problem: 1675
    /*
     * Minimize Deviation in Array.
     * Explanation: https://www.youtube.com/watch?v=boHNFptxo2A&t=3s
     * The maximum deviation is the difference between the highest & the lowest value.
     * */
    public int minimumDeviation(int[] nums) {
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        int maxVal = 0;

        // First insert the lowest possible value & track the highest value it can be.
        for (int num : nums) {
            int tmp = num;
            while (tmp % 2 == 0) {
                tmp /= 2;
            }

            maxVal = Math.max(maxVal, tmp);
            queue.add(new int[]{tmp, Math.max(2 * tmp, num)});
        }

        int res = Integer.MAX_VALUE;

        // Run until any other option is possible.
        while (queue.size() == nums.length) {
            int[] num = queue.poll();
            res = Math.min(res, maxVal - num[0]);

            // If any greater value can be taken, take it.
            if (num[1] > num[0]) {
                maxVal = Math.max(maxVal, num[0] * 2);
                queue.add(new int[]{2 * num[0], num[1]});
            }
        }

        return res;
    }

    // Leetcode problem: 1338
    /*
     * Reduce Array Size to The Half.
     * Count the occurrences of each element.
     * Remove the elements with greater count one by one to reduce the size into half.
     * */
    public int minSetSize(int[] arr) {
        Arrays.sort(arr);

        int removal = (arr.length + 1) / 2;
        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(a -> -a));
        int count = 1;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] != arr[i - 1]) {
                queue.add(count);
                count = 1;
            } else {
                count += 1;
            }
        }
        queue.add(count);

        int res = 0;
        while (removal > 0) {
            res += 1;
            removal -= queue.poll();
        }

        return res;
    }

    // Leetcode problem: 373
    /*
     * Find K Pairs with Smallest Sum.
     * Explanation: https://www.youtube.com/watch?v=Youk8DDnLU8
     * */
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> result = new ArrayList<>();
        PriorityQueue<long[]> queue = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));
        Set<List<Integer>> visited = new HashSet<>();

        // [nums1[0], nums2[0]] will always be selected.
        queue.add(new long[]{nums1[0] + nums2[0], 0, 0});
        visited.add(Arrays.asList(0, 0));

        while (k > 0 && !queue.isEmpty()) {
            long[] arr = queue.poll();
            int i = (int) arr[1];
            int j = (int) arr[2];

            result.add(Arrays.asList(nums1[i], nums2[j]));
            k -= 1;

            // Try with the adjacent pair.
            if (i + 1 < nums1.length && !visited.contains(Arrays.asList(i + 1, j))) {
                queue.add(new long[]{nums1[i + 1] + nums2[j], i + 1, j});
                visited.add(Arrays.asList(i + 1, j));
            }
            if (j + 1 < nums2.length && !visited.contains(Arrays.asList(i, j + 1))) {
                queue.add(new long[]{nums1[i] + nums2[j + 1], i, j + 1});
                visited.add(Arrays.asList(i, j + 1));
            }
        }

        return result;

    }

    // Leetcode problem: 630
    /*
     * Course Schedule III.
     * Explanation: https://www.youtube.com/watch?v=ey8FxYsFAMU
     * */
    public int scheduleCourse(int[][] courses) {
        Arrays.sort(courses, (a, b) -> a[1] == b[1] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1]));

        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> -a[0]));
        int time = 0;
        for (int[] course : courses) {
            if (time + course[0] > course[1]) { // Time limit exceeded. Try to replace with longer-duration course.
                if (!queue.isEmpty() && queue.peek()[0] > course[0]) {
                    time += course[0] - queue.poll()[0];
                    queue.add(course);
                }
            } else { // Add the course.
                queue.add(course);
                time += course[0];
            }
        }

        return queue.size();
    }

    // Leetcode problem: 407
    /*
     * Trapping Rain Water II.
     * Explanation: https://www.youtube.com/watch?v=iOzm4ht8uMQ
     * BFS from boundary.
     * */
    public int trapRainWater(int[][] heightMap) {
        int m = heightMap.length;
        int n = heightMap[0].length;
        boolean[][] visited = new boolean[m][n];
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));

        int res = 0;
        for (int i = 0; i < m; i++) {
            queue.add(new int[]{heightMap[i][0], i, 0});
            queue.add(new int[]{heightMap[i][n - 1], i, n - 1});

            visited[i][0] = true;
            visited[i][n - 1] = true;
        }

        for (int i = 1; i < n - 1; i++) {
            queue.add(new int[]{heightMap[0][i], 0, i});
            queue.add(new int[]{heightMap[m - 1][i], m - 1, i});

            visited[0][i] = true;
            visited[m - 1][i] = true;
        }

        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        while (!queue.isEmpty()) {
            int[] heightXY = queue.poll();

            for (int[] dir : dirs) {
                int x = heightXY[1] + dir[0];
                int y = heightXY[2] + dir[1];

                if (x < 0 || x >= m || y < 0 || y >= n || visited[x][y]) {
                    continue;
                }

                res += Math.max(0, heightXY[0] - heightMap[x][y]);
                queue.add(new int[]{Math.max(heightXY[0], heightMap[x][y]), x, y});
                visited[x][y] = true;
            }
        }

        return res;
    }

    // Leetcode problem: 871
    /*
     * Minimum Number of Refueling Stops.
     * Sort the stations based on the start position of the stations.
     * Add the stations those are reachable in descending. Take fuel from the stations with the highest fuel.
     * */
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        Arrays.sort(stations, Comparator.comparingInt(a -> a[0]));
        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(a -> -a));

        int n = stations.length;
        int i = 0;
        while (i < n && stations[i][0] <= startFuel) {
            queue.add(stations[i][1]);
            i += 1;
        }

        int ans = 0;
        while (startFuel < target) {
            if (queue.isEmpty()) {
                return -1;
            }

            startFuel += queue.poll();
            ans += 1;
            while (i < n && stations[i][0] <= startFuel) {
                queue.add(stations[i][1]);
                i += 1;
            }
        }

        return ans;
    }
}
