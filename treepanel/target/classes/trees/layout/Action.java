package trees.layout;

/**
 * Helper class for notifying the actions which have been done on the
 * tree in reaction of a change. There is a complete order in the sense of 
 * actions to be done.
 * 
 * @author Marcus Deininger
 *
 */
public enum Action {
	
	/**
	 * Repainting without changing the the displaying tree
	 */
	REPAINT,
	
	/**
	 * Recalculating the alignments without changing the the displaying tree
	 */
	REALIGN,

	/**
	 * Redoing the final positioning of the displaying tree
	 */
	RECALCULATE,
	
	/**
	 * Redoing the full positioning of the displaying tree
	 */
	REPOSITION,
	
	/**
	 * Rebuilding the complete structure
	 */
	REBUILD;
	
	/**
	 * Checks if the given action is at least the same as the action compared to.
	 * @param action - The action to be compared with.
	 * @return returns true if the action is at least the same.
	 */
	public boolean atLeast(Action action){
		return this.compareTo(action) >= 0;
	}

	/**
	 * Checks if the given action is at most the same as the action compared to.
	 * @param action - The action to be compared with.
	 * @return returns true if the action is at most the same.
	 */
	public boolean atMost(Action action){
		return this.compareTo(action) <= 0;
	}

}