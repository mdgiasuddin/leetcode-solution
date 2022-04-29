package trie;

import java.util.HashMap;
import java.util.Map;

public class TrieNodeNew {
    public Map<Character, TrieNodeNew> children = new HashMap<>();
    public boolean endNode = false;

    public void addWord(String word) {
        TrieNodeNew current = this;

        for (char ch : word.toCharArray()) {
            if (!current.children.containsKey(ch)) {
                current.children.put(ch, new TrieNodeNew());
            }

            current = current.children.get(ch);
        }

        current.endNode = true;

        System.out.println("word: " + word + this.children.keySet());
    }
}
