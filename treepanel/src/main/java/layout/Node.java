package layout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Node implements Iterable<Node> {
	
	public static final int NODE_WIDTH = 20, NODE_HEIGHT = 20;
	
	private String data;
	private List<Node> children = new ArrayList<>();

	protected Node parent, leftNeighbor;	
	protected int xCoordinate, yCoordinate, prelim, modifier;

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
	
	public String getData(){
		return data;
	}
	
	public Node(String data) {
		super();
		this.data = data;
	}
	
	public void add(Node ... children){
		for(Node child : children){
			this.children.add(child);
			child.parent = this;
		}
	}

	public String toString() {
		return "Node[" + data + ", " + prelim + " + " + modifier + " ->\t" + xCoordinate + "|" + yCoordinate + "]";
	}
	
	public void printPostOrder(){
		this.printPostOrder(this);
	}

	@Override
	public Iterator<Node> iterator() {
		return children.iterator();
	}

	private void printPostOrder(Node node){
		if(node == null)
			return;
		for(Node child : node.children)
			printPostOrder(child);
		System.out.println(node.toString());
	}

	// 	The current node's leftmost offspring
	protected Node getFirstChild(){
		if(children.isEmpty())
			return null;
		else
			return children.get(0);
	}
	
	// The current node's closest sibling node on the left	
	protected Node getLeftSibling(){
		if(parent == null) // apex
			return null;
		for(int i = 1; i < parent.children.size(); i++)
			if(parent.children.get(i) == this)
				return parent.children.get(i - 1);		
		return null;		
	}
	
	// 	The current node's closest sibling node on the right
	protected Node getRightSibling(){
		if(parent == null)
			return null;
		for(int i = 0; i < parent.children.size() - 1; i++)
			if(parent.children.get(i) == this)
				return parent.children.get(i + 1);		
		return null;		
	}

	protected boolean hasRightSibling() {
		return this.getRightSibling() != null;
	}

	protected boolean hasLeftSibling() {
		return this.getLeftSibling() != null;
	}

	public boolean isLeaf() {
		return children.isEmpty();
	}

	public boolean hasChild() {
		return !this.isLeaf();
	}

	// Size of the right half of the node
	protected int getRightSize() {
		return NODE_WIDTH / 2;
	}

	// Size of the left half of the node
	protected int getLeftSize() {
		return NODE_WIDTH / 2;
	}
	
	protected int getTopSize() {
		return NODE_HEIGHT / 2;
	}
	
	protected int getBottomSize() {
		return NODE_HEIGHT / 2;
	}
	
	public int getHeight() {
		return NODE_HEIGHT;
	}

	public int getWidth() {
		return NODE_WIDTH;
	}
}
