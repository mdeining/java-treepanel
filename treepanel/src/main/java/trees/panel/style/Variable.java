package trees.panel.style;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Rectangle;

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
		int width = label.width + 2 * Style.MARGIN;
		if(!hasVerticalOrientation && hasPointerBoxes) 
			width = width + Style.POINTER_BOX_HEIGHT;
		return width;
	}

	@Override
	protected int getHeight(boolean hasVerticalOrientation, boolean hasPointerBoxes, Dimension label) {
		int height = label.height + 2 * Style.MARGIN;
		if(hasVerticalOrientation && hasPointerBoxes) 
			height = height + Style.POINTER_BOX_HEIGHT;
		return height;
	}

//	@Override
//	public String[] getLabel(FontMetrics metrics, Rectangle area, String label) {
//		return null; //trimLabel(metrics, area, label);
//	}
	
}
