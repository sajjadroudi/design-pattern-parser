package ir.roudi.dpparser;

import ir.roudi.dpparser.core.ClassFinder;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        var finder = new ClassFinder();
        var projectPath = "C:\\Users\\Sajjad\\Desktop\\1 - QuickUML 2001";
        var classes = finder.findAllClasses(projectPath);
        for(var clazz : classes) {
            var fullName = clazz.getFullyQualifiedName().get();
            System.out.println(fullName);
        }
    }

}