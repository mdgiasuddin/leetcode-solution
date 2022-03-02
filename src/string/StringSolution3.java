package string;

import java.util.*;

public class StringSolution3 {
    public static void main(String[] args) {
        StringSolution3 stringSolution3 = new StringSolution3();

        int[] array = {1, 2, 3, 5, 6};
//        System.out.println(Arrays.binarySearch(array, 4));
//        System.out.println(stringSolution3.shiftingLetters("aaa", array));
        System.out.println(stringSolution3.findKthBit(4, 11));
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

    // Leetcode problem: 1400
    /*
     * If every character remains even number, palindrome can be formed
     * For odd count, each character can be placed only in middle position
     * So check if k >= odd count character
     * */
    public boolean canConstruct(String s, int k) {

        if (s.length() == k)
            return true;
        if (s.length() < k)
            return false;

        int[] charCounter = new int[26];

        for (int i = 0; i < s.length(); i++) {
            charCounter[s.charAt(i) - 'a']++;
        }

        for (int count : charCounter) {
            if (count % 2 == 1)
                k--;
        }

        return k >= 0;
    }

    // Leetcode problem: 1616
    /*
     * Check left of A with Right of until mismatch, Then check the middle part of A or B is palindrome
     * Done work for left of B with Right of A
     * */
    public boolean checkPalindromeFormation(String a, String b) {
        return check(a, b) || check(b, a);
    }

    public boolean check(String a, String b) {
        int i = 0, j = a.length() - 1;
        while (i < j && a.charAt(i) == b.charAt(j)) {
            i++;
            j--;
        }

        if (i >= j) return true;

        return checkPalindrome(a, i, j) || checkPalindrome(b, i, j);
    }

    public boolean checkPalindrome(String str, int left, int right) {
        while (left < right) {
            if (str.charAt(left++) != str.charAt(right--))
                return false;
        }

        return true;
    }

    // Leetcode problem: 1573
    /*
     * Count total number of 1's. If
     * If number of 1's is not product of 3, then return 0;
     * If all digits are 0's then the result will be (n-1)C2
     * Else found where two partition can be set
     * */
    public int numWays(String s) {

        long strLen = s.length();
        long ones = 0;
        long MOD = 1_000_000_007;

        for (char ch : s.toCharArray()) {
            ones += ch - '0';
        }

        if (ones % 3 != 0) {
            return 0;
        }
        if (ones == 0) {
            return (int) (((strLen - 1) * (strLen - 2) / 2) % MOD);
        }

        long oneThird = ones / 3, ways1 = 0, ways2 = 0;
        ones = 0;

        for (char ch : s.toCharArray()) {
            ones += ch - '0';
            if (ones == oneThird)
                ways1++;
            else if (ones == 2 * oneThird)
                ways2++;
        }

        return (int) ((ways1 * ways2) % MOD);
    }

    // Leetcode problem: 1545
    /*
     * Recursive solution. O(n)
     * */
    public char findKthBit(int n, int k) {
        if (n == 1 && k == 1)
            return '0';

        // length of s_i = 1, 3, 7, 15, 31... 2^n-1
        int len = (1 << n) - 1;
        int mid = len >> 1;

        // middle bit is always 1
        // mid is 0-based index & k is 1-based so compare k with mid + 1
        if (k == mid + 1)
            return '1';
        // if k <= mid then it lies in left side which is s_i-1
        if (k <= mid)
            return findKthBit(n - 1, k);

        // Lies in right side which is reversed and inverted or left side
        return findKthBit(n - 1, len + 1 - k) == '0' ? '1' : '0';
    }

}
