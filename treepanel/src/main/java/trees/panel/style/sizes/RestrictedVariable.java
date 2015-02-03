package trees.panel.style.sizes;

import java.awt.Dimension;

import trees.panel.style.Size;

public class RestrictedVariable extends Size {
	
	private int minWidth, minHeight, maxWidth, maxHeight;


	public RestrictedVariable(int minWidth, int minHeight, int maxWidth,
			int maxHeight) {
		super();
		this.minWidth = minWidth;
		this.minHeight = minHeight;
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
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
		return new Dimension(maxWidth, maxHeight);
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
