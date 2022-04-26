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

    // Leetcode problem: 79
    public boolean exist(char[][] board, String word) {
        int row = board.length, col = board[0].length;
        boolean[][] visited = new boolean[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++)
                if (exist(board, visited, 0, i, j, word))
                    return true;
        }
        return false;
    }

    public boolean exist(char[][] board, boolean[][] visited, int cur, int row, int col, String word) {
        if (cur == word.length())
            return true;
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length
                || board[row][col] != word.charAt(cur) || visited[row][col])
            return false;

        visited[row][col] = true;
        if (exist(board, visited, cur + 1, row - 1, col, word)
                || exist(board, visited, cur + 1, row, col - 1, word)
                || exist(board, visited, cur + 1, row + 1, col, word)
                || exist(board, visited, cur + 1, row, col + 1, word))
            return true;

        visited[row][col] = false;
        return false;
    }
}
