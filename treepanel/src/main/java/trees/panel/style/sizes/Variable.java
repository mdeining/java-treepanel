package trees.panel.style.sizes;

import java.awt.Dimension;

import trees.panel.style.Size;
import trees.panel.style.Style;

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
	protected int getWidth(boolean hasVerticalOrientation, boolean hasPointerBoxes, Dimension label) {
		int width = label.width + 2 * Style.LABEL_MARGIN;
		if(!hasVerticalOrientation && hasPointerBoxes) 
			width = width + Style.POINTER_BOX_HEIGHT;
		return width;
	}

	@Override
	protected int getHeight(boolean hasVerticalOrientation, boolean hasPointerBoxes, Dimension label) {
		int height = label.height + 2 * Style.LABEL_MARGIN;
		if(hasVerticalOrientation && hasPointerBoxes) 
			height = height + Style.POINTER_BOX_HEIGHT;
		return height;
	}

	@Override
	public boolean isFixed() {
		return false;
	}

}
