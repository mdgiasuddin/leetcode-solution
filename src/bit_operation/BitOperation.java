package bit_operation;

public class BitOperation {

    public static void main(String[] args) {

        BitOperation bitOperation = new BitOperation();

//        int[] nums = {1, 1, 1, 2, 2, 3, 4};
        int[] nums = {1};

        System.out.println();
    }

    // Leetcode problem: 371

    /**
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

    /**
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

    /**
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

    /**
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

    // Leetcode problem: 2433

    /**
     * Find The Original Array of Prefix Xor.
     * pref[i] = original[0] ^ original[1] ^ ... ^ original[i]
     * To get back original[i] XOR pref[i] with original[0] ^ original[1] ^ ... ^ original[i - 1]
     * original[0] ^ original[1] ^ ... ^ original[i - 1] part will be eliminated.
     * */
    public int[] findArray(int[] pref) {
        int prev = pref[0];
        for (int i = 1; i < pref.length; i++) {
            pref[i] ^= prev;
            prev ^= pref[i];
        }
        return pref;
    }

    // Leetcode problem: 2457

    /**
     * Minimum Addition to Make Integer Beautiful.
     * Explanation: https://www.youtube.com/watch?v=WnYxU-fDNe0&list=PLy38cn8b_xMfO7CGsUDIsYGps37yKaQ9X&index=13
     * */
    public long makeIntegerBeautiful(long n, int target) {
        long original = n;
        long count = 0;
        while (sumOfDigits(n) > target) {
            n = n / 10 + 1;
            count += 1;
        }

        return n * ((long) Math.pow(10, count)) - original;
    }

    private long sumOfDigits(long n) {
        long sum = 0;
        while (n > 0) {
            sum += n % 10;
            n /= 10;
        }

        return sum;
    }

    // Leetcode problem: 1470

    /**
     * Shuffle the Array.
     * Explanation:
     * This problem can easily be solved using extra space.
     * Since the elements of the array <= 1000 (Needs 10 bits), we can store both x & y in a same position.
     * */
    public int[] shuffle(int[] nums, int n) {
        for (int i = 0; i < n; i++) {
            nums[i] = (nums[i] << 10) | nums[i + n];
        }

        int j = 2 * n - 1;
        int mask = (1 << 10) - 1;
        for (int i = n - 1; i >= 0; i--) {
            nums[j] = nums[i] & mask;
            nums[j - 1] = nums[i] >> 10;

            j -= 2;
        }

        return nums;
    }
}
