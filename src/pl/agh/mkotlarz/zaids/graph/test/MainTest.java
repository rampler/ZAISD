package pl.agh.mkotlarz.zaids.graph.test;

import pl.agh.mkotlarz.zaids.graph.Graph;
import pl.agh.mkotlarz.zaids.graph.MatrixGraph;
import pl.agh.mkotlarz.zaids.importer.GraphImporter;

import java.io.FileNotFoundException;

/**
 * Created by Mateusz on 11.10.2015.
 */
public class MainTest {

    public static void main(String args[]) {
        try {
            testMatrixGraph();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testMatrixGraph() throws FileNotFoundException {
        System.out.println("Testing Matrix Graph");
        long startTime = System.currentTimeMillis();
        Graph graph = new MatrixGraph();
//        GraphImporter.importGraphFromConsole(graph);
        GraphImporter.importGraphFromFile(graph, "graf.txt");
        System.out.println("DONE!!! Time: "+(System.currentTimeMillis() - startTime)+" ms.");
    }

    public static void testListGraph(){

    }
}
