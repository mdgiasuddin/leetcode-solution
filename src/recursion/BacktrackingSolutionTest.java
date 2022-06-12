package recursion;

import indefinite.UtilClass;

import java.util.Arrays;

public class BacktrackingSolutionTest {

    public void solveNQueen(int n) {
        int[][] board = new int[n][n];
        board[0][0] = 1;

        if (solveNQueen(board, n, 0)) {
            UtilClass.print2DArray(board);
        }
    }

    private boolean solveNQueen(int[][] board, int n, int row) {
        if (row >= n)
            return true;

        for (int i = 0; i < n; i++) {
            if (isQueenSafe(board, n, row, i)) {
                board[row][i] = 1;
                if (solveNQueen(board, n, row + 1))
                    return true;
                board[row][i] = 0;
            }
        }
        return false;
    }

    private boolean isQueenSafe(int[][] board, int n, int row, int col) {
        // check col
        for (int i = row - 1; i >= 0; i--) {
            if (board[i][col] == 1)
                return false;
        }
        // check lower left diagonal

        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1)
                return false;
        }
        // check lower right diagonal
        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
            if (board[i][j] == 1)
                return false;
        }

        return true;

    }

    public void nightsTour(int n) {
        int[][] board = new int[n][n];
        int[][] move = new int[][]{
                {-2, -2, -1, -1, 2, 2, 1, 1},
                {-1, 1, -2, 2, -1, -1, -2, 2}
        };
        for (int[] boardRow : board) {
            Arrays.fill(boardRow, -1);
        }
        board[0][0] = 0;

        if (nightsTour(board, move, n, 0, 0, 1)) {
            UtilClass.print2DArray(board);
        }
    }

    private boolean nightsTour(int[][] board, int[][] move, int n, int curX, int curY, int currentMove) {
        if (currentMove >= n * n)
            return true;

        for (int i = 0; i < 8; i++) {
            int nextX = curX + move[0][i];
            int nextY = curY + move[1][i];

            if (isValidNightMove(board, n, nextX, nextY)) {
                board[nextX][nextY] = currentMove;
                if (nightsTour(board, move, n, nextX, nextY, 1 + currentMove))
                    return true;
                board[nextX][nextY] = -1;
            }
        }

        return false;

    }

    private boolean isValidNightMove(int[][] board, int n, int row, int col) {
        return row >= 0 && row < n && col >= 0 && col < n && board[row][col] == -1;
    }

    public void rateMaze() {
        int[][] maze = {
                {1, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 0, 1, 0},
                {1, 1, 1, 1}
        };
        if (maze[0][0] == 0)
            return;

        int m = maze.length, n = maze[0].length;
        maze[0][0] = 2;
        if (rateMaze(maze, m, n, 0, 0)) {
            UtilClass.print2DArray(maze);
        }

    }

    private boolean rateMaze(int[][] maze, int m, int n, int row, int col) {
        if (row >= m - 1 && col >= n - 1) {
            return true;
        }

        if (row + 1 < m && maze[row + 1][col] == 1) {
            maze[row + 1][col] = 2;
            if (rateMaze(maze, m, n, row + 1, col))
                return true;
            maze[row + 1][col] = 1;
        }
        if (col + 1 < n && maze[row][col + 1] == 1) {
            maze[row][col + 1] = 2;
            if (rateMaze(maze, m, n, row, col + 1))
                return true;
            maze[row][col + 1] = 1;
        }

        return false;
    }

}
