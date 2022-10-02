package array;

public class ArraySolution6 {
    public static void main(String[] args) {
        ArraySolution6 arraySolution6 = new ArraySolution6();

        int[] nums = {3, 5, 2, 1, 6, 4};
        arraySolution6.wiggleSort(nums);
    }

    // Leetcode problem: 1899
    public boolean mergeTriplets(int[][] triplets, int[] target) {
        boolean match1 = false;
        boolean match2 = false;
        boolean match3 = false;

        for (int[] triplet : triplets) {

            // If any off the value of triplet is greater than the target, merging will result greater value.
            // So ignore this.
            if (triplet[0] > target[0] || triplet[1] > target[1] || triplet[2] > target[2]) {
                continue;
            }

            // Check all 3 values are contained in other triplets.
            if (triplet[0] == target[0]) {
                match1 = true;
            }
            if (triplet[1] == target[1]) {
                match2 = true;
            }
            if (triplet[2] == target[2]) {
                match3 = true;
            }
        }

        return match1 && match2 && match3;
    }

    // Leetcode problem: 2017
    public long gridGame(int[][] grid) {
        int n = grid[0].length;

        long[] prefix1 = new long[n];
        long[] prefix2 = new long[n];

        prefix1[0] = grid[0][0];
        prefix2[0] = grid[1][0];
        for (int i = 1; i < n; i++) {
            prefix1[i] = grid[0][i] + prefix1[i - 1];
            prefix2[i] = grid[1][i] + prefix2[i - 1];
        }

        long res = Long.MAX_VALUE;
        // Calculate how many points robot2 get if robot1 moves down at ith index.
        // Then robot2 will get either top row point or bottom row point. Since robot2 plays optimally,
        // - get the max of these 2.
        // Since robot1 also plays optimally, get the minimum of all the max point robot2 can get.
        for (int i = 0; i < n; i++) {
            long top = prefix1[n - 1] - prefix1[i];
            long bottom = i == 0 ? 0 : prefix2[i - 1];

            long secondRobot = Math.max(top, bottom);
            res = Math.min(res, secondRobot);
        }

        return res;
    }

    // Leetcode problem: 280
    // Lintcode problem: 508
    public void wiggleSort(int[] nums) {

        int i = 0;
        while (i < nums.length - 1) {
            if (i % 2 == 0) {
                if (nums[i] > nums[i + 1]) {
                    int tmp = nums[i];
                    nums[i] = nums[i + 1];
                    nums[i + 1] = tmp;
                }
            } else {
                if (nums[i] < nums[i + 1]) {
                    int tmp = nums[i];
                    nums[i] = nums[i + 1];
                    nums[i + 1] = tmp;
                }
            }

            i += 1;
        }
    }
}
