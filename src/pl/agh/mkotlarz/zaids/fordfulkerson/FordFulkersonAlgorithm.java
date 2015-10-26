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

    public static int getMaxFlow(Graph graph, GraphNode source, GraphNode sink) throws NodeNotFoundException {
        long startTime = System.currentTimeMillis();

        FordFulkersonAlgorithm.graph = graph;
        flows = new HashMap<>();
        LinkedList<GraphEdge> path = findPath(source, sink, new LinkedList<GraphEdge>());

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
        }

        int flow = 0;
        for (GraphEdge edge : graph.getOutEdges(source))
            if (edge.getFirstNode().equals(source))
                flow += ((flows.containsKey(edge)) ? flows.get(edge) : 0);

        System.out.println("Time: " + (System.currentTimeMillis() - startTime));
        return flow;
    }

    private static LinkedList<GraphEdge> findPath(GraphNode startNode, GraphNode endNode, LinkedList<GraphEdge> path) throws NodeNotFoundException {
        HashMap<GraphNode, Pair<GraphNode, Integer>> visitedNodes = new HashMap<>();
        visitedNodes.put(startNode, null);

        Queue<Pair<GraphNode, Pair<GraphNode, Integer>>> queue = new LinkedList<>();
        Pair<GraphNode, Pair<GraphNode, Integer>> head;
        for (GraphEdge edge : graph.getOutEdges(startNode))
            queue.add(new Pair<>(edge.getSecondNode(), new Pair<>(edge.getFirstNode(), edge.getWeight())));

        do {
            head = queue.remove();
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
                        return newPath;
                    } else {
                        for (GraphEdge edge : graph.getOutEdges(head.getKey())) {

                            cf = edge.getWeight() - (flows.get(edge) != null ? flows.get(edge) : 0);
                            if (cf > 0) {
                                queue.add(new Pair<>(edge.getSecondNode(), new Pair<>(edge.getFirstNode(), edge.getWeight())));
                            }
                        }
                    }
                }
            }

        } while (!queue.isEmpty());
        return null;
    }
}
