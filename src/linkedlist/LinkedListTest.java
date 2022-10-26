package linkedlist;

import java.util.Arrays;
import java.util.List;

public class LinkedListTest {

    public static void main(String[] args) {
        LinkedListTest test = new LinkedListTest();
        LinkedListSolution2 solution2 = new LinkedListSolution2();

        ListNode head1 = test.createList(Arrays.asList(1, 2, 3, 4));
        ListNode head2 = test.createList(Arrays.asList(1, 9, 9));

        int len1 = solution2.getLength(head1);
        int len2 = solution2.getLength(head2);

        head2 = solution2.fillWithZero(head2, len1 - len2);

//        ListNode result = test.minusList(head1, head2);

        test.sortListWithAbsoluteValueSorted();
    }

    public ListNode minusList(ListNode head1, ListNode head2) {
        if (head1 == null) {
            return new ListNode(0);
        }

        ListNode prevNode = minusList(head1.next, head2.next);
        int sum = head1.val - head2.val + prevNode.val;
        prevNode.val = (sum + 10) % 10;

        ListNode carryNode = new ListNode((sum + 10) / 10 - 1);
        carryNode.next = prevNode;

        return carryNode;
    }

    public ListNode createList(List<Integer> list) {
        ListNode dummyHead = new ListNode(0);

        ListNode current = dummyHead;
        for (int num : list) {
            current.next = new ListNode(num);
            current = current.next;
        }

        return dummyHead.next;
    }

    public void printList(ListNode head) {
        ListNode current = head;

        while (current != null) {
            System.out.print(current.val + " -> ");
            current = current.next;
        }
    }

    /*
     * Merge 2 lists alternatively. 1->3->5->7 & 2->4->6 => 1->2->3->4->5->6->7
     * Amazon interview question.
     * */
    public void mergeLinkedList() {
        ListNode head1 = createList(Arrays.asList(1, 3, 5, 7, 9, 11));
        ListNode head2 = createList(Arrays.asList(2, 4, 6));

        ListNode first = head1;
        ListNode second = head2;

        while (first != null && second != null) {
            ListNode fNext = first.next;
            ListNode sNext = second.next;

            first.next = second;
            second.next = fNext;

            first = fNext;
            second = sNext;
        }

        printList(head1);
    }

    /*
     * Sort linked list which is already sorted by absolute value.
     * Amazon interview question.
     * For every negative value send it to the head position.
     * */
    public void sortListWithAbsoluteValueSorted() {
        ListNode head = createList(Arrays.asList(1, -2, -3, -4, 5, -6, 7, -8, 9));

        ListNode current = head.next;
        ListNode prev = head;
        while (current != null) {
            if (current.val < 0) {
                prev.next = current.next;
                current.next = head;
                head = current;
                current = prev.next;
            } else {
                prev = current;
                current = current.next;
            }
        }

        printList(head);
    }
}
