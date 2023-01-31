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
}
