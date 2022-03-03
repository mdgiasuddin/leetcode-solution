package trie;

public class TrieNode {
    public boolean endNode;
    public TrieNode[] children;

    public TrieNode() {
        this.endNode = false;
        this.children = new TrieNode[26]; // Initialized by null
    }
}

class Trie {
    private final TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode node = root;
        for (int level = 0; level < word.length(); level++) {
            int index = word.charAt(level) - 'a';

            if (node.children[index] == null) {
                node.children[index] = new TrieNode();
            }
            node = node.children[index];
        }
        node.endNode = true;
    }

    public boolean search(String word) {
        TrieNode node = root;

        for (int level = 0; level < word.length(); level++) {
            int index = word.charAt(level) - 'a';
            if (node.children[index] == null) {
                return false;
            }
            node = node.children[index];
        }

        return node.endNode;
    }

    public boolean startsWith(String prefix) {
        TrieNode node = root;

        for (int level = 0; level < prefix.length(); level++) {
            int index = prefix.charAt(level) - 'a';
            if (node.children[index] == null) {
                return false;
            }
            node = node.children[index];
        }

        return true;
    }
}

class TrieDemo {

    public static void main(String[] args) {
        Trie trie = new Trie();

        trie.insert("abcd");
        trie.insert("bcf");
        trie.insert("cdagk");

        System.out.println("abcd: " + trie.search("abcd"));
        System.out.println("bcf: " + trie.search("bcf"));
        System.out.println("cdagk: " + trie.search("cdagk"));
        System.out.println("ghi: " + trie.search("ghi"));
        System.out.println("mni: " + trie.search("mni"));
    }
}
