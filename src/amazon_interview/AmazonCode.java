package amazon_interview;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AmazonCode {

    public static void main(String[] args) {
        AmazonCode amazonCode = new AmazonCode();

        int[] nums = {1, 2, 3, 3, 4, 2, 3, 2, 3};
        int[] res = amazonCode.countDistinctElement(nums, 4);
//        System.out.println(Arrays.toString(res));

        System.out.println(amazonCode.fibonacci(15));
    }

    public void sortArrayZeroOne() {
        int[] array = {0, 1, 0, 1, 0, 0, 1, 1, 1, 0};

        int j = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 0) {
                int tmp = array[j];
                array[j] = 0;
                array[i] = tmp;

                j += 1;
            }
        }

        System.out.println(Arrays.toString(array));
    }

    public int[] countDistinctElement(int[] nums, int k) {
        int n = nums.length;
        Map<Integer, Integer> countMap = new HashMap<>();
        int[] res = new int[n - k + 1];

        int l = 0;
        int r = 0;
        while (r < k) {
            int c = countMap.getOrDefault(nums[r], 0);
            countMap.put(nums[r], c + 1);
            r += 1;
        }

        res[l] = countMap.size();
        while (r < n) {
            int c = countMap.get(nums[l]);
            if (c == 1) {
                countMap.remove(nums[l]);
            } else {
                countMap.put(nums[l], c - 1);
            }
            l += 1;

            c = countMap.getOrDefault(nums[r], 0);
            countMap.put(nums[r], c + 1);
            res[l] = countMap.size();

            r += 1;
        }

        return res;
    }

    /**
     * Calculate fibonacci(n) in O(logn) time.
     * Explanation: https://codeforces.com/blog/entry/90559
     * [fib(n+1)     fib(n)]    =      [1 1][fib(n)    fib(n-1)]
     * [fib(n)     fib(n-1)]           [1 0][fib(n-1)  fib(n-2)]
     * Where M = [fib(2)  fib(1)]   = [1 1]
     *           [fib(1)  fib(0)]     [1 0]
     * Finally: M^n = [fib(n+1)     fib(n)]
     *                [fib(n)     fib(n-1)]
     */
    public int fibonacci(int n) {
        if (n <= 1) {
            return n;
        }

        int[][] m = {{1, 1}, {1, 0}};
        int[][] mPowerN = power(m, n - 1);
        return mPowerN[0][0];
    }

    private int[][] power(int[][] m, int n) {
        if (n == 1) {
            return m;
        }

        int[][] p = power(m, n / 2);
        int[][] mul = multiply(p, p);
        if (n % 2 == 1) {
            return multiply(mul, m);
        }
        return mul;
    }

    private int[][] multiply(int[][] a, int[][] b) {
        int[][] res = new int[2][2];
        res[0][0] = a[0][0] * b[0][0] + a[0][1] * b[1][0];
        res[0][1] = a[0][0] * b[0][1] + a[0][1] * b[1][1];
        res[1][0] = a[1][0] * b[0][0] + a[1][1] * b[1][0];
        res[1][1] = a[1][0] * b[0][1] + a[1][1] * b[1][1];

        return res;
    }
}
