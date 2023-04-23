package ir.roudi.dpparser;

import ir.roudi.dpparser.core.JavaProjectParser;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        var projectParser = new JavaProjectParser();
        projectParser.parse(
                "C:\\Users\\roudi\\Desktop\\1 - QuickUML 2001",
                "output.xlsx"
        );
    }

}