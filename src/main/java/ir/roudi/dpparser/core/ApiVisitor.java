package ir.roudi.dpparser.core;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.Set;

public class ApiVisitor extends VoidVisitorAdapter<Set<ApiData>> {

    @Override
    public void visit(MethodDeclaration methodDeclaration, Set<ApiData> apiDataSet) {
        var requestMapping = methodDeclaration.getAnnotationByName("RequestMapping");
        if (requestMapping.isPresent()) {
            var params = methodDeclaration.getParameters().toString();
            var data = new ApiData(
                    methodDeclaration.getNameAsString(),
                    params
            );
            apiDataSet.add(data);
        }
    }
}