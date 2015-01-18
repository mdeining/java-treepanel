package drawing;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import layout.LayoutAlgorithm.Orientation;
import layout.Node;

@SuppressWarnings("serial")
public class TreePanel extends JPanel {
	
	private Node apex = null;
	private Orientation orientation = null;
	
	public TreePanel() {
		super();
		this.setBackground(Color.WHITE);
	}
	
	public void paint(Node apex){
		this.paint(apex, Orientation.NORTH);
	}

	public void paint(Node apex, Orientation orientation){
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

	private void paintEdges(Graphics g, Node node) {
		int x = (int)node.xCoordinate;
		int y = (int)node.yCoordinate;
		int w = node.getWidth();
		int h = node.getHeight();
		
		for(Node child : node.children){
			int xc = (int)child.xCoordinate;
			int yc = (int)child.yCoordinate;
			int wc = child.getWidth();
			int hc = child.getHeight();
			
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
		
		for(Node child : node.children)
			paintEdges(g, child);
	}

	private void paintNodes(Graphics g, Node node) {
		g.drawRect((int)node.xCoordinate, (int)node.yCoordinate, node.getWidth(), node.getHeight());
		g.drawString(node.data, (int)node.xCoordinate + 2, (int)node.yCoordinate + 15);
		for(Node child : node.children)
			paintNodes(g, child);
	}

}
