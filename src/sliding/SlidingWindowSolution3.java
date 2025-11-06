package sliding;

public class SlidingWindowSolution3 {

    public static void main(String[] args) {
        SlidingWindowSolution3 solution = new SlidingWindowSolution3();
    }

    /**
     * TikTok interview question.
     * The question is asked to Khalid.
     * Number of 1 bit of a subarray, of which the logical or value lies within the given range.
     */
    public int getLargestOnesGivenRange(int[] nums, int low, int high) {
        int[] countBits = new int[32];
        int[] bitsOrValue;
        int l = 0;
        int r = 0;
        int res = 0;
        while (r < nums.length) {
            bitsOrValue = processNum(countBits, nums[r], 1);
            while (l <= r && bitsOrValue[1] > high) {
                bitsOrValue = processNum(countBits, nums[l], -1);
                l += 1;
            }

            if (bitsOrValue[1] >= low) {
                res = Math.max(res, bitsOrValue[0]);
            }
            r += 1;
        }

        return res;
    }

    private int[] processNum(int[] countBits, int num, int sign) {
        int i = 0;
        while (num != 0) {
            int bit = num & 1;
            countBits[i] += sign * bit;
            num >>>= 1;
            i += 1;
        }


        int orValue = 0;
        int bits = 0;
        for (i = countBits.length - 1; i >= 0; i--) {
            bits += countBits[1];
            orValue = (orValue << 1) + Math.min(1, countBits[i]);
        }

        return new int[]{bits, orValue};
    }

    // Leetcode problem: 1358

    /**
     * Number of Substrings Containing All Three Characters.
     * abcabc => when 'abc' appears, we can concatenate '' 'a', 'ab', 'abc' => n - i
     * Then try by removing characters from left.
     */
    public int numberOfSubstrings(String s) {
        int[] count = new int[3];
        int n = s.length();
        int l = 0;
        int res = 0;

        for (int r = 0; r < n; r++) {
            char ch = s.charAt(r);
            count[ch - 'a'] += 1;
            while (count[0] > 0 && count[1] > 0 && count[2] > 0) {
                res += n - r;
                count[s.charAt(l) - 'a'] -= 1;
                l += 1;
            }
        }

        return res;
    }

    // Leetcode problem: 3208

    /**
     * Alternating Groups II.
     */
    public int numberOfAlternatingGroups(int[] colors, int k) {
        int l = 0;
        int n = colors.length;
        int[] copy = new int[n + k - 1];
        System.arraycopy(colors, 0, copy, 0, n);
        // Since this a circular ring, to support circle,
        // add the first k - 1 colors to make group with the last color.
        System.arraycopy(colors, 0, copy, n, k - 1);

        int res = 0;
        for (int r = 1; r < n + k - 1; r++) {
            if (copy[r] == copy[r - 1]) {
                l = r;
            } else if (r - l + 1 == k) {
                res += 1;
                l += 1;
            }
        }
        return res;
    }
}
