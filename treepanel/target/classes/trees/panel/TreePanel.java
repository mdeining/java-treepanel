package trees.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.RoundRectangle2D.Double;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import trees.building.TreeBuilder;
import trees.layout.Action;
import trees.layout.LayoutAlgorithm;
import trees.layout.Node;
import trees.panel.style.Shape;
import trees.panel.style.Style;

/**
 * A Swing component for displaying recursive tree structures. The placement
 * is calculated with the help of <i>Walker JQ II. A node-positioning algorithm 
 * for general trees. Software-Practice and Experience 1990; 20(7):685-705.</i><p>
 * The usage is straight-forward: You have to supply a Style-object for the panel 
 * first. Then you can hand over the root of a tree (which has to be of the type of
 * the generic parameter T). The node is analyzed for recursive elements and
 * displayed. For a more fine-grained control over these elements the annotations
 * Nodes and Ignore are available.
 * 
 * @see trees.panel.style.Style
 * 
 * @author Marcus Deininger
 * @version 1.1
 *
 * @param <T> The type of the recursive structure.
 */
@SuppressWarnings("serial")
public class TreePanel<T> extends JPanel implements Observer{
	
	private TreeBuilder builder = new TreeBuilder();
	private LayoutAlgorithm layoutAlgorithm = new LayoutAlgorithm();
	
	private PanelOffset<T> offset;
	private T tree;
	private Node root;
	private Style style;
	
	private Map<Object, Color> nodeColors = new HashMap<>();
	private Map<Object, Color> subtreeColors = new HashMap<>();
	
	private Map<Rectangle, Node> placements = new HashMap<>();
	
	/**
	 * NodeSelector objects will notify about a mouse-selection of
	 * a displayed node. This class should only be used for checking
	 * the notification-source in a update method.
	 */
	public class NodeSelector extends Observable{		
		protected void newSelection(Object object){
			this.setChanged();
			this.notifyObservers(object);
			this.clearChanged();
		}		
	}
	
	private NodeSelector selector;
	
	/**
	 * Constructor for initializing the tree panel with a style and a tree.
	 * @param style Describes the layout parameters of the tree.
	 * @param tree The root of the tree to be displayed.
	 */
	public TreePanel(Style style, T tree) {
		super();
		if(style == null)
			this.style = new Style();
		else
			this.style = style;
		
		this.style.addObserver(this);
		this.offset = new PanelOffset<T>(this);
		this.root = null;
		this.setBackground(Color.WHITE);
		this.addUpdateListener();
		this.addSelectionListener();
		this.setTree(tree);
	}

	/**
	 * Constructor for initializing the tree panel with a tree.
	 * This will give rather unwanted results as there is no style.
	 * @param tree The root of the tree to be displayed.
	 */
	public TreePanel(T tree) {
		this(null, tree);
	}

	/**
	 * Constructor for initializing the tree panel with a style.
	 * @param style Describes the layout parameters of the tree.
	 */
	public TreePanel(Style style) {
		this(style, null);
	}

	/**
	 * Constructor for initializing the tree panel without a style or a tree.
	 */
	public TreePanel() {
		this(null, null);
	}
	
	
	private void addUpdateListener() {
		this.addComponentListener(new ComponentAdapter(){

			@Override
			public void componentResized(ComponentEvent e) {
				TreePanel.this.externalRepaint = false;
				offset.set();
			}
		});
	}

