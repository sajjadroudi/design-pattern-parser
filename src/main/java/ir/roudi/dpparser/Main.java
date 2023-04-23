package ir.roudi.dpparser;

import ir.roudi.dpparser.core.ClassContainer;
import ir.roudi.dpparser.core.ClassFinder;
import ir.roudi.dpparser.core.ClassParser;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        var finder = new ClassFinder();
        var projectPath = "C:\\Users\\Aftab Shargh\\Desktop\\1 - QuickUML 2001";
        var classes = finder.findAllClasses(projectPath);
        var classContainer = new ClassContainer(classes);

       /* ExcelWriter excelWriter = new ExcelWriter(classContainer, "output.xlsx");
        excelWriter.createOutput();*/

        for(var clazz : classContainer.getAllClasses()) {
            var parser = new ClassParser(clazz, classContainer);
            System.out.println(clazz.getNameAsString() + " -> " + parser.findAssociatedClasses());
        }
    }

}