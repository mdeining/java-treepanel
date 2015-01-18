package drawing;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import layout.LayoutAlgorithm.Orientation;
import layout.NodeClass;

@SuppressWarnings("serial")
public class TreePanel extends JPanel {
	
	private NodeClass apex = null;
	private Orientation orientation = null;
	
	public TreePanel() {
		super();
		this.setBackground(Color.WHITE);
	}
	
	public void paint(NodeClass apex){
		this.paint(apex, Orientation.NORTH);
	}

	public void paint(NodeClass apex, Orientation orientation){
		this.apex = apex;
		this.orientation = orientation;
		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(apex == null)
			return;
		
		paintEdges(g, apex);
		paintNodes(g, apex);
	}

	private void paintEdges(Graphics g, NodeClass node) {
		int x = (int)node.XCOORD;
		int y = (int)node.YCOORD;
		int w = node.NODE_WIDTH;
		int h = node.NODE_HEIGHT;
		
		for(NodeClass child : node.children){
			int xc = (int)child.XCOORD;
			int yc = (int)child.YCOORD;
			int wc = child.NODE_WIDTH;
			int hc = child.NODE_HEIGHT;
			
			int xs = 0, ys = 0, xe = 0, ye = 0;
			
			switch(orientation){
				case NORTH:
					xs = x + w / 2;
					ys = y + h;
					xe = xc + wc / 2;
					ye = yc;
					break;
				case SOUTH:
					xs = x + w / 2;
					ys = y;
					xe = xc + wc / 2;
					ye = yc + hc;
					break;
				case EAST:
					xs = x + w;
					ys = y + h/2;
					xe = xc;
					ye = yc + hc/2;
					break;
				case WEST:
					xs = x;
					ys = y + h/2;
					xe = xc + wc;
					ye = yc + hc/2;
					break;
			}
			
			
			g.drawLine(xs, ys, xe, ye);
		}
		
		for(NodeClass child : node.children)
			paintEdges(g, child);
	}

	private void paintNodes(Graphics g, NodeClass node) {
		g.drawRect((int)node.XCOORD, (int)node.YCOORD, node.NODE_WIDTH, node.NODE_HEIGHT);
		g.drawString(node.data, (int)node.XCOORD + 2, (int)node.YCOORD + 15);
		for(NodeClass child : node.children)
			paintNodes(g, child);
	}

}
