package indefinite;

import linkedlist.ListNode;
import tree.TreeNode;

import java.util.*;

public class SecondSolution {

    public String addBinary(String a, String b) {
        if (a.equals("0") && b.equals("0"))
            return "0";

        int lenA = a.length(), lenB = b.length();
        int sumLength = 1 + Math.max(lenA, lenB);
        char[] sum = new char[sumLength];
        for (int i = 0; i < sumLength; i++)
            sum[i] = '0';

        int i = 1;
        int carry = 0;
        while (i <= lenA && i <= lenB) {
            int digit = a.charAt(lenA - i) - '0' + b.charAt(lenB - i) - '0' + carry;
            sum[sumLength - i] = (char) (digit % 2 + '0');
            carry = digit / 2;
            i++;
        }
        while (i <= lenA) {
            int digit = a.charAt(lenA - i) - '0' + carry;
            sum[sumLength - i] = (char) (digit % 2 + '0');
            carry = digit / 2;
            i++;
        }
        while (i <= lenB) {
            int digit = b.charAt(lenB - i) - '0' + carry;
            sum[sumLength - i] = (char) (digit % 2 + '0');
            carry = digit / 2;
            i++;
        }
        sum[0] = (char) (carry + '0');

        i = 0;
        while (sum[i] == '0')
            i++;

        return String.copyValueOf(sum, i, sumLength - i);
    }

    public int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            count += (n & 1) == 0 ? 0 : 1;
            n >>>= 1;
        }
        return count;
    }

    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null)
            return null;

        if (key < root.val)
            root.left = deleteNode(root.left, key);
        else if (key > root.val)
            root.right = deleteNode(root.right, key);
        else {
            if (root.right == null)
                return root.left;
            if (root.left == null)
                return root.right;

            root.val = findMin(root.right);
            root.right = deleteNode(root.right, root.val);
        }

        return root;
    }

    int findMin(TreeNode root) {
        int minValue = root.val;
        while (root != null) {
            minValue = root.val;
            root = root.left;
        }
        return minValue;
    }

    public String addStrings(String num1, String num2) {
        if (num1.equals("0") && num2.equals("0"))
            return "0";

        int len1 = num1.length();
        int len2 = num2.length();
        int sumLen = 1 + Math.max(len1, len2);

        char[] sum = new char[sumLen];

        int carry = 0;
        for (int i = 1; i <= sumLen - 1; i++) {
            int digit = (len1 - i >= 0 ? num1.charAt(len1 - i) - '0' : 0)
                    + (len2 - i >= 0 ? num2.charAt(len2 - i) - '0' : 0) + carry;
            sum[sumLen - i] = (char) (digit % 10 + '0');
            carry = digit / 10;
        }
        sum[0] = (char) (carry + '0');

        return carry == 0 ? String.copyValueOf(sum, 1, sumLen - 1) : String.valueOf(sum);
    }

    public int thirdMax(int[] nums) {
        int first, second, third;
        first = second = third = Integer.MIN_VALUE;

        boolean minExists = false;
        for (int num : nums) {
            if (num == Integer.MIN_VALUE)
                minExists = true;
            if (num > first) {
                third = second;
                second = first;
                first = num;
            } else if (num < first && num > second) {
                third = second;
                second = num;
            } else if (num < second && num > third) {
                third = num;
            }
        }
        return (second == third) || (!minExists && third == Integer.MIN_VALUE) ? first : third;
    }

    public int getSum(int a, int b) {

        int carry = 0;
        while (b != 0) {
            carry = a & b;
            a = a ^ b;
            b = carry << 1;
        }
        return a;
    }

    public int[] findDiagonalOrder(int[][] mat) {
        int m = mat.length, n = mat[0].length;
        int[] result = new int[m * n];

        int i = 0, j = 0, k = 0;
        boolean upDir = true;
        while (i < m && j < n) {
            result[k] = mat[i][j];
            if (upDir) {

            }
        }

        return result;
    }

    public int getDecimalValue(ListNode head) {
        int number = 0;
        ListNode node = head;
        while (node != null) {
            number = number * 2 + node.val;
            node = node.next;
        }
        return number;
    }

    public ListNode mergeInBetween(ListNode list1, int a, int b, ListNode list2) {
        ListNode prevA = list1, nextB = null, node = list1;

        int i = 1;
        while (i <= a) {
            prevA = node;
            node = node.next;
            i++;
        }
        i = 0;
        while (i <= b - a) {
            node = node.next;
            i++;
        }
        nextB = node;

        node = list2;
        while (node.next != null) {
            node = node.next;
        }

        prevA.next = list2;
        node.next = nextB;

        return list1;
    }

    public TreeNode sortedListToBST(ListNode head) {
        if (head == null)
            return null;
        if (head.next == null)
            return new TreeNode(head.val);

        ListNode slow = head, fast = head.next;
        while (!(fast.next == null || fast.next.next == null)) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode mid = slow.next;
        slow.next = null;

        TreeNode root = new TreeNode(mid.val);
        root.left = sortedListToBST(head);
        root.right = sortedListToBST(mid.next);

        return root;
    }

    public ListNode detectCycle(ListNode head) {
        ListNode slow, fast, meet = null;
        slow = fast = head;

        while (!(fast == null || fast.next == null)) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                meet = slow;
                break;
            }
        }
        if (meet == null)
            return null;

        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        int lenA, lenB;
        lenA = lenB = 0;
        ListNode nodeA, nodeB;
        nodeA = headA;
        nodeB = headB;

        while (nodeA != null) {
            lenA++;
            nodeA = nodeA.next;
        }
        while (nodeB != null) {
            lenB++;
            nodeB = nodeB.next;
        }
        nodeA = headA;
        nodeB = headB;
        int i = 1;
        if (lenA > lenB) {
            while (i <= (lenA - lenB)) {
                nodeA = nodeA.next;
                i++;
            }
        } else {
            while (i <= (lenB - lenA)) {
                nodeB = nodeB.next;
                i++;
            }
        }
        while (nodeA != nodeB) {
            nodeA = nodeA.next;
            nodeB = nodeB.next;
        }
        return nodeA;
    }

    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();

        if (root == null)
            return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        queue.add(null);

        List<Integer> level = new ArrayList<>();
        while (!queue.isEmpty()) {
            TreeNode tempNode = queue.poll();

            if (tempNode == null) {
                result.add(level);
                level = new ArrayList<>();
                if (queue.isEmpty())
                    break;
                queue.add(null);
            } else {
                level.add(tempNode.val);
                if (tempNode.left != null)
                    queue.add(tempNode.left);
                if (tempNode.right != null)
                    queue.add(tempNode.right);
            }
        }

        Collections.reverse(result);
        return result;
    }

}
