package recursion;

import trie.TrieNodeNew;

import java.util.*;

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

    // Leetcode problem: 39
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);

        List<List<Integer>> result = new ArrayList<>();

        combinationSum(candidates, target, 0, result, new ArrayList<>());

        return result;
    }

    public void combinationSum(int[] candidates, int target, int idx, List<List<Integer>> result, List<Integer> current) {

        if (target == 0) {
            result.add(new ArrayList<>(current));
            return;
        }

        if (idx == candidates.length || candidates[idx] > target) {
            return;
        }

        // Take the number.
        current.add(candidates[idx]);
        combinationSum(candidates, target - candidates[idx], idx, result, current);
        current.remove(current.size() - 1);

        // Don't take the number.
        combinationSum(candidates, target, idx + 1, result, current);
    }

    // Leetcode problem: 40
    /*
     * Combination sum (Leetcode problem: 39)
     * */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);

        List<List<Integer>> result = new ArrayList<>();
        combinationSum2(candidates, target, 0, result, new ArrayList<>());
        return result;
    }

    public void combinationSum2(int[] candidates, int target, int start, List<List<Integer>> result, List<Integer> combination) {
        if (target == 0) {
            result.add(new ArrayList<>(combination));
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            if (candidates[i] > target)
                return;
            if (i != start && candidates[i] == candidates[i - 1])
                continue;

            combination.add(candidates[i]);
            combinationSum2(candidates, target - candidates[i], i + 1, result, combination);
            combination.remove(combination.size() - 1);
        }
    }

    // Leetcode problem: 46
    /*
     * Permutation
     * Backtracking solution
     * */
    public List<List<Integer>> permute(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        boolean[] taken = new boolean[nums.length];

        permute(nums, result, new ArrayList<>(), taken);

        return result;
    }

    private void permute(int[] nums, List<List<Integer>> result, List<Integer> permuteSoFar, boolean[] taken) {

        if (permuteSoFar.size() == nums.length) {
            result.add(new ArrayList<>(permuteSoFar));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (!taken[i]) {
                permuteSoFar.add(nums[i]);
                taken[i] = true;
                permute(nums, result, permuteSoFar, taken);
                taken[i] = false;
                permuteSoFar.remove(permuteSoFar.size() - 1);
            }
        }
    }

    // Leetcode problem: 47
    /*
     * Backtracking solution
     * */
    public List<List<Integer>> permuteUnique(int[] nums) {
        Map<Integer, Integer> countMap = new HashMap<>();

        for (int num : nums) {
            if (countMap.containsKey(num)) {
                countMap.replace(num, countMap.get(num) + 1);
            } else {
                countMap.put(num, 1);
            }
        }

        List<List<Integer>> result = new ArrayList<>();
        permuteUnique(countMap, result, new ArrayList<>(), nums.length);

        return result;
    }

    private void permuteUnique(Map<Integer, Integer> countMap, List<List<Integer>> result, List<Integer> permuteSoFar, int requiredLength) {
        if (permuteSoFar.size() == requiredLength) {
            result.add(new ArrayList<>(permuteSoFar));
            return;
        }

        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() > 0) {
                permuteSoFar.add(entry.getKey());
                countMap.replace(entry.getKey(), entry.getValue() - 1);

                permuteUnique(countMap, result, permuteSoFar, requiredLength);

                permuteSoFar.remove(permuteSoFar.size() - 1);
                countMap.replace(entry.getKey(), entry.getValue() + 1);
            }
        }
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

    // Leetcode problem: 93
    public List<String> restoreIpAddresses(String s) {
        List<String> result = new ArrayList<>();
        if (s.length() > 12)
            return result;

        ipBacktrack(s, result, "", 0, 0);

        return result;
    }

    public void ipBacktrack(String s, List<String> result, String currentIp, int leftIndex, int dots) {
        if (dots == 4 && leftIndex == s.length()) {
            result.add(currentIp.substring(0, currentIp.length() - 1));
            return;
        }

        // If all digit not visited
        if (dots > 4) {
            return;
        }

        // Check all possible 3 cases
        for (int i = leftIndex; i <= Math.min(leftIndex + 2, s.length() - 1); i++) {
            if (Integer.parseInt(s.substring(leftIndex, i + 1)) <= 255 && (i == leftIndex || s.charAt(leftIndex) != '0')) {
                ipBacktrack(s, result, currentIp + s.substring(leftIndex, i + 1) + ".", i + 1, dots + 1);
            }
        }
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

    public List<List<String>> solveNQueens(int n) {
        char[][] board = new char[n][n];

        for (char[] chars : board)
            Arrays.fill(chars, '.');

        List<List<String>> result = new ArrayList<>();
        solveNQueens(0, board, result);
        return result;
    }

    // Leetcode problem: 51
    private void solveNQueens(int row, char[][] board, List<List<String>> result) {
        if (row == board.length) {
            result.add(convertArrayToList(board));
            return;
        }

        // In every column of the current row, place the queen and backtrack.
        for (int col = 0; col < board.length; col++) {
            if (isValidQueenPosition(board, row, col)) {
                board[row][col] = 'Q';
                solveNQueens(row + 1, board, result);
                board[row][col] = '.';
            }
        }

    }

    private boolean isValidQueenPosition(char[][] board, int row, int col) {

        // Check the column of previous rows.
        for (int r = row - 1; r >= 0; r--) {
            if (board[r][col] == 'Q')
                return false;
        }

        // Check left diagonals of previous rows.
        int r = row - 1, c = col - 1;
        while (r >= 0 && c >= 0) {
            if (board[r][c] == 'Q')
                return false;
            r--;
            c--;
        }

        // Check right diagonals of previous rows.
        r = row - 1;
        c = col + 1;
        while (r >= 0 && c < board.length) {
            if (board[r][c] == 'Q')
                return false;
            r--;
            c++;
        }

        return true;
    }

    // Auxiliary method to convert the board to list of string.
    private List<String> convertArrayToList(char[][] board) {

        List<String> list = new ArrayList<>();

        for (char[] chars : board) {
            StringBuilder str = new StringBuilder();
            for (char ch : chars) {
                str.append(ch);
            }
            list.add(str.toString());
        }

        return list;
    }
}
