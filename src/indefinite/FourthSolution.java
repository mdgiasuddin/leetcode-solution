package indefinite;

import pair.Pair;
import tree.TreeNode;

import java.util.*;

public class FourthSolution {

    public int findDuplicate(int[] nums) {
        int n = nums.length;
        int sum = n * (n - 1) / 2;
        for (int num : nums) {
            sum -= num;
        }
        return sum;
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

    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        combine(result, new ArrayList<>(), 1, n, k);
        return result;
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

    public int minJumps(int[] arr) {
        if (arr.length == 1)
            return 0;

        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (j == i + 1 || arr[j] == arr[i]) {
                    graph.get(i).add(j);
                    graph.get(j).add(i);
                }
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[arr.length];
        int[] distance = new int[arr.length];
        queue.add(0);
        visited[0] = true;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v : graph.get(u)) {
                if (!visited[v]) {
                    queue.add(v);
                    distance[v] = 1 + distance[u];
                    visited[v] = true;
                    if (v == arr.length - 1)
                        return distance[v];
                }
            }
        }

        return distance[distance.length - 1];
    }

    public boolean PredictTheWinner(int[] nums) {
        Pair[][] dp = new Pair[nums.length][nums.length];
        for (int i = 0; i < nums.length; i++) {
            dp[i][i] = new Pair(nums[i], 0);
        }
        for (int i = 0; i < nums.length - 1; i++) {
            dp[i][i + 1] = new Pair(Math.max(nums[i], nums[i + 1]), Math.min(nums[i], nums[i + 1]));
        }

        int i = 0, j = 2, initJ = j;
        while (!(i == 0 && j == nums.length - 1)) {
            int first, second;
            if (nums[i] + dp[i + 1][j].second > nums[j] + dp[i][j - 1].second) {
                first = nums[i] + dp[i + 1][j].second;
                second = dp[i + 1][j].first;
            } else {
                first = nums[j] + dp[i][j - 1].second;
                second = dp[i][j - 1].first;
            }
            dp[i][j] = new Pair(first, second);
            i++;
            j++;
            if (j == nums.length) {
                i = 0;
                j = ++initJ;
            }
        }

        return dp[0][nums.length - 1].first >= dp[0][nums.length - 1].second;
    }

    public int maxProfit2(int[] prices) {
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                profit += prices[i] - prices[i - 1];
            }
        }
        return profit;
    }

    public int maxProfit(int[] prices) {
        int first = 0, second = 0;

        int buy = prices[0];
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > buy) {
                if (prices[i] - buy > first) {
                    second = first;
                    first = prices[i] - buy;
                } else if (prices[i] - buy > second) {
                    second = prices[i] - buy;
                }
            } else {
                buy = prices[i];
            }
        }

        return 0;
    }

    // Leetcode problem: 554
    /*
     * For every gap position found the total gap in that position.
     * Then found maximum gap in any position.
     * Break the wall through that position.
     * */
    public int leastBricks(List<List<Integer>> wall) {

        Map<Integer, Integer> gaps = new HashMap<>();

        for (List<Integer> row : wall) {
            int pos = 0;

            for (int i = 0; i < row.size() - 1; i++) {
                pos += row.get(i);
                int gap = gaps.getOrDefault(pos, 0);

                gaps.put(pos, gap + 1);
            }
        }

        int maxGap = 0;
        for (Map.Entry<Integer, Integer> entry : gaps.entrySet()) {
            maxGap = Math.max(maxGap, entry.getValue());
        }

        return wall.size() - maxGap;
    }

    // Leetcode problem: 1461
    public boolean hasAllCodes(String s, int k) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i <= s.length() - k; i++) {
            set.add(s.substring(i, i + k));
        }

        return set.size() == Math.pow(2, k);
    }

}
