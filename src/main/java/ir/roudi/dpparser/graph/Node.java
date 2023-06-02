package ir.roudi.dpparser.graph;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import java.util.Objects;

public class Node {

    private final ClassOrInterfaceDeclaration clazz;

    public Node(ClassOrInterfaceDeclaration clazz) {
        this.clazz = clazz;
    }

    private String getName() {
        return clazz.getNameAsString();
    }

    private String getFullName() {
        return clazz.getFullyQualifiedName().orElse(getName());
    }

    public String getLabel() {
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(getFullName(), node.getFullName());
    }

    @Override
    public int hashCode() {
        return getFullName().hashCode();
    }

}
