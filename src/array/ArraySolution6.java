package array;

public class ArraySolution6 {
    public static void main(String[] args) {

    }

    // Leetcode problem: 1899
    public boolean mergeTriplets(int[][] triplets, int[] target) {
        boolean match1 = false;
        boolean match2 = false;
        boolean match3 = false;

        for (int[] triplet : triplets) {

            // If any off the value of triplet is greater than the target, merging will result greater value.
            // So ignore this.
            if (triplet[0] > target[0] || triplet[1] > target[1] || triplet[2] > target[2]) {
                continue;
            }

            // Check all 3 values are contained in other triplets.
            if (triplet[0] == target[0]) {
                match1 = true;
            }
            if (triplet[1] == target[1]) {
                match2 = true;
            }
            if (triplet[2] == target[2]) {
                match3 = true;
            }
        }

        return match1 && match2 && match3;
    }
}
