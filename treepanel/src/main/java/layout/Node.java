package layout;

import java.util.ArrayList;
import java.util.List;

public class Node {
	
	public static final int NODE_WIDTH = 20, NODE_HEIGHT = 20;
	
	public List<Node> children = new ArrayList<>();
	public String data;

	protected Node parent, leftNeighbor;	
	public double xCoordinate, yCoordinate, prelim, modifier;
	
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

	// For each node, the algorithm uses nine different functions. 
	// These might be stored in the memory allocated for each node, 
	// or they might be calculated for each node, depending on the internal 
	// structure of your application.
	
	// Upon entry to POSITIONTREE, the first four functions
	// -the hierarchical relationships-are required for each node. 
	// Also, XCOORD and YCOORD of the apex node are required. 
	// Upon its successful completion, the algorithm sets the XCOORD and YCOORD 
	// values for each node in the tree.
	
	
//	// The current node's hierarchical parent
//	public NodeClass PARENT(){
//		return PARENT;
//	}
	
	@Override
	public String toString() {
		return "Node[" + data + ", " + prelim + " + " + modifier + " ->\t" + xCoordinate + "|" + yCoordinate + "]";
	}
	
	public void postOrderOut(){
		this.postOrderOut(this);
	}

	private void postOrderOut(Node node){
		if(node == null)
			return;
		for(Node child : node.children)
			postOrderOut(child);
		System.out.println(node.toString());
	}

	// 	The current node's leftmost offspring
	public Node getFirstChild(){
		if(children.isEmpty())
			return null;
		else
			return children.get(0);
	}
	
	// The current node's closest sibling node on the left	
	public Node getLeftSibling(){
		if(parent == null) // apex
			return null;
		for(int i = 1; i < parent.children.size(); i++)
			if(parent.children.get(i) == this)
				return parent.children.get(i - 1);		
		return null;		
	}
	
	// 	The current node's closest sibling node on the right
	public Node getRightSibling(){
		if(parent == null)
			return null;
		for(int i = 0; i < parent.children.size() - 1; i++)
			if(parent.children.get(i) == this)
				return parent.children.get(i + 1);		
		return null;		
	}

	public boolean hasRightSibling() {
		return this.getRightSibling() != null;
	}

	public boolean hasLeftSibling() {
		return this.getLeftSibling() != null;
	}

	public boolean isLeaf() {
		return children.isEmpty();
	}

	public boolean hasChild() {
		return !this.isLeaf();
	}

	// Size of the right half of the node
	public double getRightSize() {
		return NODE_WIDTH / 2.0;
	}

	// Size of the left half of the node
	public double getLeftSize() {
		return NODE_WIDTH / 2.0;
	}
	
	public double getTopSize() {
		return NODE_HEIGHT / 2.0;
	}
	
	public double getBottomSize() {
		return NODE_HEIGHT / 2.0;
	}
	
	public int getHeight() {
		return NODE_HEIGHT;
	}

	public int getWidth() {
		return NODE_WIDTH;
	}

//	// The current node's x-coordinate
//	public int XCOORD(){ return XCOORD; }
//	public void XCOORD(int xcoord){ this.XCOORD = xcoord; }
//		
//	// The current node's y-coordinate
//	public int YCOORD(){ return YCOORD; }
//	public void YCOORD(int ycoord){ this.YCOORD = ycoord; }
//	
//	// The current node's preliminary x-coordinate
//	public int PRELIM(){ return PRELIM; }
//	public void PRELIM(int prelim){ this.PRELIM = prelim; }
//
//	// The current node's modifier value
//	public int MODIFIER(){ return MODIFIER; }
//	public void MODIFIER(int modifier){ this.MODIFIER = modifier; }

//	// The current node's nearest neighbor to the left, at the same level
//	public NodeClass LEFTNEIGHBOR(){
//		//TODO
//		return null;		
//	}

}
