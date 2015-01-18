package layout;

public class Settings {
	
	private static final int DEFAULT_MAX_DEPTH = Integer.MAX_VALUE;

	private static final int DEFAULT_SIBLING_SEPARATION = 40;
	private static final int DEFAULT_SUBTREE_SEPARATION = 40;
	private static final int DEFAULT_LEVEL_SEPARATION = 40;

	private static final Orientation DEFAULT_ORIENTATION = Orientation.NORTH;

	private int maxDepth, siblingSeparation, subtreeSeparation, levelSepartion;
	private Orientation rootOrientation;

	public Settings(int maxDepth, int siblingSeparation, int subtreeSeparation,
			int levelSepartion, Orientation rootOrientation) {
		super();
		this.maxDepth = maxDepth;
		this.siblingSeparation = siblingSeparation;
		this.subtreeSeparation = subtreeSeparation;
		this.levelSepartion = levelSepartion;
		this.rootOrientation = rootOrientation;
	}
	
	public Settings(int siblingSeparation, int subtreeSeparation,
			int levelSepartion, Orientation rootOrientation) {
		this(DEFAULT_MAX_DEPTH, siblingSeparation, subtreeSeparation, levelSepartion, rootOrientation);
	}
	
	public Settings(int siblingSeparation, int subtreeSeparation, int levelSepartion) {
		this(DEFAULT_MAX_DEPTH, siblingSeparation, subtreeSeparation, levelSepartion, DEFAULT_ORIENTATION);
	}
	
	public Settings() {
		this(DEFAULT_MAX_DEPTH, DEFAULT_SIBLING_SEPARATION, DEFAULT_SUBTREE_SEPARATION, DEFAULT_LEVEL_SEPARATION, DEFAULT_ORIENTATION);
	}

	public int getMaxDepth() {
		return maxDepth;
	}

	public int getSiblingSeparation() {
		return siblingSeparation;
	}

	public int getSubtreeSeparation() {
		return subtreeSeparation;
	}

	public int getLevelSepartion() {
		return levelSepartion;
	}

	public Orientation getRootOrientation() {
		return rootOrientation;
	}
	
	public boolean hasVericalOrientation(){
		return rootOrientation == Orientation.NORTH || rootOrientation == Orientation.SOUTH;
	}
	
	public boolean hasHorizontalOrientation(){
		return rootOrientation == Orientation.EAST || rootOrientation == Orientation.WEST;
	}

}
