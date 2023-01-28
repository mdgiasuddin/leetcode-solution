package math;

import java.util.HashMap;
import java.util.Map;

public class MathSolution {

    // Leetcode problem: 279
    /*
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
}
