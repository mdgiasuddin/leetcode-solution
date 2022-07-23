package tech_dose;

public class TechDoseSolution {

    public static void main(String[] args) {
        TechDoseSolution techDoseSolution = new TechDoseSolution();

        int[] array = {1, 5, 3, 8, 10, 4, 2};

        System.out.println("Range min: " + techDoseSolution.rangeMinimum(array, 0, 4));
    }

    public int rangeMinimum(int[] array, int left, int right) {
        int n = array.length;

        int maxLength = 1;
        while (maxLength <= 2 * n - 1)
            maxLength *= 2;
        maxLength -= 1;

        int[] segmentArray = new int[maxLength];

        buildSegmentArray(array, segmentArray, 0, n - 1, 0);

        return rangeMinimum(segmentArray, 0, n - 1, left, right, 0);
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

    public int rangeMinimum(int[] segmentArray, int sLeft, int sRight, int qLeft, int qRight, int sIndex) {
        if (qLeft <= sLeft && sRight <= qRight)
            return segmentArray[sIndex];

        if (sRight < qLeft || sLeft > qRight)
            return Integer.MAX_VALUE;

        int mid = sLeft + (sRight - sLeft) / 2;
        return Math.min(rangeMinimum(segmentArray, sLeft, mid, qLeft, qRight, 2 * sIndex + 1)
                , rangeMinimum(segmentArray, mid + 1, sRight, qLeft, qRight, 2 * sIndex + 2));
    }
}
