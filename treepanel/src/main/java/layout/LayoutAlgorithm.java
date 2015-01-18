package layout;

public class LayoutAlgorithm {
	
	private static final int SiblingSeparation = 4;
	private static final int SubtreeSeparation = 4;

	private static final int MaxDepth = 20;
	private static final int LevelSeparation = 4;
	
	private NodeList LevelZeroPtr;

	// A fixed distance used in the final walk of the tree to determine 
	// the absolute x-coordinate of a node with respect to the apex node of the tree.
	private double xTopAdjustment;
	
	// A fixed distance used in the final walk of the tree to determine 
	// the absolute y-coordinate of a node with respect to the apex node of the tree.
	private double yTopAdjustment;

	public boolean POSITIONTREE (NodeClass Node){		
		if(Node != null){
			// Initialize the list of previous nodes at each level.
			INITPREVNODELIST();
			// Do the preliminary positioning with a postorder walk.
			FIRSTWALK(Node, 0);
			// Determine how to adjust all the nodes with respect 
			// to the location of the root.
			xTopAdjustment = Node.XCOORD; // - Node.PRELIM;
			yTopAdjustment = Node.YCOORD;
			// Do the final positioning with a preorder walk.
			return SECONDWALK(Node, 0, 0);
		}else //Trivial: return TRUE if a null pointer was passed.
		return true;
	}

	public void FIRSTWALK(NodeClass Node, int Level) {
		// Set the pointer to the previous node at this level.
		Node.LEFTNEIGHBOR = GETPREVNODEATLEVEL(Level);
		SETPREVNODEATLEVEL(Level, Node); // This is now the previous.
		Node.MODIFIER = 0; // Set the default modifier value.
		if (Node.ISLEAF() | Level == MaxDepth){
			if(Node.HASLEFTSIBLING())
				// Determine the preliminary x-coordinate based on:
				// the preliminary x-coordinate of the left sibling,
				// the separation between sibling nodes, and
				// the mean size of left sibling and current node.
				Node.PRELIM = Node.LEFTSIBLING().PRELIM + SiblingSeparation + 
					MEANNODESIZE(Node.LEFTSIBLING(), Node);
			else
				// No sibling on the left to worry about.
				Node.PRELIM = 0;
		}else{
			// This Node is not a leaf, so call this procedure
			// recursively for each of its offspring.
			NodeClass Leftmost = Node.FIRSTCHILD();
			NodeClass Rightmost = Leftmost;
			FIRSTWALK(Leftmost, Level + 1); 
			while(Rightmost.HASRIGHTSIBLING()){
				Rightmost = Rightmost.RIGHTSIBLING();
				FIRSTWALK(Rightmost, Level + 1);
			}
			double Midpoint = (Leftmost.PRELIM +  Rightmost.PRELIM) / 2;
			if (Node.HASLEFTSIBLING()){
				Node.PRELIM = Node.LEFTSIBLING().PRELIM + SiblingSeparation +
						MEANNODESIZE(Node.LEFTSIBLING(), Node); 
				Node.MODIFIER = Node.PRELIM - Midpoint; 
				APPORTION(Node, Level);
			} else
				Node.PRELIM = Midpoint;
		}
	}

	public void APPORTION(NodeClass Node, int Level) {
		NodeClass Leftmost = Node.FIRSTCHILD();
		NodeClass Neighbor = Leftmost.LEFTNEIGHBOR;
		int CompareDepth = 1;
		int DepthToStop = MaxDepth - Level;
		while (Leftmost != null &  Neighbor != null & CompareDepth <= DepthToStop){
			// Compute the location of Leftmost and where it should
			// be with respect to Neighbor.
			double LeftModsum = 0;
			double RightModsum = 0;
			NodeClass AncestorLeftmost = Leftmost;
			NodeClass AncestorNeighbor = Neighbor; 
			for (int i = 0; i < CompareDepth; i++){
				AncestorLeftmost = AncestorLeftmost.PARENT; 
				AncestorNeighbor =  AncestorNeighbor.PARENT;
				RightModsum = RightModsum + AncestorLeftmost.MODIFIER;
				LeftModsum = LeftModsum + AncestorNeighbor.MODIFIER;
			}

			// Find the MoveDistance, and apply it to Node's subtree.
			// Add appropriate portions to smaller interior subtrees.
			double MoveDistance =  (Neighbor.PRELIM + LeftModsum + SubtreeSeparation + MEANNODESIZE(Leftmost, Neighbor)) - 
									(Leftmost.PRELIM + RightModsum);
			if (MoveDistance > 0){
				// Count interior sibling subtrees in LeftSiblings
				NodeClass TempPtr = Node;
				int LeftSiblings = 0; 
				while (TempPtr != null & TempPtr != AncestorNeighbor){
					LeftSiblings = LeftSiblings + 1;
					TempPtr = TempPtr.LEFTSIBLING();
				}

				if(TempPtr != null){
					// Apply portions to appropriate left sibling subtrees.
					double Portion = MoveDistance / LeftSiblings;
					TempPtr = Node;
					while(TempPtr != AncestorNeighbor){ // ??!!!!!
						TempPtr.PRELIM = TempPtr.PRELIM + MoveDistance; 
						TempPtr.MODIFIER = TempPtr.MODIFIER + MoveDistance;
						MoveDistance = MoveDistance - Portion;
						TempPtr = TempPtr.LEFTSIBLING();
					}
				} else 
					// Don't need to move anything--it needs to
					// be done by an ancestor because
					// AncestorNeighbor and AncestorLeftmost are
					// not siblings of each other.
					return;
			} // (MoveDistance > 0)
			
			// Determine the leftmost descendant of Node at the next
			// lower level to compare its positioning against that of
			// its Neighbor.
			CompareDepth = CompareDepth + 1; 
			if (Leftmost.ISLEAF())
				Leftmost =  GETLEFTMOST(Node, 0, CompareDepth);
			else
				Leftmost = Leftmost.FIRSTCHILD();
			if(Leftmost != null)
				Neighbor = Leftmost.LEFTNEIGHBOR;  // ??!!!!!
			else
				Neighbor = null;
		} // while
	}

