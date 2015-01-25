package trees.panel.style.sizes;

import java.awt.Dimension;

import trees.panel.style.Size;
import trees.panel.style.Style;

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

	@Override
	protected int getWidth(boolean hasVerticalOrientation, boolean hasPointerBoxes, Dimension label) {
		int width = label.width + 2 * Style.LABEL_MARGIN;
		if(!hasVerticalOrientation && hasPointerBoxes) 
			width = width + Style.POINTER_BOX_HEIGHT;
		if(width < minWidth)
			return minWidth;
		else
			return width;
	}

	@Override
	protected int getHeight(boolean hasVerticalOrientation, boolean hasPointerBoxes, Dimension label) {
		int height = label.height + 2 * Style.LABEL_MARGIN;
		if(hasVerticalOrientation && hasPointerBoxes) 
			height = height + Style.POINTER_BOX_HEIGHT;
		if(height < minHeight)
			return minHeight;
		else
			return height;
	}

}
