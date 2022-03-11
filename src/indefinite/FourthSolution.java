package indefinite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FourthSolution {
//    public int mySqrt(int x) {
//        if (x < 2)
//            return x;
//        int start = 0, end = x / 2, mid;
//
//        while (start <= end) {
//            mid = start + (end - start + 1) / 2;
//
//            if (mid == x / mid)
//                return mid;
//            else if (mid > x / mid)
//                end = mid + 1;
//            else
//                start = mid;
//
//        }
//
//        return start;
//    }

    public int findDuplicate(int[] nums) {
        int n = nums.length;
        int sum = n * (n - 1) / 2;
        for (int num : nums) {
            sum -= num;
        }
        return sum;
    }

    int mySqrt(int x) {
        if (x == 0 || x == 1) {
            return x;
        }

        int left = 0, right = x / 2, middle;

        while (left < right) {
            middle = left + (right - left + 1) / 2;

            if (middle > x / middle) {
                right = middle - 1;
            } else {
                left = middle;
            }

        }
        return left;
    }

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

    public int nthUglyNumber(int n, int a, int b, int c) {
        int mulA, mulB, mulC;
        Long uglyN = 0L;
        mulA = mulB = mulC = 1;
        for (int i = 1; i <= n; i++) {
            uglyN = Long.valueOf(Math.min(a * mulA, Math.min(b * mulB, c * mulC)));
            if (uglyN == a * mulA)
                mulA++;
            if (uglyN == b * mulB)
                mulB++;
            if (uglyN == c * mulC)
                mulC++;
        }

        return uglyN.intValue();
    }

    public int countDigitOne(int n) {
        int count = 0, power = 1, power10, rem;

        while (n >= power) {
            power10 = power * 10;
            count += ((n / power10) * power);
            rem = n % power10;
            if (rem >= 2 * power - 1)
                count += power;
            else if (rem >= power)
                count += (rem - power + 1);
            power *= 10;
        }

        return count;
    }

    public int fib(int n) {
        if (n <= 1)
            return n;

        int fib_1 = 1, fib_2 = 0, fibN = 1;
        for (int i = 2; i <= n; i++) {
            fibN = fib_1 + fib_2;
            fib_2 = fib_1;
            fib_1 = fibN;
        }
        return fibN;
    }

    int rangeSum = 0;

    public int rangeSumBST(TreeNode root, int low, int high) {
        rangeSumBSTAux(root, low, high);
        return rangeSum;
    }

    public void rangeSumBSTAux(TreeNode root, int low, int high) {
        if (root == null)
            return;
        if (root.val >= low && root.val <= high) {
            rangeSum += root.val;
            rangeSumBSTAux(root.left, low, high);
            rangeSumBSTAux(root.right, low, high);
        }
        if (root.val < low)
            rangeSumBSTAux(root.right, low, high);
        else if (root.val > high)
            rangeSumBSTAux(root.left, low, high);
    }

    int index = -1, currentCount = 1, maxCount = 1;
    TreeNode prev = null;

    public int[] findMode(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        result.add(root.val);

        findMode(root, result);
        int[] resultArray = new int[Math.max(1, index + 1)];
        for (int i = 0; i < result.size(); i++) {
            resultArray[i] = result.get(i);
        }

        return resultArray;
    }

    public void findMode(TreeNode root, List<Integer> result) {
        if (root == null)
            return;

        findMode(root.left, result);
        if (prev != null) {
            if (prev.val == root.val) {
                currentCount++;

            } else {
                currentCount = 1;
            }

            System.out.println("current: " + currentCount + " val: " + root.val + " max: " + maxCount);
            if (currentCount > maxCount) {
                result.set(0, root.val);
                maxCount = currentCount;
                index = 0;
            } else if (currentCount == maxCount) {
                if (index + 1 == result.size())
                    result.add(root.val);
                else
                    result.set(index + 1, root.val);
                index++;
            }
            System.out.println("List: " + result);
        }
        prev = root;
        findMode(root.right, result);
    }

    public int trap(int[] height) {
        int max = 0, n = height.length;
        for (int i = 1; i < n; i++) {
            if (height[i] > height[max])
                max = i;
        }
        int leftMax = 0;
        int totalWater = 0;

        for (int i = 1; i < max; i++) {
            if (height[i] > height[leftMax])
                leftMax = i;

            totalWater += Math.min(height[max], height[leftMax]) - height[i];
        }

        int rightMax = n - 1;
        for (int i = n - 2; i > max; i--) {
            if (height[i] > height[rightMax])
                rightMax = i;
            totalWater += Math.min(height[max], height[rightMax]) - height[i];
        }
        return totalWater;
    }

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();

        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 2; i++) {
            if (i == 0 || nums[i] != nums[i - 1]) {
                int j = i + 1, k = nums.length - 1;
                while (j < k) {
                    int sum = nums[i] + nums[j] + nums[k];
                    if (sum == 0) {
                        result.add(List.of(nums[i], nums[j], nums[k]));
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

    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        List<Integer> dp = new ArrayList<>();
        for (int i = 0; i < n; i++)
            dp.add(triangle.get(n - 1).get(i));

        for (int i = triangle.size() - 2; i >= 0; i--) {
            for (int j = 0; j < triangle.get(i).size(); j++) {
                dp.set(j, Math.min(dp.get(j), dp.get(j + 1)) + triangle.get(i).get(j));
            }
        }

        return dp.get(0);
    }

    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

        List<int[]> result = new ArrayList<>();
        int[] current = intervals[0];
        result.add(current);

        for (int[] interval : intervals) {

            if (current[1] >= interval[0]) {
                current[1] = Math.max(current[1], interval[1]);
            } else {
                current = interval;
                result.add(current);
            }
        }

        return result.toArray(new int[result.size()][]);
    }

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

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();

        permute(nums, new boolean[nums.length], new ArrayList<>(), result);
        return result;
    }

    public void permute(int[] nums, boolean[] used, List<Integer> permutation, List<List<Integer>> result) {
        if (permutation.size() == nums.length) {
            result.add(new ArrayList<>(permutation));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            System.out.print("begin: " + i + " ");
            UtilClass.printBooleanArray(used);
            if (used[i])
                continue;

            used[i] = true;
            permutation.add(nums[i]);
            System.out.println("num add: " + nums[i]);
            permute(nums, used, permutation, result);
            System.out.println("i: " + i);
            used[i] = false;
            System.out.println("nums remove: " + permutation.get(permutation.size() - 1));
            permutation.remove(permutation.size() - 1);
            UtilClass.printBooleanArray(used);
        }
    }


    // have to see after prayer
    public void solveSudoku(char[][] board) {
        solver(0, 0, board);
    }

    public boolean solver(int a, int b, char[][] board) {
        for (int y = a; y < board.length; y++) {
            for (int x = b; x < board.length; x++) {
                if (board[y][x] == '.') {
                    int[] options = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
                    for (int i = 0; i < 9; i++) {
                        char found = board[y][i];
                        if (found != '.') {
                            options[found - 49] = 0;
                        }
                        found = board[i][x];
                        if (found != '.') {
                            options[found - 49] = 0;
                        }
                        found = board[i / 3 + ((y / 3) * 3)][((x / 3) * 3) + i % 3];
                        if (found != '.') {
                            options[found - 49] = 0;
                        }
                    }
                    for (int numoptions : options) {
                        if (numoptions == 0) {
                            continue;
                        }
                        board[y][x] = (char) (numoptions + 48);
                        if (x == 8 && y == 8 || solver(y, x + 1, board)) {
                            return true;
                        }
                    }
                    board[y][x] = '.';
                    return false;
                } else if (x == 8 && y == 8) {
                    return true;
                }
            }
            b = 0;
        }
        return false;
    }

    public boolean isBalanced(TreeNode root) {
        return findHeight(root) != -1;
    }

    public int findHeight(TreeNode node) {
        if (node == null) {
            return 0;
        }

        int leftHeight = 1 + findHeight(node.left);
        int rightHeight = 1 + findHeight(node.right);

        if (leftHeight == 0 || rightHeight == 0 || Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }

        return Math.max(leftHeight, rightHeight);

    }

    public void generateParenthesis(List<String> result, String current, int n, int open, int close) {
        if (open + close == 2 * n) {
            result.add(current);
            return;
        }
        if (open < n)
            generateParenthesis(result, current + "(", n, open + 1, close);
        if (close < open)
            generateParenthesis(result, current + ")", n, open, close + 1);
    }

    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        generateParenthesis(result, "", n, 0, 0);

        return result;
    }

    public int uniquePaths(int m, int n) {
        int N = m + n - 2, r = Math.min(m, n) - 1;
        if (N <= 1)
            return 1;

        Long result = 1L;
        for (int i = 1; i <= r; i++) {
            result = (result * (N - i + 1)) / i;
        }

        return result.intValue();
    }

    public void combine(List<List<Integer>> result, List<Integer> currentList, int cur, int n, int k) {
        if (currentList.size() == k) {
            result.add(new ArrayList<>(currentList));
            return;
        }

        for (int i = cur; i <= n; i++) {
            if (k - currentList.size() <= n - i + 1) {
                currentList.add(i);
                combine(result, currentList, i + 1, n, k);
                currentList.remove(currentList.size() - 1);
            }
        }
    }

    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        combine(result, new ArrayList<>(), 1, n, k);
        return result;
    }

    public boolean exist(char[][] board, boolean[][] visited, int cur, int row, int col, String word) {
        if (cur == word.length())
            return true;
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length
                || board[row][col] != word.charAt(cur) || visited[row][col])
            return false;

        visited[row][col] = true;
        System.out.println("row col " + board[row][col] + " " + word.charAt(cur));
        if (exist(board, visited, cur + 1, row - 1, col, word)
                || exist(board, visited, cur + 1, row, col - 1, word)
                || exist(board, visited, cur + 1, row + 1, col, word)
                || exist(board, visited, cur + 1, row, col + 1, word))
            return true;
        visited[row][col] = false;
        return false;
    }

    public boolean exist(char[][] board, String word) {
        int row = board.length, col = board[0].length;
        boolean[][] visited = new boolean[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++)
                if (exist(board, visited, 0, i, j, word))
                    return true;
        }
        return false;
    }

    public String reverseWords(String s) {
        int start = 0, end = s.length() - 1;
        while (start < end) {
            char temp = s.charAt(start);
        }

        return null;
    }

    public int majorityElement(int[] a) {

        // Moore's Voting Algorithm//

        int count = 0;
        int current = 0;

        for (int i = 0; i < a.length; i++) {
            if (count == 0)
                current = a[i];

            if (current == a[i])
                count++;
            else
                count--;

            System.out.println("a[i] current count: " + a[i] + " " + current + " " + count);
        }
        return current;
    }

    public int reverseBits(int n) {
        int reverse = 0;
        for (int i = 0; i < 32; i++) {
            reverse <<= 1;
            reverse |= (n | 1);
            n >>>= 1;
        }

        return reverse;
    }

//    public boolean canJump(int[] nums) {
//        if (nums.length < 2) {
//            return true;
//        }
//
//        boolean[] jump = new boolean[nums.length];
//        jump[0] = true;
//
//        for (int i = 0; i < nums.length - 1; i++) {
//            if (jump[i] == false)
//                return false;
//            for (int j = 1; i + j < nums.length && j <= nums[i]; j++)
//                jump[i + j] = true;
//        }
//
//        return jump[jump.length - 1];
//    }

    public boolean canJump(int[] nums) {

        if (nums.length == 1)
            return true;
        if (nums[0] == 0)
            return false;
        int last = nums.length - 1;
        for (int i = nums.length - 2; i >= 0; i--) {
            if (nums[i] >= last - i) {
                last = i;
            }
        }
        return last == 0;
    }

    public void removeDuplicateFromList(List<Integer> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i).equals(list.get(j))) {
                    list.remove(j);
                    j--;
                }
            }
        }

        System.out.println("Unique list: " + list);
    }



}
