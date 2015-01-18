package layout;

public class Settings {
	
	private static final int DEFAULT_MAX_DEPTH = Integer.MAX_VALUE;

	private static final int DEFAULT_SIBLING_SEPARATION = 40;
	private static final int DEFAULT_SUBTREE_SEPARATION = 40;
	private static final int DEFAULT_LEVEL_SEPARATION = 40;

	private static final Orientation DEFAULT_ORIENTATION = Orientation.NORTH;

	private int maxDepth = DEFAULT_MAX_DEPTH, 
			siblingSeparation = DEFAULT_SIBLING_SEPARATION, 
			subtreeSeparation = DEFAULT_SUBTREE_SEPARATION, 
			levelSepartion = DEFAULT_LEVEL_SEPARATION;
	private Orientation rootOrientation = DEFAULT_ORIENTATION;
	
	protected Settings(){
		super();
	}


	protected int getMaxDepth() {
		return maxDepth;
	}

	protected void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}

	protected int getSiblingSeparation() {
		return siblingSeparation;
	}

	protected void setSiblingSeparation(int siblingSeparation) {
		this.siblingSeparation = siblingSeparation;
	}

	protected int getSubtreeSeparation() {
		return subtreeSeparation;
	}

	protected void setSubtreeSeparation(int subtreeSeparation) {
		this.subtreeSeparation = subtreeSeparation;
	}

	protected int getLevelSepartion() {
		return levelSepartion;
	}

	protected void setLevelSepartion(int levelSepartion) {
		this.levelSepartion = levelSepartion;
	}

	protected Orientation getRootOrientation() {
		return rootOrientation;
	}

	protected void setRootOrientation(Orientation rootOrientation) {
		this.rootOrientation = rootOrientation;
	}

	protected boolean hasVerticalOrientation(){
		return rootOrientation == Orientation.NORTH || rootOrientation == Orientation.SOUTH;
	}
	
	protected boolean hasHorizontalOrientation(){
		return rootOrientation == Orientation.EAST || rootOrientation == Orientation.WEST;
	}

}
