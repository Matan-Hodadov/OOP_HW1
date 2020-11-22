package ex1.src;

import java.util.Comparator;

public class NodeInfoCompare implements Comparator<node_info> {

    @Override
    public int compare(node_info o1, node_info o2) {
        return Double.compare(o1.getTag(), o2.getTag());
    }
}
