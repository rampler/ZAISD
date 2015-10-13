package pl.agh.mkotlarz.zaids.graph.test;

import pl.agh.mkotlarz.zaids.graph.*;
import pl.agh.mkotlarz.zaids.graph.exceptions.EdgeNotFoundException;
import pl.agh.mkotlarz.zaids.graph.exceptions.NodeNotFoundException;
import pl.agh.mkotlarz.zaids.importer.GraphImporter;

import java.io.FileNotFoundException;

/**
 * Created by Mateusz on 11.10.2015.
 */
public class MainTest {

    public static void main(String args[]) {
        try {
            System.out.println("Testing Matrix Graph");
            Graph matrixGraph = new MatrixGraph();
            testPrimaryFunctions(matrixGraph);

            System.out.println("Testing List Graph");
            Graph listGraph = new ListGraph();
            testPrimaryFunctions(listGraph);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testPrimaryFunctions(Graph graph) throws FileNotFoundException, NodeNotFoundException, EdgeNotFoundException {
        long startTime = System.currentTimeMillis();

        //Loading graph
        GraphImporter.importGraphFromFile(graph, "duzy_graf.txt");
        System.out.println("Graph loaded!!! Time: "+(System.currentTimeMillis() - startTime)+" ms.");

        //Nodes count
        int nodesCount1 = graph.getNodesCount();

        //Edges count
        int edgesCount1 = graph.getEdgesCount();

        //Deleting node
        graph.deleteNode(new GraphNode(1));

        //Delete edge
//        graph.deleteEdge(new GraphEdge(new GraphNode(15), new GraphNode(5), 54));
        graph.deleteEdge(new GraphEdge(new GraphNode(762), new GraphNode(606), 199));

        //Get neighbors
        GraphNode[] neighbors = graph.getNeighborNodes(new GraphNode(19));

        //Get incidental
        GraphEdge[] incidental = graph.getIncidentalEdges(new GraphNode(19));

        //Nodes count
        int nodesCount2 = graph.getNodesCount();

        //Edges count
        int edgesCount2 = graph.getEdgesCount();

        //Is neighbor
        boolean test1 = graph.isNodesNeighbors(new GraphNode(1), new GraphNode(31));
        boolean test2 = graph.isNodesNeighbors(new GraphNode(5), new GraphNode(9));

        System.out.println("Total Test Time: "+(System.currentTimeMillis() - startTime)+" ms.");
        System.out.println("");
    }
}
