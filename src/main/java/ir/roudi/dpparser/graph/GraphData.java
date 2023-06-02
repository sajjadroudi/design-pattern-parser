package ir.roudi.dpparser.graph;

import java.util.Collection;

public class GraphData {

    private final Collection<Node> nodes;
    private final Collection<Edge> edges;

    public GraphData(Collection<Node> nodes, Collection<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    public Collection<Node> getNodes() {
        return nodes;
    }

    public Collection<Edge> getEdges() {
        return edges;
    }

}
