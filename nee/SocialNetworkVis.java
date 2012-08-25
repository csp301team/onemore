package prefuse.demos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPopupMenu;

import prefuse.data.*;
import prefuse.data.event.TupleSetListener;
import prefuse.data.io.*;
import prefuse.data.tuple.TupleSet;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.render.*;
import prefuse.util.*;
import prefuse.action.assignment.*;
import prefuse.Constants;
import prefuse.visual.*;
import prefuse.action.*;
import prefuse.activity.*;
import prefuse.action.layout.graph.*;
import prefuse.controls.*;

class FinalRenderer extends AbstractShapeRenderer
{protected Ellipse2D m_box = new Ellipse2D.Double();
@Override
protected Shape getRawShape(VisualItem item) 
{m_box.setFrame(item.getX(), item.getY(), (Integer) 10, (Integer) 10);
return m_box;
}
}


/*class FinalControlListener extends ControlAdapter implements Control {
	public void itemClicked(Graph abhi,VisualItem item, MouseEvent e) 
	{
		if(item instanceof NodeItem)
		{
			int title = ((int) abhi.NODES.indexOf("name"));
			//int age = (Integer) item.get("age");

			JPopupMenu jpub = new JPopupMenu();
			jpub.add("Title: " + title);
			//jpub.add("Age: " + age);
			jpub.show(e.getComponent(),(int) item.getX(), (int) item.getY());
		}
	}
	
}*/
public class SocialNetworkVis {

    public static void main(String argv[]) {

        // 1. Load the data

        Graph graph = null;
        /* graph will contain the core data */
        try {
            graph = new GraphMLReader().readGraph("data/abh.xml");
        /* load the data from an XML file */
        } catch (DataIOException e) {
            e.printStackTrace();
            System.err.println("Error loading graph. Exiting...");
            System.exit(1);
        }
graph.getNode(0);
        // 2. prepare the visualization

        Visualization vis = new Visualization();
        /* vis is the main object that will run the visualization */
        vis.add("abh", graph);
        /* add our data to the visualization */
        //VisualGraph vg = vis.addGraph("abh", graph);
        //vis.setInteractive("abh.edges", null, false);
        //vis.setValue("abh.nodes", null, VisualItem.SHAPE,
           //     new Integer(Constants.SHAPE_ELLIPSE));
      //fix selected focus nodes
        TupleSet focusGroup = vis.getGroup(Visualization.FOCUS_ITEMS); 
        focusGroup.addTupleSetListener(new TupleSetListener() {
            public void tupleSetChanged(TupleSet ts, Tuple[] add, Tuple[] rem)
            {
                for ( int i=0; i<rem.length; ++i )
                    ((VisualItem)rem[i]).setFixed(false);
                for ( int i=0; i<add.length; ++i ) {
                    ((VisualItem)add[i]).setFixed(false);
                    ((VisualItem)add[i]).setFixed(true);
                }
                if ( ts.getTupleCount() == 0 ) {
                    ts.addTuple(rem[0]);
                    ((VisualItem)rem[0]).setFixed(false);
                }
              //  vis.run("draw");
            }
        });
        
        // 3. setup the renderers and the render factory

        // labels for name
        LabelRenderer nameLabel = new LabelRenderer("gender");
        nameLabel.setRoundedCorner(0, 0);
        //nameLabel.setTextField("AHUUJA");
        FinalRenderer s=new FinalRenderer();
        /* nameLabel decribes how to draw the data elements labeled as "name" */

        // create the render factory
        vis.setRendererFactory(new DefaultRendererFactory(s));

        // 4. process the actions

        // colour palette for nominal data type
        int[] palette = new int[]{ColorLib.rgb(2, 180, 18)};
        /* ColorLib.rgb converts the colour values to integers */


        // map data to colours in the palette
        ColorAction fill = new DataColorAction("abh.nodes", "gender", Constants.NOMINAL, VisualItem.FILLCOLOR, palette);
        /* fill describes what colour to draw the graph based on a portion of the data */
        
        //DataShapeAction shape=new DataShapeAction("abh.nodes","gender");
        //shape.setDefaultShape(Constants.SHAPE_ELLIPSE);
        //FontAction font=new FontAction("abh.nodes");
        //font.getFont(item)//setDefaultFont();
        // node text
        ColorAction text = new ColorAction("abh.nodes", VisualItem.TEXTCOLOR, ColorLib.color(Color.yellow));
        ColorAction fil = new ColorAction("abh.nodes", 
                VisualItem.FIXED, ColorLib.rgb(255,19999900,1));
        fil.add(VisualItem.HIGHLIGHT, ColorLib.rgb(2,29999,1));
        /* text describes what colour to draw the text */

        // edge
        ColorAction edges = new ColorAction("abh.edges", VisualItem.STROKECOLOR, ColorLib.gray(200));
        /* edge describes what colour to draw the edges */

        // combine the colour assignments into an action list
        ActionList colour = new ActionList();
        colour.add(fill);
        colour.add(fil);
        colour.add(text);
        colour.add(edges);

        
        
        vis.putAction("colour", colour);
        /* add the colour actions to the visualization */
        ActionList shaper= new ActionList();
        //shaper.add(shape);
        //vis.putAction("shape", shaper);
        // create a separate action list for the layout
        ActionList layout = new ActionList(Activity.INFINITY);
        layout.add(new ForceDirectedLayout("abh",true, false));
        /* use a force-directed graph layout with default parameters */

        layout.add(new RepaintAction());
        /* repaint after each movement of the graph nodes */

        vis.putAction("layout", layout);
        /* add the layout actions to the visualization */

        // 5. add interactive controls for visualization

        Display display = new Display(vis);
        display.setSize(700, 700);
        display.pan(350, 350);	// pan to the middle
        display.addControlListener(new DragControl());
        /* allow items to be dragged around */

        display.addControlListener(new PanControl());
        /* allow the display to be panned (moved left/right, up/down) (left-drag)*/
String[] MK={"name","gender"};
        display.addControlListener(new ZoomControl());
        /* allow the display to be zoomed (right-drag) */
        //display.addControlListener(new ToolTipControl("label"));??
      //  display.addControlListener(new FinalControlListener());
        display.addControlListener(new NeighborHighlightControl());
        display.addControlListener(new FocusControl(1));
        display.addControlListener(new ToolTipControl(MK));
    
       // display.addControlListener(new ToolTipControl("gender"));

        // 6. launch the visualizer in a JFrame
        

        JFrame frame = new JFrame("prefuse tutorial: abh");
        /* frame is the main window */

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(display);
        /* add the display (which holds the visualization) to the window */

        frame.pack();
        frame.setVisible(true);

        /* start the visualization working */
        vis.run("colour");
        vis.run("layout");
        vis.run("shape");
        //for(int i=0;i<105;i++){
        //System.out.println(graph.getDegree(i));}

    }
}
