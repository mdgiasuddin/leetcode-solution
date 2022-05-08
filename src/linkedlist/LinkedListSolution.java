package linkedlist;

public class LinkedListSolution {

    public static void main(String[] args) {

    }

    // Leetcode problem: 876
    public ListNode middleNode(ListNode head) {
        if (head.next == null)
            return head;

        ListNode slow = head, fast = head.next;

        /*
         * For even number of nodes, this function will return the second node of the 2 middles.
         * For getting the first node of the 2 middles, loop will be fast != null && fast.next != null
         * and return slow instead of slow.next.
         * */
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow.next;
    }

    // Leetcode problem: 2095
    public ListNode deleteMiddle(ListNode head) {
        if (head.next == null)
            return null;

        ListNode slow = head, fast = head.next;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        slow.next = slow.next.next;

        return head;
    }

    // Leetcode problem: 206
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;

        while (head != null) {
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }

        return prev;
    }

    /*
     * Reverse linked list. Recursive solution
     * */
    public ListNode reverseListRec(ListNode head) {
        if (head == null || head.next == null)
            return head;

        ListNode rev = reverseListRec(head.next);

        // Created a loop.
        head.next.next = head;

        // Release the loop.
        head.next = null;

        return rev;
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {

        // Take a dummy node at the head.
        ListNode dummy = new ListNode();

        ListNode tail = dummy;

        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                tail.next = list1;
                list1 = list1.next;
            } else {
                tail.next = list2;
                list2 = list2.next;
            }
            tail = tail.next;
        }

        if (list1 == null) {
            tail.next = list2;
        } else {
            tail.next = list1;
        }

        // Return from next position to dummy node.
        return dummy.next;
    }

    // Leetcode problem: 203
    public ListNode removeElements(ListNode head, int val) {

        // Delete the val in the head.
        while (head != null && head.val == val)
            head = head.next;

        ListNode current = head, prev = head;
        while (current != null) {
            if (current.val == val) {
                // Delete the node.
                prev.next = current.next;
            } else {
                // Move the prev pointer.
                prev = current;
            }
            current = current.next;
        }

        return head;
    }

    // Leetcode problem: 143
    /*
     * Divide the list into two parts. Reverse the right part. Then merge them.
     * */
    public void reorderList(ListNode head) {

        // Find the middle.
        ListNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode second = slow.next;
        slow.next = null;

        // Reverse the second half.
        ListNode prev = null;
        while (second != null) {
            ListNode next = second.next;
            second.next = prev;
            prev = second;
            second = next;
        }

        second = prev;
        ListNode first = head;

        // The number of nodes of second part <= fist part. So loop until second part.
        while (second != null) {
            ListNode next1 = first.next;
            first.next = second;
            ListNode next2 = second.next;
            second.next = next1;
            first = next1;
            second = next2;
        }
    }
}
