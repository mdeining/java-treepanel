package trees.style.sizes;

import java.awt.Dimension;

import trees.style.Size;

/**
 * Variable size with lower bounds. The nodes with
 * this size will have sizes within these bounds. When choosing this 
 * size type, the node label will be shown completely if
 * it fits between the bounds, otherwise it will be
 * adjusted to the size.
 * 
 * @author Marcus Deininger
 *
 */
public class MinVariable extends Size {
	
	private int minWidth, minHeight;

	/**
	 * Setting the lower bounds of the size to the given parameters.
	 * For convenience you may use the {@link trees.style.Size#MIN_VARIABLE(int, int) factory method of Size} instead
	 * @param minWidth Minimum width of the node (as pixels).
	 * @param minHeight Minimum height of the node (as pixels).
	 */
	public MinVariable(int minWidth, int minHeight) {
		super();
		this.minWidth = minWidth;
		this.minHeight = minHeight;
	}

	/* (non-Javadoc)
	 * @see trees.panel.style.Size#hasMaximum()
	 */
	@Override
	public boolean hasMaximum() {
		return false;
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
		return null;
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
