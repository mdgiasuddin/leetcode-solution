package amazon_interview;

import java.util.Arrays;

public class AmazonCode {

    public static void main(String[] args) {
        AmazonCode amazonCode = new AmazonCode();

        amazonCode.sortArrayZeroOne();
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
}
