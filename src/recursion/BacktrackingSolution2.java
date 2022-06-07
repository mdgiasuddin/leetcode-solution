package recursion;

import java.util.*;

public class BacktrackingSolution2 {

    public static void main(String[] args) {
        BacktrackingSolution2 backtrackingSolution2 = new BacktrackingSolution2();

        System.out.println(backtrackingSolution2.differByOne("100", "099"));
    }

    // Leetcode problem: 77
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        combine(n, k, result, new ArrayList<>(), 1);

        return result;
    }

    private void combine(int n, int k, List<List<Integer>> result, List<Integer> currentList, int current) {
        if (currentList.size() >= k) {
            result.add(new ArrayList<>(currentList));
            return;
        }

        for (int i = current; i <= n; i++) {

            // Check if required number of elements left to fill up k.
            if (n - i + 1 < k - currentList.size()) {
                return;
            }

            currentList.add(i);
            combine(n, k, result, currentList, i + 1);
            currentList.remove(currentList.size() - 1);
        }

    }

    // Leetcode problem: 52
    /*
     * This problem is similar to N-Queens (Leetcode problem: 51).
     * */
    public int totalNQueens(int n) {
        boolean[][] board = new boolean[n][n];

        return totalNQueens(0, board);
    }

    public int totalNQueens(int row, boolean[][] board) {
        if (row == board.length)
            return 1;

        int res = 0;
        for (int col = 0; col < board.length; col++) {
            if (isValidQueenPosition(board, row, col)) {
                board[row][col] = true;
                res += totalNQueens(row + 1, board);
                board[row][col] = false;
            }
        }

        return res;
    }

    private boolean isValidQueenPosition(boolean[][] board, int row, int col) {

        // Check the column of previous rows.
        for (int r = row - 1; r >= 0; r--) {
            if (board[r][col])
                return false;
        }

        // Check left diagonals of previous rows.
        int r = row - 1, c = col - 1;
        while (r >= 0 && c >= 0) {
            if (board[r][c])
                return false;
            r--;
            c--;
        }

        // Check right diagonals of previous rows.
        r = row - 1;
        c = col + 1;
        while (r >= 0 && c < board.length) {
            if (board[r][c])
                return false;
            r--;
            c++;
        }

        return true;
    }

    // Leetcode problem: 90
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        subsetsWithDup(nums, result, new ArrayList<>(), 0, -20);

        return result;
    }

    private void subsetsWithDup(int[] nums, List<List<Integer>> result, List<Integer> currentList, int current, Integer prev) {
        result.add(new ArrayList<>(currentList));
        for (int i = current; i < nums.length; i++) {
            if (nums[i] == prev)
                continue;

            currentList.add(nums[i]);
            prev = -20;
            subsetsWithDup(nums, result, currentList, i + 1, prev);
            prev = currentList.get(currentList.size() - 1);
            currentList.remove(currentList.size() - 1);
        }
    }

    public boolean splitString(String s) {
        for (int i = 0; i < s.length() - 1; i++) {
            String val = s.substring(0, i + 1);

            if (splitString(s, i + 1, val))
                return true;
        }
        return false;

    }

    private boolean splitString(String s, int idx, String prev) {
        if (idx == s.length())
            return true;

        for (int i = idx; i < s.length(); i++) {
            String val = s.substring(idx, i + 1);
            if (differByOne(prev, val) && splitString(s, i + 1, val))
                return true;
        }

        return false;
    }

    private boolean differByOne(String str1, String str2) {

        int idx1 = str1.length() - 1, idx2 = str2.length() - 1;

        int carry = 0;
        boolean first = true;
        while (idx1 >= 0 || idx2 >= 0 || carry > 0) {
            int digit1 = idx1 >= 0 ? str1.charAt(idx1) - '0' : 0;
            int digit2 = idx2 >= 0 ? str2.charAt(idx2) - '0' : 0;
            int digit = digit1 - (digit2 + carry);

            if (digit < 0) {
                digit += 10;
                carry = 1;
            } else {
                carry = 0;
            }

            if ((first && digit != 1) || (!first && digit != 0))
                return false;

            first = false;
            idx1--;
            idx2--;
        }

        return true;
    }

    // Leetcode problem: 1239
    public int maxLength(List<String> arr) {
        return maxLength(arr, new HashSet<>(), 0);
    }

    private int maxLength(List<String> arr, Set<Character> set, int idx) {
        if (idx == arr.size())
            return set.size();

        int res = 0;

        // If not overlap, include this string.
        if (!overlap(set, arr.get(idx))) {

            for (char ch : arr.get(idx).toCharArray()) {
                set.add(ch);
            }

            res = maxLength(arr, set, idx + 1);
            for (char ch : arr.get(idx).toCharArray()) {
                set.remove(ch);
            }
        }

        // Return the maximum, including or not including.
        return Math.max(res, maxLength(arr, set, idx + 1));

    }

    private boolean overlap(Set<Character> set, String str) {
        Set<Character> tmp = new HashSet<>(set);

        for (char ch : str.toCharArray()) {
            if (tmp.contains(ch)) {
                return true;
            }
            tmp.add(ch);
        }

        return false;
    }

    // Leetcode problem: 698
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for (int num : nums)
            sum += num;

        if (sum % k != 0)
            return false;

        Arrays.sort(nums);
        boolean[] used = new boolean[nums.length];

        return canPartitionKSubsets(nums, k, sum / k, 0, nums.length - 1, used);
    }

    private boolean canPartitionKSubsets(int[] nums, int k, int target, int current, int idx, boolean[] used) {
        if (k == 0)
            return true;

        if (current == target)
            return canPartitionKSubsets(nums, k - 1, target, 0, nums.length - 1, used);

        for (int i = idx; i >= 0; i--) {
            if (used[i] || current + nums[i] > target)
                continue;

            used[i] = true;
            if (canPartitionKSubsets(nums, k, target, current + nums[i], i - 1, used))
                return true;

            used[i] = false;
        }

        return false;
    }

    // Leetcode problem: 473
    /*
     * This problem is completely similar to partition to k subsets.
     * Just use k = 4 here.
     * */
    public boolean makesquare(int[] matchsticks) {
        int sum = 0;
        for (int matchstick : matchsticks) {
            sum += matchstick;
        }

        if (sum % 4 != 0)
            return false;

        Arrays.sort(matchsticks);
        boolean[] used = new boolean[matchsticks.length];

        return makesquare(matchsticks, 4, sum / 4, matchsticks.length - 1, 0, used);
    }

    private boolean makesquare(int[] matchsticks, int armsLeft, int target, int idx, int current, boolean[] used) {

        if (armsLeft == 0)
            return true;

        if (current == target) {
            return makesquare(matchsticks, armsLeft - 1, target, matchsticks.length - 1, 0, used);
        }

        for (int i = idx; i >= 0; i--) {
            if (used[i] || current + matchsticks[i] > target)
                continue;

            used[i] = true;
            if (makesquare(matchsticks, armsLeft, target, i - 1, current + matchsticks[i], used))
                return true;

            used[i] = false;
        }

        return false;
    }

}
