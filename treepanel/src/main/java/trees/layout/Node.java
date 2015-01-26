package trees.layout;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import trees.acessing.AbstractWrappedNode;
import trees.panel.style.Style;

public abstract class Node implements Iterable<Node> {
	
	private AbstractWrappedNode wrappedNode;
	private List<Node> children = new ArrayList<>();
	private boolean[] hasChild = new boolean[0];

	protected Node parent, leftNeighbor;	
	protected int xCoordinate, yCoordinate, prelim, modifier;
	
	private Label label;

	public Node(AbstractWrappedNode wrappedNode) {
		super();
		this.wrappedNode = wrappedNode;
		this.label = null;
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
	
	public Class<?> getNodeClass(){
		return wrappedNode.getNodeClass();
	}
	
	public Object getNode(){
		return wrappedNode.getNode();
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

	public Label getLabel(Style style) {
		if(label == null)
			label = new Label(this, style);
		return label;
	}

	protected String getSourceLabel() {
		if(wrappedNode == null)
			return "";
		else
			return wrappedNode.getLabel();
	}

	public String toString() {
		return "Node[" + getSourceLabel() + ", " + prelim + " + " + modifier + " ->\t" + xCoordinate + "|" + yCoordinate + "]";
	}
	
	@Override
	public Iterator<Node> iterator() {
		return children.iterator();
	}
	
	protected void initialize(boolean complete){
		label = null;
		xCoordinate = 0;
		yCoordinate = 0;

		if(complete){
			leftNeighbor = null;
			prelim = 0;
			modifier = 0;
		}
		
		for(Node child : children)
			if(child != null)
				child.initialize(complete);
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
		return style.getWidth(this) / 2;
	}

	// Size of the left half of the node
	protected int getLeftSize(Style style) {
		return style.getWidth(this) / 2;
	}
	
	protected int getTopSize(Style style) {
		return style.getHeight(this) / 2;
	}
	
	protected int getBottomSize(Style style) {
		return style.getHeight(this) / 2;
	}
	
	public int getHeight(Style style) {
		return style.getHeight(this);
	}

	public int getWidth(Style style) {
		return style.getWidth(this);
	}
	
	public Rectangle getTreeArea(Style style){
		// By definition, the coordinates are without offset
		Point p = this.getRightmostPoint(style);
		Rectangle area = new Rectangle(0, 0, p.x, p.y);
		return area;		
	}
	
	private Point getRightmostPoint(Style style) {
		int x = this.getX() + this.getWidth(style);
		int y = this.getY() + this.getHeight(style);
		
		for(Node child : children)
			if(child != null){				
				Point p = child.getRightmostPoint(style);				
				if(p.x > x) x = p.x;
				if(p.y > y) y = p.y;			
			}
		Point p = new Point(x, y);
		return p;
	}
	
	public Rectangle getNodeArea(Style style){
		return new Rectangle(xCoordinate, yCoordinate, style.getWidth(this), style.getHeight(this));
	}

	public Rectangle getNodeArea(Style style, Dimension offset){
		return new Rectangle(xCoordinate + offset.width, yCoordinate + offset.height, style.getWidth(this), style.getHeight(this));
	}

	public Rectangle getLabelArea(Style style, Dimension offset){
		Rectangle area = this.getLabelArea(style);
		return new Rectangle(area.x + offset.width, area.y + offset.height, area.width, area.height);
	}

	public Rectangle getLabelArea(Style style){
		int xOff = Style.LABEL_MARGIN;
		int yOff = Style.LABEL_MARGIN;
		int width = style.getWidth(this) - 2 * Style.LABEL_MARGIN;
		int height = style.getHeight(this) - 2 * Style.LABEL_MARGIN;
		if(style.hasPointerBoxes(this))
			switch(style.getOrientation()){
				case NORTH:	height = height - Style.POINTER_BOX_HEIGHT; break;
				case SOUTH:	height = height - Style.POINTER_BOX_HEIGHT; 
							yOff = yOff + Style.POINTER_BOX_HEIGHT; break;
				case EAST:	width = width - Style.POINTER_BOX_HEIGHT; break;
				case WEST:	width = width - Style.POINTER_BOX_HEIGHT; 
							xOff = xOff + Style.POINTER_BOX_HEIGHT; break;
			}
		return new Rectangle(xCoordinate + xOff, yCoordinate + yOff, width, height);
	}

	public boolean isPlaceHolder(){
		return false;
	}
	
	public boolean isDuplicate(){
		return wrappedNode.isDuplicate();
	}

}
