package sliding;

public class SlidingWindowSolution3 {

    public static void main(String[] args) {
        SlidingWindowSolution3 solution = new SlidingWindowSolution3();
        int[] nums = {50, 20, 25, 30, 70, 90, 65, 72};
        System.out.println(solution.getLargestOnesGivenRange(nums, 50, 80));

    }

    /**
     * TikTok interview question.
     * The question is asked to Khalid.
     * Number of 1 bit of a subarray, of which the logical or value lies within the given range.
     * */
    public int getLargestOnesGivenRange(int[] nums, int low, int high) {
        int[] countBits = new int[32];
        int[] bitsOrValue;
        int l = 0;
        int r = 0;
        int res = 0;
        while (r < nums.length) {
            bitsOrValue = processNum(countBits, nums[r], 1);
            while (l <= r && bitsOrValue[1] > high) {
                bitsOrValue = processNum(countBits, nums[l], -1);
                l += 1;
            }

            if (bitsOrValue[1] >= low) {
                res = Math.max(res, bitsOrValue[0]);
            }
            r += 1;
        }

        return res;
    }

    private int[] processNum(int[] countBits, int num, int sign) {
        int i = 0;
        while (num != 0) {
            int bit = num & 1;
            countBits[i] += sign * bit;
            num >>>= 1;
            i += 1;
        }


        int orValue = 0;
        int bits = 0;
        for (i = countBits.length - 1; i >= 0; i--) {
            bits += countBits[1];
            orValue = (orValue << 1) + Math.min(1, countBits[i]);
        }

        return new int[]{bits, orValue};
    }
}
