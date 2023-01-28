package array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArraySolution7 {

    // Leetcode problem: 60
    /*
     * Permutation Sequence.
     * Explanation: https://www.youtube.com/watch?v=W9SIlE2jhBQ&list=PLEJXowNB4kPzC3OYy2LRovf_xb8JjAMEF&index=2
     * */
    public String getPermutation(int n, int k) {
        int[] factorial = new int[n];
        List<Integer> digits = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            digits.add(i);
        }

        factorial[0] = 1;
        for (int i = 1; i < n; i++) {
            factorial[i] = factorial[i - 1] * i;
        }

        StringBuilder res = new StringBuilder();
        int pos = n;

        // Run the loop until the last digit.
        while (pos > 1) {
            int blockSize = factorial[pos - 1];

            // Get the ceiling.
            int idx = (k + blockSize - 1) / blockSize;
            res.append(digits.get(idx));
            digits.remove(idx);
            k -= blockSize * (idx - 1);
            pos -= 1;
        }

        // Add the last digit.
        res.append(digits.get(1));

        return res.toString();
    }

    // Leetcode problem: 149
    /*
     * Max Points on a Line.
     * Calculate slope of every point with ith point and count the max.
     * */
    public int maxPoints(int[][] points) {
        int n = points.length;
        int res = 1;
        for (int i = 0; i < n - 1; i++) {
            int[] point1 = points[i];
            Map<Double, Integer> map = new HashMap<>();
            for (int j = i + 1; j < n; j++) {
                int[] point2 = points[j];

                double slope;
                if (point1[0] == point2[0]) {
                    slope = Double.MAX_VALUE;
                } else {
                    slope = 1.0 * (point2[1] - point1[1]) / (point2[0] - point1[0]);
                }

                // Be careful in this line.
                if (slope == -0.0) {
                    slope = 0.0;
                }
                int count = map.getOrDefault(slope, 0) + 1;
                map.put(slope, count);
                res = Math.max(res, count + 1);
            }
        }

        return res;
    }

    // Leetcode problem: 2515
    /*
     * Shortest Distance to Target String in a Circular Array.
     * */
    public int closetTarget(String[] words, String target, int startIndex) {
        int n = words.length;
        int ans = n;

        for (int i = 0; i < n; i++) {
            if (words[i].equals(target)) {

                // Minimum difference lies in either side. Array is circular. So take the %.
                ans = Math.min(ans, Math.min((i - startIndex + n) % n, (startIndex - i + n) % n));
            }
        }

        return ans == n ? -1 : ans;
    }
}
