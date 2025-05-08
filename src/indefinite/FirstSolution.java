package indefinite;

import linkedlist.ListNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirstSolution {
    int maxValue = Integer.MIN_VALUE;

    // Leetcode problem: 7
    public int reverse(int x) {

        int rev = 0, prevRev = 0;
        int n = x < 0 ? -x : x;

        while (n > 0) {
            int current = n % 10;
            rev = (rev * 10) + current;

            /**
             * Check whether it overflows.
             * If this overflows, then the number will be completely different from previous.
             * */
            if ((rev - current) / 10 != prevRev)
                return 0;

            prevRev = rev;
            n /= 10;
        }
        return x < 0 ? -rev : rev;
    }

    // Leetcode problem: 233

    /**
     * The problem is tricky.
     * */
    public int countDigitOne(int n) {
        int count = 0, power = 1, power10, rem;

        while (n >= power) {
            power10 = power * 10;

            /**
             * For power = 10 there are 10, 11, 12, ..., 19 (10 1s). Similarly, for 100 => 101, 102, 103, 199 (100 1s).
             * So, multiplied by power.
             * */
            count += ((n / power10) * power);
            rem = n % power10;

            /**
             * n / power10 covers 0 to previous power. For 1356, power = 10, n / 100 covers 0, 1, 2, ..., 12 hundreds.
             * So, calculate for 13. If the reminder >= 19 (2 * power - 1), we can take the full 10 options (10, 11, 12, ..., 19).
             * But for less value we will get fewer options. For rem = 15, we will get 10, 11, 13, ..., 15.
             * */
            if (rem >= 2 * power - 1)
                count += power;
            else if (rem >= power)
                count += (rem - power + 1);

            power *= 10;
        }

        return count;
    }

    public boolean isPalindrome(int x) {
        String xString = String.valueOf(x);
        int i = 0, j = xString.length() - 1;
        while (i < j) {
            if (xString.charAt(i) != xString.charAt(j))
                return false;
            i++;
            j--;
        }
        return true;
    }

    public void changeArray(int[] array) {
        array[0] = 100;
    }

    public int[] plusOne(int[] digits) {
        for (int i = digits.length - 1; i >= 0; i--) {
            digits[i] = (digits[i] + 1) % 10;
            if (digits[i] != 0)
                break;
        }
        if (digits[0] == 0) {
            int[] newArray = new int[digits.length + 1];
            newArray[0] = 1;
            for (int i = 0; i < digits.length; i++) {
                newArray[i + 1] = digits[i];
            }
            return newArray;
        }
        return digits;
    }

    public int findMax(int[] nums) {
        int left, right, mid;
        left = 0;
        right = nums.length - 1;

        while ((right - left + 1) > 2) {
            if (nums[left] < nums[right])
                return nums[right];
            mid = (left + right) / 2;
            if (nums[mid] > nums[mid - 1] && nums[mid] > nums[mid + 1])
                return nums[mid];
            if (nums[left] > nums[mid])
                right = mid - 1;
            else
                left = mid + 1;
        }
        if (left == right)
            return nums[left];
        return Math.max(nums[left], nums[right]);
    }

    public void deleteNode(ListNode node) {
        ListNode prev = node;
        while (node.next != null) {
            prev = node;
            node.val = node.next.val;
            node = node.next;
        }
        prev.next = null;
    }

    public boolean isHappy(int n) {
        if (n == 1 || n == 7)
            return true;
        int sum = n, x = n;

        while (sum > 9) {
            sum = 0;

            while (x > 0) {
                int d = x % 10;
                sum += d * d;
                x /= 10;
            }
            x = sum;
        }
        return sum == 1 || sum == 7;
    }

    public int countPrimes(int n) {
        if (n < 2)
            return 0;
        boolean[] nonPrimes = new boolean[n];
        nonPrimes[0] = nonPrimes[1] = true;

        for (int i = 2; i * i <= n; i++) {
            if (!nonPrimes[i]) {
                for (int j = 2; i * j < n; j++) {
                    nonPrimes[i * j] = true;
                }
            }
        }
        int count = 0;
        for (boolean nonPrime : nonPrimes) {
            count += (nonPrime ? 0 : 1);
        }

        return count;
    }

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 1; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                int val = i - map.get(nums[i]);
                if (val <= k)
                    return true;
            }
        }

        return false;
    }

    public int addDigits(int num) {
        int sum;
        while (num > 9) {
            sum = 0;
            while (num > 0) {
                sum += (num % 10);
                num /= 10;
            }
            num = sum;
        }
        return num;
    }

    // Leetcode problem: 264
    public int nthUglyNumber(int n) {
        int[] array = new int[n];
        array[0] = 1;
        int serial = 0;
        int ugly2, ugly3, ugly5, uglyN;
        ugly2 = ugly3 = ugly5 = 0;
        while (serial < n - 1) {
            uglyN = Math.min(Math.min(array[ugly2] * 2, array[ugly3] * 3), array[ugly5] * 5);
            serial++;
            array[serial] = uglyN;
            if (uglyN == array[ugly2] * 2)
                ugly2++;
            if (uglyN == array[ugly3] * 3)
                ugly3++;
            if (uglyN == array[ugly5] * 5)
                ugly5++;
        }

        return array[n - 1];
    }

    // Leetcode problem: 268
    public int missingNumber(int[] nums) {
        int n = nums.length;
        int sum = n * (n + 1) / 2;
        for (int num : nums) {
            sum -= num;
        }

        return sum;
    }

    boolean isBadVersion(int version) {
        return version >= 1702766719;
    }

    public int firstBadVersion(int n) {
        int st = 1, end = n, mid = 0;
        boolean bad;
        while (st <= end) {
            mid = st + (end - st) / 2;
            bad = isBadVersion(mid);
            if (bad) {
                if (!isBadVersion(mid - 1))
                    return mid;
                end = mid - 1;
            } else {
                if (isBadVersion(mid + 1))
                    return mid + 1;
                st = mid + 1;
            }
        }
        return mid;
    }

    public void moveZeroes(int[] nums) {
        int n = nums.length;
        int idx = 0;
        for (int element : nums) {
            if (element != 0) {
                nums[idx] = element;
                idx++;
            }
        }
        while (idx < nums.length) {
            nums[idx] = 0;
            idx++;
        }
    }

    public void reverseString(char[] s) {
        int left = 0, right = s.length - 1;
        char temp;
        while (left < right) {
            temp = s[left];
            s[left] = s[right];
            s[right] = temp;

            left++;
            right--;
        }
    }

    public String reverseString(String string) {
        if (string.length() <= 1)
            return string;

        return string.charAt(string.length() - 1) + reverseString(string.substring(0, string.length() - 1));
    }

    public int secondLargest(int[] array) {
        if (array.length < 2)
            return -1;

        int first = Math.max(array[0], array[1]);
        int second = Math.min(array[0], array[1]);

        for (int i = 2; i < array.length; i++) {
            if (array[i] > first) {
                second = first;
                first = array[i];
            } else if (array[i] > second) {
                second = array[i];
            }
        }

        return second;
    }

    void numberToWords(int num, String power, List<String> stringList) {
        if (num == 0)
            return;

        String[] singleDigit = new String[]{
                "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen"
                , "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"
        };
        String[] doubleDigit = new String[]{
                "", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"
        };
        if (num < 20) {
            stringList.add(singleDigit[num]);
        } else {
            stringList.add(doubleDigit[num / 10] + (num % 10 == 0 ? "" : " " + singleDigit[num % 10]));
        }
        if (power.length() > 0)
            stringList.add(power);
    }

    public String numberToWords(int num) {
        if (num == 0)
            return "Zero";

        int billion = num / 1000000000;
        int million = (num / 1000000) % 1000;
        int thousand = (num / 1000) % 1000;
        int rest = num % 1000;

        List<String> stringList = new ArrayList<>();

        if (billion > 0) {
            int hundred = billion / 100;
            numberToWords(hundred, "Hundred", stringList);
            int decimal = billion % 100;
            numberToWords(decimal, "", stringList);
            stringList.add("Billion");
        }
        if (million > 0) {
            int hundred = million / 100;
            numberToWords(hundred, "Hundred", stringList);
            int decimal = million % 100;
            numberToWords(decimal, "", stringList);
            stringList.add("Million");
        }
        if (thousand > 0) {
            int hundred = thousand / 100;
            numberToWords(hundred, "Hundred", stringList);
            int decimal = thousand % 100;
            numberToWords(decimal, "", stringList);
            stringList.add("Thousand");
        }
        if (rest > 0) {
            int hundred = rest / 100;
            numberToWords(hundred, "Hundred", stringList);
            int decimal = rest % 100;
            numberToWords(decimal, "", stringList);
        }

        return String.join(" ", stringList);
    }

    public int divide(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == -1)
            return Integer.MAX_VALUE; //Cornor case when -2^31 is divided by -1 will give 2^31 which doesnt exist so overflow

        boolean negative = dividend < 0 ^ divisor < 0; //Logical XOR will help in deciding if the results is negative only if any one of them is negative

        dividend = Math.abs(dividend);
        divisor = Math.abs(divisor);
        int quotient = 0, subQuot = 0;

        while (dividend - divisor >= 0) {
            //for (subQuot = 0; dividend - (divisor << subQuot << 1) >= 0; subQuot++) ;
            subQuot = 0;
            while (dividend - (divisor << subQuot << 1) >= 0) {
                System.out.println("Divisor loop: " + (divisor << subQuot << 1));
                subQuot++;
            }
            System.out.println("Sub Quot: " + subQuot);
            quotient += 1 << subQuot; //Add to the quotient
            System.out.println("Quot: " + quotient);
            dividend -= divisor << subQuot; //Substract from dividend to start over with the remaining
            System.out.println("Dividend: " + dividend);
        }
        return negative ? -quotient : quotient;
    }

    public int divideWithoutDivision(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == -1)
            return Integer.MAX_VALUE;

        int result = 0;

        boolean negative = dividend < 0 ^ divisor < 0;
        dividend = Math.abs(dividend);
        divisor = Math.abs(divisor);
        while (dividend >= divisor) {
            int power = 0;
            while (dividend - (divisor << (power + 1)) >= 0) {
                power++;
            }
            result += (1 << power);
            dividend -= (divisor << power);
        }
        return negative ? -result : result;
    }

    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> triangle = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        list.add(1);
        triangle.add(list);
        if (numRows == 1)
            return triangle;
        for (int i = 1; i < numRows; i++) {
            List<Integer> line = new ArrayList<>();
            line.add(1);
            int num = 1;
            for (int j = 1; j <= i; j++) {
                num = num * (i - j + 1) / j;
                line.add(num);
            }
            triangle.add(line);
        }
        return triangle;
    }

    public List<Integer> getRow(int rowIndex) {
        List<Integer> list = new ArrayList<>();
        Long num = 1L;
        list.add(num.intValue());
        if (rowIndex == 0)
            return list;
        for (int i = 1; i <= rowIndex; i++) {
            num = num * (rowIndex - i + 1) / i;
            list.add(num.intValue());
        }
        return list;
    }

    public int myAtoi(String s) {
        int number = 0, i = 0;
        boolean minus = false;
        while (s.charAt(i) == ' ')
            i++;
        if (s.charAt(0) == '-') {
            minus = true;
            i++;
        } else if (s.charAt(0) == '+') {
            minus = false;
            i++;
        }
        while (i < s.length()) {
            int digit = s.charAt(i) - '0';
            if (digit < 0 || digit > 9)
                break;
            number = number * 10 + digit;
            i++;
        }

        return minus ? -number : number;
    }
}
