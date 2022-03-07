package string;

import java.util.ArrayList;
import java.util.List;

public class StringSolution6 {

    public static void main(String[] args) {
        StringSolution6 stringSolution6 = new StringSolution6();
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
    public List<Integer> splitIntoFibonacci(String num) {
        List<Integer> res = new ArrayList<>();
        return dfs(0, num, res);
    }

    public List<Integer> dfs(int start, String num, List<Integer> path) {
        if (path.size() > 2) {
            if (path.get(path.size() - 1) != path.get(path.size() - 2) + path.get(path.size() - 3)) {
                return new ArrayList<>();
            }
        }
        if (start >= num.length()) {
            if (path.size() > 2) {
                return path;
            } else {
                return new ArrayList<>();
            }
        }
        int cur = 0;
        List<Integer> ans = new ArrayList<>();
        for (int i = start; i < num.length(); i++) {
            //cut
            if (i > start && num.charAt(start) == '0') {
                return new ArrayList<>();
            }
            cur = cur * 10 + num.charAt(i) - '0';
            if (cur < 0) {
                return new ArrayList<>();
            }
            path.add(cur);
            ans = dfs(i + 1, num, path);
            if (ans.size() > 2) {
                return ans;
            }
            path.remove(path.size() - 1);
        }
        return ans;
    }

}
