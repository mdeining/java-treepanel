package trees.panel.style.sizes;

import java.awt.Dimension;

import trees.panel.style.Size;

public class MinVariable extends Size {
	
	private int minWidth, minHeight;

	public MinVariable(int minWidth, int minHeight) {
		super();
		this.minWidth = minWidth;
		this.minHeight = minHeight;
	}

	@Override
	public boolean hasMaximum() {
		return false;
	}

	@Override
	public boolean hasMinimum() {
		return true;
	}

	@Override
	public Dimension getMaximum() {
		return null;
	}

	@Override
	public Dimension getMinimum() {
		return new Dimension(minWidth, minHeight);
	}

	@Override
	public boolean isFixed() {
		return false;
	}
}
