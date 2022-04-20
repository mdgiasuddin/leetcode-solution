package recursion;

import java.util.ArrayList;
import java.util.List;

public class BacktrackingSolution {

    public static void main(String[] args) {
        BacktrackingSolution recursionSolution = new BacktrackingSolution();
    }

    // Leetcode problem: 22
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        generateParenthesis(result, "", n, 0, 0);

        return result;
    }

    public void generateParenthesis(List<String> result, String current, int n, int open, int close) {
        if (open + close == 2 * n) {
            result.add(current);
            return;
        }
        if (open < n)
            generateParenthesis(result, current + "(", n, open + 1, close);
        if (close < open)
            generateParenthesis(result, current + ")", n, open, close + 1);
    }
}
