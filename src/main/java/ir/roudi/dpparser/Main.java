package ir.roudi.dpparser;

import ir.roudi.dpparser.core.JavaProjectParser;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
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