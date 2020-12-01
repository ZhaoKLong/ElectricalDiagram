package com.mxgraph.examples.swing;

import com.mxgraph.examples.swing.editor.BasicGraphEditor;
import com.mxgraph.examples.swing.editor.EditorMenuBar;
import com.mxgraph.examples.swing.editor.EditorPalette;
import com.mxgraph.examples.swing.editor.TestEditorPalette;
import com.mxgraph.io.mxCodec;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxICell;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxGraphTransferable;
import com.mxgraph.swing.util.mxSwingConstants;
import com.mxgraph.util.*;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;
import org.w3c.dom.Document;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;

/**
 * @description: 测试
 * @author: Zhaokl
 * @create: 2020-09-02 15:51
 **/
public class Test extends BasicGraphEditor {

    public static final NumberFormat numberFormat = NumberFormat.getInstance();

    public Test() {
//        this("修改测试", new CustomGraphComponent(new CustomGraph()));
        this("修改测试", new CustomGraphComponent(new CustomGraph()));
    }

    public Test(String appTitle, mxGraphComponent component) {
        super(appTitle, component);
        final mxGraph graph = graphComponent.getGraph();
        EditorPalette shapesPalette = insertPalette(mxResources.get("shapes"));
        TestEditorPalette treePalette = insertTree("线路");
        addTreeToLibrary("树", new TreePopupMenu());
        shapesPalette.addListener(mxEvent.SELECT, new mxIEventListener() {
            public void invoke(Object sender, mxEventObject evt) {
                Object tmp = evt.getProperty("transferable");
                if (tmp instanceof mxGraphTransferable) {
                    mxGraphTransferable t = (mxGraphTransferable) tmp;
                    Object cell = t.getCells()[0];
                    if (graph.getModel().isEdge(cell)) {
                        ((CustomGraph) graph).setEdgeTemplate(cell);
                    }
                }
            }
        });
        shapesPalette.addTemplate("Container", new ImageIcon(GraphEditor.class.getResource("/com/mxgraph/examples/swing/images/swimlane.png")), "swimlane", 280, 280, "Container");
        shapesPalette.addTemplate("Icon", new ImageIcon(GraphEditor.class.getResource("/com/mxgraph/examples/swing/images/rounded.png")), "icon;image=/com/mxgraph/examples/swing/images/wrench.png", 70, 70, "Icon");
        shapesPalette.addTemplate("Label", new ImageIcon(GraphEditor.class.getResource("/com/mxgraph/examples/swing/images/rounded.png")), "label;image=/com/mxgraph/examples/swing/images/gear.png", 130, 50, "Label");
        shapesPalette.addTemplate("Rectangle", new ImageIcon(GraphEditor.class.getResource("/com/mxgraph/examples/swing/images/rectangle.png")), null, 160, 120, "");
        shapesPalette.addTemplate("Rounded Rectangle", new ImageIcon(GraphEditor.class.getResource("/com/mxgraph/examples/swing/images/rounded.png")), "rounded=1", 160, 120, "");
        shapesPalette.addTemplate("Double Rectangle", new ImageIcon(GraphEditor.class.getResource("/com/mxgraph/examples/swing/images/doublerectangle.png")), "rectangle;shape=doubleRectangle", 160, 120, "");
        shapesPalette.addTemplate("Ellipse", new ImageIcon(GraphEditor.class.getResource("/com/mxgraph/examples/swing/images/ellipse.png")), "ellipse", 160, 160, "");
        shapesPalette.addTemplate("Double Ellipse", new ImageIcon(GraphEditor.class.getResource("/com/mxgraph/examples/swing/images/doubleellipse.png")), "ellipse;shape=doubleEllipse", 160, 160, "");
        shapesPalette.addTemplate("Triangle", new ImageIcon(GraphEditor.class.getResource("/com/mxgraph/examples/swing/images/triangle.png")), "triangle", 120, 160, "");
        shapesPalette.addTemplate("Rhombus", new ImageIcon(GraphEditor.class.getResource("/com/mxgraph/examples/swing/images/rhombus.png")), "rhombus", 160, 160, "");
        shapesPalette.addTemplate("Horizontal Line", new ImageIcon(GraphEditor.class.getResource("/com/mxgraph/examples/swing/images/hline.png")), "line", 160, 10, "");
        shapesPalette.addTemplate("Hexagon", new ImageIcon(GraphEditor.class.getResource("/com/mxgraph/examples/swing/images/hexagon.png")), "shape=hexagon", 160, 120, "");
        shapesPalette.addTemplate("Cylinder", new ImageIcon(GraphEditor.class.getResource("/com/mxgraph/examples/swing/images/cylinder.png")), "shape=cylinder", 120, 160, "");
        shapesPalette.addTemplate("Actor", new ImageIcon(GraphEditor.class.getResource("/com/mxgraph/examples/swing/images/actor.png")), "shape=actor", 120, 160, "");
        shapesPalette.addTemplate("Cloud", new ImageIcon(GraphEditor.class.getResource("/com/mxgraph/examples/swing/images/cloud.png")), "ellipse;shape=cloud", 160, 120, "");
        shapesPalette.addEdgeTemplate("Straight", new ImageIcon(GraphEditor.class.getResource("/com/mxgraph/examples/swing/images/straight.png")), "straight", 120, 120, "");
        shapesPalette.addEdgeTemplate("Horizontal Connector", new ImageIcon(GraphEditor.class.getResource("/com/mxgraph/examples/swing/images/connect.png")), null, 100, 100, "");
        shapesPalette.addEdgeTemplate("Vertical Connector", new ImageIcon(GraphEditor.class.getResource("/com/mxgraph/examples/swing/images/vertical.png")), "vertical", 100, 100, "");
        shapesPalette.addEdgeTemplate("Entity Relation", new ImageIcon(GraphEditor.class.getResource("/com/mxgraph/examples/swing/images/entity.png")), "entity", 100, 100, "");
        shapesPalette.addEdgeTemplate("Arrow", new ImageIcon(GraphEditor.class.getResource("/com/mxgraph/examples/swing/images/arrow.png")), "arrow", 120, 120, "");
    }

