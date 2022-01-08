package trees.style;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.Observable;

import javax.swing.JPanel;

import trees.layout.Action;
import static trees.layout.Action.*;
import static trees.style.Orientation.*;

/**
 * A component for configuring the layout of a displayed tree. The style lets
 * you configure:
 * <ul style="list-style-type:disc">
 * <li>The spacings (in pixels) between siblings, subtrees, and levels. (Defaults: 0)</li>
 * <li>The maximum drawing depth, with 0 only displaying the root. (Default: Integer.MAX_VALUE)</li>
 * <li>The orientation, vertical, and horizontal alignment of the tree. (Default: NORTH|TOP|LEFT)</li>
 * <li>The usage and naming of a root label. (Default: none)</li>
 * <li>The usage of pointer boxes - overall or for explicit node classes. (Default: false)</li>
 * <li>The usage of placeholder - overall or for explicit node classes. (Default: false)</li>
 * <li>The label font - overall or for explicit node classes. (Default: JPanel-Font)</li>
 * <li>The node box size - overall or for explicit node classes. (Default: VARIABLE())</li>
 * <li>The node shape - overall or for explicit node classes. (Default: RECTANGLE)</li>
 * </ul>
 * 
 * @see trees.panel.TreePanel
 * 
 * @author Marcus Deininger
 *
 */
public class Style extends Observable{
	
	private static final int DEFAULT_DEPTH = Integer.MAX_VALUE;
	private static final Orientation DEFAULT_ORIENTATION = Orientation.NORTH;	
	private static final Alignment DEFAULT_HORIZONTAL_ALIGNMENT = Alignment.LEFT;
	private static final Alignment DEFAULT_VERTICAL_ALIGNMENT = Alignment.TOP;	
	private static final Shape DEFAULT_SHAPE = Shape.RECTANGLE; 
	private static final Size DEFAULT_SIZE = Size.VARIABLE(); 

	
	public static final int TREE_MARGIN = 8;	
	private static final int MIN_LEVEL_SEPARATION = 4;

	public static String NULL = "null";	
	public static int ROOT_ARROW_LENGTH = 20;
	public static int ROOT_ARROW_HEAD = 4;
	
	public static final int POINTER_BOX_HEIGHT = 8;
	public static final int ARC_SIZE = 10;
	
	public static final int LABEL_MARGIN = 4;	
	public static final Dimension MIN_DIMENSION = new Dimension(0, 0);
	private static final Dimension MAX_DIMENSION = new Dimension(1920, 1080);
	public static final String ETC = "...";

	private static final JPanel panel = new JPanel();
	
	// General settings
	private int maxDepth, siblingSeparation, subtreeSeparation, levelSepartion;
	private Orientation orientation;
	private Alignment horizontalAlignment, verticalAlignment;
	
	private String rootLabel = null;
	
	// Possible class specific settings
	private Value<Font> font = new Value<>();
	private Value<FontMetrics> metrics = new Value<>();
	private Value<Shape> shape = new Value<>();	
	private Value<Size> size = new Value<>();
	private Value<Boolean> pointerBoxes = new Value<>();
	private Value<Boolean> placeHolder = new Value<>();
	
	/**
	 * Initializes style with default values. 
	 * @see trees.style.Style Style
	 */
	public Style(){
		super();
		this.maxDepth = DEFAULT_DEPTH;
		this.siblingSeparation = 0;
		this.subtreeSeparation = 0;
		this.levelSepartion = 0;
		this.orientation = DEFAULT_ORIENTATION;
		this.horizontalAlignment = DEFAULT_HORIZONTAL_ALIGNMENT;
		this.verticalAlignment = DEFAULT_VERTICAL_ALIGNMENT;
		
		this.setFont(panel.getFont());
		this.setShape(DEFAULT_SHAPE);
		this.setSize(DEFAULT_SIZE);
		this.setPointerBoxes(false);
		this.setPlaceHolder(false);
	}

	/**
	 * Initializes separation of siblings, subtrees and levels; 
	 * all others are set to default values. 
	 * @see trees.style.Style Style
	 * @param siblingSeparation Separation of adjacent siblings counted in pixels.
	 * @param subtreeSeparation Separation of adjacent subtrees counted in pixels.
	 * @param levelSepartion Separation of adjacent levels counted in pixels.
	 */
	public Style(int siblingSeparation, int subtreeSeparation, int levelSepartion) {
		this();
		this.siblingSeparation = siblingSeparation;
		this.subtreeSeparation = subtreeSeparation;
		this.levelSepartion = levelSepartion;
	}

