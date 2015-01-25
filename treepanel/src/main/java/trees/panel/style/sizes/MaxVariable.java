package trees.panel.style.sizes;

import java.awt.Dimension;

import trees.panel.style.Size;
import trees.panel.style.Style;

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

	@Override
	protected int getWidth(boolean hasVerticalOrientation, boolean hasPointerBoxes, Dimension label) {
		int width = label.width + 2 * Style.LABEL_MARGIN;
		if(!hasVerticalOrientation && hasPointerBoxes) 
			width = width + Style.POINTER_BOX_HEIGHT;
		if(width > maxWidth)
			return maxWidth;
		else
			return width;
	}

	@Override
	protected int getHeight(boolean hasVerticalOrientation, boolean hasPointerBoxes, Dimension label) {
		int height = label.height + 2 * Style.LABEL_MARGIN;
		if(hasVerticalOrientation && hasPointerBoxes) 
			height = height + Style.POINTER_BOX_HEIGHT;
		if(height > maxHeight)
			return maxHeight;
		else
			return height;
	}

}
