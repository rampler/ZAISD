package pl.agh.mkotlarz.zaids.exceptions;

/**
 * Created by Mateusz on 06.10.2015.
 */
public class NodeNotFoundException extends Exception {

    public NodeNotFoundException() {
        super("Node not found. ");
    }

    public NodeNotFoundException(String message) {
        super("Node not found. "+message);
    }
}
