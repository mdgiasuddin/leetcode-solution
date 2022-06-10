package indefinite;

import java.util.*;

public class SixthSolution {

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

    public int numOfStrings(String[] patterns, String word) {
        int count = 0;
        for (String pattern : patterns) {
            if (word.indexOf(pattern) != -1) count++;
        }
        return count;
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
