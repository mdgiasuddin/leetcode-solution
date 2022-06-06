package recursion;

import java.util.ArrayList;
import java.util.List;

public class BacktrackingSolution2 {

    public static void main(String[] args) {
        BacktrackingSolution2 backtrackingSolution2 = new BacktrackingSolution2();

        System.out.println(backtrackingSolution2.combine(4, 2));
    }

    // Leetcode problem: 77
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        combine(n, k, result, new ArrayList<>(), 1);

        return result;
    }

    private void combine(int n, int k, List<List<Integer>> result, List<Integer> currentList, int current) {
        if (currentList.size() >= k) {
            result.add(new ArrayList<>(currentList));
            return;
        }

        for (int i = current; i <= n; i++) {

            // Check if required number of elements left to fill up k.
            if (n - i + 1 < k - currentList.size()) {
                return;
            }

            currentList.add(i);
            combine(n, k, result, currentList, i + 1);
            currentList.remove(currentList.size() - 1);
        }

    }

    // Leetcode problem: 52
    /*
     * This problem is similar to N-Queens (Leetcode problem: 51).
     * */
    public int totalNQueens(int n) {
        boolean[][] board = new boolean[n][n];

        return totalNQueens(0, board);
    }

    public int totalNQueens(int row, boolean[][] board) {
        if (row == board.length)
            return 1;

        int res = 0;
        for (int col = 0; col < board.length; col++) {
            if (isValidQueenPosition(board, row, col)) {
                board[row][col] = true;
                res += totalNQueens(row + 1, board);
                board[row][col] = false;
            }
        }

        return res;
    }

    private boolean isValidQueenPosition(boolean[][] board, int row, int col) {

        // Check the column of previous rows.
        for (int r = row - 1; r >= 0; r--) {
            if (board[r][col])
                return false;
        }

        // Check left diagonals of previous rows.
        int r = row - 1, c = col - 1;
        while (r >= 0 && c >= 0) {
            if (board[r][c])
                return false;
            r--;
            c--;
        }

        // Check right diagonals of previous rows.
        r = row - 1;
        c = col + 1;
        while (r >= 0 && c < board.length) {
            if (board[r][c])
                return false;
            r--;
            c++;
        }

        return true;
    }
}
