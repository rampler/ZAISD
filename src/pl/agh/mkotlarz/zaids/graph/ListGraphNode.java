package pl.agh.mkotlarz.zaids.graph;

/**
 * Created by Mateusz on 07.10.2015.
 */
public class ListGraphNode {

    private ListGraphEdge firstEdge;
    private GraphNode graphNode;

    public ListGraphNode(GraphNode graphNode) {
        this.graphNode = graphNode;
    }

    public GraphNode getGraphNode() {
        return graphNode;
    }

    public ListGraphNode(ListGraphEdge firstEdge, GraphNode graphNode) {
        this.firstEdge = firstEdge;
        this.graphNode = graphNode;
    }

    public ListGraphEdge getFirstEdge() {
        return firstEdge;
    }

    public void setFirstEdge(ListGraphEdge firstEdge) {
        this.firstEdge = firstEdge;
    }
}
