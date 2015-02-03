package trees.panel.style;

public enum Orientation {
	NORTH(false), SOUTH(false), EAST(true), WEST(true);
	
	private boolean horizontal;

	private Orientation(boolean horizontal) {
		this.horizontal = horizontal;
	}

	public boolean isHorizontal() {
		return horizontal;
	}
	
	public boolean isVertical() {
		return !horizontal;
	}	
	
}