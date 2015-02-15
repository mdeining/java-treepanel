package trees.layout;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import trees.style.Orientation;
import trees.style.Size;
import trees.style.Style;
import static trees.layout.Action.*;

/**
 * Helper class for wrapping a tree into an internal structure which 
 * contains all the displaying information.
 * 
 * @author Marcus Deininger
 *
 */
public class Node implements Iterable<Node> {
	
	private ModelData model;
	private List<Node> children = new ArrayList<>();
	private boolean[] hasChild = new boolean[0];

	protected Node parent, leftNeighbor;	
	protected int xCoordinate, yCoordinate, prelim, modifier;
	private int width, height;
	
	private Orientation orientation;
	private boolean pointerBoxes;
	
	/**
	 * Constructor for creating a node wrapper.
	 * 
	 * @param model - The object to be wrapped.
	 * @param style - The displaying style to be used.
	 */
	public Node(ModelData model, Style style) {
		this.model = model;
		this.resize(style);
	}
	
	/**
	 * Adds some nodes to the current node.
	 * 
	 * @param children - The nodes to be added.
	 */
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

	/* (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Node> iterator() {
		return children.iterator();
	}
	
	// Printing //////////////////////////////////////////////////////
	
	/**
	 * Prints the tree in post-order sequence.
	 */
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
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Node[" + model.getModelLabel().split("\\n")[0] + ", " + prelim + " + " + modifier + " ->\t" + xCoordinate + "|" + yCoordinate + "]";
	}
	
	// Model methods //////////////////////////////////////////////////////
	
	/**
	 * Returns the class of the wrapped model object.
	 * @return The class of the wrapped model object.
	 */
	public Class<?> getModelClass() {
		return model.getModelClass();
	}
	
	/**
	 * Returns the wrapped model object.
	 * @return The wrapped model object.
	 */
	public Object getModelObject(){
		return model.getModelObject();
	}

	// Display methods //////////////////////////////////////////////////////
	
	/**
	 * Checks if the wrapped node is a duplicate - 
	 * i.e. already contained in the tree. Actually this means,
	 * the structure is not really a tree.
	 * @return true, if the node is already displayed.
	 */
	public boolean isDuplicate() {
		return model.isDuplicate();
	}

	/**
	 * Checks if the node is a placeholder for generating space.
	 * @return true, if the node is a placeholder object.
	 */
	public boolean isPlaceHolder(){
		return model.isPlaceholder();
	}

	/**
	 * Returns the x-coordinate of the node which starts top left.
	 * @return - The x-coordinate of the node
	 */
	public int getX() {
		return xCoordinate;
	}

	/**
	 * Returns the y-coordinate of the node which starts top left.
	 * @return - The y-coordinate of the node
	 */
	public int getY() {
		return yCoordinate;
	}

	/**
	 * Returns the x-coordinate with an added offset.
	 * @return - The x-coordinate of the node with offset.
	 */
	public int getX(int offset) {
		return xCoordinate + offset;
	}

	/**
	 * Returns the y-coordinate with an added offset.
	 * @return - The y-coordinate of the node with offset.
	 */
	public int getY(int offset) {
		return yCoordinate + offset;
	}

	/**
	 * Returns the preliminary coordinate.
	 * @return - The preliminary coordinate of the node.
	 */
	public int getPrelim() {
		return prelim;
	}

	/**
	 * Returns the modifier, which gives an additional placement.
	 * @return - The placement modifier.
	 */
	public int getModifier() {
		return modifier;
	}
	
	/**
	 * Returns the size of the contained label.
	 * @return - The size of the contained label.
	 */
	public Dimension getLabelSize(){
		return model.getLabelSize();
	}
	
	/**
	 * Returns the label split into lines.
	 * @return - The lines of the label.
	 */
	public String[]	getLabelLines(){
		return model.getLines();
	}
	
	/**
	 * Calculates the rectangle which is taken by the tree
	 * with respect to the style. By default this rectangle
	 * starts top left at (0|0). An offset is added later.
	 * @param style - The style for this tree.
	 * @return - The rectangle covered by this tree.
	 */
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
	
	/**
	 * Returns the rectangle coordinates for this node.
	 * @return - The rectangle coordinates for this node.
	 */
	public Rectangle getNodeArea(){
		return new Rectangle(xCoordinate, yCoordinate, width, height);
	}

	/**
	 * Returns the rectangle coordinates for this node with an added offset.
	 * @return - The rectangle coordinates for this node with an added offset.
	 */
	public Rectangle getNodeArea(Dimension offset){
		return new Rectangle(xCoordinate + offset.width, yCoordinate + offset.height, width, height);
	}

	/**
	 * Returns the rectangle coordinates for this nodes' label with an added offset.
	 * @return - The rectangle coordinates for this label with an added offset.
	 */
	public Rectangle getLabelArea(Dimension offset){
		Rectangle area = this.getLabelArea();
		return new Rectangle(area.x + offset.width, area.y + offset.height, area.width, area.height);
	}

	/**
	 * Returns the rectangle coordinates for this nodes' label.
	 * @return - The rectangle coordinates for this label.
	 */
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
	
	/**
	 * Initializes the tree recursively.
	 * @param style - The style to be used.
	 * @param action - The initalization level.
	 */
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

	/**
	 * Checks if the node is a leaf.
	 * @return true, if the node is a leaf.
	 */
	public boolean isLeaf() {
		return !hasChildren();
	}

	/**
	 * Checks if the node has at least one child
	 * which is not null.
	 * @return true, if the has non-null children.
	 */
	public boolean hasChildren() {
		for(Node child : children)
			if(child != null)
				return true;
		return false;
	}

	/**
	 * Checks if the node has a child at index
	 * which is not null.
	 * @param index - Index of the child to be checked.
	 * @return true, if the has a non-null child at index.
	 */
	public boolean hasChild(int index) {
		return hasChild[index];
	}
	
	/**
	 * Returns the number of children (null and non-null).
	 * @return The number of children.
	 */
	public int getChildrenSlots(){
		return hasChild.length;
	}

	/**
	 * The height of the node.
	 * @return The height of the node.
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * The width of the node.
	 * @return The width of the node.
	 */
	public int getWidth() {
		return this.width;
	}
	
	/**
	 * The size of the node. This is the height when the style is
	 * horizontal, else the width.
	 * @return The size of the node.
	 */
	protected int getSize(){
		if(orientation.isHorizontal())
			return this.height;
		else
			return this.width;
	}
	
}
