package linkedlist;

class DoublyListNode {
    int val;
    DoublyListNode next, prev;

    DoublyListNode(int val) {
        this.val = val;
    }
}

public class MyCircularQueue {

    DoublyListNode LEFT, RIGHT;
    int capacity, k;

    public MyCircularQueue(int k) {
        LEFT = new DoublyListNode(0);
        RIGHT = new DoublyListNode(0);

        LEFT.next = RIGHT;
        RIGHT.prev = LEFT;
        this.k = k;
        capacity = k;
    }

    public boolean enQueue(int value) {
        if (capacity == 0)
            return false;

        DoublyListNode node = new DoublyListNode(value);
        DoublyListNode prev = RIGHT.prev;
        prev.next = node;
        node.prev = prev;
        node.next = RIGHT;
        RIGHT.prev = node;

        capacity--;
        return true;
    }

    public boolean deQueue() {
        if (capacity == k)
            return false;

        LEFT.next = LEFT.next.next;
        LEFT.next.prev = LEFT;

        capacity++;
        return true;
    }

    public int Front() {
        if (capacity == k)
            return -1;

        return LEFT.next.val;
    }

    public int Rear() {
        if (capacity == k)
            return -1;

        return RIGHT.prev.val;
    }

    public boolean isEmpty() {
        return capacity == k;
    }

    public boolean isFull() {
        return capacity == 0;
    }
}