	private NodeClass GETLEFTMOST(NodeClass Node, int Level, int Depth) {
		if (Level >= Depth)
			return Node; 
		else if (Node.ISLEAF()) 
			return null; 
		else {
			NodeClass Rightmost =  Node.FIRSTCHILD();
			NodeClass Leftmost = GETLEFTMOST (Rightmost, Level + 1, Depth);
			// Do a postorder walk of the subtree below Node.
			while (Leftmost == null & Rightmost.HASRIGHTSIBLING()){
				Rightmost = Rightmost.RIGHTSIBLING();
				Leftmost = GETLEFTMOST(Rightmost, Level + 1, Depth); 
			}
			return Leftmost;
		}
	}

	private double MEANNODESIZE(NodeClass LeftNode, NodeClass RightNode) {
		double NodeSize = 0;
		
		if(LeftNode != null)
			NodeSize = NodeSize + LeftNode.RIGHTSIZE();
		if(RightNode != null)
			NodeSize = NodeSize + RightNode.LEFTSIZE();
		return NodeSize;
	}

	private boolean SECONDWALK(NodeClass Node, int Level, double Modsum) {
		boolean Result = true;
		if (Level <= MaxDepth){
			double xTemp = xTopAdjustment + Node.PRELIM + Modsum;
			double yTemp = yTopAdjustment + (Level * LevelSeparation);
			// Check to see that xTemp and yTemp are of the proper
			// size for your application.
			if (CHECKEXTENTSRANGE(xTemp, yTemp)){
				Node.XCOORD = xTemp;
				Node.YCOORD = yTemp;
				if (Node.HASCHILD())
					// Apply the Modifier value for this node to all its offspring.
					Result = SECONDWALK (Node.FIRSTCHILD(), Level + 1, Modsum + Node.MODIFIER);
				if (Result == true & Node.HASRIGHTSIBLING())
					Result = SECONDWALK(Node.RIGHTSIBLING(), Level + 1, Modsum);
			}else
				// Continuing would put the tree outside of the drawable extents range.
				Result = false;
		} else
			// We are at a level deeper than what we want to draw.
			Result = true;
		return Result;
	}

	private boolean CHECKEXTENTSRANGE(double xValue, double yValue) {
		// xValue is a valid value for the x-coordinate
		// yValue is a valid value for the у-coordinate
		if(xValue >= 0 && yValue >= 0)
			return true;
		else
			return false;
	}

	public void INITPREVNODELIST() {
		LevelZeroPtr = null;
	}

	private NodeClass GETPREVNODEATLEVEL(int Level) {
		// Start with the node at level 0--the apex of the tree.
		NodeList TempPtr = LevelZeroPtr; 
		int i = 0;
		while (TempPtr != null){
			if (i == Level)
				return TempPtr.PREVNODE;
			TempPtr = TempPtr.NEXTLEVEL; 
			i = i + 1;
		}
		// Otherwise, there was no node at the specific level.
		return null;
	}

	private void SETPREVNODEATLEVEL(int Level, NodeClass Node) {
		// Start with the node at level 0-the apex of the tree.
		NodeList TempPtr = LevelZeroPtr; 
		int i = 0;
		while(TempPtr!= null){
				if(i == Level){
					// At this level, replace the existing list
					// element with the passed-in node.
					TempPtr.PREVNODE =  Node;
					return;
				}else if (TempPtr.NEXTLEVEL == null){
					// There isn't a list element yet at this level, so
					// add one. The following instructions prepare the
					// list element at the next level, not at this one.
					NodeList NewNode = new NodeList();
					NewNode.PREVNODE = null;
					NewNode.NEXTLEVEL = null;
					TempPtr.NEXTLEVEL = NewNode;
				}
				//Prepare to move to the next level, to look again. 
				TempPtr = TempPtr.NEXTLEVEL;
				i = i + 1;
		}
		// Should only get here if LevelZeroPtr is nil.
		LevelZeroPtr = new NodeList();
		LevelZeroPtr.PREVNODE = Node;
		LevelZeroPtr.NEXTLEVEL = null;
	}

}
