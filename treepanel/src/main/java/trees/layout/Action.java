package trees.layout;

public enum Action {
	
	// there is a complete order in the sense of actions to be done
	
	REPAINT,		// Repainting without changing the the displaying tree
	REALIGN,		// Recalculating the alignments without changing the the displaying tree
	RECALCULATE,	// Redoing the final positioning of the displaying tree
	REPOSITION,		// Redoing the full positioning of the displaying tree
	REBUILD;		// Rebuilding the complete structure
	
	public boolean atLeast(Action action){
		return this.compareTo(action) >= 0;
	}

	public boolean atMost(Action action){
		return this.compareTo(action) <= 0;
	}

}