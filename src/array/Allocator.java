package array;

import java.util.Arrays;

// Leetcode problem: 2502
class Allocator {

    int[] memory;

    public Allocator(int n) {
        memory = new int[n];
        Arrays.fill(memory, -1);
    }

    public int allocate(int size, int mID) {
        int count = 0;
        for (int i = 0; i < memory.length; i++) {
            if (memory[i] == -1) {
                count += 1;
            } else {
                count = 0;
            }

            if (count == size) {
                for (int j = i; j > i - size; j--) {
                    memory[j] = mID;
                }

                return i - size + 1;
            }
        }

        return -1;
    }

    public int free(int mID) {
        int count = 0;
        for (int i = 0; i < memory.length; i++) {
            if (memory[i] == mID) {
                count += 1;
                memory[i] = -1;
            }
        }

        return count;
    }
}
