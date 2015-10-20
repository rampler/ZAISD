package pl.agh.mkotlarz.zaids.fordbellman;

import pl.agh.mkotlarz.zaids.graph.GraphEdge;
import pl.agh.mkotlarz.zaids.graph.GraphNode;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Mateusz on 20.10.2015.
 */
public class FordBellManAlgorithm {

    HashMap<GraphNode, Integer> weights = new HashMap<>();
    HashMap<GraphNode, GraphNode> predecessors = new HashMap<>();

    public HashMap<GraphNode, Integer> getShortestPath(LinkedList<GraphNode> nodes, LinkedList<GraphEdge> edges, GraphNode source) throws Exception {
        long startTime = System.currentTimeMillis();
        for (GraphNode node : nodes) {
            if (node.equals(source))
                weights.put(node, 0);
            else
                weights.put(node, Integer.MAX_VALUE);
            predecessors.put(node, null);
        }

        for (int i = 1; i < nodes.size() - 1; i++) {
            for (GraphEdge edge : edges) {
                if (weights.get(edge.getFirstNode()) != Integer.MAX_VALUE) {
                    if (weights.get(edge.getFirstNode()) + edge.getWeight() < weights.get(edge.getSecondNode())) {
                        weights.put(edge.getSecondNode(), (weights.get(edge.getFirstNode()) + edge.getWeight()));
                        predecessors.put(edge.getSecondNode(), edge.getFirstNode());
                    }
                }
            }
        }

        for (GraphEdge edge : edges) {
            if (weights.get(edge.getFirstNode()) + edge.getWeight() < weights.get(edge.getSecondNode()))
                throw new Exception("Graph contains a negative-weight cycle");
        }
        System.out.println("Calculation Time: " + (System.currentTimeMillis() - startTime) + " ms");
        return weights;
    }

    public HashMap<GraphNode, Integer> getWeights() {
        return weights;
    }

    public HashMap<GraphNode, GraphNode> getPredecessors() {
        return predecessors;
    }
}
