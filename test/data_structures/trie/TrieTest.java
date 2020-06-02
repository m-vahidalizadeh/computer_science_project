package data_structures.trie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TrieTest {

    Trie root;
    Trie a;
    Trie n;
    Trie y;
    Trie a2;
    Trie b;
    Trie y2;
    Trie e;

    @BeforeEach
    public void initialize() {
        root = new Trie();
        a = new Trie("a", false);
        n = new Trie("n", false);
        y = new Trie("y", true);
        a2 = new Trie("a", true);
        b = new Trie("b", false);
        y2 = new Trie("y", false);
        e = new Trie("e", true);
        root.addChild(a);
        root.addChild(b);
        a.addChild(n);
        n.addChild(y);
        n.addChild(a2);
        b.addChild(y2);
        y2.addChild(e);
    }

    @Test
    public void testGetWords() {
        Assertions.assertEquals(List.of("any", "ana", "bye"), root.getWords());
    }

}
