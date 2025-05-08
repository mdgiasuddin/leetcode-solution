package array;

import java.util.Arrays;
import java.util.Random;

// Leetcode problem: 384

/**
 * Shuffle an Array.
 * */
public class ShuffleArray {

    int[] originalArray;
    Random random;

    public ShuffleArray(int[] nums) {
        this.originalArray = nums;
        this.random = new Random();
    }

    public int[] reset() {
        return originalArray;
    }

    public int[] shuffle() {
        int n = originalArray.length;
        int[] shuffledArray = Arrays.copyOf(originalArray, n);

        for (int i = n - 1; i >= 0; i--) {
            int index = random.nextInt(i + 1);
            int tmp = shuffledArray[i];
            shuffledArray[i] = shuffledArray[index];
            shuffledArray[index] = tmp;
        }

        return shuffledArray;
    }
}
