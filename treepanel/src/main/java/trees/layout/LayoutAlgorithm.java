package trees.layout;

import java.util.HashMap;
import java.util.Map;

import trees.style.Style;
import static trees.layout.Action.*;

/**
 * Internal core class for analyzing a given tree. Only to be used 
 * within the {@link trees.panel.TreePanel TreePanel}. 
 * This is basically an implementation of
 * <i>Walker JQ II. A node-positioning algorithm for general trees. 
 * Software-Practice and Experience 1990; 20(7):685-705.</i> with some
 * adaptions for Java.
 * 
 * @author Marcus Deininger
 *
 */
public class LayoutAlgorithm {
	
	private Style style;

	// A fixed distance used in the final walk of the tree to determine 
	// the absolute x-coordinate and y-coordinate of a node with respect 
	// to the apex node of the tree.
	private int xOffset, yOffset;
	
	// A list of previous nodes at each level.
	private Map<Integer, Node> previousNodes = new HashMap<>();
	
	/**
	 * Executes a full positioning algorithm on the given tree.
	 * @param style The style for displaying. This contains information about
	 *   element separation, orientation, font, etc.
	 * @param root The root of the tree to be displayed.
	 * @return Returns true if the positioning succeeded.
	 */
	public boolean positionTree (Style style, Node root){
		return this.positionTree(style, root, REPOSITION);
	}
	
	/**
	 * Executes a recalculation on the given tree. This can be done
	 * when only the orientation of the tree has changed.
	 * @param style The style for displaying. This contains information about
	 *   element separation, orientation, font, etc.
	 * @param root The root of the tree to be displayed.
	 * @return Returns true if the positioning succeeded.
	 */
	public boolean recalculateTree (Style style, Node root){
		return this.positionTree(style, root, RECALCULATE);
	}
	
	private boolean positionTree (Style style, Node root, Action action){
		if(action.atMost(REALIGN))
			return true;
		
		if(style == null)
			throw new NullPointerException("style must not be null");
		
		if(root == null)
			return true;

		this.style = style;
		root.init(style, action);

		 // skip initial building process on RECALCULATE
		if(action.atLeast(REPOSITION)){
			previousNodes.clear();
			preliminaryPositioning(root, 0);
		}
		
		// Determine how to adjust all the nodes with respect 
		// to the location of the root.			
		switch(this.style.getOrientation()){
			case NORTH:
				xOffset = 0;
				yOffset = 0;
				break;
			case SOUTH:
				xOffset = 0;
				yOffset = getDrawingDepth(root, 0) * style.getLevelSepartion();
				break;
			case EAST:
				xOffset = getDrawingDepth(root, 0) * style.getLevelSepartion();
				yOffset = 0;
				break;
			case WEST: 
				xOffset = 0;
				yOffset = 0;
				break;
		}
		
		return finalPositioning(root, 0, 0);
	}

	private int getDrawingDepth(Node node, int level){
		int depth = level;
		if(level < style.getMaxDepth()){
			for(Node child : node)
				if(child != null){
					int subtreeDepth = getDrawingDepth(child, level + 1);
					if(subtreeDepth > depth)
						depth = subtreeDepth;
				}
		}
		return depth;
	}

	private void preliminaryPositioning(Node node, int level) {
		// Set the pointer to the previous node at this level.
		node.leftNeighbor = previousNodes.get(level);
		previousNodes.put(level, node);
		if (node.isLeaf() || level == style.getMaxDepth()){
			if(node.hasLeftSibling())
				// Determine the preliminary x-coordinate based on:
				// the preliminary x-coordinate of the left sibling,
				// the separation between sibling nodes, and
				// the mean size of left sibling and current node.
				node.prelim = node.getLeftSibling().prelim + node.getLeftSibling().getSize() + style.getSiblingSeparation();
			else // No sibling on the left to worry about.
				node.prelim = 0;
		}else{
			// This Node is not a leaf, so call this procedure
			// recursively for each of its offspring.
			Node leftmost = node.getFirstChild();
			Node rightmost = leftmost;
			preliminaryPositioning(leftmost, level + 1); 
			while(rightmost.hasRightSibling()){
				rightmost = rightmost.getRightSibling();
				preliminaryPositioning(rightmost, level + 1);
			}
			int midpoint = (leftmost.prelim + rightmost.prelim + rightmost.getSize() - node.getSize()) / 2;
			if (node.hasLeftSibling()){
				node.prelim = node.getLeftSibling().prelim + node.getLeftSibling().getSize() + style.getSiblingSeparation();
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
		int depthToStop = style.getMaxDepth() - level;
		while (leftmost != null && neighbor != null && compareDepth <= depthToStop){
			// Compute the location of leftmost and where it should be 
			// with respect to neighbor.
			int leftModsum = 0;
			int rightModsum = 0;
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
			int moveDistance = (neighbor.prelim + leftModsum) 
								  + style.getSubtreeSeparation() + neighbor.getSize()
								  - (leftmost.prelim + rightModsum);
			if (moveDistance > 0){
				
				// Count interior sibling subtrees in LeftSiblings
				Node tempPtr = node;
				int leftSiblings = 0; 
				while (tempPtr != null && tempPtr != ancestorNeighbor){
					leftSiblings = leftSiblings + 1;
					tempPtr = tempPtr.getLeftSibling();
				}

				if(tempPtr != null){
					// Apply portions to appropriate left sibling subtrees.
					int portion = moveDistance / leftSiblings;
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
			while (leftmost == null && rightmost.hasRightSibling()){
				rightmost = rightmost.getRightSibling();
				leftmost = getLeftmost(rightmost, level + 1, depth); 
			}
			return leftmost;
		}
	}

	private boolean finalPositioning(Node node, int level, int modsum) {
		if (level > style.getMaxDepth())
			// We are at a level deeper than what we want to draw.
			return true;

		int xTemp = 0, yTemp = 0;			
		switch(style.getOrientation()){
			case NORTH:
				xTemp = xOffset + node.prelim + modsum;
				yTemp = yOffset + (level * style.getLevelSepartion()); break;
			case SOUTH:
				xTemp = xOffset + node.prelim + modsum;
				yTemp = yOffset - (level * style.getLevelSepartion()); break;
			case EAST:
				xTemp = xOffset - (level * style.getLevelSepartion());
				yTemp = yOffset + node.prelim + modsum; break;
			case WEST:
				xTemp = xOffset + (level * style.getLevelSepartion());
				yTemp = yOffset + node.prelim + modsum; break;
		}
		
		// Check to see that xTemp and yTemp are of the proper size for your application.
		if (!checkExtentsRange(xTemp, yTemp))
			// Continuing would put the tree outside of the drawable extents range.
			return false;
		
		node.xCoordinate = xTemp;
		node.yCoordinate = yTemp;
		
		boolean ok = true;
		if (node.hasChildren()) // Apply the Modifier value for this node to all its offspring.
			ok = finalPositioning (node.getFirstChild(), level + 1, modsum + node.modifier);
		if (ok && node.hasRightSibling())
			ok = finalPositioning(node.getRightSibling(), level, modsum);
		
		return ok;
	}

	private boolean checkExtentsRange(int xValue, int yValue) {
		// xValue is a valid value for the x-coordinate
		// yValue is a valid value for the у-coordinate
		return (xValue >= 0 && yValue >= 0);
	}

}
