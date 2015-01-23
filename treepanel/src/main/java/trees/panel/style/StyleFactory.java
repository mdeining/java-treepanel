package trees.panel.style;



public class StyleFactory {
	
	private static final int MAX_DEPTH = Integer.MAX_VALUE;

	private static final int SIBLING_SEPARATION = 40;
	private static final int SUBTREE_SEPARATION = 40;
	private static final int LEVEL_SEPARATION = 40;

	private static final Orientation ORIENTATION = Orientation.NORTH;
	
	private static final Alignment HORIZONTAL_ALIGNMENT = Alignment.LEFT;
	private static final Alignment VERTICAL_ALIGNMENT = Alignment.TOP;
	
	private static final Shape SHAPE = Shape.RECTANGLE; 
	private static final boolean POINTER_BOXES = false;

	private static final Size SIZE = new Fixed(20, 20);
	
	public static Style getPlainStyle(){
		return new Style();
	}
	
	public static Style getDefaultStyle(){
		Style style = new Style();
		style.setMaxDepth(MAX_DEPTH);
		style.setSiblingSeparation(SIBLING_SEPARATION);
		style.setSubtreeSeparation(SUBTREE_SEPARATION);
		style.setLevelSepartion(LEVEL_SEPARATION);
		style.setOrientation(ORIENTATION);
		style.setHorizontalAlignment(HORIZONTAL_ALIGNMENT);
		style.setVerticalAlignment(VERTICAL_ALIGNMENT);
		style.setPointerBoxes(POINTER_BOXES);
		style.setShape(SHAPE);
		style.setSize(SIZE);
		return style;
	}

}
