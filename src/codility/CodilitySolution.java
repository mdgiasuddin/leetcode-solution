package codility;

import java.util.*;

public class CodilitySolution {

    /**
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

    /**
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

    /**
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

    /**
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

    /**
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

    /**
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

    /**
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

    /**
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

    /**
     * NailingPlanks.
     * https://app.codility.com/programmers/lessons/14-binary_search_algorithm/nailing_planks/
     * */
    public int solution9(int[] A, int[] B, int[] C) {
        int n = A.length;
        int m = C.length;
        int[][] sortedNail = new int[m][2];
        for (int i = 0; i < m; i++) {
            sortedNail[i][0] = C[i];
            sortedNail[i][1] = i;
        }
        Arrays.sort(sortedNail, Comparator.comparingInt(a -> a[0]));

        int res = 0;
        for (int i = 0; i < n; i++) {
            int minIdx = getMinNailIndex(A[i], B[i], sortedNail);
            if (minIdx == -1) {
                return -1;
            }

            res = Math.max(res, minIdx);
        }

        return res;
    }

    private int getMinNailIndex(int start, int end, int[][] nails) {
        int res = -1;

        int l = 0;
        int h = nails.length - 1;

        while (l <= h) {
            int m = l + (h - l) / 2;

            if (nails[m][0] < start) {
                l = m + 1;
            } else if (nails[m][0] > end) {
                h = m - 1;
            } else {
                res = m;
                h = m - 1;
            }
        }

        if (res == -1) {
            return -1;
        }

        int i = res + 1;
        int min = nails[res][1];
        while (i < nails.length && nails[i][0] <= end) {
            min = Math.min(min, nails[i][1]);
            i += 1;
        }

        return min + 1;
    }

    /**
     * CountDistinctSlices.
     * https://app.codility.com/programmers/lessons/15-caterpillar_method/count_distinct_slices/
     * */
    public int solution10(int M, int[] A) {
        Set<Integer> set = new HashSet<>();
        int l = 0;
        int r = 0;
        long res = 0;

        while (r < A.length) {
            while (set.contains(A[r])) {
                set.remove(A[l++]);
            }

            set.add(A[r]);
            res += (r - l + 1);
            r += 1;
        }

        return (int) Math.min(res, 1000000000);
    }

    /**
     * TieRopes.
     * https://app.codility.com/programmers/lessons/16-greedy_algorithms/tie_ropes/
     * */
    public int solution11(int K, int[] A) {
        int res = 0;
        int i = 0;
        int current = 0;
        while (i < A.length) {
            current += A[i];
            if (current >= K) {
                current = 0;
                res += 1;
            }

            i += 1;
        }

        return res;
    }

    /**
     * NumberSolitaire.
     * https://app.codility.com/programmers/lessons/17-dynamic_programming/number_solitaire/
     * */
    public int solution12(int[] A) {
        int n = A.length;
        int[] dp = new int[n];
        dp[0] = A[0];

        for (int i = 1; i < n; i++) {
            dp[i] = Integer.MIN_VALUE;
            for (int j = 1; j <= 6 && i - j >= 0; j++) {
                dp[i] = Math.max(dp[i], A[i] + dp[i - j]);
            }
        }

        return dp[n - 1];
    }

    /**
     * FibFrog.
     * https://app.codility.com/programmers/lessons/13-fibonacci_numbers/fib_frog/
     * */
    public int solution13(int[] A) {
        int n = A.length;

        // n <= 1,00,000, 25 fibonacci numbers cover 1,00,000.
        int fCount = 25;
        int[] fib = new int[fCount];
        // Skip 0 & 1.
        fib[0] = 1;
        fib[1] = 2;

        for (int i = 2; i < fCount; i++) {
            fib[i] = fib[i - 1] + fib[i - 2];
        }


        int[] dp = new int[n + 1];

        for (int i = 0; i <= n; i++) {
            dp[i] = Integer.MAX_VALUE;
            if (i == n || A[i] == 1) {
                for (int j = 0; j < fCount && i - fib[j] >= -1; j++) {
                    if (i - fib[j] == -1) {
                        dp[i] = 1;
                    } else if (dp[i - fib[j]] < Integer.MAX_VALUE && 1 + dp[i - fib[j]] < dp[i]) {
                        dp[i] = 1 + dp[i - fib[j]];
                    }
                }
            }
        }

        return dp[n] == Integer.MAX_VALUE ? -1 : dp[n];
    }

    /**
     * Ladder.
     * https://app.codility.com/programmers/lessons/13-fibonacci_numbers/ladder/
     * Generate all Fibonacci numbers [1, 50,000] & return as required.
     * */
    public int[] solution14(int[] A, int[] B) {
        int mod = (1 << 30) - 1;
        int maxFib = 50000;

        int[] dp = new int[maxFib];
        dp[0] = 1;
        dp[1] = 2;

        for (int i = 2; i < maxFib; i++) {
            dp[i] = (dp[i - 1] + dp[i - 2]) & mod;
        }

        int n = A.length;
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            mod = (1 << B[i]) - 1;
            res[i] = dp[A[i] - 1] & mod;
        }

        return res;
    }

    /**
     * CommonPrimeDivisors.
     * https://app.codility.com/programmers/lessons/12-euclidean_algorithm/common_prime_divisors/
     * Explanation: https://www.youtube.com/watch?v=F_th-1Rza_s&t=539s
     * */
    public int solution15(int[] A, int[] B) {
        int res = 0;
        for (int i = 0; i < A.length; i++) {
            int a = A[i];
            int b = B[i];
            int d = gcd(a, b);
            int e;

            while ((e = gcd(a, d)) != 1) {
                a /= e;
            }
            while ((e = gcd(b, d)) != 1) {
                b /= e;
            }

            // If they have same prime factor, value of both will be 1.
            if (a == b) {
                res += 1;
            }
        }

        return res;
    }

    /**
     * ChocolatesByNumbers.
     * https://app.codility.com/programmers/lessons/12-euclidean_algorithm/chocolates_by_numbers/
     * They will meet their LCM.
     * */
    public int solution16(int N, int M) {
        int d = gcd(N, M);
        long m = 1;
        m = m * N * M;
        long l = m / d;

        return (int) (l / M);
    }

    private int gcd(int a, int b) {
        if (a < b) {
            int t = a;
            a = b;
            b = t;
        }

        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }

        return a;
    }

}
