package trie;

import java.util.ArrayList;
import java.util.List;

public class MaximumXOR {

    // Leetcode problem: 421
    /*
     * Maximum XOR of Two Numbers in an Array.
     * Explanation: https://www.youtube.com/watch?v=jCuZCbXnpLo
     * */
    public int findMaximumXOR(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return 0;
        }

        int maxNum = nums[0];
        for (int num : nums) {
            maxNum = Math.max(maxNum, num);
        }
        List<String> numsStr = new ArrayList<>();
        int maxLen = Integer.toString(maxNum, 2).length();
        for (int num : nums) {
            String numStr = Integer.toString(num, 2);

            numsStr.add(
                fillZero(maxLen - numStr.length()).append(numStr).toString()
            );
        }

        TrieNodeInt root = new TrieNodeInt();
        for (int i = 0; i < n; i++) {
            root.insert(nums[i], numsStr.get(i));
        }

        int res = 0;
        for (int i = 0; i < n; i++) {
            int optimal = root.getOptimalInt(numsStr.get(i));
            res = Math.max(res, nums[i] ^ optimal);
        }

        return res;
    }

    private StringBuilder fillZero(int len) {
        StringBuilder res = new StringBuilder();
        res.append("0".repeat(Math.max(0, len)));

        return res;
    }
}

class TrieNodeInt {
    int num;
    TrieNodeInt[] children;

    public TrieNodeInt() {
        this.num = -1;
        children = new TrieNodeInt[2];
    }

    public void insert(int num, String numStr) {
        TrieNodeInt current = this;

        for (int i = 0; i < numStr.length(); i++) {
            int idx = numStr.charAt(i) - '0';
            if (current.children[idx] == null) {
                current.children[idx] = new TrieNodeInt();
            }
            current = current.children[idx];
        }
        current.num = num;
    }

    public int getOptimalInt(String numStr) {
        TrieNodeInt current = this;

        for (int i = 0; i < numStr.length(); i++) {
            int idx = numStr.charAt(i) - '0';
            current = current.children[1 - idx] == null ? current.children[idx] : current.children[1 - idx];
        }
        return current.num;
    }
}
