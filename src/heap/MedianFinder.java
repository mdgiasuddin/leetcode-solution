package heap;

import java.util.PriorityQueue;

class MedianFinder {

    // Leetcode problem: 295
    PriorityQueue<Integer> leftHeap;
    PriorityQueue<Integer> rightHeap;

    public MedianFinder() {
        // Left heap is max heap, right heap is min heap.
        leftHeap = new PriorityQueue<>((a, b) -> b - a);
        rightHeap = new PriorityQueue<>();
    }

    public void addNum(int num) {

        // Always add into the left heap.
        leftHeap.add(num);

        // If left heap become more larger, transfer element to right heap.
        if (leftHeap.size() - rightHeap.size() > 1) {
            rightHeap.add(leftHeap.poll());
        }

        // If new element is larger than minimum of right heap, exchange two heap.
        if (!rightHeap.isEmpty() && rightHeap.peek() < leftHeap.peek()) {
            leftHeap.add(rightHeap.poll());
            rightHeap.add(leftHeap.poll());
        }
    }

    public double findMedian() {

        // Odd number of elements.
        if (leftHeap.size() > rightHeap.size()) {
            return leftHeap.peek();
        }

        // Even number of elements.
        return (leftHeap.peek() + rightHeap.peek()) / 2.0;
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
