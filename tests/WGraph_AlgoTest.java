package ex1.tests;

import ex1.src.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTest {
    @Test
    void isConnected() {
        weighted_graph g0 = new WGraph_DS();
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertTrue(ag0.isConnected());
        g0.addNode(3);
        assertTrue(ag0.isConnected());
        g0.addNode(3);
        assertTrue(ag0.isConnected());
        g0.addNode(5);
        assertFalse(ag0.isConnected());
        g0.connect(3,5,1);
        assertTrue(ag0.isConnected());
        g0.addNode(7);
        g0.connect(5,7,1);
        assertTrue(ag0.isConnected());
        g0.removeNode(5);
        assertFalse(ag0.isConnected());
        g0.removeNode(3);
        assertTrue(ag0.isConnected());
        g0.removeNode(7);
        assertTrue(ag0.isConnected());

    }

    @Test
    void shortestPathDist() {
        weighted_graph g0 = new WGraph_DS();
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        g0.addNode(0);
        g0.addNode(1);
        assertEquals(-1, ag0.shortestPathDist(0,1));
        g0.connect(0,1,33);
        assertEquals(33, ag0.shortestPathDist(0,1));
        assertEquals(0, ag0.shortestPathDist(0,0));
        assertEquals(-1, ag0.shortestPathDist(-1,1));
        assertEquals(-1, ag0.shortestPathDist(-1,-1));
        g0.removeNode(0);
        assertEquals(-1, ag0.shortestPathDist(0,1));
        g0.addNode(0);
        g0.connect(0,1,1);
        g0.addNode(2);
        g0.addNode(3);
        g0.connect(1,2,1);
        g0.connect(2,3,31);
        assertEquals(33, ag0.shortestPathDist(0,3));
        g0.connect(0,3,3);
        assertEquals(3, ag0.shortestPathDist(0,3));

    }

    @Test
    void shortestPath() {
        weighted_graph g0 = new WGraph_DS();
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        for (int i = 0; i < 11; i++) {
            g0.addNode(i);
        }
        for (int i = 0; i < 10; i++) {
            g0.connect(i, i+1, i+1);
        }

        List<node_info> sp = ag0.shortestPath(0,4);
        int[] checkKey = {0,1,2,3,4};
        int[] checkTag = {0, 1, 3, 6, 10};
        int i = 0;
        for(node_info n: sp) {
        	assertEquals(checkTag[i], n.getTag());
        	assertEquals(checkKey[i], n.getKey());
        	i++;
        }

        g0.connect(4,7,1);
        sp = ag0.shortestPath(0,7);
        int[] checkKey2 = {0,1,2,3,4,7};
        int[] checkTag2 = {0,1,3,6,10,11};
        int j = 0;
        for(node_info n: sp) {
            assertEquals(checkTag2[j], n.getTag());
            assertEquals(checkKey2[j], n.getKey());
            j++;
        }

        g0.connect(0,3,1);
        sp = ag0.shortestPath(0,7);
        int[] checkKey3 = {0,3,4,7};
        int[] checkTag3 = {0,1,5,6};
        int k = 0;
        for(node_info n: sp) {
            assertEquals(checkTag3[k], n.getTag());
            assertEquals(checkKey3[k], n.getKey());
            k++;
        }
    }
    
    @Test
    void save_load() {
        weighted_graph g0 = new WGraph_DS();
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        g0.addNode(1);
        g0.addNode(2);
        g0.addNode(3);
        g0.connect(1,2,1);
        g0.connect(2,3,1);
        String str = "g0.obj";
        ag0.save(str);
        weighted_graph g1 = new WGraph_DS();

        g1.addNode(1);
        g1.addNode(2);
        g1.addNode(3);
        g1.connect(1,2,1);
        g1.connect(2,3,1);
        ag0.load(str);
        assertEquals(g0,g1);
        g0.removeNode(2);
        assertNotEquals(g0,g1);
    }
}
