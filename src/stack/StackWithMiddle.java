package stack;

class StackNode {
    public int val;
    public StackNode prev;
    public StackNode next;

    public StackNode(int val) {
        this.val = val;
    }
}

public class StackWithMiddle {
    int size;
    public StackNode LEFT;
    public StackNode RIGHT;
    public StackNode MIDDLE;

    public StackWithMiddle() {
        this.size = 0;
        this.LEFT = new StackNode(0);
        this.RIGHT = new StackNode(0);
        this.MIDDLE = null;
        LEFT.next = RIGHT;
        RIGHT.prev = LEFT;
    }

    public void push(int val) {
        StackNode newNode = new StackNode(val);
        this.size += 1;

        StackNode prev = RIGHT.prev;
        prev.next = newNode;
        newNode.prev = prev;
        newNode.next = RIGHT;
        RIGHT.prev = newNode;

        if (this.size == 1) { // First insert.
            this.MIDDLE = newNode;
        } else if (this.size % 2 == 0) { // Number of nodes becomes even, middle will be shifted.
            this.MIDDLE = this.MIDDLE.next;
        }
    }

    public int pop() {
        if (this.size == 0) {
            return -1;
        }
        this.size -= 1;

        StackNode popNode = this.RIGHT.prev;
        StackNode prevNode = popNode.prev;

        prevNode.next = this.RIGHT;
        this.RIGHT.prev = prevNode;

        if (this.size == 0) { // Pop the last element
            this.MIDDLE = null;
        } else if (this.size % 2 == 1) {
            this.MIDDLE = MIDDLE.prev;
        }
        return prevNode.val;
    }

    public int deleteMiddle() {
        if (this.size == 0) {
            return -1;
        }
        this.size -= 1;

        int val = this.MIDDLE.val;
        StackNode next = this.MIDDLE.next;
        StackNode prev = this.MIDDLE.prev;

        prev.next = next;
        next.prev = prev;

        if (this.size == 0) {
            this.MIDDLE = null;
        } else if (this.size % 2 == 0) {
            this.MIDDLE = next;
        } else {
            this.MIDDLE = prev;
        }

        return val;
    }

    public int getMiddle() {
        if (this.size == 0) {
            return -1;
        }

        return this.MIDDLE.val;
    }

    public static void main(String[] args) {
        StackWithMiddle stackWithMiddle = new StackWithMiddle();
    }
}
