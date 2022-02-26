package string;

import java.util.*;

public class StringSolution2 {

    public static void main(String[] args) {
        StringSolution2 stringSolution2 = new StringSolution2();

//        System.out.println(stringSolution2.calculate("(1+(4+5+2)-3)+(6+8)"));

        int[] numbers = {34323, 3432};
//        System.out.println(stringSolution2.largestNumber(numbers));
//        System.out.println(stringSolution2.shortestPalindrome("abacd"));
//        System.out.println(stringSolution2.removeDuplicateLetters("cbacdcbc"));
//        System.out.println(stringSolution2.frequencySort("treemap"));
//        System.out.println(stringSolution2.repeatedSubstringPattern("abab"));
//        System.out.println(stringSolution2.reverseStr("abcdefg", 2));
        System.out.println(stringSolution2.solveEquation("2x+3x-6x=x+2"));
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

        return kmpTable;
    }

    // Leetcode problem: 316
    // Leetcode problem: 1081
    /*
     * Build up a counter array of number of occurrences of character
     * Compare every character with the last taken character
     * Remove if the character is greater and there is another character later
     * */
    public String removeDuplicateLetters(String s) {
        int[] counter = new int[26];
        boolean[] taken = new boolean[26];
        LinkedList<Character> result = new LinkedList<>();

        for (int i = 0; i < s.length(); i++) {
            counter[s.charAt(i) - 'a']++;
        }

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            counter[ch - 'a']--;
            if (taken[ch - 'a'])
                continue;

            while (!result.isEmpty() && result.getLast() > ch && counter[result.getLast() - 'a'] > 0) {
                taken[result.removeLast() - 'a'] = false;
            }
            result.add(ch);
            taken[ch - 'a'] = true;
        }

