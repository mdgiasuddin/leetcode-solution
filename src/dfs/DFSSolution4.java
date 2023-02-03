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

    // Leetcode problem: 1519
    /*
     * Number of Nodes in the Sub-Tree With the Same Label.
     * Explanation: https://www.youtube.com/watch?v=X8WZApluMEw&list=PLy38cn8b_xMfO7CGsUDIsYGps37yKaQ9X&index=42
     * */
    public int[] countSubTrees(int n, int[][] edges, String labels) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        int[] res = new int[n];
        dfsSubTrees(0, -1, graph, res, labels);
        return res;
    }

    private int[] dfsSubTrees(int node, int parent, List<List<Integer>> graph, int[] res, String labels) {
        int[] count = new int[26];
        char label = labels.charAt(node);
        count[label - 'a'] = 1;

        for (int child : graph.get(node)) {
            if (child != parent) {
                int[] childCount = dfsSubTrees(child, node, graph, res, labels);
                for (int i = 0; i < 26; i++) {
                    count[i] += childCount[i];
                }
            }
        }

        res[node] = count[label - 'a'];
        return count;
    }

    // Leetcode problem: 2477
    /*
     * Minimum Fuel Cost to Report to the Capital.
     * Explanation: https://www.youtube.com/watch?v=O7Z0kiWVk5c&list=PLy38cn8b_xMfO7CGsUDIsYGps37yKaQ9X&index=44
     * */
    public long minimumFuelCost(int[][] roads, int seats) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= roads.length; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] road : roads) {
            graph.get(road[0]).add(road[1]);
            graph.get(road[1]).add(road[0]);
        }

        long[] res = {0};
        dfsFuelCost(0, -1, graph, res, seats);
        return res[0];
    }

    public long dfsFuelCost(int node, int parent, List<List<Integer>> graph, long[] res, int seats) {
        long people = 1;

        for (int child : graph.get(node)) {
            if (child != parent) {
                people += dfsFuelCost(child, node, graph, res, seats);
            }
        }

        if (node != 0) {
            res[0] += (people + seats - 1) / seats;
        }
        return people;
    }
}
