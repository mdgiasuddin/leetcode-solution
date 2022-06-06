package array;

import java.util.*;

public class ArraySolution {

    public static void main(String[] args) {
        ArraySolution arraySolution = new ArraySolution();

        int[] nums1 = {1, 2, 3};
        int[] nums2 = {1};

        System.out.println(arraySolution.permute(nums1));
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
    public int removeDuplicates(int[] nums) {
        int res = 1;

        for (int i = 1; i < nums.length; i++) {
            // If new value comes store it to res position.
            if (nums[i] != nums[i - 1]) {
                nums[res] = nums[i];
                res++;
            }
        }

        return res;
    }

    // Leetcode problem: 27
    public int removeElement(int[] nums, int val) {
        int res = 0;

        for (int num : nums) {
            // num != val take it.
            if (num != val)
                nums[res++] = num;
        }
        return res;
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

    // Leetcode problem: 46
    /*
     * Permutation
     * Backtracking solution
     * */
    public List<List<Integer>> permute(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        boolean[] taken = new boolean[nums.length];

        permute(nums, result, new ArrayList<>(), taken);

        return result;
    }

    private void permute(int[] nums, List<List<Integer>> result, List<Integer> permuteSoFar, boolean[] taken) {

        if (permuteSoFar.size() == nums.length) {
            result.add(new ArrayList<>(permuteSoFar));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (!taken[i]) {
                permuteSoFar.add(nums[i]);
                taken[i] = true;
                permute(nums, result, permuteSoFar, taken);
                taken[i] = false;
                permuteSoFar.remove(permuteSoFar.size() - 1);
            }
        }
    }

    // Leetcode problem: 47
    /*
     * Backtracking solution
     * */
    public List<List<Integer>> permuteUnique(int[] nums) {
        Map<Integer, Integer> countMap = new HashMap<>();

        for (int num : nums) {
            if (countMap.containsKey(num)) {
                countMap.replace(num, countMap.get(num) + 1);
            } else {
                countMap.put(num, 1);
            }
        }

        List<List<Integer>> result = new ArrayList<>();
        permuteUnique(countMap, result, new ArrayList<>(), nums.length);

        return result;
    }

    private void permuteUnique(Map<Integer, Integer> countMap, List<List<Integer>> result, List<Integer> permuteSoFar, int requiredLength) {
        if (permuteSoFar.size() == requiredLength) {
            result.add(new ArrayList<>(permuteSoFar));
            return;
        }

        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() > 0) {
                permuteSoFar.add(entry.getKey());
                countMap.replace(entry.getKey(), entry.getValue() - 1);

                permuteUnique(countMap, result, permuteSoFar, requiredLength);

                permuteSoFar.remove(permuteSoFar.size() - 1);
                countMap.replace(entry.getKey(), entry.getValue() + 1);
            }
        }
    }
}
