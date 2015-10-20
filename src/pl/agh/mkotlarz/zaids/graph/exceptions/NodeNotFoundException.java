package pl.agh.mkotlarz.zaids.graph.exceptions;

import pl.agh.mkotlarz.zaids.graph.GraphNode;

/**
 * Created by Mateusz on 06.10.2015.
 */
public class NodeNotFoundException extends Exception {

    public NodeNotFoundException() {
        super("Node not found. ");
    }

    public NodeNotFoundException(String message) {
        super("Node not found. " + message);
    }

    public NodeNotFoundException(GraphNode graphNode) {
        super("Node " + graphNode.getNodeId() + " not found!");
    }
}
