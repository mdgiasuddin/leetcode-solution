package linkedlist;

import java.util.HashMap;
import java.util.Map;

class Node {
    public int key, val;
    public Node prev, next;

    public Node(int key, int val) {
        this.key = key;
        this.val = val;
        this.prev = this.next = null;
    }
}

public class LRUCache {

    // Leetcode problem: 146
    private final int CAPACITY;
    private final Node LEFT, RIGHT;
    private Map<Integer, Node> cache;

    public LRUCache(int capacity) {
        CAPACITY = capacity;
        cache = new HashMap<>();

        LEFT = new Node(0, 0);
        RIGHT = new Node(0, 0);
        LEFT.next = RIGHT;
        RIGHT.prev = LEFT;
    }

    // Insert at the rightmost (recent) position.
    private void insert(Node node) {
        Node prev = RIGHT.prev;

        node.prev = prev;
        node.next = RIGHT;
        prev.next = node;
        RIGHT.prev = node;

    }

    private void remove(Node node) {
        Node prev = node.prev, next = node.next;

        prev.next = next;
        next.prev = prev;
    }

    public int get(int key) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);

            // Remove from the list and insert it to the recent position.
            remove(node);
            insert(node);
            return node.val;
        }

        return -1;
    }

    public void put(int key, int value) {

        // If already exists, remove it and insert to the recent position.
        if (cache.containsKey(key))
            remove(cache.get(key));

        Node node = new Node(key, value);
        cache.put(key, node);

        insert(node);

        // If size exceeds the capacity remove the LRU (leftmost) from both the cache and the list.
        if (cache.size() > CAPACITY) {
            Node lru = LEFT.next;
            remove(lru);
            cache.remove(lru.key);
        }
    }
}
