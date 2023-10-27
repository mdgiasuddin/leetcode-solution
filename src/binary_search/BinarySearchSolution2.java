package binary_search;

import java.util.Arrays;

public class BinarySearchSolution2 {

    public static void main(String[] args) {
        BinarySearchSolution2 binarySearchSolution2 = new BinarySearchSolution2();

        int[] array = {1, 2, 4, 5, 0};
        // 3 0
        // 1, 4, 4, 5, 0
        System.out.println(binarySearchSolution2.maxPower(array, 1, 2));
    }

    // Leetcode problem: 2528
    /*
     * Maximize the Minimum Powered City.
     * Explanation: https://www.youtube.com/watch?v=E6aEq7V9TDk
     * */
    public long maxPower(int[] stations, int r, int k) {
        long low = stations[0];
        long high = k;

        for (int station : stations) {
            low = Math.min(low, station);
            high += station;
        }

        long res = low;
        while (low <= high) {
            long mid = low + (high - low) / 2;

            if (isPossible(stations, r, k, mid)) {
                res = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return res;
    }

    private boolean isPossible(int[] stations, int r, int k, long power) {
        int n = stations.length;
        int[] copy = Arrays.copyOf(stations, n);

        long total = 0;
        for (int i = 0; i <= r; i++) {
            total += copy[i];
        }

        int i = 0;
        while (i < n) {

            long addition = Math.max(0, power - total);
            if (addition > k) {
                return false;
            }

            total += addition;
            copy[Math.min(i + r, n - 1)] += addition;
            k -= addition;

            i += 1;
            if (i > r) {
                total -= copy[i - (r + 1)];
            }
            if (i + r < n) {
                total += copy[i + r];
            }
        }

        return true;
    }

    // Leetcode problem: 1011
    /*
     * Capacity To Ship Packages Within D Days.
     * This problem is similar to Maximum Tastiness of Candy Basket (Leetcode problem: 2517).
     * */
    public int shipWithinDays(int[] weights, int days) {
        int sum = 0;
        int max = weights[0];

        for (int weight : weights) {
            max = Math.max(max, weight);
            sum += weight;
        }

        int l = max;
        int h = sum;
        int capacity = sum;

        while (l <= h) {
            int m = l + (h - l) / 2;
            if (canShipAll(weights, m, days)) {
                capacity = m;
                h = m - 1;
            } else {
                l = m + 1;
            }
        }

        return capacity;
    }

    private boolean canShipAll(int[] weights, int capacity, int days) {
        int current = 0;
        int currentDays = 1;

        for (int i = 0; i < weights.length; i++) {
            current += weights[i];
            if (current > capacity) {
                current = weights[i];
                currentDays += 1;
            }
        }

        return currentDays <= days;
    }

    // Leetcode problem: 2300
    /*
     * Successful Pairs of Spells and Potions.
     * */
    public int[] successfulPairs(int[] spells, int[] potions, long success) {
        Arrays.sort(potions);
        int n = spells.length;
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = countSuccess(potions, spells[i], success);
        }

        return res;

    }

    private int countSuccess(int[] potions, long spell, long success) {

        int l = 0;
        int r = potions.length - 1;
        int ans = 0;

        while (l <= r) {
            int m = l + (r - l) / 2;

            if (spell * potions[m] >= success) {
                ans = potions.length - m;
                r = m - 1;
            } else {
                l = m + 1;
            }
        }

        return ans;
    }

    // Leetcode problem: 2513
    /*
     * Minimize the Maximum of Two Arrays
     * Explanation: https://www.youtube.com/watch?v=7gyZXnU3KGw&list=PLEvw47Ps6OBC_kTW44HAFBBCiDOk9E4Rs&index=3
     * */
    public int minimizeSet(int divisor1, int divisor2, int uniqueCnt1, int uniqueCnt2) {
        long d1 = divisor1;
        long d2 = divisor2;

        long lcm = lcm(d1, d2);

        long l = 1;
        long r = 10000000000L;

        long ans = r;
        while (l <= r) {
            long m = l + (r - l) / 2;

            // If divisors are same exclude the numbers divided by d1
            if (d1 == d2) {
                if (m - m / d1 >= uniqueCnt1 + uniqueCnt2) {
                    ans = m;
                    r = m - 1;
                } else {
                    l = m + 1;
                }
            } else {
                long com = m / lcm;
                long div1 = m / d1;
                long div2 = m / d2;

                // Numbers only divisible by d1 can be put into second array.
                // Numbers only divisible by d2 can be put into first array.
                long z1 = div2 - com;
                long z2 = div1 - com;

                // Numbers neither divisible by d1 nor d2.
                long z3 = m - (div1 + div2 - com);

                // Count the remaining numbers needs in the first & second array.
                long req1 = Math.max(0, uniqueCnt1 - z1);
                long req2 = Math.max(0, uniqueCnt2 - z2);
                if (z3 >= req1 + req2) {
                    ans = m;
                    r = m - 1;
                } else {
                    l = m + 1;
                }
            }
        }

        return (int) ans;
    }

    private long gcd(long a, long b) {
        if (a < b) {
            long tmp = a;
            a = b;
            b = tmp;
        }

        while (b != 0) {
            long tmp = a % b;
            a = b;
            b = tmp;
        }

        return a;
    }

    private long lcm(long a, long b) {
        return (a * b) / gcd(a, b);
    }

    // Leetcode problem: 2616
    /*
     * Minimize the Maximum Difference of Pairs.
     * Explanation: https://www.youtube.com/watch?v=lf1Pxg7IrzQ
     * */
    public int minimizeMax(int[] nums, int p) {
        Arrays.sort(nums);
        int n = nums.length;
        int l = 0;
        int r = nums[n - 1];
        int ans = r;

        while (l <= r) {
            int m = l + (r - l) / 2;
            if (isPossible(nums, p, m)) {
                ans = m;
                r = m - 1;
            } else {
                l = m + 1;
            }
        }

        return ans;
    }

    // Leetcode problem: 1539
    /*
     * Kth Missing Positive Number.
     * Explanation: https://www.youtube.com/watch?v=88k8xa-pSrM
     * */
    public int findKthPositive(int[] arr, int k) {
        int n = arr.length;
        int left = 0;
        int right = n - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int missing = arr[mid] - (mid + 1);
            if (missing >= k) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        /*
         * right = -1 => All the missing number lies behind arr[0].
         * otherwise, result = arr[right] + count of the rest of the missing numbers.
         * */
        return right == -1 ? k : arr[right] + (k - (arr[right] - (right + 1)));

    }

    private boolean isPossible(int[] nums, int p, int diff) {
        int i = 0;
        int valid = 0;
        while (i < nums.length - 1) {
            if (nums[i + 1] - nums[i] <= diff) {
                valid += 1;
                i += 2;
            } else {
                i += 1;
            }
        }

        return valid >= p;
    }

    // Leetcode problem: 852
    /*
     * Peak Index in a Mountain Array.
     * */
    public int peakIndexInMountainArray(int[] arr) {

        // Initial value is important.
        int l = 1;
        int r = arr.length - 2;

        while (l <= r) {
            int m = l + (r - l);

            if (arr[m - 1] < arr[m] && arr[m] > arr[m + 1]) {
                return m;
            } else if (arr[m - 1] > arr[m]) {
                r = m - 1;
            } else {
                l = m + 1;
            }
        }

        return -1;
    }

    // Leetcode problem: 719
    /*
     * Find K-th Smallest Pair Distance.
     * Explanation: https://www.youtube.com/watch?v=veu_Q-da6ZY
     * */
    public int smallestDistancePair(int[] nums, int k) {
        Arrays.sort(nums);

        int n = nums.length;
        int high = nums[n - 1] - nums[0];
        int low = Integer.MAX_VALUE;
        for (int i = 0; i < n - 1; i++) {
            low = Math.min(low, nums[i + 1] - nums[i]);
        }

        // Conditions are important.
        while (low < high) {
            int mid = low + (high - low) / 2;
            int pair = satisfiedPair(nums, mid);
            if (pair < k) {
                low = mid + 1;
            } else { // Find better solution.
                high = mid;
            }
        }

        return low;
    }

    private int satisfiedPair(int[] nums, int mid) {
        int l = 0;
        int count = 0;
        for (int r = 1; r < nums.length; r++) {
            while (nums[r] - nums[l] > mid) {
                l += 1;
            }

            count += r - l;
        }
        return count;
    }

    // Leetcode problem: 154
    /*
     * Find Minimum in Rotated Sorted Array II.
     * Explanation: https://www.youtube.com/watch?v=uTqU_CVNoWw
     * The conditions are important.
     * */
    public int findMin(int[] nums) {
        int l = 0;
        int r = nums.length - 1;

        while (l < r) {
            int m = l + (r - l) / 2;

            if (nums[m] < nums[r]) {
                r = m;
            } else if (nums[m] == nums[r]) {
                r -= 1;
            } else {
                l = m + 1;
            }
        }

        return nums[l];
    }
}
