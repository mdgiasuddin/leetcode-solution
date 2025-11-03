package binary_search;

public class BinarySearchSolution3 {

    // Leetcode problem: 2594

    /**
     * Minimum Time to Repair Cars.
     * Explanation: https://www.youtube.com/watch?v=RHct_Pz9EBo
     *
     */
    public long repairCars(int[] ranks, int cars) {
        long l = 1;
        long r = (long) ranks[0] * cars * cars;

        long res = -1;
        while (l <= r) {
            long m = l + (r - l) / 2;
            if (repairedCars(ranks, m) >= cars) {
                res = m;
                r = m - 1;
            } else {
                l = m + 1;
            }
        }

        return res;
    }

    private long repairedCars(int[] ranks, long time) {
        long count = 0;
        for (int rank : ranks) {
            count += (long) Math.sqrt((double) time / rank);
        }

        return count;
    }


    // Leetcode problem: 2560

    /**
     * House Robber IV.
     * Explanation: https://www.youtube.com/watch?v=OHZqAc6h3Us
     */
    public int minCapability(int[] nums, int k) {
        int l = 1;
        int r = 0;
        for (int num : nums) {
            r = Math.max(r, num);
        }

        int res = 0;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (isCapable(nums, m, k)) {
                res = m;
                r = m - 1;
            } else {
                l = m + 1;
            }
        }

        return res;
    }

    private boolean isCapable(int[] nums, int value, int k) {
        int i = 0;
        int count = 0;
        while (i < nums.length) {
            if (nums[i] <= value) {
                count += 1;
                i += 2;
            } else {
                i += 1;
            }
        }

        return count >= k;
    }

    // Leetcode problem: 2226

    /**
     * Maximum Candies Allocated to K Children.
     */
    public int maximumCandies(int[] candies, long k) {
        int l = 1;
        int r = 0;
        for (int candy : candies) {
            r = Math.max(r, candy);
        }

        int res = 0;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (canGetCandy(candies, m, k)) {
                res = m;
                l = m + 1;
            } else {
                r = m - 1;
            }
        }

        return res;
    }

    private boolean canGetCandy(int[] candies, int number, long k) {
        long persons = 0;

        for (int candy : candies) {
            persons += candy / number;
        }

        return persons >= k;
    }
}
