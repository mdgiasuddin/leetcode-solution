package string;

import indefinite.UtilClass;
import tree.TreeNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StringSolution {

    public static void main(String[] args) {
        StringSolution stringSolution = new StringSolution();
//        System.out.println(stringSolution.lengthOfLongestSubstring("abcdab"));
//        System.out.println(stringSolution.convert("PAYPALISHIRING", 3));
//        stringSolution.isMatchMemoryOptimized("", "******");
//        System.out.println(stringSolution.minCut("banana"));

        System.out.println(stringSolution.calculateII("3-5/3*3+2"));
    }

    // Leetcode problem: 5
    /*
     * for every index traverse left and right simultaneously if palindrome found
     * palindrome can be odd length or even length
     * check both even and odd length
     * */
    public String longestPalindrome(String s) {
        if (s.length() == 0)
            return s;

        int maxLength = 1;
        int start = 0, len = s.length(), high, low;
        for (int i = 0; i < s.length(); i++) {
            // even length substring
            high = i;
            low = i - 1;
            while (low >= 0 && high < len && s.charAt(low) == s.charAt(high)) {
                if (high - low + 1 > maxLength) {
                    maxLength = high - low + 1;
                    start = low;
                }
                high++;
                low--;
            }

            // odd length substring
            high = i + 1;
            low = i - 1;
            while (low >= 0 && high < len && s.charAt(low) == s.charAt(high)) {
                if (high - low + 1 > maxLength) {
                    maxLength = high - low + 1;
                    start = low;
                }
                high++;
                low--;
            }
        }

        return s.substring(start, start + maxLength);
    }

    // Leetcode problem: 6
    /*
     * Zigzag conversion to number of rows
     * Efficient solution without space
     * Find the next character every time to insert
     * Traverse the number of row incremental stage
     * */
    public String convert(String s, int numRows) {
        if (numRows == 1)
            return s;

        int inc = 2 * (numRows - 1);
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < numRows; i++) {
            for (int j = i; j < s.length(); j += inc) {

                // Character for incremental stage
                ans.append(s.charAt(j));

                // Character for decremental stage
                if (i > 0 && i < numRows - 1 && j + inc - 2 * i < s.length())
                    ans.append(s.charAt(j + inc - 2 * i));
            }
        }
        return ans.toString();
    }

    // Leetcode problem: 43
    /*
     * Multiply two number by string
     * */
    public String multiply(String num1, String num2) {
        int m = num1.length();
        int n = num2.length();
        char[] sum = new char[m + n];
        int i, j;
        for (i = 0; i < m + n; i++)
            sum[i] = '0';

        for (i = m - 1; i >= 0; i--) {
            int carry = 0;
            for (j = n - 1; j >= 0; j--) {
                int temp = (num1.charAt(i) - '0') * (num2.charAt(j) - '0') + sum[i + j + 1] - '0' + carry;
                sum[i + j + 1] = (char) (temp % 10 + '0');
                carry = temp / 10;
            }
            sum[i] = (char) (sum[i] + carry);
        }

        i = 0;
        while (sum[i] == '0')
            i++;

        return String.copyValueOf(sum, i, m + n - i);
    }

    // Leetcode problem: 44
    /*
     * Wildcard matching
     * Dynamic programming
     * M[i][j] = M[i-1][j-1] if s[i] = p[j] || p[j] = ?
     * M[i][j] = M[i-1][j] || M[i][j-1] if p[j] = * , else false
     * */
    public boolean isMatch(String s, String p) {
        int m = s.length(), n = p.length();
        boolean[][] matrix = new boolean[m + 1][n + 1];
        matrix[0][0] = true;
        for (int i = 1; i <= m; i++) {
            matrix[i][0] = false;
        }
        for (int j = 1; j <= n; j++) {
            if (p.charAt(j - 1) == '*')
                matrix[0][j] = matrix[0][j - 1];
            else
                matrix[0][j] = false;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '?')
                    matrix[i][j] = matrix[i - 1][j - 1];
                else if (p.charAt(j - 1) == '*')
                    matrix[i][j] = matrix[i][j - 1] || matrix[i - 1][j];
                else
                    matrix[i][j] = false;
            }
        }

        return matrix[m][n];
    }

    /*
     * This is memory optimized version of wildcard matching
     * We need last 2 rows only. Continuously build up last rows by previous and copy it to previous
     * */
    public boolean isMatchMemoryOptimized(String s, String p) {
        int m = s.length(), n = p.length();
        boolean[][] matrix = new boolean[2][n + 1];
        matrix[0][0] = true;
        matrix[1][0] = false;

        for (int j = 1; j <= n; j++) {
            if (p.charAt(j - 1) == '*')
                matrix[0][j] = matrix[0][j - 1];
            else
                matrix[0][j] = false;
        }

        if (s.length() == 0)
            return matrix[0][n];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '?')
                    matrix[1][j] = matrix[0][j - 1];
                else if (p.charAt(j - 1) == '*')
                    matrix[1][j] = matrix[1][j - 1] || matrix[0][j];
                else
                    matrix[1][j] = false;
            }

            System.arraycopy(matrix[1], 0, matrix[0], 0, n + 1);
        }

        UtilClass.print2DArrayBoolean(matrix);
        return matrix[1][n];
    }

    // Leetcode problem: 10
    /*
     * Regular expression matching
     * Dynamic programming
     * M[i][j] = M[i-1][j-1] if s[i] = p[j] or p[j] = .
     * M[i][j] = M[i][j-2] or (M[i-1][j] if s[i] = p[j-1]) if p[j] = * else false
     * */
    public boolean isMatchRegularExpression(String s, String p) {
        int m = s.length(), n = p.length();
        boolean[][] matrix = new boolean[m + 1][n + 1];
        matrix[0][0] = true;
        for (int i = 1; i <= m; i++) {
            matrix[i][0] = false;
        }
        for (int j = 1; j <= n; j++) {
            if (p.charAt(j - 1) == '*') {
                matrix[0][j] = matrix[0][j - 2];
            } else {
                matrix[0][j] = false;
            }
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '.') {
                    matrix[i][j] = matrix[i - 1][j - 1];
                } else if (p.charAt(j - 1) == '*') {
                    matrix[i][j] = matrix[i][j - 2] || (matrix[i - 1][j] && (s.charAt(i - 1) == p.charAt(j - 2) || p.charAt(j - 2) == '.'));
                } else {
                    matrix[i][j] = false;
                }
            }
        }
        return matrix[m][n];
    }

    // Leetcode problem: 132
    /*
     * Palindrome partitioning II
     * Build up a table of which substrings are palindrome O(n^2)
     * Then find the minimum cut
     * */
    public int minCut(String s) {

        int lenString = s.length();
        boolean[][] palindromeArray = new boolean[lenString][lenString];

        // All substring of length 1 is a palindrome
        for (int i = 0; i < lenString; i++) {
            palindromeArray[i][i] = true;
        }

        // All substring of length 2 will be palindrome if 2 character matches each other
        for (int i = 0; i < lenString - 1; i++) {
            palindromeArray[i][i + 1] = (s.charAt(i) == s.charAt(i + 1));
        }

        // Find palindrome of length 3 to n
        for (int currLength = 3; currLength <= lenString; currLength++) {
            // currLength - 1 already calculated
            for (int i = 0; i < lenString - (currLength - 1); i++) {
                int j = i + (currLength - 1);
                palindromeArray[i][j] = s.charAt(i) == s.charAt(j) && palindromeArray[i + 1][j - 1];
            }
        }

        // Find the minimum cut value
        int[] cuts = new int[lenString];
        for (int i = 0; i < lenString; i++) {
            if (palindromeArray[0][i]) {
                cuts[i] = 0;
            } else {
                int temp = Integer.MAX_VALUE;
                for (int j = 0; j < i; j++) {
                    if (palindromeArray[j + 1][i] && cuts[j] + 1 < temp) {
                        temp = cuts[j] + 1;
                    }
                }
                cuts[i] = temp;
            }
        }

        return cuts[lenString - 1];
    }

    // Leetcode problem: 139
    /*
     * Word break
     * */
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> wordSet = new HashSet<>(wordDict);

        int strLen = s.length();

        boolean[] breakPossibles = new boolean[strLen];

        for (int i = 0; i < strLen; i++) {
            if (wordSet.contains(s.substring(0, i + 1))) {
                breakPossibles[i] = true;
            } else {
                boolean temp = false;
                for (int j = 0; j < i; j++) {
                    if (breakPossibles[j] && wordSet.contains(s.substring(j + 1, i + 1)))
                        temp = true;
                }
                breakPossibles[i] = temp;
            }
        }

        return breakPossibles[strLen - 1];
    }

    // Leetcode problem: 257
    /*
     * Binary Tree Paths
     * Run dfs from root to leaf
     * */
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> pathList = new ArrayList<>();
        String path = "";
        dfsBinaryTree(root, pathList, path);
        return pathList;
    }

    public void dfsBinaryTree(TreeNode root, List<String> pathList, String path) {
        if (root == null)
            return;
        if (root.left == null && root.right == null) {
            path += root.val;
            pathList.add(path);
            return;
        }
        dfsBinaryTree(root.left, pathList, path + root.val + "->");
        dfsBinaryTree(root.right, pathList, path + root.val + "->");
    }

    // Leetcode problem: 140
    /*
     * Word break II
     * Backtracking solution
     * */
    public List<String> wordBreakII(String s, List<String> wordDict) {
        List<String> resultList = new ArrayList<>();
        wordBreakII(s, new HashSet<>(wordDict), resultList, 0, new ArrayList<>());

        return resultList;
    }

    public void wordBreakII(String s, Set<String> wordDict, List<String> resultList, int indexSoFar, List<String> stringSoFar) {

        if (indexSoFar >= s.length()) {
            resultList.add(String.join(" ", stringSoFar));
            return;
        }
        for (int i = indexSoFar; i < s.length(); i++) {
            if (wordDict.contains(s.substring(indexSoFar, i + 1))) {
                stringSoFar.add(s.substring(indexSoFar, i + 1));
                wordBreakII(s, wordDict, resultList, i + 1, stringSoFar);
                stringSoFar.remove(stringSoFar.size() - 1);
            }
        }
    }

    // Leetcode problem: 227
    /*
     * Basic calculator II
     * For every new operator calculate the previous operator
     * Operation of '*' and '/' are little bit tricky
     * */
    public int calculateII(String s) {
        s = s.trim();
        int sum = 0, num = 0, last = 0;
        char operator = '+';

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (!Character.isWhitespace(ch)) {
                if (Character.isDigit(ch)) {
                    num = num * 10 + ch - '0';
                }
                if (!Character.isDigit(ch) || i == s.length() - 1) {
                    switch (operator) {
                        case '+' -> {
                            sum = sum + num;
                            last = num;
                        }
                        case '-' -> {
                            sum = sum - num;
                            last = -num;
                        }
                        case '*' -> {
                            sum = (sum - last) + (last * num);
                            last = last * num;
                        }
                        case '/' -> {
                            sum = (sum - last) + (last / num);
                            last = last / num;
                        }
                    }
                    num = 0;
                    operator = ch;
                }
            }
        }
        return sum;
    }

    // Leetcode problem: 680
    /*
     * Check character from left and right until mismatch
     * If mismatch found then skip left and check if palindrome and skip right and check if palindrome
     * If anyone is palindrome then return true
     * */
    public boolean validPalindrome(String s) {
        int left = 0, right = s.length() - 1;

        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                break;
            }
            left++;
            right--;
        }

        if (left >= right) return true;
        return isPalindrome(s, left + 1, right) || isPalindrome(s, left, right - 1);
    }

    public boolean isPalindrome(String str, int left, int right) {
        while (left < right) {
            if (str.charAt(left++) != str.charAt(right--))
                return false;
        }

        return true;
    }

    // Leetcode problem: 1653
    public int minimumDeletions(String s) {
        int extraB = 0, deletion = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'b') {
                extraB++;
            } else {
                /*
                 * If 'a' appears after some 'b' then delete 'a'
                 * But if number of 'a' is more than 'b' then delete 'b' and extra 'b' count will be reset
                 * */
                if (extraB > 0) {
                    extraB--;
                    deletion++;
                }
            }
        }

        return deletion;
    }

    // Leetcode problem: 1616


}
