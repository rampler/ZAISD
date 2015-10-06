package pl.agh.mkotlarz.zaids;

/**
 * Created by Mateusz on 06.10.2015.
 */
public interface Graph {

    void addNode(GraphNode graphNode);
    void deleteNode(GraphNode graphNode);
    void addEdge(GraphEdge graphEdge);
    void deleteEdge(GraphEdge graphEdge);
    GraphNode[] getNeighborNodes(GraphNode graphNode);
    GraphEdge[] getIncidentalEdges(GraphNode graphNode);
    int getNodesCount();
    int getEdgesCount();
    boolean isNodesNeighbors(GraphNode graphNode1, GraphNode graphNode2);

}
