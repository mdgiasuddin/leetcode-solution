package array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArraySolution8 {

    // Leetcode problem: 764

    /**
     * Largest Plus Sign.
     * Explanation: https://www.youtube.com/watch?v=Sa5SUrf04g0&list=PLEvw47Ps6OBDcuLQ8FJZ7gc8rLOVXMjbL&index=7
     * Calculate prefix sum of four sides => left, right, up, down.
     * Take the minimum length of the four sides for each index.
     * */
    public int orderOfLargestPlusSign(int n, int[][] mines) {
        int[][] left = new int[n][n];
        int[][] right = new int[n][n];
        int[][] up = new int[n][n];
        int[][] down = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                left[i][j] = right[i][j] = up[i][j] = down[i][j] = 1;
            }
        }

        for (int[] mine : mines) {
            left[mine[0]][mine[1]] = right[mine[0]][mine[1]] = up[mine[0]][mine[1]] = down[mine[0]][mine[1]] = 0;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < n; j++) {
                left[i][j] = left[i][j] == 1 ? left[i][j] + left[i][j - 1] : 0;
                right[i][n - j - 1] = right[i][n - j - 1] == 1 ? right[i][n - j - 1] + right[i][n - j] : 0;
            }
        }

        for (int j = 0; j < n; j++) {
            for (int i = 1; i < n; i++) {
                up[i][j] = up[i][j] == 1 ? up[i][j] + up[i - 1][j] : 0;
                down[n - i - 1][j] = down[n - i - 1][j] == 1 ? down[n - i - 1][j] + down[n - i][j] : 0;
            }
        }

        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res = Math.max(res, Math.min(Math.min(left[i][j], right[i][j]), Math.min(up[i][j], down[i][j])));
            }
        }

        return res;
    }

    // Leetcode problem: 31

    /**
     * Next Permutation.
     * Explanation: https://www.youtube.com/watch?v=6qXO72FkqwM
     * */
    public void nextPermutation(int[] nums) {
        int n = nums.length;
        int i = n - 1;

        while (i > 0) {
            if (nums[i] > nums[i - 1]) {
                break;
            }

            i -= 1;
        }

        // No peek found. Reverse the array.
        if (i == 0) {
            reverseArray(nums, 0, n - 1);
            return;
        }

        /**
         * Smaller element is in the index (i - 1). Find the greater element than [i - 1] on the right side.
         * Swap with [i - 1]
         * Reverse the right side of i - 1 so that it will be ascending order.
         * */
        for (int j = n - 1; j >= i; j--) {
            if (nums[j] > nums[i - 1]) {
                int tmp = nums[i - 1];
                nums[i - 1] = nums[j];
                nums[j] = tmp;
                break;
            }
        }
        reverseArray(nums, i, n - 1);
    }

    private void reverseArray(int[] array, int l, int r) {
        while (l < r) {
            int tmp = array[l];
            array[l] = array[r];
            array[r] = tmp;

            l += 1;
            r -= 1;
        }
    }

    // Leetcode problem: 2483

    /**
     * Minimum Penalty for a Shop.
     * */
    public int bestClosingTime(String customers) {
        int n = customers.length();
        int[] prefix = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            prefix[i] += prefix[i - 1] + (customers.charAt(i - 1) == 'Y' ? 1 : 0);
        }

        int res = n;
        int minPenalty = n;
        for (int i = 0; i <= n; i++) {
            // Number of 'N' in the left = i - prefix[i], Number of 'Y' in the right = prefix[n] - prefix[i].
            int penalty = i - prefix[i] + prefix[n] - prefix[i];
            if (penalty < minPenalty) {
                minPenalty = penalty;
                res = i;
            }
        }

        return res;
    }

    // Leetcode problem: 2366

    /**
     * Minimum Replacements to Sort the Array
     * Explanation: https://www.youtube.com/watch?v=UCgbJzoSaSQ&t=1s
     * */
    public long minimumReplacement(int[] nums) {
        int n = nums.length;
        int right = nums[n - 1];
        long split = 0;

        for (int i = n - 2; i >= 0; i--) {
            int left = nums[i];
            if (left > right) {
                int parts = (left + right - 1) / right;
                split += parts - 1;
                right = left / parts;
            } else {
                right = left;
            }
        }

        return split;
    }

    // Leetcode problem: 1351

    /**
     * Count Negative Numbers in a Sorted Matrix.
     * This problem is similar to => Leetcode problem: 240
     * */
    public int countNegatives(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int r = 0;
        int c = n - 1;
        int res = 0;
        while (r < m && c >= 0) {
            if (grid[r][c] < 0) {
                res += (m - r);
                c -= 1;
            } else {
                r += 1;
            }
        }

        return res;
    }

    // Leetcode problem: 315

    /**
     * Count of Smaller Numbers After Self.
     * Merge sort algorithm (reverse order).
     * */
    public List<Integer> countSmaller(int[] nums) {
        int n = nums.length;

        // Build up a array with num & index.
        int[][] arr = new int[n][2];
        for (int i = 0; i < n; i++) {
            arr[i][0] = nums[i];
            arr[i][1] = i;
        }

        int[] count = new int[n];
        mergeSort(arr, 0, n - 1, count);

        List<Integer> countList = new ArrayList<>();
        for (int c : count) {
            countList.add(c);
        }
        return countList;

    }

    private void mergeSort(int[][] arr, int l, int r, int[] count) {
        if (l < r) {
            int m = l + (r - l) / 2;
            mergeSort(arr, l, m, count);
            mergeSort(arr, m + 1, r, count);
            merge(arr, l, m, r, count);
        }
    }

    private void merge(int[][] arr, int l, int m, int r, int[] count) {
        int[][] tmp = new int[r - l + 1][2];
        for (int i = l; i <= r; i++) {
            tmp[i - l][0] = arr[i][0];
            tmp[i - l][1] = arr[i][1];
        }

        int i = 0;
        int j = m + 1 - l;
        int k = l;

        while (i <= m - l && j <= r - l) {

            // Firs half contains greater number. So all the numbers in the right half >= jth index are smaller than the ith number.
            // So increase the count of ith number.
            if (tmp[i][0] > tmp[j][0]) {
                count[tmp[i][1]] += (r - l) - j + 1;
                arr[k][0] = tmp[i][0];
                arr[k][1] = tmp[i][1];

                i += 1;
            } else {
                arr[k][0] = tmp[j][0];
                arr[k][1] = tmp[j][1];

                j += 1;
            }

            k += 1;
        }

        while (i <= m - l) {
            arr[k][0] = tmp[i][0];
            arr[k][1] = tmp[i][1];

            i += 1;
            k += 1;
        }

        while (j <= r - l) {
            arr[k][0] = tmp[j][0];
            arr[k][1] = tmp[j][1];

            j += 1;
            k += 1;
        }
    }

    // Leetcode problem: 581

    /**
     * Shortest Unsorted Continuous Subarray.
     * Explanation: https://www.youtube.com/watch?v=YUt72nsLAIc
     * */
    public int findUnsortedSubarray(int[] nums) {
        int n = nums.length;
        int leftMax = nums[0];
        int rightMin = nums[n - 1];

        int leftIdx = -1;
        int rightIdx = -1;

        // Find the rightmost index of which there exist at least one element in the left > arr[index].
        for (int i = 0; i < n; i++) {
            leftMax = Math.max(leftMax, nums[i]);
            if (leftMax > nums[i]) {
                rightIdx = i;
            }
        }

        // Find the leftmost index of which there exist at least one element in the right < arr[index].
        for (int i = n - 1; i >= 0; i--) {
            rightMin = Math.min(rightMin, nums[i]);
            if (rightMin < nums[i]) {
                leftIdx = i;
            }
        }

        return leftIdx == -1 ? 0 : rightIdx - leftIdx + 1;
    }

    // Leetcode problem: 845

    /**
     * Longest Mountain in Array.
     * Explanation: https://www.youtube.com/watch?v=rh2Bkul2zzQ
     * */
    public int longestMountain(int[] arr) {
        int n = arr.length;
        int ans = 0;
        int i = 1;
        while (i < n - 1) {

            // Found a peak point.
            if (arr[i - 1] < arr[i] && arr[i] > arr[i + 1]) {
                int count = 1; // Initial value is important.
                int j = i;

                // Go backward.
                while (j > 0 && arr[j] > arr[j - 1]) {
                    j -= 1;
                    count += 1;
                }

                // Go forward.
                while (i < n - 1 && arr[i] > arr[i + 1]) {
                    i += 1;
                    count += 1;
                }

                ans = Math.max(ans, count);
            } else {
                i += 1;
            }
        }

        return ans;

    }

    // Leetcode problem: 697

    /**
     * Degree of an Array.
     * Explanation: https://www.youtube.com/watch?v=7wT5sFELf7Qo
     * */
    public int findShortestSubArray(int[] nums) {
        Map<Integer, Integer> countMap = new HashMap<>();
        Map<Integer, Integer> firstSeen = new HashMap<>();

        int minLen = 0;
        int degree = 0;

        for (int i = 0; i < nums.length; i++) {
            firstSeen.putIfAbsent(nums[i], i);
            int count = countMap.getOrDefault(nums[i], 0) + 1;
            countMap.put(nums[i], count);

            if (count > degree) {
                // In case of new maximum count, update the min len.
                degree = count;
                minLen = i - firstSeen.get(nums[i]) + 1;
            } else if (count == degree) {
                // For multiple maximum count get the minimum length.
                minLen = Math.min(minLen, i - firstSeen.get(nums[i]) + 1);
            }
        }

        return minLen;
    }

    // Leetcode problem: 493

    /**
     * Reverse Pairs.
     * Explanation: https://www.youtube.com/watch?v=Uf-27aFXhHY
     * Merge sort.
     * */
    public int reversePairs(int[] nums) {
        return mergeSort(nums, 0, nums.length - 1);
    }

    private int mergeSort(int[] nums, int l, int r) {
        int count = 0;

        if (l < r) {
            int m = l + (r - l) / 2;
            count += mergeSort(nums, l, m);
            count += mergeSort(nums, m + 1, r);
            count += merge(nums, l, m, r);
        }

        return count;
    }

    private int merge(int[] nums, int l, int m, int r) {
        int count = 0;

        int[] tmp = new int[r - l + 1];
        for (int i = l; i <= r; i++) {
            tmp[i - l] = nums[i];
        }

        int i = l;
        int j = m + 1;
        int k = l;

        // Do not merge first. Check the reverse condition.
        while (i <= m && j <= r) {
            if (tmp[i - l] > (long) 2 * tmp[j - l]) {
                count += m - i + 1;
                j += 1;
            } else {
                i += 1;
            }
        }

        // Merge 2 arrays.
        i = l;
        j = m + 1;
        while (i <= m && j <= r) {
            if (tmp[i - l] <= tmp[j - l]) {
                nums[k] = tmp[i - l];
                i += 1;
            } else {
                nums[k] = tmp[j - l];
                j += 1;
            }
            k += 1;
        }

        while (i <= m) {
            nums[k] = tmp[i - l];
            i += 1;
            k += 1;
        }

        while (j <= r) {
            nums[k] = tmp[j - l];
            j += 1;
            k += 1;
        }

        return count;
    }
}
