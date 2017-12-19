package IRCI;

import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import edu.uci.ics.jung.algorithms.generators.random.MixedRandomGraphGenerator;
import edu.uci.ics.jung.algorithms.scoring.VoltageScorer;
import edu.uci.ics.jung.algorithms.scoring.util.VertexScoreTransformer;
import edu.uci.ics.jung.algorithms.util.SelfLoopEdgePredicate;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.Context;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.samples.PluggableRendererDemo;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractPopupGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.decorators.*;
import edu.uci.ics.jung.visualization.decorators.EdgeShape.CubicCurve;
import edu.uci.ics.jung.visualization.decorators.EdgeShape.Line;
import edu.uci.ics.jung.visualization.decorators.EdgeShape.Orthogonal;
import edu.uci.ics.jung.visualization.decorators.EdgeShape.QuadCurve;
import edu.uci.ics.jung.visualization.decorators.EdgeShape.Wedge;
import edu.uci.ics.jung.visualization.picking.PickedInfo;
import edu.uci.ics.jung.visualization.picking.PickedState;
import edu.uci.ics.jung.visualization.renderers.BasicEdgeArrowRenderingSupport;
import edu.uci.ics.jung.visualization.renderers.CenterEdgeArrowRenderingSupport;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.Predicate;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.ConstantTransformer;
import org.apache.commons.collections15.functors.MapTransformer;

public class ViewGraphController extends JApplet{
    private static final long serialVersionUID = -5345319851341875800L;
    private Graph<String, String> g = null;
    private VisualizationViewer<String, String> vv = null;
    private AbstractLayout<String, String> layout = null;

    public void init(Graph<String, String> g) {
        this.g = g;
        this.layout = new FRLayout<>(this.g);
        this.vv = new VisualizationViewer(this.layout, new Dimension(1280, 1280));
        JRootPane rp = this.getRootPane();
        rp.putClientProperty("defeatSystemEventQueueCheck", true);
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().setBackground(Color.lightGray);
        this.getContentPane().setFont(new Font("Serif", 0, 12));
        this.vv.setGraphMouse(new DefaultModalGraphMouse());
        this.vv.getRenderer().getVertexLabelRenderer().setPosition(Position.AUTO);

//        Transformer<String,Shape> vertexSize = new Transformer<String, Shape>(){
//            public Shape transform(String i) {
//                Rectangle2D rect = new Rectangle2D.Double(-10, -20, 20, 40);
//                // in this case, the vertex is twice as large
//                Article temp = new Article();
////                for(Map.Entry m : MetadataController.articleMap.entrySet()){
////                    temp = (Article)m.getValue();
////                    System.out.println(temp.getReferredtimes());
////                }
//                boolean topic = false;
//                for (String o : MetadataController.articleMap.keySet()) {
//                    if (MetadataController.articleMap.get(o).equals(i)) {
//                       topic = true;
//                    }
//                }
//                if (MetadataController.articleMap.get(i).getReferredtimes() >= 1) return AffineTransform
//                        .getScaleInstance(MetadataController.articleMap.get(i).getReferredtimes(), MetadataController.articleMap.get(i).getReferredtimes())
//                        .createTransformedShape(rect);
//                else if(topic) return rect;
//                else return rect;
//            }
//        };
//        this.vv.getRenderContext().setVertexShapeTransformer(vertexSize);
        this.vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        this.vv.setForeground(Color.black);
        this.getContentPane().add(this.vv);
    }

}
