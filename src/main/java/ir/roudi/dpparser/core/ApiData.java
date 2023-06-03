package ir.roudi.dpparser.core;

public class ApiData {

    private final String methodName;
    private final String httpMethod;
    private final String parameters;

    public ApiData(String methodName, String httpMethod, String parameters) {
        this.methodName = methodName;
        this.httpMethod = httpMethod;
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return methodName + "{" + (httpMethod == null ? "" : "method='" + httpMethod + "', ") +
                "parameters='" + parameters + '\'' +
                '}';
    }

}
