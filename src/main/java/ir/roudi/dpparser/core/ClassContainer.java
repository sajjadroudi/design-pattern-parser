package ir.roudi.dpparser.core;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import java.util.ArrayList;
import java.util.List;

public class ClassContainer {

    private final List<ClassOrInterfaceDeclaration> classes;


    public ClassContainer(List<ClassOrInterfaceDeclaration> classes) {
        this.classes = classes;
    }

    public ClassOrInterfaceDeclaration getClass(String canonicalName) {
        return classes.stream()
                .filter(clazz -> clazz.getFullyQualifiedName().orElse("").equals(canonicalName))
                .findFirst()
                .orElse(null);
    }

    public ClassOrInterfaceDeclaration getClassFromItsName(String name) {
        return classes.stream()
                .filter(clazz -> clazz.getNameAsString().equals(name))
                .findFirst()
                .orElse(null);
    }

    public List<ClassOrInterfaceDeclaration> getAllClasses() {
        return new ArrayList<>(classes);
    }

}
