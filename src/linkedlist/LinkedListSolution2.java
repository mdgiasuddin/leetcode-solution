package linkedlist;

public class LinkedListSolution2 {
    public static void main(String[] args) {

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
        tEven.next = null;

        return oddHead.next;
    }
}
