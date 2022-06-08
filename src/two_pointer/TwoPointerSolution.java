package two_pointer;

import java.util.Arrays;

public class TwoPointerSolution {

    public static void main(String[] args) {
        TwoPointerSolution twoPointerSolution = new TwoPointerSolution();
    }

    // Leetcode problem: 881
    public int numRescueBoats(int[] people, int limit) {

        Arrays.sort(people);
        int left = 0;
        int right = people.length - 1;
        int current = 0;
//        int result = 1; for unlimited person
        int result = 0;

        while (left <= right) {

            // If a boat can carry unlimited number of person then this is the logic
            /*if (current + people[right] <= limit) {
                current += people[right];
                right--;
            } else if (current + people[left] <= limit) {
                current += people[left];
                left++;
            } else {
                result++;
                current = 0;
            }*/

            // Since at most two person can be carried, we have to change the logic
            // Try to take 2 person first. If not possible then take only right
            if (people[left] + people[right] <= limit) {
                left++;
            }
            right--;
            result++;

        }

        return result;
    }

    // Leetcode problem: 977
    public int[] sortedSquares(int[] nums) {
        int n = nums.length;
        int l = 0, r = n - 1, k = n - 1;
        int[] squares = new int[n];

        while (l <= r) {
            if (nums[l] * nums[l] > nums[r] * nums[r]) {
                squares[k] = nums[l] * nums[l];
                l++;
            } else {
                squares[k] = nums[r] * nums[r];
                r--;
            }
            k--;
        }

        return squares;
    }

    // Leetcode problem: 1498
    public int numSubseq(int[] nums, int target) {
        Arrays.sort(nums);

        long MOD = 1000000007;
        int r = nums.length - 1;

        long res = 0;
        for (int i = 0; i < nums.length; i++) {
            while (r >= i && nums[i] + nums[r] > target)
                r--;

            // For all the number getter than it has 2 choices. Include / Not include.
            if (i <= r) {
                res = (res + getPower(2, r - i)) % MOD;
            }
        }

        return (int) res;
    }

    private long getPower(long x, long n) {

        if (n == 0)
            return 1;

        long p = getPower(x, n / 2);
        if (n % 2 == 0)
            return ((p % 1000000007) * (p % 1000000007)) % 1000000007;

        return (x * (p % 1000000007) * (p % 1000000007)) % 1000000007;
    }
}
