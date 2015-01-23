package trees.panel.style;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Observable;

import trees.layout.Node;
import static trees.panel.style.Orientation.*;
import static trees.panel.style.Action.*;

public class Style extends Observable{
	
	public static int MARGIN = 0;
	
	public static int POINTER_BOX_HEIGHT = 8;
	public static int ARC_SIZE = 10, ARC_OFFSET = 2;
	
	// General settings
	private int maxDepth, siblingSeparation, 
			subtreeSeparation, levelSepartion;
	private Orientation orientation;
	private Alignment horizontalAlignment, verticalAlignment;
	
	private boolean rootPointer;
	
	// Possible class specific settings
	private Value<Boolean> pointerBoxes = new Value<>();
	private Value<Shape> shape = new Value<>();	
	private Value<Size> size = new Value<>();
	
	protected Style(){
		super();
	}
	
	private void notify(Action action){
		this.setChanged();
		this.notifyObservers(action);
		this.clearChanged();
	}
	
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

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
		this.notify(RECALCULATE);
	}

	public boolean hasVerticalOrientation(){
		return orientation == Orientation.NORTH || orientation == Orientation.SOUTH;
	}
	
	public boolean hasHorizontalOrientation(){
		return orientation == Orientation.EAST || orientation == Orientation.WEST;
	}

	public Alignment getHorizontalAlignment() {
		return horizontalAlignment;
	}

	public void setHorizontalAlignment(Alignment horizontalAlignment) {
		this.horizontalAlignment = horizontalAlignment;
		this.notify(REPAINT);
	}

	public Alignment getVerticalAlignment() {
		return verticalAlignment;
	}

	public void setVerticalAlignment(Alignment verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
		this.notify(REPAINT);
	}

	public boolean hasRootPointer() {
		return rootPointer;
	}

	public void setRootPointer(boolean rootPointer) {
		this.rootPointer = rootPointer;
		this.notify(REPAINT);
	}
	
	/////////////////////////////////////////////////////////
	
	
	private Size getSize(Object obj) {
		return size.getValue(obj);
	}

	public void setSize(Size size) {
		this.size.setValue(size);
		this.notify(REBUILD);
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
		Size size = this.getSize(node);	
		int width = size.getWidth(node.getLabel());
		return width;		
	}

	public int getHeight(Node node){
		Size size = this.getSize(node.getData());	
		int height = size.getHeight(node.getLabel());
		return height;		
	}
	
	public String[] getLabel(Graphics g, Node node){
		Object wrappedNode = node.getData();
		Size size = this.getSize(wrappedNode);
		String[] label = size.getLabel(g, this.getDrawingArea(node), node.getLabel());
		return label;		
	}

	public Rectangle getDrawingArea(Node node) {
		int x = node.getX();
		x = x + MARGIN;
		if(this.hasPointerBoxes(node.getData()) && orientation == WEST)
			x = x + POINTER_BOX_HEIGHT;

		int y = node.getY();
		y = y + MARGIN;
		if(this.hasPointerBoxes(node.getData()) && orientation == SOUTH)
			y = y + POINTER_BOX_HEIGHT;

		int w = this.getWidth(node);
		w = w - 2 * MARGIN;
		if(this.hasPointerBoxes(node.getData()) && this.hasHorizontalOrientation())
			w = w - POINTER_BOX_HEIGHT;

		int h = this.getHeight(node);
		h = h - 2 * MARGIN;
		if(this.hasPointerBoxes(node.getData()) && this.hasVerticalOrientation())
			h = h - POINTER_BOX_HEIGHT;

		return new Rectangle(x, y, w, h);
	}

//	public int getDrawingX(Node node) {
//		int x = node.getX();
//		x = x + MARGIN;
//		if(this.hasPointerBoxes(node.getData()) && orientation == WEST)
//			x = x + POINTER_BOX_HEIGHT;
//		return x;
//	}
//
//	public int getDrawingY(Node node) {
//		int y = node.getY();
//		y = y + MARGIN;
//		if(this.hasPointerBoxes(node.getData()) && orientation == SOUTH)
//			y = y + POINTER_BOX_HEIGHT;
//		return y;
//	}
//
//	public int getDrawingWidth(Node node) {
//		int width = this.getWidth(node);
//		width = width - 2 * MARGIN;
//		if(this.hasPointerBoxes(node.getData()) && this.hasHorizontalOrientation())
//			width = width - POINTER_BOX_HEIGHT;
//		return width;
//	}
//
//	public int getDrawingHeight(Node node) {
//		int height = this.getHeight(node);
//		height = height - 2 * MARGIN;
//		if(this.hasPointerBoxes(node.getData()) && this.hasVerticalOrientation())
//			height = height - POINTER_BOX_HEIGHT;
//		return height;
//	}

	/////////////////////////////////////////
	
	public boolean hasPointerBoxes() {
		return pointerBoxes.getValue();
	}

	public boolean hasPointerBoxes(Object obj) {
		return pointerBoxes.getValue(obj);
	}

	public void setPointerBoxes(boolean pointerBoxes) {
		this.pointerBoxes.setValue(pointerBoxes);
		this.notify(REPAINT);
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
		this.shape.setValue(shape);
		this.notify(REPAINT);
	}
	
	public void setShape(Class<?> cls, Shape shape) {
		this.shape.setValue(cls, shape);
		this.notify(REPAINT);
	}
	
}
