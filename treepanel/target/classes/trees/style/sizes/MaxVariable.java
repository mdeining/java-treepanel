package trees.style.sizes;

import java.awt.Dimension;

import trees.style.Size;

/**
 * Variable size with upper bounds. The nodes with
 * this size will have sizes within these bounds. When choosing this 
 * size type, the node label will be shown completely if
 * it fits between the bounds, otherwise it will be
 * adjusted to the size.
 * 
 * @author Marcus Deininger
 *
 */
public class MaxVariable extends Size {
	
	private int maxWidth, maxHeight;

	/**
	 * Setting the upper bounds of the size to the given parameters.
	 * For convenience you may use the {@link trees.style.Size#MAX_VARIABLE(int, int) factory method of Size} instead
	 * @param maxWidth Maximum width of the node (as pixels).
	 * @param maxHeight Maximum height of the node (as pixels).
	 */
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