package trie;

import java.util.ArrayList;
import java.util.List;

class TrieNodeAuto {
    boolean endOfWord;
    TrieNodeAuto[] children;

    TrieNodeAuto() {
        endOfWord = false;
        children = new TrieNodeAuto[26];
    }
}

public class TrieAutoComplete {

    public void insertWord(TrieNodeAuto root, String word) {
        TrieNodeAuto current = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (current.children[ch - 'a'] == null) {
                current.children[ch - 'a'] = new TrieNodeAuto();
            }

            current = current.children[ch - 'a'];
        }
        current.endOfWord = true;
    }

    public List<String> autoSearch(TrieNodeAuto root, String query, int size) {
        List<String> searchResult = new ArrayList<>();
        TrieNodeAuto current = root;

        for (int i = 0; i < query.length(); i++) {
            char ch = query.charAt(i);
            if (current.children[ch - 'a'] == null) {
                return searchResult;
            }

            current = current.children[ch - 'a'];
        }
        autoSearchRec(current, query, searchResult, size);
        return searchResult;
    }

    private void autoSearchRec(TrieNodeAuto node, String prefix, List<String> searchResult, int size) {
        if (node.endOfWord) {
            searchResult.add(prefix);
        }

        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null && searchResult.size() < size) {
                autoSearchRec(node.children[i], prefix + (char) ('a' + i), searchResult, size);
            }
        }
    }

    public static void main(String[] args) {
        TrieAutoComplete trie = new TrieAutoComplete();
        TrieNodeAuto root = new TrieNodeAuto();

        trie.insertWord(root, "abcd");
        trie.insertWord(root, "abcef");
        trie.insertWord(root, "acdef");
        trie.insertWord(root, "abxyz");
        trie.insertWord(root, "bcdyz");
        trie.insertWord(root, "gcdyz");

        System.out.println(trie.autoSearch(root, "a", 3));
    }

}
