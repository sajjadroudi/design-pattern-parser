package ir.roudi.dpparser.core;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.nodeTypes.NodeWithSimpleName;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OverriddenMethodsFinder {

    private final ClassContainer classContainer;
    private final ClassOrInterfaceDeclaration clazz;

    public OverriddenMethodsFinder(ClassContainer classContainer, ClassOrInterfaceDeclaration clazz) {
        this.classContainer = classContainer;
        this.clazz = clazz;
    }

    public List<String> findOverriddenMethods() {
        return clazz.getMethods()
                .stream()
                .map(it -> findBaseMethod(it, extractAllParents()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private String findBaseMethod(MethodDeclaration method, List<ClassOrInterfaceDeclaration> parents) {
        for(var parent : parents) {
            for(var parentMethod : parent.getMethods()) {
                if(doesFollowSameSignature(parentMethod, method)) {
                    var fullyQualifiedNameOptional = parent.getFullyQualifiedName();
                    if(fullyQualifiedNameOptional.isPresent()) {
                        return fullyQualifiedNameOptional.get() + "#" + parentMethod.getNameAsString();
                    }
                }
            }
        }
        return null;
    }

    private boolean doesFollowSameSignature(MethodDeclaration method1, MethodDeclaration method2) {
        if(!method1.getNameAsString().equals(method2.getNameAsString()))
            return false;

        if (method1.getParameters().size() != method2.getParameters().size())
            return false;

        for (int i = 0; i < method1.getParameters().size(); i++) {
            if (!method1.getParameter(i).getType().asString().equals(method2.getParameter(i).getType().asString())) {
                return false;
            }
        }

        return true;
    }

    private List<ClassOrInterfaceDeclaration> extractAllParents() {
        List<String> parents = new ArrayList<>();
        String current = clazz.getNameAsString();

        findAllParents(current, parents);

        return parents.stream()
                .map(classContainer::getClassFromItsName)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private void findAllParents(String current, List<String> allParents) {
        if(current == null)
            return;

        var parents = getDirectParents(current);

        allParents.addAll(parents);

        for(String parentClassName : parents) {
            findAllParents(parentClassName, allParents);
        }
    }
    public List<String> getDirectParents(String className) {
        var clazz = classContainer.getClassFromItsName(className);

        if(clazz == null) {
            return Collections.emptyList();
        }

        List<ClassOrInterfaceType> parents = new ArrayList<>();
        parents.addAll(clazz.getExtendedTypes());
        parents.addAll(clazz.getImplementedTypes());

        return parents.stream()
                .filter(Objects::nonNull)
                .map(NodeWithSimpleName::getNameAsString)
                .collect(Collectors.toList());
    }

}
