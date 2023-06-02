package ir.roudi.dpparser.graph;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Edge {

    private final Set<Relationship> relationships;
    private final Node from;
    private final Node to;

    public Edge(Node from, Node to, Relationship relationship) {
        this.from = from;
        this.to = to;

        relationships = new HashSet<>();
        relationships.add(relationship);
    }

    public void addRelationship(Relationship relationship) {
        relationships.add(relationship);
    }

    public String getWeight() {
        var weight = 1;
        for(Relationship relationship : relationships)
            weight *= relationship.getValue();

        if(weight == 1)
            throw new IllegalStateException("Weight can not be 1");

        return String.valueOf(weight);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Objects.equals(from, edge.from) && Objects.equals(to, edge.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    public Node getFrom() {
        return from;
    }

    public Node getTo() {
        return to;
    }
}
