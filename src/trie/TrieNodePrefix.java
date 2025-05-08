package trie;

import java.util.HashMap;
import java.util.Map;

/**
 * Shortest Unique prefix for every word
 * Amazon interview question.
 * Code source: https://www.geeksforgeeks.org/find-all-shortest-unique-prefixes-to-represent-each-word-in-a-given-list/
 * */
class TrieNodePrefix {

    public int freq;
    public Map<Character, TrieNodePrefix> children;

    public TrieNodePrefix() {
        freq = 0;
        children = new HashMap<>();
    }

    public void insertWord(String word) {
        TrieNodePrefix current = this;

        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);

            if (!current.children.containsKey(ch)) {
                current.children.put(ch, new TrieNodePrefix());
            }

            current = current.children.get(ch);
            current.freq += 1;
        }
    }

    public String searchPrefix(String word) {
        StringBuilder prefix = new StringBuilder();
        TrieNodePrefix current = this;

        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            current = current.children.get(ch);

            prefix.append(ch);

            if (current.freq == 1) {
                break;
            }
        }

        return prefix.toString();
    }
}
