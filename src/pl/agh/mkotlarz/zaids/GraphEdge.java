package pl.agh.mkotlarz.zaids;

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
}
