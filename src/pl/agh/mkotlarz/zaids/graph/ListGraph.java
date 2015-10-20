package pl.agh.mkotlarz.zaids.graph;

import pl.agh.mkotlarz.zaids.graph.exceptions.EdgeNotFoundException;
import pl.agh.mkotlarz.zaids.graph.exceptions.NodeNotFoundException;

import java.util.LinkedList;

/**
 * Created by Mateusz on 07.10.2015.
 */
public class ListGraph implements Graph {

    private final int DEFAULT_INIT_SIZE = 100;
    private final int DEFAULT_STEP_SIZE = 10;
    private int actualSize = 0;

    private ListGraphNode[] nodes;

    /* Constructors */
    public ListGraph(int initSize) {
        nodes = new ListGraphNode[initSize];
    }

    public ListGraph() {
        nodes = new ListGraphNode[DEFAULT_INIT_SIZE];
    }

    /* Private methods */

    private void makeNodesArrayBigger(int step) {
        ListGraphNode[] newArray = new ListGraphNode[actualSize + step];
        for (int i = 0; i < nodes.length; i++)
            newArray[i] = nodes[i];
        nodes = newArray;
    }

    private int findNodeArrayIndex(GraphNode graphNode) throws NodeNotFoundException {
        int nodeInArrayIndex = 0;
        while (nodeInArrayIndex < actualSize && !graphNode.equals(nodes[nodeInArrayIndex].getGraphNode()))
            nodeInArrayIndex++;

        if (nodeInArrayIndex == actualSize)
            throw new NodeNotFoundException(graphNode);

        return nodeInArrayIndex;
    }

    /* Interface methods */
    @Override
    public void addNode(GraphNode graphNode) {
        ListGraphNode listGraphNode = new ListGraphNode(graphNode);

        try {
            findNodeArrayIndex(graphNode);
        } catch (NodeNotFoundException ex) {   //If not exist - add node
            if (nodes.length - 1 == actualSize)
                makeNodesArrayBigger(DEFAULT_STEP_SIZE);

            nodes[actualSize] = listGraphNode;

            actualSize++;
        }                                   //Else do nothing
    }

