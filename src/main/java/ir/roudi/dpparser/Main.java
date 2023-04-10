package ir.roudi.dpparser;

import ir.roudi.dpparser.core.ClassFinder;
import ir.roudi.dpparser.core.ClassParser;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        var finder = new ClassFinder();
        var projectPath = "C:\\Users\\Sajjad\\Desktop\\1 - QuickUML 2001";
        var classes = finder.findAllClasses(projectPath);

        for(var clazz : classes) {
            var parser = new ClassParser(clazz);
            System.out.println(parser.extractClassName() + " -> " + parser.extractExtendedClasses());
        }
    }

}