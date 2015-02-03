package trees.panel.style.sizes;

import java.awt.Dimension;

import trees.panel.style.Size;

public class Variable extends Size {
	
	@Override
	public boolean hasMaximum() {
		return false;
	}

	@Override
	public boolean hasMinimum() {
		return false;
	}

	@Override
	public Dimension getMaximum() {
		return null;
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
