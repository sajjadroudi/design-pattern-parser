package ir.roudi.dpparser.graph;

import ir.roudi.dpparser.core.ClassContainer;
import ir.roudi.dpparser.core.ClassParser;

import java.util.Objects;

public class GraphHandler {

    private GraphVisualizer visualizer;

    public GraphHandler(ClassContainer classContainer) {
        parse(classContainer);
    }

    private void parse(ClassContainer classContainer) {
        var classes = classContainer.getAllClasses();
        var graph = new Graph();

        for(var clazz : classes) {
            var parser = new ClassParser(clazz, classContainer);

            parser.findAssociatedClasses()
                    .stream()
                    .map(classContainer::getClassFromItsName)
                    .filter(Objects::nonNull)
                    .forEach(target -> {
                        graph.considerEdge(clazz, target, Relationship.ASSOCIATION);
                    });

            parser.findDelegatedClasses()
                    .keySet()
                    .stream()
                    .map(classContainer::getClassFromItsName)
                    .filter(Objects::nonNull)
                    .forEach(target -> {
                        graph.considerEdge(clazz, target, Relationship.DELEGATION);
                    });
        }

        visualizer = new GraphVisualizer(graph.getData());
    }

    public void displayGraph() {
        visualizer.displayOnJFrame();
    }

    public void saveGraphAsImage(String path) {
        visualizer.saveAsImage(path);
    }

}
