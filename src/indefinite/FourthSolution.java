package indefinite;

import java.util.ArrayList;
import java.util.Arrays;
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
