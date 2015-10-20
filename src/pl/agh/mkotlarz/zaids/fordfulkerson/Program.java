package pl.agh.mkotlarz.zaids.fordfulkerson;

import pl.agh.mkotlarz.zaids.graph.Graph;
import pl.agh.mkotlarz.zaids.graph.GraphNode;
import pl.agh.mkotlarz.zaids.graph.ListGraph;
import pl.agh.mkotlarz.zaids.importer.GraphImporter;

/**
 * Created by Mateusz on 17.10.2015.
 */
public class Program {

    // List implementation:     result = 9351, time = 460286 ms, paths = 9017
    // Matrix implementation:   result = 9351, time = 293834 ms, paths = 8382
    // ListTime/MatrixTime = 1,56648311

    public static void main(String args[]) {
        try {
            System.out.println("Loading graph...");
            Graph graph = new ListGraph();
            GraphImporter.importGraphFromFile(graph, "duzy_graf.txt");
            GraphNode source = new GraphNode(109);
            GraphNode sink = new GraphNode(609);
//
//            GraphImporter.importGraphFromFile(graph, "graf.txt");
//            GraphNode source = new GraphNode(13);
//            GraphNode sink = new GraphNode(18);

//            GraphImporter.importGraphFromFile(graph, "test_graph.txt");
//            GraphNode source = new GraphNode(1);
//            GraphNode sink = new GraphNode(6);

            System.out.println("Graph Loaded! Calculating max flow...");
            int maxFlow = FordFulkersonAlgorithm.getMaxFlow(graph, source, sink);
            System.out.println("Max flow = " + maxFlow);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
