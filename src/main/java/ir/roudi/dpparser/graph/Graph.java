package ir.roudi.dpparser.graph;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import java.util.*;

public class Graph {

    private final Map<String, Node> nodes;
    private final EdgeContainer edgeContainer;

    public Graph() {
        nodes = new HashMap<>();
        edgeContainer = new EdgeContainer();
    }

    public void considerEdge(
            ClassOrInterfaceDeclaration from,
            ClassOrInterfaceDeclaration to,
            Relationship relationship
    ) {
        considerEdge(new Node(from), new Node(to), relationship);
    }

    private void considerEdge(Node from, Node to, Relationship relationship) {
        putNodeIfAbsent(from);
        putNodeIfAbsent(to);
        putOrUpdateEdge(from, to, relationship);
    }

    private void putNodeIfAbsent(Node node) {
        nodes.putIfAbsent(node.getLabel(), node);
    }

    private void putOrUpdateEdge(Node from, Node to, Relationship relationship) {
        var edge = edgeContainer.getEdge(from, to);

        if(edge == null) {
            edge = new Edge(from, to, relationship);
            edgeContainer.putEdge(edge);
        } else {
            edge.addRelationship(relationship);
        }
    }

    public GraphData getData() {
        var nodes = this.nodes.values();
        var edges = edgeContainer.getEdges();
        return new GraphData(nodes, edges);
    }

}
