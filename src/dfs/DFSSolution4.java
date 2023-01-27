package dfs;

import java.util.*;
import java.util.stream.Collectors;

public class DFSSolution4 {

    // Leetcode problem: 1443
    /*
     * Minimum Time to Collect All Apples in a Tree.
     * */
    public int minTime(int n, int[][] edges, List<Boolean> hasApple) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        return dfsGraph(graph, hasApple, 0, -1);
    }

    private int dfsGraph(List<List<Integer>> graph, List<Boolean> hasApple, int node, int parent) {
        int res = 0;

        for (int child : graph.get(node)) {
            if (child == parent) {
                continue;
            }

            int childRes = dfsGraph(graph, hasApple, child, node);

            if (childRes > 0 || Boolean.TRUE.equals(hasApple.get(child))) {
                res += 2 + childRes;
            }
        }

        return res;
    }

    // Leetcode problem: 472
    /*
     * Concatenated Words.
     * Explanation: https://www.youtube.com/watch?v=iHp7fjw1R28
     * */
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        Set<String> wordSet = Arrays.stream(words).collect(Collectors.toSet());

        Map<String, Boolean> map = new HashMap<>();
        List<String> result = new ArrayList<>();
        for (String word : words) {
            if (dfsWord(word, wordSet, map)) {
                result.add(word);
            }
        }

        return result;
    }

    private boolean dfsWord(String word, Set<String> words, Map<String, Boolean> map) {
        if (map.containsKey(word)) {
            return map.get(word);
        }

        for (int i = 1; i <= word.length(); i++) {
            String prefix = word.substring(0, i);
            String suffix = word.substring(i);

            if ((words.contains(prefix) && words.contains(suffix)) || (words.contains(prefix) && dfsWord(suffix, words, map))) {
                map.put(word, true);
                return true;
            }
        }

        map.put(word, false);
        return false;
    }
}
