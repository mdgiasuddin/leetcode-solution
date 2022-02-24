package string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringSolution2 {

    public static void main(String[] args) {
        StringSolution2 stringSolution2 = new StringSolution2();

//        System.out.println(stringSolution2.calculate("(1+(4+5+2)-3)+(6+8)"));

        int[] numbers = {34323, 3432};
        System.out.println(stringSolution2.largestNumber(numbers));
    }

    // Leetcode problem: 224
    /*
     * Basic Calculator
     * DFS
     * Every time get a '(' DFS to the next level
     * Get a ')' and return from that level
     * */
    public int calculate(String s) {
        return dfsCalculator(s);
    }

    int index = 0;

    public int dfsCalculator(String s) {
        int sum = 0, operator = 1;
        while (index < s.length()) {
            char ch = s.charAt(index);
            if (ch == ')') {
                break;
            } else if (ch == '(') {
                index++;
                sum = sum + dfsCalculator(s) * operator;
            } else if (ch == '+') {
                operator = 1;
            } else if (ch == '-') {
                operator = -1;
            } else if (Character.isDigit(ch)) {
                StringBuilder buildNum = new StringBuilder();
                while (index < s.length() && Character.isDigit(s.charAt(index))) {
                    buildNum.append(s.charAt(index++));
                }
                index--;
                sum += Integer.parseInt(buildNum.toString()) * operator;
            }
            index++;
        }
        return sum;
    }

    // Leetcode problem: 179
    public String largestNumber(int[] nums) {
        List<String> stringList = new ArrayList<>(Arrays.stream(nums).mapToObj(String::valueOf).toList());
        stringList.sort((first, second) -> {
            String str1 = first + second;
            String str2 = second + first;
            return str2.compareTo(str1);
        });

        return String.join("", stringList);
    }
}
