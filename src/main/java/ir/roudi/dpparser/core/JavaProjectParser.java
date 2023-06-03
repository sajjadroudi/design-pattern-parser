package ir.roudi.dpparser.core;

import ir.roudi.dpparser.ExcelWriter;
import ir.roudi.dpparser.graph.GraphHandler;

import java.io.IOException;

public class JavaProjectParser {

    private final ClassContainer classContainer;
    private final GraphHandler graphHandler;

    public JavaProjectParser(String projectPath) throws IOException {
        var finder = new ClassFinder();
        var classes = finder.findAllClasses(projectPath);
        classContainer = new ClassContainer(classes);

        graphHandler = new GraphHandler(classContainer);
    }

    public void saveToExcel(String outputFileName) throws IOException {
        new ExcelWriter(classContainer, outputFileName).createOutput();
    }

    public void saveGraphAsImage(String outputFileName) {
        graphHandler.saveGraphAsImage(outputFileName);
    }

    public void showGraph() throws IOException {
        graphHandler.displayGraph();
    }

}