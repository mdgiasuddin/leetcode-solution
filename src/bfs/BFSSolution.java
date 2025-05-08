package bfs;

import pair.Pair;

import java.util.*;

public class BFSSolution {
    public static void main(String[] args) {

        BFSSolution bfsSolution = new BFSSolution();

//        int[][] board = {
//                {-1, -1, -1, -1, -1, -1},
//                {-1, -1, -1, -1, -1, -1},
//                {-1, -1, -1, -1, -1, -1},
//                {-1, 35, -1, -1, 13, -1},
//                {-1, -1, -1, -1, -1, -1},
//                {-1, 15, -1, -1, -1, -1}
//        };

        int[][] board = {
                {-1, -1},
                {-1, 3}
        };

        System.out.println(bfsSolution.snakesAndLadders(board));
    }

    // Leetcode problem: 752

    /**
     * Run BFS to reach target
     * */
    public int openLock(String[] deadends, String target) {
        Set<String> deadEndsSet = new HashSet<>();
        Collections.addAll(deadEndsSet, deadends);

        if (deadEndsSet.contains("0000"))
            return -1;

        if (target.equals("0000"))
            return 0;

        Queue<Map.Entry<String, Integer>> queue = new LinkedList<>();
        queue.add(Map.entry("0000", 0));

        Set<String> visited = new HashSet<>();
        visited.add("0000");

        while (!queue.isEmpty()) {
            Map.Entry<String, Integer> parent = queue.poll();

            for (String child : getLockChildren(parent.getKey())) {
                if (child.equals(target))
                    return parent.getValue() + 1;
                if (!visited.contains(child) && !deadEndsSet.contains(child)) {
                    queue.add(Map.entry(child, parent.getValue() + 1));
                    visited.add(child);
                }
            }
        }

        return -1;
    }

    public List<String> getLockChildren(String parent) {
        List<String> children = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            String next = parent.substring(0, i) + (char) ((parent.charAt(i) - '0' + 1) % 10 + '0') + parent.substring(i + 1);
            String prev = parent.substring(0, i) + (char) ((parent.charAt(i) - '0' + 9) % 10 + '0') + parent.substring(i + 1);

            children.add(next);
            children.add(prev);
        }

