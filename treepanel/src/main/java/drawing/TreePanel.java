package drawing;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import layout.Node;
import layout.Root;

@SuppressWarnings("serial")
public class TreePanel extends JPanel {
	
	private Alignment horizontalAlignment, verticalAlignment;	
	private Root root;

	public TreePanel() {
		super();
		this.setBackground(Color.WHITE);
	}
	
	public void clear() {
		root = null;
		this.repaint();
	}

	public void setHorizontalAlignment(Alignment horizontalAlignment) {
		this.horizontalAlignment = horizontalAlignment;
	}

	public void setVerticalAlignment(Alignment verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
	}

	public void paint(Root root){
		this.root = root;
		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(root == null)
			return;
		
		int treeWidth = root.getDrawingWidth();
		int treeHeight = root.getDrawingHeight();
		
		int xOffset, yOffset;
		
			switch(horizontalAlignment){
				case LEFT: xOffset = 0; break;
				case ROOT_CENTER: xOffset = -root.getX() + (this.getWidth() - root.getWidth()) / 2; break;
				case TREE_CENTER: xOffset = (this.getWidth() - treeWidth) / 2; break;
				case RIGHT: xOffset = this.getWidth() - treeWidth - 1; break;
				default: xOffset = 0;
			}

			switch(verticalAlignment){
				case TOP: yOffset = 0; break;
				case ROOT_CENTER:  yOffset = -root.getY() + (this.getHeight() - root.getHeight()) / 2; break;
				case TREE_CENTER: yOffset = (this.getHeight() - treeHeight) / 2; break;
				case BOTTOM: yOffset = this.getHeight() - treeHeight - 1; break;
				default: yOffset = 0;
			}
		
		paintEdges(g, root, 0, xOffset, yOffset);
		paintNodes(g, root, 0, xOffset, yOffset);
	}

	private void paintEdges(Graphics g, Node node, int level, int xOffset, int yOffset) {
		if(level >= root.getMaxDepth())
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
			
			switch(root.getOrientation()){
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
			
			
			g.drawLine(xs + xOffset, ys + yOffset, xe + xOffset, ye + yOffset);
		}
		
		for(Node child : node)
			paintEdges(g, child, level + 1, xOffset, yOffset);
	}

	private void paintNodes(Graphics g, Node node, int level, int xOffset, int yOffset) {
		if(level > root.getMaxDepth())
			return;
		
		g.drawRect(node.getX() + xOffset, node.getY() + yOffset, node.getWidth(), node.getHeight());
		g.drawString(node.getData(), node.getX() + xOffset + 2, node.getY() + yOffset + 15);
		for(Node child : node)
			paintNodes(g, child, level + 1, xOffset, yOffset);
	}

}
