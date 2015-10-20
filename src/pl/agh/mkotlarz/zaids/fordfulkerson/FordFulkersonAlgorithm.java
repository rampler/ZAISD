package pl.agh.mkotlarz.zaids.fordfulkerson;

import javafx.util.Pair;
import pl.agh.mkotlarz.zaids.graph.Graph;
import pl.agh.mkotlarz.zaids.graph.GraphEdge;
import pl.agh.mkotlarz.zaids.graph.GraphNode;
import pl.agh.mkotlarz.zaids.graph.exceptions.NodeNotFoundException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Mateusz on 17.10.2015.
 */
public class FordFulkersonAlgorithm {

    private static Graph graph;
    private static HashMap<GraphEdge, Integer> flows;
    private static HashMap<GraphNode, Pair<GraphNode, Integer>> visitedNodes; //Visited, Previous

    public static int getMaxFlow(Graph graph, GraphNode source, GraphNode sink) throws NodeNotFoundException {
        long startTime = System.currentTimeMillis();

        FordFulkersonAlgorithm.graph = graph;
        flows = new HashMap<>();
        LinkedList<GraphEdge> path = findPath(source, sink, new LinkedList<GraphEdge>());
//        System.out.println((path != null)?path.getFirst():null);
//        System.out.println(path);

        while (path != null && !path.isEmpty()) {
            int minCf = (path.getFirst().getWeight() - ((flows.containsKey(path.getFirst())) ? flows.get(path.getFirst()) : 0));
            for (GraphEdge edge : path) {
                int cf = (edge.getWeight() - ((flows.containsKey(edge)) ? flows.get(edge) : 0));
                if (cf < minCf)
                    minCf = cf;
            }
            for (GraphEdge edge : path) {
                flows.put(edge, (((flows.containsKey(edge)) ? flows.get(edge) : 0) + minCf));
                GraphEdge reverseEdge = new GraphEdge(edge.getSecondNode(), edge.getFirstNode(), 0);
                flows.put(reverseEdge, (((flows.containsKey(reverseEdge)) ? flows.get(reverseEdge) : 0) - minCf)); //TODO uncomment
            }
            path = findPath(source, sink, new LinkedList<GraphEdge>());
//            System.out.println((path != null)?path.getFirst():null);
//            System.out.println(path);
        }

//        System.out.println(flows);
        int flow = 0;
        for (GraphEdge edge : graph.getIncidentalEdges(source))
            if (edge.getFirstNode().equals(source))
                flow += ((flows.containsKey(edge)) ? flows.get(edge) : 0);

        System.out.println("Time: " + (System.currentTimeMillis() - startTime));
        return flow;
    }

    private static LinkedList<GraphEdge> findPath(GraphNode startNode, GraphNode endNode, LinkedList<GraphEdge> path) throws NodeNotFoundException {
        long startTime = System.currentTimeMillis();
        visitedNodes = new HashMap<>();
        visitedNodes.put(startNode, null);

        Queue<Pair<GraphNode, Pair<GraphNode, Integer>>> stack = new LinkedList<>();
        Pair<GraphNode, Pair<GraphNode, Integer>> head;
        for (GraphEdge edge : graph.getIncidentalEdges(startNode))
            stack.add(new Pair<>(edge.getSecondNode(), new Pair<>(edge.getFirstNode(), edge.getWeight())));

        do {
            head = stack.remove();
            int cf = head.getValue().getValue() - (flows.get(new GraphEdge(head.getValue().getKey(), head.getKey(), head.getValue().getValue())) != null ? flows.get(new GraphEdge(head.getValue().getKey(), head.getKey(), head.getValue().getValue())) : 0);
            if (cf > 0) {
                if (!visitedNodes.containsKey(head.getKey())) {
                    visitedNodes.put(head.getKey(), head.getValue());
                    if (head.getKey().equals(endNode)) {
                        GraphNode node = endNode;
                        LinkedList<GraphEdge> newPath = new LinkedList<>();
                        while (visitedNodes.get(node) != null) {
                            newPath.addFirst(new GraphEdge(visitedNodes.get(node).getKey(), node, visitedNodes.get(node).getValue()));
                            node = visitedNodes.get(node).getKey();
                        }
//                        System.out.println((System.currentTimeMillis()-startTime));
                        return newPath;
                    } else {
                        for (GraphEdge edge : graph.getIncidentalEdges(head.getKey())) {

                            cf = edge.getWeight() - (flows.get(edge) != null ? flows.get(edge) : 0);
                            if (cf > 0) {
                                stack.add(new Pair<>(edge.getSecondNode(), new Pair<>(edge.getFirstNode(), edge.getWeight())));
                            }
                        }
                    }
                }
            }

        } while (!stack.isEmpty());
        return null;
    }
}
