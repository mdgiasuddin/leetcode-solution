package dp;

import pair.Pair;

import java.util.HashMap;
import java.util.Map;

public class RecursiveDP {
    public static void main(String[] args) {
        RecursiveDP recursiveDP = new RecursiveDP();

        int[] days = {1, 4, 6, 7, 8, 20};
        int[] costs = {2, 7, 15};
        System.out.println(recursiveDP.mincostTickets(days, costs));
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
}
