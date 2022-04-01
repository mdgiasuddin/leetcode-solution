package greedy;

public class GreedySolution {
    public static void main(String[] args) {

    }

    // Leetcode problem: 376
    /*
     * This solution is tricky
     * Count the ups and down of the array
     * If up or down come consecutively then consider it a single element
     * */
    public int wiggleMaxLength(int[] nums) {

        if (nums.length == 1)
            return 1;

        int up, down;
        up = down = 1;

        /*
         * If up or down values comes consecutively then up values then nothing will happen
         * If they come alternatively then up and down will be increased
         * */
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                up = down + 1;
            } else if (nums[i] < nums[i - 1]) {
                down = up + 1;
            }
        }

        return Math.max(up, down);

    }

}
