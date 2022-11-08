package array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ArraySolution2 {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6};

        System.out.println(Arrays.toString(Arrays.copyOfRange(arr, 2, 4)));
    }

    // Leetcode problem: 45
    /*
     * Jump Game
     * BFS
     * */
    public int jump(int[] nums) {
//        if (nums.length < 2) {
//            return 0;
//        }
//        int[] array = new int[nums.length];
//        Arrays.fill(array, Integer.MAX_VALUE);
//
//        array[0] = 0;
//        for (int i = 0; i < nums.length - 1; i++) {
//            for (int j = i + 1; j <= i + nums[i] && j < array.length; j++) {
//                array[j] = Math.min(array[j], array[i] + 1);
//                if (j == array.length - 1)
//                    return array[j];
//            }
//        }
//
//        return array[array.length - 1];

        // This problem can be solved by O(n) time

        int currentReach, maxReach, jump;
        currentReach = maxReach = jump = 0;

        for (int i = 0; i < nums.length - 1; i++) {
            // If jump from there can reach more than previous calculated position
            if (i + nums[i] > maxReach) {
                maxReach = i + nums[i];
            }

            // If i reaches to the currently possible then 1 more jump needed to go further position
            if (i == currentReach) {
                jump++;
                currentReach = maxReach;
            }
        }

        return jump;
    }

    // Leetcode problem: 55
    /*
     * This problem can be solved by similar way to Jump game II (Leetcode problem: 45)
     * Since the Jump game II use BFS, it is inefficient
     * This problem doesn't need BFS, because shortest path is not required
     * */
    public boolean canJump(int[] nums) {

        if (nums.length == 1)
            return true;
        if (nums[0] == 0)
            return false;
        int last = nums.length - 1;
        for (int i = nums.length - 2; i >= 0; i--) {

            // Last position is reachable from ith position
            if (nums[i] >= last - i) {
                last = i;
            }
        }
        return last == 0;
    }

    // Leetcode problem: 48
    /*
     * Rotate matrix 90 degree clockwise
     * Loop range are important. i => (0 ... n / 2), j => (i ... n-1-i)
     * */
    public void rotate(int[][] matrix) {
        int n = matrix.length;

        for (int i = 0; i < n / 2; i++) {
            for (int j = i; j < n - 1 - i; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n - 1 - j][i];
                matrix[n - 1 - j][i] = matrix[n - 1 - i][n - 1 - j];
                matrix[n - 1 - i][n - 1 - j] = matrix[j][n - 1 - i];
                matrix[j][n - 1 - i] = temp;
            }
        }
    }

    // Leetcode problem: 41
    /*
     * First missing positive number
     * First reset the negative numbers to 0
     * For every number set that index to negative
     * If 0 is at an index then set such a negative number so that it cannot affect further calculations.
     * It may be any number less than -(len of the array)
     * If there is already a negative number do nothing
     * For negative number index take absolute value
     * */
    public int firstMissingPositive(int[] nums) {
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            if (nums[i] < 0) {
                nums[i] = 0;
            }
        }

        for (int num : nums) {
            int absNum = Math.abs(num);

            if (absNum - 1 >= 0 && absNum - 1 < len) {
                nums[absNum - 1] = nums[absNum - 1] > 0 ? -nums[absNum - 1] :
                        (nums[absNum - 1] == 0 ? -(len + 1) : nums[absNum - 1]);
            }
        }

        for (int i = 1; i <= len; i++) {
            if (nums[i - 1] >= 0) {
                return i;
            }
        }

        return len + 1;
    }

    // Leetcode problem: 54
    /*
     * Traverse left to right direction first and change direction and limit of traversed line
     * */
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> list = new ArrayList<>();
        int left = 0, right = matrix[0].length - 1, up = 0, down = matrix.length - 1;
        int dir = 1, i;
        while (left <= right && up <= down) {
            if (dir == 1) {
                i = left;
                while (i <= right) {
                    list.add(matrix[up][i]);
                    if (i == right) {
                        dir = 2;
                        up++;
                    }
                    i++;
                }
            }
            if (dir == 2) {
                i = up;
                while (i <= down) {
                    list.add(matrix[i][right]);
                    if (i == down) {
                        dir = 3;
                        right--;
                    }
                    i++;
                }
            }
            if (dir == 3) {
                i = right;
                while (i >= left) {
                    list.add(matrix[down][i]);
                    if (i == left) {
                        dir = 4;
                        down--;
                    }
                    i--;
                }
            }
            if (dir == 4) {
                i = down;
                while (i >= up) {
                    list.add(matrix[i][left]);
                    if (i == up) {
                        dir = 1;
                        left++;
                    }
                    i--;
                }
            }
        }

        return list;
    }

    // Leetcode problem: 56
    /*
     * Merge intervals
     * Sort the intervals according to start value
     * Take first interval as current value and add it to result
     * Try to merge current with other intervals
     * If merge is not possible add it to result make new current to it
     * */
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

        List<int[]> result = new ArrayList<>();
        int[] current = intervals[0];
        result.add(current);

        for (int[] interval : intervals) {

            if (current[1] >= interval[0]) {
                // Merge possible merge intervals by updating the end value
                current[1] = Math.max(current[1], interval[1]);
            } else {
                // Merge not possible. So add it to result and update current
                current = interval;
                result.add(current);
            }
        }

        return result.toArray(new int[result.size()][]);
    }

    // Leetcode problem: 57
    /*
     * Compare new interval with each interval
     * If new interval's end value is less than interval then return new interval + rest of the intervals
     * If new interval's start value is greater than interval then add the current interval and compare with next
     * Else modify new interval by merging
     * Finally add merged interval to the result
     * */
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();

        for (int i = 0; i < intervals.length; i++) {
            if (newInterval[1] < intervals[i][0]) {
                result.add(newInterval);
                result.addAll(Arrays.asList(Arrays.copyOfRange(intervals, i, intervals.length)));

                return result.toArray(new int[result.size()][]);
            } else if (newInterval[0] > intervals[i][1]) {
                result.add(intervals[i]);
            } else {
                newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
                newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
            }
        }

        result.add(newInterval);

        return result.toArray(new int[result.size()][]);
    }

    // Leetcode problem: 260
    /*
     * Find XOR of all numbers
     * Since exactly two numbers are unique, any set bit from XOR will come from either 1 unique number
     * So, we can differentiate all the number with respect to any of the set bit of XOR
     * The rightmost set bit can be found by '&' operation with its negative number
     * */
    public int[] singleNumber(int[] nums) {
        int xor = 0;
        int[] result = new int[2];

        for (int num : nums) {
            xor ^= num;
        }

        xor &= -xor;

        for (int num : nums) {
            if ((xor & num) == 0)
                result[0] ^= num;
            else
                result[1] ^= num;
        }

        return result;
    }


    // Leetcode problem: 42
    /*
     * Trapping Rain water
     * Find the maximum value
     * Then find water in both left and right side
     * */
    public int trap(int[] height) {
        int max = 0, n = height.length;
        for (int i = 1; i < n; i++) {
            if (height[i] > height[max])
                max = i;
        }
        int leftMax = 0;
        int totalWater = 0;

        // Leftmost pilar cannot trap water, so start from index 1
        for (int i = 1; i < max; i++) {

            // Update the left boundary
            if (height[i] > height[leftMax])
                leftMax = i;

            totalWater += Math.min(height[max], height[leftMax]) - height[i];
        }

        int rightMax = n - 1;
        // Rightmost pilar cannot trap water, so start with index n-2
        for (int i = n - 2; i > max; i--) {
            if (height[i] > height[rightMax])
                rightMax = i;
            totalWater += Math.min(height[max], height[rightMax]) - height[i];
        }
        return totalWater;
    }

}
