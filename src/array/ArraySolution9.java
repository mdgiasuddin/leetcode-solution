package array;

import pair.Pair;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ArraySolution9 {

    // Leetcode problem: 659

    /**
     * Split Array Into Consecutive Sequence.
     * Explanation: https://www.youtube.com/watch?v=Ty8EZlxVNC8
     * Create group of list. For a number always try to add this in an existing group.
     * If it is not possible then create a new group.
     * */
    public boolean isPossible(int[] nums) {
        Map<Integer, Integer> available = new HashMap<>();
        Map<Integer, Integer> vacancy = new HashMap<>();

        for (int num : nums) {
            available.put(num, available.getOrDefault(num, 0) + 1);
        }

        for (int num : nums) {
            if (available.get(num) == 0) {
                continue;
            }

            available.put(num, available.get(num) - 1);
            if (vacancy.getOrDefault(num, 0) > 0) { // Can be fit in existing group.
                vacancy.put(num, vacancy.get(num) - 1);

                // Create a new vacancy for the next element.
                vacancy.put(num + 1, vacancy.getOrDefault(num + 1, 0) + 1);
            } else if (available.getOrDefault(num + 1, 0) > 0 &&
                    available.getOrDefault(num + 2, 0) > 0) { // Create a new group.
                available.put(num + 1, available.get(num + 1) - 1);
                available.put(num + 2, available.get(num + 2) - 1);

                // Create a new vacancy for the next element.
                vacancy.put(num + 3, vacancy.getOrDefault(num + 3, 0) + 1);
            } else {
                return false;
            }
        }

        return true;
    }

    // Leetcode problem: 1395

    /**
     * Count Number of Teams.
     * For each player, consider it as middle and count lower & higher rating in the left & right.
     * Sum up the combinations.
     * */
    public int numTeams(int[] rating) {
        int teams = 0;
        int n = rating.length;
        for (int i = 1; i < n - 1; i++) {
            int j = i - 1;
            int k = i + 1;

            int lLower = 0;
            int rHigher = 0;
            int lHigher = 0;
            int rLower = 0;

            while (j >= 0) {
                if (rating[j] < rating[i]) {
                    lLower += 1;
                } else if (rating[j] > rating[i]) {
                    lHigher += 1;
                }
                j -= 1;
            }
            while (k < n) {
                if (rating[k] < rating[i]) {
                    rLower += 1;
                } else if (rating[k] > rating[i]) {
                    rHigher += 1;
                }
                k += 1;
            }

            teams += (lLower * rHigher) + (lHigher * rLower);
        }

        return teams;
    }

    // Leetcode problem: 1027

    /**
     * Longest Arithmetic Subsequence.
     * Explanation: https://www.youtube.com/watch?v=sQznNULe2J0
     * */
    public int longestArithSeqLength(int[] nums) {
        Map<Pair, Integer> map = new HashMap<>();
        int n = nums.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                int diff = nums[j] - nums[i];
                map.put(
                        new Pair(j, diff),
                        // Take the count up to ith index with difference = diff & add jth value with it.
                        map.getOrDefault(new Pair(i, diff), 1) + 1
                );
            }
        }

        int res = 0;
        for (Map.Entry<Pair, Integer> entry : map.entrySet()) {
            res = Math.max(res, entry.getValue());
        }
        return res;
    }

    // Leetcode problem: 950

    /**
     * Reveal Cards in Increasing Order.
     * Explanation: https://www.youtube.com/watch?v=COiE-PQqf28
     * */
    public int[] deckRevealedIncreasing(int[] deck) {
        int n = deck.length;
        Arrays.sort(deck);
        Deque<Integer> deque = new ArrayDeque<>(n);
        for (int i = 0; i < n; i++) {
            deque.add(i);
        }

        int idx = 0;
        int[] ans = new int[n];
        boolean reveal = true;

        while (!deque.isEmpty()) {
            if (reveal) {
                ans[deque.pollFirst()] = deck[idx];
                idx += 1;
            } else {
                deque.addLast(deque.pollFirst());
            }
            reveal = !reveal;
        }

        return ans;
    }

    // Leetcode problem: 2009

    /**
     * Minimum Number of Operations to Make Array Continuous.
     * Explanation: https://www.youtube.com/watch?v=Dd-yJylrcOY
     * [1 2 3 4 5 5 5 15]
     * Sort the array after removing duplicates.
     * Starting from every smallest value, find out how many numbers lies inside the range => nums[l] + length - 1.
     * The other number must be replaced with the missing number.
     * Find the smallest number of replacement.
     * */
    public int minOperations(int[] nums) {
        int len = nums.length;
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }
        List<Integer> numList = new ArrayList<>(numSet);
        numList.sort(Comparator.comparingInt(a -> a));

        int res = len;
        int r = 0;
        for (int l = 0; l < numList.size(); l++) {
            while (r < numList.size() && numList.get(r) < numList.get(l) + len) {
                r += 1;
            }
            // r is not included because it already goes out of bound.
            int window = r - l;
            res = Math.min(res, len - window);
        }

        return res;
    }

    // Leetcode problem: 2610

    /**
     * Convert an Array int a 2D Array with Conditions.
     * Count all the elements.
     * The maximum count will be the size of the result array.
     * Fill each num from the first row up to the count-th row.
     * */
    public List<List<Integer>> findMatrix(int[] nums) {
        Map<Integer, Integer> countMap = new HashMap<>();
        int maxCount = 0;
        for (int num : nums) {
            int count = countMap.getOrDefault(num, 0) + 1;
            maxCount = Math.max(maxCount, count);
            countMap.put(num, count);
        }

        List<List<Integer>> result = new ArrayList<>(maxCount);
        for (int i = 0; i < maxCount; i++) {
            result.add(new ArrayList<>());
        }

        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                result.get(i).add(entry.getKey());
            }
        }

        return result;
    }

    // Leetcode problem: 1578

    /**
     * Minimum Time to Make Rope Colorful.
     * Need to remove the duplicate from the consecutive same color balloon keeping only 1.
     * Take the sum and keep the balloon with maximum time.
     * */
    public int minCost(String colors, int[] neededTime) {
        int prevIdx = 0;
        int sum = neededTime[0];
        int maxTime = sum;
        int result = 0;

        for (int i = 1; i < colors.length(); i++) {

            if (colors.charAt(i) != colors.charAt(prevIdx)) {
                result += sum - maxTime;
                maxTime = sum = neededTime[i];
                prevIdx = i;
            } else {
                sum += neededTime[i];
                maxTime = Math.max(maxTime, neededTime[i]);
            }
        }

        result += sum - maxTime;
        return result;
    }

    // Leetcode problem: 1481

    /**
     * Least Number of Unique Integers after K Removals.
     * */
    public int findLeastNumOfUniqueInts(int[] arr, int k) {
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int num : arr) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }

        int result = countMap.size();

        // Create a bucket reversely from count map => {count, number of distinct element with the count}
        Map<Integer, Integer> bucket = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            bucket.put(entry.getValue(), bucket.getOrDefault(entry.getValue(), 0) + 1);
        }

        for (int count = 1; count <= arr.length && k / count > 0; count++) {
            if (bucket.containsKey(count)) {
                // Count how many distinct numbers can fully be removed.
                int uniqueRemove = Math.min(bucket.get(count), k / count);
                k -= uniqueRemove * count;
                result -= uniqueRemove;
            }
        }

        return result;
    }

    // Leetcode problem: 1014

    /**
     * Best Sightseeing Pair.
     * Explanation: https://www.youtube.com/watch?v=YAYnMfHbjz4
     * */
    public int maxScoreSightseeingPair(int[] values) {
        int currentMax = values[0] - 1;
        int maxScore = 0;
        for (int i = 1; i < values.length; i++) {
            maxScore = Math.max(maxScore, currentMax + values[i]);
            currentMax = Math.max(currentMax - 1, values[i] - 1);
        }

        return maxScore;
    }

    // Leetcode problem: 2948

    /**
     * Make Lexicographically Smallest Array by Swapping Elements.
     * Explanation: https://www.youtube.com/watch?v=-FGl6dzPexY
     * Sort the array.
     * Group the adjacent element of which the consecutive difference is <= limit.
     * For each index of the array, insert the first element of the specific group.
     * */
    public int[] lexicographicallySmallestArray(int[] nums, int limit) {
        int n = nums.length;
        int[][] sorted = new int[n][2];

        for (int i = 0; i < n; i++) {
            sorted[i][0] = nums[i];
            sorted[i][1] = i;
        }

        Arrays.sort(sorted, Comparator.comparingInt(a -> a[0]));

        Map<Integer, Integer> indices = new HashMap<>();
        List<Deque<Integer>> list = new ArrayList<>();
        Deque<Integer> firstQue = new ArrayDeque<>();
        firstQue.add(sorted[0][0]);
        list.add(firstQue);
        indices.put(sorted[0][1], 0);

        for (int i = 1; i < n; i++) {
            int listIndex = list.size() - 1;
            Deque<Integer> deque = list.get(listIndex);
            if (sorted[i][0] - deque.peekLast() <= limit) {
                deque.add(sorted[i][0]);
                indices.put(sorted[i][1], listIndex);
            } else {
                deque = new ArrayDeque<>();
                deque.add(sorted[i][0]);
                list.add(deque);
                indices.put(sorted[i][1], listIndex + 1);
            }
        }

        int[] resArray = new int[n];
        for (int i = 0; i < n; i++) {
            resArray[i] = list.get(indices.get(i)).pollFirst();
        }

        return resArray;
    }

    // Leetcode problem: 1769

    /**
     * Minimum Number of Operations to Move All Balls to Each Box.
     * Maintain 2 variable leftBall & rightBall.
     * First calculate the move needed to place all the balls in the first index.
     * Moving left to right move will be increased for the leftBall & decreased for the rightBall.
     * */
    public int[] minOperations(String boxes) {
        int n = boxes.length();
        int move = 0;
        int rightBall = 0;

        for (int i = 1; i < n; i++) {
            if (boxes.charAt(i) == '1') {
                rightBall += 1;
                move += i;
            }
        }

        int[] operation = new int[n];
        operation[0] = move;
        int leftBall = boxes.charAt(0) - '0';

        for (int i = 1; i < n; i++) {
            move -= rightBall;
            move += leftBall;
            operation[i] = move;
            leftBall += boxes.charAt(i) - '0';
            rightBall -= boxes.charAt(i) - '0';
        }

        return operation;
    }

    // Leetcode problem: 769

    /**
     * Max Chunks to Make Sorted.
     * Explanation: https://www.youtube.com/watch?v=wpHzXTkuVkY
     * */
    public int maxChunksToSorted(int[] arr) {
        int res = 0;
        int currentMax = -1;

        for (int i = 0; i < arr.length; i++) {
            currentMax = Math.max(currentMax, arr[i]);

            // If all the numbers up to i are found, this can form a new chunk.
            if (currentMax == i) {
                res += 1;
            }
        }

        return res;
    }

}
