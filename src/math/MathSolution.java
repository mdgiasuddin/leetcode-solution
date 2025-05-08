package math;

import java.util.HashMap;
import java.util.Map;

public class MathSolution {

    // Leetcode problem: 279

    /**
     * This solution is based on Legendre's Algorithm.
     * Every number can be represented as sum of 4 squares.
     * Any number can be represented as sum of 3 squares if it is not in the form: 4^a(8b + 7).
     * */
    public int numSquares(int n) {
        double sq = Math.sqrt(n);
        if (Math.ceil(sq) == Math.floor(sq)) {
            return 1;
        }

        while (n % 4 == 0) {
            n /= 4;
        }

        if (n % 8 == 7) {
            return 4;
        }

        for (int i = 1; i * i <= n; i++) {
            sq = Math.sqrt(n - i * i);
            if (Math.ceil(sq) == Math.floor(sq)) {
                return 2;
            }
        }

        return 3;
    }

    public int countAnagrams(String s) {
        int n = s.length();
        long[] factorials = new long[n + 1];
        long mod = 1000000007;
        factorials[0] = 1;
        for (int i = 1; i <= n; i++) {
            factorials[i] = (factorials[i - 1] * i) % mod;
        }

        String[] splitted = s.split(" ");
        long ans = 1;

        for (String word : splitted) {
            ans = (ans * countPermutation(word, factorials, mod)) % mod;
        }

        return (int) ans;
    }

    private long countPermutation(String word, long[] factorials, long mod) {
        int n = word.length();
        long ans = factorials[n];

        Map<Character, Integer> countMap = new HashMap<>();
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            int count = countMap.getOrDefault(ch, 0);
            countMap.put(ch, count + 1);
        }

        for (Map.Entry<Character, Integer> entry : countMap.entrySet()) {
            ans = (ans / factorials[entry.getValue()]) % mod;
        }

        return mod;
    }

    // Leetcode problem: 29

    /**
     * Divide Two Integers.
     * Explanation: https://www.youtube.com/watch?v=xefkgtd44hg&list=PLy38cn8b_xMfO7CGsUDIsYGps37yKaQ9X&index=11
     * Edge cases are very important.
     * */
    public int divide(int dividend, int divisor) {
        if ((dividend == 2147483647 && divisor == 1) || (dividend == -2147483648 && divisor == -1)) {
            return 2147483647;
        }
        if (dividend == -2147483648 && divisor == 1) {
            return -2147483648;
        }
        if (divisor == -2147483648 && dividend != -2147483648) {
            return 0;
        }

        boolean negative = (dividend > 0 && divisor < 0) || (dividend < 0 && divisor > 0);
        int dd = dividend == -2147483648 ? 2147483647 : Math.abs(dividend);
        int ds = divisor == -2147483648 ? 2147483647 : Math.abs(divisor);

        int quotient = 0;
        while (dd >= ds) {
            int counter = 1;
            int decrement = ds;

            while (dd >= decrement && decrement > 0) {
                dd -= decrement;
                quotient += counter;
                counter += counter;
                decrement += decrement;

            }
        }

        if (dividend == -2147483648 && dd + 1 >= ds) {
            quotient += 1;
        }
        if (negative) {
            quotient = -quotient;
        }

        return quotient;
    }

    // Leetcode problem: 1359

    /**
     * Count All Valid Pickup and Delivery Options.
     * Explanation: https://www.youtube.com/watch?v=OpgslsirW8s
     * There are total 2 * n slots.
     * For the first order, all slots are empty. Place 2 (P1, D1) to slots => slots * (slots - 1).
     * It will place P1 & D1 randomly. for any (P1, D1) position, there will be 2 permutations. => (P1, D1), (D1, P1).
     * One of them is valid. So, divide by 2.
     * */
    public int countOrders(int n) {
        long mod = 1000000007;
        long result = 1;

        long slots = n * 2L;
        while (slots > 0) {
            result = (result * (slots * (slots - 1)) / 2) % mod;
            slots -= 2;
        }

        return (int) result;
    }
}
