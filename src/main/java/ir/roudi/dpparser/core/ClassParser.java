package ir.roudi.dpparser.core;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

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

}
