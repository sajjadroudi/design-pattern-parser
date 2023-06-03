package ir.roudi.dpparser.core;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.List;

public class ApiVisitor extends VoidVisitorAdapter<List<ApiData>> {

    @Override
    public void visit(MethodDeclaration methodDeclaration, List<ApiData> apiDataList) {
        var annotation = methodDeclaration.getAnnotationByName("RequestMapping");
        if (annotation.isPresent()) {
            AnnotationExpr requestMappingAnnotation = annotation.get();
            String httpMethod = null;
            if (requestMappingAnnotation.isNormalAnnotationExpr()) {
                NodeList<MemberValuePair> pairs = requestMappingAnnotation.asNormalAnnotationExpr().getPairs();
                for (MemberValuePair pair : pairs) {
                    if (pair.getNameAsString().equals("method") && pair.getValue().isFieldAccessExpr()) {
                        FieldAccessExpr methodExpr = pair.getValue().asFieldAccessExpr();
                        httpMethod = methodExpr.getNameAsString();
                    }
                }
            }

            var params = methodDeclaration.getParameters().toString();
            var data = new ApiData(
                    methodDeclaration.getNameAsString(),
                    httpMethod,
                    params
            );
            apiDataList.add(data);
        }

        super.visit(methodDeclaration, apiDataList);
    }
}