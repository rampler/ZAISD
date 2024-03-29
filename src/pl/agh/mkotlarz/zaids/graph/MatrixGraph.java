package pl.agh.mkotlarz.zaids.graph;

import pl.agh.mkotlarz.zaids.graph.exceptions.EdgeNotFoundException;
import pl.agh.mkotlarz.zaids.graph.exceptions.NodeNotFoundException;

import java.util.LinkedList;

/**
 * Created by Mateusz on 06.10.2015.
 */
public class MatrixGraph implements Graph {

    private final int DEFAULT_INIT_SIZE = 100;
    private final int DEFAULT_STEP_SIZE = 10;
    private int actualSize = 0;

    private Object[][] matrix;

    /* Constructors */
    public MatrixGraph(int initSize) {
        matrix = new Object[initSize][initSize];
    }

    public MatrixGraph() {
        matrix = new Object[DEFAULT_INIT_SIZE][DEFAULT_INIT_SIZE];
    }

    /* Private methods */

    private void makeMatrixBigger(int step) {
        Object[][] newMatrix = new Object[matrix.length + step][matrix.length + step];
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++)
                newMatrix[i][j] = matrix[i][j];
        matrix = newMatrix;
    }

    private int findNodeMatrixIndex(GraphNode graphNode) throws NodeNotFoundException {
        int nodeInMatrixIndex = 0;
        while (nodeInMatrixIndex < matrix.length && !graphNode.equals(matrix[nodeInMatrixIndex][0]))
            nodeInMatrixIndex++;

        if (nodeInMatrixIndex == matrix.length)
            throw new NodeNotFoundException(graphNode);

        return nodeInMatrixIndex;
    }

    private int getNeighborsCount(GraphNode graphNode) throws NodeNotFoundException {
        int nodeMatrixIndex = findNodeMatrixIndex(graphNode);
        int neighborsCount = 0;
        for (int i = 1; i <= actualSize; i++)
            if (matrix[nodeMatrixIndex][i] != null)
                neighborsCount++;
        return neighborsCount;
    }

    /* Implementation of interface Graph */
    @Override
    public void addNode(GraphNode graphNode) {
        try {
            findNodeMatrixIndex(graphNode);
        } catch (NodeNotFoundException ex) {
            if (matrix.length - 1 == actualSize)
                makeMatrixBigger(DEFAULT_STEP_SIZE);

            matrix[actualSize + 1][0] = graphNode;
            matrix[0][actualSize + 1] = graphNode;

            actualSize++;
        }
    }

    @Override
    public void deleteNode(GraphNode graphNode) throws NodeNotFoundException {
        int nodeInMatrixIndex = findNodeMatrixIndex(graphNode);

        for (int i = nodeInMatrixIndex; i <= actualSize; i++)
            for (int j = 0; j <= actualSize; j++)
                if (i != actualSize)
                    matrix[i][j] = matrix[i + 1][j];
                else
                    matrix[i][j] = null;

        for (int i = 0; i <= actualSize; i++)
            for (int j = nodeInMatrixIndex; j <= actualSize; j++)
                if (j != actualSize)
                    matrix[i][j] = matrix[i][j + 1];
                else
                    matrix[i][j] = null;

        actualSize--;
    }

    @Override
    public void addEdge(GraphEdge graphEdge) {
        int firstNodeMatrixIndex = 0;
        int secondNodeMatrixIndex = 0;

        try {
            firstNodeMatrixIndex = findNodeMatrixIndex(graphEdge.getFirstNode());
            secondNodeMatrixIndex = findNodeMatrixIndex(graphEdge.getSecondNode());
        } catch (NodeNotFoundException ex) {
            addNode(graphEdge.getFirstNode());
            firstNodeMatrixIndex = actualSize;
            addNode(graphEdge.getSecondNode());
            secondNodeMatrixIndex = actualSize;
        } finally {
            if (matrix[firstNodeMatrixIndex][secondNodeMatrixIndex] == null || ((GraphEdge) matrix[firstNodeMatrixIndex][secondNodeMatrixIndex]).getWeight() > graphEdge.getWeight())
                matrix[firstNodeMatrixIndex][secondNodeMatrixIndex] = graphEdge; // If lower weight then replace current edge.
        }
    }

    @Override
    public void deleteEdge(GraphEdge graphEdge) throws NodeNotFoundException, EdgeNotFoundException {
        int firstNodeMatrixIndex = findNodeMatrixIndex(graphEdge.getFirstNode());
        int secondNodeMatrixIndex = findNodeMatrixIndex(graphEdge.getSecondNode());
        if (matrix[firstNodeMatrixIndex][secondNodeMatrixIndex] == null) throw new EdgeNotFoundException(graphEdge);
        else matrix[firstNodeMatrixIndex][secondNodeMatrixIndex] = null;
    }

    @Override
    public GraphNode[] getNeighborNodes(GraphNode graphNode) throws NodeNotFoundException {
        int nodeMatrixIndex = findNodeMatrixIndex(graphNode);
        int neighborsCount = getNeighborsCount(graphNode);
        GraphNode[] neighbors = new GraphNode[neighborsCount];

        int actualNeighborIndex = 0;

        for (int i = 1; i <= actualSize; i++)
            if (matrix[nodeMatrixIndex][i] != null) {
                neighbors[actualNeighborIndex] = ((GraphEdge) matrix[nodeMatrixIndex][i]).getSecondNode();
                actualNeighborIndex++;
            }

        return neighbors;
    }

    @Override
    public GraphEdge[] getOutEdges(GraphNode graphNode) throws NodeNotFoundException {
        int incidentalCount = 0;
        int index = findNodeMatrixIndex(graphNode);
        for (int i = 1; i < actualSize + 1; i++)
            if (matrix[index][i] != null && (((GraphEdge) matrix[index][i]).getFirstNode().equals(graphNode)))
                incidentalCount++;


        GraphEdge[] incidentalList = new GraphEdge[incidentalCount];
        int actualElementIndex = 0;
        for (int i = 1; i < actualSize + 1; i++) {
            if (matrix[index][i] != null && (((GraphEdge) matrix[index][i]).getFirstNode().equals(graphNode))) {
                incidentalList[actualElementIndex] = (GraphEdge) matrix[index][i];
                actualElementIndex++;
            }
        }

        return incidentalList;
    }

    @Override
    public GraphEdge[] getInEdges(GraphNode graphNode) throws NodeNotFoundException {
        int incidentalCount = 0;
        int index = findNodeMatrixIndex(graphNode);
        for (int i = 1; i < actualSize + 1; i++)
            if (matrix[i][index] != null && (((GraphEdge) matrix[i][index]).getSecondNode().equals(graphNode)))
                incidentalCount++;

        GraphEdge[] incidentalList = new GraphEdge[incidentalCount];
        int actualElementIndex = 0;
        for (int i = 1; i < actualSize + 1; i++) {
            if (matrix[i][index] != null && (((GraphEdge) matrix[i][index]).getSecondNode().equals(graphNode))) {
                incidentalList[actualElementIndex] = (GraphEdge) matrix[i][index];
                actualElementIndex++;
            }
        }

        return incidentalList;
    }

    @Override
    public GraphEdge[] getIncidentalEdges(GraphNode graphNode) throws NodeNotFoundException {
        GraphEdge[] inEdges = getInEdges(graphNode);
        GraphEdge[] outEdges = getOutEdges(graphNode);
        GraphEdge[] incidentalEdges = new GraphEdge[inEdges.length + outEdges.length];
        for (int i = 0; i < outEdges.length; i++)
            incidentalEdges[i] = outEdges[i];
        for (int i = 0; i < inEdges.length; i++) {
            incidentalEdges[i + outEdges.length] = inEdges[i];
        }

        return incidentalEdges;
    }

    @Override
    public int getNodesCount() {
        return actualSize;
    }

    @Override
    public int getEdgesCount() {
        int size = 0;
        for (int i = 1; i <= actualSize; i++)
            for (int j = 1; j <= actualSize; j++)
                if (matrix[i][j] != null)
                    size++;
        return size;
    }

    @Override
    public boolean isNodesNeighbors(GraphNode graphNode1, GraphNode graphNode2) {
        try {
            int firstNodeMatrixIndex = findNodeMatrixIndex(graphNode1);
            int secondNodeMatrixIndex = findNodeMatrixIndex(graphNode2);

            return matrix[firstNodeMatrixIndex][secondNodeMatrixIndex] != null || matrix[secondNodeMatrixIndex][firstNodeMatrixIndex] != null;
        } catch (NodeNotFoundException e) {
            return false;
        }

    }

    @Override
    public LinkedList<GraphNode> getNodes() {
        LinkedList<GraphNode> newNodes = new LinkedList<>();
        for (int i = 1; i < actualSize + 1; i++)
            newNodes.add(((GraphNode) matrix[0][i]));
        return newNodes;
    }

    @Override
    public LinkedList<GraphEdge> getEdges() throws NodeNotFoundException {
        LinkedList<GraphEdge> edges = new LinkedList<>();
        for (GraphNode node : getNodes())
            for (GraphEdge edge : getOutEdges(node))
                edges.add(edge);

        return edges;
    }
}
