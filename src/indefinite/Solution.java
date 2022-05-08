package indefinite;

import linkedlist.ListNode;
import tree.TreeNode;

import java.util.*;

public class Solution {
    int maxValue = Integer.MIN_VALUE;

    public int reverse(int x) {
        int rev = 0, prevRev = 0;
        int n = x < 0 ? -x : x;
        while (n > 0) {
            int current = n % 10;
            rev = (rev * 10) + current;
            if ((rev - current) / 10 != prevRev)
                return 0;
            prevRev = rev;
            n /= 10;
        }
        return x < 0 ? -rev : rev;
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

    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0)
            return "";

        StringBuilder result = new StringBuilder();

        String str = strs[0];
        for (int j = 0; j < str.length(); j++) {
            int i;
            for (i = 1; i < strs.length; i++) {
                if (j == strs[i].length() || strs[i].charAt(j) != str.charAt(j))
                    return result.toString();
            }
            result.append(str.charAt(j));
        }

        return result.toString();

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

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> traversal = new ArrayList<>();
        if (root == null)
            return new ArrayList<>();
        traversal.addAll(inorderTraversal(root.left));
        traversal.add(root.val);
        traversal.addAll(inorderTraversal(root.right));
        return traversal;
    }

    public boolean isSymmetric(TreeNode root) {
        if (root == null)
            return true;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root.left);
        queue.add(root.right);

        while (!queue.isEmpty()) {
            TreeNode tempLeft = queue.remove();
            TreeNode tempRight = queue.remove();

            if (tempLeft == null && tempRight == null)
                continue;
            if (tempLeft == null || tempRight == null)
                return false;

            if (tempLeft.val != tempRight.val)
                return false;

            if (tempLeft.left == null && tempLeft.right == null && tempRight.left == null && tempRight.right == null)
                continue;
            queue.add(tempLeft.left);
            queue.add(tempRight.right);
            queue.add(tempLeft.right);
            queue.add(tempRight.left);
        }

        return true;
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

    public boolean isAnagram(String s, String t) {
        int[] array = new int[26];
        for (char c : s.toCharArray()) {
            array[c - 'a']++;
        }
        for (char c : t.toCharArray()) {
            array[c - 'a']--;
        }
        for (int element : array) {
            if (element != 0)
                return false;
        }

        return true;
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

    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (set.contains(num))
                return false;
            set.add(num);
        }

        return true;
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

    public boolean isUgly(int n) {
        if (n == 0)
            return false;
        while (n % 2 == 0) {
            n /= 2;
        }
        while (n % 3 == 0) {
            n /= 3;
        }
        while (n % 5 == 0) {
            n /= 5;
        }

        return n == 1;
    }

    public void printArray(int[] nums) {
        System.out.print("[ ");
        for (int num : nums) {
            System.out.print(num + " ");
        }
        System.out.println("]");
    }

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

    int guess(int num) {
        int pick = 6;
        if (num == pick)
            return 0;
        if (pick < num)
            return -1;
        return 1;
    }

    public int guessNumber(int n) {
        int start = 1, end = n, mid, g;

        while (start <= end) {
            mid = start + (end - start) / 2;
            g = guess(mid);
            if (g == 0)
                return mid;
            else if (g == -1)
                end = mid - 1;
            else
                start = mid + 1;
        }
        return start;
    }

    public boolean canConstruct(String ransomNote, String magazine) {
        int[] array = new int[26];
        for (char ch : magazine.toCharArray()) {
            array[ch - 'a']++;
        }
        for (char ch : ransomNote.toCharArray()) {
            array[ch - 'a']--;
            if (array[ch - 'a'] < 0)
                return false;
        }
        return true;
    }

    public char findTheDifference(String s, String t) {
        int xor = 0;
        for (char ch : s.toCharArray()) {
            xor ^= ch;
        }
        for (char ch : t.toCharArray()) {
            xor ^= ch;
        }
        return (char) xor;
    }

    public boolean isSubsequence(String s, String t) {
        if (s.length() == 0)
            return true;
        int i = 0;
        for (char ch : t.toCharArray()) {
            if (s.charAt(i) == ch) {
                i++;
                if (i == s.length())
                    return true;
            }
        }
        return false;
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

    public void sumOfLeftLeaves(TreeNode root, List<Integer> list, boolean isLeft) {
        if (root == null)
            return;
        if (isLeft && root.left == null && root.right == null) {
            list.add(root.val);
            return;
        }
        if (!isLeft && root.left == null && root.right == null)
            return;

        sumOfLeftLeaves(root.left, list, true);
        sumOfLeftLeaves(root.left, list, false);

    }

    public int sumOfLeftLeaves(TreeNode root) {
        int sum = 0;
        List<Integer> list = new ArrayList<>();
        sumOfLeftLeaves(root, list, true);
        for (int element : list) {
            sum += element;
        }
        return sum;
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        if (l1 == null && l2 == null)
            return null;

        ListNode node1 = l1, node2 = l2, prev = l1;
        int carry = 0;
        boolean fistInsert = true;
        while (!(node1 == null && node2 == null)) {
            int sum = carry + ((node1 == null ? 0 : node1.val) + (node2 == null ? 0 : node2.val));
            carry = sum / 10;
            sum %= 10;
            if (node1 == null) {
                node1 = new ListNode(sum);
                if (fistInsert) {
                    l1 = node1;
                } else {
                    prev.next = node1;
                }
            } else {
                node1.val = sum;
            }
            prev = node1;
            node1 = node1.next;
            if (node2 != null) {
                node2 = node2.next;
            }
            fistInsert = false;
        }
        if (carry > 0) {
            node1 = new ListNode(carry);
            prev.next = node1;
        }

        return l1;
    }

    public String intToRoman(int num) {
        String result = "";
        while (num > 0) {
            if (num >= 1000) {
                result += "M";
                num -= 1000;
            } else if (num >= 900) {
                result += "CM";
                num -= 900;
            } else if (num >= 500) {
                result += "D";
                num -= 500;
            } else if (num >= 400) {
                result += "CD";
                num -= 400;
            } else if (num >= 100) {
                result += "C";
                num -= 100;
            } else if (num >= 90) {
                result += "XC";
                num -= 90;
            } else if (num >= 50) {
                result += "L";
                num -= 50;
            } else if (num >= 40) {
                result += "XL";
                num -= 40;
            } else if (num >= 10) {
                result += "X";
                num -= 10;
            } else if (num >= 9) {
                result += "IX";
                num -= 9;
            } else if (num >= 5) {
                result += "V";
                num -= 5;
            } else if (num >= 4) {
                result += "IV";
                num -= 4;
            } else if (num >= 1) {
                result += "I";
                num -= 1;
            }
        }

        return result;
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

    public int romanToInt(String s) {
        int number = 0;
        while (s.length() > 0) {
            if (s.startsWith("CM")) {
                number += 900;
                s = s.substring(2);
            } else if (s.startsWith("M")) {
                number += 1000;
                s = s.substring(1);
            } else if (s.startsWith("CD")) {
                number += 400;
                s = s.substring(2);
            } else if (s.startsWith("D")) {
                number += 500;
                s = s.substring(1);
            } else if (s.startsWith("XC")) {
                number += 90;
                s = s.substring(2);
            } else if (s.startsWith("C")) {
                number += 100;
                s = s.substring(1);
            } else if (s.startsWith("XL")) {
                number += 40;
                s = s.substring(2);
            } else if (s.startsWith("L")) {
                number += 50;
                s = s.substring(1);
            } else if (s.startsWith("IX")) {
                number += 9;
                s = s.substring(2);
            } else if (s.startsWith("X")) {
                number += 10;
                s = s.substring(1);
            } else if (s.startsWith("IV")) {
                number += 4;
                s = s.substring(2);
            } else if (s.startsWith("V")) {
                number += 5;
                s = s.substring(1);
            } else if (s.startsWith("I")) {
                number += 1;
                s = s.substring(1);
            }
        }
        return number;
    }

    public double myPow(double x, int n) {
        int sign = 1, powerSign = 1;
        if (x < 0) {
            x = -x;
            sign = -1;
        }
        if (n < 0) {
            n = -n;
            powerSign = -1;
        }
        if (n == 0)
            return 1;
        if (n == 1)
            return x * sign;

        double mul = 1;
        while (n > 0) {

        }
        return mul;
    }

    public int jump(int[] nums) {
        int n = nums.length;
        int ans = 0, end = 0, far = 0;
        for (int i = 0; i < n - 1; i++) {
            far = Math.max(far, i + nums[i]);
            if (end == i) {
                ans++;
                end = far;
            }
            if (end >= n - 1) break;
        }
        return end < n - 1 ? -1 : ans;
    }

    public double negativePow(double x, int n) {
        if (n == 0) {
            return 1;
        }
        if (n % 2 == 0) {
            return negativePow(x * x, n / 2);
        }
        return ((double) 1 / (x)) * negativePow(x, n + 1);
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

    public ListNode swapPairs(ListNode head) {
        if (head == null)
            return null;

        ListNode prev = head, next = head.next;
        while (!(prev == null || next == null)) {
            int temp = prev.val;
            prev.val = next.val;
            next.val = temp;
            prev = next.next;
            next = prev == null ? null : prev.next;
        }
        return head;
    }

    public int lengthOfLastWord(String s) {
        int i = s.length() - 1;
        int lastWordLength = 0;

        while (i >= 0 && s.charAt(i) != ' ') {
            lastWordLength++;
            i--;
        }
        return lastWordLength;
    }

    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || k == 0)
            return head;
        ListNode node = head;
        int n = 0;
        while (node != null) {
            n++;
            node = node.next;
        }
        int actualRotate = k % n;
        if (actualRotate == 0)
            return head;

        ListNode rightPart = head;
        int right = n - actualRotate;
        node = head;
        int i = 1;
        while (i < right) {
            node = node.next;
            i++;
        }
        head = node.next;
        node.next = null;
        node = head;
        ListNode prev = head;
        while (node != null) {
            prev = node;
            node = node.next;
        }
        prev.next = rightPart;

        return head;
    }

    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (head == null || left == right)
            return head;

        ListNode node = head, prev, next, leftNode, rightNode;
        leftNode = rightNode = head;
        int i = 1;
        while (i < right) {
            i++;
            node = node.next;
            if (i == left) {
                leftNode = node;
            }
            if (i == right) {
                rightNode = node;
            }

        }
        prev = rightNode.next;
        node = leftNode;
        i = 1;
        while (i <= right - left + 1) {
            next = node.next;
            node.next = prev;
            prev = node;
            node = next;

            i++;
        }

        if (left == 1)
            return prev;
        node = head;
        i = 1;
        while (i < left) {
            leftNode = node;
            node = node.next;
            i++;
        }

        leftNode.next = prev;

        return head;
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null)
            return null;
        ListNode slower, faster, prev;
        slower = faster = prev = head;
        int i = 1;
        while (i < n) {
            faster = faster.next;
            i++;
        }
        while (faster.next != null) {
            prev = slower;
            slower = slower.next;
            faster = faster.next;
        }
        if (prev == slower)
            head = head.next;
        else
            prev.next = slower.next;
        return head;
    }

    public ListNode swapNodes(ListNode head, int k) {
        ListNode leftNode, slower, faster;
        slower = faster = head;
        int i = 1;
        while (i < k) {
            i++;
            faster = faster.next;
        }
        leftNode = faster;
        while (faster.next != null) {
            slower = slower.next;
            faster = faster.next;
        }
        int temp = leftNode.val;
        leftNode.val = slower.val;
        slower.val = temp;
        return head;
    }

    public void rotate(int[] nums, int k) {
        int n = nums.length;
        int actualRotate = k % n;
        if (actualRotate == 0)
            return;


    }
}
