package array;

import java.util.*;

public class ArraySolution6 {
    public static void main(String[] args) {
        ArraySolution6 arraySolution6 = new ArraySolution6();

//        int[] nums = {3, 5, 2, 1, 6, 4};
//        arraySolution6.wiggleSort(nums);


        System.out.println(-10 % 7);
    }

    // Leetcode problem: 1899
    public boolean mergeTriplets(int[][] triplets, int[] target) {
        boolean match1 = false;
        boolean match2 = false;
        boolean match3 = false;

        for (int[] triplet : triplets) {

            // If any off the value of triplet is greater than the target, merging will result greater value.
            // So ignore this.
            if (triplet[0] > target[0] || triplet[1] > target[1] || triplet[2] > target[2]) {
                continue;
            }

            // Check all 3 values are contained in other triplets.
            if (triplet[0] == target[0]) {
                match1 = true;
            }
            if (triplet[1] == target[1]) {
                match2 = true;
            }
            if (triplet[2] == target[2]) {
                match3 = true;
            }
        }

        return match1 && match2 && match3;
    }

    // Leetcode problem: 2017
    public long gridGame(int[][] grid) {
        int n = grid[0].length;

        long[] prefix1 = new long[n];
        long[] prefix2 = new long[n];

        prefix1[0] = grid[0][0];
        prefix2[0] = grid[1][0];
        for (int i = 1; i < n; i++) {
            prefix1[i] = grid[0][i] + prefix1[i - 1];
            prefix2[i] = grid[1][i] + prefix2[i - 1];
        }

        long res = Long.MAX_VALUE;
        // Calculate how many points robot2 get if robot1 moves down at ith index.
        // Then robot2 will get either top row point or bottom row point. Since robot2 plays optimally,
        // - get the max of these 2.
        // Since robot1 also plays optimally, get the minimum of all the max point robot2 can get.
        for (int i = 0; i < n; i++) {
            long top = prefix1[n - 1] - prefix1[i];
            long bottom = i == 0 ? 0 : prefix2[i - 1];

            long secondRobot = Math.max(top, bottom);
            res = Math.min(res, secondRobot);
        }

        return res;
    }

    // Leetcode problem: 280
    // Lintcode problem: 508
    public void wiggleSort(int[] nums) {

        int i = 0;
        while (i < nums.length - 1) {
            if (i % 2 == 0) {
                if (nums[i] > nums[i + 1]) {
                    int tmp = nums[i];
                    nums[i] = nums[i + 1];
                    nums[i + 1] = tmp;
                }
            } else {
                if (nums[i] < nums[i + 1]) {
                    int tmp = nums[i];
                    nums[i] = nums[i + 1];
                    nums[i + 1] = tmp;
                }
            }

            i += 1;
        }
    }

    // Leetcode problem: 525
    /*
     * This problem is similar to: Subarray Sum Equals K (Leetcode problem: 560)
     * */
    public int findMaxLength(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);

        int sum = 0;
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {
                sum += 1;
            } else {
                sum -= 1;
            }

