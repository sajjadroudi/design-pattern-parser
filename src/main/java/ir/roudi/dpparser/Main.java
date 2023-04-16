package ir.roudi.dpparser;

import ir.roudi.dpparser.core.ClassFinder;
import ir.roudi.dpparser.core.ClassParser;

import java.io.IOException;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        var finder = new ClassFinder();
        var projectPath = "C:\\Users\\roudi\\Desktop\\1 - QuickUML 2001";
        var classes = finder.findAllClasses(projectPath);

        for(var clazz : classes) {
            var parser = new ClassParser(clazz);
            System.out.println(clazz.getNameAsString() + " -> " + parser.extractChildren(classes));
        }
    }

}