package final_project;

import java.util.ArrayList;

public class Tries {
    static Trie_node root;

    public Tries(){
        root = new Trie_node();
    }

    public void insert(String word) {
        Trie_node curr = root;
        word = word.toLowerCase();
        for (char ch : word.toCharArray()) {
            int index = ch - 'a';
            Trie_node node = curr.children[index];
            if (node == null) {
                node = new Trie_node();
                curr.children[index] = node;
            }
            curr = node;
        }
        curr.isWord = true;
    }

    public boolean find(String key) {
        Trie_node curr = root;
        key = key.toLowerCase();
        for (char ch : key.toCharArray()) {
            int index = ch - 'a';
            if (curr.children[index] == null)
                return false;
            curr = curr.children[index];
        }
        return curr.isWord;
    }

    public ArrayList<String> findWords(String prefix) {
        Trie_node curr = root;
        ArrayList<String> words = new ArrayList<>();
        for (char ch : prefix.toCharArray()) {
            int index = ch - 'a';
            if (curr.children[index] == null) {
                return words;
            }
            curr = curr.children[index];
        }
        collectWords(curr, prefix, words);
        return words;
    }

    private void collectWords(Trie_node node, String prefix, ArrayList<String> words) {
        if (node.isWord) {
            words.add(prefix);
        }
        for (char ch = 'a'; ch <= 'z'; ch++) {
            int index = ch - 'a';
            if (node.children[index] != null) {
                collectWords(node.children[index], prefix + ch, words);
            }
        }
    }

}

 class Trie_node {
    static Trie_node root = new Trie_node();

    Trie_node[] children = new Trie_node[26];
    boolean isWord;

    public Trie_node() {
        isWord = false;
        for (int i = 0; i < children.length; i++) {
            children[i] = null;
        }
    }
}