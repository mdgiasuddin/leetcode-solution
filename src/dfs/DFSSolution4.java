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

    // Leetcode problem: 2492
    /*
     * Minimum Score of a Path Between Two Cities.
     * Since 1 & n lies in the same connected component, visit all the path from 1 and find the minimum path.
     * */
    public int minScore(int n, int[][] roads) {
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            graph.put(i, new ArrayList<>());
        }

        for (int[] road : roads) {
            graph.get(road[0]).add(new int[]{road[1], road[2]});
            graph.get(road[1]).add(new int[]{road[0], road[2]});
        }

        int[] res = {Integer.MAX_VALUE};
        Set<Integer> visited = new HashSet<>();
        visited.add(1);
        dfsAllRoad(1, graph, visited, res);
        return res[0];
    }

    private void dfsAllRoad(int node, Map<Integer, List<int[]>> graph, Set<Integer> visited, int[] res) {
        for (int[] road : graph.get(node)) {
            res[0] = Math.min(res[0], road[1]);
            if (!visited.contains(road[0])) {
                visited.add(road[0]);
                dfsAllRoad(road[0], graph, visited, res);
            }
        }
    }

    // Leetcode problem: 2246
    /*
     * Longest Path With Different Adjacent Characters.
     * This problem is similar to:  Diameter of Binary Tree (Leetcode problem: 543),
     * Binary Tree Maximum Path Sum (Leetcode problem: 124).
     * */
    public int longestPath(int[] parent, String s) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < parent.length; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 1; i < parent.length; i++) {
            graph.add(new ArrayList<>());
            graph.get(parent[i]).add(i);
        }

        int[] res = {1};
        dfsPath(0, graph, s, res);
        return res[0];
    }

    private PathCounter dfsPath(int node, List<List<Integer>> graph, String s, int[] res) {
        PathCounter counter = new PathCounter(s.charAt(node), 1);
        for (int child : graph.get(node)) {
            PathCounter childCounter = dfsPath(child, graph, s, res);
            if (childCounter.ch != counter.ch) {
                res[0] = Math.max(res[0], counter.depth + childCounter.depth);
                counter.depth = Math.max(counter.depth, 1 + childCounter.depth);
            }
        }

        return counter;
    }

    // Leetcode problem: 2503
    /*
     * Maximum Number of Points From Grid Queries.
     * Explanation: https://www.youtube.com/watch?v=zZWSZM7fboI&list=PLy38cn8b_xMenERa6pKdmc2hfQHKBUfZP&index=8
     * */
    public int[] maxPoints(int[][] grid, int[] queries) {

        int[][] sorted = new int[queries.length][2];
        for (int i = 0; i < queries.length; i++) {
            sorted[i][0] = queries[i];
            sorted[i][1] = i;
        }
        Arrays.sort(sorted, Comparator.comparingInt(a -> a[0]));
        int[] result = new int[queries.length];

        int m = grid.length;
        int n = grid[0].length;
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));
        boolean[][] visited = new boolean[m][n];
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        queue.add(new int[]{0, 0, grid[0][0]});

        visited[0][0] = true;
        List<Integer> list = new ArrayList<>();

        while (!queue.isEmpty()) {
            int[] rc = queue.poll();
            list.add(rc[2]);
            for (int[] direction : directions) {
                int r = rc[0] + direction[0];
                int c = rc[1] + direction[1];

                if (r >= 0 && r < m && c >= 0 && c < n && !visited[r][c]) {
                    visited[r][c] = true;
                    queue.add(new int[]{r, c, Math.max(rc[2], grid[r][c])});
                }
            }
        }

        int i = 0;
        int j = 0;
        while (i < queries.length) {
            while (j < list.size() && list.get(j) < sorted[i][0]) {
                j += 1;
            }

            result[sorted[i][1]] = j;
            i += 1;
        }

        return result;
    }

    // Leetcode problem: 1462
    /*
     * Course Schedule IV.
     * Explanation: https://www.youtube.com/watch?v=cEW05ofxhn0&t=29s
     * */
    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] prereq : prerequisites) {
            graph.get(prereq[1]).add(prereq[0]);
        }

        Map<Integer, Set<Integer>> reqMap = new HashMap<>();
        for (int i = 0; i < numCourses; i++) {
            findAllPreRequisites(i, graph, reqMap);
        }

        List<Boolean> result = new ArrayList<>();
        for (int[] query : queries) {
            result.add(reqMap.get(query[1]).contains(query[0]));
        }

        return result;
    }

    public Set<Integer> findAllPreRequisites(int node, List<List<Integer>> graph, Map<Integer, Set<Integer>> reqMap) {
        if (reqMap.containsKey(node)) {
            return reqMap.get(node);
        }

        Set<Integer> req = new HashSet<>();
        for (int child : graph.get(node)) {
            req.add(child);
            req.addAll(findAllPreRequisites(child, graph, reqMap));
        }

        reqMap.put(node, req);
        return req;
    }

    // Leetcode problem: 1857
    /*
     * Largest Color Value in a Directed Graph.
     * Explanation: https://www.youtube.com/watch?v=xLoDjKczUSk
     * */
    public int largestPathValue(String colors, int[][] edges) {
        int n = colors.length();
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
        }

        Set<Integer> visited = new HashSet<>();
        Set<Integer> path = new HashSet<>();
        int[][] count = new int[n][26];

        int res = 0;
        for (int node = 0; node < n; node++) {
            res = Math.max(res, dfsPathValue(node, graph, colors, visited, path, count));
        }

        return res == Integer.MAX_VALUE ? -1 : res;

    }

    private int dfsPathValue(int node, List<List<Integer>> graph, String colors, Set<Integer> visited, Set<Integer> path, int[][] count) {

        // If a loop is created.
        if (path.contains(node)) {
            return Integer.MAX_VALUE;
        }

        // If already visited, skip this. No effect of return value.
        if (visited.contains(node)) {
            return 0;
        }

        visited.add(node);
        path.add(node);
        int colorIdx = colors.charAt(node) - 'a';
        count[node][colorIdx] = 1;

        for (int nei : graph.get(node)) {
            if (dfsPathValue(nei, graph, colors, visited, path, count) == Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }

            for (int c = 0; c < 26; c++) {
                count[node][c] = Math.max(
                        count[node][c],
                        (c == colorIdx ? 1 : 0) + count[nei][c]
                );
            }
        }

        int res = 0;
        for (int c = 0; c < 26; c++) {
            res = Math.max(res, count[node][c]);
        }

        path.remove(node);
        return res;
    }

    // Leetcode problem: 785
    /*
     * Is Graph Bipartite?
     * */
    public boolean isBipartite(int[][] graph) {
        int n = graph.length;
        int[] colors = new int[n];
        Arrays.fill(colors, -1);

        for (int i = 0; i < n; i++) {
            if (colors[i] == -1 && !dfsGraph(i, 0, colors, graph)) {
                return false;
            }
        }

        return true;
    }

    private boolean dfsGraph(int node, int c, int[] colors, int[][] graph) {

        if (colors[node] == c) {
            return true;
        }

        if (colors[node] != -1 && colors[node] != c) {
            return false;
        }

        colors[node] = c;
        for (int nei : graph[node]) {
            if (!dfsGraph(nei, (c + 1) % 2, colors, graph)) {
                return false;
            }
        }

        return true;
    }

}

class PathCounter {
    char ch;
    int depth;

    public PathCounter(char ch, int depth) {
        this.ch = ch;
        this.depth = depth;
    }
}

