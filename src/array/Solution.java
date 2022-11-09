package array;

import java.util.Random;

// Leetcode problem: 528
public class Solution {
    int[] sumWeight;

    public Solution(int[] w) {
        this.sumWeight = w;

        for (int i = 1; i < w.length; i++) {
            sumWeight[i] += sumWeight[i - 1];
        }
    }

    public int pickIndex() {
        int val = 1 + new Random().nextInt(sumWeight[sumWeight.length - 1]);

        return pickIndex(val);
    }

    private int pickIndex(int val) {
        int len = sumWeight.length;
        int left = 0;
        int right = len - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (sumWeight[mid] == val) {
                return mid;
            } else if (val < sumWeight[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }
}
