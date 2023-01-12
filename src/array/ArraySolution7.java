package array;

import java.util.ArrayList;
import java.util.List;

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
}
