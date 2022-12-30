package dp;

import pair.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecursiveDP {
    public static void main(String[] args) {
        RecursiveDP recursiveDP = new RecursiveDP();

        String[] strings = {"with", "example", "science"};
        System.out.println(recursiveDP.minStickers(strings, "thehat"));
    }

    // Leetcode problem: 494
    public int findTargetSumWays(int[] nums, int target) {
        return backTrackTargetSum(0, 0, nums, target, new HashMap<>());
    }

    public int backTrackTargetSum(int idx, int current, int[] nums, int target, Map<Pair, Integer> map) {

        // Every time traversing all the numbers, return the result.
        if (idx == nums.length)
            return current == target ? 1 : 0;

        Pair pair = new Pair(idx, current);
        if (map.containsKey(pair))
            return map.get(pair);

        int res = backTrackTargetSum(idx + 1, current + nums[idx], nums, target, map) +
                backTrackTargetSum(idx + 1, current - nums[idx], nums, target, map);

        map.put(pair, res);
        return res;
    }

    // Leetcode problem: 983
    public int mincostTickets(int[] days, int[] costs) {
        int[][] dayCosts = {{1, costs[0]}, {7, costs[1]}, {30, costs[2]}};

        return backtrackTicket(0, days, dayCosts, new HashMap<>());
    }

    private int backtrackTicket(int idx, int[] days, int[][] dayCosts, Map<Integer, Integer> map) {
        // If no days left return $0.
        if (idx == days.length)
            return 0;

        if (map.containsKey(idx))
            return map.get(idx);

        int res = Integer.MAX_VALUE;
        for (int[] dayCost : dayCosts) {
            int i = idx;
            // Find the next index when to buy ticket again.
            while (i < days.length && days[i] < (days[idx] + dayCost[0]))
                i++;
            res = Math.min(res, dayCost[1] + backtrackTicket(i, days, dayCosts, map));
        }

        map.put(idx, res);
        return res;
    }

    // Leetcode problem: 312
    /*
     * For every index, burst the balloon last and calculate.
     * Time limit exceeded in leetcode but works fine.
     * */
    public int maxCoins(int[] nums) {

        return maxCoins(0, nums.length - 1, nums, new HashMap());

    }

    private int maxCoins(int left, int right, int[] nums, Map<Pair, Integer> map) {

        if (left > right)
            return 0;

        Pair pair = new Pair(left, right);
        if (map.containsKey(pair))
            return map.get(pair);

        int res = 0;
        for (int i = left; i <= right; i++) {
            int coin = (left > 0 ? nums[left - 1] : 1) * nums[i] * (right < nums.length - 1 ? nums[right + 1] : 1);
            coin += (maxCoins(left, i - 1, nums, map) + maxCoins(i + 1, right, nums, map));
            res = Math.max(res, coin);
        }

        map.put(pair, res);
        return res;
    }

    // Leetcode problem: 416
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        if (sum % 2 == 1)
            return false;

        int target = sum / 2;

        return canPartition(0, 0, target, nums, new HashMap<>());
    }

    private boolean canPartition(int idx, int current, int target, int[] nums, Map<Pair, Boolean> map) {
        if (current == target)
            return true;

        if (idx == nums.length || current > target)
            return false;

        Pair pair = new Pair(idx, current);
        if (map.containsKey(pair))
            return map.get(pair);

        boolean res = canPartition(idx + 1, current + nums[idx], target, nums, map)
                || canPartition(idx + 1, current, target, nums, map);

        map.put(pair, res);
        return res;
    }

    // Leetcode problem: 1553
    public int minDays(int n) {
        Map<Integer, Integer> map = new HashMap<>();

        map.put(0, 0);
        map.put(1, 1);

        return minDays(n, map);
    }

    private int minDays(int n, Map<Integer, Integer> map) {
        if (map.containsKey(n))
            return map.get(n);

        // Eat (n % 2) orange to make eat divisible by 2 then 1 for eating n / 2 orange.
        // Eat (n % 3) orange to make eat divisible by 3 then 1 for eating n / 3 orange.
        int two = 1 + (n % 2) + minDays(n / 2, map);
        int three = 1 + (n % 3) + minDays(n / 3, map);

        map.put(n, Math.min(two, three));
        return Math.min(two, three);
    }

    // Leetcode problem: 877
    public boolean stoneGame(int[] piles) {
        int sum = 0;
        for (int pile : piles)
            sum += pile;

        return stoneGame(0, piles.length - 1, piles, new HashMap<>()) > sum / 2;
    }

    private int stoneGame(int l, int r, int[] piles, Map<Pair, Integer> map) {
        if (l > r)
            return 0;

        Pair pair = new Pair(l, r);

        if (map.containsKey(pair))
            return map.get(pair);

        // Calculate only Alice's piles. For even length Alice can take a pile.
        int left = (r - l + 1) % 2 == 0 ? piles[l] : 0;
        int right = (r - l + 1) % 2 == 0 ? piles[r] : 0;

        int res = Math.max(left + stoneGame(l + 1, r, piles, map), right + stoneGame(l, r - 1, piles, map));

        map.put(pair, res);
        return res;
    }

    // Leetcode problem: 691
    /*
     * Stickers to Spell Word.
     * This issue is tricky.
     * */
    public int minStickers(String[] stickers, String target) {
        List<Map<Character, Integer>> stickCounts = new ArrayList<>();

        // Build up character count for every sticker.
        for (int i = 0; i < stickers.length; i++) {
            stickCounts.add(new HashMap<>());
            for (int j = 0; j < stickers[i].length(); j++) {
                char ch = stickers[i].charAt(j);
                int c = stickCounts.get(i).getOrDefault(ch, 0);
                stickCounts.get(i).put(ch, c + 1);
            }
        }

        int res = dfsMinStickers(target, new HashMap<>(), stickCounts, new HashMap<>());
        return res == Integer.MAX_VALUE ? -1 : res;
    }

    private int dfsMinStickers(String target, Map<Character, Integer> stickCount, List<Map<Character, Integer>> stickCounts, Map<String, Integer> map) {
        if (map.containsKey(target)) {
            return map.get(target);
        }

        // For first call stick count map is empty.
        int res = stickCount.isEmpty() ? 0 : 1;
        StringBuilder remainingT = new StringBuilder();
        for (int i = 0; i < target.length(); i++) {
            char ch = target.charAt(i);

            // If character found then decrease the count otherwise add it to remaining.
            if (stickCount.containsKey(ch) && stickCount.get(ch) > 0) {
                stickCount.put(ch, stickCount.get(ch) - 1);
            } else {
                remainingT.append(ch);
            }
        }

        if (!remainingT.isEmpty()) {
            int used = Integer.MAX_VALUE;
            for (Map<Character, Integer> st : stickCounts) {

                // If first character of target in not the string skip it.
                if (!st.containsKey(remainingT.charAt(0)))
                    continue;

                // Always sent a copy of character count map to the recursive call.
                used = Math.min(used, dfsMinStickers(remainingT.toString(), new HashMap<>(st), stickCounts, map));
            }

            res = (used == Integer.MAX_VALUE) ? used : res + used;
            map.put(remainingT.toString(), used);
        }
        return res;
    }

    // Leetcode problem: 123
    // Leetcode problem: 188
    /*
     * Best Time to Buy and Sell Stock III & IV
     * Time limit exceeded for recursive solution.
     * Code source III: https://www.youtube.com/watch?v=37s1_xBiqH0
     * Code source IV: https://www.youtube.com/watch?v=6928FkPhGUA&t=26s
     * */
    public int maxProfit(int k, int[] prices) {

        // Optimized code for III
        /*
        int n = prices.length;
        int [] leftProfit = new int[n];
        int [] rightProfit = new int[n];

        int leftMin = prices[0];
        for (int i = 1; i < n; i++) {
            leftProfit[i] = Math.max(leftProfit[i - 1], prices[i] - leftMin);
            leftMin = Math.min(leftMin, prices[i]);
        }

        int rightMax = prices[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightProfit[i] = Math.max(rightProfit[i + 1], rightMax - prices[i]);
            rightMax = Math.max(rightMax, prices[i]);
        }

        int profit = rightProfit[0];
        for (int i = 1; i < n; i++) {
            profit = Math.max(profit, leftProfit[i - 1] + rightProfit[i]);
        }

        return profit;
        */

        // Optimized code for problem IV
        /*
        int [] dp = new int[2 * k];

        for (int i = 0; i < 2 * k; i += 2) {
            dp[i] = Integer.MIN_VALUE;
        }

        for (int price : prices) {
            for (int j = 0; j < 2 * k; j++) {
                if (j == 0) {
                    dp[j] = Math.max(dp[j], -price);
                } else if (j % 2 == 0) {
                    dp[j] = Math.max(dp[j], dp[j - 1] - price);
                } else {
                    dp[j] = Math.max(dp[j], dp[j - 1]  +price);
                }
            }
        }

        return dp[2 * k - 1];
        */

        int n = prices.length;
        int[][][] dp = new int[2][k + 1][n];

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j <= k; j++) {
                for (int l = 0; l < n; l++) {
                    dp[i][j][l] = -1;
                }
            }
        }

        return maxProfit(0, k, 0, prices, dp);
    }

    public int maxProfit(int bought, int k, int idx, int[] prices, int[][][] dp) {
        if (idx >= prices.length || k == 0) {
            return 0;
        }
        if (dp[bought][k][idx] != -1) {
            return dp[bought][k][idx];
        }

        int profit = maxProfit(bought, k, idx + 1, prices, dp);
        if (bought == 0) {
            profit = Math.max(profit, maxProfit(1, k, idx + 1, prices, dp) - prices[idx]);
        } else {
            profit = Math.max(profit, maxProfit(0, k - 1, idx + 1, prices, dp) + prices[idx]);
        }

        return profit;
    }
}
