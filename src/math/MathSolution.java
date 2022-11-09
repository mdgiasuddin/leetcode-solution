package math;

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
}
