package binary_search;

import java.time.LocalDate;

public class BinarySearchSolution {

    public static void main(String[] args) {
        System.out.println(LocalDate.now().atStartOfDay());
    }

    // Leetcode problem: 410
    /*
     * For every value check whether the array can be splitted by m partition
     * */
    public int splitArray(int[] nums, int m) {

        // For any partition minimum sum cannot be less than the highest number
        // and cannot be greater than the sum of all elements
        int left = findMaxInArray(nums), right = findSumInOfElements(nums);

        int result = right;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            // If array can be splitted then update result and try in left side
            if (canSplit(nums, mid, m)) {
                result = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return result;
    }

    private boolean canSplit(int[] nums, int target, int m) {
        int subArray = 1, currentSum = 0;

        for (int num : nums) {
            currentSum += num;
            // If number cannot be taken in this partition then take it in next partition
            if (currentSum > target) {
                subArray++;
                currentSum = num;
            }
        }

        // Check whether total partition can be fit into m
        return subArray <= m;
    }

    private int findMaxInArray(int[] nums) {
        int max = Integer.MIN_VALUE;
        for (int num : nums) {
            max = Math.max(max, num);
        }
        return max;
    }

    private int findSumInOfElements(int[] nums) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        return sum;
    }

}
