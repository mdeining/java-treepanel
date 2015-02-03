package trees.panel.style.sizes;

import java.awt.Dimension;

import trees.panel.style.Size;

public class MaxVariable extends Size {
	
	private int maxWidth, maxHeight;

	public MaxVariable(int maxWidth, int maxHeight) {
		super();
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
	}

	@Override
	public boolean hasMaximum() {
		return true;
	}

	@Override
	public boolean hasMinimum() {
		return false;
	}

	@Override
	public Dimension getMaximum() {
		return new Dimension(maxWidth, maxHeight);
	}

	@Override
	public Dimension getMinimum() {
		return null;
	}

	@Override
	public boolean isFixed() {
		return false;
	}
}