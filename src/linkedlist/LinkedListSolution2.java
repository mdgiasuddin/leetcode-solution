package linkedlist;

class DoublyNode {
    char ch;
    DoublyNode prev, next;

    public DoublyNode(char ch) {
        this.ch = ch;
    }
}

public class LinkedListSolution2 {
    public static void main(String[] args) {
        LinkedListSolution2 solution2 = new LinkedListSolution2();
        solution2.solve("jyhrcwuengcbnuchctluxjgtxqtfvrebveewgasluuwooupcyxwgl");
    }

    // Leetcode problem: 83
    public ListNode deleteDuplicates(ListNode head) {
        ListNode current = head;
        while (current != null) {
            // Remove until found duplicate.
            while (current.next != null && current.val == current.next.val)
                current.next = current.next.next;

            current = current.next;
        }

        return head;
    }

    // Leetcode problem: 143
    /*
     * This problem is tricky. Take special attention.
     * */
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null)
            return head;

        ListNode mid = findMid(head);
        head = sortList(head);
        mid = sortList(mid);

        return merge(head, mid);
    }

    ListNode merge(ListNode left, ListNode right) {
        ListNode dummy = new ListNode();
        ListNode temp = dummy;

        while (!(left == null || right == null)) {
            if (left.val <= right.val) {
                temp.next = left;
                left = left.next;
            } else {
                temp.next = right;
                right = right.next;
            }
            temp = temp.next;
        }
        if (left != null) {
            temp.next = left;
        }
        if (right != null)
            temp.next = right;

        return dummy.next;
    }

    ListNode findMid(ListNode head) {
        ListNode slow = head, fast = head.next;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode mid = slow.next;
        slow.next = null;
        return mid;
    }

    // Leetcode problem: 141
    public boolean hasCycle(ListNode head) {
        ListNode slow, fast;
        slow = fast = head;

        while (!(fast == null || fast.next == null)) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast)
                return true;
        }
        return false;
    }

    // Leetcode problem: 287
    /*
     * Cycle detection problem.
     * Consider array as linked list. Index as prev and value as next.
     * [1,3,4,2,2] => 0->1->3->2->4->2
     * */
    public int findDuplicate(int[] nums) {
        int slow = 0, fast = 0;

        while (true) {
            slow = nums[slow];
            fast = nums[nums[fast]];

            if (slow == fast)
                break;
        }

        int slow2 = 0;

        while (slow != slow2) {
            slow = nums[slow];
            slow2 = nums[slow2];
        }

        return slow;
    }

    // Leetcode problem: 147
    public ListNode insertionSortList(ListNode head) {
        ListNode dummy = new ListNode();
        dummy.next = head;

        ListNode prev = head, current = head.next;

        while (current != null) {
            if (current.val >= prev.val) {
                prev = current;
                current = current.next;
                continue;
            }

            ListNode tmp = dummy;
            while (current.val > tmp.next.val)
                tmp = tmp.next;

            prev.next = current.next;
            current.next = tmp.next;
            tmp.next = current;
            current = prev.next;

        }

        return dummy.next;
    }

    // Leetcode problem: 328
    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null)
            return head;

        ListNode oddHead = new ListNode();
        ListNode evenHead = new ListNode();

        ListNode tOdd = oddHead, tEven = evenHead;

        boolean odd = true;

        while (head != null) {
            if (odd) {
                tOdd.next = head;
                tOdd = tOdd.next;
            } else {
                tEven.next = head;
                tEven = tEven.next;
            }
            head = head.next;
            odd = !odd;
        }

        tOdd.next = evenHead.next;

        // Don't forget to do this.
        tEven.next = null;

        return oddHead.next;
    }

    // Leetcode problem: 1721
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

    // Leetcode problem: 445
    /*
     * Fill leading position of small list with 0.
     * This problem contains high level recursion.
     * Understand the step carefully.
     * */
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

    public int getLength(ListNode head) {
        if (head == null)
            return 0;
        return 1 + getLength(head.next);
    }

    public ListNode fillWithZero(ListNode head, int lenZero) {
        if (lenZero == 0)
            return head;

        ListNode newNode = new ListNode(0);
        newNode.next = head;
        head = newNode;

        return fillWithZero(head, lenZero - 1);
    }

    // Leetcode problem: 25
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode current = head;
        int len = 0;

        while (current != null) {
            len += 1;
            current = current.next;
        }

        int group = len / k;

        current = head;
        ListNode last;
        ListNode prevLast;

        last = current;

        int i = 0;
        while (i < group) {

            // After reverse, first node will be the last node.
            prevLast = last;
            last = current;

            // Reverse each group
            ListNode prev = null;
            for (int j = 0; j < k; j++) {
                ListNode next = current.next;
                current.next = prev;
                prev = current;
                current = next;
            }

            // Current is now the first node of next group. Prev is now the head of the recently reversed group.
            if (i == 0) {
                head = prev;
            } else {
                prevLast.next = prev;
            }

            i += 1;
        }

        // Connect with the last group which is not reversed.
        last.next = current;

        return head;
    }

    // Lintcode problem: 904
    /*
     * Recursive call for this problem is similar to Leetcode problem: 445
     * */
    public ListNode plusOne(ListNode head) {
        ListNode node = plusOneRec(head);

        return node.val == 0 ? node.next : node;
    }

    public ListNode plusOneRec(ListNode head) {
        if (head == null) {
            return new ListNode(1);
        }

        ListNode node = plusOneRec(head.next);
        node.val += head.val;
        ListNode carryNode = new ListNode(node.val / 10);
        node.val %= 10;

        carryNode.next = node;
        return carryNode;
    }

    /*
     * First non-repeating character in a stream of characters
     * Amazon interview question.
     * https://www.interviewbit.com/problems/first-non-repeating-character-in-a-stream-of-characters/
     * Explanation: Tech Dose
     * */
    public String solve(String A) {
        DoublyNode head = new DoublyNode('#');
        DoublyNode tail = head;

        DoublyNode[] nodes = new DoublyNode[26];
        boolean[] repeated = new boolean[26];

        StringBuilder res = new StringBuilder();
        for (int i = 0; i < A.length(); i++) {
            char ch = A.charAt(i);
            if (nodes[ch - 'a'] == null && !repeated[ch - 'a']) {
                DoublyNode newNode = new DoublyNode(ch);
                tail.next = newNode;
                newNode.prev = tail;
                tail = newNode;

                nodes[ch - 'a'] = newNode;
            } else if (nodes[ch - 'a'] != null) {
                DoublyNode node = nodes[ch - 'a'];
                DoublyNode prevNode = node.prev;
                prevNode.next = node.next;
                if (node.next != null) {
                    node.next.prev = prevNode;
                } else {
                    tail = prevNode;
                }

                nodes[ch - 'a'] = null;
                repeated[ch - 'a'] = true;
            }

            if (head.next != null) {
                res.append(head.next.ch);
            } else {
                res.append('#');
            }
        }

        return res.toString();
    }

}
