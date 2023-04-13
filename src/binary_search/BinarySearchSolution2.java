package binary_search;

import java.util.Arrays;

public class BinarySearchSolution2 {

    public static void main(String[] args) {
        BinarySearchSolution2 binarySearchSolution2 = new BinarySearchSolution2();

        int[] array = {1, 2, 4, 5, 0};
        // 3 0
        // 1, 4, 4, 5, 0
        System.out.println(binarySearchSolution2.maxPower(array, 1, 2));
    }

    // Leetcode problem: 2528
    /*
     * Maximize the Minimum Powered City.
     * Explanation: https://www.youtube.com/watch?v=E6aEq7V9TDk
     * */
    public long maxPower(int[] stations, int r, int k) {
        long low = stations[0];
        long high = k;

        for (int station : stations) {
            low = Math.min(low, station);
            high += station;
        }

        long res = low;
        while (low <= high) {
            long mid = low + (high - low) / 2;

            if (isPossible(stations, r, k, mid)) {
                res = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return res;
    }

    private boolean isPossible(int[] stations, int r, int k, long power) {
        int n = stations.length;
        int[] copy = Arrays.copyOf(stations, n);

        long total = 0;
        for (int i = 0; i <= r; i++) {
            total += copy[i];
        }

        int i = 0;
        while (i < n) {

            long addition = Math.max(0, power - total);
            if (addition > k) {
                return false;
            }

            total += addition;
            copy[Math.min(i + r, n - 1)] += addition;
            k -= addition;

            i += 1;
            if (i > r) {
                total -= copy[i - (r + 1)];
            }
            if (i + r < n) {
                total += copy[i + r];
            }
        }

        return true;
    }

    // Leetcode problem: 1011
    /*
     * Capacity To Ship Packages Within D Days.
     * This problem is similar to Maximum Tastiness of Candy Basket (Leetcode problem: 2517).
     * */
    public int shipWithinDays(int[] weights, int days) {
        int sum = 0;
        int max = weights[0];

        for (int weight : weights) {
            max = Math.max(max, weight);
            sum += weight;
        }

        int l = max;
        int h = sum;
        int capacity = sum;

        while (l <= h) {
            int m = l + (h - l) / 2;
            if (canShipAll(weights, m, days)) {
                capacity = m;
                h = m - 1;
            } else {
                l = m + 1;
            }
        }

        return capacity;
    }

    private boolean canShipAll(int[] weights, int capacity, int days) {
        int current = 0;
        int currentDays = 1;

        for (int i = 0; i < weights.length; i++) {
            current += weights[i];
            if (current > capacity) {
                current = weights[i];
                currentDays += 1;
            }
        }

        return currentDays <= days;
    }

    // Leetcode problem: 2300
    /*
     * Successful Pairs of Spells and Potions.
     * */
    public int[] successfulPairs(int[] spells, int[] potions, long success) {
        Arrays.sort(potions);
        int n = spells.length;
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = countSuccess(potions, spells[i], success);
        }

        return res;

    }

    private int countSuccess(int[] potions, long spell, long success) {

        int l = 0;
        int r = potions.length - 1;
        int ans = 0;

        while (l <= r) {
            int m = l + (r - l) / 2;

            if (spell * potions[m] >= success) {
                ans = potions.length - m;
                r = m - 1;
            } else {
                l = m + 1;
            }
        }

        return ans;
    }
}
