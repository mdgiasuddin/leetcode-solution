package bit_operation;

public class BitOperation {

    public static void main(String[] args) {

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
}
