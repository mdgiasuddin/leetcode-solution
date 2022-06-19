package stack;

import java.util.LinkedList;
import java.util.Queue;

public class MyStack {

    // Leetcode problem: 225
    Queue<Integer> queue;

    public MyStack() {
        queue = new LinkedList<>();
    }

    public void push(int x) {
        queue.add(x);
    }

    public int pop() {
        for (int i = 0; i < queue.size() - 1; i++)
            this.push(queue.poll());

        return queue.poll();
    }

    public int top() {
        for (int i = 0; i < queue.size() - 1; i++)
            this.push(queue.poll());

        int res = queue.poll();
        this.push(res);

        return res;
    }

    public boolean empty() {
        return queue.isEmpty();
    }
}
