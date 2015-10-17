package pl.agh.mkotlarz.zaids.fordfulkerson;

import pl.agh.mkotlarz.zaids.graph.Graph;
import pl.agh.mkotlarz.zaids.graph.GraphEdge;
import pl.agh.mkotlarz.zaids.graph.GraphNode;
import pl.agh.mkotlarz.zaids.graph.exceptions.NodeNotFoundException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Mateusz on 17.10.2015.
 */
public class FordFulkersonAlgorithm {

    private static Graph graph;
    private static HashMap<GraphEdge, Integer> flows;
    private static LinkedList<GraphNode> visitedNodes;

    public static int getMaxFlow(Graph graph, GraphNode source, GraphNode sink) throws NodeNotFoundException {
        long startTime = System.currentTimeMillis();

        FordFulkersonAlgorithm.graph = graph;
        flows = new HashMap<>();
        LinkedList<GraphEdge> path = findPath(source, sink, new LinkedList<GraphEdge>());
        System.out.println(path);
        while(path != null && !path.isEmpty()) {
            int minCf = (path.getFirst().getWeight() - ((flows.containsKey(path.getFirst()))?flows.get(path.getFirst()):0));
            for(GraphEdge edge : path) {
                int cf = (edge.getWeight() - ((flows.containsKey(edge)) ? flows.get(edge) : 0));
                if (cf < minCf)
                    minCf = cf;
            }
            for(GraphEdge edge : path) {
                flows.put(edge, (((flows.containsKey(edge))?flows.get(edge):0)+minCf));
                GraphEdge reverseEdge = new GraphEdge(edge.getSecondNode(),edge.getFirstNode(),0);
                flows.put(reverseEdge, (((flows.containsKey(reverseEdge))?flows.get(reverseEdge):0)-minCf)); //TODO uncomment
            }
            path = findPath(source, sink, new LinkedList<GraphEdge>());
            System.out.println(path);
        }

        System.out.println(flows);
        int flow = 0;
        for(GraphEdge edge : graph.getIncidentalEdges(source))
            if(edge.getFirstNode().equals(source))
                flow += ((flows.containsKey(edge))?flows.get(edge):0);

        System.out.println("Time: "+(System.currentTimeMillis()-startTime));
        return flow;
    }

    private static LinkedList<GraphEdge> findPath(GraphNode startNode, GraphNode endNode, LinkedList<GraphEdge> path) throws NodeNotFoundException {
        if(path.isEmpty())
            visitedNodes = new LinkedList<>();

//        LinkedList<GraphEdge> newPath = new LinkedList<>(path);
        if (startNode.equals(endNode))
            return path;
        else {
            for (GraphEdge edge : graph.getIncidentalEdges(startNode)) {
                    int cf = edge.getWeight() - (flows.get(edge) != null ? flows.get(edge): 0);
                    visitedNodes.add(edge.getSecondNode());
                    if(cf > 0 && !path.contains(edge)) { //avoid cycle
                        path.add(edge);

                        LinkedList<GraphEdge> cycPath = findPath(edge.getSecondNode(), endNode, path);
                        if(cycPath != null)
                            return cycPath;
                    }
            }
            return null;
        }
    }
}
