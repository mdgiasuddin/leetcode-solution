package array;

public class NumArray {

    int[] arr;
    int[] segmentArray;

    public NumArray(int[] nums) {
        int n = nums.length;
        arr = new int[n];

        System.arraycopy(nums, 0, arr, 0, n);

        // Max size will be ((the smallest power of 2) - 1) >= 2n - 1 (Full binary tree).
        int maxSize = 1;
        while (maxSize <= 2 * n - 1) {
            maxSize *= 2;
        }
        maxSize -= 1;

        this.segmentArray = new int[maxSize];

        buildSegmentArray(segmentArray, arr, 0, n - 1, 0);
    }

    private int buildSegmentArray(int[] segmentArray, int[] arr, int left, int right, int idx) {
        if (left == right) {
            segmentArray[idx] = arr[left];
            return segmentArray[idx];
        }

        int mid = left + (right - left) / 2;
        segmentArray[idx] = buildSegmentArray(segmentArray, arr, left, mid, 2 * idx + 1)
                + buildSegmentArray(segmentArray, arr, mid + 1, right, 2 * idx + 2);

        return segmentArray[idx];
    }

    public void update(int index, int val) {
        int diff = val - arr[index];
        arr[index] = val;

        update(segmentArray, 0, arr.length - 1, 0, index, diff);
    }

    public void update(int[] segmentArray, int sLeft, int sRight, int sIdx, int index, int diff) {
        if (index < sLeft || index > sRight)
            return;

        segmentArray[sIdx] += diff;

        if (sLeft < sRight) {
            int mid = sLeft + (sRight - sLeft) / 2;
            update(segmentArray, sLeft, mid, 2 * sIdx + 1, index, diff);
            update(segmentArray, mid + 1, sRight, 2 * sIdx + 2, index, diff);
        }
    }

    public int sumRange(int left, int right) {
        return sumRange(segmentArray, 0, arr.length - 1, left, right, 0);
    }

    public int sumRange(int[] segmentArray, int sLeft, int sRight, int qLeft, int qRight, int idx) {
        if (qLeft <= sLeft && qRight >= sRight)
            return segmentArray[idx];

        if (sRight < qLeft || sLeft > qRight)
            return 0;

        int mid = sLeft + (sRight - sLeft) / 2;

        return sumRange(segmentArray, sLeft, mid, qLeft, qRight, 2 * idx + 1) +
                sumRange(segmentArray, mid + 1, sRight, qLeft, qRight, 2 * idx + 2);
    }
}
