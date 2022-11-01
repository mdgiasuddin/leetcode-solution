package amazon_interview;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AmazonCode {

    public static void main(String[] args) {
        AmazonCode amazonCode = new AmazonCode();

        int[] nums = {1, 2, 3, 3, 4, 2, 3, 2, 3};
        int[] res = amazonCode.countDistinctElement(nums, 4);
        System.out.println(Arrays.toString(res));
    }

    public void sortArrayZeroOne() {
        int[] array = {0, 1, 0, 1, 0, 0, 1, 1, 1, 0};

        int j = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 0) {
                int tmp = array[j];
                array[j] = 0;
                array[i] = tmp;

                j += 1;
            }
        }

        System.out.println(Arrays.toString(array));
    }

    public int[] countDistinctElement(int[] nums, int k) {
        int n = nums.length;
        Map<Integer, Integer> countMap = new HashMap<>();
        int[] res = new int[n - k + 1];

        int l = 0;
        int r = 0;
        while (r < k) {
            int c = countMap.getOrDefault(nums[r], 0);
            countMap.put(nums[r], c + 1);
            r += 1;
        }

        res[l] = countMap.size();
        while (r < n) {
            int c = countMap.get(nums[l]);
            if (c == 1) {
                countMap.remove(nums[l]);
            } else {
                countMap.put(nums[l], c - 1);
            }
            l += 1;

            c = countMap.getOrDefault(nums[r], 0);
            countMap.put(nums[r], c + 1);
            res[l] = countMap.size();

            r += 1;
        }

        return res;
    }
}
