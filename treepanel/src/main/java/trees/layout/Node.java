package trees.layout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import trees.acessing.NodeWrapper;
import trees.panel.style.Style;

public abstract class Node implements Iterable<Node> {
	
//	public static final int NODE_WIDTH = 60, NODE_HEIGHT = 40;
	
	private NodeWrapper data;
	private List<Node> children = new ArrayList<>();
	private boolean[] childrenState = new boolean[0];

	protected Node parent, leftNeighbor;	
	protected int xCoordinate, yCoordinate, prelim, modifier;

	public Node(NodeWrapper data) {
		super();
		this.data = data;
	}

	public int getX() {
		return xCoordinate;
	}

	public int getY() {
		return yCoordinate;
	}

	public int getPrelim() {
		return prelim;
	}

	public int getModifier() {
		return modifier;
	}
	
	public Object getData(){
		return data.getNode();
	}
	
	public void add(Node ... children){
		int i = childrenState.length;
		childrenState = Arrays.copyOf(childrenState, childrenState.length + children.length);
		for(Node child : children){
			this.children.add(child);
			if(child != null)
				child.parent = this;
			childrenState[i++] = (child != null && !child.isPlaceHolder());
		}
		
	}

	public String getLabel() {
		if(data == null)
			return "";
		else
			return data.getLabel();
	}

	public String toString() {
		return "Node[" + getLabel() + ", " + prelim + " + " + modifier + " ->\t" + xCoordinate + "|" + yCoordinate + "]";
	}
	
	@Override
	public Iterator<Node> iterator() {
		return children.iterator();
	}
	
	protected void initialize(){
		leftNeighbor = null;
		prelim = 0;
		modifier = 0;
		xCoordinate = 0;
		yCoordinate = 0;
		
		for(Node child : children)
			if(child != null)
				child.initialize();
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
		return !hasChild();
	}

	public boolean hasChild() {
		for(Node child : children)
			if(child != null)
				return true;
		return false;
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
		return style.getWidth(this);
	}

	public int getWidth(Style style) {
		return style.getHeight(this);
	}
	
	protected int getDrawingWidth(Style style){
		int width = this.getX() + this.getWidth(style);
		for(Node child : children)
			if(child != null){
				int childWidth = child.getDrawingWidth(style);
				if(childWidth > width)
					width = childWidth;
			}
		return width;
	}

	protected int getDrawingHeight(Style style){
		int height = this.getY() + this.getHeight(style);
		for(Node child : children)
			if(child != null){
				int childHeight = child.getDrawingHeight(style);
				if(childHeight > height)
					height = childHeight;
			}
		return height;
	}

	public boolean isPlaceHolder(){
		return false;
	}

	public boolean[] getChildrenState() {
		return childrenState;
	}
}
