package pl.agh.mkotlarz.zaids;

import pl.agh.mkotlarz.zaids.exceptions.NodeNotFoundException;

/**
 * Created by Mateusz on 06.10.2015.
 */
public interface Graph {

    void addNode(GraphNode graphNode);
    void deleteNode(GraphNode graphNode) throws NodeNotFoundException;
    void addEdge(GraphEdge graphEdge);
    void deleteEdge(GraphEdge graphEdge) throws NodeNotFoundException;
    GraphNode[] getNeighborNodes(GraphNode graphNode) throws NodeNotFoundException;
    GraphEdge[] getIncidentalEdges(GraphNode graphNode) throws NodeNotFoundException;
    int getNodesCount();
    int getEdgesCount();
    boolean isNodesNeighbors(GraphNode graphNode1, GraphNode graphNode2) throws NodeNotFoundException;

}