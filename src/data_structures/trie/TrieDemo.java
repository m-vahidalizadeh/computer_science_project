package data_structures.trie;

import java.util.List;

public class TrieDemo {
    public static void main(String[] args) {
        Trie root = new Trie();
        Trie a = new Trie("a", false);
        Trie n = new Trie("n", false);
        Trie y = new Trie("y", true);
        Trie a2 = new Trie("a", true);
        Trie b = new Trie("b", false);
        Trie y2 = new Trie("y", false);
        Trie e = new Trie("e", true);
        root.addChild(a);
        root.addChild(b);
        a.addChild(n);
        n.addChild(y);
        n.addChild(a2);
        b.addChild(y2);
        y2.addChild(e);
        List<String> result = root.getWords();
        printList(result);
    }

    private static void printList(List<String> input) {
        for (String i : input) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
