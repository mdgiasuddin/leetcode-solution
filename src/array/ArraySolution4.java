package array;

import java.util.*;

public class ArraySolution4 {
    public static void main(String[] args) {

//        ArraySolution4 arraySolution4 = new ArraySolution4();
    }

    // Leetcode problem: 240
    /*
     * Start from top-right or bottom left index
     * Compare with target
     * Move according to the target value
     * */
    public boolean searchMatrix(int[][] matrix, int target) {
        int row = 0, col = matrix[0].length - 1;

        while (row < matrix.length && col >= 0) {
            if (matrix[row][col] == target) {
                return true;
            }
            if (target < matrix[row][col]) {
                col--;
            } else {
                row++;
            }
        }

        // This can also be done starting from bottom-left index
        /*int row = matrix.length - 1, col = 0;

        while (row >= 0 && col < matrix[0].length) {
            if (matrix[row][col] == target) {
                return true;
            }
            if (target < matrix[row][col]) {
                row--;
            } else {
                col++;
            }
        }*/

        return false;
    }

    // Leetcode problem: 289
    /*
     * Traverse each index and update it according to the living cell of the surrounding neighbours
     * Since we don't want to use extra memory use (0->0 = 0, 0->1 = 2, 1->0 = 1, 1->1 = 3)
     * In second time traversing we change the 1, 2, 3 value
     * */
    public void gameOfLife(int[][] board) {
        int[][] neighbours = {
                {0, -1}, // left
                {0, 1}, // right
                {-1, 0}, // up
                {1, 0}, // down
                {-1, -1}, // up-left
                {-1, 1}, // up-right
                {1, 1}, // down-right
                {1, -1} // down-left
        };

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                int livingCell = numberOfLivingCells(board, neighbours, row, col);
                if (board[row][col] == 0) {
                    if (livingCell == 3) {
                        board[row][col] = 2;
                    } else {
                        board[row][col] = 0;
                    }
                } else {
                    if (livingCell == 2 || livingCell == 3) {
                        board[row][col] = 3;
                    } else {
                        board[row][col] = 1;
                    }
                }
            }
        }

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col] == 1) {
                    board[row][col] = 0;
                } else if (board[row][col] == 2 || board[row][col] == 3) {
                    board[row][col] = 1;
                }
            }
        }
    }

    public int numberOfLivingCells(int[][] board, int[][] neighbours, int row, int col) {

        int livingCell = 0;
        for (int[] neighbour : neighbours) {
            int neighbourRow = row + neighbour[0];
            int neighbourCol = col + neighbour[1];

            if (neighbourRow >= 0 && neighbourRow < board.length && neighbourCol >= 0 && neighbourCol < board[0].length) {
                livingCell += board[neighbourRow][neighbourCol] % 2;
            }
        }

        return livingCell;
    }

    // Leetcode problem: 304
    /*
    [
        [3, 0, 1, 4, 2],
        [5, 6, 3, 2, 1],
        [1, 2, 0, 1, 5],
        [4, 1, 0, 1, 7],
        [1, 0, 3, 0, 5]
    ],
    * Build up sum from (0, 0) to every index
    * */


    // Leetcode problem: 322
    /*
     * Dynamic programming
     * */
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;

        // Calculate the possible minimum coin number for every number 1 to amount
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                // If coin can be taken then update the dp[i]
                if (coin <= i) {
                    dp[i] = Math.min(dp[i], 1 + dp[i - coin]);
                }
            }
        }

        return dp[amount] == amount + 1 ? -1 : dp[amount];
    }

    // Leetcode problem: 300
    /*
     * Dynamic programming
     * */
    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        int[] seqIdx = new int[nums.length];

        // Build up an array to print the subsequence
        Arrays.fill(seqIdx, -1);

        for (int i = 1; i < nums.length; i++) {
            // Search the element which is less than i'th index element
            for (int j = i - 1; j >= 0; j--) {
                if (nums[i] > nums[j] && (1 + dp[j] > dp[i])) {
                        dp[i] = 1 + dp[j];

                        // Make parent[i] to j
                        seqIdx[i] = j;

                }
            }

        }

        int max = 0;
        for (int i = 0; i < dp.length; i++) {
            if (dp[i] > dp[max])
                max = i;
        }

        System.out.print("increasing sequence: ");
        int idx = max;
        while (idx >= 0) {
            System.out.print(nums[idx] + " ");
            idx = seqIdx[idx];
        }
        System.out.println();

        return dp[max];
    }

    // Leetcode problem: 396
    public int maxRotateFunction(int[] nums) {
        int ans, rotateSum = 0, sum = 0;

        int n = nums.length;
        for (int i = 0; i < n; i++) {
            rotateSum += i * nums[i];
            sum += nums[i];
        }

        ans = rotateSum;

        for (int i = 1; i < n; i++) {
            // After rotate last element goes to first position, so its contribution becomes 0
            // So, subtract the previous contribution
            // The contribution of other elements is increase by 1 multiplication
            // So, add it. (Sum - last element)
            // Every time last element is updated. [n-i]
            rotateSum -= (n - 1) * nums[n - i];
            rotateSum += (sum - nums[n - i]);

            // Update ans
            ans = Math.max(ans, rotateSum);
        }

        return ans;
    }

    // Leetcode problem: 1260
    public List<List<Integer>> shiftGrid(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length;
        int[] tempArray = new int[m * n];

        for (int row = 0; row < m; row++) {
            for (int col = 0; col < n; col++) {
                int newPos = (row * n + col + k) % (m * n);
                tempArray[newPos] = grid[row][col];
            }
        }

        List<List<Integer>> result = new ArrayList<>();

        for (int row = 0; row < m; row++) {
            List<Integer> currentRow = new ArrayList<>();
            for (int col = 0; col < n; col++) {
                currentRow.add(tempArray[row * n + col]);
            }
            result.add(currentRow);
        }

        return result;
    }

    // Leetcode problem: 347
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            if (!map.containsKey(num)) {
                map.put(num, 1);
            } else {
                int newCount = 1 + map.get(num);
                map.replace(num, newCount);
            }
        }

        ArrayList<Integer>[] freq = new ArrayList[nums.length + 1];
        for (int i = 0; i <= nums.length; i++) {
            freq[i] = new ArrayList<>();
        }

        // Create a list against the count value.
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            freq[(entry.getValue())].add(entry.getKey());
        }

        List<Integer> result = new ArrayList<>();

        // Here added means how many elements added to the result so far.
        int added = 0;
        for (int i = nums.length; i >= 0; i--) {
            if (freq[i].size() > 0) {

                // Increase the value of added and add the values.
                added += freq[i].size();
                result.addAll(freq[i]);

                if (added == k)
                    break;
            }
        }

        return result.stream().mapToInt(i -> i).toArray();
    }
}
