package bit_operation;

public class BitOperation {

    public static void main(String[] args) {

        BitOperation bitOperation = new BitOperation();

//        int[] nums = {1, 1, 1, 2, 2, 3, 4};
        int[] nums = {1};

        System.out.println();
    }

    // Leetcode problem: 371
    /*
     * Get sum without '+' and '-'.
     * This solution is tricky.
     * */
    public int getSum(int a, int b) {
        while (b != 0) {
            // Calculate carry and flow it to the next.
            int tmp = (a & b) << 1;
            a = a ^ b;
            b = tmp;
        }

        return a;
    }

    // Leetcode problem: 338
    /*
     * Dynamic programming.
     * */
    public int[] countBits(int n) {
        int[] bits = new int[n + 1];

        bits[0] = 0;
        for (int i = 1; i <= n; i++) {
            // DP[i] = DP[i/2] + '1' in rightmost bit.
            bits[i] = bits[i >> 1] + i % 2;
        }

        return bits;
    }

    // Leetcode problem: 389
    public char findTheDifference(String s, String t) {
        int xor = 0;
        for (char ch : s.toCharArray()) {
            xor ^= ch;
        }
        for (char ch : t.toCharArray()) {
            xor ^= ch;
        }
        return (char) xor;
    }

    // Leetcode problem: 201
    /*
     * Bitwise AND of Numbers Range
     * Code source: https://www.youtube.com/watch?v=-qrpJykY2gE&list=PLEJXowNB4kPwCPVjDv6KsAsThtDOCQUok&index=23
     * */
    public int rangeBitwiseAnd(int left, int right) {
        int count = 0;
        while (left != right) {
            left >>= 1;
            right >>= 1;

            count += 1;
        }

        return left << count;
    }

    // Leetcode problem: 1680
    /*
     * Concatenation of Consecutive Binary Numbers.
     * Explanation: https://www.youtube.com/watch?v=ttlNQ6YrdGs&list=PLEJXowNB4kPwa5VPvdQ1U3B2yaogEGDjX&index=32
     * */
    public int concatenatedBinary(int n) {
        long mod = 1000000007;
        int shift = 1;
        long sum = 0;
        for (int i = 1; i <= n; i++) {
            sum = ((sum << shift) % mod + i) % mod;
            if ((i & (i + 1)) == 0) {
                shift += 1;
            }
        }

        return (int) sum;
    }
}
