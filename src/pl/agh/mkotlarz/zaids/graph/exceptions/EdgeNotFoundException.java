package pl.agh.mkotlarz.zaids.graph.exceptions;

import pl.agh.mkotlarz.zaids.graph.GraphEdge;

/**
 * Created by Mateusz on 11.10.2015.
 */
public class EdgeNotFoundException extends Exception {

    public EdgeNotFoundException() {
        super("Edge not found. ");
    }

    public EdgeNotFoundException(String message) {
        super("Edge not found. "+message);
    }
    public EdgeNotFoundException(GraphEdge graphEdge){ super("Edge ("+graphEdge.getFirstNode()+","+graphEdge.getSecondNode()+","+graphEdge.getWeight()+") not found!"); }
}
