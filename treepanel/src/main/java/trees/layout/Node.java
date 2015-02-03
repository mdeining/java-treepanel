package trees.layout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import trees.panel.style.Orientation;
import trees.panel.style.Size;
import trees.panel.style.Style;
import static trees.layout.Action.*;

public class Node implements Iterable<Node> {
	
	private ModelData model;
	private List<Node> children = new ArrayList<>();
	private boolean[] hasChild = new boolean[0];

	protected Node parent, leftNeighbor;	
	protected int xCoordinate, yCoordinate, prelim, modifier;
	private int width, height;
	
	private Orientation orientation;
	private boolean pointerBoxes;
	
	public Node(ModelData model, Style style) {
		this.model = model;
		this.resize(style);
	}
	
	public void add(Node ... children){
		int i = hasChild.length;
		hasChild = Arrays.copyOf(hasChild, hasChild.length + children.length);
		for(Node child : children){
			this.children.add(child);
			if(child != null)
				child.parent = this;
			hasChild[i++] = (child != null && !child.isPlaceHolder());
		}		
	}

	@Override
	public Iterator<Node> iterator() {
		return children.iterator();
	}
	
	// Printing //////////////////////////////////////////////////////
	
	public void printPostOrder(){
		this.printPostOrder(this);
	}

	private void printPostOrder(Node node){
		if(node == null)
			return;
		for(Node child : node)
			printPostOrder(child);
		System.out.println(node.toString());
	}
	

	public String toString() {
		return "Node[" + model.getModelLabel().split("\\n")[0] + ", " + prelim + " + " + modifier + " ->\t" + xCoordinate + "|" + yCoordinate + "]";
	}
	
	// Model methods //////////////////////////////////////////////////////
	
	public Class<?> getModelClass() {
		return model.getModelClass();
	}	

	// Display methods //////////////////////////////////////////////////////
	
	public Color selectColor(Map<Object, Color> colorMap) {
		return colorMap.get(model.getModelObject());
	}

	public boolean isDuplicate() {
		return model.isDuplicate();
	}

	public boolean isPlaceHolder(){
		return model.isPlaceholder();
	}

	public int getX() {
		return xCoordinate;
	}

	public int getY() {
		return yCoordinate;
	}

	public int getX(int offset) {
		return xCoordinate + offset;
	}

	public int getY(int offset) {
		return yCoordinate + offset;
	}

	public int getPrelim() {
		return prelim;
	}

	public int getModifier() {
		return modifier;
	}
	
	public Dimension getLabelSize(){
		return model.getLabelSize();
	}
	
	public String[]	getLabelLines(){
		return model.getLines();
	}
	
	public Rectangle getTreeArea(Style style){
		// By definition, the coordinates are without offset
		Point p = this.getRightmostPoint(style);
		Rectangle area = new Rectangle(0, 0, p.x, p.y);
		return area;		
	}
	
	private Point getRightmostPoint(Style style) {
		int x = this.getX() + this.getWidth();
		int y = this.getY() + this.getHeight();
		
		for(Node child : children)
			if(child != null){				
				Point p = child.getRightmostPoint(style);				
				if(p.x > x) x = p.x;
				if(p.y > y) y = p.y;			
			}
		Point p = new Point(x, y);
		return p;
	}
	
	public Rectangle getNodeArea(){
		return new Rectangle(xCoordinate, yCoordinate, width, height);
	}

	public Rectangle getNodeArea(Dimension offset){
		return new Rectangle(xCoordinate + offset.width, yCoordinate + offset.height, width, height);
	}

	public Rectangle getLabelArea(Dimension offset){
		Rectangle area = this.getLabelArea();
		return new Rectangle(area.x + offset.width, area.y + offset.height, area.width, area.height);
	}

