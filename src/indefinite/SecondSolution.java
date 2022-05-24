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

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int len1 = getLength(l1);
        int len2 = getLength(l2);

        if (len1 < len2) {
            l1 = fillWithZero(l1, len2 - len1);
        } else {
            l2 = fillWithZero(l2, len1 - len2);
        }
        ListNode result = addList(l1, l2);

        return result.val == 0 ? result.next : result;
    }

    ListNode addList(ListNode l1, ListNode l2) {
        if (l1 == null)
            return new ListNode(0);

        ListNode node = addList(l1.next, l2.next);
        int sum = l1.val + l2.val + node.val;
        node.val = sum % 10;
        ListNode carryNode = new ListNode(sum / 10);
        carryNode.next = node;

        return carryNode;
    }

    private int getLength(ListNode head) {
        if (head == null)
            return 0;
        return 1 + getLength(head.next);
    }

    private ListNode fillWithZero(ListNode head, int lenZero) {
        if (lenZero == 0)
            return head;

        ListNode newNode = new ListNode(0);
        newNode.next = head;
        head = newNode;

        return fillWithZero(head, lenZero - 1);
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

    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null || head.next.next == null)
            return head;

        ListNode odd = head, even = head.next, oddNode = head, evenNode = head.next, oddNext, evenNext, prev = head;
        while (!(oddNode == null || evenNode == null)) {
            oddNext = evenNode.next;
            evenNext = oddNext == null ? null : oddNext.next;

            oddNode.next = oddNext;
            evenNode.next = evenNext;
            prev = oddNode;
            oddNode = oddNode.next;
            evenNode = evenNode.next;
        }
        if (prev.next != null)
            prev = prev.next;

        prev.next = even;

        return odd;
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

    int result = 0;

    private void sumRootToLeaf(TreeNode root, int sum) {
        if (root == null)
            return;
        if (root.left == null && root.right == null)
            result += sum;
        sum = (sum << 1) | root.val;
        sumRootToLeaf(root.left, sum);
        sumRootToLeaf(root.right, sum);
    }

    public int sumRootToLeaf(TreeNode root) {
        sumRootToLeaf(root, 0);
        return result;
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

    public void pathSum(TreeNode root, int targetSum, int sum, List<List<Integer>> finalResult, List<Integer> current) {

        if (root == null)
            return;
        sum += root.val;
        current.add(root.val);
        if (root.left == null && root.right == null) {
            if (sum == targetSum) {
                finalResult.add(new ArrayList<>(current));
            }
        }
        if (root.left != null)
            pathSum(root.left, targetSum, sum, finalResult, current);
        if (root.right != null)
            pathSum(root.right, targetSum, sum, finalResult, current);

        current.remove(current.size() - 1);
    }

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();

        pathSum(root, targetSum, 0, result, current);
        return result;
    }

}