            if (map.containsKey(sum)) {
                res = Math.max(res, i - map.get(sum));
            } else {
                map.put(sum, i);
            }
        }

        return res;
    }

    // Leetcode problem: 986
    /*
     * Interval List Intersections.
     * */
    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        int i = 0;
        int j = 0;

        List<int[]> resList = new ArrayList<>();
        while (i < firstList.length && j < secondList.length) {
            int start = Math.max(firstList[i][0], secondList[j][0]);
            int end = Math.min(firstList[i][1], secondList[j][1]);

            if (start <= end) {
                resList.add(new int[]{start, end});
            }

            if (firstList[i][1] < secondList[j][1]) {
                i += 1;
            } else {
                j += 1;
            }
        }

        int[][] result = new int[resList.size()][2];
        for (i = 0; i < resList.size(); i++) {
            result[i] = resList.get(i);
        }

        return result;
    }

    // Leetcode problem: 406
    /*
     * Queue Reconstruction by Height.
     * Code source: https://www.youtube.com/watch?v=khddrw6Bfyw&list=PLEJXowNB4kPxxaPCDVrZhSvW3NSD6ATaS&index=6
     * */
    public int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people, (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
        int n = people.length;
        int[][] result = new int[n][2];
        for (int i = 0; i < n; i++) {
            result[i] = new int[]{-1, -1};
        }

        for (int[] person : people) {
            int count = person[1];

            for (int j = 0; j < n; j++) {
                if (result[j][0] == -1 && count == 0) {
                    result[j] = person;
                    break;
                } else if (result[j][0] == -1 || result[j][0] >= person[0]) {
                    count -= 1;
                }
            }
        }

        return result;

    }

    // Leetcode problem: 1010
    /*
     * Pairs of Songs With Total Durations Divisible by 60.
     * */
    public int numPairsDivisibleBy60(int[] time) {
        Map<Integer, Long> countMap = new HashMap<>();

        for (int t : time) {
            long count = countMap.getOrDefault(t % 60, 0L);
            countMap.put(t % 60, count + 1);
        }

        long zeroCount = countMap.getOrDefault(0, 0L);
        long thirtyCount = countMap.getOrDefault(30, 0L);
        long res = (zeroCount * (zeroCount - 1)) / 2;
        res += (thirtyCount * (thirtyCount - 1)) / 2;

        countMap.remove(0);
        countMap.remove(30);

        for (Map.Entry<Integer, Long> entry : countMap.entrySet()) {
            if (entry.getKey() < 30 && countMap.containsKey(60 - entry.getKey())) {
                res += entry.getValue() * countMap.get(60 - entry.getKey());
            }
        }

        return (int) res;
    }

    public int[] prisonAfterNDays(int[] cells, int n) {
        List<List<Integer>> states = new ArrayList<>();

        int[] tmp = new int[8];
        while (n-- > 0) {
            tmp[0] = tmp[7] = 0;
            for (int i = 1; i <= 6; i++) {
                tmp[i] = cells[i - 1] == cells[i + 1] ? 1 : 0;
            }

            List<Integer> tmpList = Arrays.stream(tmp).boxed().toList();

            if (!states.isEmpty() && states.get(0).equals(tmpList)) {
                return states.get(n % states.size()).stream().mapToInt(i -> i).toArray();
            }
            states.add(tmpList);
            System.arraycopy(tmp, 0, cells, 0, tmp.length);
        }

        return tmp;

    }

    // Leetcode problem: 1007
    /*
     * Minimum Domino Rotations For Equal Row.
     * */
    public int minDominoRotations(int[] tops, int[] bottoms) {

        // Try to transform to the first top row or bottom row value.
        int[] targets = {tops[0], bottoms[0]};

        int n = tops.length;
        int res = n + 1;
        for (int target : targets) {
            int topRotate = 0;
            int bottomRotate = 0;
            int i = 0;
            for (; i < n; i++) {
                if (tops[i] != target && bottoms[i] != target) {
                    // Transformation is not possible.
                    break;
                }

                if (tops[i] != target) {
                    topRotate += 1;
                }
                if (bottoms[i] != target) {
                    bottomRotate += 1;
                }
            }

            // If transformation is possible.
            if (i == n) {
                res = Math.min(res, Math.min(topRotate, bottomRotate));
            }
        }

        return (res == n + 1) ? -1 : res;
    }

    // Leetcode problem: 163
    // Lintcode problem: 641
    /*
     * Missing Ranges.
     * */
    public List<String> findMissingRanges(int[] nums, int lower, int upper) {
        List<String> missingRanges = new ArrayList<>();

        int n = nums.length;
        if (nums.length == 0 || upper < nums[0] || lower > nums[n - 1]) {
            if (upper > lower) {
                missingRanges.add(lower + "->" + upper);
            } else {
                missingRanges.add(lower + "");
            }
            return missingRanges;
        }

        int i = 0;
        while (nums[i] < lower) {
            i += 1;
        }

        if (lower < nums[i]) {
            if (nums[0] - 1 > lower) {
                missingRanges.add(lower + "->" + (nums[i] - 1));
            } else {
                missingRanges.add(lower + "");
            }
        }
        while (i + 1 < nums.length && nums[i] < upper) {
            if (nums[i + 1] > nums[i] + 1) {
                if (nums[i + 1] - 1 > nums[i] + 1) {
                    missingRanges.add((nums[i] + 1) + "->" + (Math.min(upper, nums[i + 1]) - 1));
                } else {
                    missingRanges.add(nums[i] + 1 + "");
                }
            }
            i += 1;
        }

        if (nums[i] < upper) {
            if (nums[i] + 1 < upper) {
                missingRanges.add((nums[i] + 1) + "->" + upper);
            } else {
                missingRanges.add(upper + "");
            }
        }

        return missingRanges;
    }

    // Leetcode problem: 1968
    /*
     * Array With Elements Not Equal to Average of Neighbors.
     * */
    public int[] rearrangeArray(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        int l = 0;
        int r = n - 1;
        int idx = 0;
        int[] res = new int[n];

        while (idx < n) {
            res[idx++] = nums[l++];

            if (l <= r) {
                res[idx++] = nums[r--];
            }
        }

        return res;
    }

    // Leetcode problem: 454
    /*
     * 4Sum II.
     * */
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int n3 : nums3) {
            for (int n4 : nums4) {
                int count = map.getOrDefault(n3 + n4, 0);
                map.put(n3 + n4, count + 1);
            }
        }

        int count = 0;
        for (int n1 : nums1) {
            for (int n2 : nums2) {
                count += map.getOrDefault(-(n1 + n2), 0);
            }
        }

        return count;
    }

    // Leetcode problem: 350
    /*
     * Intersection of Two Arrays II.
     * */
    public int[] intersect(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int n : nums1) {
            int count = map.getOrDefault(n, 0);
            map.put(n, count + 1);
        }

        List<Integer> result = new ArrayList<>();
        for (int n : nums2) {
            if (map.containsKey(n)) {
                result.add(n);
                int count = map.get(n);
                if (count == 1) {
                    map.remove(n);
                } else {
                    map.replace(n, count - 1);
                }
            }
        }

        return result.stream().mapToInt(i -> i).toArray();
    }
}