	public Rectangle getLabelArea(){
		int xOff = Style.LABEL_MARGIN;
		int yOff = Style.LABEL_MARGIN;
		int width = this.width - 2 * Style.LABEL_MARGIN;
		int height = this.height - 2 * Style.LABEL_MARGIN;
		if(this.pointerBoxes)
			switch(this.orientation){
				case NORTH:	height = height - Style.POINTER_BOX_HEIGHT; break;
				case SOUTH:	height = height - Style.POINTER_BOX_HEIGHT; 
							yOff = yOff + Style.POINTER_BOX_HEIGHT; break;
				case EAST:	width = width - Style.POINTER_BOX_HEIGHT; break;
				case WEST:	width = width - Style.POINTER_BOX_HEIGHT; 
							xOff = xOff + Style.POINTER_BOX_HEIGHT; break;
			}
		return new Rectangle(xCoordinate + xOff, yCoordinate + yOff, width, height);
	}

	// Layout methods //////////////////////////////////////////////////////
	
	protected void init(Style style, Action action){
		model.align(style);
		this.resize(style);
		xCoordinate = 0;
		yCoordinate = 0;

		if(action.atLeast(REPOSITION)){
			leftNeighbor = null;
			prelim = 0;
			modifier = 0;
		}
		
		for(Node child : children)
			if(child != null)
				child.init(style, action);
	}
	
	// calculates the nodes width and height with respect to the current style
	private void resize(Style style) {
		this.pointerBoxes = style.hasPointerBoxes(model.getModelClass());
		this.orientation = style.getOrientation();
		
		Size size = style.getSize(model.getModelClass());
		if(size.isFixed()){
			Dimension dimension = size.getMaximum();
			this.width = dimension.width;
			this.height = dimension.height;
			return;
		}
		
		Dimension labelDimension = model.getLabelSize();
		this.width = labelDimension.width + 2 * Style.LABEL_MARGIN;
		this.height = labelDimension.height + 2 * Style.LABEL_MARGIN;
		if(this.pointerBoxes)
			if(this.orientation.isHorizontal())
				this.width = this.width + Style.POINTER_BOX_HEIGHT;
			else
				this.height = this.height + Style.POINTER_BOX_HEIGHT;

		if(size.hasMaximum()){
			Dimension max = size.getMaximum();
			if(max.width < this.width)
				this.width = max.width;
			if(max.height < this.height)
				this.height = max.height;
		}

		if(size.hasMinimum()){
			Dimension min = size.getMinimum();
			if(min.width > this.width)
				this.width = min.width;
			if(min.height > this.height)
				this.height = min.height;
		}
	}

	// 	The current node's leftmost offspring
	protected Node getFirstChild(){
		for(Node child : children)
			if(child != null)
				return child;
			return null;
	}
	
	// The current node's closest sibling node on the left	
	protected Node getLeftSibling(){
		if(parent == null) // root
			return null;
		
		int thisPos = -1;	
		for(int i = 0; i < parent.children.size(); i++)
			if(parent.children.get(i) == this)
				thisPos = i;

		for(int i = thisPos - 1; i >= 0; i--)
			if(parent.children.get(i) != null)
				return parent.children.get(i);
		
		return null;		
	}
	
	// 	The current node's closest sibling node on the right
	protected Node getRightSibling(){
		if(parent == null)
			return null;

		int thisPos = -1;	
		for(int i = 0; i < parent.children.size(); i++)
			if(parent.children.get(i) == this)
				thisPos = i;

		for(int i = thisPos + 1; i < parent.children.size(); i++)
			if(parent.children.get(i) != null)
				return parent.children.get(i);
		
		return null;		
	}

	protected boolean hasRightSibling() {
		return this.getRightSibling() != null;
	}

	protected boolean hasLeftSibling() {
		return this.getLeftSibling() != null;
	}

	public boolean isLeaf() {
		return !hasChildren();
	}

	public boolean hasChildren() {
		for(Node child : children)
			if(child != null)
				return true;
		return false;
	}

	public boolean hasChild(int index) {
		return hasChild[index];
	}
	
	public int getChildrenSlots(){
		return hasChild.length;
	}

	// Size of the right half of the node
	protected int getRightSize(Style style) {
		return this.width / 2;
	}

	// Size of the left half of the node
	protected int getLeftSize() {
		return this.width / 2;
	}
	
	protected int getTopSize() {
		return this.height / 2;
	}
	
	protected int getBottomSize() {
		return this.height / 2;
	}
	
	public int getHeight() {
		return this.height;
	}

	public int getWidth() {
		return this.width;
	}
	
}
