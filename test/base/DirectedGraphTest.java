package base;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DirectedGraphTest {
    DirectedGraph dg;
    Vertex a;
    Vertex b;
    Vertex c;
    Vertex d;
    Vertex e;

    @BeforeEach
    public void initialize() {
        dg = new DirectedGraph();
        a = new Vertex("a");
        b = new Vertex("b");
        c = new Vertex("c");
        d = new Vertex("d");
        e = new Vertex("e");
        dg.addVertex(a);
        dg.addVertex(b);
        dg.addVertex(c);
        dg.addVertex(d);
        dg.addVertex(e);
        dg.addEdge(a, b);
        dg.addEdge(b, e);
        dg.addEdge(a, c);
        dg.addEdge(e, c);
        dg.addEdge(c, d);
    }

    @Test
    public void testDFS() {
        Assertions.assertEquals(List.of(a, c, d, b, e), dg.dfs(a));
    }

    @Test
    public void testBFS() {
        Assertions.assertEquals(List.of(a, b, c, e, d), dg.bfs(a));
    }
}
