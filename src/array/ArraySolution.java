package array;

import java.util.*;

public class ArraySolution {

    public static void main(String[] args) {
        ArraySolution arraySolution = new ArraySolution();

        int[] nums1 = {2, 3, 4, 5};
        int[] nums2 = {1};

        System.out.println(arraySolution.findMedianSortedArrays(nums1, nums2));
    }

    // Leetcode problem: 1
    /*
     * Use map
     * */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }

        return new int[]{};
    }

    public int[] twoSumII(int[] numbers, int target) {
        int[] result = new int[2];
        int left = 0, right = numbers.length - 1;
        while (left < right) {
            if (numbers[left] + numbers[right] == target) {
                result[0] = left;
                result[1] = right;
                break;
            } else if (numbers[left] + numbers[right] < target) {
                left++;
            } else {
                right--;
            }
        }
        return result;
    }

    // Leetcode problem: 11
    /*
     * Container with most water
     * Traverse from both left and right to middle
     * Left height is smaller than right then increase left, otherwise decrease right
     * */
    public int maxArea(int[] height) {
        int left = 0, right = height.length - 1, area = Integer.MIN_VALUE;

        while (left < right) {
            int h = Math.min(height[left], height[right]);
            int a = h * (right - left);
            area = Math.max(area, a);
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return area;
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
                median = (nums1.length + nums2.length) % 2 == 0 ? (Math.max(left1, left2) + Math.min(right1, right2)) / 2.0
                        : Math.min(right1, right2);

                break;
            }
        }

        return median;
    }

    // Leetcode problem: 15
    /*
     * 3 sum
     * Sort the array.
     * Fix 1 number from the last and traverse from both left and right to middle to find sum match
     * Change the fixed number & try in the same way
     * */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();

        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 2; i++) {

            // Skip the same first number
            if (i == 0 || nums[i] != nums[i - 1]) {
                int j = i + 1, k = nums.length - 1;
                while (j < k) {
                    int sum = nums[i] + nums[j] + nums[k];
                    if (sum == 0) {
                        result.add(List.of(nums[i], nums[j], nums[k]));

                        // To remove the duplicate combination skip the same number
                        while (j < k && nums[j] == nums[j + 1]) j++;
                        while (k > j && nums[k] == nums[k - 1]) k--;
                        j++;
                        k--;
                    }

                    if (sum < 0)
                        j++;
                    else
                        k--;
                }
            }
        }

        return result;
    }

    // Leetcode problem: 16
    /*
     * The problem is similar to 3 sum. Just find the sum of minimum difference to the target
     * */
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);

        int minDifference = Integer.MAX_VALUE, result = target;

        for (int i = 0; i < nums.length - 2; i++) {
            int j = i + 1, k = nums.length - 1;

            while (j < k) {
                int sum = nums[i] + nums[j] + nums[k];
                if (Math.abs(sum - target) < minDifference) {
                    minDifference = Math.abs(sum - target);
                    result = sum;
                }
                if (sum < target) {
                    j++;
                } else if (sum > target) {
                    k--;
                } else {
                    // Sum is equal to target then no need to continue
                    return target;
                }
            }
        }

        return result;
    }

    // Leetcode problem: 18
    /*
     * 4 Sum
     * This extended version of 3 Sum
     * Run an extra loop with 3 sum & similarly avoid duplicate
     * */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();

        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 3; i++) {

            // Remove duplicate in first number
            if (i == 0 || nums[i] != nums[i - 1]) {
                for (int j = i + 1; j < nums.length - 2; j++) {

                    // Remove duplicate in second number
                    if (j == i + 1 || nums[j] != nums[j - 1]) {
                        int k = j + 1, l = nums.length - 1;

                        while (k < l) {
                            int sum = nums[i] + nums[j] + nums[k] + nums[l];

                            if (sum == target) {
                                result.add(List.of(nums[i], nums[j], nums[k], nums[l]));

                                // To remove the duplicate combination skip the same number
                                while (k < l && nums[k] == nums[k + 1]) k++;
                                while (l > k && nums[l] == nums[l - 1]) l--;
                                k++;
                                l--;
                            } else if (sum < target) {
                                k++;
                            } else {
                                l--;
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    // Leetcode problem: 26
    /*
     * Save the first index where the element first shown
     * Then for the same value come erase the position by '_'
     * Whenever a different value come store it next to the first index
     * */
    public int removeDuplicates(int[] nums) {
        int firstIndex = 0;

        for (int i = 1; i < nums.length; i++) {

            // Different element comes. Store it next to the first index
            if (nums[firstIndex] != nums[i]) {
                firstIndex++;

                nums[firstIndex] = nums[i];

                // If first index is less than current position then erase ith position
                if (firstIndex != i)
                    nums[i] = '_';
            } else { // If same value comes erase it
                nums[i] = '_';
            }
        }
        return firstIndex + 1;
    }

    // Leetcode problem: 27
    /*
     * This problem similar to previous problem (Leetcode problem: 26)
     * */
    public int removeElement(int[] nums, int val) {
        int firstIndex = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == val) {
                nums[i] = '_';
            } else {
                if (firstIndex != i) {
                    nums[firstIndex] = nums[i];
                    nums[i] = '_';
                }
                firstIndex++;
            }
        }
        return firstIndex;

    }

    // Leetcode problem: 33
    /*
     * Search in rotated sorted array
     * Binary search
     * */
    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1, mid;

        while (left <= right) {
            mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[left] <= nums[mid]) {
                if (target >= nums[left] && target < nums[mid])
                    right = mid - 1;
                else
                    left = mid + 1;
            } else {
                if (target > nums[mid] && target <= nums[right])
                    left = mid + 1;
                else
                    right = mid - 1;
            }
        }
        return -1;
    }

    // Leetcode problem: 34
    /*
     * Find first & last position by binary search
     * */
    public int[] searchRange(int[] nums, int target) {
        int[] result = new int[]{-1, -1};

        result[0] = binarySearchFirst(nums, target, 0, nums.length - 1);
        result[1] = binarySearchLast(nums, target, 0, nums.length - 1);
        return result;
    }

    public int binarySearchFirst(int[] nums, int target, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if ((mid == 0 || nums[mid - 1] < target) && nums[mid] == target) {
                return mid;
            } else if (target > nums[mid]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    public int binarySearchLast(int[] nums, int target, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if ((mid == nums.length - 1 || nums[mid + 1] > target) && nums[mid] == target) {
                return mid;
            } else if (target < nums[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    // Leetcode problem: 39
    /*
     * Backtracking solution
     * */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);

        combinationSum(candidates, target, 0, result, new ArrayList<>());
        return result;
    }

    public void combinationSum(int[] candidates, int target, int start, List<List<Integer>> result, List<Integer> combination) {
        if (target == 0) {
            result.add(new ArrayList<>(combination));
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            if (candidates[i] > target)
                return;

            combination.add(candidates[i]);
            combinationSum(candidates, target - candidates[i], i, result, combination);
            combination.remove(combination.size() - 1);
        }
    }

    // Leetcode problem: 40
    /*
     * Combination sum (Leetcode problem: 39)
     * */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);

        List<List<Integer>> result = new ArrayList<>();
        combinationSum2(candidates, target, 0, result, new ArrayList<>());
        return result;
    }

    public void combinationSum2(int[] candidates, int target, int start, List<List<Integer>> result, List<Integer> combination) {
        if (target == 0) {
            result.add(new ArrayList<>(combination));
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            if (candidates[i] > target)
                return;
            if (i != start && candidates[i] == candidates[i - 1])
                continue;

            combination.add(candidates[i]);
            combinationSum2(candidates, target - candidates[i], i + 1, result, combination);
            combination.remove(combination.size() - 1);
        }
    }
}