        return children;
    }

    // Leetcode problem: 994
    public int orangesRotting(int[][] grid) {

        Queue<Pair> queue = new LinkedList<>();
        Set<Pair> visited = new HashSet<>();

        // Run BFS from rotten position
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == 2) {
                    queue.add(new Pair(r, c));
                    visited.add(new Pair(r, c));
                }
            }
        }

        int minTime = 0;
        while (!queue.isEmpty()) {
            int qSize = queue.size();

            while (qSize-- > 0) {
                Pair pair = queue.poll();

                rotOrange(pair.first - 1, pair.second, grid, queue, visited);
                rotOrange(pair.first + 1, pair.second, grid, queue, visited);
                rotOrange(pair.first, pair.second - 1, grid, queue, visited);
                rotOrange(pair.first, pair.second + 1, grid, queue, visited);
            }

            // Increase only if it goes to next level.
            if (!queue.isEmpty()) {
                minTime++;
            }
        }

        // Check if any fresh orange exists.
        for (int[] ints : grid) {
            for (int c = 0; c < grid[0].length; c++) {
                if (ints[c] == 1) {
                    return -1;
                }
            }
        }

        return minTime;
    }

    private void rotOrange(int r, int c, int[][] grid, Queue<Pair> queue, Set<Pair> visited) {
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length || grid[r][c] != 1
                || visited.contains(new Pair(r, c))) {

            return;
        }

        grid[r][c] = 2;
        queue.add(new Pair(r, c));
        visited.add(new Pair(r, c));
    }

    // Leetcode problem: 909
    public int snakesAndLadders(int[][] board) {

        int N = board.length;

        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        visited.add(1);
        queue.add(1);

        int moves = 1;
        while (!queue.isEmpty()) {
            int qSize = queue.size();

            while (qSize-- > 0) {
                int current = queue.poll();

                for (int i = 1; i <= 6; i++) {
                    int row = (current + i - 1) / N;
                    int col = row % 2 == 0 ? (current + i - 1) % N : N - 1 - (current + i - 1) % N;

                    int newPosition = board[N - 1 - row][col] == -1 ? current + i : board[N - 1 - row][col];

                    if (newPosition == N * N) {
                        return moves;
                    }

                    if (!visited.contains(newPosition)) {
                        queue.add(newPosition);
                        visited.add(newPosition);
                    }
                }
            }

            moves++;
        }

        return -1;
    }

    // Leetcode problem: 1871
    public boolean canReach(String s, int minJump, int maxJump) {
        int len = s.length();
        if (s.charAt(len - 1) == '1')
            return false;

        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);

        // Keep track of the farthest node to which all nodes have already been visited.
        int farthest = 0;
        while (!queue.isEmpty()) {
            int idx = queue.poll();

            for (int i = Math.max(idx + minJump, farthest + 1); i <= Math.min(len - 1, idx + maxJump); i++) {
                if (s.charAt(i) == '0') {
                    if (i == len - 1)
                        return true;
                    queue.add(i);
                }
            }

            farthest = idx + maxJump;
        }
        return false;
    }

    // Leetcode problem: 433

    /**
     * BFS Search
     * See Leetcode problem: 127
     * */
    public int minMutation(String start, String end, String[] bank) {
        Set<String> bankSet = new HashSet<>(List.of(bank));

        if (!bankSet.contains(end)) {
            return -1;
        }

        Queue<String> queue = new LinkedList<>();
        int maxLength = 0;
        queue.add(start);
        char[] dnaChars = {'A', 'C', 'G', 'T'};

        while (!queue.isEmpty()) {
            int currentSize = queue.size();
            maxLength++;
            while (currentSize-- > 0) {
                String top = queue.poll();
                for (int i = 0; i < top.length(); i++) {
                    for (char dnaChar : dnaChars) {
                        String temp = top.substring(0, i) + dnaChar + top.substring(i + 1);
                        if (temp.equals(top))
                            continue;

                        if (temp.equals(end)) {
                            return maxLength;
                        }
                        if (bankSet.contains(temp)) {
                            queue.add(temp);
                            bankSet.remove(temp);
                        }
                    }
                }
            }
        }

        return -1;
    }

    // Leetcode problem: 127

    /**
     * Word ladder
     * Breadth first search to find minimum distance
     * Find next node by changing one single character of each index of the word
     * */
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {

        Set<String> visited = new HashSet<>();
        Set<String> wordSet = new HashSet<>(wordList);

        if (!wordSet.contains(endWord))
            return 0;

        Queue<String> queue = new LinkedList<>();
        queue.add(beginWord);
        visited.add(beginWord);

        int res = 1;
        while (!queue.isEmpty()) {
            int qSize = queue.size();

            while (qSize-- > 0) {
                String word = queue.poll();

                if (word.equals(endWord))
                    return res;

                queue.addAll(getNeighborWords(word, visited, wordSet));
            }

            res++;

        }

        return 0;
    }

    private List<String> getNeighborWords(String word, Set<String> visited, Set<String> wordSet) {

        List<String> wordList = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {

            for (char c = 'a'; c <= 'z'; c++) {
                String neighbor = word.substring(0, i) + c + word.substring(i + 1);
                if (!neighbor.equals(word) && wordSet.contains(neighbor) && !visited.contains(neighbor)) {
                    wordList.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }

        return wordList;
    }

    // Leetcode problem: 126

    /**
     * This is extended version of Word Ladder (Leetcode problem: 127)
     * First run BFS to find word ladder then run DFS to get ladders.
     * */
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> result = new ArrayList<>();

        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordSet.contains(endWord))
            return new ArrayList<>();

        Queue<String> queue = new LinkedList<>();
        Map<String, Integer> visited = new HashMap<>();
        Map<String, List<String>> adjacencyList = new HashMap<>();
        boolean found = false;

        queue.add(beginWord);
        visited.put(beginWord, 0);
        int depth = 1;

        while (!queue.isEmpty()) {
            int qSize = queue.size();

            while (qSize-- > 0) {
                String word = queue.poll();

                if (word.equals(endWord)) {
                    found = true;
                    break;
                }

                List<String> adjacent = new ArrayList<>();

                List<String> children = getChildren(word, wordSet);

                for (String child : children) {
                    if (!visited.containsKey(child)) {
                        visited.put(child, depth);
                        adjacent.add(child);
                        queue.add(child);
                    } else if (visited.get(child) == depth) {
                        adjacent.add(child);
                    }
                }
                adjacencyList.put(word, adjacent);
            }

            if (found)
                break;

            depth++;
        }

        if (!found)
            return new ArrayList<>();


        List<String> currentList = new ArrayList<>();
        currentList.add(beginWord);

        dfsLadder(currentList, endWord, depth, result, adjacencyList);

        return result;
    }

    private List<String> getChildren(String word, Set<String> wordSet) {

        List<String> children = new ArrayList<>();

        for (int i = 0; i < word.length(); i++) {
            for (char ch = 'a'; ch <= 'z'; ch++) {
                String child = word.substring(0, i) + ch + word.substring(i + 1);
                if (!word.equals(child) && wordSet.contains(child)) {
                    children.add(child);
                }
            }
        }

        return children;
    }

    private void dfsLadder(List<String> currentList, String endWord, int depth, List<List<String>> result, Map<String, List<String>> adjacencyList) {
        if (currentList.get(currentList.size() - 1).equals(endWord)) {
            result.add(new ArrayList<>(currentList));
            return;
        }

        if (currentList.size() == depth) {
            return;
        }

        List<String> children = adjacencyList.get(currentList.get(currentList.size() - 1));

        for (String child : children) {
            currentList.add(child);

            dfsLadder(currentList, endWord, depth, result, adjacencyList);
            currentList.remove(currentList.size() - 1);
        }
    }
}
