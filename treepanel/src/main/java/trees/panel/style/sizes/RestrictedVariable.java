package trees.panel.style.sizes;

import java.awt.Dimension;

import trees.panel.style.Size;
import trees.panel.style.Style;

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

	@Override
	protected int getWidth(boolean hasVerticalOrientation, boolean hasPointerBoxes, Dimension label) {
		int width = label.width + 2 * Style.LABEL_MARGIN;
		if(!hasVerticalOrientation && hasPointerBoxes) 
			width = width + Style.POINTER_BOX_HEIGHT;
		if(width > maxWidth)
			return maxWidth;
		else if(width < minWidth)
			return minWidth;
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
		else if(height < minHeight)
			return minHeight;
		else
			return height;
	}

}
