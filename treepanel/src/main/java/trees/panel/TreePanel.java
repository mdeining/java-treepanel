package trees.panel;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import trees.acessing.TreeWrapper;
import trees.layout.LayoutAlgorithm;
import trees.layout.Node;
import trees.layout.Root;
import trees.panel.style.Action;
import trees.panel.style.Shape;
import trees.panel.style.Style;
import trees.panel.style.StyleFactory;

@SuppressWarnings("serial")
public class TreePanel<T> extends JPanel implements Observer{
	
	private TreeWrapper wrapper = new TreeWrapper();
	private LayoutAlgorithm layoutAlgorithm = new LayoutAlgorithm();
	private Root root;
	private Style style;
	
	public TreePanel(T root) {
		this();
		this.setTree(root);
	}

	public TreePanel() {
		super();
		this.style = StyleFactory.getDefaultStyle();
		this.style.addObserver(this);
		this.root = null;
		this.setBackground(Color.WHITE);
	}
	
	public void setTree(T root){
		this.root = wrapper.wrap(root);
		layoutAlgorithm.positionTree(this.style, this.root);
		this.repaint();
	}
	
	@SuppressWarnings("unchecked")
	public T getTree(){
		if(root == null)
			return null;
		return (T)root.getData();		
	}
	
	public void clear() {
		root = null;
		this.repaint();
	}

	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		style.deleteObservers();
		this.style.deleteObservers();
		this.style = style;
		this.style.addObserver(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(root == null)
			return;
				
		int xOffset = root.getXOffset(style, this.getWidth());
		int yOffset = root.getYOffset(style, this.getHeight());
		
		drawEdges(g, root, 0, xOffset, yOffset);
		drawNodes(g, root, 0, xOffset, yOffset);
	}

//	private void drawEdges(Graphics g, Node node, int level, int xOffset, int yOffset) {
//		if(node == null || node.isPlaceHolder())
//			return;
//				
//		if(level >= style.getMaxDepth())
//			return;
//
//		int x = node.getX();
//		int y = node.getY();
//		int w = node.getWidth();
//		int h = node.getHeight();
//		
//		for(Node child : node){
//			if(child == null || child.isPlaceHolder())
//				continue;
//			
//			int xc = child.getX();
//			int yc = child.getY();
//			int wc = child.getWidth();
//			int hc = child.getHeight();
//			
//			int xs = 0, ys = 0, xe = 0, ye = 0;
//			
//			switch(style.getOrientation()){
//				case NORTH:
//					xs = x + w / 2;
//					ys = y + h;
//					xe = xc + wc / 2;
//					ye = yc;
//					break;
//				case SOUTH:
//					xs = x + w / 2;
//					ys = y;
//					xe = xc + wc / 2;
//					ye = yc + hc;
//					break;
//				case EAST:
//					xs = x + w;
//					ys = y + h/2;
//					xe = xc;
//					ye = yc + hc/2;
//					break;
//				case WEST:
//					xs = x;
//					ys = y + h/2;
//					xe = xc + wc;
//					ye = yc + hc/2;
//					break;
//			}
//			
//			
//			g.drawLine(xs + xOffset, ys + yOffset, xe + xOffset, ye + yOffset);
//		}
//		
//		for(Node child : node)
//			drawEdges(g, child, level + 1, xOffset, yOffset);
//	}
	
	private void drawEdges(Graphics g, Node node, int level, int xOffset, int yOffset) {
		if(node == null || node.isPlaceHolder())
			return;
				
		if(level >= style.getMaxDepth())
			return;

		int slots = node.getChildrenState().length, position = 0;
		for(Node child : node)
			if(child != null && !child.isPlaceHolder())
				drawEdge(g, node, child, position, slots, xOffset, yOffset);
		
		for(Node child : node)
			drawEdges(g, child, level + 1, xOffset, yOffset);
	}

