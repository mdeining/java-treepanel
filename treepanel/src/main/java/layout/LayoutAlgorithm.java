package layout;

import static layout.LayoutAlgorithm.Orientation.*;

public class LayoutAlgorithm {
	
	private static final int SIBLING_SEPARATION = 40;
	private static final int SUBTREE_SEPARATION = 40;
	private static final int MAX_DEPTH = 20;

	private static final int LEVEL_SEPARATION = 40;
	
	// A fixed distance used in the final walk of the tree to determine 
	// the absolute x-coordinate and y-coordinate of a node with respect 
	// to the apex node of the tree.
	private double xTopAdjustment, yTopAdjustment;
	
	public Orientation rootOrientation = NORTH;	
	public enum Orientation {		
		NORTH, SOUTH, EAST, WEST
	}
	
	private NodeList levelZeroPtr;	
	private class NodeList {
		
		public Node prevNode;
		public NodeList nextLevel;
		
		public String toString(){
			StringBuilder sb = new StringBuilder();
			for(NodeList p = this; p != null; p = p.nextLevel)
				sb.append(p.prevNode.toString() + " -> ");
			sb.append("null");
			return sb.toString();
		}
	}


	public boolean positionTree (Node node){		
		if(node != null){
			// Initialize the list of previous nodes at each level.
			initPrevNodeList();
			// Do the preliminary positioning with a postorder walk.
			firstWalk(node, 0);
			// Determine how to adjust all the nodes with respect 
			// to the location of the root.
			
			if (rootOrientation == null)
				rootOrientation = NORTH;
			
			switch(rootOrientation){
				case NORTH:
					xTopAdjustment = 0;
					yTopAdjustment = 0;
					break;
				case SOUTH:
					xTopAdjustment = 0;
					yTopAdjustment = getDrawingDepth(node, 0) * LEVEL_SEPARATION;
					break;
				case EAST: 
					xTopAdjustment = 0;
					yTopAdjustment = 0;
					break;
				case WEST:
					xTopAdjustment = getDrawingDepth(node, 0) * LEVEL_SEPARATION;
					yTopAdjustment = 0;
					break;
			}
			// Do the final positioning with a preorder walk.
			return secondWalk(node, 0, 0);
		}else //Trivial: return TRUE if a null pointer was passed.
			return true;
	}
	
	private int getDrawingDepth(Node node, int level){
		int depth = level;
		if(level < MAX_DEPTH){
			for(Node child : node.children){
				int subtreeDepth = getDrawingDepth(child, level + 1);
				if(subtreeDepth > depth)
					depth = subtreeDepth;
			}
		}
		return depth;
	}

	private void firstWalk(Node node, int level) {
		// Set the pointer to the previous node at this level.
		node.leftNeighbor = getPrevNodeAtLevel(level);
		setPrevNodeAtLevel(level, node); // This is now the previous.
		node.modifier = 0; // Set the default modifier value.
		if (node.isLeaf() | level == MAX_DEPTH){
			if(node.hasLeftSibling())
				// Determine the preliminary x-coordinate based on:
				// the preliminary x-coordinate of the left sibling,
				// the separation between sibling nodes, and
				// the mean size of left sibling and current node.
				node.prelim = node.getLeftSibling().prelim + SIBLING_SEPARATION + 
					meanNodeSize(node.getLeftSibling(), node);
			else // No sibling on the left to worry about.
				node.prelim = 0;
		}else{
			// This Node is not a leaf, so call this procedure
			// recursively for each of its offspring.
			Node leftmost = node.getFirstChild();
			Node rightmost = leftmost;
			firstWalk(leftmost, level + 1); 
			while(rightmost.hasRightSibling()){
				rightmost = rightmost.getRightSibling();
				firstWalk(rightmost, level + 1);
			}
			double midpoint = (leftmost.prelim +  rightmost.prelim) / 2;
			if (node.hasLeftSibling()){
				node.prelim = node.getLeftSibling().prelim + SIBLING_SEPARATION +
						meanNodeSize(node.getLeftSibling(), node); 
				node.modifier = node.prelim - midpoint; 
				apportion(node, level);
			} else
				node.prelim = midpoint;
		}
	}

	private void apportion(Node node, int level) {
		Node leftmost = node.getFirstChild();
		Node neighbor = leftmost.leftNeighbor;
		int compareDepth = 1;
		int depthToStop = MAX_DEPTH - level;
		while (leftmost != null &  neighbor != null & compareDepth <= depthToStop){
			// Compute the location of leftmost and where it should be 
			// with respect to neighbor.
			double leftModsum = 0;
			double rightModsum = 0;
			Node ancestorLeftmost = leftmost;
			Node ancestorNeighbor = neighbor; 
			for (int i = 0; i < compareDepth; i++){
				ancestorLeftmost = ancestorLeftmost.parent; 
				ancestorNeighbor =  ancestorNeighbor.parent;
				rightModsum = rightModsum + ancestorLeftmost.modifier;
				leftModsum = leftModsum + ancestorNeighbor.modifier;
			}

			// Find the moveDistance, and apply it to node's subtree.
			// Add appropriate portions to smaller interior subtrees.
			double moveDistance = (neighbor.prelim + leftModsum) 
								  + SUBTREE_SEPARATION + meanNodeSize(leftmost, neighbor)
								  - (leftmost.prelim + rightModsum);
			if (moveDistance > 0){
				
				// Count interior sibling subtrees in LeftSiblings
				Node tempPtr = node;
				int leftSiblings = 0; 
				while (tempPtr != null & tempPtr != ancestorNeighbor){
					leftSiblings = leftSiblings + 1;
					tempPtr = tempPtr.getLeftSibling();
				}

				if(tempPtr != null){
					// Apply portions to appropriate left sibling subtrees.
					double portion = moveDistance / leftSiblings;
					tempPtr = node;
					while(tempPtr != ancestorNeighbor){
						tempPtr.prelim = tempPtr.prelim + moveDistance; 
						tempPtr.modifier = tempPtr.modifier + moveDistance;
						moveDistance = moveDistance - portion;
						tempPtr = tempPtr.getLeftSibling();
					}
				} else 
					// Don't need to move anything--it needs to
					// be done by an ancestor because
					// ancestorNeighbor and ancestorLeftmost are
					// not siblings of each other.
					return;
			} // (moveDistance > 0)
			
			// Determine the leftmost descendant of Node at the next
			// lower level to compare its positioning against that of
			// its Neighbor.
			compareDepth = compareDepth + 1; 
			if (leftmost.isLeaf())
				leftmost =  getLeftmost(node, 0, compareDepth);
			else
				leftmost = leftmost.getFirstChild();
			if(leftmost != null)
				neighbor = leftmost.leftNeighbor;
			else
				neighbor = null;
		} // while
	}

