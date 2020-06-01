package base;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UndirectedGraphTest {

    UndirectedGraph ug;
    Vertex a = new Vertex("a");
    Vertex b = new Vertex("b");
    Vertex c = new Vertex("c");
    Vertex d = new Vertex("d");
    Vertex e = new Vertex("e");

    @BeforeEach
    public void initialize() {
        ug = new UndirectedGraph();
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
