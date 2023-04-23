package ir.roudi.dpparser.core;

import ir.roudi.dpparser.ExcelWriter;

import java.io.IOException;

public class JavaProjectParser {

    public void parse(String projectPath, String outputFileName) throws IOException {
        var finder = new ClassFinder();
        var classes = finder.findAllClasses(projectPath);
        var classContainer = new ClassContainer(classes);

        ExcelWriter excelWriter = new ExcelWriter(classContainer, outputFileName);
        excelWriter.createOutput();
    }

}
