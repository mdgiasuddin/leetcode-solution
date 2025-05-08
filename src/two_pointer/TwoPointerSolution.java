package two_pointer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            /**if (current + people[right] <= limit) {
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
            return (p * p) % 1000000007;

        return (x * p * p) % 1000000007;
    }

    // Leetcode problem: 611
    public int triangleNumber(int[] nums) {
        Arrays.sort(nums);
        int count = 0;

        for (int i = nums.length - 1; i >= 2; i--) {
            int left = 0, right = i - 1;

            while (left < right) {
                if (nums[left] + nums[right] > nums[i]) {
                    count += (right - left);
                    right--;
                } else {
                    left++;
                }
            }
        }
        return count;
    }

    // Leetcode problem: 1268
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        Arrays.sort(products);

        List<List<String>> result = new ArrayList<>();

        int l = 0, r = products.length - 1;

        for (int i = 0; i < searchWord.length(); i++) {
            char ch = searchWord.charAt(i);

            l = binarySearch(products, ch, i, l, r, true);
            r = binarySearch(products, ch, i, l, r, false);

            int remain = r - l + 1;
            List<String> list = new ArrayList<>();

            for (int j = 0; j < Math.min(3, remain); j++)
                list.add(products[l + j]);

            result.add(list);
        }

        return result;
    }

    private int binarySearch(String[] products, char ch, int charIdx, int start, int end, boolean isStart) {

        int ans = -1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (charIdx >= products[mid].length()) {
                start = mid + 1;
            } else if (products[mid].charAt(charIdx) == ch) {
                ans = mid;
                if (isStart)
                    end = mid - 1;
                else
                    start = mid + 1;
            } else if (products[mid].charAt(charIdx) < ch) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }

        return ans == -1 ? (isStart ? end + 1 : start - 1) : ans;
    }

    // Leetcode problem: 633
    public boolean judgeSquareSum(int c) {
        int first = 0, second = mySqrt(c);

        while (first <= second) {
            if (first * first + second * second < c)
                first++;
            else if (first * first + second * second > c)
                second--;
            else
                return true;
        }

        return false;
    }

    // Leetcode problem: 69

    /**
     * Binary search.
     * The ranges are tricky.
     * */
    public int mySqrt(int x) {
        if (x == 0 || x == 1) {
            return x;
        }

        int left = 0, right = x / 2;

        while (left < right) {

            // Take the ceiling of middle.
            int middle = left + (right - left + 1) / 2;

            if (middle > x / middle) {
                right = middle - 1;
            } else {
                left = middle;
            }

        }
        return left;
    }
}
