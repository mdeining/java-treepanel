package trees.panel;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.RoundRectangle2D.Double;
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
		return (T)root.getNode();		
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
				
//		int xOffset = root.getXOffset(style, this.getWidth());
//		int yOffset = root.getYOffset(style, this.getHeight());
		
		layoutAlgorithm.setOffsets(this.getWidth(), this.getHeight());
		
		int xOffset = 0;
		int yOffset = 0;

		
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
		int x = node.getX();
		int y = node.getY();
		int w = node.getWidth(style);
		int h = node.getHeight(style);
		
		Shape shape = style.getShape(node);
		switch(shape){
			case RECTANGLE:			g.drawRect(x, y, w, h); break;
			case ROUNDED_RECTANGLE:	g.drawRoundRect(x, y, w, h, Style.ARC_SIZE, Style.ARC_SIZE); break;
		}		
		drawPointerBoxes(g, node, x, y, w, h, shape);		
		drawLabel(g, node);		
	}

	private void drawPointerBoxes(Graphics g, Node node, int x, int y, int w,
			int h, Shape shape) {
		if(style.hasPointerBoxes()){
			boolean[] childrenState = node.getChildrenState();
			int boxes = childrenState.length;
			int y1 = 0, y2 = 0, x1 = 0, x2 = 0, yp = 0, xp = 0, b = Style.POINTER_BOX_HEIGHT;
			switch(style.getOrientation()){
				case NORTH: y1 = y + h - b; y2 = y1 + b; x1 = x; xp = w / boxes; break;
				case SOUTH: y1 = y + b; y2 = y1 - b; x1 = x; xp = w / boxes;  break;
				case EAST:	x1 = x + w - b; x2 = x1 + b; y1 = y; yp = h / boxes; break;
				case WEST:	x1 = x + b; x2 = x1 - b; y1 = y; yp = h / boxes; break;
			}
			for(int i = 0; i < boxes; i++)
				if(style.hasVerticalOrientation()){
					x2 = x1 + xp;
					drawHorizontalPointerBox(g, y1, y2, x1, x2, childrenState[i], i, boxes);
					x1 = x2;
				}else{ // style.hasHorozontalOrientation()
					y2 = y1 + yp;
					drawVerticalPointerBox(g, x1, x2, y1, y2, childrenState[i], i, boxes);
					y1 = y2;
				}
			fixShape(g, shape, x, y, w, h, childrenState);
		}
	}

	private void drawHorizontalPointerBox(Graphics g, int y1, int y2, int x1, int x2, boolean hasChild, int i, int boxes) {
		g.drawLine(x1, y1, x2, y1);
		if (i != boxes - 1) g.drawLine(x2, y1, x2, y2);
		if(hasChild) return;
				
		g.drawLine(x1, y1, x2, y2);
		g.drawLine(x1, y2, x2, y1);
	}

	private void drawVerticalPointerBox(Graphics g, int x1, int x2, int y1, int y2, boolean hasChild, int i, int boxes) {
		g.drawLine(x1, y1, x1, y2);
		if (i != boxes - 1) g.drawLine(x1, y2, x2, y2);		
		if(hasChild) return;

		g.drawLine(x1, y1, x2, y2);
		g.drawLine(x1, y2, x2, y1);
	}

	private void fixShape(Graphics g, Shape shape, int x, int y, int w, int h, boolean[] childrenState) {
		if(shape == Shape.RECTANGLE)
			return;
		if(childrenState[0] && childrenState[childrenState.length - 1])
			return;
		
		Graphics2D g2 = (Graphics2D) g;
		
		Double rectangle = new RoundRectangle2D.Double();
		rectangle.setRoundRect(x - 1, y - 1, w + 2, h + 2, Style.ARC_SIZE, Style.ARC_SIZE);
		Area rectangleArea = new Area(rectangle);
		Area mask = new Area(new Rectangle2D.Double(x - 1, y - 1, w + 2, h + 2));
		
		mask.subtract(rectangleArea);
		g2.setColor(Color.WHITE);
		g2.fill(mask);
		g2.setColor(Color.BLACK);
	}

	private void drawLabel(Graphics g, Node node) {
		FontMetrics metrics = g.getFontMetrics();
		String[] label = style.getLabel(g, node);
		Rectangle area = style.getDrawingArea(node);
		
//		g.setColor(Color.YELLOW);
		int dx = area.x;
		int dy = area.y;
		int dw = area.width;
		int dh = area.height;
//		g.fillRect(dx, dy, dw, dh);
//		g.setColor(Color.BLACK);
		
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
//			case RECALCULATE:	layoutAlgorithm.recalculateTree(style, root); break;
			case REBUILD:		layoutAlgorithm.positionTree(style, root); break;
		}
		this.repaint();
	}

}
