package ir.roudi.dpparser.graph;

import ir.roudi.dpparser.utils.Pair;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EdgeContainer {

    private final Map<Pair<Node, Node>, Edge> edges;

    public EdgeContainer() {
        this.edges = new HashMap<>();
    }

    public Edge getEdge(Node from, Node to) {
        return edges.get(new Pair<>(from, to));
    }

    public void putEdge(Edge edge) {
        var key = new Pair<>(edge.getFrom(), edge.getTo());
        edges.put(key, edge);
    }

    public Collection<Edge> getEdges() {
        return edges.values();
    }

}
