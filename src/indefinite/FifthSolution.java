package indefinite;

import pair.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FifthSolution {

    public int minJumps(int[] arr) {
        if (arr.length == 1)
            return 0;

        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (j == i + 1 || arr[j] == arr[i]) {
                    graph.get(i).add(j);
                    graph.get(j).add(i);
                }
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[arr.length];
        int[] distance = new int[arr.length];
        queue.add(0);
        visited[0] = true;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v : graph.get(u)) {
                if (!visited[v]) {
                    queue.add(v);
                    distance[v] = 1 + distance[u];
                    visited[v] = true;
                    if (v == arr.length - 1)
                        return distance[v];
                }
            }
        }

        return distance[distance.length - 1];
    }

    public boolean PredictTheWinner(int[] nums) {
        Pair[][] dp = new Pair[nums.length][nums.length];
        for (int i = 0; i < nums.length; i++) {
            dp[i][i] = new Pair(nums[i], 0);
        }
        for (int i = 0; i < nums.length - 1; i++) {
            dp[i][i + 1] = new Pair(Math.max(nums[i], nums[i + 1]), Math.min(nums[i], nums[i + 1]));
        }

        int i = 0, j = 2, initJ = j;
        while (!(i == 0 && j == nums.length - 1)) {
            int first, second;
            if (nums[i] + dp[i + 1][j].second > nums[j] + dp[i][j - 1].second) {
                first = nums[i] + dp[i + 1][j].second;
                second = dp[i + 1][j].first;
            } else {
                first = nums[j] + dp[i][j - 1].second;
                second = dp[i][j - 1].first;
            }
            dp[i][j] = new Pair(first, second);
            i++;
            j++;
            if (j == nums.length) {
                i = 0;
                j = ++initJ;
            }
        }

        return dp[0][nums.length - 1].first >= dp[0][nums.length - 1].second;
    }

}