        StringBuilder stringBuilder = new StringBuilder();
        while (!result.isEmpty()) {
            stringBuilder.append(result.removeFirst());
        }
        return stringBuilder.toString();
    }

    // Leetcode problem: 451
    /*
     * Build up a list of vowel index of the string
     * Then reverse the vowels of that string
     * */
    public String reverseVowels(String s) {
        char[] chars = s.toCharArray();
        List<Integer> vowelIndices = new ArrayList<>();

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == 'a' || chars[i] == 'A' || chars[i] == 'e' || chars[i] == 'E' || chars[i] == 'i' || chars[i] == 'I'
                    || chars[i] == 'o' || chars[i] == 'O' || chars[i] == 'u' || chars[i] == 'U')
                vowelIndices.add(i);
        }

        int left = 0, right = vowelIndices.size() - 1;
        while (left < right) {
            char temp = chars[vowelIndices.get(left)];
            chars[vowelIndices.get(left)] = chars[vowelIndices.get(right)];
            chars[vowelIndices.get(right)] = temp;
            left++;
            right--;
        }

        return String.copyValueOf(chars);
    }

    // Leetcode problem: 336
    /*
     * Build up a counter map of characters
     * Sorts the map according to value
     * Then build up the result string
     * */
    public String frequencySort(String s) {
        Map<Character, Integer> frequencyMap = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (frequencyMap.containsKey(ch)) {
                int frequency = frequencyMap.get(ch);
                frequencyMap.replace(ch, frequency + 1);
            } else {
                frequencyMap.put(ch, 1);
            }
        }

        List<Map.Entry<Character, Integer>> frequencyList = new ArrayList<>(frequencyMap.entrySet());
        frequencyList.sort((freq1, freq2) -> freq2.getValue().compareTo(freq1.getValue()));

        StringBuilder stringBuilder = new StringBuilder();

        for (Map.Entry<Character, Integer> entry : frequencyList) {
            stringBuilder.append(String.valueOf(entry.getKey()).repeat(Math.max(0, entry.getValue())));
        }

        return stringBuilder.toString();
    }

    // Leetcode problem: 387
    /*
     * Build up counter of character
     * Then traverse the string and find the first character of count 1
     * */
    public int firstUniqChar(String s) {
        int[] array = new int[26];
        for (char ch : s.toCharArray()) {
            array[ch - 'a']++;
        }
        for (int i = 0; i < s.length(); i++) {
            if (array[s.charAt(i) - 'a'] == 1)
                return i;
        }
        return -1;
    }

    // Leetcode problem: 459
    /*
     * Check every substring of length 1 to len/2 which are divisor of len
     * */
    public boolean repeatedSubstringPattern(String s) {
        int len = s.length();

        for (int i = len / 2; i >= 1; i--) {
            if (len % i == 0) {
                int repeat = len / i;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(s.substring(0, i).repeat(repeat));
                if (stringBuilder.toString().equals(s))
                    return true;
            }
        }

        return false;
    }

    // Leetcode problem: 541
    /*
     * Partition and process the substrings
     * */
    public String reverseStr(String s, int k) {
        int strLen = s.length();
        int part = strLen / (k * 2);

        for (int i = 0; i < part; i++) {
            String first = s.substring(0, i * 2 * k);
            String toReverse = s.substring(i * 2 * k, (i + 1) * 2 * k);
            StringBuilder stringBuilder = new StringBuilder(toReverse.substring(0, k));
            stringBuilder.reverse().append(toReverse.substring(k));
            String rest = s.substring((i + 1) * 2 * k);

            s = first + stringBuilder + rest;
        }

        String processed = s.substring(0, part * 2 * k);
        String rest = s.substring(part * 2 * k);

        if (rest.length() < k) {
            StringBuilder stringBuilder = new StringBuilder(rest);
            stringBuilder.reverse();

            return processed + stringBuilder;
        }

        StringBuilder stringBuilder = new StringBuilder(rest.substring(0, k));
        return processed + stringBuilder.reverse() + rest.substring(k);
    }

    // Leetcode problem 796
    /*
     * If two strings are rotation of each other doubling one string have the other string
     * */
    public boolean rotateString(String s, String goal) {
        return s.length() == goal.length() && (s + s).contains(goal);
    }


    // Leetcode problem: 640
    /*
     * Calculate left x coefficient and constants
     * Subtract right x coefficient and constants from left side
     * Then find the final result
     * */
    public String solveEquation(String equation) {
        int constant = 0, xCoefficient = 0, sign = 1;

        int i = 0;

        // Traverse the left side
        while (equation.charAt(i) != '=') {
            char ch = equation.charAt(i);
            if (ch == 'x') {
                xCoefficient += sign;
            } else if (ch == '+') {
                sign = 1;
            } else if (ch == '-') {
                sign = -1;
            } else if (ch >= '0' && ch <= '9') {
                int j = i, num = 0;
                while (equation.charAt(j) >= '0' && equation.charAt(j) <= '9') {
                    num = num * 10 + equation.charAt(j) - '0';
                    j++;
                }

                if (equation.charAt(j) == 'x') {
                    xCoefficient += sign * num;
                    i = j;
                } else {
                    constant += sign * num;
                    i = j - 1;
                }
            }
            i++;
        }

        i++; // equation[i] = '=' now so go to next character
        sign = 1; // reset the sign
        while (i < equation.length()) {
            char ch = equation.charAt(i);
            if (ch == 'x') {
                xCoefficient -= sign;
            } else if (ch == '+') {
                sign = 1;
            } else if (ch == '-') {
                sign = -1;
            } else if (ch >= '0' && ch <= '9') {
                int j = i, num = 0;
                while (j < equation.length() && equation.charAt(j) >= '0' && equation.charAt(j) <= '9') {
                    num = num * 10 + equation.charAt(j) - '0';
                    j++;
                }

                if (j < equation.length() && equation.charAt(j) == 'x') {
                    xCoefficient -= sign * num;
                    i = j;
                } else {
                    constant -= sign * num;
                    i = j - 1;
                }
            }
            i++;
        }

        constant *= -1; // Equation is now ax + b = 0
        if (xCoefficient == 0) {
            if (constant == 0) {
                return "Infinite solutions";
            } else {
                return "No solution";
            }
        }
        return "x=" + (constant / xCoefficient);
    }
}
