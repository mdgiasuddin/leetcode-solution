package array;

import java.util.*;

public class ArraySolution5 {

    public static void main(String[] args) {

    }

    // Leetcode problem: 1288
    public int removeCoveredIntervals(int[][] intervals) {
        // Sort based on start ascending. If ties then by end descending.
        Arrays.sort(intervals, (a, b) -> (a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]));

        int[] prev = intervals[0];
        int result = 1;
        for (int i = 1; i < intervals.length; i++) {
            // Test if current interval can be covered by prev interval.
            if (intervals[i][1] > prev[1]) {
                prev = intervals[i];
                result++;
            }
        }

        return result;
    }

    // Leetcode problem: 179
    public String largestNumber(int[] nums) {
        List<String> strings = new java.util.ArrayList<>(Arrays.stream(nums).mapToObj(String::valueOf).toList());

        strings.sort((a, b) -> (b + a).compareTo(a + b));

        StringBuilder result = new StringBuilder();
        for (String str : strings) {
            result.append(str);
        }

        if (result.toString().startsWith("0"))
            return "0";
        return result.toString();
    }

    // Leetcode problem: 88
    /*
     * Merge from the last position.
     * */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int last = m + n - 1;
        while (m > 0 && n > 0) {
            if (nums2[n - 1] >= nums1[m - 1]) {
                nums1[last] = nums2[n - 1];
                n--;
            } else {
                nums1[last] = nums1[m - 1];
                m--;
            }
            last--;
        }

        // If second array haven't been merged yet, merge them.
        while (n > 0) {
            nums1[last--] = nums2[--n];
        }
    }

    // Leetocode problem: 1096
    /*
     * Build up a max priority queue to get maximum number every time.
     * */
    public int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> b - a);

        for (int stone : stones) {
            queue.add(stone);
        }

        while (queue.size() > 1) {
            int first = queue.poll();
            int second = queue.poll();

            if (first > second) {
                queue.add(first - second);
            }
        }

        return queue.isEmpty() ? 0 : queue.poll();
    }

    // Leetcode problem: 605
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        if (n == 0)
            return true;

        for (int i = 0; i < flowerbed.length; i++) {

            // Check if a flower can be placed.
            if (flowerbed[i] == 0 && (i == 0 || flowerbed[i - 1] == 0)
                    && (i == flowerbed.length - 1 || flowerbed[i + 1] == 0)) {
                flowerbed[i] = 1;
                n--;
            }
        }

        return n <= 0;
    }

    // Leetcode problem: 448
    /*
     * For every number make its index element negative.
     * Then check in which index numbers are still positive.
     * */
    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> result = new ArrayList<>();

        for (int num : nums) {
            int index = Math.abs(num) - 1;

            if (nums[index] > 0)
                nums[index] = -nums[index];
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                result.add(i + 1);
            }
        }

        return result;
    }

    // Leetcode problem: 1189
    public int maxNumberOfBalloons(String text) {
        // Index and count of chars in balloon 'a', 'b', 'l', 'n', 'o'
        int[] indices = {0, 1, 11, 13, 14};
        int[] balloon = {1, 1, 2, 1, 2};
        int[] count = new int[26];

        for (int i = 0; i < text.length(); i++) {
            count[text.charAt(i) - 'a']++;

        }

        // Result is the minimum multiplication of all characters in balloon.
        int res = text.length();
        for (int i = 0; i < 5; i++) {
            res = Math.min(res, count[indices[i]] / balloon[i]);
        }

        return res;
    }

    // Leetcode problem: 2001
    /*
     * Build up count matrix for the ratio of width and height.
     * */
    public long interchangeableRectangles(int[][] rectangles) {
        Map<Double, Long> rectCount = new HashMap<>();

        for (int[] rectangle : rectangles) {
            double ratio = (rectangle[0] * 1.0) / rectangle[1];
            long c = rectCount.getOrDefault(ratio, 0L);

            rectCount.put(ratio, c + 1);
        }

        long res = 0;
        for (Map.Entry<Double, Long> entry : rectCount.entrySet()) {
            if (entry.getValue() > 1) {
                res += (entry.getValue() * (entry.getValue() - 1)) / 2;
            }
        }

        return res;
    }

    // Leetcode problem: 1985
    /*
     * Sort the number and find the minimum of all k-elements slides.
     * */
    public int minimumDifference(int[] nums, int k) {
        Arrays.sort(nums);

        int res = Integer.MAX_VALUE;
        for (int i = 0; i <= nums.length - k; i++) {
            res = Math.min(res, nums[i + k - 1] - nums[i]);
        }

        return res;
    }

    // Leetcode problem: 135
    public int candy(int[] ratings) {
        /*int[] result = new int[ratings.length];
        Arrays.fill(result, 1);

        // First traverse left to right.
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1])
                result[i] = result[i - 1] + 1;
        }

        // Then traverse right to left.
        for (int i = ratings.length - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1] && result[i] <= result[i + 1])
                result[i] = result[i + 1] + 1;
        }

        int sum = 0;

        for (int r : result)
            sum += r;

        return sum;*/

        // This is memory optimized version.

        int ans = 1; // For first index;

        int up, down, peak;
        up = down = peak = 0;
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                up++;
                peak = up;
                down = 0;

                ans += up + 1;
            } else if (ratings[i] == ratings[i - 1]) {
                up = down = peak = 0;
                ans += 1;
            } else {
                down++;
                up = 0;

                ans += down;

                // Increase the peak by 1. 1,2,3  3,2,1. To keep more candy in the previous.
                ans += (down > peak ? 1 : 0);
            }
        }

        return ans;
    }

    // Leetcode problem: 334
    public boolean increasingTriplet(int[] nums) {
        /*int n = nums.length;
        if (n < 3)
            return false;

        int [] leftMin = new int[n];
        int [] rightMax = new int[n];

        leftMin[0] = nums[0];
        rightMax[n - 1] = nums[n - 1];

        // Store the minimum values in left side.
        for (int i = 1; i < n; i++) {
            leftMin[i] = Math.min(nums[i], leftMin[i - 1]);
        }

        // Store the maximum values in right side.
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(nums[i], rightMax[i + 1]);
        }

        for (int i = 0; i < n; i++) {
            if (leftMin[i] < nums[i] && nums[i] < rightMax[i])
                return true;
        }

        return false;*/

        // This is constant memory version.

        int first = Integer.MAX_VALUE;
        int second = Integer.MAX_VALUE;

        for (int num : nums) {
            if (num <= first) {
                first = num;
            } else if (num <= second) {
                second = num;
            } else {
                return true;
            }
        }

        return false;
    }

    // Leetcode problem: 885
    /*
     * Go 1-step right, 1-step down, 2-step left, 2-step up, 3-step right, 3-step down & so on.
     * After every 2 iteration step size will be increased.
     * After each iteration, the index may be out of bounds. We will only add valid index.
     * After completing iteration in one direction, we will change the direction.
     * */
    public int[][] spiralMatrixIII(int rows, int cols, int rStart, int cStart) {
        int[][] result = new int[rows * cols][2];
        int index = 0, iteration = 0;

        int r = rStart, c = cStart;
        int dr = 0, dc = 1, currentStep = 1;
        result[index++] = new int[]{r, c};

        while (index < rows * cols) {
            iteration += 1;
            for (int i = 0; i < currentStep; i++) {
                r += dr;
                c += dc;

                if (r >= 0 && r < rows && c >= 0 && c < cols)
                    result[index++] = new int[]{r, c};
            }

            int tmp = dr;
            dr = dc;
            dc = -tmp;

            if (iteration % 2 == 0)
                currentStep += 1;

        }

        return result;
    }

    // Leetcode problem: 1921
    public int eliminateMaximum(int[] dist, int[] speed) {
        int n = dist.length;
        int[] minuteReaches = new int[n];

        // Calculate how many minutes will be taken for each monster to reach the city. Take the ceiling.
        for (int i = 0; i < n; i++) {
            minuteReaches[i] = (dist[i] + speed[i] - 1) / speed[i];
        }

        Arrays.sort(minuteReaches);
        int res = 0;
        for (int minute = 1; minute <= n; minute += 1) {

            // If the monster reaches before destroying, then loss the game.
            if (minuteReaches[minute - 1] < minute)
                return res;

            // Destroy 1 monster every 1 minute.
            res += 1;
        }

        return res;
    }

    // Leetcode problem: 1958
    /*
     * Go to each of 8 directions & try to find a good line.
     * */
    public boolean checkMove(char[][] board, int rMove, int cMove, char color) {

        int m = board.length;
        int n = board[0].length;

        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };

        for (int[] direction : directions) {
            int r = rMove + direction[0];
            int c = cMove + direction[1];

            while (isValid(r, c, m, n) && board[r][c] != color) {

                // If blank cell found in the line, it is invalid.
                if (board[r][c] == '.')
                    break;

                r += direction[0];
                c += direction[1];
            }

            // If position is found where color is same as endpoint & minimum length is 3.
            if ((Math.abs(r - rMove) > 1 || Math.abs(c - cMove) > 1) && isValid(r, c, m, n) && board[r][c] == color) {
                return true;
            }
        }

        return false;


    }

    private boolean isValid(int r, int c, int m, int n) {
        return r >= 0 && r < m && c >= 0 && c < n;
    }
}