	private void addSelectionListener(){
		this.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent event) {
				if(selector == null)
					return;
				int x = event.getX();
				int y = event.getY();
				for(Rectangle r : placements.keySet())
					if(r.x <= x && x <= (r.x + r.width) && r.y <= y && y <= (r.y + r.height)){
						Node selection = placements.get(r);
						selector.newSelection(selection.getModelObject());
						return;
					}
				selector.newSelection(null);
			}
		});
	}
	
	/**
	 * Adds an observer to the set of observers for this object, 
	 * provided that it is not the same as some observer already in the set. 
	 * The order in which notifications will be delivered to multiple 
	 * observers is not specified. Observers will be notified about a 
	 * mouse-(de)selected node. They will receive as an argument the
	 * selected node or null.
	 * 
	 * @param observer - an observer to be added.
	 */
	public void addObserver(Observer observer){
		if(selector == null)
			selector = new NodeSelector();
		selector.addObserver(observer);
	}
	
	/**
	 * Deletes an observer from the set of observers of this object. 
	 * Passing null to this method will have no effect.
	 * @param observer - the observer to be deleted.
	 */
	public void deleteObserver(Observer observer){
		selector.deleteObserver(observer);
		if(selector.countObservers() == 0)
			selector = null;
	}
	
	/**
	 * Clears the observer list so that this object no longer has any observers.
	 */
	public void deleteObservers(){
		selector.deleteObservers();
		selector = null;
	}

	/**
	 * Sets a new tree to  be displayed. Handed over is the root-node
	 * of the tree. This node is analyzed for recursive elements and
	 * displayed. For a more fine-grained control over these elements 
	 * the annotations Nodes and Ignore are available.
	 * @param tree - The root node of the tree to be displayed.
	 */
	public void setTree(T tree){
		this.tree = tree;
		this.root = builder.build(tree, this.style);
		layoutAlgorithm.positionTree(this.style, this.root);
		offset.set();
		this.repaint();
	}
	
	/**
	 * Standard getter.
	 * @return The root of the displayed tree.
	 */
	public T getTree(){
		return tree;		
	}
	
	/**
	 * Clears the currently displayed tree. This is equivalent
	 * to treePanel.setTree(null).
	 */
	public void clear() {
		root = null;
		this.repaint();
	}

	/**
	 * Standard getter.
	 * @return The current displaying style.
	 */
	public Style getStyle() {
		return style;
	}

	/**
	 * Sets a new Style for displaying a tree.
	 * The tree is rebuild according to this new style.
	 * @param style - The style to be set.
	 */
	public void setStyle(Style style) {
		style.deleteObservers();
		this.style.deleteObservers();
		this.style = style;
		this.style.addObserver(this);
		this.rebuild();
		this.repaint();
	}
	
	/**
	 * Colors the supplied nodes.
	 * @param color - The color to be used.
	 * @param nodes - The nodes to be colored.
	 */
	public void setNodeColor(Color color, Object ... nodes){
		for(Object node : nodes)
			nodeColors.put(node, color);
		this.repaint();
	}
	
	/**
	 * Removes the color from the supplied nodes.
	 * @param nodes - The nodes to be uncolored.
	 */
	public void removeNodeColor(Object ... nodes){
		for(Object node : nodes)
			nodeColors.remove(node);
		this.repaint();
	}
	
	/**
	 * Removes all node coloring.
	 */
	public void clearNodeColor(){
		nodeColors.clear();
		this.repaint();
	}
	
	/**
	 * Colors the supplied nodes and their children.
	 * @param color - The color to be used.
	 * @param nodes - The subtrees to be colored.
	 */
	public void setSubtreeColor(Color color, Object ... nodes){
		for(Object node : nodes)
			subtreeColors.put(node, color);
		this.repaint();
	}
	
	/**
	 * Removes the color from the supplied nodes and children.
	 * @param nodes - The subtrees to be uncolored.
	 */
	public void removeSubtreeColor(Object ... nodes){
		for(Object node : nodes)
			subtreeColors.remove(node);
		this.repaint();
	}
	
	/**
	 * Removes all subtree coloring.
	 */
	public void clearSubtreeColor(){
		subtreeColors.clear();
		this.repaint();
	}
	
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Style && arg instanceof Action){
			switch((Action)arg){
				case REPAINT:  		break;
				case REALIGN:		realign(); break;
				case RECALCULATE:	recalculate(); break;
				case REPOSITION:	reposition(); break;
				case REBUILD:		rebuild(); break;
			}
			this.externalRepaint = false;
		}
		this.repaint();
	}

	private void realign() {
		offset.set();
	}

	private void recalculate() {
		layoutAlgorithm.recalculateTree(style, root); 
		offset.set();
	}

	private void reposition() {
		layoutAlgorithm.positionTree(style, root); 
		offset.set();
	}
	
	private void rebuild(){
		this.root = builder.build(this.tree, this.style);
		this.layoutAlgorithm.positionTree(this.style, this.root);
		offset.set();
	}

	protected Node getRoot(){
		return root;
	}
	
	////////////// Paint Component ///////////////
	
	private boolean externalRepaint = true;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(root == null){
			drawRootPointer(g, root);
			return;
		}
				
		placements.clear();		
		if(externalRepaint)
			rebuild();
		
		externalRepaint = true;
		drawRootPointer(g, root);
		
		Font font = this.getFont();
		Color color = this.getForeground();

		drawTree(g, root, 0);
		
		this.setForeground(color);
		this.setFont(font);
	}
	
	////////////// Nodes ///////////////

	private void drawRootPointer(Graphics g, Node root) {
		if(!style.hasRootPointer())
			return;
		
		g.setFont(style.getFont());
		FontMetrics fm = style.getFontMetrics();

		Rectangle r;
		if(root != null)
			r = root.getNodeArea(offset);
		else
			r = new Rectangle(offset.width, offset.height, fm.stringWidth(style.getRootLabel()), fm.getHeight());
		
		int x1 = 0, x2 = 0, y1 = 0, y2 = 0;
		final int arrow = Style.ROOT_ARROW_LENGTH;
		
		switch(style.getOrientation()){
			case NORTH:
				x1 = r.x + r.width / 2; y1 = r.y - arrow; x2 = x1; y2 = y1 + arrow;
				break;
			case SOUTH:
				x1 = r.x + r.width / 2; y1 = r.y + r.height + arrow; x2 = x1; y2 = y1 - arrow;
				break;
			case EAST:
				x1 = r.x - arrow; y1 = r.y + r.height / 2; x2 = r.x; y2 = y1;
				break;
			case WEST:
				x1 = r.x + r.width + arrow; y1 = r.y + r.height / 2; x2 = x1 - arrow; y2 = y1;
				break;
		}
		drawRootPointerArrow(g, x1, y1, x2, y2);
		drawRootPointerText(g, x1, y1, x2, y2);
	}
	
	private void drawRootPointerText(Graphics g, int x1, int y1, int x2, int y2) {
		String rootLabel = style.getRootLabel();
		g.setFont(style.getFont());
		FontMetrics fm = style.getFontMetrics();
		final int rootWidth = fm.stringWidth(rootLabel), nullWidth = fm.stringWidth(Style.NULL);
		final int ascent = fm.getAscent(), descent = fm.getDescent();
		
		Dimension rootDim = this.getStringBounds(g, rootLabel);
		final int height = rootDim.height;
		
		switch(style.getOrientation()){
			case NORTH:
				g.drawString(rootLabel, x1 - rootWidth / 2, y1 - 1);
				if(root == null)
					g.drawString(Style.NULL, x1 - nullWidth / 2, y2 + ascent);
				break;
			case SOUTH:
				g.drawString(rootLabel, x1 - rootWidth / 2, y1 + ascent + 1);
				if(root == null) 
					g.drawString(Style.NULL, x1 - nullWidth / 2, y2 - descent);
				break;
			case EAST:
				g.drawString(rootLabel, x1 - rootWidth - 1, y1 + height / 2);
				if(root == null) 
					g.drawString(Style.NULL, x2, y1 + height / 2);
				break;
			case WEST:
				g.drawString(rootLabel, x1 + 1, y1 + height / 2);
				if(root == null) 
					g.drawString(Style.NULL, x2 - rootWidth, y1 + height / 2);
				break;
		}
	}
	
    private Dimension getStringBounds(Graphics g, String str) {
    	Graphics2D g2 = (Graphics2D)g;
        FontRenderContext frc = g2.getFontRenderContext();
        GlyphVector gv = g2.getFont().createGlyphVector(frc, str);
        Rectangle r = gv.getPixelBounds(null, 0, 0);
        return new Dimension(r.width, r.height);
    }

	private void drawRootPointerArrow(Graphics g, int x1, int y1, int x2, int y2) {
		g.drawLine(x1, y1, x2, y2);
		final int head = Style.ROOT_ARROW_HEAD;
		Point p0 = null, p1 = null, p2 = null; // p0 is the peak, p1 and p2 the wings
		switch(style.getOrientation()){
			case NORTH: 
				p0 = new Point(x2, y2); 
				p1 = new Point(x2 - head, y2 - head*2); 
				p2 = new Point(x2 + head, y2 - head*2);
				break;
			case SOUTH:
				p0 = new Point(x2, y2); 
				p1 = new Point(x2 - head, y2 + head*2); 
				p2 = new Point(x2 + head, y2 + head*2);
				break;
			case EAST:
				p0 = new Point(x2, y2); 
				p1 = new Point(x2 - head*2, y2 - head); 
				p2 = new Point(x2 - head*2, y2 + head);
				break;
			case WEST:
				p0 = new Point(x2, y2); 
				p1 = new Point(x2 + head*2, y2 - head); 
				p2 = new Point(x2 + head*2, y2 + head);
				break;
		}
		
		g.drawLine(p1.x, p1.y, p0.x, p0.y);
		g.drawLine(p2.x, p2.y, p0.x, p0.y);
	}
	
	private void drawTree(Graphics g, Node node, int level) {
		if(node == null || node.isPlaceHolder())
			return;

		if(level > style.getMaxDepth())
			return;
		
		Color currentColor = g.getColor(); // Save current color
		Color nColor = nodeColors.get(node.getModelObject());
		Color sColor = subtreeColors.get(node.getModelObject());
		
		if(sColor != null)
			g.setColor(sColor);
		drawNode(g, node, nColor);
		
		int slots = node.getChildrenSlots(), pos = 0;
		for(Node child : node){
			drawEdge(g, node, child, pos++, slots, level);
			drawTree(g, child, level + 1);
		}
		if(sColor != null)
			g.setColor(currentColor);
	}

	//// Nodes /////////////////////////////
	
	private void drawNode(Graphics g, Node node, Color color) {
		Color currentColor = g.getColor();
		if(color != null)
			g.setColor(color);
		
		Rectangle r = node.getNodeArea(offset);
		Rectangle l = node.getLabelArea(offset);
		
		if(selector != null)
			placements.put(r, node);
		
		Shape shape = style.getShape(node.getModelClass());
		switch(shape){
			case RECTANGLE:
				if(node.isDuplicate()){
					Color currentColor2 = g.getColor();
					g.setColor(Color.LIGHT_GRAY);
					g.fillRect(r.x, r.y, r.width, r.height);
					g.setColor(currentColor2);
				}
				g.drawRect(r.x, r.y, r.width, r.height);
				break;
			case ROUNDED_RECTANGLE:
				if(node.isDuplicate()){
					Color currentColor2 = g.getColor();
					g.setColor(Color.LIGHT_GRAY);
					g.fillRoundRect(r.x, r.y, r.width, r.height, Style.ARC_SIZE, Style.ARC_SIZE);
					g.setColor(currentColor2);
				}
				g.drawRoundRect(r.x, r.y, r.width, r.height, Style.ARC_SIZE, Style.ARC_SIZE); 
				break;
		}		
		drawPointerBoxes(g, node, shape, r.x, r.y, r.width, r.height);		
		drawLabel(g, node, l.x, l.y, l.width, l.height);
		
		if(color != null)
			g.setColor(currentColor);
	}

	private void drawPointerBoxes(Graphics g, Node node, Shape shape, int x, int y, int w, int h) {
		if(style.hasPointerBoxes(node.getModelClass())){
			int boxes = node.getChildrenSlots();
			if(boxes == 0)
				return;
			int y1 = 0, y2 = 0, x1 = 0, x2 = 0, yp = 0, xp = 0, b = Style.POINTER_BOX_HEIGHT;
			switch(style.getOrientation()){
				case NORTH: y1 = y + h - b; y2 = y1 + b; x1 = x; xp = w / boxes; break;
				case SOUTH: y1 = y + b; y2 = y1 - b; x1 = x; xp = w / boxes;  break;
				case EAST:	x1 = x + w - b; x2 = x1 + b; y1 = y; yp = h / boxes; break;
				case WEST:	x1 = x + b; x2 = x1 - b; y1 = y; yp = h / boxes; break;
			}
			for(int i = 0; i < boxes; i++)
				if(style.hasVerticalOrientation()){
					if(i < boxes - 1)
						x2 = x1 + xp;
					else
						x2 = x + w;
					drawHorizontalPointerBox(g, y1, y2, x1, x2, node.hasChild(i), i, boxes);
					x1 = x2;
				}else{ // style.hasHorozontalOrientation()
					if(i < boxes - 1)
						y2 = y1 + yp;
					else
						y2 = y + h;
					drawVerticalPointerBox(g, x1, x2, y1, y2, node.hasChild(i), i, boxes);
					y1 = y2;
				}
			fixShape(g, node, shape, x, y, w, h);
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

	private void fixShape(Graphics g, Node node, Shape shape, int x, int y, int w, int h) {
		if(shape == Shape.RECTANGLE)
			return;
		if(node.hasChild(0) && node.hasChild(node.getChildrenSlots() - 1))
			return;
		
		Color currentColor = g.getColor();
		Graphics2D g2 = (Graphics2D) g;
		
		Double rectangle = new RoundRectangle2D.Double();
		rectangle.setRoundRect(x - 1, y - 1, w + 2, h + 2, Style.ARC_SIZE, Style.ARC_SIZE);
		Area rectangleArea = new Area(rectangle);
		Area mask = new Area(new Rectangle2D.Double(x - 1, y - 1, w + 2, h + 2));
		
		mask.subtract(rectangleArea);
		g2.setColor(Color.WHITE);
		g2.fill(mask);
		g2.setColor(Color.BLACK);
		
		g.setColor(currentColor);
	}

	private void drawLabel(Graphics g, Node node, int x, int y, int w, int h) {
		g.setFont(style.getFont(node.getModelClass()));
		
		FontMetrics metrics = g.getFontMetrics();
		Dimension label = node.getLabelSize();
		
		x = x + (w - label.width) / 2;
		y = y + (h - label.height) / 2;
		
		y = y + metrics.getAscent(); 
		for(String line : node.getLabelLines()){
			g.drawString(line, x, y);
			y = y + metrics.getHeight();
		}
	}
		
	////////////// Edges ///////////////

	private void drawEdge(Graphics g, Node from, Node to, int pos, int slots, int level){			
		if(level >= style.getMaxDepth() || to == null || to.isPlaceHolder())
			return;

		int x = from.getX();
		int y = from.getY();
		int w = from.getWidth();
		int h = from.getHeight();
		
		int xc = to.getX();
		int yc = to.getY();
		int wc = to.getWidth();
		int hc = to.getHeight();
		
		int xs = 0, ys = 0, xe = 0, ye = 0;
		
		switch(style.getOrientation()){
			case NORTH:
				xs = x + (w + 2*pos*w)/(2*slots);
				ys = y + h;
				if(style.hasPointerBoxes(from.getModelClass()))
					ys = ys - Style.POINTER_BOX_HEIGHT / 2;
				xe = xc + wc / 2;
				if((x + w/2) == xe && xs >= xc && xs <= xc + wc) // would have been orthogonal if centered
					xe = xs;
				ye = yc;
				break;
			case SOUTH:
				xs = x + (w + 2*pos*w)/(2*slots);
				ys = y;
				if(style.hasPointerBoxes(from.getModelClass()))
					ys = ys + Style.POINTER_BOX_HEIGHT / 2;
				xe = xc + wc/2;
				if((x + w/2) == xe && xs >= xc && xs <= xc + wc) // would have been orthogonal if centered
					xe = xs;
				ye = yc + hc;
				break;
			case EAST:
				xs = x + w;
				if(style.hasPointerBoxes(from.getModelClass()))
					xs = xs - Style.POINTER_BOX_HEIGHT / 2;
				ys = y + (h + 2*pos*h)/(2*slots);
				xe = xc;
				ye = yc + hc/2;
				if((y + h/2) == ye && ys >= yc && ys <= yc + hc) // would have been orthogonal if centered
					ye = ys;
				break;
			case WEST:
				xs = x;
				if(style.hasPointerBoxes(from.getModelClass()))
					xs = xs + Style.POINTER_BOX_HEIGHT / 2;
				ys = y + (h + 2*pos*h)/(2*slots);
				xe = xc + wc;
				ye = yc + hc/2;
				if((y + h/2) == ye && ys >= yc && ys <= yc + hc) // would have been orthogonal if centered
					ye = ys;
				break;
		}
		
		int xOff = offset.width;
		int yOff = offset.height;
		g.drawLine(xs + xOff, ys + yOff, xe + xOff, ye + yOff);
	}


}
