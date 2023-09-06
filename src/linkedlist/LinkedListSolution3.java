package linkedlist;

public class LinkedListSolution3 {


    // Leetcode problem: 725
    /*
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
}
