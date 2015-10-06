package pl.agh.mkotlarz.zaids;

/**
 * Created by Mateusz on 06.10.2015.
 */
public class MatrixGraph implements Graph {

    private final int DEFAULT_INIT_SIZE = 100;
    private final int DEFAULT_STEP_SIZE = 10;
    private int actualSize;

    private Object[][] matrix;

    /* Constructors */
    public MatrixGraph(int initSize){
        matrix = new Object[initSize][initSize];
        
    }

    public MatrixGraph() {
        matrix = new Object[DEFAULT_INIT_SIZE][DEFAULT_INIT_SIZE];
        actualSize = DEFAULT_INIT_SIZE;
    }

    /* Private methods */


    /* Implementation of interface Graph */
    @Override
    public void addNode(GraphNode graphNode) {

    }

    @Override
    public void deleteNode(GraphNode graphNode) {

    }

    @Override
    public void addEdge(GraphEdge graphEdge) {

    }

    @Override
    public void deleteEdge(GraphEdge graphEdge) {

    }

    @Override
    public GraphNode[] getNeighborNodes(GraphNode graphNode) {
        return new GraphNode[0];
    }

    @Override
    public GraphEdge[] getIncidentalEdges(GraphNode graphNode) {
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
    public boolean isNodesNeighbors(GraphNode graphNode1, GraphNode graphNode2) {
        return false;
    }
}
