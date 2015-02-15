package trees.style;

/**
 * Possible alignment values for trees. 
 * 
 * @author Marcus Deininger
 *
 */
public enum Orientation {
	/**
	 * (Vertical) top-down orientation of the tree.
	 */
	NORTH(false), /**
	 * (Vertical) bottom-up orientation of the tree.
	 */
	SOUTH(false), /**
	 * (Horizontal) left-to-right orientation of the tree.
	 */
	EAST(true), /**
	 * (Horizontal) right-to-left orientation of the tree.
	 */
	WEST(true);
	
	private boolean horizontal;

	private Orientation(boolean horizontal) {
		this.horizontal = horizontal;
	}

	/**
	 * Checks if the orientation is horizontal, i.e. <code>EAST</code> or <code>WEST</code>.
	 * @return true, if horizontal orientation.
	 */
	public boolean isHorizontal() {
		return horizontal;
	}
	
	/**
	 * Checks if the orientation is vertical, i.e. <code>NORTH</code> or <code>SOUTH</code>.
	 * @return true, if vertical orientation.
	 */
	public boolean isVertical() {
		return !horizontal;
	}	
	
}