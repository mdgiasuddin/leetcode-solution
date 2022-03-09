package indefinite;

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


}
