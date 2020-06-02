package data_structures.trie;

import java.util.ArrayList;
import java.util.List;

public class Trie {
    public List<Trie> children;
    boolean endNode;
    String label;

    public Trie() {
        children = new ArrayList<>();
        this.label = "";
        this.endNode = false;
    }

    public Trie(String label, boolean endNode) {
        children = new ArrayList<>();
        this.label = label;
        this.endNode = endNode;
    }

    public void addChild(Trie c) {
        children.add(c);
    }

    public List<String> getWords() {
        List<String> result = new ArrayList<>();
        findWords(this, "", result);
        return result;
    }

    private void findWords(Trie t, String word, List<String> result) {
        String newWord = word + t.label;
        if (t.endNode) {
            result.add(newWord);
        } else {
            for (int i = 0; i < t.children.size(); i++) {
                findWords(t.children.get(i), newWord, result);
            }
        }
    }

}