	private Node getLeftmost(Node node, int level, int depth) {
		if (level >= depth)
			return node; 
		else if (node.isLeaf()) 
			return null; 
		else {
			Node rightmost =  node.getFirstChild();
			Node leftmost = getLeftmost(rightmost, level + 1, depth);
			
			// Do a postorder walk of the subtree below node.
			while (leftmost == null & rightmost.hasRightSibling()){
				rightmost = rightmost.getRightSibling();
				leftmost = getLeftmost(rightmost, level + 1, depth); 
			}
			return leftmost;
		}
	}

	private double meanNodeSize(Node leftNode, Node rightNode) {
		double nodeSize = 0;
		
		switch(rootOrientation){
			case NORTH: case SOUTH:
				if(leftNode != null)
					nodeSize = nodeSize + leftNode.getRightSize();
				if(rightNode != null)
					nodeSize = nodeSize + rightNode.getLeftSize();
				break;
			case EAST: case WEST:
				if(leftNode != null)
					nodeSize = nodeSize + leftNode.getTopSize();
				if(rightNode != null)
					nodeSize = nodeSize + rightNode.getBottomSize();
				break;
		}
		return nodeSize;
	}

	private boolean secondWalk(Node node, int level, double modsum) {
		boolean result = true;
		if (level <= MAX_DEPTH){

			double xTemp = 0, yTemp = 0;			
			switch(rootOrientation){
				case NORTH:
					xTemp = xTopAdjustment + node.prelim + modsum;
					yTemp = yTopAdjustment + (level * LEVEL_SEPARATION); break;
				case SOUTH:
					xTemp = xTopAdjustment + node.prelim + modsum;
					yTemp = yTopAdjustment - (level * LEVEL_SEPARATION); break;
				case EAST:
					xTemp = xTopAdjustment + (level * LEVEL_SEPARATION);
					yTemp = yTopAdjustment + node.prelim + modsum; break;
				case WEST:
					xTemp = xTopAdjustment - (level * LEVEL_SEPARATION);
					yTemp = yTopAdjustment + node.prelim + modsum; break;
			}
			
			// Check to see that xTemp and yTemp are of the proper
			// size for your application.
			if (checkExtentsRange(xTemp, yTemp)){
				node.xCoordinate = xTemp;
				node.yCoordinate = yTemp;
				if (node.hasChild())
					// Apply the Modifier value for this node to all its offspring.
					result = secondWalk (node.getFirstChild(), level + 1, modsum + node.modifier);
				if (result == true & node.hasRightSibling())
					result = secondWalk(node.getRightSibling(), level, modsum);
			}else
				// Continuing would put the tree outside of the drawable extents range.
				result = false;
		} else
			// We are at a level deeper than what we want to draw.
			result = true;
		return result;
	}

	private boolean checkExtentsRange(double xValue, double yValue) {
		// xValue is a valid value for the x-coordinate
		// yValue is a valid value for the у-coordinate
		if(xValue >= 0 && yValue >= 0)
			return true;
		else
			return false;
	}

	private void initPrevNodeList() {
		levelZeroPtr = null;
	}

	private Node getPrevNodeAtLevel(int level) {
		// Start with the node at level 0--the apex of the tree.
		NodeList tempPtr = levelZeroPtr; 
		int i = 0;
		while (tempPtr != null){
			if (i == level)
				return tempPtr.prevNode;
			tempPtr = tempPtr.nextLevel; 
			i = i + 1;
		}
		// Otherwise, there was no node at the specific level.
		return null;
	}

	private void setPrevNodeAtLevel(int level, Node node) {
		// Start with the node at level 0-the apex of the tree.
		NodeList tempPtr = levelZeroPtr; 
		int i = 0;
		while(tempPtr!= null){
				if(i == level){
					// At this level, replace the existing list
					// element with the passed-in node.
					tempPtr.prevNode =  node;
					return;
				}else if (tempPtr.nextLevel == null){
					// There isn't a list element yet at this level, so
					// add one. The following instructions prepare the
					// list element at the next level, not at this one.
					NodeList newNode = new NodeList();
					newNode.prevNode = null;
					newNode.nextLevel = null;
					tempPtr.nextLevel = newNode;
				}
				//Prepare to move to the next level, to look again. 
				tempPtr = tempPtr.nextLevel;
				i = i + 1;
		}
		// Should only get here if LevelZeroPtr is nil.
		levelZeroPtr = new NodeList();
		levelZeroPtr.prevNode = node;
		levelZeroPtr.nextLevel = null;
	}

}
