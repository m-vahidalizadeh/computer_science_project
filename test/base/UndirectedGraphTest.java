package base;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class UndirectedGraphTest {

    UndirectedGraph ug;
    Vertex a;
    Vertex b;
    Vertex c;
    Vertex d;
    Vertex e;

    @BeforeEach
    public void initialize() {
        ug = new UndirectedGraph();
        a = new Vertex("a");
        b = new Vertex("b");
        c = new Vertex("c");
        d = new Vertex("d");
        e = new Vertex("e");
        ug.addVertex(a);
        ug.addVertex(b);
        ug.addVertex(c);
        ug.addVertex(d);
        ug.addVertex(e);
        ug.addEdge(a, b);
        ug.addEdge(a, d);
        ug.addEdge(b, c);
        ug.addEdge(d, c);
        ug.addEdge(c, e);
    }

    @Test
    public void testBFS() {
        Assertions.assertEquals(List.of(a, b, d, c, e), ug.bfs(a));
    }

    @Test
    public void testDFS() {
        Assertions.assertEquals(List.of(a, d, c, e, b), ug.dfs(a));
    }

}
