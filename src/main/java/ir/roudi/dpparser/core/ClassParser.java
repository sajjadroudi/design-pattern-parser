package ir.roudi.dpparser.core;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.nodeTypes.NodeWithSimpleName;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ClassParser {

    public static final int CLASS_TYPE_ORDINARY = 1;
    public static final int CLASS_TYPE_INTERFACE = 2;
    public static final int CLASS_TYPE_NESTED = 3;

    public static final int CLASS_VISIBILITY_PUBLIC = 1;
    public static final int CLASS_VISIBILITY_PROTECTED = 2;
    public static final int CLASS_VISIBILITY_PRIVATE = 3;
    public static final int CLASS_VISIBILITY_PACKAGE_ACCESS = 4;

    private final ClassOrInterfaceDeclaration clazz;

    private final ClassContainer classContainer;

    public ClassParser(ClassOrInterfaceDeclaration clazz, ClassContainer classContainer) {
        this.clazz = clazz;
        this.classContainer = classContainer;
    }

    public String extractPackageName() {
        var canonicalName = clazz.getFullyQualifiedName().orElse("");
        var packageName = canonicalName.replace("." + extractClassName(), "");
        return packageName;
    }

    public String extractClassName() {
        return clazz.getNameAsString();
    }

    public int extractClassType() {
        if(clazz.isInterface())
            return CLASS_TYPE_INTERFACE;

        if(clazz.isInnerClass())
            return CLASS_TYPE_NESTED;

        return CLASS_TYPE_ORDINARY;
    }

    public int extractClassVisibility() {
        if(clazz.isPublic())
            return CLASS_VISIBILITY_PUBLIC;

        if(clazz.isProtected())
            return CLASS_VISIBILITY_PROTECTED;

        if(clazz.isPrivate())
            return CLASS_VISIBILITY_PRIVATE;

        return CLASS_VISIBILITY_PACKAGE_ACCESS;
    }

    public boolean isAbstract() {
        return clazz.isAbstract();
    }

    public boolean isStatic() {
        return clazz.isStatic();
    }

    public boolean isFinal() {
        return clazz.isFinal();
    }


    public boolean isClassInterface() {
        return clazz.isInterface();
    }

    public String extractExtendedClasses() {
        return clazz.getExtendedTypes().toString();
    }

    public String extractImplementedClasses() {
        return clazz.getImplementedTypes().toString();
    }

    public String extractChildren() {
        List<ClassOrInterfaceDeclaration> children = new ArrayList<>();
        classContainer.getAllClasses().forEach(possibleChild -> {

            var parents = new ArrayList<ClassOrInterfaceType>();
            parents.addAll(possibleChild.getExtendedTypes());
            parents.addAll(possibleChild.getImplementedTypes());

            var isChild = parents.stream()
                    .anyMatch(it -> it.getNameAsString().equals(clazz.getNameAsString()));

            if(isChild)
                children.add(possibleChild);
        });

        return children.stream()
                .map(ClassOrInterfaceDeclaration::getFullyQualifiedName)
                .map(it -> it.orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList())
                .toString();
    }

    public List<String> findOverriddenMethods() {
        return new OverriddenMethodsFinder(classContainer, clazz).findOverriddenMethods();
    }

    public List<String> extractStaticMethods() {
        List<String> staticMethods = new ArrayList<>();
        for (MethodDeclaration method : clazz.getMethods()) {
            if (method.isStatic()) {
                staticMethods.add(method.getNameAsString());
            }
        }
        return staticMethods;
    }
    public List<String> findFinalMethods() {
        return clazz.getMethods().stream()
                .filter(MethodDeclaration::isFinal)
                .map(NodeWithSimpleName::getNameAsString)
                .collect(Collectors.toList());
    }

    public List<String> extractAbstractMethods() {
        return clazz.getMethods().stream()
                .filter(MethodDeclaration::isAbstract)
                .map(NodeWithSimpleName::getNameAsString)
                .collect(Collectors.toList());
    }

    public List<String> extractMethodsDetails() {
        List<String> methodsDetails = new ArrayList<>();
        for (MethodDeclaration method : clazz.getMethods()) {
            String methodName = method.getNameAsString();
            String returnType = method.getTypeAsString();
            List<String> parameters = method.getParameters().stream()
                    .map(parameter -> parameter.getTypeAsString() + " " + parameter.getNameAsString())
                    .collect(Collectors.toList());
            String methodDetails = methodName + "(" + String.join(", ", parameters) + ") : " + returnType;
            methodsDetails.add(methodDetails);
        }
        return methodsDetails;
    }

    public List<String> extractFields() {
        List<String> fields = new ArrayList<>();
        for (var field : clazz.getFields()) {
            fields.add(field.getElementType().asString() + " " + field.getVariable(0).getNameAsString());
        }
        return fields;
    }

    public List<String> extractConstructors() {
        return clazz.getConstructors().stream()
                .map(constructor -> {
                    String modifiers = constructor.getModifiers().toString();
                    String name = constructor.getNameAsString();
                    List<String> parameters = constructor.getParameters().stream()
                            .map(parameter -> parameter.getType().asString())
                            .collect(Collectors.toList());
                    return String.format("%s %s(%s)", modifiers, name, String.join(", ", parameters));
                })
                .collect(Collectors.toList());
    }

    public List<String> extractInstantiatedClasses() {
        var cu = clazz.findCompilationUnit();

        if(!cu.isPresent())
            return List.of();

        var classes = new HashSet<String>();
        var visitor = new InstantiatedClassesVisitor();
        visitor.visit(cu.get(), classes);
        return new ArrayList<>(classes);
    }

    public List<String> findAssociatedClasses() {
        List<String> associatedClasses = new ArrayList<>();
        for (var field : clazz.getFields()) {
            if (field.getElementType().isClassOrInterfaceType()) {
                var classOrInterfaceType = field.getElementType().asClassOrInterfaceType();
                var name = classOrInterfaceType.getNameAsString();
                if (!name.equals(clazz.getNameAsString())) {
                    associatedClasses.add(name);
                }
            }
        }
        return associatedClasses;
    }

}