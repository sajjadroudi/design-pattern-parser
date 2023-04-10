package ir.roudi.dpparser.core;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import ir.roudi.dpparser.utils.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ClassFinder {

    private final JavaParser javaParser = new JavaParser();

    public List<ClassOrInterfaceDeclaration> findAllClasses(String projectPath) throws IOException {
        return findAllJavaFiles(projectPath)
                .stream()
                .map(file -> {
                    try {
                        return new Pair<>(file, javaParser.parse(file));
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(pair -> {
                    var className = extractClassName(pair.getFirst());
                    return pair.getSecond()
                            .getResult()
                            .get()
                            .getClassByName(className)
                            .orElse(null);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private List<File> findAllJavaFiles(String projectPath) throws IOException {
        Path folderPath = Paths.get(projectPath);
        return Files.walk(folderPath)
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".java"))
                .map(Path::toFile)
                .collect(Collectors.toList());
    }

    private String extractClassName(File file) {
        return file.getName().replace(".java", "");
    }

}
