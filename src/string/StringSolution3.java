package string;

import java.util.*;

public class StringSolution3 {
    public static void main(String[] args) {
        StringSolution3 stringSolution3 = new StringSolution3();

        int[] array = {1, 2, 3, 5, 6};
        System.out.println(Arrays.binarySearch(array, 4));
//        System.out.println(stringSolution3.shiftingLetters("aaa", array));
    }

    // Leetcode problem: 556
    /*
     * Traverse from right side and find first digit which is greater than left digit
     * Sort the right side
     * Find the appropriate place for the digit which is smaller
     * */
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

    // Leetcode problem: 678
    /*
     * Valid parenthesis
     * Maintain 2 stacks (1 for '(' and 1 for '*')
     * Balance ')' using '(' if failed then '*'
     * Balance remaining '(' with '*'
     * */
    public boolean checkValidString(String s) {
        Stack<Integer> parentheses = new Stack<>();
        Stack<Integer> stars = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(') {
                parentheses.push(i);
            } else if (ch == '*') {
                stars.push(i);
            } else {
                if (!parentheses.isEmpty()) {
                    parentheses.pop();
                } else if (!stars.isEmpty()) {
                    stars.pop();
                } else {
                    return false;
                }
            }
        }

        while (!parentheses.isEmpty()) {
            if (stars.isEmpty() || parentheses.pop() > stars.pop()) {
                return false;
            }
        }
        return true;
    }

    // Leetcode problem: 696
    /*
     * Sliding window solution
     * Previous and current store consecutive number of digits
     * When a different digit found update result previous and current window
     * */
    public int countBinarySubstrings(String s) {
        int prev = 0, current = 1, result = 0;

        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                current++;
            } else {
                result += Math.min(prev, current);
                prev = current;
                current = 1;
            }
        }
        return result + Math.min(prev, current);

    }

    // Leetcode problem: 752
    /*
     * Run BFS to reach target
     * */
    public int openLock(String[] deadends, String target) {
        Set<String> deadEndsSet = new HashSet<>();
        Collections.addAll(deadEndsSet, deadends);

        if (deadEndsSet.contains("0000"))
            return -1;

        if (target.equals("0000"))
            return 0;

        Queue<Map.Entry<String, Integer>> queue = new LinkedList<>();
        queue.add(Map.entry("0000", 0));

        Set<String> visited = new HashSet<>();
        visited.add("0000");

        while (!queue.isEmpty()) {
            Map.Entry<String, Integer> parent = queue.poll();

            for (String child : getLockChildren(parent.getKey())) {
                if (child.equals(target))
                    return parent.getValue() + 1;
                if (!visited.contains(child) && !deadEndsSet.contains(child)) {
                    queue.add(Map.entry(child, parent.getValue() + 1));
                    visited.add(child);
                }
            }
        }

        return -1;
    }

    public List<String> getLockChildren(String parent) {
        List<String> children = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            String next = parent.substring(0, i) + (char) ((parent.charAt(i) - '0' + 1) % 10 + '0') + parent.substring(i + 1);
            String prev = parent.substring(0, i) + (char) ((parent.charAt(i) - '0' + 9) % 10 + '0') + parent.substring(i + 1);

            children.add(next);
            children.add(prev);
        }

        return children;
    }


    // Leetcode problem: 848
    /*
     * Shifting Letters
     * First calculate the shift count of each character, then shift
     * */
    public String shiftingLetters(String s, int[] shifts) {
        int strLen = s.length();

        shifts[strLen - 1] = shifts[strLen - 1] % 26;
        for (int i = strLen - 2; i >= 0; i--) {
            shifts[i] = (shifts[i] % 26 + shifts[i + 1]) % 26;
        }

        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) ((chars[i] - 'a' + shifts[i]) % 26 + 'a');
        }

        return String.copyValueOf(chars);
    }

    // Leetcode problem: 784
    /*
     * Letter case combination
     * Go to every character and change its case then add to the next
     * */
    public List<String> letterCasePermutation(String s) {
        List<String> resultList = new ArrayList<>();

        letterCasePermutation(resultList, s, 0);

        return resultList;
    }

    public void letterCasePermutation(List<String> resultList, String s, int index) {
        resultList.add(s);

        for (int i = index; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (Character.isLetter(ch)) {
                if (Character.isUpperCase(ch)) {
                    letterCasePermutation(resultList, s.substring(0, i) + Character.toLowerCase(ch) + s.substring(i + 1), i + 1);
                } else {
                    letterCasePermutation(resultList, s.substring(0, i) + Character.toUpperCase(ch) + s.substring(i + 1), i + 1);
                }
            }
        }
    }

}
