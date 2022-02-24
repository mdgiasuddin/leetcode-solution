package string;

import indefinite.UtilClass;

import java.util.*;

public class StringSolution2 {

    public static void main(String[] args) {
        StringSolution2 stringSolution2 = new StringSolution2();

//        System.out.println(stringSolution2.calculate("(1+(4+5+2)-3)+(6+8)"));

        int[] numbers = {34323, 3432};
//        System.out.println(stringSolution2.largestNumber(numbers));
        System.out.println(stringSolution2.shortestPalindrome("abacd"));
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
    /*
     * Compare two numbers by joining them
     * */
    public String largestNumber(int[] nums) {
        List<String> stringList = new ArrayList<>(Arrays.stream(nums).mapToObj(String::valueOf).toList());
        stringList.sort((first, second) -> {
            String str1 = first + second;
            String str2 = second + first;
            return str2.compareTo(str1);
        });

        String result = String.join("", stringList);

        return result.startsWith("0") ? "0" : result;
    }

    // Leetcode problem: 205
    /*
     * Isomorphic String
     * Use HashMap to store the mappings of character
     * */
    public boolean isIsomorphic(String s, String t) {
        if (s.length() != t.length())
            return false;

        Map<Character, Character> characterMap = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            if (characterMap.containsKey(s.charAt(i)) && characterMap.get(s.charAt(i)) != t.charAt(i))
                return false;

            characterMap.put(s.charAt(i), t.charAt(i));
        }

        Map<Character, Character> characterMap2 = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            if (characterMap2.containsKey(t.charAt(i)) && characterMap2.get(t.charAt(i)) != s.charAt(i))
                return false;

            characterMap2.put(t.charAt(i), s.charAt(i));
        }

        return true;
    }


    // Leetcode problem: 214
    /*
     * Shortest palindrome
     * Build KMP table to find the longest suffix which is also a prefix
     * */
    public String shortestPalindrome(String s) {
        if (s.length() < 2)
            return s;

        String modifiedString = s + "$" + new StringBuilder(s).reverse();
        int[] kmpTable = buildKMPTable(modifiedString);
        int modifiedLength = modifiedString.length();

        System.out.println(kmpTable[modifiedLength - 1]);

        return modifiedString.substring(s.length() + 1, modifiedLength - kmpTable[modifiedLength - 1]) + s;
    }

    public int[] buildKMPTable(String s) {
        int[] kmpTable = new int[s.length()];
        kmpTable[0] = 0;

        int index = 0;
        for (int i = 1; i < s.length(); ) {
            if (s.charAt(i) == s.charAt(index)) {
                kmpTable[i] = index + 1;
                i++;
                index++;
            } else if (index != 0) {
                index = kmpTable[index - 1];
            } else {
                kmpTable[i] = 0;
                i++;
            }
        }

        UtilClass.printIntArray(kmpTable);
        return kmpTable;
    }

}
