package ir.roudi.dpparser.core;

import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.Set;

public class InstantiatedClassesVisitor extends VoidVisitorAdapter<Set<String>> {

    @Override
    public void visit(ObjectCreationExpr objectCreation, Set<String> classNames) {
        classNames.add(objectCreation.getType().getNameAsString());
        super.visit(objectCreation, classNames);
    }

}