    @Override
    public void deleteNode(GraphNode graphNode) throws NodeNotFoundException {
        //Deleting edges connected to node
        for (int i = 0; i < actualSize; i++) {
            ListGraphEdge actualEdge = nodes[i].getFirstEdge();
            while (actualEdge != null) {
                if (actualEdge.getSecondNode().equals(graphNode)) {
                    try {
                        deleteEdge(actualEdge);
                    } catch (EdgeNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                actualEdge = actualEdge.getNextEdge();
            }
        }

        int nodeIndex = findNodeArrayIndex(graphNode);
        for (int i = nodeIndex; i < actualSize; i++)
            nodes[i] = nodes[i + 1];
        nodes[actualSize - 1] = null;
        actualSize--;
    }

    @Override
    public void addEdge(GraphEdge graphEdge) {
        int primaryNodeIndex = 0;
        ListGraphEdge newEdge = new ListGraphEdge(graphEdge.getFirstNode(), graphEdge.getSecondNode(), graphEdge.getWeight());

        try {
            primaryNodeIndex = findNodeArrayIndex(graphEdge.getFirstNode());
        } catch (NodeNotFoundException ex) {   //If not exist first node - add it
            addNode(graphEdge.getFirstNode());
            primaryNodeIndex = actualSize - 1;
        } finally {
            ListGraphEdge prevEdge = null;
            ListGraphEdge actualEdge = nodes[primaryNodeIndex].getFirstEdge();
            while (actualEdge != null) {
                prevEdge = actualEdge;
                actualEdge = actualEdge.getNextEdge();
            }

            if (prevEdge == null) nodes[primaryNodeIndex].setFirstEdge(newEdge);
            else {
                prevEdge.setNextEdge(newEdge);
                newEdge.setPrevEdge(prevEdge);
            }
        }
    }

    @Override
    public void deleteEdge(GraphEdge graphEdge) throws NodeNotFoundException, EdgeNotFoundException {
        int primaryNodeIndex = findNodeArrayIndex(graphEdge.getFirstNode());
        ListGraphEdge actualEdge = nodes[primaryNodeIndex].getFirstEdge();
        while (actualEdge != null && !actualEdge.getSecondNode().equals(graphEdge.getSecondNode()))
            actualEdge = actualEdge.getNextEdge();

        if (actualEdge == null) throw new EdgeNotFoundException(graphEdge);
        else {
            if (actualEdge.getPrevEdge() != null) actualEdge.getPrevEdge().setNextEdge(actualEdge.getNextEdge());
            else nodes[primaryNodeIndex].setFirstEdge(actualEdge.getNextEdge());
            if (actualEdge.getNextEdge() != null) actualEdge.getNextEdge().setPrevEdge(actualEdge.getPrevEdge());
        }
    }

    @Override
    public GraphNode[] getNeighborNodes(GraphNode graphNode) throws NodeNotFoundException {
        int nodesCount = 0;
        int primaryNodeIndex = findNodeArrayIndex(graphNode);

        ListGraphEdge actualEdge = nodes[primaryNodeIndex].getFirstEdge();
        while (actualEdge != null) {
            actualEdge = actualEdge.getNextEdge();
            nodesCount++;
        }

        GraphNode[] neighbors = new GraphNode[nodesCount];
        int actualPosition = 0;
        actualEdge = nodes[primaryNodeIndex].getFirstEdge();
        while (actualEdge != null) {
            neighbors[actualPosition] = actualEdge.getSecondNode();
            actualEdge = actualEdge.getNextEdge();
            actualPosition++;
        }

        return neighbors;
    }

    @Override
    public GraphEdge[] getIncidentalEdges(GraphNode graphNode) throws NodeNotFoundException {
        int edgesCount = 0;

        int index = findNodeArrayIndex(graphNode);

//        for(int i=0; i<actualSize; i++){
//            ListGraphEdge actualEdge = nodes[i].getFirstEdge();
        ListGraphEdge actualEdge = nodes[index].getFirstEdge();
        while (actualEdge != null) {
//                if (actualEdge.getFirstNode().equals(graphNode) || actualEdge.getSecondNode().equals(graphNode))
            edgesCount++;
            actualEdge = actualEdge.getNextEdge();
        }
//        }

        GraphEdge[] neighbors = new GraphEdge[edgesCount];
        int actualPosition = 0;
//        for(int i=0; i<actualSize; i++){
//            ListGraphEdge actualEdge = nodes[i].getFirstEdge();
        actualEdge = nodes[index].getFirstEdge();
        while (actualEdge != null) {
//                if (actualEdge.getFirstNode().equals(graphNode) || actualEdge.getSecondNode().equals(graphNode)){
            neighbors[actualPosition] = actualEdge;
            actualPosition++;
//                }
            actualEdge = actualEdge.getNextEdge();
        }
//        }

        return neighbors;
    }

    @Override
    public int getNodesCount() {
        return actualSize;
    }

    @Override
    public int getEdgesCount() {
        int count = 0;
        for (int i = 0; i < actualSize; i++) {
            ListGraphEdge actualEdge = nodes[i].getFirstEdge();
            while (actualEdge != null) {
                count++;
                actualEdge = actualEdge.getNextEdge();
            }
        }
        return count;
    }

    @Override
    public boolean isNodesNeighbors(GraphNode graphNode1, GraphNode graphNode2) {
        try {
            int firstNodeIndex = findNodeArrayIndex(graphNode1);
            ListGraphEdge actualEdge = nodes[firstNodeIndex].getFirstEdge();
            while (actualEdge != null && !actualEdge.getSecondNode().equals(graphNode2)) {
                actualEdge = actualEdge.getNextEdge();
            }
            return (actualEdge != null);
        } catch (NodeNotFoundException ex) {
            return false;
        }
    }

    @Override
    public LinkedList<GraphNode> getNodes() {
        LinkedList<GraphNode> newNodes = new LinkedList<>();
        for (ListGraphNode node : nodes)
            if (node != null && node.getGraphNode() != null)
                newNodes.add(node.getGraphNode());

        return newNodes;
    }

    @Override
    public LinkedList<GraphEdge> getEdges() throws NodeNotFoundException {
        LinkedList<GraphEdge> edges = new LinkedList<>();
        for (GraphNode node : getNodes())
            for (GraphEdge edge : getIncidentalEdges(node))
                edges.add(edge);

        return edges;
    }
}
