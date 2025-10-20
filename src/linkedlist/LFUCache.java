package linkedlist;

import java.util.HashMap;
import java.util.Map;

// Leetcode problem: 460

/**
 * LFU Cache.
 * Explanation: https://www.youtube.com/watch?v=0PSB9y8ehbk
 * This problem is the extension of LRU Cache (Leetcode problem: 146).
 * Maintain minFreq that contains the minimum frequency of access of all the present keys.
 * For each frequency, maintain a Doubly link list (LRU Cache).
 * For put or get update the node. Increase the frequency and move the node to the next frequency list.
 * When capacity is full, delete the last node from the minFreq list.
 */
class LFUNode {
    int key;
    int value;
    int freq;
    LFUNode prev;
    LFUNode next;

    public LFUNode(int key, int value) {
        this.key = key;
        this.value = value;
        this.freq = 1;
        this.prev = this.next = null;
    }
}

class LFUList {
    LFUNode head;
    LFUNode tail;

    public LFUList() {
        head = new LFUNode(0, 0);
        tail = new LFUNode(0, 0);
        head.next = tail;
        tail.prev = head;
    }

    public void insert(LFUNode node) {
        node.next = tail;
        node.prev = tail.prev;
        tail.prev = node;
        node.prev.next = node;
    }

    public void remove(LFUNode node) {
        LFUNode prev = node.prev;
        prev.next = node.next;
        node.next.prev = prev;
    }

    public LFUNode removeFirst() {
        LFUNode node = head.next;
        head.next = node.next;
        head.next.prev = head;
        return node;
    }
}

class LFUCache {

    int capacity;
    int currentSize;
    int minFreq;
    Map<Integer, LFUNode> map;
    Map<Integer, LFUList> freqMap;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.currentSize = 0;
        this.minFreq = 0;
        this.map = new HashMap<>();
        this.freqMap = new HashMap<>();
    }

    public int get(int key) {
        LFUNode node = map.get(key);
        if (node == null) {
            return -1;
        }

        updateNode(node);
        return node.value;
    }

    public void put(int key, int value) {
        LFUNode node = map.get(key);

        if (node == null) {
            if (currentSize == capacity) {
                LFUList list = freqMap.get(minFreq);
                LFUNode delNode = list.removeFirst();
                map.remove(delNode.key);
                currentSize -= 1;
            }

            node = new LFUNode(key, value);
            minFreq = 1;
            currentSize += 1;
            LFUList list = freqMap.getOrDefault(minFreq, new LFUList());
            list.insert(node);
            freqMap.put(minFreq, list);
            map.put(key, node);

            return;
        }

        node.value = value;
        updateNode(node);
    }

    private void updateNode(LFUNode node) {
        LFUList list = freqMap.get(node.freq);
        list.remove(node);
        if (node.freq == minFreq && list.head.next == list.tail) {
            minFreq += 1;
        }

        node.freq += 1;
        list = freqMap.getOrDefault(node.freq, new LFUList());
        list.insert(node);
        freqMap.put(node.freq, list);
    }
}

