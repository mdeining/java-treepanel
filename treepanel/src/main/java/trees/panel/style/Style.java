package trees.panel.style;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Observable;

import javax.swing.JPanel;

import trees.layout.Label;
import trees.layout.Node;
import static trees.panel.style.Orientation.*;
import static trees.panel.style.Action.*;

public class Style extends Observable{
	
	public static int MARGIN = 4;
	
	public static int POINTER_BOX_HEIGHT = 8;
	public static int ARC_SIZE = 10;
	
	private JPanel panel;
	private FontMetrics defaultMetrics;
	
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
		this.setDefaultMetrics();
	}

	protected Style(JPanel panel){
		this();
		this.panel = panel;
	}

	private void setDefaultMetrics() {
		JPanel panel = new JPanel();
		Font font = panel.getFont();
		defaultMetrics = panel.getFontMetrics(font);
	}
	
	private void notify(Action action){
		this.setChanged();
		this.notifyObservers(action);
		this.clearChanged();
	}
	
	public void setPanel(JPanel panel){
		this.panel = panel;
	}
	
	public FontMetrics getFontMetrics() {
		if(panel == null)
			return defaultMetrics;
		
		Font font = panel.getFont();
		FontMetrics metrics = panel.getFontMetrics(font);
		return metrics;
	}
	
	public Font getFont(){
		return this.getFontMetrics().getFont();
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
		if(this.orientation == orientation)
			return;
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

	public boolean hasRootPointer() {
		return rootPointer;
	}

	public void setRootPointer(boolean rootPointer) {
		this.rootPointer = rootPointer;
		this.notify(REALIGN);
	}
	
	/////////////////////////////////////////////////////////
	
	private Size getSize(Class<?> cls){
		return size.getValue(cls);
	}
	
	public Size getSize(Object obj){
		return size.getValue(obj.getClass());
	}
	
	public void setSize(Size size) {
		size.setStyle(this);
		this.size.setValue(size);
		this.notify(REBUILD);
	}

	public void setSize(Class<?> cls, Size size) {
		size.setStyle(this);
		this.size.setValue(cls, size);
		this.notify(REBUILD);
	}

	public void unsetSize(Class<?> cls) {
		this.size.setValue(cls, null);
		this.notify(REBUILD);
	}
	
	public int getWidth(Node node){
		Size size = this.getSize(node.getNodeClass());
		Label label = node.getAdjustedLabel(this);
		int width = size.getWidth(this.hasVerticalOrientation(), this.hasPointerBoxes(node), label.getDimension());
		return width;		
	}

	public int getHeight(Node node){
		Size size = this.getSize(node.getNodeClass());	
		Label label = node.getAdjustedLabel(this);
		int height = size.getHeight(this.hasVerticalOrientation(), this.hasPointerBoxes(node), label.getDimension());
		return height;		
	}
	
//	public String[] getLabel(Graphics g, Node node){
//		Size size = this.getSize(node.getNodeClass());
//		String[] label = size.getLabel(g, node.getLabelArea(this, offset), node.getLabel());
//		return label;		
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
