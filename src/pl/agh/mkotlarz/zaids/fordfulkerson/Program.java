package pl.agh.mkotlarz.zaids.fordfulkerson;

import pl.agh.mkotlarz.zaids.graph.Graph;
import pl.agh.mkotlarz.zaids.graph.GraphNode;
import pl.agh.mkotlarz.zaids.graph.ListGraph;
import pl.agh.mkotlarz.zaids.importer.GraphImporter;

/**
 * Created by Mateusz on 17.10.2015.
 */
public class Program {

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
