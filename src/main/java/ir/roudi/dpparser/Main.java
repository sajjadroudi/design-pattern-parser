package ir.roudi.dpparser;

import ir.roudi.dpparser.core.JavaProjectParser;

import java.io.File;
import java.io.IOException;

public class Main {

    private interface DoOnEachProjectCallback {
        void doOnProject(String projectName, JavaProjectParser projectParser) throws IOException;
    }

    public static void main(String[] args) throws IOException {
        doOnEachProject((projectName, projectParser) -> {
            projectParser.saveToExcel("outputs/microservice/" + projectName + ".xlsx");
        });
    }

    private static void doOnEachProject(DoOnEachProjectCallback callback) throws IOException {
        var root = "C:\\ood\\Microservice Open Source Project";
        var file = new File(root);
        for(var f : file.listFiles()) {
            var name = f.getName();
            var path = f.getAbsolutePath();
            System.out.println(name + " ...");
            new JavaProjectParser(path).saveGraphAsImage("outputs/microservice/" + name + ".jpg");
            System.gc();
//            callback.doOnProject(name, new JavaProjectParser(path));
        }
    }

}