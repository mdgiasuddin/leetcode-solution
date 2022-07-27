package string;

import java.util.*;

public class StringSolution6 {

    public static void main(String[] args) {
        StringSolution6 stringSolution6 = new StringSolution6();

        System.out.println(stringSolution6.minFlipsMonoIncr("010110"));
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
            // If the number overflows integer range, then discard
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

    // Leetcode problem: 859
    /*
     * Buddy string
     * */
    public boolean buddyStrings(String s, String goal) {

        // If length are different or length is 1 then there is no option to match by swap
        if (s.length() != goal.length() || s.length() == 1) {
            return false;
        }

        // If they are equal then at least 1 duplicate character two swap with one another
        if (s.equals(goal)) {
            // If length > 26 then at least one duplicate character
            if (s.length() > 26) {
                return true;
            }

            Set<Character> characterSet = new HashSet<>();
            for (int i = 0; i < s.length(); i++) {
                char ch = s.charAt(i);
                if (characterSet.contains(ch)) {
                    return true;
                }
                characterSet.add(ch);
            }
            return false;
        } else {
            // There must be 2 different characters which can be swapped with one another to match with the goal
            int[] different = new int[2];

            int j = 0;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) != goal.charAt(i)) {
                    if (j < 2) {
                        different[j++] = i;
                    } else {
                        return false;
                    }
                }
            }

            return j == 2 && s.charAt(different[0]) == goal.charAt(different[1]) && s.charAt(different[1]) == goal.charAt(different[0]);
        }
    }

    // Leetcode problem: 880
    /*
     * First find out the total length of decoded string by scanning from left to right
     * Then remove character from the last one by one from the right to left and match with k
     * */
    public String decodeAtIndex(String s, int k) {
        long totalLength = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (Character.isDigit(ch)) {
                totalLength *= (ch - '0');
            } else {
                totalLength++;
            }
        }

        for (int i = s.length() - 1; i >= 0; i--) {
            char ch = s.charAt(i);
            k %= totalLength;

            if (k == 0 && Character.isLetter(ch)) {
                return String.valueOf(ch);
            }
            if (Character.isDigit(ch)) {
                totalLength /= (ch - '0');
            } else {
                totalLength--;
            }
            System.out.println(ch + " : " + totalLength);
        }

        return "";
    }

    // Leetcode problem: 899
    /*
     * If k = 1, just find the minimum of all rotations
     * Else sort the characters
     * It will work in the logic of bubble sort
     * If k > 1 then any 2 characters can be swapped. So sorting the characters will work.
     * */
    public String orderlyQueue(String s, int k) {

        if (k == 1) {
            String ans = s;

            for (int i = 1; i < s.length(); i++) {
                String temp = s.substring(i) + s.substring(0, i);
                if (temp.compareTo(ans) < 0) {
                    ans = temp;
                }
            }

            return ans;
        } else {
            char[] ans = s.toCharArray();
            Arrays.sort(ans);

            return String.copyValueOf(ans);
        }
    }

    // Leetcode problem: 916
    /*
     * Calculate maximum character count of each character of words2
     * Check which word of words1 contains all characters
     * */
    public List<String> wordSubsets(String[] words1, String[] words2) {
        List<String> result = new ArrayList<>();

        int[] maxChar = new int[26];

        for (String word : words2) {
            int[] charWord2 = new int[26];

            for (int i = 0; i < word.length(); i++) {
                charWord2[word.charAt(i) - 'a']++;
            }

            for (int i = 0; i < 26; i++) {
                maxChar[i] = Math.max(maxChar[i], charWord2[i]);
            }
        }

        for (String word : words1) {
            int[] charWord1 = new int[26];

            for (int i = 0; i < word.length(); i++) {
                charWord1[word.charAt(i) - 'a']++;
            }

            int i;
            for (i = 0; i < 26; i++) {
                if (charWord1[i] < maxChar[i]) break;
            }
            if (i == 26) {
                result.add(word);
            }
        }

        return result;
    }

    // Leetcode problem: 921
    public int minAddToMakeValid(String s) {
        /*Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (ch == '(') {
                stack.push(ch);
            } else {
                if (!stack.isEmpty() && stack.peek() == '(') {
                    stack.pop();
                } else {
                    stack.push(ch);
                }
            }
        }

        return stack.size();*/

        // Without extra memory
        int opening, closing;
        opening = closing = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(') {
                opening++;
            } else {
                if (opening > 0) {
                    opening--;
                } else {
                    closing++;
                }
            }
        }

        return opening + closing;

    }

    // Leetcode problem: 926
    /*
     * See Leetcode problem: 1653
     * */
    public int minFlipsMonoIncr(String s) {

        int extraOne = 0, flip = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '0') {
                if (extraOne > 0) {
                    extraOne--;
                    flip++;
                }
            } else {
                extraOne++;
            }
        }

        return flip;
    }

    // Leetcode problem: 940
    /*
     * Dynamic programming
     * */
    public int distinctSubseqII(String s) {
        int strLen = s.length();

        long[] dp = new long[strLen + 1];
        dp[0] = 1; // 1 for ""
        dp[1] = 2; // 1 for "" and another for "s[0]"
        int[] lastSeen = new int[26];
        lastSeen[s.charAt(0) - 'a'] = 1;

        long MOD = 1000000007;

        for (int i = 2; i <= strLen; i++) {
            char ch = s.charAt(i - 1);

            /*
             * If the character is new then dp[i] will be doubled dp[i-1]
             * For example "abcbd" -> dp[2] = "", "a", "b", "ab" dp[3] = will be the previous all and 'b' appended with all previous
             * So, extra string will be added. "c", "ac", "bc", "abc"
             * For dp[3] 'b' is not new. It has been seen at position 2. So, 'b' appended with all strings dp[1] ("b", "ab") will be duplicate
             * So, subtract dp[1] from dp[3] calculated doubling dp[2]
             * */
            dp[i] = (2 * dp[i - 1]) % MOD;

            if (lastSeen[ch - 'a'] > 0) {
                dp[i] = (dp[i] - dp[lastSeen[ch - 'a'] - 1]) % MOD;
            }

            lastSeen[ch - 'a'] = i;
        }

        // Deduct empty string from final result
        return (int) ((dp[strLen] - 1 + MOD) % MOD);
    }

    // Leetcode problem: 943

    // Leetcode problem: 1041
    public boolean isRobotBounded(String instructions) {
        List<Map.Entry<Integer, Integer>> directions = Arrays.asList(
                Map.entry(0, 1), // North
                Map.entry(1, 0), // East
                Map.entry(0, -1), // South
                Map.entry(-1, 0) // West
        );

        int currentDir = 0, currentX = 0, currentY = 0;

        for (int i = 0; i < instructions.length(); i++) {
            char ch = instructions.charAt(i);

            if (ch == 'G') {
                Map.Entry<Integer, Integer> direction = directions.get(currentDir);
                currentX += direction.getKey();
                currentY += direction.getValue();
            } else if (ch == 'L') {
                currentDir = (currentDir + 3) % 4;
            } else {
                currentDir = (currentDir + 1) % 4;
            }
        }

        // If Current direction is not north then after some completion of instructions it will make a circle
        // Else it will continue to move on a specific directions
        return (currentX == 0 && currentY == 0) || currentDir != 0;
    }

}
