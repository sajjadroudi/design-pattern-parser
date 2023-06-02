package ir.roudi.dpparser;

import ir.roudi.dpparser.core.ClassContainer;
import ir.roudi.dpparser.core.ClassFinder;
import ir.roudi.dpparser.core.JavaProjectParser;
import ir.roudi.dpparser.graph.GraphHandler;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        handleGraph();
    }

    private static void handleGraph() throws IOException {
        var path = "C:\\Users\\roudi\\Desktop\\Open Source OO project-20230602\\1 - QuickUML 2001";
        var finder = new ClassFinder();
        var classes = finder.findAllClasses(path);
        var classContainer = new ClassContainer(classes);
        var graph = new GraphHandler(classContainer);
        graph.displayGraph();
//        graph.saveGraphAsImage("test.jpg");
    }

    private static void saveToExcelFile() throws IOException {
        var projectParser = new JavaProjectParser();

        var path = "C:\\Users\\roudi\\Desktop\\Open Source OO project-20230423";
        var file = new File(path);
        for(var f : file.listFiles()) {
            var name = f.getName();
            var p = f.getAbsolutePath();
            System.out.println(name + " | " + path);
            projectParser.parse(
                    p,
                    name + ".xlsx"
            );
        }
    }

}