package binary_search;

import java.util.ArrayList;
import java.util.List;

public class BinarySearchSolution {

    public static void main(String[] args) {
        int[] array = {2, 6, 10, 15, 20, 30, 32, 36, 40, 45};
        BinarySearchSolution binarySearchSolution = new BinarySearchSolution();

        binarySearchSolution.findClosestElements(array, 3, 16);

    }

    // Leetcode problem: 167
    public boolean isPerfectSquare(int num) {

        int start = 1;
        int end = num;
        while (start <= end) {
            int mid = start + (end - start) / 2;

            if (num / mid == mid) {
                return mid * mid == num;
            } else if (num / mid < mid)
                end = mid - 1;
            else
                start = mid + 1;
        }
        return false;
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

    // Leetcode problem: 441
    public int arrangeCoins(int n) {
        long left = 1;
        long right = n;
        long ans = 1;

        while (left <= right) {
            long mid = left + (right - left) / 2;

            if (mid * (mid + 1) / 2 <= n) {
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return (int) ans;
    }

    // Leetcode problem: 658
    /*
     * The problem is tricky. Need careful attention
     * Find the starting position
     * */
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int left = 0;
        int right = arr.length - k;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (x - arr[mid] > arr[mid + k] - x) {
                // Inclusion of mid will not give optimal result, because [mid+1] to [mid+k] is optimal.
                // Right answer lies in [mid+1] to right
                left = mid + 1;
            } else {
                // Inclusion of [mid+k] is not optimal, so start value must be <<== mid.
                // Shift right to the mid.
                // Right answer lies in [left] to [mid+k-1]
                right = mid;
            }

        }

        List<Integer> result = new ArrayList<>();
        for (int i = left; i < left + k; i++) {
            result.add(arr[i]);
        }

        return result;
    }

}
