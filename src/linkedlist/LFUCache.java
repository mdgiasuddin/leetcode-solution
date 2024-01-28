package linkedlist;

import java.util.HashMap;
import java.util.Map;

// Leetcode problem: 460
/*
 * LFU Cache.
 * Explanation: https://www.youtube.com/watch?v=0PSB9y8ehbk
 * This problem is the extension of LRU Cache (Leetcode problem: 146).
 * Maintain minFreq that contains the minimum frequency of access of all the present keys.
 * For each frequency, maintain a Doubly link list (LRU Cache).
 * For put or get update the node. Increase the frequency & move the node to the next frequency list.
 * When capacity is full, delete the last node from minFreq list.
 * */
class DLNode {
    public int key;
    public int value;
    public int freq;
    public DLNode prev;
    public DLNode next;

    public DLNode(int key, int value) {
        this.key = key;
        this.value = value;
        this.freq = 1;
        this.prev = null;
        this.next = null;
    }
}

class DLList {
    DLNode head;
    DLNode tail;

    public DLList() {
        head = new DLNode(0, 0);
        tail = new DLNode(0, 0);
        head.next = tail;
        tail.prev = head;
    }

    public void insertNode(DLNode node) {
        node.next = head.next;
        node.prev = head;
        head.next = node;
        node.next.prev = node;
    }

    public void deleteNode(DLNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public DLNode deleteLast() {
        DLNode node = tail.prev;
        deleteNode(node);
        return node;
    }
}

public class LFUCache {
    int curSize;
    int capacity;
    int minFreq;
    Map<Integer, DLNode> map;
    Map<Integer, DLList> freqMap;

    public LFUCache(int capacity) {
        this.curSize = 0;
        this.capacity = capacity;
        this.minFreq = 0;
        map = new HashMap<>();
        freqMap = new HashMap<>();
    }

    public int get(int key) {
        DLNode node = map.get(key);
        if (node == null) {
            return -1;
        }

        updateNode(node);

        return node.value;
    }

    public void put(int key, int value) {
        DLNode node = map.get(key);
        if (node == null) {
            if (curSize == capacity) {
                DLList list = freqMap.get(minFreq);
                DLNode delNode = list.deleteLast();
                map.remove(delNode.key);
                curSize -= 1;
            }

            node = new DLNode(key, value);
            map.put(key, node);
            minFreq = 1;
            curSize += 1;
            DLList list = freqMap.getOrDefault(1, new DLList());
            list.insertNode(node);
            freqMap.put(1, list);
            return;
        }

        node.value = value;
        updateNode(node);

    }

    private void updateNode(DLNode node) {
        int freq = node.freq;
        DLList list = freqMap.get(freq);

        list.deleteNode(node);
        node.freq += 1;
        DLList newList = freqMap.getOrDefault(node.freq, new DLList());
        newList.insertNode(node);
        freqMap.put(node.freq, newList);
        DLList prevList = freqMap.get(freq);

        // After moving the node to next freq, it may become empty.
        // If it was minFreq previously, update the minFreq.
        if (freq == minFreq && prevList.head.next == prevList.tail) {
            minFreq += 1;
        }
    }
}
