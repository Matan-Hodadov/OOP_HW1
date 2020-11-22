package ex1.tests;

import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest {
    private static Random _rnd = null;

    @Test
    void nodeSize() {
        weighted_graph g = new WGraph_DS();
        for (int i = 0; i < 4; i++) {
            g.addNode(i);
        }
        for (int i = 0; i < 3; i++) {
            g.removeNode(i);
        }
        g.addNode(3);
        int s = g.nodeSize();
        assertEquals(1,s);
        g.removeNode(2);
        assertEquals(1,s);

    }

    @Test
    void edgeSize() {
        weighted_graph g = new WGraph_DS();
        for (int i = 0; i < 4; i++) {
            g.addNode(i);
        }
        for (int i = 0; i < 4; i++) {
            g.connect(0, i, i);
        }
        g.connect(0,1,1);
        int e_size =  g.edgeSize();
        assertEquals(3, e_size);

        double w23 = g.getEdge(2,3);
        assertEquals(w23, -1);
        double w25 = g.getEdge(2,5);
        assertEquals(w25, -1);

        g.connect(2,3,-5);
        assertEquals(g.getEdge(2,3), -5);

        g.connect(2,2,-5);
        assertEquals(g.getEdge(2,2), 0);
    }

    @Test
    void getV() {
        weighted_graph g = new WGraph_DS();
        for (int i = 0; i < 4; i++) {
            g.addNode(i);
        }
        for (int i = 0; i < 4; i++) {
            g.connect(0, i, i);
        }
        g.connect(0,1,1);
        g.connect(0, -1, 5);
        g.connect(-1, -4, 0);
        g.connect(0, 1, 4);
        Collection<node_info> v = g.getV();
        Iterator<node_info> iter = v.iterator();
        while (iter.hasNext()) {
            node_info n = iter.next();
            assertNotNull(n);
        }
    }

    @Test
    void hasEdge() {
        weighted_graph g = graph_creator(10,9,2);
        assertFalse(g.hasEdge(3,5));
        assertFalse(g.hasEdge(5,3));
        assertTrue(g.hasEdge(7,9));
        assertTrue(g.hasEdge(9,7));
        assertFalse(g.hasEdge(-1,2));
        assertFalse(g.hasEdge(3,-4));
        assertTrue(g.hasEdge(1,1));
        assertFalse(g.hasEdge(-1,-1));
    }

    @Test
    void connect() {
        weighted_graph g = new WGraph_DS();
        for (int i = 0; i < 4; i++) {
            g.addNode(i);
        }
        g.removeEdge(0,1);
        assertFalse(g.hasEdge(1,0));
        assertFalse(g.hasEdge(-1,2));
        assertTrue(g.hasEdge(1,1));
        g.removeEdge(2,1);
        assertEquals(g.getEdge(2,1),-1);
        g.connect(0,1,1);
        assertTrue(g.hasEdge(1,0));
        double w = g.getEdge(1,0);
        assertEquals(w,1);
        assertEquals(g.getEdge(-1,-1),-1);
    }


    @Test
    void removeNode() {
        weighted_graph g = new WGraph_DS();
        for (int i = 0; i < 4; i++) {
            g.addNode(i);
        }
        for (int i = 1; i < 4; i++) {
            g.connect(0, i, i);
        }
        assertTrue(g.hasEdge(1,0));
        g.removeNode(4);
        g.removeNode(0);
        assertFalse(g.hasEdge(1,0));
        assertFalse(g.hasEdge(4,0));
        assertFalse(g.hasEdge(1,4));
        int e = g.edgeSize();
        assertEquals(0,e);
        assertEquals(3,g.nodeSize());
    }

    @Test
    void removeEdge() {
        weighted_graph g = new WGraph_DS();
        g.addNode(7);
        g.addNode(8);
        g.removeEdge(7,8);
        assertEquals(0, g.edgeSize());
        for (int i = 0; i < 4; i++) {
            g.addNode(i);
        }
        for (int i = 1; i < 4; i++) {
            g.connect(0, i, i);
        }
        double w = g.getEdge(0,3);
        assertEquals(3, w);
        g.removeEdge(0,3);
        w = g.getEdge(0,3);
        assertEquals(-1,w);
        assertEquals(g.getEdge(-1, 2),-1);
        assertEquals(g.getEdge(-2, -2),-1);
        g.removeEdge(0,3);
        assertEquals(g.getEdge(-2, -2),-1);
    }

    public static weighted_graph graph_creator(int v_size, int e_size, int seed) {
        weighted_graph g = new WGraph_DS();
        _rnd = new Random(seed);
        for(int i=0;i<v_size;i++) {
            g.addNode(i);
        }

        Object[] nodes = g.getV().toArray();
        while(g.edgeSize() < e_size) {
            int node1 = ((node_info)nodes[(int)(_rnd.nextDouble()*v_size)]).getKey();
            int node2 = ((node_info)nodes[(int)(_rnd.nextDouble()*v_size)]).getKey();
            double w = (int)_rnd.nextDouble()*100;
            g.connect(node1,node2, w);
        }
        return g;
    }
}
