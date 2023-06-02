package ir.roudi.dpparser.graph;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GraphVisualizer {

    private static final int WINDOW_HEIGHT = 300;
    private static final int WINDOW_WIDTH = 400;
    private final mxGraph graph = new mxGraph();
    private final Map<Node, Object> vertices = new HashMap<>();

    public GraphVisualizer(GraphData graphOutput) {
        setupGraph(graphOutput.getNodes(), graphOutput.getEdges());
    }

    private void setupGraph(Collection<Node> nodes, Collection<Edge> edges) {
        Object parent = graph.getDefaultParent();
        setupGraphStyle();
        insertVertices(parent, nodes);
        insertEdges(parent, edges);
        applyLayoutToGraph(parent);
    }

    private void setupGraphStyle() {
        graph.getStylesheet().getDefaultEdgeStyle().put(mxConstants.STYLE_NOLABEL, "0");
        graph.getStylesheet().getDefaultVertexStyle().put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
    }

    private void insertVertices(Object parent, Collection<Node> nodes) {
        for(Node node : nodes) {
            var width = node.getLabel().length() * 7;
            Object nodeObj = graph.insertVertex(parent, null, node.getLabel(), 0, 0, width, 30);
            vertices.put(node, nodeObj);
        }
    }

    private void applyLayoutToGraph(Object parent) {
        mxIGraphLayout layout = new mxHierarchicalLayout(graph);
        layout.execute(parent);
    }

    private void insertEdges(Object parent, Collection<Edge> edges) {
        for(Edge edge : edges) {
            var from = vertices.get(edge.getFrom());
            var to = vertices.get(edge.getTo());
            graph.insertEdge(parent, null, edge.getWeight(), from, to);
        }
    }

    public void displayOnJFrame() {
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        graphComponent.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

        JFrame frame = new JFrame("Graph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(graphComponent);
        frame.pack();
        frame.setVisible(true);
    }

    public void saveAsImage(String path) {
        try {
            BufferedImage image = mxCellRenderer.createBufferedImage(
                    graph,
                    null,
                    1,
                    Color.WHITE,
                    true,
                    null
            );
            File file = new File(path);
            ImageIO.write(image, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