	/**
	 * Initializes separation of siblings, subtrees and levels; 
	 * all others are set to default values. 
	 * @see trees.style.Style Style
	 * @param siblingSeparation Separation of adjacent siblings counted in pixels.
	 * @param subtreeSeparation Separation of adjacent subtrees counted in pixels.
	 * @param levelSepartion Separation of adjacent levels counted in pixels.
	 * @param size Size properties of all boxes.
	 */
	public Style(int siblingSeparation, int subtreeSeparation, int levelSepartion, Size size) {
		this();
		this.siblingSeparation = siblingSeparation;
		this.subtreeSeparation = subtreeSeparation;
		this.levelSepartion = levelSepartion;
		this.setSize(size);
	}

	private void notify(Action action){
		this.setChanged();
		this.notifyObservers(action);
		this.clearChanged();
	}
	
	//// Tree Geometry //////////////////////////////////
	
	/**
	 * Standard getter.
	 * @return Maximum drawing level depth.
	 */
	public int getMaxDepth() {
		return maxDepth;
	}

	/**
	 * Standard setter with notification of change.
	 * @param maxDepth Maximum drawing level depth.
	 */
	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
		this.notify(REPOSITION);
	}

	/**
	 * Standard getter.
	 * @return Separation of adjacent siblings counted in pixels.
	 */
	public int getSiblingSeparation() {
		return siblingSeparation;
	}

	/**
	 * Standard setter with notification of change.
	 * @param siblingSeparation Separation of adjacent siblings counted in pixels.
	 */
	public void setSiblingSeparation(int siblingSeparation) {
		this.siblingSeparation = siblingSeparation;
		this.notify(REPOSITION);
	}

	/**
	 * Standard getter.
	 * @return Separation of adjacent subtrees counted in pixels.
	 */
	public int getSubtreeSeparation() {
		return subtreeSeparation;
	}

	/**
	 * Standard setter with notification of change.
	 * @param subtreeSeparation Separation of adjacent subtrees counted in pixels.
	 */
	public void setSubtreeSeparation(int subtreeSeparation) {
		this.subtreeSeparation = subtreeSeparation;
		this.notify(REPOSITION);
	}

	/**
	 * Standard getter.
	 * @return Separation of adjacent levels counted in pixels.
	 */
	public int getLevelSepartion() {
		return levelSepartion;
	}

	/**
	 * Standard setter with notification of change.
	 * @param levelSepartion Separation of adjacent levels counted in pixels.
	 */
	public void setLevelSeparation(int levelSepartion) {
		this.levelSepartion = levelSepartion;
		this.notify(RECALCULATE);
	}
	
	/**
	 * Calculates the maximum dimension a node can take. This is restricted
	 * by the available space between the levels and an additional margin.
	 * @return The maximum dimension a node can take.
	 */
	public Dimension getMaxDimension(){
		return this.getMaxDimension(MAX_DIMENSION);
	}
	
	/**
	 * Calculates the maximum dimension a node can take. This is restricted
	 * by the available space between the levels and an additional margin and
	 * the dimension parameter.
	 * @param dimension The dimension to be compared with.
	 * @return The maximum dimension a node can take.
	 */
	public Dimension getMaxDimension(Dimension dimension){
		int maxSize = this.getLevelSepartion() - MIN_LEVEL_SEPARATION;
		if(orientation.isVertical())
			return new Dimension(dimension.width, (dimension.height < maxSize ? dimension.height : maxSize));
		else
			return new Dimension((dimension.width < maxSize ? dimension.width : maxSize), dimension.height);
	}

	//// Orientation & Alignment //////////////////////////////////
	
	/**
	 * Standard getter.
	 * @return Orientation of the overall tree.
	 */
	public Orientation getOrientation() {
		return orientation;
	}

	/**
	 * Setter with notification of change.
	 * @param orientation Orientation of the overall tree.
	 */
	public void setOrientation(Orientation orientation) {
		if(this.orientation == orientation)
			return;
		this.orientation = orientation;
		this.notify(REPOSITION);
	}

	
	/**
	 * Checks if the tree has a vertical orientation, i.e. top down or bottom up.
	 * @return true, if orientation is <code>NORTH</code> or <code>SOUTH</code>.
	 */
	public boolean hasVerticalOrientation(){
		return orientation == NORTH || orientation == SOUTH;
	}
	
	/**
	 * Checks if the tree has a horizontal orientation, i.e. left to right or right to left.
	 * @return true, if orientation is <code>EAST</code> or <code>WEST</code>.
	 */
	public boolean hasHorizontalOrientation(){
		return orientation == EAST || orientation == WEST;
	}

	/**
	 * Standard getter.
	 * @return Horizontal alignment of the tree, which may be 
	 * <code>LEFT</code>, <code>TREE_CENTERED</code>, <code>ROOT_CENTERED</code>, 
	 * or <code>RIGHT</code>.
	 */
	public Alignment getHorizontalAlignment() {
		return horizontalAlignment;
	}

	/**
	 * Setter with notification of change.
	 * @param horizontalAlignment Horizontal alignment of the tree, which may be 
	 * <code>LEFT</code>, <code>TREE_CENTERED</code>, <code>ROOT_CENTERED</code>, 
	 * or <code>RIGHT</code>.
	 */
	public void setHorizontalAlignment(Alignment horizontalAlignment) {
		if(this.horizontalAlignment == horizontalAlignment)
			return;
		this.horizontalAlignment = horizontalAlignment;
		this.notify(REALIGN);
	}

	/**
	 * Standard getter.
	 * @return Vertical alignment of the tree, which may be 
	 * <code>TOP</code>, <code>TREE_CENTERED</code>, <code>ROOT_CENTERED</code>, 
	 * or <code>BOTTOM</code>.
	 */
	public Alignment getVerticalAlignment() {
		return verticalAlignment;
	}

	/**
	 * Setter with notification of change.
	 * @param verticalAlignment Vertical alignment of the tree, which may be 
	 * <code>TOP</code>, <code>TREE_CENTERED</code>, <code>ROOT_CENTERED</code>, 
	 * or <code>BOTTOM</code>.
	 */
	public void setVerticalAlignment(Alignment verticalAlignment) {
		if(this.verticalAlignment == verticalAlignment)
			return;
		this.verticalAlignment = verticalAlignment;
		this.notify(REALIGN);
	}
	
	/**
	 * Sets the usage of place holders to all tree nodes. Place holders
	 * are calculated as nodes but not drawn. This is useful if you 
	 * want to have spread nodes.
	 * @param placeHolder true, when to use place holders.
	 */
	public void setPlaceHolder(boolean placeHolder){
		this.setPlaceHolder(null, placeHolder);
	}

	/**
	 * Sets the usage of place holders to all tree nodes of type <code>cls</code>. Place holders
	 * are calculated as nodes but not drawn. This is useful if you 
	 * want to have spread nodes.
	 * @param cls class for which the property to be set.
	 * @param placeHolder true, when to use place holders.
	 */
	public void setPlaceHolder(Class<?> cls, boolean placeHolder){
		if(cls == null)
			this.placeHolder.setValue(placeHolder);
		else
			this.placeHolder.setValue(cls, placeHolder);
		this.notify(REBUILD);
	}
	
	/**
	 * Returns the placeholder property of a given class.
	 * If the class is not registered with the property,
	 * the overall value is returned.
	 * @param cls Class for which the property to be returned.
	 * @return true, if place holders to be used.
	 */
	public boolean usesPlaceHolder(Class<?> cls){
		return this.placeHolder.getValue(cls);
	}

	///// Fonts & FontMetrics ////////////////////////////////////////////////////
	
	/**
	 * Returns the overall font to be used for drawing a node.
	 * @return The overall node font.
	 */
	public Font getFont(){
		return this.font.getValue();
	}
	
	/**
	 * Returns the font to be used for drawing a node of type <code>cls</code>.
	 * If no font is registered for the type, the default font is returned instead.
	 * @param cls class for which the property to be gotten.
	 * @return The  node font for <code>cls</code>.
	 */
	public Font getFont(Class<?> cls){
		return this.font.getValue(cls);
	}
	
	/**
	 * Sets the font for all nodes. Also sets the corresponding font metrics.
	 * @param font The font to be set.
	 */
	public void setFont(Font font){
		this.setFont(null, font);
	}
	
	/**
	 * Sets the font for a node of type <code>cls</code>.
	 * Also sets the font metrics for this type.
	 * @param cls class for which the property to be set.
	 * @param font The font to be set.
	 */
	public void setFont(Class<?> cls, Font font){
		if(this.font.getValue(cls) == font)
			return;
		this.font.setValue(cls, font);
		this.metrics.setValue(cls, panel.getFontMetrics(font));
		Size size = this.size.getValue(cls);
		if(size == null)
			return;
		if(size.isFixed())
			this.notify(RECALCULATE);
		else
			this.notify(REPOSITION);
	}
	
	/**
	 * Gets the overall font metrics.
	 * @return The overall font metrics.
	 */
	public FontMetrics getFontMetrics(){
		return this.metrics.getValue();
	}
	
	/**
	 * Gets the font metrics for a node of type <code>cls</code>.
	 * @param cls The node class for which the metrics are returned.
	 * @return The font metrics for <code>cls</code>.
	 */
	public FontMetrics getFontMetrics(Class<?> cls){
		return this.metrics.getValue(cls);
	}
	
	//// Size /////////////////////////////////////////////

	/**
	 * Gets the Size for a node of type <code>cls</code>.
	 * @param cls The node class for which the size is returned.
	 * @return The Size for <code>cls</code>.
	 */
	public Size getSize(Class<?> cls){
		return size.getValue(cls);
	}
	
	/**
	 * Sets the Size for all nodes.
	 * @param size The Size to be set.
	 */
	public void setSize(Size size) {
		this.setSize(null, size);
		this.notify(REPOSITION);
	}

	/**
	 * Sets the Size for a node of type <code>cls</code>.
	 * @param cls class for which the property to be set.
	 * @param size The Size to be set. <code>null</code> will unset the size.
	 */
	public void setSize(Class<?> cls, Size size) {
		this.size.setValue(cls, size);
		this.notify(REPOSITION);
	}

	///// Node-Layout ////////////////////////////////////
	
	/**
	 * Checks if the tree has a visible root pointer.
	 * @return true, if the tree has a visible root pointer.
	 */
	public boolean hasRootPointer() {
		return rootLabel != null;
	}

	/**
	 * Sets the root label to be displayed. 
	 * <code>null</code> will unset the root pointer.
	 * @param rootLabel the new root label.
	 */
	public void setRootPointer(String rootLabel) {
		this.rootLabel = rootLabel;
		this.notify(REALIGN);
	}
	
	/**
	 * Standard getter.
	 * @return The root label of the tree.
	 */
	public String getRootLabel(){
		return rootLabel;
	}
	
	/**
	 * Checks if all nodes have pointer boxes.
	 * @return true, if nodes should be displayed with pointer boxes.
	 */
	public boolean hasPointerBoxes() {
		return pointerBoxes.getValue();
	}

	/**
	 * Checks if nodes of type <code>cls</code> have pointer boxes.
	 * @param cls class for which the property to be checked.
	 * @return true, if nodes should be displayed with pointer boxes.
	 */
	public boolean hasPointerBoxes(Class<?> cls) {
		return pointerBoxes.getValue(cls);
	}

	/**
	 * Sets the pointer boxes for all nodes. If true, nodes will be drawn
	 * with a small box enclosing the edge origin.
	 * @param pointerBoxes The pointer box attribute to be set.
	 */
	public void setPointerBoxes(boolean pointerBoxes) {
		this.setPointerBoxes(null, pointerBoxes);
		this.notify(REPOSITION);
	}

	/**
	 * Sets the pointer boxes for of type <code>cls</code>. 
	 * If true, nodes will be drawn
	 * with a small box enclosing the edge origin.
	 * @param cls class for which the property to be set.
	 * @param pointerBoxes The pointer box attribute to be set.
	 */
	public void setPointerBoxes(Class<?> cls, boolean pointerBoxes) {
		this.pointerBoxes.setValue(cls, pointerBoxes);
		this.notify(REPOSITION);
	}

	/**
	 * Returns the drawing shape of all nodes.
	 * @return the drawing shape of all nodes.
	 */
	public Shape getShape() {
		return shape.getValue();
	}

	/**
	 * Returns the drawing shape of nodes of type <code>cls</code>.
	 * @param cls class for which the property to be returned.
	 * @return the node shape.
	 */
	public Shape getShape(Class<?> cls) {
		return shape.getValue(cls);
	}

	/**
	 * Sets the shape of all nodes.
	 * @param shape The shape to be set.
	 */
	public void setShape(Shape shape) {
		this.setShape(null, shape);
		this.notify(REPAINT);
	}
	
	/**
	 * Sets the shape of nodes of type <code>cls</code>.
	 * @param cls class for which the property to be set.
	 * @param shape The shape to be set.
	 */
	public void setShape(Class<?> cls, Shape shape) {
		this.shape.setValue(cls, shape);
		this.notify(REPAINT);
	}

}
