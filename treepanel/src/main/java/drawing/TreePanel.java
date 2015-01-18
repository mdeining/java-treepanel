package drawing;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import layout.Node;
import layout.Settings;

@SuppressWarnings("serial")
public class TreePanel extends JPanel {
	
	private Node root;
	private Settings settings;
	
	public TreePanel() {
		super();
		this.setBackground(Color.WHITE);
	}
	
	public void clear() {
		root = null;
		settings = null;
		this.repaint();
	}

	public void paint(Settings settings, Node root){
		this.root = root;
		this.settings = settings;
		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(root == null)
			return;
		
		paintEdges(g, root, 0);
		paintNodes(g, root, 0);
	}

	private void paintEdges(Graphics g, Node node, int level) {
		if(level >= settings.getMaxDepth())
			return;

		int x = node.getX();
		int y = node.getY();
		int w = node.getWidth();
		int h = node.getHeight();
		
		for(Node child : node){
			int xc = child.getX();
			int yc = child.getY();
			int wc = child.getWidth();
			int hc = child.getHeight();
			
			int xs = 0, ys = 0, xe = 0, ye = 0;
			
			switch(settings.getRootOrientation()){
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
		
		for(Node child : node)
			paintEdges(g, child, level + 1);
	}

	private void paintNodes(Graphics g, Node node, int level) {
		if(level > settings.getMaxDepth())
			return;
		
		g.drawRect(node.getX(), node.getY(), node.getWidth(), node.getHeight());
		g.drawString(node.getData(), node.getX() + 2, node.getY() + 15);
		for(Node child : node)
			paintNodes(g, child, level + 1);
	}

}
