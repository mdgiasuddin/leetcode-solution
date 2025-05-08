package linkedlist;

public class LinkedListSolution3 {


    // Leetcode problem: 725

    /**
     * Split Linked List in Parts
     * */
    public ListNode[] splitListToParts(ListNode head, int k) {
        ListNode[] res = new ListNode[k];
        if (k == 1) {
            res[0] = head;
            return res;
        }

        ListNode current = head;
        int len = 0;
        while (current != null) {
            current = current.next;
            len += 1;
        }

        int partLen = len / k;
        int extraNodes = len % k;

        current = head;
        ListNode prev = head;
        int resIdx = 0;
        while (current != null) {
            res[resIdx] = current;
            for (int i = 0; i < partLen && current != null; i++) {
                prev = current;
                current = current.next;
            }

            if (extraNodes > 0) {
                prev = current;
                current = current.next;
                extraNodes -= 1;
            }
            prev.next = null;
            resIdx += 1;
        }

        return res;
    }

    // Leetcode problem: 430

    /**
     * Flatten a Multilevel Linked List.
     * Explanation: https://www.youtube.com/watch?v=BVDbZzhJk2E
     * */
    public Node flatten(Node head) {
        Node current = head;
        while (current != null) {

            // If there is no child then continue.
            if (current.child == null) {
                current = current.next;
                continue;
            }

            Node tmp = current.child;
            // There is a child. find the tail node of the child.
            while (tmp.next != null) {
                tmp = tmp.next;
            }

            // Join the tail with current.next;
            tmp.next = current.next;
            if (current.next != null) {
                current.next.prev = tmp;
            }

            // Update the next of current to tmp;
            current.next = current.child;
            current.child.prev = current;

            // Remove connection with child.
            current.child = null;

            // Modify current to next for the next iteration.
            current = current.next;
        }

        return head;
    }

    class Node {
        public int val;
        public Node prev;
        public Node next;
        public Node child;
    }
}
