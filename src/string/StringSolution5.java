package string;

import tree.TreeNode;

import java.util.*;

public class StringSolution5 {
    public static void main(String[] args) {
        StringSolution5 stringSolution5 = new StringSolution5();

        System.out.println(stringSolution5.lengthOfLongestSubstringTwoDistinct("abcbaabc"));
    }

    // Leetcode problem: 516
    /*
     * Dynamic programming
     * See Leetcode problem: 132 & 647
     * */
    public int longestPalindromeSubseq(String s) {
        int strLen = s.length();
        int[][] palindromeTable = new int[strLen][strLen];

        for (int i = 0; i < strLen; i++) {
            palindromeTable[i][i] = 1;
        }
        for (int i = 0; i < strLen - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                palindromeTable[i][i + 1] = 2;
            } else {
                palindromeTable[i][i + 1] = 1;
            }
        }

        for (int currentLen = 3; currentLen <= strLen; currentLen++) {
            for (int i = 0; i < strLen - (currentLen - 1); i++) {
                int j = i + currentLen - 1;

                if (s.charAt(i) == s.charAt(j)) {
                    palindromeTable[i][j] = 2 + palindromeTable[i + 1][j - 1];
                } else {
                    palindromeTable[i][j] = Math.max(palindromeTable[i][j - 1], palindromeTable[i + 1][j]);
                }
            }
        }

