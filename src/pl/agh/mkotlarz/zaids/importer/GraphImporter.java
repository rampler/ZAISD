package pl.agh.mkotlarz.zaids.importer;

import pl.agh.mkotlarz.zaids.graph.Graph;
import pl.agh.mkotlarz.zaids.graph.GraphEdge;
import pl.agh.mkotlarz.zaids.graph.GraphNode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Mateusz on 11.10.2015.
 */
public class GraphImporter {

    /* Private methods */

    private static void proceedNextLine(Graph graph, String line) {
        line = line.replaceAll("\\s+","");
        String lines[] = line.split(";");
        GraphNode firstNode = new GraphNode(Integer.parseInt(lines[0]));
        GraphNode secondNode = new GraphNode(Integer.parseInt(lines[1]));
        graph.addNode(firstNode);
        graph.addNode(secondNode);
        graph.addEdge(new GraphEdge(firstNode, secondNode, Integer.parseInt(lines[2])));
    }


    /* Public methods */

    public static Graph importGraphFromConsole(Graph graph) {
        System.out.println("Write new edges in lines in format:    firstNodeId; secondNodeId; edgeWeight");
        System.out.println("To end creating graph put empty line.");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        while(line != null && !line.replaceAll("\\s+","").equals("")) {
            proceedNextLine(graph, line);
            line = scanner.nextLine();
        }
        scanner.close();
        return graph;
    }

    public static Graph importGraphFromString(Graph graph, String graphString) {
        String lines[] = graphString.split("\\r?\\n");
        for(String line : lines)
            proceedNextLine(graph, line);
        return graph;
    }

    public static Graph importGraphFromFile(Graph graph, String path) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(path));
        while(scanner.hasNextLine())
            proceedNextLine(graph, scanner.nextLine());
        scanner.close();
        return graph;
    }
}
