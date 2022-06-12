package indefinite;

import tree.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class FourthSolution {

    public int findDuplicate(int[] nums) {
        int n = nums.length;
        int sum = n * (n - 1) / 2;
        for (int num : nums) {
            sum -= num;
        }
        return sum;
    }

    public int nthUglyNumber(int n, int a, int b, int c) {
        int mulA, mulB, mulC;
        long uglyN = 0L;
        mulA = mulB = mulC = 1;
        for (int i = 1; i <= n; i++) {
            uglyN = Math.min(a * mulA, Math.min(b * mulB, c * mulC));
            if (uglyN == (long) a * mulA)
                mulA++;
            if (uglyN == (long) b * mulB)
                mulB++;
            if (uglyN == (long) c * mulC)
                mulC++;
        }

        return (int) uglyN;
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

        for (int j : a) {
            if (count == 0)
                current = j;

            if (current == j)
                count++;
            else
                count--;

            System.out.println("a[i] current count: " + j + " " + current + " " + count);
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

    }



}
