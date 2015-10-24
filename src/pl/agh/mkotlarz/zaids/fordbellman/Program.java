package pl.agh.mkotlarz.zaids.fordbellman;

import pl.agh.mkotlarz.zaids.graph.Graph;
import pl.agh.mkotlarz.zaids.graph.GraphNode;
import pl.agh.mkotlarz.zaids.graph.ListGraph;
import pl.agh.mkotlarz.zaids.graph.MatrixGraph;
import pl.agh.mkotlarz.zaids.importer.GraphImporter;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Mateusz on 20.10.2015.
 */
public class Program {
    public static void calculate(Graph graph) {
        try {
            System.out.println("Loading graph...");
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

            System.out.println("Calculating Shortest Path");
            FordBellManAlgorithm fordBellManAlgorithm = new FordBellManAlgorithm();
            HashMap<GraphNode, Integer> weights = fordBellManAlgorithm.getShortestPath(graph.getNodes(), graph.getEdges(), source);
            HashMap<GraphNode, GraphNode> predecessors = fordBellManAlgorithm.getPredecessors();
            System.out.println("Shortest Path  weight from 109 to 609: " + weights.get(sink));

            GraphNode predecessor = new GraphNode(609);
            LinkedList<GraphNode> path = new LinkedList<>();
            while (predecessor != null) {
                path.addFirst(predecessor);
                predecessor = predecessors.get(predecessor);
            }

            System.out.println(path);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String args[]) {
        System.out.println("MATRIX");
        calculate(new MatrixGraph());
        System.out.println();
        System.out.println("LIST");
        calculate(new ListGraph());
    }
}
