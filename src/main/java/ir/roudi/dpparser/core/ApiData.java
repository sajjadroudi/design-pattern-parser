package ir.roudi.dpparser.core;

import java.util.Objects;

public class ApiData {

    private final String methodName;
    private final String parameters;

    public ApiData(String methodName, String parameters) {
        this.methodName = methodName;
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return methodName + " {parameters=" + parameters + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiData apiData = (ApiData) o;
        return Objects.equals(methodName, apiData.methodName) && Objects.equals(parameters, apiData.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(methodName, parameters);
    }

}
