package pl.agh.mkotlarz.zaids.graph;

import pl.agh.mkotlarz.zaids.graph.exceptions.NodeNotFoundException;

/**
 * Created by Mateusz on 07.10.2015.
 */
public class ListGraph implements Graph {

    private final int DEFAULT_INIT_SIZE = 100;
    private final int DEFAULT_STEP_SIZE = 10;
    private int actualSize = 0;

    private ListGraphNode[] nodes;

    /* Constructors */
    public ListGraph(int initSize){
        nodes = new ListGraphNode[initSize];
    }

    public ListGraph() {
        nodes = new ListGraphNode[DEFAULT_INIT_SIZE];
    }

    /* Private methods */

    private void makeNodesArrayBigger(int step){
        ListGraphNode[] newArray = new ListGraphNode[actualSize + step];
        for(int i=0; i < nodes.length; i++)
            newArray[i] = nodes[i];
        nodes = newArray;
    }

    private int findNodeArrayIndex(GraphNode graphNode) throws NodeNotFoundException {
        int nodeInArrayIndex = 0;
        while(nodeInArrayIndex < nodes.length && nodes[nodeInArrayIndex].getGraphNode() == graphNode)
            nodeInArrayIndex++;

        if(nodeInArrayIndex == nodes.length)
            throw new NodeNotFoundException();

        return nodeInArrayIndex;
    }

    /* Interface methods */
    @Override
    public void addNode(GraphNode graphNode) {
        ListGraphNode listGraphNode = new ListGraphNode(graphNode);

        try{
            findNodeArrayIndex(graphNode);
        }
        catch (NodeNotFoundException ex){
            if(nodes.length == actualSize)
                makeNodesArrayBigger(DEFAULT_STEP_SIZE);

            nodes[actualSize+1] = listGraphNode;

            actualSize++;
        }
    }

    @Override
    public void deleteNode(GraphNode graphNode) throws NodeNotFoundException {

    }

    @Override
    public void addEdge(GraphEdge graphEdge) {

    }

    @Override
    public void deleteEdge(GraphEdge graphEdge) throws NodeNotFoundException {

    }

    @Override
    public GraphNode[] getNeighborNodes(GraphNode graphNode) throws NodeNotFoundException {
        return new GraphNode[0];
    }

    @Override
    public GraphEdge[] getIncidentalEdges(GraphNode graphNode) throws NodeNotFoundException {
        return new GraphEdge[0];
    }

    @Override
    public int getNodesCount() {
        return 0;
    }

    @Override
    public int getEdgesCount() {
        return 0;
    }

    @Override
    public boolean isNodesNeighbors(GraphNode graphNode1, GraphNode graphNode2) throws NodeNotFoundException {
        return false;
    }
}