        return palindromeTable[0][strLen - 1];
    }

    // Leetcode problem: 91
    /*
     * Solve by dynamic programming
     * */
    public int numDecodingsI(String s) {
        /*
        int[] dp = new int[s.length() + 1];
        if (s.charAt(0) == '0')
            return 0;

        // base case
        dp[0] = dp[1] = 1;

        for (int i = 2; i <= s.length(); i++) {
            char currentChar = s.charAt(i - 1), prevChar = s.charAt(i - 2);

            // If a partition can be made with current character
            if (currentChar != '0') {
                dp[i] += dp[i - 1];
            }

            // If a partition can be made with previous character
            if (prevChar == '1' || (prevChar == '2' && currentChar <= '6')) {
                dp[i] += dp[i - 2];
            }
        }

        return dp[s.length()];
        */

        // Efficient Solution without saving all the state, we can store only 2 previous state

        if (s.charAt(0) == '0')
            return 0;

        if (s.length() == 1)
            return 1;

        int prevSolution, prevPrevSolution, currentSolution = 0;
        prevPrevSolution = prevSolution = 1;

        for (int i = 2; i <= s.length(); i++) {
            currentSolution = 0;
            char currentChar = s.charAt(i - 1), prevChar = s.charAt(i - 2);

            if (currentChar != '0') {
                currentSolution += prevSolution;
            }
            if (prevChar == '1' || (prevChar == '2' && currentChar <= '6')) {
                currentSolution += prevPrevSolution;
            }

            prevPrevSolution = prevSolution;
            prevSolution = currentSolution;
        }

        return currentSolution;

    }

    // Leetcode problem: 639
    /*
     * The problem is similar of problem: 91
     * There is '*' character extra
     * Solve by Dynamic programming
     * */
    public int numDecodings(String s) {
        if (s.charAt(0) == '0')
            return 0;

        long prevSolution, prevPrevSolution, currentSolution = 0;

        // Base case 0 and 1 position
        prevPrevSolution = 1;
        prevSolution = s.charAt(0) == '*' ? 9 : 1;

        if (s.length() == 1)
            return (int) prevSolution;

        long MOD = 1_000_000_007;
        for (int i = 2; i <= s.length(); i++) {
            char currentChar = s.charAt(i - 1), prevChar = s.charAt(i - 2);
            currentSolution = 0;

            // Consider only current character
            // If character is '*' then it can be any of 9 character
            if (currentChar == '*') {
                currentSolution += (9 * prevSolution) % MOD;
            } else if (currentChar != '0') {
                currentSolution += prevSolution % MOD;
            }

            // Make pair with previous character
            if (prevChar == '*') {
                // If 2 '*' side by side then there are 15 options. [11, 12, ..., 19, 21, 22, ..., 26]
                if (currentChar == '*') {
                    currentSolution += (15 * prevPrevSolution) % MOD;
                }

                // If current char is a digit less than '6' then for '*' we have 2 option [1, 2]
                else if (currentChar <= '6') {
                    currentSolution += (2 * prevPrevSolution) % MOD;
                }

                // If current char is greater than '6' we have only one option for '*' [1]
                else {
                    currentSolution += prevPrevSolution % MOD;
                }
            } else if (prevChar == '1') {
                // If prev char is '1' then there are 9 options for '*' [11, 12, ..., 19]
                if (currentChar == '*') {
                    currentSolution += (9 * prevPrevSolution) % MOD;
                }
                // If current char is any digit then only one option [1Digit]
                else {
                    currentSolution += prevPrevSolution % MOD;
                }
            } else if (prevChar == '2') {
                // If prev char is '2' then there are 6 options for '*' [21, 22, ..., 26]
                if (currentChar == '*') {
                    currentSolution += (6 * prevPrevSolution) % MOD;
                } else if (currentChar <= '6') {
                    currentSolution += prevPrevSolution % MOD;
                }
            }

            prevPrevSolution = prevSolution;
            prevSolution = currentSolution;
        }

        return (int) (currentSolution % MOD);
    }

    // Leetcode problem: 686
    /*
     * Math.ceil(b.length() / a.length()) or Math.ceil(b.length() / a.length()) + 1
     * */

    // Leetcode problem: 1028
    /*
     * Build up a stack and find out right parent
     * */
    public TreeNode recoverFromPreorder(String traversal) {
        int i = 0;
        Stack<TreeNode> stack = new Stack<>();
        while (i < traversal.length()) {
            int level = 0;

            while (traversal.charAt(i) == '-') {
                level++;
                i++;
            }

            int numStartIndex = i;
            while (i < traversal.length() && Character.isDigit(traversal.charAt(i))) {
                i++;
            }
            int num = Integer.parseInt(traversal.substring(numStartIndex, i));

            TreeNode node = new TreeNode(num);

            if (stack.isEmpty()) {
                stack.push(node);
                continue;
            }

            // Find out right parent
            while (stack.size() > level) {
                stack.pop();
            }

            TreeNode top = stack.peek();
            // First try to insert at left child
            if (top.left == null) {
                top.left = node;
            } else {
                top.right = node;
            }

            stack.push(node);
        }

        while (stack.size() > 1) {
            stack.pop();
        }

        return stack.pop();
    }

    // Leetcode problem: 467
    /*
     * Build up a table for every character with the maximum count which maintain serial with previous character
     * Sum up all the counts
     * */
    public int findSubstringInWraproundString(String p) {
        int[] dp = new int[26];

        int len = 0, sum = 0;

        for (int i = 0; i < p.length(); i++) {
            // If current character is the alphabetic next character of previous character then increment the length
            if (i >= 1 && (p.charAt(i) - p.charAt(i - 1) + 26) % 26 == 1) {
                len++;
            } else {
                len = 1;
            }

            // Update maximum length for this character
            dp[p.charAt(i) - 'a'] = Math.max(dp[p.charAt(i) - 'a'], len);
        }

        for (int count : dp) {
            sum += count;
        }

        return sum;
    }

    // Leetcode problem: 767
    /*
     * Build up a max-priority queue the count of characters
     * Every time append the character with maximum count then second maximum count
     * Update count and reorder the queue
     * */
    public String reorganizeString(String s) {
        PriorityQueue<CharCounter> queue = new PriorityQueue<>((c1, c2) -> c2.count - c1.count);

        int[] counts = new int[26];

        for (int i = 0; i < s.length(); i++) {
            counts[s.charAt(i) - 'a']++;
        }

        for (int i = 0; i < counts.length; i++) {
            if (counts[i] > 0) {
                queue.add(new CharCounter((char) ('a' + i), counts[i]));
            }
        }

        StringBuilder result = new StringBuilder();
        while (!queue.isEmpty()) {
            CharCounter max = queue.poll();
            result.append(max.ch);

            if (max.count > 1) {

                // If there is no other character left
                if (queue.isEmpty())
                    return "";

                CharCounter secondMax = queue.poll();
                result.append(secondMax.ch);

                max.count--;
                queue.add(max);

                if (--secondMax.count > 0) {
                    queue.add(secondMax);
                }
            }
        }

        return result.toString();
    }

    // 37.1 Longest Substring Which Contains 2 Unique Characters (From Book)
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        if (s.length() <= 2)
            return s.length();

        Map<Character, Integer> map = new HashMap<>();
        map.put(s.charAt(0), 0);
        map.put(s.charAt(1), 1);

        int left = 0, right = 2, maxLength = 2;

        while (right < s.length()) {
            if (map.containsKey(s.charAt(right))) {
                maxLength = Math.max(maxLength, right - left + 1);
            } else {
                int index = s.length();
                char ch = '0';

                for (Map.Entry<Character, Integer> entry : map.entrySet()) {
                    if (entry.getValue() < index) {
                        index = entry.getValue();
                        ch = entry.getKey();
                    }
                }

                left = map.get(ch) + 1;
                map.remove(ch);
            }

            map.put(s.charAt(right), right);
            right++;

        }

        return maxLength;
    }

    // Leetcode problem: 293
    // Lintcode problem: 914
    public List<String> generatePossibleNextMoves(String s) {
        List<String> result = new ArrayList<>();

        char[] chars = s.toCharArray();

        for (int i = 0; i < s.length() - 1; i++) {
            if (chars[i] == '+' && chars[i + 1] == '+') {
                chars[i] = chars[i + 1] = '-';

                result.add(String.valueOf(chars));

                chars[i] = chars[i + 1] = '+';
            }
        }

        return result;
    }

    // Leetcode problem: 294
    // Lintcode problem: 913
    public boolean canWin(String s) {
        char[] chars = s.toCharArray();
        return canWin(chars);
    }

    public boolean canWin(char[] chars) {

        for (int i = 0; i < chars.length - 1; i++) {
            if (chars[i] == '+' && chars[i + 1] == '+') {
                chars[i] = chars[i + 1] = '-';

                // Don't return in this line. First restore the '+' then return.
                boolean win = canWin(chars);

                chars[i] = chars[i + 1] = '+';

                if (!win)
                    return true;
            }
        }

        return false;
    }
}
