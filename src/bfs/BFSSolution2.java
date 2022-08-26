package bfs;

import java.util.*;

public class BFSSolution2 {
    public static void main(String[] args) {

    }

    // Leetcode problem: 854
    /*
     * K-Similar Strings
     * Go to every child after swapping a character
     * */
    public int kSimilarity(String s1, String s2) {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(s1);
        visited.add(s1);
        int maxSwap = 0;
        while (!queue.isEmpty()) {
            int queueSize = queue.size();

            while (queueSize-- > 0) {
                String temp = queue.poll();
                if (temp.equals(s2)) {
                    return maxSwap;
                }

                for (String str : getSwappedString(temp, s2)) {
                    if (!visited.contains(str)) {
                        queue.add(str);
                        visited.add(str);
                    }
                }
            }
            maxSwap++;
        }

        return maxSwap;
    }

    List<String> getSwappedString(String str, String target) {
        List<String> result = new ArrayList<>();

        int i = 0;
        // If character of target and str matches no need to swap
        while (str.charAt(i) == target.charAt(i)) i++;

        for (int j = i + 1; j < target.length(); j++) {
            if (str.charAt(j) == target.charAt(i)) {
                result.add(str.substring(0, i) + str.charAt(j) + str.substring(i + 1, j) + str.charAt(i) + str.substring(j + 1));
            }
        }
        return result;
    }
}
