package binary_search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BinarySearchSolution {

    public static void main(String[] args) {
        int[] array = {2, 6, 10, 15, 20, 30, 32, 36, 40, 45};
        BinarySearchSolution binarySearchSolution = new BinarySearchSolution();

        binarySearchSolution.findClosestElements(array, 3, 16);

    }

    // Leetcode problem: 4
    /*
     * Binary search
     * [1, 5, 10, 18, 20, 25] [3, 4, 6, 7, 9, 12] => [1, 3, 4, 5, 6 | 7 || 9 | 10, 12, 18, 20, 25]
        Left = 1, 3, 4, 5, 6, 7     Right = 9, 10, 12, 18, 20, 25
        Nums1 => 1, 5               10, 18, 20, 25
        Nums => 3, 4, 6, 7          9, 12
        left1 < right2 & left2 < right1
        Median Max(left1, left2) & Min(right1, right2)
     * Binary search until this condition fulfilled
     * */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

        if (nums1.length > nums2.length) {
            int[] temp = nums1;
            nums1 = nums2;
            nums2 = temp;
        }

        int low = 0, high = nums1.length;
        int middle = (nums1.length + nums2.length) / 2;

        // We are working with length every time. So, in indexing use -1 (cut1 - 1, cut2 - 1 ...)
        double median = Integer.MIN_VALUE;
        while (low <= high) {
            int cut1 = low + (high - low) / 2;
            int cut2 = middle - cut1;
            int left1 = cut1 == 0 ? Integer.MIN_VALUE : nums1[cut1 - 1];
            int left2 = cut2 == 0 ? Integer.MIN_VALUE : nums2[cut2 - 1];
            int right1 = cut1 == nums1.length ? Integer.MAX_VALUE : nums1[cut1];
            int right2 = cut2 == nums2.length ? Integer.MAX_VALUE : nums2[cut2];

            if (left1 > right2) {
                high = cut1 - 1;
            } else if (left2 > right1) {
                low = cut1 + 1;
            } else {
                // For odd number of elements middle is in less side. So median in right side.
                median = (nums1.length + nums2.length) % 2 == 0 ? (Math.max(left1, left2) + Math.min(right1, right2)) / 2.0
                        : Math.min(right1, right2);

                break;
            }
        }

        return median;
    }

    // Leetcode problem: 153
    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        int ans = nums[0];


        while (left <= right) {

            if (nums[left] < nums[right]) {
                // Minimum number can be found in previous step, so check if the new is minimum
                ans = Math.min(ans, nums[left]);
                break;
            }

            int mid = left + (right - left) / 2;

            // From next iteration mid will be omitted, so check if it contains the minimum.
            ans = Math.min(ans, nums[mid]);

            // If left part sorted then there is no chance of minimum value in left side.
            if (nums[left] <= nums[mid]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return ans;
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

    // Leetcode problem: 875
    /*
     * Try all value from 1 to the largest value by binary search
     * */
    public int minEatingSpeed(int[] piles, int h) {
        int left = 1;
        int right = findMaxInArray(piles);

        int ans = right;
        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (canEatAll(piles, mid, h)) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return ans;
    }

    private boolean canEatAll(int[] piles, int eat, int hour) {

        int hourTaken = 0;
        for (int pile : piles) {
            hourTaken += (pile + eat - 1) / eat; // Take the ceiling value
        }

        return hourTaken <= hour;
    }

    private int findMaxInArray(int[] nums) {
        int max = Integer.MIN_VALUE;
        for (int num : nums) {
            max = Math.max(max, num);
        }
        return max;
    }

    public int maximumRemovals(String s, String p, int[] removable) {
        int left = 0;
        int right = removable.length - 1;
        int ans = 0;
        Set<Integer> set = new HashSet<>();
        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (isSubsequence(s, p, set, removable, mid)) {
                ans = mid + 1; // mid is 0-indexed, but ans is length
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return ans;
    }

    private boolean isSubsequence(String str, String subSeq, Set<Integer> set, int[] removable, int mid) {
        set.clear();
        for (int i = 0; i <= mid; i++) {
            set.add(removable[i]);
        }

        int idx1 = 0;
        int idx2 = 0;

        while (idx1 < str.length() && idx2 < subSeq.length()) {
            if (str.charAt(idx1) != subSeq.charAt(idx2) || set.contains(idx1)) {
                idx1++;
                continue;
            }

            idx1++;
            idx2++;
        }

        return idx2 == subSeq.length();
    }

}
