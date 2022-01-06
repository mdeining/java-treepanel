package trees.style.sizes;

import java.awt.Dimension;

import trees.style.Size;

/**
 * Variable size with upper and lower bounds. The nodes with
 * this size will have sizes within these bounds. When choosing this 
 * size type, the node label will be shown completely if
 * it fits between the bounds, otherwise it will be
 * adjusted to the size.
 * 
 * @author Marcus Deininger
 *
 */
public class RestrictedVariable extends Size {
	
	private int minWidth, minHeight, maxWidth, maxHeight;


	/**
	 * Setting the upper and lower bounds of the size to the given parameters.
	 * For convenience you may use the {@link trees.style.Size#RESTRICTED_VARIABLE(int, int, int, int) factory method of Size} instead
	 * @param minWidth Minimum width of the node (as pixels).
	 * @param minHeight Minimum height of the node (as pixels).
	 * @param maxWidth Maximum width of the node (as pixels).
	 * @param maxHeight Maximum height of the node (as pixels).
	 */
	public RestrictedVariable(int minWidth, int minHeight, int maxWidth, int maxHeight) {
		super();
		this.minWidth = minWidth;
		this.minHeight = minHeight;
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
	}

	/* (non-Javadoc)
	 * @see trees.panel.style.Size#hasMaximum()
	 */
	@Override
	public boolean hasMaximum() {
		return true;
	}

	/* (non-Javadoc)
	 * @see trees.panel.style.Size#hasMinimum()
	 */
	@Override
	public boolean hasMinimum() {
		return true;
	}

	/* (non-Javadoc)
	 * @see trees.panel.style.Size#getMaximum()
	 */
	@Override
	public Dimension getMaximum() {
		return new Dimension(maxWidth, maxHeight);
	}

	/* (non-Javadoc)
	 * @see trees.panel.style.Size#getMinimum()
	 */
	@Override
	public Dimension getMinimum() {
		return new Dimension(minWidth, minHeight);
	}

	/* (non-Javadoc)
	 * @see trees.panel.style.Size#isFixed()
	 */
	@Override
	public boolean isFixed() {
		return false;
	}
}
