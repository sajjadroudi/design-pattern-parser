package ir.roudi.dpparser;

import ir.roudi.dpparser.core.JavaProjectParser;

import java.io.File;
import java.io.IOException;

public class Main {

    private interface DoOnEachProjectCallback {
        void doOnProject(String projectName, JavaProjectParser projectParser) throws IOException;
    }

    public static void main(String[] args) throws IOException {
        var parser = new JavaProjectParser("C:\\ood\\Microservice Open Source Project\\spring-cloud-netflix-main");
        parser.saveToExcel("test.xlsx");
//        doOnEachProject((projectName, projectParser) -> {
//            projectParser.saveToExcel("outputs/" + projectName + ".xlsx");
//        });
    }

    private static void doOnEachProject(DoOnEachProjectCallback callback) throws IOException {
        var root = "C:\\ood\\Open Source OO project";
        var file = new File(root);
        for(var f : file.listFiles()) {
            var name = f.getName();
            var path = f.getAbsolutePath();
            callback.doOnProject(name, new JavaProjectParser(path));
        }
    }

}