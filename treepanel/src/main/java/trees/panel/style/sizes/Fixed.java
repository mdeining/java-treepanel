package trees.panel.style.sizes;

import java.awt.Dimension;

import trees.panel.style.Size;

public class Fixed extends Size {
	
	private int width, height;

	public Fixed(int width, int height) {
		super();
		this.width = width;
		this.height = height;
	}

	@Override
	public boolean hasMaximum() {
		return true;
	}

	@Override
	public boolean hasMinimum() {
		return true;
	}

	@Override
	public Dimension getMaximum() {
		return new Dimension(width, height);
	}

	@Override
	public Dimension getMinimum() {
		return new Dimension(width, height);
	}

	@Override
	public boolean isFixed() {
		return true;
	}

	@Override
	protected int getWidth(boolean hasVerticalOrientation, boolean hasPointerBoxes, Dimension label) {
		// Ignore Label
		return width;
	}

	@Override
	protected int getHeight(boolean hasVerticalOrientation, boolean hasPointerBoxes, Dimension label) {
		// Ignore Label
		return height;
	}
}
