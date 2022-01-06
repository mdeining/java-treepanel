package trees.panel.style.sizes;

import java.awt.Dimension;

import trees.panel.style.Size;

/**
 * Fixed size with the same upper and lower bounds. The nodes with
 * this size will have all equal sizes. When choosing this 
 * size type, the node label is adjusted to the size (and not vice versa).
 * 
 * @author Marcus Deininger
 *
 */
public class Fixed extends Size {
	
	private int width, height;

	/**
	 * Setting the (upper and lower) bounds of the size to the given parameters.
	 * For convenience you may use the {@link trees.panel.style.Size#FIXED(int, int) factory method of Size} instead
	 * @param width Width of the node (in pixels).
	 * @param height height of the node (in pixels).
	 */
	public Fixed(int width, int height) {
		super();
		this.width = width;
		this.height = height;
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
		return new Dimension(width, height);
	}

	/* (non-Javadoc)
	 * @see trees.panel.style.Size#getMinimum()
	 */
	@Override
	public Dimension getMinimum() {
		return new Dimension(width, height);
	}

	/* (non-Javadoc)
	 * @see trees.panel.style.Size#isFixed()
	 */
	@Override
	public boolean isFixed() {
		return true;
	}
}
