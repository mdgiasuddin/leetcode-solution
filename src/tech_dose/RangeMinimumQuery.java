package tech_dose;

public class RangeMinimumQuery {

    int[] array;
    int[] segmentArray;

    public static void main(String[] args) {

        int[] array = {1, 5, 3, 8, 10, 4, 2};

        RangeMinimumQuery rangeMinimumQuery = new RangeMinimumQuery(array);

        System.out.println("Range min: " + rangeMinimumQuery.rangeMinimum(2, 5));

        rangeMinimumQuery.update(3, 0);
        System.out.println("Range min: " + rangeMinimumQuery.rangeMinimum(0, 5));

    }

    public RangeMinimumQuery(int[] nums) {
        int n = nums.length;

        int maxLength = 1;
        while (maxLength <= 2 * n - 1)
            maxLength *= 2;
        maxLength -= 1;

        this.segmentArray = new int[maxLength];
        array = new int[n];
        System.arraycopy(nums, 0, array, 0, n);

        buildSegmentArray(nums, segmentArray, 0, n - 1, 0);
    }

    public int rangeMinimum(int left, int right) {
        return rangeMinimum(0, array.length - 1, left, right, 0);
    }

    public int buildSegmentArray(int[] array, int[] segmentArray, int sLeft, int sRight, int sIndex) {

        if (sLeft == sRight) {
            segmentArray[sIndex] = array[sLeft];
            return segmentArray[sIndex];
        }

        int mid = sLeft + (sRight - sLeft) / 2;
        segmentArray[sIndex] = Math.min(buildSegmentArray(array, segmentArray, sLeft, mid, 2 * sIndex + 1)
                , buildSegmentArray(array, segmentArray, mid + 1, sRight, 2 * sIndex + 2));

        return segmentArray[sIndex];
    }

    public int rangeMinimum(int sLeft, int sRight, int qLeft, int qRight, int sIndex) {
        if (qLeft <= sLeft && sRight <= qRight)
            return segmentArray[sIndex];

        if (qLeft > sRight || sLeft > qRight)
            return Integer.MAX_VALUE;

        int mid = sLeft + (sRight - sLeft) / 2;
        return Math.min(rangeMinimum(sLeft, mid, qLeft, qRight, 2 * sIndex + 1)
                , rangeMinimum(mid + 1, sRight, qLeft, qRight, 2 * sIndex + 2));
    }

    public void update(int index, int val) {
        array[index] = val;

        update(0, array.length - 1, index, val, 0);
    }

    public void update(int sLeft, int sRight, int index, int val, int sIndex) {

        if (index < sLeft || index > sRight)
            return;

        if (sLeft == sRight) {
            segmentArray[sIndex] = val;
        } else {
            int mid = sLeft + (sRight - sLeft) / 2;
            update(sLeft, mid, index, val, 2 * sIndex + 1);
            update(mid + 1, sRight, index, val, 2 * sIndex + 2);

            segmentArray[sIndex] = Math.min(segmentArray[2 * sIndex + 1], segmentArray[2 * sIndex + 2]);
        }
    }
}