    public static class CustomGraphComponent extends mxGraphComponent {
        /**
         * @param graph
         */
        public CustomGraphComponent(mxGraph graph) {
            super(graph);
            // Sets switches typically used in an editor
            //页面是否可缩放
            setPageVisible(true);
            //是否显示点阵
            setGridVisible(true);
            //按钮提示
            setToolTips(true);
            getConnectionHandler().setCreateTarget(true);
            // Loads the defalt stylesheet from an external file
            mxCodec codec = new mxCodec();
            Document doc = mxUtils.loadDocument(GraphEditor.class.getResource("/com/mxgraph/examples/swing/resources/default-style.xml").toString());
            codec.decode(doc.getDocumentElement(), graph.getStylesheet());
            // Sets the background to white
            getViewport().setOpaque(true);
            getViewport().setBackground(Color.WHITE);
        }

        /**
         * Overrides drop behaviour to set the cell style if the target
         * is not a valid drop target and the cells are of the same
         * type (eg. both vertices or both edges).
         */
        public Object[] importCells(Object[] cells, double dx, double dy, Object target, Point location) {
            if (target == null && cells.length == 1 && location != null) {
                target = getCellAt(location.x, location.y);
                if (target instanceof mxICell && cells[0] instanceof mxICell) {
                    mxICell targetCell = (mxICell) target;
                    mxICell dropCell = (mxICell) cells[0];
                    if (targetCell.isVertex() == dropCell.isVertex() || targetCell.isEdge() == dropCell.isEdge()) {
                        mxIGraphModel model = graph.getModel();
                        model.setStyle(target, model.getStyle(cells[0]));
                        graph.setSelectionCell(target);
                        return null;
                    }
                }
            }
            return super.importCells(cells, dx, dy, target, location);
        }
    }

