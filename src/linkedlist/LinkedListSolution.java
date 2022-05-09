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

    // Leetcode problem: 23
    /*
     * Merge adjacent two lists until become a single list.
     * */
    public ListNode mergeKLists(ListNode[] lists) {

        if (lists.length == 0) {
            return null;
        }

        int currentLength = lists.length;
        while (currentLength > 1) {
            for (int i = 0; i < currentLength; i += 2) {
                ListNode list1 = lists[i];
                ListNode list2 = i + 1 < currentLength ? lists[i + 1] : null;

                lists[i / 2] = mergeTwoLists(list1, list2);
            }

            // Next time process half of the lists.
            currentLength = (currentLength + 1) / 2;

        }

        return lists[0];
    }

    // Leetcode problem: 21
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

    // Leetcode problem: 2
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode();
        ListNode current = dummy;

        int carry = 0;
        while (l1 != null || l2 != null || carry != 0) {
            int v1 = l1 == null ? 0 : l1.val;
            int v2 = l2 == null ? 0 : l2.val;

            int val = (v1 + v2 + carry);
            carry = val / 10;
            val %= 10;

            current.next = new ListNode(val);
            current = current.next;
            l1 = l1 == null ? null : l1.next;
            l2 = l2 == null ? null : l2.next;
        }

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

    // Leetcode problem: 234
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null)
            return true;

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

        // Check palindrome. Second half has <= nodes than first half.
        second = prev;
        while (second != null) {
            if (second.val != head.val)
                return false;

            second = second.next;
            head = head.next;
        }

        return true;
    }
}
