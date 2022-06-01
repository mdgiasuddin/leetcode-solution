package dp;

import pair.Pair;

import java.util.HashMap;
import java.util.Map;

public class RecursiveDP {
    public static void main(String[] args) {
        RecursiveDP recursiveDP = new RecursiveDP();

        int[] days = {1, 4, 6, 7, 8, 20};
        int[] nums = {21, 47, 65, 65, 26, 17, 80, 25, 92, 42, 1, 5, 20, 75, 98, 42, 61, 76, 2, 95, 76, 12, 87, 87, 71, 71, 38, 95, 49, 61, 85, 50, 8, 83, 36, 16, 12, 49, 51, 85, 7, 29, 26, 17, 61, 57, 34, 89, 89, 16, 9, 79, 11, 75, 65, 61, 78, 45, 67, 14, 59, 21, 67, 82, 27, 14, 36, 94, 65, 52, 38, 89, 29, 75, 52, 28, 87, 22, 4, 12, 20, 41, 63, 78, 30, 92, 26, 40, 0, 52, 65, 51, 30, 34, 71, 64, 74, 48, 49, 80, 45, 3, 65, 27, 90, 23, 88, 56, 64, 97, 88, 97, 70, 25, 93, 41, 63, 0, 18, 74, 41, 41, 63, 72, 29, 97, 24, 46, 3, 50, 32, 16, 95, 80, 98, 14, 27, 25, 81, 34, 27, 62, 84, 2, 93, 72, 34, 61, 92, 94, 59, 88, 75, 12, 3, 10, 60, 22, 71, 47, 49, 1, 39, 48, 2, 99, 53, 32, 32, 18, 51, 4, 43, 77, 78, 62, 42, 76, 82, 70, 57, 67, 37, 87, 75, 72, 0, 42, 43, 73, 67, 91, 22, 14, 69, 38, 100, 40, 100, 15, 26, 91, 1, 67, 28, 58, 92, 16, 11, 78, 7, 53, 28, 65, 48, 90, 78, 92, 49, 78, 38, 3, 79, 13, 59, 41, 7, 8, 27, 18, 71, 97, 71, 14, 61, 58, 86, 84, 0, 8, 97, 51, 65, 69, 39, 24, 20, 62, 45, 97, 42, 12, 92, 52, 7, 46, 8, 56, 20, 22, 11, 12, 81, 27, 12, 15, 19, 1, 84, 14, 38, 29, 69, 42, 44, 66, 68, 22, 24, 94, 31, 62, 36, 37, 75, 6, 7, 26, 69, 17, 67, 52, 12, 100, 88, 10, 16, 40, 93, 62};

        System.out.println(recursiveDP.maxCoins(nums));
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
}
