package string;

import java.util.*;

public class StringSolution6 {

    public static void main(String[] args) {
        StringSolution6 stringSolution6 = new StringSolution6();

        System.out.println(stringSolution6.kSimilarity("abccaacceecdeea", "bcaacceeccdeaae"));
    }

    // Leetcode problem: 816
    /*
     * Divide the string with left & right at every position and try to put '.' at every position of left and right
     * */
    public List<String> ambiguousCoordinates(String s) {
        List<String> result = new ArrayList<>();

        // Skip '(' & ')'
        for (int i = 2; i < s.length() - 1; i++) {
            for (String left : placeDecimal(s, 1, i)) {
                for (String right : placeDecimal(s, i, s.length() - 1)) {
                    result.add("(" + left + ", " + right + ")");
                }
            }
        }

        return result;
    }

    List<String> placeDecimal(String s, int left, int right) {
        List<String> decimalPoints = new ArrayList<>();

        for (int d = 1; d <= right - left; d++) {
            String leftString = s.substring(left, left + d);
            String rightString = s.substring(left + d, right);

            // Skip leading 0 and 0 at rightmost position after '.'
            if ((!leftString.startsWith("0") || leftString.equals("0")) && !rightString.endsWith("0"))
                decimalPoints.add(leftString + (d < right - left ? "." : "") + rightString);
        }
        return decimalPoints;
    }

    // Leetcode problem: 820

    // Leetcode problem: 842
    /*
     * Backtracking
     * */
    public List<Integer> splitIntoFibonacci(String num) {
        List<Integer> result = new ArrayList<>();
        return dfsFibonacci(0, num, result);
    }

    public List<Integer> dfsFibonacci(int start, String num, List<Integer> path) {
        if (path.size() > 2) {
            if (path.get(path.size() - 1) != path.get(path.size() - 2) + path.get(path.size() - 3)) {
                return new ArrayList<>();
            }
        }

        if (start >= num.length()) {
            return path.size() > 2 ? path : new ArrayList<>();
        }

        int cur = 0;
        List<Integer> ans = new ArrayList<>();
        for (int i = start; i < num.length(); i++) {

            // Avoid leading 0
            if (i > start && num.charAt(start) == '0') {
                return new ArrayList<>();
            }

            cur = cur * 10 + num.charAt(i) - '0';
            if (cur < 0) {
                return new ArrayList<>();
            }
            path.add(cur);
            ans = dfsFibonacci(i + 1, num, path);
            if (ans.size() > 2) {
                return ans;
            }

            // Backtrack
            path.remove(path.size() - 1);
        }
        return ans;
    }

    // Leetcode problem: 856
    /*
     * Score the parenthesis
     * Use stack
     * Whenever '(' comes save the score calculated so far and reset the score
     * Whenever ')' comes pop last score saved and update the current score
     * */
    public int scoreOfParentheses(String s) {
        Stack<Integer> stack = new Stack<>();
        int currentScore = 0;

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (ch == '(') {
                stack.push(currentScore);
                currentScore = 0;
            } else {
                // stack.pop give the score stored before '(' to which the ')' is balancing
                // Current score (Before updating) = 0 means ')' comes exactly after '(', So Math.max(...) will give 1
                // Otherwise will be the double of the previous calculated score
                currentScore = stack.pop() + Math.max(currentScore * 2, 1);
            }
        }

        return currentScore;
    }

    // Leetcode problem: 854
    /*
     * K-Similar Strings
     * Go to every child after swapping a character
     * */
    public int kSimilarity(String s1, String s2) {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(s1);
        visited.add(s1);
        int maxSwap = 0;
        while (!queue.isEmpty()) {
            int queueSize = queue.size();

            while (queueSize-- > 0) {
                String temp = queue.poll();
                if (temp.equals(s2)) {
                    return maxSwap;
                }

                for (String str : getSwappedString(temp, s2)) {
                    if (!visited.contains(str)) {
                        queue.add(str);
                        visited.add(str);
                    }
                }
            }
            maxSwap++;
        }

        return maxSwap;
    }

    List<String> getSwappedString(String str, String target) {
        List<String> result = new ArrayList<>();

        int i = 0;
        // If character of target and str matches no need to swap
        while (str.charAt(i) == target.charAt(i)) i++;

        for (int j = i + 1; j < target.length(); j++) {
            if (str.charAt(j) == target.charAt(i)) {
                result.add(str.substring(0, i) + str.charAt(j) + str.substring(i + 1, j) + str.charAt(i) + str.substring(j + 1));
            }
        }
        return result;
    }


}
