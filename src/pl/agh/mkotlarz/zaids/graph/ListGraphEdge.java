package pl.agh.mkotlarz.zaids.graph;

/**
 * Created by Mateusz on 07.10.2015.
 */
public class ListGraphEdge extends GraphEdge {

    private ListGraphEdge prevEdge;
    private ListGraphEdge nextEdge;

    public ListGraphEdge(GraphNode firstNode, GraphNode secondNode, int weight) {
        super(firstNode, secondNode, weight);
    }

    public ListGraphEdge getPrevEdge() {
        return prevEdge;
    }

    public void setPrevEdge(ListGraphEdge prevEdge) {
        this.prevEdge = prevEdge;
    }

    public ListGraphEdge getNextEdge() {
        return nextEdge;
    }

    public void setNextEdge(ListGraphEdge nextEdge) {
        this.nextEdge = nextEdge;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
