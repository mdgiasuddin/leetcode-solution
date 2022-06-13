package trie;

import java.util.HashMap;
import java.util.Map;

class DictTrieNode {
    Map<Character, DictTrieNode> children;
    boolean word;

    DictTrieNode() {
        children = new HashMap<>();
        word = false;
    }
}

public class WordDictionary {

    // Leetcode problem: 211

    DictTrieNode root;

    public WordDictionary() {
        root = new DictTrieNode();
    }

    public void addWord(String word) {
        DictTrieNode current = root;

        for (char ch : word.toCharArray()) {
            if (!current.children.containsKey(ch))
                current.children.put(ch, new DictTrieNode());

            current = current.children.get(ch);
        }

        current.word = true;
    }

    private boolean dfs(int idx, DictTrieNode node, String word) {
        if (idx == word.length())
            return node.word;

        char ch = word.charAt(idx);
        if (ch == '.') {
            for (Map.Entry<Character, DictTrieNode> entry : node.children.entrySet()) {
                if (dfs(idx + 1, entry.getValue(), word))
                    return true;
            }

            return false;
        } else {
            if (!node.children.containsKey(ch))
                return false;

            return dfs(idx + 1, node.children.get(ch), word);
        }
    }

    public boolean search(String word) {
        return dfs(0, root, word);
    }
}

