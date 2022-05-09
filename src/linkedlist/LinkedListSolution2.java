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

}
