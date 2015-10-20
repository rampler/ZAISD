package pl.agh.mkotlarz.zaids.graph;

/**
 * Created by Mateusz on 06.10.2015.
 */
public class GraphNode {
    private int nodeId;

    public GraphNode(int nodeId) {
        this.nodeId = nodeId;
    }

    public int getNodeId() {
        return nodeId;
    }

    @Override
    public String toString() {
        return nodeId + "";
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof GraphNode && ((GraphNode) obj).getNodeId() == nodeId);
    }

    @Override
    public int hashCode() {
        return nodeId;
    }
}
