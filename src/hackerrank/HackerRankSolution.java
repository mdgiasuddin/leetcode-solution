package hackerrank;

import java.util.*;

public class HackerRankSolution {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(
                278, 576, 496, 727, 410, 124, 338, 149, 209, 702, 282, 718, 771, 575, 436
        );

        decentNumber(11);
    }

    /**
     * Hacker Rank: https://www.hackerrank.com/challenges/non-divisible-subset/problem?isFullScreen=true
     * Explanation: https://www.geeksforgeeks.org/subset-no-pair-sum-divisible-k/
     * */
    public static int nonDivisibleSubset(int k, List<Integer> s) {
        int[] count = new int[k];

        for (int num : s) {
            count[num % k] += 1;
        }

        if (k % 2 == 0) {
            count[k / 2] = Math.min(count[k / 2], 1);
        }

        int res = Math.min(count[0], 1);
        for (int i = 1; i <= k / 2; i++) {
            res += Math.max(count[i], count[k - i]);
        }

        return res;

    }

    /**
     * Hacker Rank: https://www.hackerrank.com/challenges/queens-attack-2/problem?isFullScreen=true
     * */
    public static int queensAttack(int n, int k, int r_q, int c_q, List<List<Integer>> obstacles) {

        Set<List<Integer>> obstaclesSet = new HashSet<>(obstacles);
        int[][] directions = {
                {1, 0}, {-1, 0}, {0, -1}, {0, 1}, {1, -1}, {1, 1}, {-1, -1}, {-1, 1}
        };

        int res = 0;
        for (int[] dir : directions) {
            int r = r_q + dir[0];
            int c = c_q + dir[1];

            while (r >= 1 && r <= n && c >= 1 && c <= n && !obstaclesSet.contains(Arrays.asList(r, c))) {
                res += 1;
                r += dir[0];
                c += dir[1];
            }
        }

        return res;

    }

    /**
     * Hacker Rank: https://www.hackerrank.com/challenges/flatland-space-stations/problem?isFullScreen=true
     * Multi-source BFS
     * */
    static int flatlandSpaceStations(int n, int[] c) {
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        for (int st : c) {
            visited[st] = true;
            queue.add(st);
        }

        int distance = -1;
        while (!queue.isEmpty()) {
            int qSize = queue.size();

            while (qSize-- > 0) {
                int node = queue.poll();
                if (node - 1 >= 0 && !visited[node - 1]) {
                    visited[node - 1] = true;
                    queue.add(node - 1);
                }

                if (node + 1 < n && !visited[node + 1]) {
                    visited[node + 1] = true;
                    queue.add(node + 1);
                }
            }

            distance += 1;
        }

        return distance;
    }

    /**
     * Hacker Rank: https://www.hackerrank.com/challenges/3d-surface-area/problem?isFullScreen=true
     * Explanation: https://www.youtube.com/watch?v=tIG0GF2EqjE
     * */
    public static int surfaceArea(List<List<Integer>> A) {
        int rows = A.size();
        int cols = A.get(0).size();

        int area = 2 * rows * cols;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int val = A.get(r).get(c);

                int prevRowVal;
                if (r == 0) {
                    prevRowVal = 0;
                } else {
                    prevRowVal = A.get(r - 1).get(c);
                }

                area += Math.abs(val - prevRowVal);

                int prevColVal;
                if (c == 0) {
                    prevColVal = 0;
                } else {
                    prevColVal = A.get(r).get(c - 1);
                }

                area += Math.abs(val - prevColVal);

                if (c == cols - 1) {
                    area += A.get(r).get(c);
                }

                if (r == rows - 1) {
                    area += A.get(r).get(c);
                }
            }
        }

        return area;
    }


    /**
     * Hacker Rank: https://www.hackerrank.com/challenges/absolute-permutation/problem?isFullScreen=true
     * Explanation: https://www.youtube.com/watch?v=Z0bpJiVz0no
     * */
    public static List<Integer> absolutePermutation(int n, int k) {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            list.add(i);
        }

        if (k == 0) {
            return list;
        }

        Set<Integer> set = new HashSet<>(list);
        List<Integer> res = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            int low = i - k;
            int high = i + k;

            if (set.contains(low)) {
                res.add(low);
                set.remove(low);
            } else if (set.contains(high)) {
                res.add(high);
                set.remove(high);
            } else {
                return Collections.singletonList(-1);
            }
        }

        return res;
    }

    public static void almostSorted(List<Integer> arr) {
        List<Integer> sortedArray = new ArrayList<>(arr);
        sortedArray.sort(Comparator.comparingInt(a -> a));

        if (arr.equals(sortedArray)) {
            System.out.println("yes");
            return;
        }

        int n = arr.size();
        int left = -1;
        int right = -1;
        for (int i = 0; i < n - 1; i++) {
            if (arr.get(i) > arr.get(i + 1)) {
                left = i;
                break;
            }
        }

        for (int i = n - 1; i > 0; i--) {
            if (arr.get(i) < arr.get(i - 1)) {
                right = i;
                break;
            }
        }

        List<Integer> swapArray = new ArrayList<>(arr);
        int tmp = swapArray.get(left);
        swapArray.set(left, swapArray.get(right));
        swapArray.set(right, tmp);

        if (swapArray.equals(sortedArray)) {
            System.out.println("yes");
            System.out.println("swap " + (left + 1) + " " + (right + 1));
            return;
        }

        List<Integer> reverseArray = new ArrayList<>(arr);
        int l = left;
        int r = right;
        while (l < r) {
            tmp = reverseArray.get(l);
            reverseArray.set(l, reverseArray.get(r));
            reverseArray.set(r, tmp);

            l += 1;
            r -= 1;
        }

        if (reverseArray.equals(sortedArray)) {
            System.out.println("yes");
            System.out.println("reverse " + (left + 1) + " " + (right + 1));
            return;
        }

        System.out.println("no");

    }

    /**
     * Hacker Rank: https://www.hackerrank.com/challenges/two-characters/problem?isFullScreen=true
     * */
    public static int alternate(String s) {
        Map<Character, List<Integer>> charIndex = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            List<Integer> indices = charIndex.getOrDefault(ch, new ArrayList<>());
            indices.add(i);
            charIndex.put(ch, indices);
        }

        List<Character> uniqueList = new ArrayList<>(charIndex.keySet());

        int res = 0;
        for (int i = 0; i < uniqueList.size() - 1; i++) {
            for (int j = i + 1; j < uniqueList.size(); j++) {
                if (isAlternating(uniqueList.get(i), uniqueList.get(j), charIndex)) {
                    res = Math.max(res, charIndex.get(uniqueList.get(i)).size() + charIndex.get(uniqueList.get(j)).size());
                }
            }
        }

        return res;
    }

    private static boolean isAlternating(char first, char second, Map<Character, List<Integer>> charIndex) {
        List<Integer> firstIndices = charIndex.get(first);
        List<Integer> secondIndices = charIndex.get(second);

        if (Math.abs(firstIndices.size() - secondIndices.size()) > 1) {
            return false;
        }

        boolean ordered = firstIndices.get(0) < secondIndices.get(0);
        if ((ordered && firstIndices.size() < secondIndices.size()) ||
                (!ordered && secondIndices.size() < firstIndices.size())) {
            return false;
        }
        int i = 0;
        int j = 0;
        while (i < firstIndices.size() && j < secondIndices.size()) {
            if (ordered) {
                if (firstIndices.get(i) > secondIndices.get(j) || (i + 1 < firstIndices.size()
                        && firstIndices.get(i + 1) < secondIndices.get(j))) {
                    return false;
                }
            } else {
                if (firstIndices.get(i) < secondIndices.get(j) || (j + 1 < secondIndices.size()
                        && firstIndices.get(i) > secondIndices.get(j + 1))) {
                    return false;
                }
            }

            i += 1;
            j += 1;
        }

        return true;
    }

    /**
     * Hacker Rank: https://www.hackerrank.com/challenges/weighted-uniform-string/problem?isFullScreen=true
     * */
    public static List<String> weightedUniformStrings(String s, List<Integer> queries) {

        int current = s.charAt(0) - 'a' + 1;
        Set<Integer> weights = new HashSet<>();
        weights.add(current);

        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                current += s.charAt(i) - 'a' + 1;
            } else {
                current = s.charAt(i) - 'a' + 1;
            }

            weights.add(current);
        }


        List<String> res = new ArrayList<>();
        for (int query : queries) {
            if (weights.contains(query)) {
                res.add("Yes");
            } else {
                res.add("No");
            }
        }

        return res;

    }

    /**
     * Hacker Rank: https://www.hackerrank.com/challenges/beautiful-pairs/problem?isFullScreen=true
     * Explanation: https://www.youtube.com/watch?v=yKNhg5vWqKY
     * */
    public static int beautifulPairs(List<Integer> A, List<Integer> B) {
        int res = 0;
        Map<Integer, Integer> countA = new HashMap<>();
        Map<Integer, Integer> countB = new HashMap<>();

        for (int a : A) {
            int c = countA.getOrDefault(a, 0);
            countA.put(a, c + 1);
        }

        for (int b : B) {
            int c = countB.getOrDefault(b, 0);
            countB.put(b, c + 1);
        }

        for (int a : countA.keySet()) {
            if (countB.containsKey(a)) {
                res += Math.min(countA.get(a), countB.get(a));
            }
        }

        if (res == A.size()) {
            return res - 1;
        }

        return res + 1;

    }

    /**
     * Hacker Rank: https://www.hackerrank.com/challenges/the-power-sum/problem?isFullScreen=true
     * Explanation: https://www.youtube.com/watch?v=AIw5ljcTuyg
     * */
    public static int powerSum(int X, int N) {
        return powerSum(X, 1, N);
    }

    private static int powerSum(int total, int num, int power) {
        int val = total - (int) Math.pow(num, power);
        if (val == 0) {
            return 1;
        }
        if (val < 0) {
            return 0;
        }

        return powerSum(val, num + 1, power) + powerSum(total, num + 1, power);
    }

    public static void decentNumber(int n) {
        int count5 = -1;

        for (int i = 0; i * 3 <= n; i++) {
            if ((n - i * 3) % 5 == 0) {
                count5 = i * 3;
            }
        }

        if (count5 == -1) {
            System.out.println(-1);
            return;
        }

        StringBuilder res = new StringBuilder();
        res.append("5".repeat(count5)).append("3".repeat(n - count5));
        System.out.println(res);

    }

    /**
     * Hacker Rank: https://www.hackerrank.com/challenges/down-to-zero-ii/problem?isFullScreen=true
     * BFS Solution.
     * Explanation: https://www.youtube.com/watch?v=uuyThZv2Vis
     * */
    public static int downToZero(int n) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{n, 0});
        Set<Integer> visited = new HashSet<>();
        visited.add(n);

        int res = n;
        while (!queue.isEmpty()) {
            int[] numCount = queue.poll();
            if (numCount[0] == 1) {
                res = numCount[1] + 1;
                break;
            }

            if (!visited.contains(numCount[0] - 1)) {
                queue.add(new int[]{numCount[0] - 1, numCount[1] + 1});
                visited.add(numCount[0] - 1);
            }

            for (int i = 2; i * i <= numCount[0]; i++) {
                if (numCount[0] % i == 0) {
                    int factor = Math.max(i, numCount[0] / i);
                    if (!visited.contains(factor)) {
                        queue.add(new int[]{factor, numCount[1] + 1});
                        visited.add(factor);
                    }
                }
            }
        }

        return res;
    }


}
