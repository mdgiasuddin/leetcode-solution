package recursion;

import trie.TrieNodeNew;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BacktrackingSolution {

    public static void main(String[] args) {
        BacktrackingSolution recursionSolution = new BacktrackingSolution();
    }

    // Leetcode problem: 17
    public List<String> letterCombinations(String digits) {
        if (digits.isEmpty())
            return new ArrayList<>();

        String[] strs = {"abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

        List<String> result = new ArrayList<>();

        letterCombinations(digits, strs, result, "", 0);

        return result;
    }

    public void letterCombinations(String digits, String[] strs, List<String> result, String current, int idx) {
        if (idx == digits.length()) {
            result.add(current);
            return;
        }

        int digit = digits.charAt(idx) - '0';
        for (int i = 0; i < strs[digit - 2].length(); i++) {
            letterCombinations(digits, strs, result, current + strs[digit - 2].charAt(i), idx + 1);
        }
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

    // Leetcode problem: 78
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        subsets(nums, result, new ArrayList<>(), 0);

        return result;
    }

    private void subsets(int[] nums, List<List<Integer>> result, List<Integer> currentList, int current) {
        result.add(new ArrayList<>(currentList));

        for (int i = current; i < nums.length; i++) {
            currentList.add(nums[i]);
            subsets(nums, result, currentList, i + 1);
            currentList.remove(currentList.size() - 1);
        }
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

    // Leetcode problem: 131
    /*
     * Palindrome partitioning
     * Find all the combination by backtracking
     * */
    public List<List<String>> partition(String s) {
        List<List<String>> finalResult = new ArrayList<>();
        partition(s, finalResult, new ArrayList<>(), 0, s.length());

        return finalResult;
    }

    public void partition(String s, List<List<String>> finalResult, List<String> currentList, int left, int right) {
        if (left >= right) {
            finalResult.add(new ArrayList<>(currentList));
            return;
        }

        for (int i = left + 1; i <= right; i++) {
            if (isPalindrome(s, left, i - 1)) {
                currentList.add(s.substring(left, i));
                partition(s, finalResult, currentList, i, right);
                currentList.remove(currentList.size() - 1);
            }
        }
    }

    public boolean isPalindrome(String str, int left, int right) {
        while (left < right) {
            if (str.charAt(left++) != str.charAt(right--))
                return false;
        }

        return true;
    }

    // Leetcode problem: 212
    public List<String> findWords(char[][] board, String[] words) {
        TrieNodeNew root = new TrieNodeNew();

        for (String word : words) {
            root.addWord(word);
        }

        int ROWS = board.length, COLS = board[0].length;
        boolean[][] visited = new boolean[ROWS][COLS];
        Set<String> result = new HashSet<>();

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                dfsWord(r, c, board, root, visited, result, "");
            }
        }

        return result.stream().toList();
    }

    private void dfsWord(int r, int c, char[][] board, TrieNodeNew root, boolean[][] visited, Set<String> result, String current) {
        if (r < 0 || r >= board.length || c < 0 || c >= board[0].length
                || !root.children.containsKey(board[r][c]) || visited[r][c]) {

            return;
        }

        visited[r][c] = true;
        current += board[r][c];

        root = root.children.get(board[r][c]);
        if (root.endNode) {
            result.add(current);
        }

        dfsWord(r - 1, c, board, root, visited, result, current);
        dfsWord(r + 1, c, board, root, visited, result, current);
        dfsWord(r, c - 1, board, root, visited, result, current);
        dfsWord(r, c + 1, board, root, visited, result, current);

        visited[r][c] = false;
    }
}