	private void drawEdge(Graphics g, Node from, Node to, int position, int slots, int xOffset, int yOffset){

		int x = from.getX();
		int y = from.getY();
		int w = from.getWidth(style);
		int h = from.getHeight(style);
		
		int xc = to.getX();
		int yc = to.getY();
		int wc = to.getWidth(style);
		int hc = to.getHeight(style);
		
		int xs = 0, ys = 0, xe = 0, ye = 0;
		
		switch(style.getOrientation()){
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

	private void drawNodes(Graphics g, Node node, int level, int xOffset, int yOffset) {
		if(node == null || node.isPlaceHolder())
			return;

		if(level > style.getMaxDepth())
			return;
		
		drawNode(g, node, xOffset, yOffset);
		for(Node child : node)
			drawNodes(g, child, level + 1, xOffset, yOffset);
	}
	
	private void drawNode(Graphics g, Node node, int xOffset, int yOffset) {
		int x = node.getX() + xOffset;
		int y = node.getY() + yOffset;
		int w = node.getWidth(style);
		int h = node.getHeight(style);
		
		Shape shape = style.getShape();
		switch(shape){
			case RECTANGLE:			g.drawRect(x, y, w, h); break;
			case ROUNDED_RECTANGLE:	g.drawRoundRect(x, y, w, h, Style.ARC_SIZE, Style.ARC_SIZE); break;
		}
		
		if(style.hasPointerBoxes()){
			boolean[] childrenState = node.getChildrenState();

			int x1 = x, x2 = x + w, y1 = y, y2 = y + h, xp, yp, portion;
			int edge = (shape == Shape.ROUNDED_RECTANGLE ? Style.ARC_OFFSET : 0);
			
			switch(style.getOrientation()){
				case NORTH:
					yp = y + h - Style.POINTER_BOX_HEIGHT; 
					g.drawLine(x1, yp, x2, yp);
					portion = w / childrenState.length;
					for(int i = 0, xb = x + portion; xb < x + w; i++, xb += portion){
						g.drawLine(xb, yp, xb, y2);
						if(!childrenState[i]){
							g.drawLine(xb - portion, yp, xb, y2);
							g.drawLine(xb - portion + (i == 0 ? edge : 0), y2 - (i == 0 ? edge : 0), xb, yp);
						}
					}
					if(!childrenState[childrenState.length - 1]){
						g.drawLine(x2 - portion, yp, x2 - edge, y2 - edge);
						g.drawLine(x2 - portion, y2, x2, yp);
					}
					break;
					
				case SOUTH:
					yp = y + Style.POINTER_BOX_HEIGHT; 
					g.drawLine(x1, yp, x2, yp);
					portion = w / childrenState.length;
					for(int i = 0, xb = x + portion; xb < x + w; i++, xb += portion){
						g.drawLine(xb, yp, xb, y1);
						if(!childrenState[i]){
							g.drawLine(xb - portion, yp, xb, y1);
							g.drawLine(xb - portion + (i == 0 ? edge : 0), y1 + (i == 0 ? edge : 0), xb, yp);
						}
					}
					if(!childrenState[childrenState.length - 1]){
						g.drawLine(x2 - portion, yp, x2 - edge, y1 + edge);
						g.drawLine(x2 - portion, y1, x2, yp);
					}
					break;
					
				case EAST:
					xp = x + w - Style.POINTER_BOX_HEIGHT; 
					g.drawLine(xp, y1, xp, y2);
					portion = h / childrenState.length;
					for(int i = 0, yb = y + portion; yb < y + h; i++, yb += portion){
						g.drawLine(xp, yb, x2, yb);
						if(!childrenState[i]){
							g.drawLine(xp, yb - portion, x2, yb);
							g.drawLine(x2 - (i == 0 ? edge : 0), yb - portion + (i == 0 ? edge : 0), xp, yb);
						}
					}
					if(!childrenState[childrenState.length - 1]){
						g.drawLine(xp, y2 - portion, x2 - edge, y2 - edge);
						g.drawLine(x2, y2 - portion, xp, y2);
					}
				break;
				
				case WEST:
					xp = x + Style.POINTER_BOX_HEIGHT; 
					g.drawLine(xp, y1, xp, y2);
					portion = h / childrenState.length;
					for(int i = 0, yb = y + portion; yb < y + h; i++, yb += portion){
						g.drawLine(xp, yb, x1, yb);
						if(!childrenState[i]){
							g.drawLine(xp, yb - portion, x1, yb);
							g.drawLine(x1 + (i == 0 ? edge : 0), yb - portion + (i == 0 ? edge : 0), xp, yb);
						}
					}
					if(!childrenState[childrenState.length - 1]){
						g.drawLine(xp, y2 - portion, x1 + edge, y2 - edge);
						g.drawLine(x1, y2 - portion, xp, y2);
					}
					break;
			}
		}
		
		FontMetrics metrics = g.getFontMetrics();
		String[] label = style.getLabel(g, node);
		Rectangle area = style.getDrawingArea(node);
		
		g.setColor(Color.YELLOW);
		int dx = area.x;
		int dy = area.y;
		int dw = area.width;
		int dh = area.height;
		g.fillRect(dx, dy, dw, dh);
		g.setColor(Color.BLACK);
		
		dy = dy + metrics.getAscent();
		for(int i = 0; i < label.length; i++){
			String line = label[i];
			int p = (dw - metrics.stringWidth(line))/2;
			g.drawString(line, dx + p, dy);
			dy = dy + metrics.getHeight();
		}
		
	}


	@Override
	public void update(Observable o, Object arg) {
		if(!(o instanceof Style && arg instanceof Action))
			return;
		
		switch((Action)arg){
			case REPAINT:  		break;
			case RECALCULATE:	layoutAlgorithm.recalculateTree(style, root); break;
			case REBUILD:		layoutAlgorithm.positionTree(style, root); break;
		}
		this.repaint();
	}

}
