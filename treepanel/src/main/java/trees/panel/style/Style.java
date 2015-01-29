package trees.panel.style;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.Observable;

import javax.swing.JPanel;

import trees.layout.Label;
import trees.layout.Node;
import static trees.panel.style.Orientation.*;
import static trees.panel.style.Action.*;

public class Style extends Observable{
	
	private static final int DEFAULT_DEPTH = Integer.MAX_VALUE;
	private static final Orientation DEFAULT_ORIENTATION = Orientation.NORTH;	
	private static final Alignment DEFAULT_HORIZONTAL_ALIGNMENT = Alignment.LEFT;
	private static final Alignment DEFAULT_VERTICAL_ALIGNMENT = Alignment.TOP;	
	private static final Shape DEFAULT_SHAPE = Shape.RECTANGLE; 
	private static final Size DEFAULT_SIZE = Size.VARIABLE(); 

	
	public static final int TREE_MARGIN = 8;	

	public static String ROOT = "root";
	public static String NULL = "null";	
	public static int ROOT_ARROW_LENGTH = 20;
	public static int ROOT_ARROW_HEAD = 4;
	
	public static final int POINTER_BOX_HEIGHT = 8;
	public static final int ARC_SIZE = 10;
	
	public static final int LABEL_MARGIN = 4;	
	public static final Dimension MIN_DIMENSION = new Dimension(0, 0);
	public static final Dimension MAX_DIMENSION = new Dimension(1920, 1080);
	public static final String ETC = "...";

	private static final JPanel panel = new JPanel();
	
	// General settings
	private int maxDepth, siblingSeparation, subtreeSeparation, levelSepartion;
	private Orientation orientation;
	private Alignment horizontalAlignment, verticalAlignment;
	
	private boolean rootPointer;
	
	// Possible class specific settings
	private Value<Font> font = new Value<>();
	private Value<FontMetrics> metrics = new Value<>();
	private Value<Shape> shape = new Value<>();	
	private Value<Size> size = new Value<>();
	private Value<Boolean> pointerBoxes = new Value<>();
	private Value<Boolean> placeHolder = new Value<>();
	
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

	public Style(int siblingSeparation, int subtreeSeparation, int levelSepartion) {
		this();
		this.siblingSeparation = siblingSeparation;
		this.subtreeSeparation = subtreeSeparation;
		this.levelSepartion = levelSepartion;
	}

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
	
	public int getMaxDepth() {
		return maxDepth;
	}

	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
		this.notify(REBUILD);
	}

	public int getSiblingSeparation() {
		return siblingSeparation;
	}

	public void setSiblingSeparation(int siblingSeparation) {
		this.siblingSeparation = siblingSeparation;
		this.notify(REBUILD);
	}

	public int getSubtreeSeparation() {
		return subtreeSeparation;
	}

	public void setSubtreeSeparation(int subtreeSeparation) {
		this.subtreeSeparation = subtreeSeparation;
		this.notify(REBUILD);
	}

	public int getLevelSepartion() {
		return levelSepartion;
	}

	public void setLevelSepartion(int levelSepartion) {
		this.levelSepartion = levelSepartion;
		this.notify(RECALCULATE);
	}
	
	

	//// Orientation & Alignment //////////////////////////////////
	
	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		if(this.orientation == orientation)
			return;
		this.orientation = orientation;
		this.notify(RECALCULATE);
	}

	public boolean hasVerticalOrientation(){
		return orientation == NORTH || orientation == SOUTH;
	}
	
	public boolean hasHorizontalOrientation(){
		return orientation == EAST || orientation == WEST;
	}

	public Alignment getHorizontalAlignment() {
		return horizontalAlignment;
	}

	public void setHorizontalAlignment(Alignment horizontalAlignment) {
		if(this.horizontalAlignment == horizontalAlignment)
			return;
		this.horizontalAlignment = horizontalAlignment;
		this.notify(REALIGN);
	}

	public Alignment getVerticalAlignment() {
		return verticalAlignment;
	}

	public void setVerticalAlignment(Alignment verticalAlignment) {
		if(this.verticalAlignment == verticalAlignment)
			return;
		this.verticalAlignment = verticalAlignment;
		this.notify(REALIGN);
	}
	
	public void setPlaceHolder(boolean placeHolder){
		this.setUsesPlaceHolder(null, placeHolder);
	}

	public void setUsesPlaceHolder(Class<?> cls, boolean placeHolder){
		if(cls == null)
			this.placeHolder.setValue(placeHolder);
		else
			this.placeHolder.setValue(cls, placeHolder);
		this.notify(RESET);
	}
	
	public boolean usesPlaceHolder(Object obj){
		return this.placeHolder.getValue(obj.getClass());
	}

	public boolean usesPlaceHolder(Class<?> cls){
		return this.placeHolder.getValue(cls);
	}

	///// Fonts & FontMetrics ////////////////////////////////////////////////////
	
	public Font getFont(){
		return this.font.getValue();
	}
	
	public Font getFont(Object obj){
		return this.font.getValue(obj.getClass());
	}
	
	public void setFont(Font font){
		this.setFont(null, font);
	}
	
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
			this.notify(REBUILD);
	}
	
	public FontMetrics getFontMetrics(){
		return this.metrics.getValue();
	}
	
	public FontMetrics getFontMetrics(Object obj){
		return this.metrics.getValue(obj.getClass());
	}
	
	//// Size /////////////////////////////////////////////

	private Size getSize(Class<?> cls){
		return size.getValue(cls);
	}
	
	public Size getSize(Object obj){
		return size.getValue(obj.getClass());
	}
	
	public void setSize(Size size) {
		this.setSize(null, size);
	}

	public void setSize(Class<?> cls, Size size) {
		this.size.setValue(cls, size);
		this.notify(REBUILD);
	}

	public void unsetSize(Class<?> cls) {
		this.size.setValue(cls, null);
		this.notify(REBUILD);
	}
	
	public int getWidth(Node node){
		Size size = this.getSize(node.getNodeClass());
		Label label = node.getLabel(this);
		int width = size.getWidth(this.hasVerticalOrientation(), this.hasPointerBoxes(node), label.getDimension());
		return width;		
	}

	public int getHeight(Node node){
		Size size = this.getSize(node.getNodeClass());	
		Label label = node.getLabel(this);
		int height = size.getHeight(this.hasVerticalOrientation(), this.hasPointerBoxes(node), label.getDimension());
		return height;		
	}
	
	///// Node-Layout ////////////////////////////////////
	
	public boolean hasRootPointer() {
		return rootPointer;
	}

	public void setRootPointer(boolean rootPointer) {
		this.rootPointer = rootPointer;
		this.notify(REALIGN);
	}
	
	public boolean hasPointerBoxes() {
		return pointerBoxes.getValue();
	}

	public boolean hasPointerBoxes(Object obj) {
		return pointerBoxes.getValue(obj);
	}

	public void setPointerBoxes(boolean pointerBoxes) {
		this.setPointerBoxes(null, pointerBoxes);
	}

	public void setPointerBoxes(Class<?> cls, boolean pointerBoxes) {
		this.pointerBoxes.setValue(cls, pointerBoxes);
		this.notify(REPAINT);
	}

	public Shape getShape() {
		return shape.getValue();
	}

	public Shape getShape(Object obj) {
		return shape.getValue(obj);
	}

	public void setShape(Shape shape) {
		this.setShape(null, shape);
	}
	
	public void setShape(Class<?> cls, Shape shape) {
		this.shape.setValue(cls, shape);
		this.notify(REPAINT);
	}
	
}
