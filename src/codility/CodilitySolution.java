package codility;

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
}
