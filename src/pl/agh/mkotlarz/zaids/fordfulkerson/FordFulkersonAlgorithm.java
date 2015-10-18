package pl.agh.mkotlarz.zaids.fordfulkerson;

import javafx.util.Pair;
import pl.agh.mkotlarz.zaids.graph.Graph;
import pl.agh.mkotlarz.zaids.graph.GraphEdge;
import pl.agh.mkotlarz.zaids.graph.GraphNode;
import pl.agh.mkotlarz.zaids.graph.exceptions.NodeNotFoundException;

import java.util.*;

/**
 * Created by Mateusz on 17.10.2015.
 */
public class FordFulkersonAlgorithm {

    private static Graph graph;
    private static HashMap<GraphEdge, Integer> flows;
    private static HashMap<GraphEdge, GraphEdge> visitedEdges; //Visited, Previous

    public static int getMaxFlow(Graph graph, GraphNode source, GraphNode sink) throws NodeNotFoundException {
        long startTime = System.currentTimeMillis();

        FordFulkersonAlgorithm.graph = graph;
        flows = new HashMap<>();
        LinkedList<GraphEdge> path = findPath(source, sink, new LinkedList<GraphEdge>());
        System.out.println(path);
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
            System.out.println(path);
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
        if (path.isEmpty())
            visitedEdges = new HashMap<>();

        Stack<Pair<GraphEdge, GraphEdge>> stack = new Stack<>();
        for (GraphEdge edge : graph.getIncidentalEdges(startNode)) {
                int cf = edge.getWeight() - (flows.get(edge) != null ? flows.get(edge) : 0);
                if (cf > 0) {
                    if (edge.getSecondNode().equals(endNode)) {
                        LinkedList<GraphEdge> newPath = new LinkedList<>();
                        GraphEdge prevEdge = edge;
                        newPath.add(edge);
                        while (visitedEdges.get(prevEdge) != null && !prevEdge.getFirstNode().equals(startNode)) {
                            newPath.addFirst(prevEdge);
                            prevEdge = visitedEdges.get(visitedEdges.get(prevEdge));
                        }
                        if(!prevEdge.equals(newPath.getFirst()))
                            newPath.addFirst(prevEdge);
                        return newPath;
                    } else {
                        stack.push(new Pair<>(edge, edge));
                        visitedEdges.put(edge, edge);
                    }
                }
        }
//            stack.push(new Pair<GraphEdge, GraphEdge>(edge, null));

        while (!stack.empty()) {
            Pair<GraphEdge, GraphEdge> head = stack.pop();
            for (GraphEdge edge : graph.getIncidentalEdges(head.getKey().getSecondNode())) {
                if (!visitedEdges.containsKey(edge)) {

                    int cf = edge.getWeight() - (flows.get(edge) != null ? flows.get(edge) : 0);
                    if (cf > 0) {
                        if (edge.getSecondNode().equals(endNode)) {
                            LinkedList<GraphEdge> newPath = new LinkedList<>();
                            GraphEdge prevEdge = head.getKey();
                            newPath.add(edge);
                            while (prevEdge != null && !prevEdge.getFirstNode().equals(startNode)) {
                                newPath.addFirst(prevEdge);
                                prevEdge = visitedEdges.get(prevEdge);
                            }
                            if(prevEdge != null)
                                newPath.addFirst(prevEdge);
                            return newPath;
                        } else {
                            if(!edge.equals(head.getKey()))
                                stack.push(new Pair<>(edge, head.getKey()));
                            visitedEdges.put(edge, head.getKey());
                        }
                    }

                }
            }

        }
        return null;

////        LinkedList<GraphEdge> newPath = new LinkedList<>(path);
//        if (startNode.equals(endNode))
//            return path;
//        else {
//            for (GraphEdge edge : graph.getIncidentalEdges(startNode)) {
//                    int cf = edge.getWeight() - (flows.get(edge) != null ? flows.get(edge): 0);
////                    visitedNodes.put(edge.getSecondNode());
//                    if(cf > 0 && !path.contains(edge)) { //avoid cycle
//                        path.add(edge);
//
//                        LinkedList<GraphEdge> cycPath = findPath(edge.getSecondNode(), endNode, path);
//                        if(cycPath != null)
//                            return cycPath;
//                    }
//            }
//            return null;
//        }
    }
}
