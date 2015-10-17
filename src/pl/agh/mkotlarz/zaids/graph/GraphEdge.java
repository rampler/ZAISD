package pl.agh.mkotlarz.zaids.graph;

/**
 * Created by Mateusz on 06.10.2015.
 */
public class GraphEdge {
    private GraphNode firstNode;
    private GraphNode secondNode;
    private int weight;

    public GraphEdge(GraphNode firstNode, GraphNode secondNode, int weight) {
        this.firstNode = firstNode;
        this.secondNode = secondNode;
        this.weight = weight;
    }

    public GraphNode getFirstNode() {
        return firstNode;
    }

    public GraphNode getSecondNode() {
        return secondNode;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "(" + firstNode + "," + secondNode + "," + weight + ')';
    }

    @Override
    public int hashCode() {
        int result = firstNode != null ? firstNode.getNodeId() : 0;
        result = 31 * result + (secondNode != null ? secondNode.getNodeId() : 0);
//        result = 31 * result + weight;
        return result;
    }
}
