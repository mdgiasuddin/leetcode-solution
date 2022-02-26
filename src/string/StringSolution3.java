package string;

import java.util.Arrays;

public class StringSolution3 {
    public static void main(String[] args) {
        StringSolution3 stringSolution3 = new StringSolution3();
        System.out.println(stringSolution3.nextGreaterElement(12443322));
    }

    // Leetcode problem: 556
    public int nextGreaterElement(int n) {
        String numString = String.valueOf(n);

        int i;
        for (i = numString.length() - 1; i > 0; i--) {
            if (numString.charAt(i) > numString.charAt(i - 1))
                break;
        }
        if (i == 0) return -1;

        char[] rightPart = numString.substring(i).toCharArray();
        Arrays.sort(rightPart);
        StringBuilder stringBuilder = new StringBuilder();
        int j = 0;
        char ch = numString.charAt(i - 1);
        while (j < rightPart.length) {
            if (rightPart[j] <= ch) {
                j++;
                continue;
            }
            ch = rightPart[j];
            rightPart[j] = numString.charAt(i - 1);
            break;
        }
        stringBuilder.append(ch);
        stringBuilder.append(String.copyValueOf(rightPart));

        long numLong = Long.parseLong(numString.substring(0, i - 1) + stringBuilder);

        return numLong > Integer.MAX_VALUE ? -1 : (int) numLong;
    }
}
