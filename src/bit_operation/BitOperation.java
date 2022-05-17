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
}
