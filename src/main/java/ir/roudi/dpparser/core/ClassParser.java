package ir.roudi.dpparser.core;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

import java.util.ArrayList;
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

    public ClassParser(ClassOrInterfaceDeclaration clazz) {
        this.clazz = clazz;
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

    public boolean isClassAbstract() {
        return clazz.isAbstract();
    }

    public boolean isClassStatic() {
        return clazz.isStatic();
    }

    public boolean isClassFinal() {
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

    public String extractChildren(List<ClassOrInterfaceDeclaration> allClasses) {
        List<ClassOrInterfaceDeclaration> children = new ArrayList<>();
        allClasses.forEach(possibleChild -> {

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

}
