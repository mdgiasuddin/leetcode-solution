package codility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class CodilitySolution {

    /*
     * Find minimum average of al slices of length >= 2.
     * Problem: https://app.codility.com/programmers/lessons/5-prefix_sums/min_avg_two_slice/
     * Proof: https://math.stackexchange.com/questions/2682379/will-the-length-of-a-minimum-average-slice-of-a-numeric-array-ever-be-greater-th
     * */
    public int solution(int[] A) {
        int n = A.length;
        if (n < 3) {
            return 0;
        }

        double minAvg = 100000;
        int minIdx = 0;

        for (int i = 0; i < n - 1; i++) {
            double avg = (A[i] + A[i + 1]) / 2.0;
            if (avg < minAvg) {
                minAvg = avg;
                minIdx = i;
            }
        }

        for (int i = 0; i < n - 2; i++) {
            double avg = (A[i] + A[i + 1] + A[i + 2]) / 3.0;
            if (avg < minAvg) {
                minAvg = avg;
                minIdx = i;
            }
        }

        return minIdx;
    }

    /*
     * Minimum stones needed for a wall.
     * https://app.codility.com/programmers/lessons/7-stacks_and_queues/stone_wall/
     * */
    public int solution2(int[] H) {
        Stack<Integer> stack = new Stack<>();

        int blocks = 0;
        for (int h : H) {
            while (!stack.isEmpty() && stack.peek() > h) {
                stack.pop();
            }

            if (!stack.isEmpty() && stack.peek() == h) {
                continue;
            }

            stack.push(h);
            blocks += 1;

        }

        return blocks;
    }

    /*
     * Max double slice sum.
     * https://app.codility.com/programmers/lessons/9-maximum_slice_problem/max_double_slice_sum/
     * */
    public int solution3(int[] A) {
        int n = A.length;

        int[] left = new int[n];
        int[] right = new int[n];

        // 3  2 6 -1 4  5  -1 2
        // 0  2 8  7 11 16 15 0
        // 0 16 14 8 9  5 -1  0
        for (int i = 1; i < n - 1; i++) {
            left[i] = Math.max(A[i], A[i] + left[i - 1]);
            right[n - 1 - i] = Math.max(A[n - 1 - i], A[n - 1 - i] + right[n - i]);
        }

        int max = A[1];

        for (int i = 1; i < n - 1; i++) {
            max = Math.max(max, left[i - 1] + right[i + 1]);
        }

        return max;
    }

    /*
     * MaxCounters.
     * https://app.codility.com/programmers/lessons/4-counting_elements/max_counters/
     * */
    public int[] solution4(int N, int[] A) {
        int[] res = new int[N];
        int b = 0;
        int m = 0;

        for (int i : A) {
            if (i <= N) {
                // Check whether maximum value is hit or not.
                res[i - 1] = Math.max(b, res[i - 1]) + 1;

                // Update the maximum value.
                m = Math.max(m, res[i - 1]);
            } else {
                // Maximum value is hit. Update b.
                b = m;
            }
        }

        for (int i = 0; i < N; i++) {
            res[i] = Math.max(res[i], b);
        }

        return res;
    }

    /*
     * GenomicRangeQuery.
     * https://app.codility.com/programmers/lessons/5-prefix_sums/genomic_range_query/
     * */
    public int[] solution5(String S, int[] P, int[] Q) {
        int n = S.length();
        int m = P.length;

        // Store the count of each character from starting to ith position.
        int[] A = new int[n + 1];
        int[] C = new int[n + 1];
        int[] G = new int[n + 1];
        int[] T = new int[n + 1];

        int a = 0;
        int c = 0;
        int g = 0;
        int t = 0;

        for (int i = 0; i < n; i++) {
            if (S.charAt(i) == 'A') {
                a += 1;
            } else if (S.charAt(i) == 'C') {
                c += 1;
            } else if (S.charAt(i) == 'G') {
                g += 1;
            } else {
                t += 1;
            }

            A[i + 1] = a;
            C[i + 1] = c;
            G[i + 1] = g;
            T[i + 1] = t;
        }

        int[] res = new int[m];
        for (int i = 0; i < m; i++) {
            // Check which character count is increased.
            if (A[Q[i] + 1] > A[P[i]]) {
                res[i] = 1;
            } else if (C[Q[i] + 1] > C[P[i]]) {
                res[i] = 2;
            } else if (G[Q[i] + 1] > G[P[i]]) {
                res[i] = 3;
            } else {
                res[i] = 4;
            }
        }
        return res;
    }

    /*
     * NumberOfDiscIntersections.
     * https://app.codility.com/programmers/lessons/6-sorting/number_of_disc_intersections/
     * */
    public int solution6(int[] A) {
        int n = A.length;
        long[] open = new long[n];
        long[] close = new long[n];

        for (int i = 0; i < n; i++) {
            open[i] = (open[i] + i) - A[i];
            close[i] = (close[i] + i) + A[i];
        }

        Arrays.sort(open);
        Arrays.sort(close);

        int i = 0;
        int j = 0;
        int opening = 0;
        int intersection = 0;

        while (i < n) {

            // If an opening circle arises, it will intersect all current opening circles.
            if (open[i] <= close[j]) {
                intersection += opening;
                opening += 1;
                i += 1;
            } else { // 1 circle is closed.
                opening -= 1;
                j += 1;
            }
        }

        return intersection > 10000000 ? -1 : intersection;
    }

    /*
     * MaxDoubleSliceSum.
     * https://app.codility.com/programmers/lessons/9-maximum_slice_problem/max_double_slice_sum/
     * */
    public int solution7(int[] A) {
        int n = A.length;
        int[] left = new int[n];
        int[] right = new int[n];

        // First & last element is always ignored.
        for (int i = 1; i < n - 1; i++) {
            left[i] = Math.max(left[i - 1] + A[i], 0);
        }

        for (int i = n - 2; i > 0; i--) {
            right[i] = Math.max(right[i + 1] + A[i], 0);
        }

        int res = 0;
        for (int i = 1; i < n - 2; i++) {
            res = Math.max(res, left[i - 1] + right[i + 1]);
        }

        return res;
    }

    /*
     * Flags.
     * https://app.codility.com/programmers/lessons/10-prime_and_composite_numbers/flags/
     * Binary Search.
     * */
    public int solution8(int[] A) {

        // Find out the peak index.
        List<Integer> peaks = new ArrayList<>();
        for (int i = 1; i < A.length - 1; i++) {
            if (A[i - 1] < A[i] && A[i] > A[i + 1]) {
                peaks.add(i);
            }
        }

        if (peaks.isEmpty()) {
            return 0;
        }

        int l = 1;
        int h = peaks.size();
        int res = 1;

        while (l <= h) {
            int m = l + (h - l) / 2;
            if (canPlaceFlag(m, peaks)) {
                res = m;
                l = m + 1;
            } else {
                h = m - 1;
            }
        }

        return res;
    }

    private boolean canPlaceFlag(int flag, List<Integer> peaks) {
        int prev = peaks.get(0);
        int k = flag;
        flag -= 1;
        int i = 1;

        while (i < peaks.size()) {

            // Find the next mountain to place flag.
            while (i < peaks.size() && peaks.get(i) < prev + k) {
                i += 1;
            }

            if (i < peaks.size()) {
                prev = peaks.get(i);
                flag -= 1;
            }

            i += 1;
        }

        return flag <= 0;
    }
}