    public static class CustomGraph extends mxGraph {
        /**
         * Holds the edge to be used as a template for inserting new edges.
         */
        protected Object edgeTemplate;

        /**
         * Custom graph that defines the alternate edge style to be used when
         * the middle control point of edges is double clicked (flipped).
         */
        public CustomGraph() {
            setAlternateEdgeStyle("edgeStyle=mxEdgeStyle.ElbowConnector;elbow=vertical");
        }

        /**
         * Sets the edge template to be used to inserting edges.
         */
        public void setEdgeTemplate(Object template) {
            edgeTemplate = template;
        }

        /**
         * Prints out some useful information about the cell in the tooltip.
         */
        public String getToolTipForCell(Object cell) {
            String tip = "<html>";
            mxGeometry geo = getModel().getGeometry(cell);
            mxCellState state = getView().getState(cell);

            if (getModel().isEdge(cell)) {
                tip += "points={";

                if (geo != null) {
                    List<mxPoint> points = geo.getPoints();

                    if (points != null) {
                        Iterator<mxPoint> it = points.iterator();

                        while (it.hasNext()) {
                            mxPoint point = it.next();
                            tip += "[x=" + numberFormat.format(point.getX())
                                    + ",y=" + numberFormat.format(point.getY())
                                    + "],";
                        }

                        tip = tip.substring(0, tip.length() - 1);
                    }
                }

                tip += "}<br>";
                tip += "absPoints={";

                if (state != null) {

                    for (int i = 0; i < state.getAbsolutePointCount(); i++) {
                        mxPoint point = state.getAbsolutePoint(i);
                        tip += "[x=" + numberFormat.format(point.getX())
                                + ",y=" + numberFormat.format(point.getY())
                                + "],";
                    }

                    tip = tip.substring(0, tip.length() - 1);
                }

                tip += "}";
            } else {
                tip += "geo=[";

                if (geo != null) {
                    tip += "x=" + numberFormat.format(geo.getX()) + ",y="
                            + numberFormat.format(geo.getY()) + ",width="
                            + numberFormat.format(geo.getWidth()) + ",height="
                            + numberFormat.format(geo.getHeight());
                }

                tip += "]<br>";
                tip += "state=[";

                if (state != null) {
                    tip += "x=" + numberFormat.format(state.getX()) + ",y="
                            + numberFormat.format(state.getY()) + ",width="
                            + numberFormat.format(state.getWidth())
                            + ",height="
                            + numberFormat.format(state.getHeight());
                }

                tip += "]";
            }

            mxPoint trans = getView().getTranslate();

            tip += "<br>scale=" + numberFormat.format(getView().getScale())
                    + ", translate=[x=" + numberFormat.format(trans.getX())
                    + ",y=" + numberFormat.format(trans.getY()) + "]";
            tip += "</html>";

            return tip;
        }

        /**
         * Overrides the method to use the currently selected edge template for
         * new edges.
         *
         * @param parent
         * @param id
         * @param value
         * @param source
         * @param target
         * @param style
         * @return
         */
        public Object createEdge(Object parent, String id, Object value, Object source, Object target, String style) {
            if (edgeTemplate != null) {
                mxCell edge = (mxCell) cloneCells(new Object[]{edgeTemplate})[0];
                edge.setId(id);
                return edge;
            }
            return super.createEdge(parent, id, value, source, target, style);
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // 将应用程序风格设置为操作系统风格
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        // 阴影颜色：亮灰
        mxSwingConstants.SHADOW_COLOR = Color.LIGHT_GRAY;
        mxConstants.W3C_SHADOWCOLOR = "#D3D3D3";

        // 创建窗口实例并添加基础内容
        Test editor = new Test();
        // 添加菜单栏并展现窗口
        editor.createFrame(new EditorMenuBar(editor)).setVisible(true);
    }
}
