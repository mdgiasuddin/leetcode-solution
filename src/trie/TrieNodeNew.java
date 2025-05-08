package trie;

import java.util.HashMap;
import java.util.Map;

public class TrieNodeNew {
    public Map<Character, TrieNodeNew> children;
    public boolean endNode;

    public TrieNodeNew() {
        children = new HashMap<>();
        endNode = false;
    }

    public void addWord(String word) {
        TrieNodeNew current = this;

        for (char ch : word.toCharArray()) {
            if (!current.children.containsKey(ch)) {
                current.children.put(ch, new TrieNodeNew());
            }

            current = current.children.get(ch);
        }

        current.endNode = true;

    }

    /**
     * Search word with wildcard matching.
     * Explanation: https://www.youtube.com/watch?v=h-F2jRUzpBo&list=PLEJXowNB4kPxbFzYz06LrPQpsMxYeKxiB&index=3
     * */
    public boolean searchWord(TrieNodeNew node, String word, int pos) {
        if (word.charAt(pos) == '.') {
            for (TrieNodeNew child : node.children.values()) {
                if (pos == word.length() - 1) {
                    if (child.endNode) {
                        return true;
                    }
                } else {
                    if (searchWord(child, word, pos + 1)) {
                        return true;
                    }
                }
            }
            return false;
        } else {
            TrieNodeNew child = node.children.get(word.charAt(pos));
            if (child == null) {
                return false;
            }

            if (pos == word.length() - 1) {
                return child.endNode;
            }

            return searchWord(child, word, pos + 1);
        }
    }
}

class TrieNew {
    public static void main(String[] args) {
        TrieNodeNew root = new TrieNodeNew();
        root.addWord("abc");
        root.addWord("ded");

        System.out.println(root.searchWord(root, "..d", 0));
    }
}
