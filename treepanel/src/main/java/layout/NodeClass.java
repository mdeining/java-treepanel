package layout;

import java.util.ArrayList;
import java.util.List;

public class NodeClass {
	
	public static final int NODE_WIDTH = 2;
	
	private List<NodeClass> children = new ArrayList<>();;
	private String data;

	public NodeClass PARENT, LEFTNEIGHBOR;	
	public double XCOORD, YCOORD, PRELIM, MODIFIER;
	
	public NodeClass(String data) {
		super();
		this.data = data;
	}
	
	public void add(NodeClass ... children){
		for(NodeClass child : children){
			this.children.add(child);
			child.PARENT = this;
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
		return "Node[" + data + ", " + PRELIM + ", " + MODIFIER + ", " + XCOORD + "]";
	}
	
	public void postOrderOut(){
		this.postOrderOut(this);
	}

	private void postOrderOut(NodeClass node){
		if(node == null)
			return;
		for(NodeClass child : node.children)
			postOrderOut(child);
		System.out.println(node.toString());
	}

	// 	The current node's leftmost offspring
	public NodeClass FIRSTCHILD(){
		if(children.isEmpty())
			return null;
		else
			return children.get(0);
	}
	
	// The current node's closest sibling node on the left	
	public NodeClass LEFTSIBLING(){
		if(PARENT == null) // apex
			return null;
		for(int i = 1; i < PARENT.children.size(); i++)
			if(PARENT.children.get(i) == this)
				return PARENT.children.get(i - 1);		
		return null;		
	}
	
	// 	The current node's closest sibling node on the right
	public NodeClass RIGHTSIBLING(){
		if(PARENT == null)
			return null;
		for(int i = 0; i < PARENT.children.size() - 1; i++)
			if(PARENT.children.get(i) == this)
				return PARENT.children.get(i + 1);		
		return null;		
	}

	public boolean HASRIGHTSIBLING() {
		return this.RIGHTSIBLING() != null;
	}

	public boolean HASLEFTSIBLING() {
		return this.LEFTSIBLING() != null;
	}

	public boolean ISLEAF() {
		return children.isEmpty();
	}

	// Size of the right half of the node
	public double RIGHTSIZE() {
		return NODE_WIDTH / 2.0;
	}

	// Size of the left half of the node
	public double LEFTSIZE() {
		return NODE_WIDTH / 2.0;
	}

	public boolean HASCHILD() {
		return !this.ISLEAF();
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
